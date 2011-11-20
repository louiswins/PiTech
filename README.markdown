πTech Treadmill Simulator Software
==================================

PiTech Software Treadmill is a software that mimics the operations of a
treadmill. The user can walk, jog, and run by changing the speed and inclination
and may stop or pause at any time. The user has the ability to manually adjust
the settings to target specific goals, such as a specific amount of calories,
time, or a certain distance. The software monitors and calculates distance,
calories, and time throughout the workout. The user can have a better estimate
of calories burned by inputting age and weight at any time during the session.
The user has the choice to select a pre-designed program to work out on a
specific fitness goal such as weight loss, endurance, or cardiovascular fitness.
The software saves data and creates a history file, which includes the user’s
time, distance, and calories per workout. Treadmill messages are displayed in
the message display section, and the treadmill’s user guide can be viewed at any
time.

Compiling the program into a jar-file is easy:

    javac *.java
    jar cvfe PiTech.jar DisplayInterface *.class

This creates a jar-file called `PiTech.jar` which can be run by executing `java
-jar PiTech.jar`, or by double-clicking on the icon.

If you have [Apache Ant](http://ant.apache.org/), you can compile and create the
jar-file by simply executing `ant`.
