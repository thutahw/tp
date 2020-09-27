# Baymax - User Guide

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## 1. Introduction

Welcome to Baymax! Are you a healthcare professional looking for a reliable app to keep track of patients and 
appointments? You have come to the right place! <br>

Baymax is a Command Line Interface Focused desktop application, it helps you manage customer appointments using just 
the keyboard itself, no fiddling with the mouse is needed! You can now schedule new appointments, change appointments, 
cancel appointments and manage customer information easily with just a few keystrokes. On top of that, it has a few 
neat features that makes appointment scheduling faster and less of a hassle. There is even a built-in calendar that 
helps you to see at a glance how available is a particular day, so that customers can make quick decisions on the 
ground. What are you waiting for? Head on to Section 2, “Quick Start”!

## 2. Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest version of Baymax from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your Baymax application.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. 
   Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. At the top of the screen, type in your commands in the command box and press Enter to execute it.
   e.g. typing **`help`** and pressing Enter will open the help window.<br>

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## 3. Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addPatient name/NAME`, `NAME` is a parameter which can be used as `addPatient name/Alice Tan`.

* Items in square brackets are optional.<br>
  e.g `name/NAME [remark/REMARK]` can be used as `name/Alice Tan remark/No drug allergy` or as `name/Alice Tan`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[tag/TAG]…​` can be used as ` ` (i.e. 0 times), `tag/braces`, `tag/braces tag/cleaning` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `name/NAME contact/PHONE_NUMBER`, `contact/PHONE_NUMBER name/NAME` is also acceptable.

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

Format: `help`

### 3.3 Utilizing the Calendar
This feature allows you to display the availability status of each day in a month, as well as the appointment schedule 
for a period of time or within a particular day. Section 3.3.1 and Section 3.3.2 will guide you through the commands to 
set the calendar to a particular year and month.

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
1. Baymax will switch to the calendar tab.
2. The year 2020 will be displayed on the top right-hand corner of the window.

[insert screenshot]

#### 3.3.2 Switching to a particular month
You can use this command to switch to a particular month based on the year you set in Section 3.3.1. The default is the 
current month. For example, if today is 3rd January 2020, then the calendar will display the year 2020 and the month 
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
1. The month will be set to March and be displayed in the calendar.

[Insert Screenshot]

#### 3.2.2 Displaying appointments on a particular day in the current month
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
1. All appointments on 2020-01-13 will be displayed in the calendar.

[Insert Screenshot]

#### 3.3.1 Displaying appointments within the next n days
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
1. All appointments from 2020-03-01 to 2020-03-07 will be displayed in the calendar.

[Insert Screenshot]

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data
Baymax data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.


## 4. FAQ


## 5. Command summary
