Distributed Room Rental Platform – Version 1.0
Overview

Distributed Room Rental Platform is a cutting-edge solution for managing room rentals in a distributed environment. Built in Java for the backend and paired with an Android client, this project leverages multi-threading, TCP socket communication, and in-memory data storage to deliver a responsive and scalable system. Designed with modern architectural principles in mind, it offers a robust platform for both property managers and renters.
Key Features

• Distributed Architecture:

    Implements a custom Master-Worker model.
    A multi-threaded Master node handles client connections and distributes tasks to dynamically allocated Worker nodes using a hash-based strategy.

• Room Management:

    Add and manage room listings via a console application using JSON descriptions.
    (Note: This version does not support image uploads.)

• Reservation System:

    Renters can filter available rooms by area, date, capacity, price, and rating.
    Ensures real-time booking while preventing double reservations with synchronized access.

• Scalable & Fault-Tolerant:

    Supports concurrent multi-user operations.
    Optional active replication ensures data resilience in case of node failures.

• Modern Android Client:

    Provides an intuitive interface for searching, booking, and reviewing room rentals.
    Uses asynchronous TCP communication for a smooth user experience.

How to Use

    Backend Setup:
        Configure the number of Worker nodes via command line arguments or the provided configuration file.
        Run the Master node to start handling TCP connections and distribute room data.

    Room Listing Management:
        Use the console application to add room listings via JSON.
        Schedule available dates and manage reservations seamlessly through the backend.

    Android Client:
        Open the "android-client" project in Android Studio.
        Update the Master server IP and port settings as needed.
        Build and run the app to explore available rooms and make reservations in real-time.

Technologies Used

• Java – Core language for backend development. • TCP Socket Programming – Enables multi-threaded server-client communication. • Android – Platform for building the client application. • JSON Serialization – Manages room data and state persistence.
Project Structure

distributed-room-rental-platform/ ├── backend/ │ ├── Master.java // Handles TCP connections and client requests. │ ├── Worker.java // Manages room data storage and processing. │ └── utils/ // Helper classes and utility functions. ├── android-client/ │ ├── app/ // Android application source code. │ └── resources/ // Configuration files and assets. └── README.txt
Contributors

• Your Name (https://github.com/yourusername) • Collaborator Name (https://github.com/collaborator)
