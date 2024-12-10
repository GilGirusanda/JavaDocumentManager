# Document Manager

This application is aimed to help the users create, search and find documents by id. (in-memory database)

## Prerequisites

Before you begin, ensure you have met the basic requirements:
- [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) (or any version compatible with your project)
- [Maven](https://maven.apache.org/download.cgi) for building and managing dependencies
- [Visual Studio Code](https://code.visualstudio.com/) with Java extensions (used: [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack))

#### **Batch scripts**

- `mvn_install.bat` - cleans previous build and makes new one
- `mvn_run.bat` - run the project one more time if no changes made
- `mvn_compile_run.bat` - run the project with regard to the changes made.

## Set up the project

### Clone the repo

```bash
git clone https://github.com/GilGirusanda/JavaDocumentManager.git
cd JavaDocumentManager
```

### Build with Maven

```bash
mvn clean install
```

### Run the project

To run the application, use Maven's `exec:java` plugin, which runs the main class specified in the `pom.xml`.

```bash
mvn exec:java
```