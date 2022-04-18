# Hi All,

This was a project completed as my final homework assignment for an Algorithms and Data Structures course I took.

It's a elementary execution of Dijikstra's algorithm to find the shortest path between Tron (T) and all of the bugs (a-z)
on the field. The program will first ask if you want to move Tron up/left/right/down and move Tron if the choice is valid (there's not
a stationary object in the way). After that it will display the shortest path for any and all bugs on the field to reach Tron. At present, there 
is only one move that takes place with the possibility for extra movement should I tweak a few lines, but due to time constraints and interest in 
other projects, I don't think I'll be revisiting this project.

The program requires a text file to start. The file must be in the format:

Rows*int* Cols*int*

\#\#\#\#\#\#\#\#\#\#\#\#\#

\# d     c\#  \#

\#      \#    \#

\#      T    \#

\#           \#

\# I         \#

\#\#\#\#\#\#\#\#\#\#\#\#\#

Where:
1. \# = border or stationary/non-moving object
2. T = Tron
3. I = Tower/End Goal
4. a-z = Bugs that attack Tron

Sample files have been provided.

**Only Tron Moves** Please keep this in mind. There is no game over functionality; The program **only displays the shortest distance from each bug 
to Tron based on the initial user input.*

Thanks,

-Kayy
