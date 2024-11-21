![Static Badge](https://img.shields.io/badge/Scala-sbt-red?style=for-the-badge&logo=Scala&logoColor=%23dc322f&color=%23dc322f)
[![Coverage Status](https://coveralls.io/repos/github/dflox/TimelyTask/badge.svg)](https://coveralls.io/github/dflox/TimelyTask)
# TimelyTask

TimelyTask is a Scala-based application designed to help users manage their tasks and schedules efficiently. The application provides a terminal-based user interface (TUI) for viewing and organizing tasks within a calendar format.

This is an educational project for the Software Engineering module at HTWG Konstanz written in Scala.

## Table of Contents

- [Features](#Features)
- [Instalation](#Instalation)
- [Projekt Structure](#Project)
- [Additional Information](#License)
## Features

- **Task Management**: Add, edit, and delete tasks. (not fully implemented yet)
- **Calendar View**: Display tasks in a calendar format.
- **Time Selection**: Customize the time frame for viewing tasks.
- **Terminal Interface**: Interact with the application through a terminal-based user interface.

## Installation

### Prerequisites

- [Scala](https://www.scala-lang.org/download/)
- [sbt (Scala Build Tool)](https://www.scala-sbt.org/download.html)

### Clone the Repository

```sh
git clone https://github.com/dflox/TimelyTask.git
cd TimelyTask
````
## Build and run the Application

```sh
sbt compile
sbt run
````

## Project Structure
- src/main/scala/me/timelytask/: Contains the main application code.
  - view/tui/: Terminal user interface components.
  - view/viewmodel/: ViewModel components for managing the application's state.
  - model/: Data models and business logic.
  - utility/: Utility functions and helpers.
- src/test/scala/me/timelytask/: Contains test cases for the application.

## License

This project is licensed under the MIT License. See the [LICENSE](https://github.com/UnKompetent/TimelyTask/blob/main/LICENSE) file for details.

## Acknowledgements

- [nscala-time](https://github.com/nscala-time/nscala-time): A Scala wrapper for Joda Time.
- [ScalaTest](https://www.scalatest.org/): A testing tool for Scala and Java.

## Contact

For any questions or feedback, please open an issue on the [GitHub repository](https://github.com/dflox/TimelyTask/issues).
