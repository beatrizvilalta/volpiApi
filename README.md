
# volpi API

This project is a **Spring Boot API** designed for a teacher's social media platform where educators can share and create posts with teaching materials. It provides endpoints for managing posts, users, files and interactions.

## Features

- Create, update, and manage posts with educational materials.
- Upload and manage files.
- Engage with posts through supports and saved items.
- Authentication and security support.

---

## Prerequisites

To run this project, you need:

1. **Java 21** or higher.
2. **Maven** for dependency management.
3. **PostgreSQL** or your preferred database (ensure the connection matches `DATABASE_URL`).
4. AWS S3 credentials (for file storage).
5. A properly configured environment with the following variables:

---

## Environment Variables

The following environment variables must be set before running the application:

| Name                  | Description                           |
|-----------------------|---------------------------------------|
| `ACCESS_KEY`          | AWS Access Key for S3 integration.   |
| `S3_SECRET_KEY`       | AWS Secret Key for S3 integration.   |
| `DATABASE_URL`        | Database connection URL.             |
| `DATABASE_USERNAME`   | Database username.                   |
| `DATABASE_PASSWORD`   | Database password.                   |
| `SECRET_KEY`          | Application secret key for security. |

> **Note**: Replace these variables in your environment or configuration file before running the app.

---

## How to Run

Follow these steps to get the application running locally:

### 1. Clone the Repository
```bash
git clone git@github.com:beatrizvilalta/volpiApi.git
cd git@github.com:beatrizvilalta/volpiApi.git
```

### 2. Set Environment Variables
Set the above-mentioned environment variables in your system or use a `.env` file.

### 3. Install Dependencies
Ensure you have **Maven** installed, then run:
```bash
mvn clean install
```

### 4. Run the Application
Use the following command:
```bash
mvn spring-boot:run
```

---

## API Endpoints

Here's a basic overview of the key endpoints:

| HTTP Method | Endpoint         | Description                     |
|-------------|------------------|---------------------------------|
| `POST`      | `/posts`         | Create a new post.             |
| `GET`       | `/posts`         | Retrieve all posts.            |
| `POST`      | `/auth/register` | Register a new user.           |
| `POST`      | `/auth/login`    | Authenticate a user.           |
| `POST`      | `/files`         | Upload a file.                 |

For a full list of endpoints, see the [API Documentation](https://volpi-api-ec9e2c714aa7.herokuapp.com/swagger-ui/index.html).

---

## Contributing

If you'd like to contribute, please fork the repository and use a feature branch. Pull requests are warmly welcome.
