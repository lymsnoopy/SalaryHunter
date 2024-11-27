Use this command to compile the project's source code:
$ javac -cp .:mysql-connector-java-9.1.0.jar -d out src/SalaryHunterApp.java src/model.java src/controller.java src/DatabaseLoginFrame.java

Use this command to run the compiled program:
$ java -cp .:mysql-connector-java-9.1.0.jar:out SalaryHunterApp
This should start a  controlled browser. 