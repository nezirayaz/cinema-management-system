# Cinema Management System

**Cinema Management System** is a comprehensive and professional Java-based console application designed to manage the core operations of a cinema. It provides a robust and scalable architecture for handling customers, movies, and theaters, with data persisted in JSON format.

## Key Features

- **Customer Management**: Add, view, search, update, and delete customer records. Includes a loyalty points system.
- **Movie Management**: Manage the movie catalog, including adding new movies, updating details, and managing ratings.
- **Theater Management**: Handle cinema halls, including capacity, screen type, and assigning movies to theaters.
- **Persistent Storage**: All data is saved in a clean, human-readable JSON format, ensuring data persistence between application runs.
- **Robust Error Handling**: Implements custom exceptions to handle specific error cases like `EntityNotFoundException` and `ValidationException`.
- **Data Validation**: Ensures data integrity by validating all inputs before they are saved to the system.
- **Comprehensive Unit Tests**: Includes a suite of JUnit 5 tests to ensure the reliability and correctness of the business logic.
- **Professional Architecture**: Built using a layered architecture (UI, Service, Repository) for maintainability and scalability.
- **Interactive Console UI**: A user-friendly and well-organized console interface for easy interaction with the system.

## Project Structure

The project follows a standard Maven project structure with a clear and organized package layout that separates concerns into different layers.

```
cinema-management-system/
├── pom.xml
├── docs/
│   └── (additional documentation)
└── src/
    ├── main/
    │   ├── java/com/cinema/
    │   │   ├── CinemaApplication.java  // Main application entry point
    │   │   ├── exception/              // Custom exception classes
    │   │   │   ├── CinemaException.java
    │   │   │   ├── EntityNotFoundException.java
    │   │   │   └── ValidationException.java
    │   │   ├── models/                 // Data model classes (POJOs)
    │   │   │   ├── BaseEntity.java
    │   │   │   ├── Customer.java
    │   │   │   ├── Movie.java
    │   │   │   └── Theater.java
    │   │   ├── repository/             // Data access layer (CRUD operations)
    │   │   │   ├── Repository.java
    │   │   │   ├── JsonRepository.java
    │   │   │   ├── CustomerRepository.java
    │   │   │   ├── MovieRepository.java
    │   │   │   └── TheaterRepository.java
    │   │   ├── service/                // Business logic layer
    │   │   │   ├── CustomerService.java
    │   │   │   ├── MovieService.java
    │   │   │   └── TheaterService.java
    │   │   ├── ui/                     // User interface components
    │   │   │   └── ConsoleUI.java
    │   │   └── utils/                  // Utility classes
    │   │       └── JsonUtil.java
    │   └── resources/                // JSON data files for storage
    │       ├── customers.json
    │       ├── movies.json
    │       └── theaters.json
    └── test/
        └── java/com/cinema/
            ├── models/                 // Unit tests for models
            │   ├── CustomerTest.java
            │   └── MovieTest.java
            ├── repository/             // (Tests for repository layer)
            └── service/                // Unit tests for service layer
                ├── CustomerServiceTest.java
                └── MovieServiceTest.java
```

## Requirements

- **Java Development Kit (JDK)**: Version 11 or higher
- **Apache Maven**: Version 3.6 or higher

## Installation & Setup

Follow these steps to get the project up and running on your local machine.

1.  **Clone the Repository**:

    ```bash
    git clone https://github.com/nezirayaz/cinema-management-system.git
    cd cinema-management-system
    ```

2.  **Build with Maven**:

    Navigate to the project's root directory and use Maven to compile the project and install dependencies. This command also runs the unit tests.

    ```bash
    mvn clean install
    ```

## How to Run

Once the project is built, you can run the application using Maven.

```bash
# Run the main application class
mvn exec:java
```

This will start the interactive console menu, where you can begin managing the cinema system.

Alternatively, you can run the packaged JAR file directly:

```bash
# Build the project first if you haven't already
mvn clean install

# Run the executable JAR
java -jar target/cinema-management-system-2.0.0.jar
```

## Running Tests

To run the comprehensive suite of unit tests, use the following Maven command:

```bash
# This will run all tests under src/test/java
mvn test
```

## How It Works

The application operates through a series of interactive console menus:

1.  **Main Menu**: Choose between managing Customers, Movies, or Theaters.
2.  **Sub-Menus**: Each management section has its own menu for specific actions (Add, View, Search, Update, Delete).
3.  **Data Persistence**: All data is automatically saved to `.json` files in the `src/main/resources` directory. The system loads this data on startup, ensuring that your information is always preserved.

### Example Workflow: Adding a New Movie

1.  Run the application.
2.  Select `2` for Movie Management.
3.  Select `1` to Add a New Movie.
4.  Enter the movie's details as prompted (title, genre, duration, etc.).
5.  The movie is now saved to `movies.json` and can be viewed or assigned to a theater.

## Contributing

Contributions are welcome! This project was rebuilt to serve as a strong foundation for further development. If you have ideas for new features or improvements, please feel free to contribute.

1.  **Fork the repository** on GitHub.
2.  **Create a new branch** for your feature (`git checkout -b feature/my-new-feature`).
3.  **Commit your changes** (`git commit -am 'Add some feature'`).
4.  **Push to the branch** (`git push origin feature/my-new-feature`).
5.  **Create a new Pull Request**.

## License

This project is open-source and available under the [MIT License](https://opensource.org/licenses/MIT).
