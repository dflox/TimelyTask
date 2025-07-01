![Static Badge](https://img.shields.io/badge/Scala-sbt-red?style=for-the-badge&logo=Scala&logoColor=%23dc322f&color=%23dc322f)
[![Coverage Status](https://coveralls.io/repos/github/dflox/TimelyTask/badge.svg)](https://coveralls.io/github/dflox/TimelyTask)
![Tests](https://github.com/dflox/TimelyTask/actions/workflows/ci.yml/badge.svg)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/dflox/TimelyTask)


# TimelyTask

TimelyTask is a Scala-based application designed to efficiently manage your calendar and tasks. It's built to be as agile as possible, offering flexible options for organizing and planning your activities. In the future, TimelyTask will intelligently manage your tasks to optimize your schedule and help you become more productive.

This is an educational project for the Software Engineering module at HTWG Konstanz written in Scala.

---

## Table of Contents

- [Keyboard Shortcuts and Functions](#Keyboard-Shortcuts-and-Functions)
- [Instalation](#Instalation)
- [License](#License)
- [Acknowledgements](#Acknowledgements)
- [Contact](#Contact)

---
## Keyboard Shortcuts and Functions

Use the following key combinations in the Terminal UI:

### Calendar Navigation
| Shortcut          | Function                 |
|-------------------|--------------------------|
| **Shift + →**     | Move to next day         |
| **Shift + ←**     | Move to previous day     |
| **Ctrl + →**      | Jump to next week        |
| **Ctrl + ←**      | Jump to previous week    |
| **T**             | Go to today              |
| **W**             | Show full week           |
| **\+**            | Show more days           |
| **\-**            | Show fewer days          |

### General Actions
| Shortcut          | Function                        |
|-------------------|---------------------------------|
| **Z**             | Undo                            |
| **Y**             | Redo                            |
| **R**             | Create random test task         |
| **Ctrl + G**      | Open new GUI window             |
| **Ctrl + I**      | Start new instance              |
| **Ctrl + X**      | Close current instance          |

### Import / Export
| Shortcut          | Function                   |
|-------------------|----------------------------|
| **1**             | Export to JSON             |
| **2**             | Export to YAML             |
| **3**             | Export to XML              |
| **Shift + 1**     | Import from JSON           |
| **Shift + 2**     | Import from YAML           |
| **Shift + 3**     | Import from XML            |

---

## Installation
### Prerequisites

- [Scala](https://www.scala-lang.org/download/)
- [sbt (Scala Build Tool)](https://www.scala-sbt.org/download.html)
- [jdk 24](https://jdk.java.net/24/)

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
---

## License

This project is licensed under the MIT License. See the [LICENSE](https://github.com/UnKompetent/TimelyTask/blob/main/LICENSE) file for details.

---

## Acknowledgements

- [nscala-time](https://github.com/nscala-time/nscala-time): A Scala wrapper for Joda Time.
- [ScalaTest](https://www.scalatest.org/): A testing tool for Scala and Java.

---

## Contact

For any questions or feedback, please open an issue on the [GitHub repository](https://github.com/dflox/TimelyTask/issues).
