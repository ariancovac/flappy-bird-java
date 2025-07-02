FLAPPY BIRDS

Para jugar presione "espacio".

Compilar:

mkdir -p bin

javac -d bin -cp src App.java src/**/**/*.java

java -cp bin App

--------------------------

javac -d bin -sourcepath src App.java

jar cfm flappybird.jar Manifest.txt -C bin/ .

java -jar flappybird.jar

---------------------------
mkdir -p bin
javac -d bin -sourcepath src App.java
xcopy /E /I src\res bin\res
jar cfm flappybird.jar Manifest.txt -C bin .
java -jar flappybird.jar
