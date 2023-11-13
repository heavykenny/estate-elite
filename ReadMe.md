### Estate Elite - Real Estate Mobile App

Estate Elite is a mobile application for real estate agents to manage their properties and tenant
inquires.
The application is built Kotlin and PHP Backend. The application is currently only available for
Android devices.

## Features

- [x] Login and Registration
- [x] Add, Edit, Delete Properties
- [x] Manage Inquiries
- [x] Manage Users, Landlords

## Requirements to run the application

- Android Studio
- PHP Server (XAMPP)

## Installation

### Change the API URL in the config file - src/main/res/values/config.xml

```xml

<string name="base_api_url">
    http://192.168.0.144/landlord/api
</string>
```

### PHP Backend Setup - XAMPP should be running

- Copy the backend folder to the htdocs folder in XAMPP
- Create a database named landlord
- Import the landlord.sql file to the database

### Screenshots
![alt text](screenshots/Screenshot_20231111-204938_Landlord.jpg "Screen 1")
![alt text](screenshots/Screenshot_20231111-205004_Landlord.jpg "Screen 2")
![alt text](screenshots/Screenshot_20231111-205012_Landlord.jpg "Screen 3")
![alt text](screenshots/Screenshot_20231111-205130_Landlord.jpg "Screen 4")
![alt text](screenshots/Screenshot_20231111-205136_Landlord.jpg "Screen 5")
![alt text](screenshots/Screenshot_20231111-205145_Landlord.jpg "Screen 6")