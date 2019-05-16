# Requirement Verification Plan

## Manual Tested Requirements
These requirements are mainly visually tested, so we will run the app on android studio and have one group member walk through the application. Requirements are listed in the order they will be encountered/performed in the walkthrough. As a note, we will do one walkthrough where permissions are allowed then another where permissions are denied to test the first requirement.

#### Software must prompt the user for all necessary permissions the first time the application is opened. If the user accepts, the application allows the user full functionality. If not, the Android OS will not allow any of the functionalities that require denied permissions
Failure Conditions:
- Upon opening for the first time, the app does not prompt the user for permissions
- The app continues to prompt the user for a permission after allowing it
- The app allows the user to access functionality where a permission has been denied

#### Software must present the “Saved Alarms” screen as the first screen of the application
Failure Conditions:
- The app opens to any screen that is not the “Saved Alarm” screen
- The app throws an exception of any kind

#### Software must allow the user to create a new alarm from the saved alarms screen
Failure Conditions:
- There is no way for the user to travel to the create a new alarm screen
- The method of traveling to the create alarm screen is obstructed or inaccessible

#### Software must allow a user to create an alarm with a user-selected location, nickname for the alarm, circle around the location with a set radius edited via slider, and notification type (phone sound, vibrate, or sound and vibrate)
Failure Conditions:
- Any of the fields are not present at the time of creation
- Any of the fields are not accessible to the user for editing / input
- An exception of any kind is thrown

#### Software must present saved alarms with the nickname of the alarm and the street address/name/coordinates of the alarm location (based on input).
Failure Conditions:
- Any created alarm does not appear on the Saved Alarms screen
- Deleted alarms appear on the saved alarms screen
- The nickname or address/name/coordinates of the alarm are not visible
- The nickname or street address of the alarm are incorrect

#### Software must allow users to travel to the edit alarm screen upon tapping in the area for that saved alarm anywhere except the disable/enable button.
Failure Conditions:
- There is no way for the user to travel to the create a edit alarm screen
- The method of traveling to the create alarm screen is obstructed or inaccessible
- Selecting the enable/disable button travels the user to the edit alarm screen

#### Software must allow a user to delete an alarm or edit it (location, nickname, radius, notification type)
Failure Conditions:
- Any of the fields are not present at the time of editing
- Any of the fields are not accessible to the user for input
- Location radius is not visible or interactable
- Any condition is not accepted
- An exception of any kind is thrown
- Edits are not reflected in the saved version of the alarm
- A deleted alarm is displayed on the saved alarms screen

#### Software must allow the user to place a pin on the map by touching and holding a location, then use that location metadata as the location entry in a new alarm.
Failure Conditions:
- User is unable to interact with the map
- The map does not appear for the user
- User is unable to mark a pin placement on the map
- The location data for the alarm is not set correctly to the pin location
- An exception of any kind is thrown

#### Software must show a non-dismissible notification for each running alarm in the notification panel. The notification disappears if the alarm is disabled or deleted.
Failure Conditions:
- Notification fails to appear upon starting an alarm
- Notification fails to disappear upon disabling/deleting an alarm
- Multiple notification fail to appear for multiple alarms being active
- Notifications disappear upon attempting to dismiss them

#### Software must allow a user to tap the active alarm notification, bringing the user to the alarm list.
Failure Conditions:
- Tapping the notification does not bring the user to the alarm list
- An exception of any kind is thrown upon tapping a notification.

## Automatic Tested Requirements

### Saving Alarms

#### Software must persist alarms (and associated settings) to a database.
For this requirement, we will create a test alarm with the following fields:
- Name: Test Alarm
- Location: University of Washington
- Radius: .01 km
- Notification Type: vibrate

We will mock our database in-memory and save the alarm (and all generated settings like latitude and longitude) to this database, storing the ID of the pushed object in the process. We will then use the ID to retrieve the alarm and check all fields of the retrieved alarm against the original alarm.

Failure Conditions:
- Any exception occurs when creating the test alarm
- The alarm fails to push to the in-memory database
- The alarm fails to be retrieved from the in-memory database
- Any of the fields in the retrieved alarm do not match the corresponding field in the original alarm

#### Software must order saved alarms by alarm creation date (earliest created at the start).
For this requirement, we will create two test alarms with the following fields (alarm one created first):

One:
- Name: Test Alarm 1
- Location: University of Washington
- Radius: .01 km
- Notification Type: vibrate

Two:
- Name: Test Alarm 2
- Location: University of Washington
- Radius: .01 km
- Notification Type: vibrate

We will mock our database in-memory and save alarm two then alarm one in the database. We will retrieve all alarms from the database and ensure alarm one is before alarm two.

