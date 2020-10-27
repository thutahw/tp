# Baymax - User Guide

## Table of Contents
1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)<br>
    [Viewing Help:](#viewing-help--help)<br>
    3.1. [Managing Patient Information](#31-managing-patient-information)<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.1.1. [Add a new patient: `addpatient`](#311-add-a-new-patient-addpatient)<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.1.2. [List all patients: `listpatient`](#312-list-all-patients-listpatient)<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.1.3. [Delete a patient profile: `deletepatient`](#313-delete-a-patient-profile-deletepatient)<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.1.4. [Edit a patient profile: `editpatient`](#314-edit-a-patient-profile-editpatient)<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.1.5. [Find a patient: `find`](#315-find-a-patient-find)<br>
    3.2. [Managing Appointments](#32-managing-appointments)<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.2.1. [Add a new appointment: `addappt`](#321-add-a-new-appointment-addappt)<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.2.2. [List all appointments: `listappt`](#322-list-all-appointments-listappt)<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.2.3. [Edit an appointment: `editappt`](#323-edit-an-appointment-editappt)<br>
    3.3. [Using the Calendar](#33-using-the-calendar) 

--------------------------------------------------------------------------------------------------------------------

## 1. Introduction

Welcome to Baymax! Are you a healthcare professional looking for a reliable app to keep track of patients and
appointments? You have come to the right place! <br>

Baymax is a Command Line Interface Focused desktop application, it helps you manage patient appointments using just
the keyboard itself, no fiddling with the mouse is needed! You can now schedule new appointments, change appointments,
cancel appointments and manage patient information easily with just a few keystrokes. On top of that, it has a few
neat features that makes appointment scheduling faster and less of a hassle. There is even a built-in appointmentCalendar that
helps you to see at a glance how available is a particular day, so that patients can make quick decisions on the
ground. What are you waiting for? Head on to Section 2, “Quick Start”!

## 2. Quick Start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest version of Baymax from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your Baymax application.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds.
   Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)
   Figure 1. Baymax Graphical User Interface

1. At the top of the screen, type in your commands in the command box and press Enter to execute it.
   e.g. typing **`help`** and pressing Enter will open the help window.<br>

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## 3. Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addpatient name/NAME`, `NAME` is a parameter which can be used as `addpatient name/Alice Tan`.

* Items in square brackets are optional.<br>
  e.g `name/NAME [remark/REMARK]` can be used as `name/Alice Tan remark/No drug allergy` or as `name/Alice Tan`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[tag/TAG]…​` can be used as ` ` (i.e. 0 times), `tag/braces`, `tag/braces tag/cleaning` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `name/NAME contact/PHONE_NUMBER`, `contact/PHONE_NUMBER name/NAME` is also acceptable.

</div>

### Viewing help : `help`

Displays a link that brings the user to the help page.

**Format:**<br>
`help`

**Example:**<br>
1. Type `help` into the command box.
2. Press `Enter` on your keyboard <br>

**Outcome:**<br>
1. A link directing the user to the help page will be displayed as shown below.<br>

![help](images/help.png)<br>
Figure 2. Execution of help command

### 3.1 Managing Patient Information

This feature allows you to manage patient information. You can add a new patient, delete an existing patient, edit a 
patient's details, find patients by name and list all the patients in the system.

#### 3.1.1 Add a new patient: `addpatient`
You can use this command to add a new patient who has not yet been registered.

Before we examine the format of this command, let us take a look at what the parameters it takes are and what they mean:

Parameter Name | Description
---------------|------------
NRIC          | The nric of the patient. It must start and end with a capital letter and contain 7 numbers in between them.
NAME          | The name of the patient. It must consist solely of alphabets, and should be a combination of the first and last name in that order. E.g. Alice Tan
CONTACT       | The hand phone number which the patient wishes to be contacted by. It must consist solely of numbers. E.g. 91710012
GENDER        | The gender of the patient. In short, female is indicated by the letter ‘F’ and male is indicated by the letter ‘M’.

**Format:**<br>
`addpatient nric/NRIC name/NAME contact/CONTACT gender/GENDER`

**Example:**<br>
1. Type `addpatient nric/S9772234F name/Jason Tan contact/98765432 gender/M` into the command box.
2. Press `Enter` on your keyboard.

**Outcome:**<br>
If the command is valid (i.e. the user keyed in the valid fields):
1. A success message will be displayed. <br>

![addpatient](images/addpatient.png)<br>
Figure 3. Adding a patient 

#### 3.1.2 List all patients: `listpatient`
You can use this command to list all the patients in the system.

**Format:**<br>
`listpatient`

**Example:**<br>
1. Type `listpatient` into the command box.
2. Press `Enter` on your keyboard.

**Outcome:**<br>
1. All the patients in the system will be listed as shown below.

![listpatient](images/listpatient.png)<br>
Figure 4. Listing all patients

#### 3.1.3 Delete a patient profile: `deletepatient`
You can use this command to delete a patient’s profile by his or her ID. However, you will need to use the `listpatient` command
or the `findpatient` command to find out the patient’s ID first. This extra step helps you to confirm the profile to be deleted, and prevent
accidental deletes.

**Format:**<br>
`deletepatient PATIENT_ID`

**Example:**<br>
1. Type `deletepatient 4` into the command box.
2. Press `Enter` on your keyboard.

**Outcome:**<br>
If the _PATIENT_ID_ (i.e 4) is valid:
1. A success message will be displayed as shown below.
2. The patient at index 4 in the recent list will be deleted.

![deletepatient](images/deletepatient.png)<br>
Figure 5. Deleting a patient's profile

#### 3.1.4 Edit a patient profile: `editpatient`
You can use this command to edit a patient’s profile. However, you will need to use the `listpatient` command
or the `findpatient` command to find out the patient’s ID first. This extra step helps you to confirm the profile to be edited, and prevent
accidental edits.

**Format:**<br>
`editpatient PATIENT_ID t/TAG`

**Example:**<br>
1. Type `editpatient 2 t/Asthmatic` into the command box.
2. Press `Enter` on your keyboard.

**Outcome:**<br>
If the _PATIENT_ID_ (i.e 2) is valid:
1. A success message will be displayed as shown below.
2. The tag of the patient at index 2 in the recent list will be edited.

![editpatient](images/editpatient.png)<br>
Figure 6. Editing a patient's profile

#### 3.1.5 Find a patient: `findpatient`
You can use this command to find a patient’s profile by entering a part of his name. The search string for name is not case-sensitive.

**Format:**<br>
`findpatient NAME`

**Example:**<br>
1. Type `findpatient Alex` into the command box.
2. Press `Enter` on your keyboard.

**Outcome:**<br>
1. A success message will be displayed as shown below.

![findpatient](images/findpatient.png)<br>
Figure 7. Finding patient by name

### 3.2 Managing Appointments
This feature allows you to manage the appointments of every single patient. You can
add an appointment, edit an appointment and list all the appointments in the system.

#### 3.2.1 Add a new appointment: `addappt`
You can use this command to add a new appointment for a patient.

Before we examine the format of this command, let us take a look at what the parameters it takes are and what they mean:

Parameter Name | Description
---------------|------------
ID            | The index of the patient.
DATETIME      | The date followed by the time of the appointment.
DESCRIPTION   | The description of the appointment.
TAG           | The tag related to the appointment.

**Format:**<br>
`addappt id/ID dt/DATETIME desc/DESCRIPTION t/TAG`

**Example:**<br>
1. Type `addappt id/1 dt/11-10-2020 12:30 desc/Removal of braces. t/DrGoh t/1HR` into the command box.
2. Press `Enter` on your keyboard.

**Outcome:**<br>
If the command is valid (i.e. the user keyed in the valid fields):
1. A success message will be displayed as shown below. <br>

![addappt](images/addappt.png)<br>
Figure 8. Scheduling a new appointment for a patient

#### 3.2.2 List all appointments: `listappt`
You can use this command to list all the appointments in the system.

**Format:**<br>
`listappt`

**Example:**<br>
1. Type `listappt` into the command box.
2. Press `Enter` on your keyboard.

**Outcome:**<br>
1. All the appointments in the system will be listed as shown below.

![listappt](images/listappt.png)<br>
Figure 9. List all appointments

#### 3.2.3. Edit an appointment: `editappt`
You can use this command to edit an appointment. However, you will need to use the `listappt` command
to find out the patient’s ID first. This extra step helps you to confirm the appointment to be edited, and prevent
accidental edits.

**Format:**<br>
`editappt INDEX dt/DATETIME`

**Example:**<br>
1. Type `editappt 1 dt/12-10-2020 12:00` into the command box.
2. Press `Enter` on your keyboard.

**Outcome:**<br>
If the INDEX (i.e 1) is valid:
1. A success message will be displayed as shown below.
2. The `DATETIME` of the appointment at index 1 in the recent list will be edited.

![editappt](images/editappt.png)<br>
Figure 10. Edit an appointment

### 3.3. Using the Calendar
This feature allows you to display the availability status of each day in a month, as well as the appointment schedule
for a period of time or within a particular day. Section 3.3.1 and Section 3.3.2 will guide you through the commands to
set the appointmentCalendar to a particular year and month.

#### 3.3.1 Switching to a particular year
You can use this command to switch to a particular year. The default is the current year. Suppose there are appointments
scheduled one year in advance, you can use this function to switch to the following year. The year set by this command
will affect the command we will discuss in Section 3.3.2 (Switching to a particular month).

**Format:**<br>
`year YEAR`

**Example:**<br>
1. Type year 2020 into the command box.
2. Press enter on your keyboard.

**Outcome:**<br>
1. Baymax will switch to the appointmentCalendar tabId.
2. The year 2020 will be displayed on the top right-hand corner of the window.

[App Screenshot (Still in Progress)]

#### 3.3.2 Switching to a particular month
You can use this command to switch to a particular month based on the year you set in Section 3.3.1. The default is the
current month. For example, if today is 3rd January 2020, then the appointmentCalendar will display the year 2020 and the month
January by default.

Parameter Name | Description
---------------|------------
MONTH          | The month you want to switch to. It must be a positive number from 1 (January) to 12 (December).

**Format:**<br>
`month MONTH`

**Example:**<br>
1. Type year 2020 into the command box and press enter to switch to the year 2020.
2. Type month 3 into the command box.
3. Press enter on your keyboard.

**Outcome:**<br>
1. The month will be set to March and be displayed in the appointmentCalendar.

[App Screenshot (Still in Progress)]

#### 3.3.3 Displaying appointments on a particular day in the current month
You can use this command to display all appointments on a particular day.

Parameter Name | Description
---------------|------------
DAY            | The day of the month you want to switch to. It must be a positive number from one to the last day of the month. For example, if the month is February (which only has 28 days), the range of numbers you can enter is 1 to 28.

**Format:**<br>
`day DAY`

**Example:**<br>
1. Type year 2020 into the command box and press enter to switch to the year 2020.
2. Type month 1 into the command box and press enter to switch to the month January.
3. Type day 13 into the command box.
4. Press enter on your keyboard.

**Outcome:**<br>
1. All appointments on 2020-01-13 will be displayed in the appointmentCalendar.

[App Screenshot (Still in Progress)]

#### 3.3.4 Displaying appointments within the next n days
You can use this command to display all appointments within the next n days. For example, choosing n to be 7, Baymax
will display all appointments in the upcoming week (7 days).

**Format:**<br>
`next DAYS`

**Example:**<br>
1. Type year 2020 into the command box and press enter to switch to the year 2020.
2. Type month 3 into the command box and press enter to switch to March.
3. Type day 1 into the command box and press enter to switch to the first day of March.
4. Type  next 7 into the command box.
5. Press enter on your keyboard.

**Outcome:**
1. All appointments from 2020-03-01 to 2020-03-07 will be displayed in the appointmentCalendar.

[App Screenshot (Still in Progress)]

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data
Baymax data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.


## 4. FAQ


## 5. Command summary
