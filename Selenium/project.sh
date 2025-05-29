#!/bin/bash

# Define project name
PROJECT_NAME="my-automation-project"

# Create the main project directory
mkdir -p $PROJECT_NAME

# Create source directories
mkdir -p $PROJECT_NAME/src/main/java/utils
mkdir -p $PROJECT_NAME/src/main/java/pages
mkdir -p $PROJECT_NAME/src/main/java/base
mkdir -p $PROJECT_NAME/src/main/java/constants
mkdir -p $PROJECT_NAME/src/main/java/config
mkdir -p $PROJECT_NAME/src/main/resources

# Create test directories
mkdir -p $PROJECT_NAME/src/test/java/stepDefinitions
mkdir -p $PROJECT_NAME/src/test/java/testRunners
mkdir -p $PROJECT_NAME/src/test/java/hooks
mkdir -p $PROJECT_NAME/src/test/java/testCases
mkdir -p $PROJECT_NAME/src/test/resources/features

# Create essential files
touch $PROJECT_NAME/pom.xml
touch $PROJECT_NAME/README.md
touch $PROJECT_NAME/.gitignore
touch $PROJECT_NAME/src/test/resources/testng.xml

# Populate .gitignore with common exclusions
echo "target/
.idea/
*.log
*.class
*.iml
.DS_Store" > $PROJECT_NAME/.gitignore

# Success message
echo "✅ Maven automation project structure created successfully in '$PROJECT_NAME'!"
