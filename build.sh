#!/bin/bash
# compiles this java program and runs the jar file
javac -Xlint:-serial *.java
jar cvfe PiTech.jar DisplayInterface *.class rsc/*
java -jar PiTech.jar
