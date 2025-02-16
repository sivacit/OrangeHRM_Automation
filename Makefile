# Variables
JAVA_HOME_CMD := $(shell /usr/libexec/java_home -v 17)
MVN := mvn

# Set JAVA_HOME
export JAVA_HOME := $(JAVA_HOME_CMD)

# Default target
all: clean install generate test build_and_test

# Remove generated test cases directory
clean:
	@echo "Removing temp directory..."
	@rm -rf src/temp

# Compile and run the Java program
generate: clean
	@echo "Using JAVA_HOME: $(JAVA_HOME)"
	@$(MVN) compile exec:java -Dexec.mainClass="ai.TestCaseGenerator"

# Build and install the project
install: clean
	@$(MVN) clean install

# Run tests
test: clean
	@$(MVN) test

# Clean, build, and run tests
build_and_test: clean
	@$(MVN) clean install test

.PHONY: all clean install generate test build_and_test       