# Java FTP Client with AWS MySQL Database and Proftpd Server

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [File Sharing](#file-sharing)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This repository contains a Java-based FTP Client project that connects to an FTP server created using the Proftpd package. It also utilizes an AWS server for storing and managing data through a MySQL database. The FTP Client allows users to upload, download, and manage files on the FTP server, including the ability to share files between users through the MySQL database on AWS.

## Features

- FTP Client:
  - Upload files to the FTP server.
  - Download files from the FTP server.
  - List files and directories on the FTP server.
  - Delete files and directories on the FTP server.
- AWS MySQL Database:
  - Store user credentials and access permissions.
  - Manage metadata and additional information for files.
- Proftpd Server:
  - Create and configure an FTP server for file storage.
- File Sharing:
  - Share files between users securely using the MySQL database.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- AWS account with access to AWS RDS for MySQL.
- Proftpd package installed and configured on your server.
- Java Development Kit (JDK) installed.
- Required Java libraries and dependencies.

## Installation

1. Clone this repository to your local machine:

   ```shell
   git clone https://github.com/yourusername/java-ftp-client-aws-proftpd.git
   ```

2. Navigate to the project directory:

   ```shell
   cd java-ftp-client-aws-proftpd
   ```

3. Build and compile the Java project as needed:

   ```shell
   javac YourJavaFile.java
   ```

## Configuration

Before using the FTP Client, you need to configure the following:

1. AWS Configuration:
   - Set up an AWS RDS instance for MySQL and obtain the necessary connection details.
   - Configure AWS credentials on your local machine (AWS CLI or environment variables).

2. FTP Server Configuration:
   - Ensure that Proftpd is installed and configured with the appropriate settings on your server.
   - Update the FTP server details in the `config.ini` file.

3. Database Configuration:
   - Create a MySQL database on your AWS RDS instance.
   - Update the database connection details in the `config.ini` file.

4. FTP Client Configuration:
   - Configure the FTP Client with the server details and database credentials in the `config.ini` file.

## Usage

1. Run the Java FTP Client:

   ```shell
   java YourJavaFile
   ```

2. Use the FTP Client to perform various operations on the FTP server. You will be prompted for authentication and file operations.

## File Sharing

To share files between users:

1. Implement a user-friendly mechanism within your Java application that allows users to select files for sharing.

2. Store the shared file information, including sender, recipient, and file details, in the MySQL database.

3. Implement a feature within your Java application that allows users to view and access shared files based on their permissions.

## Contributing

Contributions are welcome! Please follow the [Contribution Guidelines](CONTRIBUTING.md) in this repository.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

