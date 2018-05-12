# SeattleMissionApp

To search start typing in the text field on first screen. Search results will start appearing after you have entered three or
more characters.

Pressing the map floating button will only open up the map if there are any results to be displayed on the map.

Design and Architecture
-----------------------

UI - Its pretty standard design. We have activities and there corresponding models. Models contain live data which is observed by the activities.

API - Retrofit is used to talk to FourSquare API


Packages
--------

1) ui - Contains Activities and Adapters. Main activity uses a Recycler adapter (Venues Adapter) to display the list.

2) viewmodel - It contains the viewmodels that are observed by the activities.

3) model - Model contains one Models class which contains all immutable classes used to represent the data
 model used in  the app. Models class is using Lombok plugin.

4) api -  It contains an interface used by retrofit and one concrete class implementing the interface which is used by the app to utilize the service.


