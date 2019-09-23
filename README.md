# Bowling application
Processes a text file with plays and prints a board with chances and scores.

## Run the application

First let's compile everything:

mvn compile
mvn test compile
mvn test
mvn clean verify

Then, create the executable jar:

mvn clean package

Finally, run the application:

java -jar /path/to/bowling.jar /path/to/file.txt

For example, if I have the project in path: /workspace/bowling, then to run the application I can do:
/workspace/bowling$ java -jar ./target/bowling.jar /workspace/bowling/src/test/resources/hits.txt

* For normal input use hits.txt file.
* For second normal input use hits_2.txt file.
* For all strikes input use hits_perfect.txt file.
* For all faults input use hits_all_F.txt file.
* For all zeros input use hits_all_zeros.txt file.
* For input with wrong numbers use hits_wrong_chances.txt file. 
* For input with multiple players use hits_multiple_players.txt file. 

## Run Unit Test
mvn test compile

mvn test

## Run Integration Test
mvn clean verify
