# MovieHub

This project consists of a mobile application where I learned how to use and interpret the MovieDB API to retrieve information and display it in the form of listings, either individually or in groups of three items. In addition, I implemented a search engine that allows filtering movies according to specific criteria. I also added a configuration page where several additional changes can be made, such as changing the language and selecting categories such as popular movies, upcoming releases and top rated movies.

> [!IMPORTANT]
> **Before executing the code:**
> Get a MovieDB API Key: You must `register a MovieDB account and get an API Key`. This key is needed to access the movies and series database.
> 1. Add the API Key to the getBearerToken() function: In the API.java file,
> 2. Go to line 34 and replace "YOUR_API_KEY" with the API Key you obtained from MovieDB.
> 
> This will allow the application to access the database and display results correctly.

### Featured Features
Animations
Input and output animations have been created to provide smooth transitions between application screens. These animations add a pleasing visual effect when moving between fragments.

### Icons and Layouts
Several icons were imported for use in the application interface. In addition, all necessary layouts for the application were designed and developed, including the main layout, movie detail, individual movie list items and the settings menu.

### Menu and Navigation
A menu with options for accessing application settings and reloading data was implemented. The application navigation follows a clear and organized layout, facilitating the user experience when moving between different parts of the application.

### Application Logic
A package structure has been created to handle the connection and queries to the MovieDB API, as well as the handling of movie information. This includes classes to perform API requests, map the received data, and manage the user interface logic.

### Technologies Used
Retrofit: This was used to simplify the management of API calls and data processing.
Glide: Used for image loading, providing a smooth and efficient visual experience.
SharedPreferences: Used to store and manage application configuration preferences.
SafeArgs Plugin: It was installed to ensure security in the communication between fragments.
