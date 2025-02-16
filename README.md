Distributed Room Rental Platform

Welcome to the Distributed Room Rental Platform—an end-to-end solution designed to manage and scale room rental operations seamlessly. This project leverages distributed computing with a Java-based backend and an Android client, making it ideal for real-world property management and rental applications.
Project Highlights

Distributed Architecture:
        Master Node: A robust, multi-threaded TCP server implemented in Java that handles client connections and distributes tasks.
        Worker Nodes: Dynamically allocated nodes store room data in memory using a hash-based distribution strategy, ensuring efficient load balancing.
        MapReduce-Inspired Processing: Simplifies filtering and processing room data to quickly deliver search results.

Modern Android Client:
        User-friendly interface for searching, booking, and reviewing room rentals.
        Asynchronous TCP communication for a responsive experience, even under heavy load.

Scalable and Fault-Tolerant:
        Supports concurrent multi-user operations.
        Optional active replication for high availability, ensuring data resilience in case of node failures.

Key Features

Room Management:
        Add new room listings using JSON descriptions.
        Schedule available dates for rentals.
        Include key details such as room name, capacity, location, price, and ratings.
        Note: This version currently does not support adding images.

Reservation System:
        Renters can filter available rooms by area, dates, occupancy, price, and star rating.
        Real-time booking with synchronized access to prevent double bookings.
        Detailed reservation reports available for property managers.

Efficient Data Processing:
        Leverages Java threading and low-level TCP socket programming for performance.
        Utilizes a simplified MapReduce model to efficiently filter and manage data.

Getting Started
Prerequisites

Java 8 or Higher: Ensure you have the JDK installed.
Android Studio: Required for building and testing the Android client.
