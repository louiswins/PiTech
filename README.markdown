PiTech Treadmill Simulator Software
===================================

PiTech Software Treadmill is a software that mimics the operations of a
treadmill. The user can walk, jog, and run by changing the speed and inclination
and may stop or pause at any time. The user has the ability to manually adjust
the settings to target specific goals, such as a specific amount of calories,
time, or a certain distance. The software monitors and calculates distance,
calories, and time throughout the workout. The user can have a better estimate
of calories burned by inputting age and weight at any time during the session.
The user has the choice to select a pre-designed program to work out on a
specific fitness goal such as weight loss, endurance, or cardiovascular fitness.
The software saves data and creates a history file, which includes the user's
time, distance, and calories per workout. Treadmill messages are displayed in
the message display section, and the treadmill's user guide can be viewed at any
time.


Installation
------------

If you use the [Eclipse][1] IDE, there is provided a `.project` file which you
can add, and then simply click the Run button.

Compiling the program into a jar-file on the command line is easy:

    javac *.java
    jar cvfe PiTech.jar DisplayInterface *.class rsc/*

This creates a jar-file called `PiTech.jar` which can be run by executing
`java -jar PiTech.jar`, or by double-clicking on the icon. The included shell
script `build.sh` will automate this in a POSIX environment.

A third alternative, if you have [Apache Ant][2], you can compile and create the
jar-file by simply executing `ant`.


[1]: http://www.eclipse.org/
[2]: http://ant.apache.org/
