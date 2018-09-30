===================================================
== Solution to the Maze problem - By Kelvin Chan ==
===================================================

A Python and Java program had been developed to find the solution to mazes. The
Python program does NOT interact with the Java program and vice versa.
Either program can be ran through command line, however please find the requirements
below for the programs to be ran smoothly.
 
To run the programs, you must run Main.py (for Python) or Main (for java).
The mazes must be in the text file format given below (same as the ones given in the samples)

To solve the mazes, the Main file is ran and a GUI will allow you to choose the text file.
The window or command line may require you to interact with it.

Example run from command line (Windows):
	- python3 Main.py
	- java java_solution/Main

The program will output the solution to the standard output (command line if ran from command line).
The option to output the solution to a text file of customisable name is also given.
If no solution is found, the user will be notified through the standard output.

===============================
Requirements to run the program
===============================

Python:
- Python 3.6.4
- Python Standard Libraries
- Files: Main.py, Player.py

Java:
- Java 1.8
- Java Standard Libraries
- Source Files: Main.java, Maze.java, Player.java
- Compiled Files: Main.class, Maze.class, Player.class 

=================================================
Maze file format - Taken from the original README
=================================================

The input is a maze description file in plain text.  
 1 - denotes walls
 0 - traversable passage way

INPUT:
<WIDTH> <HEIGHT><CR>
<START_X> <START_Y><CR>		(x,y) location of the start. (0,0) is upper left and (width-1,height-1) is lower right
<END_X> <END_Y><CR>		(x,y) location of the end
<HEIGHT> rows where each row has <WIDTH> {0,1} integers space delimited

OUTPUT:
 the maze with a path from start to end
 walls marked by '#', passages marked by ' ', path marked by 'X', start/end marked by 'S'/'E'

Example file:  
10 10
1 1
8 8
1 1 1 1 1 1 1 1 1 1
1 0 0 0 0 0 0 0 0 1
1 0 1 0 1 1 1 1 1 1
1 0 1 0 0 0 0 0 0 1
1 0 1 1 0 1 0 1 1 1
1 0 1 0 0 1 0 1 0 1
1 0 1 0 0 0 0 0 0 1
1 0 1 1 1 0 1 1 1 1
1 0 1 0 0 0 0 0 0 1
1 1 1 1 1 1 1 1 1 1

OUTPUT:
##########
#SXX     #
# #X######
# #XX    #
# ##X# ###
# # X# # #
# # XX   #
# ###X####
# #  XXXE#
##########

============================
========= CREDITS ==========
============================

The programs were made by Kelvin Chan
Contact details: kckchan96@gmail.com

