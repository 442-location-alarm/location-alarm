# Location Alarm Requirements

## Saving Alarms
- Software must present the “Saved Alarms” screen as the first screen of the application.
- Software must present saved alarms with:
    - The nickname of the alarm.
    - The street address of the alarm location.
- Software must persist alarms (and associated settings) that the user chooses to save
in a list ordered by alarm creation date (earliest created at the start).
- Software must allow a user to disable/enable an alarm via a toggle icon along the right side of the alarm.
    - If the alarm is disabled, the phone no longer tracks that location while keeping the alarm settings saved.
    - If there are no alarms or no alarms are active, the application does not track the user location.
- Software must allow a user to scroll to see more alarms if there are too many alarms to fit on the screen.
- Software must allow the user to create a new alarm from the saved alarms screen.
- Software must allow users to travel to the edit alarm screen upon tapping in the area for that saved alarm anywhere except the disable/enable button.

## Creating Alarms
- Software must allow a user to create an alarm:
    - With a user-selected location.
    - With a nickname for the alarm.
    - With a circle around the location with a set radius edited via slider
    - With a notification type: phone sound, vibrate, or sound and vibrate.
        - Software must use the default ringer sound and vibration intensity.
- Software must have the minimum circle radius be 0.01 miles and the maximum radius be 50 miles.
- Software must allow a user to save the alarm.

## Editing Alarms
- Software must allow a user to delete an alarm by clicking the delete button.
- Software must remove alarm from user’s device local storage if it has been deleted.
- Software must allow a user to edit the alarm’s location and save those updates.
- Software must allow a user to edit the alarm’s nickname and save those updates.
- Software must allow a user to edit the size of the circle surrounding the location within which the alarm will ring and save those updates.
    - Software must display the results of editing the alarm circle by the circle growing larger or smaller on the map.
- Software must allow a user to edit the alarm’s notification type (sound or vibrate) and save those updates.

## Finding a Location
- Software must allow the user to search for a location by:
    - Coordinate.
    - Name (e.g. University of Washington).
    - Address.
- Software must use the Google Maps API to facilitate location search.
- Software must display search results as a list of locations ordered by closest to current location
- Location results must display:
    - Name or street address.
    - City.
    - Distance to that location from the user’s current location.
- Software must allow a user to make an alarm with the device current location as the location field.
- Software must allow the user to place a pin on the map by touching and holding a location, then use that location metadata as the location entry in a new alarm.

## Running Alarms
- Software must show a non-dismissible notification for each running alarm in the notification panel. The notification disappears if the alarm is disabled or deleted.
    - The notification must include the alarm name and how many miles until the alarm sounds in a real decimal rounded to two decimal points.
- Software must allow a user to tap the active alarm notification, bringing the user to the saved alarms screen.

## Backend
- Software must prompt the user if they are willing to share their device location the first time a user attempts to add an alarm:
    - If the user accepts, the application allows the user full functionality.
    - If not, the user is taken back to the saved alarms screen and the application displays the permissions prompt every time the user attempts to add an alarm.
- Software must use the Google Maps API to display the user’s phone location.
- Software must use the Google Maps API to display and track active alarm locations (if there are any alarms active).
- Software must automatically store saved alarms in the local device storage and delete user-deleted alarms from the local device.
- Software must track the device location only while an alarm is running or when the user is searching for a location.