Failure Conditions:
- An exception occurs when creating either of the two alarms
- Either alarm fails to push to the in-memory database
- There is any error when retrieving all alarms
- The size of the retrieved alarms list is anything other than two
- Any of the fields in the retrieved alarms do not match up with the corresponding fields in the original alarms
- In the returned alarms list, alarm one (the earlier created one) is not before alarm two

#### Software must allow a user to disable/enable an alarm. If the alarm is disabled, the proximity alert for that location is cancelled.
For this requirement, we will create an alarm with the fields:
- Name: Test Alarm 1
- Location: 0, 0
- Radius: .01 km
- Notification Type: vibrate

We will enable the alarm and use a mock location provider to set the phone location to 0, 0 and check for a proximity alert. We will then change the mock location to 100, 100, re-enable the alarm (so another proximity alert is primed but not sent immediately), disable the alarm, change the mock location back to 0, 0 and ensure that no proximity alert triggers.

Failure Conditions:
- An exception occurs when creating the alarm
- An exception occurs when enabling the alarm
- An exception occurs when disabling the alarm
- The alarm does not trigger when the alarm is enabled and the phone is within .01km of 0, 0
- The alarm does trigger when the alarm is disabled and the phone is within .01km of 0, 0

### Editing Alarms

#### Software must allow a user to delete an alarm (removing it from the database)
For this requirement, we will create an alarm with the fields:
- Name: Test Alarm 1
- Location: University of Washington
- Radius: .01 km
- Notification Type: vibrate

We will store that alarm, save the alarm ID, delete the alarm and then request the alarm from the database. The request should return nothing meaning that the deletion was successful.

Failure Conditions:
- Any exceptions occur in creating, saving, or deleting the alarm
- The alarm is present in the database after delete has been called

#### Software must allow the saving of edits made by the user
For this requirement, we will create an alarm with the following fields:
- Name: Test Alarm 1
- Location: University of Washington
- Radius: .01 km
- Notification Type: vibrate

We will then make edits to all editable fields, save the changes, request the alarm from the database and check that those changes have been made:
- Name: Test Alarm 1 -> Test Alarm 2
- Location: University of Washington -> 0, 0
- Radius: .01 km -> .02 km
- Notification Type: vibrate -> sound

Failure Conditions:
- Any exceptions occur in creating, editing, or saving the alarm
- Any of the edits are not reflected in the edited alarm

### Finding a Location

#### Software must allow the user to search for a location by coordinates, name (e.g. University of Washington), or address.
For this requirement, we will create three separate tests: searching for a coordinate, searching by name, searching by address. We will use the same location in all three cases (and we will find the name, coordinates, and address for that location prior on Google Maps) and we will store the Google Maps API expected output for that location for comparison. We will query the Google Maps API and assert that the output we receive is the same as our expected in all three cases.  

Failure Conditions:
- An exception occurs when querying the Google Maps API
- In any case, the output received differs from the expected output

#### Software must allow a user to make an alarm at the device current location.
For this requirement, we will create a test alarm with the following fields:
- Name: Test Current Location Alarm
- Location: (this will be the device’s current location)
- Radius: .01 km
- Notification Type: vibrate

We will use a mock location provider to set the location to 0, 0 before we create the alarm. Upon creation of the alarm, we will make sure that the location field of the alarm is 0, 0. As a note, we will implement alarm creation such that the ‘device current location’ option will set the alarm location to coordinates and that is why this test uses coordinates.

Failure Conditions:
- An exception occurs while creating the alarm
- The location of the alarm is not 0, 0

## Verification Process
For our automatic tested requirements, we will run tests on every build (using Gradle). We will also disable commits directly to master and use Travis CI to build all branches on commit and enforce the rule that branch builds must pass all test cases to merge to master.

For our manual tested requirements, we will test the requirements that have been implemented by the end of the week/sprint (with all tested 6/7/19). One person will walk through the manual testing process with the other three watching and taking notes to ensure that nothing is missed.

At the end of every sprint, we will conduct code reviews before merging pull requests to master. We will all review the code (including the person that wrote it) and everyone must agree before the branch is merged. These reviews will not be focused on analyzing the code (since tests largely handle that) but it is fair game to bring up edge cases/investigate the logic behind the code to ensure the code works as intended. The primary aspects of the code we will investigate are:
Variable and function names - do all names make sense and accurately reflect the data/functionality?
Lack of duplication - are there areas where refactoring can create a reusable pattern?
Overall logic/efficiency - do the data structures used and actions performed make sense?
Test coverage/effectiveness - do current tests appropriately cover all inputs/outputs?
