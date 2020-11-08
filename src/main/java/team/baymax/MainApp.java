package team.baymax;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import team.baymax.commons.core.Config;
import team.baymax.commons.core.LogsCenter;
import team.baymax.commons.core.Version;
import team.baymax.commons.exceptions.DataConversionException;
import team.baymax.commons.util.ConfigUtil;
import team.baymax.commons.util.StringUtil;
import team.baymax.logic.Logic;
import team.baymax.logic.LogicManager;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentMatchesDatePredicate;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.modelmanagers.ReadOnlyListManager;
import team.baymax.model.patient.Patient;
import team.baymax.model.userprefs.ReadOnlyUserPrefs;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.model.util.SampleDataUtil;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.DateTimeUtil;
import team.baymax.storage.Storage;
import team.baymax.storage.StorageManager;
import team.baymax.storage.appointment.AppointmentManagerStorage;
import team.baymax.storage.appointment.JsonAppointmentManagerStorage;
import team.baymax.storage.patient.JsonPatientManagerStorage;
import team.baymax.storage.patient.PatientManagerStorage;
import team.baymax.storage.userprefs.JsonUserPrefsStorage;
import team.baymax.storage.userprefs.UserPrefsStorage;
import team.baymax.ui.Ui;
import team.baymax.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Baymax ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        PatientManagerStorage patientManagerStorage = new JsonPatientManagerStorage(
                userPrefs.getPatientStorageFilePath());
        AppointmentManagerStorage appointmentManagerStorage = new JsonAppointmentManagerStorage(
                userPrefs.getAppointmentStorageFilePath());
        storage = new StorageManager(patientManagerStorage, appointmentManagerStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);

        initDashboard(model);

    }

    private void initDashboard(Model model) {

        Date today = DateTimeUtil.getCurrentDate();

        model.updateFilteredAppointmentList(new AppointmentMatchesDatePredicate(today));
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage} files and {@code userPrefs}. <br>
     * The data from the sample Baymax will be used instead if {@code storage} files are not
     * found, or an empty Baymax app will be used instead if errors occur when reading {@code storage} files.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        ReadOnlyListManager<Patient> patientManager = initPatientManager(storage);
        ReadOnlyListManager<Appointment> appointmentManager = initAppointmentManager(patientManager, storage);
        CalendarManager calendarManager = new CalendarManager();
        return new ModelManager(patientManager, appointmentManager, userPrefs, calendarManager);
    }

    /**
     * Returns a {@code ReadOnlyListManager<Patient>} with the data from {@code storage}'s patients file. The data from
     * the sample patients file will be used instead if {@code storage}'s patient manager is not found, or an empty
     * patient manager will be used isntead of errors occur when reading {@code storage}'s patient manager.
     */
    private ReadOnlyListManager<Patient> initPatientManager(Storage storage) {
        Optional<ReadOnlyListManager<Patient>> patientManagerOptional;
        ReadOnlyListManager<Patient> initialPatientManager;

        try {
            patientManagerOptional = storage.readPatients();
            if (!patientManagerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample patient manager");
            }
            initialPatientManager = patientManagerOptional.orElseGet(SampleDataUtil::getSamplePatientManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty PatientManager");
            initialPatientManager = new PatientManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty PatientManager");
            initialPatientManager = new PatientManager();
        }

        return initialPatientManager;
    }

    /**
     * Returns a {@code ReadOnlyListManager<Appointment>} with the data from {@code storage}'s appointments file.
     * The data from the sample appointments file will be used instead if {@code storage}'s appointment manager is
     * not found, or an empty appointment manager will be used isntead of errors occur when reading {@code storage}'s
     * appointment manager.
     */
    private ReadOnlyListManager<Appointment> initAppointmentManager(
            ReadOnlyListManager<Patient> patientManager, Storage storage) {
        Optional<ReadOnlyListManager<Appointment>> appointmentManagerOptional;
        ReadOnlyListManager<Appointment> initialAppointmentManager;

        try {
            appointmentManagerOptional = storage.readAppointments(new PatientManager(patientManager));
            if (!appointmentManagerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample appointment manager");
            }
            initialAppointmentManager = appointmentManagerOptional
                    .orElseGet(SampleDataUtil::getSampleAppointmentManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AppointmentManager");
            initialAppointmentManager = new AppointmentManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AppointmentManager");
            initialAppointmentManager = new AppointmentManager();
        }

        return initialAppointmentManager;
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty UserPrefs");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Baymax " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Baymax ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
