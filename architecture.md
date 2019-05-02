# Blueprint for Woke

## LocationManager
Prebuilt: [API here](https://developer.android.com/reference/android/location/LocationManager.html)

### Responsibilities
Tracks phone location and proximity to any active alarms. Fires an intent (phone vibration/sound) if phone location is within a certain radius of an active alarm. Also responsible for canceling the proximity alert if the alarm is disabled.

### Data Encapsulated:
- N/A - this is prebuilt class instantiated as so: locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); so we do not know the encapsulated data. At most we know that it includes some sort of location service field since this is passed in the constructor.

### Functionality:
- Add proximity alert
    - Inputs:
        - Latitude - double
        - Longitude - double
        - Radius, in meters - float
        - Expiration, milliseconds or -1 for no expiration - long
        - Intent, another prebuilt android class that specifies an action to take - PendingIntent
    - Output:
        - N/A
- Remove proximity alert
    - Input:
        - Intent, the action that no longer needs to happen - PendingIntent
    - Output
        - N/A

### Connections
- Alarm calls in the enable method to add a proximity alert to the alarm location
- Alarm calls in the disable method to remove the proximity alert for the alarm

## AlarmStore

### Responsibilities
Saves, updates, and deletes alarm data in the application database and retrieves all saved alarms.

### Data Encapsulated
- The database connection

### Functionality
- Add alarm metadata
    - Inputs
        - Alarm data object (from AlarmManager)
    - Output
        - Alarm data object with new database ID
- Update stored alarm metadata
    - Input:
        - Updated alarm object
    - Output
        - N/A
- Delete alarm metadata
    - Input
        - Alarm ID (long)
    - Output
        - N/A
- Get all alarm data
    - Input:
        - Alarm ID (long)
    - Output
        - Alarm list data object (AlarmList)

### Connections
- AlarmList is instantiated by AlarmStore in the get all alarm data method
- Add, update, and delete methods of the AlarmStore are called in the store, update, and delete methods of the AlarmList

## Alarm

### Responsibilities
Alarm creation and editing.

### Data Encapsulated
- ID (in the database) - long
- Name of the alarm - string
- Location of the alarm (name, address, or coordinates depending on user input) - string
- Latitude - double
- Longitude - double
- Distance from the alarm location - double
- Alert - PendingIntent, function that makes the phone vibrate or play the ringer
- Whether the alarm is active or not - boolean

### Functionality
- Create an alarm
    - Inputs:
        - Name of the alarm - string
        - Location of the alarm (name, address, or coordinates depending on what is entered - the method calls the Google Maps API to determine latitude & longitude) - string
        - Distance from the alarm location - double
        - Alert - enum (“buzz” or “ring” or “both”)
    - Output:
        - The created alarm object
- Update an Alarm data object if the user makes edits to an existing alarm
    - Input:
        - The field to be changed (name - string, location - string (this would trigger the Google Maps API and also update the latitude and longitude), distance (double), alert (enum)
        - The value to change the field to
    - Output:
        - N/A
- Enable alarm
    - Input & Output:
        - N/A
- Disable alarm
    - Input & Output:
        - N/A

### Connections
- Alarms are added and updated in the AlarmList
- Enable and disable call the LocationManager to add and remove proximity alerts and call the phone OS to send/remove a notification
- Create and update (if updating the location) methods call GooglePlaces to get the latitude and longitude of the location
- Create and update call GoogleMaps to show the alarm on a map

## AlarmList

### Responsibilities
In-memory storage of user-created alarms and display of those alarms

### Data Encapsulated
- Alarms - map of alarm IDs (long) to alarms
- AlarmStore - to modify the database

### Functionality
- Render and display all alarms
    - Input & Output:
        - N/A
- Update an alarm
    - Input:
        - The updated alarm object
    - Output:
        - N/A
- Add an alarm
    - Input:
        - The alarm object
    - Output:
        - N/A
- Delete an alarm
    - Input:
        - The alarm ID (long)
    - Output:
        - N/A

### Connections
- Alarms are stored, added, updated, and deleted in the AlarmList
- AlarmList is created by the AlarmStore
- Add, update, and delete AlarmList methods call the same methods of the AlarmStore to propagate changes from in-memory to the database

## GoogleMaps

### Responsibilities
Handles creating a map graphic for the alarm showing the location and the radius around that location.

### Data Encapsulated
- The Google Maps android SDK

### Functionality
- Return alarm location graphic
    - Inputs:
        - Latitude - double
        - Longitude - double
        - Radius, in meters - float
    - Output:
        - A Google Maps map object with the location (latitude and longitude) as a pin and the radius as a red circle surrounding the pin

### Connections
- Called in the Alarm create and update methods to show a visual representation of the alarm on the map

## GooglePlaces

### Responsibilities
Handles searching for places based on user-input parameters and displays possible matching places in a list for the user to select. Also controls selecting current or nearby locations for alarm creation.

### Data Encapsulated
- The Google Places API

### Functionality
- Query Places API for possible locations
    - Input:
        - Query, representing a name (or even "current location"), address, or coordinates for a location - string
    - Output:
        - Double array of size 2 - the first element is latitude and the second is longitude

### Connections
- Called in the Alarm create and update (if updating location) to find latitude and longitude
