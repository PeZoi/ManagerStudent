# Student Project Manager

## Introduction
This is my first project using [SPRING BOOT](https://spring.io/projects/spring-boot) and [SPRING FRAMEWORK 6.0.xx](https://spring.io/projects/spring-framework). My project mainly focuses on CRUD operations. I hope everyone will accept it, and if you find any bugs or errors in the project, feel free to [contact me](https://www.facebook.com/dongph.0502).

## Features
1. Basic CRUD (Create, Read, Update, Delete) operations for entities.
2. Image upload using [Cloudinary](https://cloudinary.com/).
3. Student data upload via Excel file (Limited to role: Teacher).

#### Format Excel File
- **Sheet Name**: First, ensure that you name the sheet as `Students`.
   ![image](https://github.com/PeZoi/ManagerStudent/assets/87312316/9ca51c8c-8baa-45f6-9afd-b6887ee4ce03)

- **First Row**: In the first row, you can either write a title (or leave it blank), but please note that the first row will not be added to the database.

-  **Data Entry**: For all other rows, please input the data as shown in the image below:
   ![image](https://github.com/PeZoi/ManagerStudent/assets/87312316/5c80117d-f13f-439a-8d61-9730feca3452)

Ensure that you follow these instructions for proper Excel file formatting.

## Programming Languages

My project employs the following programming languages:

- HTML/CSS
- jQuery
- JavaScript
- Java
- Spring Boot
- Spring Framework (Spring Security, Spring Web, Spring JPA, ...)
- Templating Engine: [Thymeleaf](https://www.thymeleaf.org/)

## Installation

To clone this project:

- Navigate to the folder where you want to save the project.
- Open the command prompt or terminal and use Git Bash.

```sh
git clone https://github.com/PeZoi/ManagerStudent.git
```

## Database

I am using [MySQL](https://www.mysql.com/) as the database management system through [xampp](https://www.apachefriends.org/download.html) and managing data via [HeidiSQL](https://www.heidisql.com/). I chose these software tools due to their lightweight nature. Alternatively, you can use other software tools such as [MySQL Workbench](https://www.mysql.com/products/workbench/), [SQL Server](https://www.microsoft.com/en-us/sql-server/sql-server-downloads), and so on.

Download the database from: [here](https://drive.google.com/file/d/1yzMyVqaaZTt9WUHwpwmuxrhUlsCR0I4r/view?usp=drive_link)

If you are using HeidiSQL, follow these steps: File -> Run SQL File -> Choose the database file.
