export JAVA_HOME=$(/usr/libexec/java_home -v 17)
mvn compile exec:java -Dexec.mainClass="ai.TestCaseGenerator"
# sleep(10)
# mvn clean install test
