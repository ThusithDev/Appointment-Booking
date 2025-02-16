======================= Appointment Booking Web Application =================================

Steps to set up and run the project locally.

Make sure you have installed following softwares with below versions.

* JDK 17 or higher
* Node js 18 or higher
* MySQL

Make sure you have installed following IDEs

* VS Code
* IntelliJ IDEA
* MySQL Workbench

Download the project and unzip.

Drag and drop booking folder to IntelliJ IDEA.

Open Appointment-Booking folder in VS code and select the folder "frontend" using cd frontend command in the terminal.

Setup applications.properties file in the IntelliJ IDEA with following steps.

Replace your mysql port number with {port} in mysql://localhost:{port}/bookingdb
Include you mysql username and password in the relevant lines
Crate a token which is greater than 32-bit using a token generator.
Paste the token in JWT_SECRET=
Fist run the project in IntelliJ IDEA.

Then run the project in VS code terminal using "npm run dev"

Check your mysql workbench to see whether it created the tables.

Book appointments and refresh the database for validate the process.

Thank You. 
Thusith Wickramasinghe
