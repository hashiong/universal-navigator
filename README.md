# Universal Studios Route Optimization Project

This project helps users find an optimal path through Universal Studios Hollywood by considering walking distances and wait times.

- **Frontend**: Built with ReactJS and Tailwind CSS
- **Backend**: Java Spring Boot (using Gradle) with MySQL hosted on AWS Aurora

### Getting Started

#### Prerequisites
- Ensure you have **Node.js** and **npm** installed for the frontend.
- Ensure **Java JDK** and **Gradle** are installed, or use the Gradle wrapper (`./gradlew`).
- Confirm access to an **AWS Aurora MySQL** database, or set up a local MySQL instance for testing.

### Instructions for Running

1. **Clone the Project**
   ```bash
   git clone <repository-url>
   cd <project-directory>
   ```

2. **Run the Frontend**
   - Navigate to the frontend directory:
     ```bash
     cd frontend
     ```
   - Install dependencies:
     ```bash
     npm install
     ```
   - Start the frontend:
     ```bash
     npm run dev
     ```
   - The frontend will run on `http://localhost:3000` by default.

3. **Run the Backend**
   - Navigate to the backend directory:
     ```bash
     cd backend
     ```
   - Run the backend using Gradle:
     ```bash
     ./gradlew bootRun  # For Windows, use `gradlew.bat bootRun`
     ```
   - The backend will run on `http://localhost:8080` by default.
