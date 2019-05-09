# Team Process

> How will you communicate with teammates you depend on?

We will be using Facebook Messenger for text messaging and Discord for audio and video chatting. We chose Facebook Messenger because everyone checks it multiple times per day already, and we chose Discord because it is free and has better audio and video quality than other audio/video chat methods we have tried in the past (e.g. Skype and Facebook Messenger).

> What coordination and planning practices will you follow?

We will be having check-ins weekly on Saturdays at 2 PM over Discord because everybody is available at that time. We will conduct these check-ins Agile-style with each person sharing what they worked on the past week, what they will work on in the coming week, and any blockers. If any blockers exist, we will discuss the blocker as a team and determine the best course of action to remove the blocker. We are choosing Agile-style since it gives everyone insight into what the other people are working on, and provides the possibility to work with someone else if having issues. If additional communication is needed, we will set up further meetings over Facebook Messenger or Discord. 

> Who will own each of the components in your architecture?

### AlarmStore and AlarmList
James will be taking ownership of AlarmStore and AlarmList. He has had the most development experience, so we are giving him two components to take ownership of. Furthermore, the two components are related (AlarmList is created by AlarmStore), so it will make it easier to coordinate processes. Additionally, James has experience with databases from INFO 442 and his internship last summer, meaning he will be able to complete the AlarmStore quickly so that the other developers are not blocked since the application depends on being able to persist data. 

### Alarm
Emily will be taking ownership of the Alarm component because it is connected with all of the other components and she is willing to coordinate with every member of the team. Joy will implement the enable and disable functions for Alarm since those two functions involve sending/removing notifications and she has experience with Android notifications from TAing the Android development class.

### LocationManager
This component is prebuilt in Android, but we added it to this list because Joy will be using it to add/remove proximity alerts in the enable/disable functions of Alarm.

### GoogleMaps and GooglePlaces
Maddie is taking ownership of the GoogleMaps and GooglePlaces components because she has had experience with the Google Maps API from an Android development class and is interested in taking her skills with these tools further for our location alarm.

> By what date will you have a release candidate?

We will have a release candidate by the end of June 7th (three days before the due date). We want to finish our project a little early so that we can do some preliminary testing for bugs and make any last-minute changes. 

> What practices will you use to know if you're making progress toward that release candidate?

Every member of the group will be checking commit history daily on GitHub to make sure that people are doing their work. To facilitate this, we have implemented automatic alerts in our Discord channel so everyone gets a notification when someone pushes to GitHub. We have deadlines set up for each component so we can ensure we are ready for release on June 7th:
- AlarmStore, AlarmList, GoogleMaps, GooglePlaces due 5/30
- Alarm due 6/4

We chose these deadlines so we can really focus on setting everything up for the Alarm component the first two sprint, then focus on finishing the Alarm component halfway through the last sprint so we can work on the visuals of the application in the three days before our release candidate is due.

> What practices will you follow to improve your process if it's not working?

If our process is not working, we will convene as a group over Discord or in person to talk about how we can improve our process and get our work done in time to meet the deadline. The deliverable from this meeting will be an updated process document so that we have our decisions written down. This will allow us to refer back to the what and the why of our decisions. If one or more group members is not available to chat, we will reschedule the meeting unless he or she gives permission to make decisions and report back. This is so that everyone will have the ability to give input on any process improvements.
