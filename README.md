# FetchCodingExercise
Coding exercise for Fetch job application

Native Android app written in Kotlin that retrieves data from https://fetch-hiring.s3.amazonaws.com/hiring.json.

Data is retrieved using Retrofit and is stored in a StateFlow Observable via a ViewModel 

The data is displayed in a Jetpack Composable LazyColumn based on the following requirements:
- Grouped by "listId" (displayed using a stickyHeader)
- Sorted first by "listId" then by "name"
- Items where "name" is blank or null are filtered out

# Installation Instructions
1. To run the app you must have these items installed in the SDK Manager:
   1. Android SDK Platform 35
   2. Android SDK Build-Tools 35.0.1
2. Clone the repo and import into Android Studio