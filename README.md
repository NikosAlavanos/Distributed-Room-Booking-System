# **Distributed Room Rental Platform** 🏠💻
## **Overview**

Distributed Room Rental Platform is a cutting-edge solution for managing room rentals in a distributed environment. Built in Java for the backend and paired with an Android client, this project leverages multi-threading, TCP socket communication, and in-memory data storage to deliver a responsive and scalable system. Designed with modern architectural principles in mind, it offers a robust platform for both property managers and renters.

## **Key Features**

- **Distributed Architecture:**  
  Implements a custom Master-Worker model where a multi-threaded Master node handles TCP connections and distributes tasks to dynamically allocated Worker nodes using a hash-based strategy.

- **Room Management:**  
  Manage room listings with ease via a console application that uses JSON descriptions. *(Note: This version does not support image uploads.)*

- **Reservation System:**  
  Allows renters to filter available rooms by area, dates, occupancy, price, and rating. The system ensures real-time booking with synchronized access to prevent double reservations.

- **Scalable & Fault-Tolerant:**  
  Designed to handle concurrent multi-user operations with optional active replication to ensure high availability and data resilience.

- **Modern Android Client:**  
  Provides an intuitive interface for searching, booking, and reviewing room rentals. Utilizes asynchronous TCP communication for a smooth user experience.

## **How to Use**

1. **Backend Setup:**  
   - Configure the number of Worker nodes using command line arguments or a configuration file.  
   - Launch the Master node to handle TCP connections and manage room data.

2. **Room Listing Management:**  
   - Use the console application to add new room listings via JSON.  
   - Schedule available dates and manage reservations seamlessly through the backend.

3. **Android Client:**  
   - Open the `android-client` project in Android Studio.  
   - Update the Master server's IP address and port settings as needed.  
   - Build and run the application to browse available rooms and make reservations in real time.

## **Technologies Used**

- **Java:** For backend development.  
- **TCP Socket Programming:** Facilitates multi-threaded server-client communication.  
- **Android:** For the client application.  
- **JSON Serialization:** Manages room data and state persistence.

## **Contributors**

* [Νίκος Αλαβάνος](https://github.com/NikosAlavanos)  
* [Κωσταντίνος Γιοβανόπουλος](https://github.com/Giovas2126)
* [Βασίλης Ρεπάνης](https://github.com/VasRep)
