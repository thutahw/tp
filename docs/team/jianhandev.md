---
layout: page
title: Li Jianhan's Project Portfolio Page
---

## Project: Baymax

Baymax is a desktop appointment book application that helps small clinics manage patient and appointment information. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 20 kLoC.

Given below are my contributions to the project.

* **New Feature**: Appointment Calendar
  * What it does: allows the user to visualise date and time, coupled with a schedule view which displays appointments chronologically on a timeline.
  * Justification: This feature improves the user's experience as they can hop between different dates inside the Calendar view, and zoom into a particular day for all the appointments on that day using a single command.
  * Highlights: This enhancement revolutionized the way dates are handled in our CLI application. For example, with the use of the calendar, the user can add appointments to a day without having to enter the date itself since it has been captured by the Calendar. This shortens commands and quickens the process of appointment scheduling.
  It required an in-depth analysis of design alternatives. The implementation too was challenging as it required the implementation of an entirely new Calendar Manager that is very different from the traditionally used List Managers.

* **New Feature**: Added a history command that allows the user to navigate to previous commands using up/down keys.

* **Code contributed**: [RepoSense link]()

* **Project management**:
  * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Updated the GUI color scheme (Pull requests [\#33](), [\#34]())
  * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
  * User Guide:
    * Added documentation for the features `delete` and `find` [\#72]()
    * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
  * Developer Guide:
    * Added implementation details of the `delete` feature.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
  * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
  * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
  * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* **Tools**:
  * Integrated a third party library (Natty) to the project ([\#42]())
  * Integrated a new Github plugin (CircleCI) to the team repo

* _{you can add/remove categories in the list above}_
