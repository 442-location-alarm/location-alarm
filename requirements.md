# Location Alarm Requirements

## Saving Alarms
- Software must present the “Saved Alarms” screen as the first screen of the application. - Complete
- Software must present saved alarms with:
    - The nickname of the alarm. - Complete
    - The street address of the alarm location. - Complete
- Software must persist alarms (and associated settings) that the user chooses to save
in a list ordered by alarm creation date (earliest created at the start). - Complete
- Software must allow a user to disable/enable an alarm via a toggle icon along the right side of the alarm. - Complete
    - If the alarm is disabled, the phone no longer tracks that location while keeping the alarm settings saved. - Complete
    - If there are no alarms or no alarms are active, the application does not track the user location. - Complete
- Software must allow a user to scroll to see more alarms if there are too many alarms to fit on the screen. - Complete
- Software must allow the user to create a new alarm from the saved alarms screen. - Complete
- Software must allow users to travel to the edit alarm screen upon tapping in the area for that saved alarm anywhere except the disable/enable button. - Complete

## Creating Alarms
- Software must allow a user to create an alarm: - Complete
    - With a user-selected location. - Complete
    - With a nickname for the alarm. - Complete
    - With a circle around the location with a set radius edited via slider - Revised, editing alarm information on the same screen as the map was very cramped so we separated the map and the information, thus meaning no circle on the map
    - With a notification type: phone sound, vibrate, or sound and vibrate. - Revised, we decided that ringing and vibrating at the same time was too much and so the user should only be able to select one at a time
        - Software must use the default ringer sound and vibration intensity. - Complete
- Software must have the minimum circle radius be 0.01 miles and the maximum radius be 50 miles. - Revised, the slider does not allow enough granular control to allow the user to select all these values so we settle for 1 to 50 miles
- Software must allow a user to save the alarm. - Complete

## Editing Alarms
- Software must allow a user to delete an alarm by clicking the delete button. - Complete
- Software must remove alarm from user’s device local storage if it has been deleted. - Complete
- Software must allow a user to edit the alarm’s location and save those updates. - Complete
- Software must allow a user to edit the alarm’s nickname and save those updates. - Complete
- Software must allow a user to edit the size of the circle surrounding the location within which the alarm will ring and save those updates. - Complete
    - Software must display the results of editing the alarm circle by the circle growing larger or smaller on the map. - Revised, no circle as explained above
- Software must allow a user to edit the alarm’s notification type (sound or vibrate) and save those updates. - Complete

## Finding a Location
- Software must allow the user to search for a location by:
    - Coordinate. - Revised, the Google Places Autocomplete API does not allow search by coordinate and we were locked in to using Google APIs from other requirements
    - Name (e.g. University of Washington). - Complete
    - Address. - Complete
- Software must use the Google Maps API to facilitate location search. - Revised, the Google Places API handles search, Maps handles drawing the map/markers on the phone
- Software must display search results as a list of locations ordered by closest to current location - Complete
- Location results must display:
    - Name or street address. - Complete
    - City. - Complete
    - Distance to that location from the user’s current location. - Incomplete
- Software must allow a user to make an alarm with the device current location as the location field. - Complete
- Software must allow the user to place a pin on the map by touching and holding a location, then use that location metadata as the location entry in a new alarm. - Complete

## Running Alarms
- Software must show a non-dismissible notification for each running alarm in the notification panel. The notification disappears if the alarm is disabled or deleted. - Complete
    - The notification must include the alarm name and how many miles until the alarm sounds in a real decimal rounded to two decimal points. - Revised, continuously updating the notification added a lot of overhead with polling for user location continuously sending that to the system so we settled just for the alarm name
- Software must allow a user to tap the active alarm notification, bringing the user to the saved alarms screen. - Incomplete

## Backend
- Software must prompt the user if they are willing to share their device location the first time a user attempts to add an alarm: - Complete
    - If the user accepts, the application allows the user full functionality. - Complete
    - If not, the user is taken back to the saved alarms screen and the application displays the permissions prompt every time the user attempts to add an alarm. - Complete
- Software must use the Google Maps API to display the user’s phone location. - Revised, Google Places does this as outlined above
- Software must use the Google Maps API to display and track active alarm locations (if there are any alarms active). - Revised, the phone location manager does this
- Software must automatically store saved alarms in the local device storage and delete user-deleted alarms from the local device. - Complete
- Software must track the device location only while an alarm is running or when the user is searching for a location. - Complete
