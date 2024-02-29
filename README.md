# Location-Tracker Application

Welcome to the Spring Location-Tracker Application! This project is designed to display location information of a bus by using the android device of the driver.

## Overview

The Spring Location Application is built using the Spring MVC framework, providing a scalable and flexible architecture for developing robust web applications. The primary focus of this project is to showcase the retrieval and display of location data.

## Features

### 1. Location Service

The application incorporates a location service that retrieves data about specific geographic locations. This data include coordinates related to the locations.

### 2. SSE

The project leverages SSE (Server Sent Events) That will update all the clients whenever there is an update from the driver's device.

### 3. Responsive User Interface

The user interface is designed using HTML, CSS and JavaScript, the application gets location updates from the server and instantly updates the UI.

### 4. Javascripts's leaflet framework Integration

As a part of the location demonstration, the application seamlessly integrates with Javascripts's leaflet framework to visually represent the retrieved location data. Users can interact with the map to explore various geographic points.

## Usage

To use the Spring Location Application:

1. **Navigate to the Home Page:**
   - Open your web browser and go to the application's home page.

2. **Specify bus id in the url**
   - Use the provided interface to explore different locations. The application will display relevant information about each location.

3. **Interact with the Map:**
   - On specific pages, you'll find an interactive map powered by Leaflet. Use this map to visualize the geographic details of the displayed locations.

## Technologies Used

- **Spring Framework:** A comprehensive framework for building enterprise Java applications.
- **Spring SSE:** A module within the Spring Framework for pushing data to the clients from the server.
- **Leaflet:** Integration with Leaflet to display location data on an interactive map.

## Getting Started

To set up the project locally, follow these steps:

1. Clone the repository: `git clone https://github.com/kailas098/location-tracker`
2. Navigate to the project directory: `cd Location-Tracker\src\main\java\com\kailasnath\locationtracker\LocationTrackerApplication`
3. Access the application in your browser: `http://localhost:8080/index.html`

---

Thank you for exploring the location-tracker project! If you have any questions or feedback, please don't hesitate to reach out.

Happy coding!
