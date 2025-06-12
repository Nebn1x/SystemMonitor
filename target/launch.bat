@echo off
cd /d %~dp0
java -Djava.library.path="bin" --module-path "lib" --add-modules javafx.controls,javafx.fxml -jar cursova-1.0-SNAPSHOT-jar-with-dependencies.jar
pause