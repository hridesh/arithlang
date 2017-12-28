*Lang: A family of pedagogical interpreters 
========

This repository contains code and associated documents for each chapter in the 
textbook "An Experiential Introduction to Principles of Programming Languages" by Hridesh 
Rajan. Please report errors and difficulties to the author at hridesh@iastate.edu

This framework requires that you have latest versions of Java as well as Ant
installed on your computer. 

# Organization

This code is organized as follows:

- build.xml: This is the ant build file that contains the target parser for generating 
  parsers for all programming languages. You should run it as a ant script to remove 
  "*Parser not found*" errors from your code.
  
- src: The source directory that contains source code for the language e.g. Arithlang.

- build: The build directory that contains generated classfiles. Note that this 
  directory must be called build in order for the interpreter to run program 
  files correctly. 
  
- lib: libraries e.g. antlr jar file. These files must be on the build path of 
  the project.  
  
# Run the Interpreters 

You can run the interpreters as follows.
 - Build the parsers by running the build.xml. You can do that from command-line
  by writing 
     
     ant build.xml or simply ant
     
  From within the Eclipse, you can build the parsers by right clicking on the 
  build.xml file and selecting "Run As" followed by "Ant Build".
  
 - Refresh your project.
 
 - Select "Interpreter.java", right click, and select "Run As" -> "Java Application".
 
  
  

