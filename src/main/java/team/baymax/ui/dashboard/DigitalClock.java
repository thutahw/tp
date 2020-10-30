package team.baymax.ui.dashboard;

import java.util.Calendar;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;
import team.baymax.commons.util.StringUtil;

public class DigitalClock extends Label {

    private EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Calendar calendar = Calendar.getInstance();
            String hourString = StringUtil.pad(2, ' ', calendar.get(Calendar.HOUR) == 0
                    ? "12" : calendar.get(Calendar.HOUR) + "");
            String minuteString = StringUtil.pad(2, '0', calendar.get(Calendar.MINUTE) + "");
            String secondString = StringUtil.pad(2, '0', calendar.get(Calendar.SECOND) + "");
            String postfix = calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";

            setText(hourString + ":" + minuteString + ":" + secondString + " " + postfix);
        }
    };

    /**
     * Constructs a digital clock.
     */
    public DigitalClock() {
        bindToTime();
        setStyle("-fx-font-size: 30; -fx-font-family: 'Apple Braille'");
    }

    /**
     * Starts the clocks.
     */
    private void bindToTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), eventHandler),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
