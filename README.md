# About
Solving a tile puzzle problem the size of NxM with up to 2 empty spaces using various search algorithms:
* BFS
* DFID
* A*
* IDA*
* DFBnB

Minimizing the following cost: <br />
  Single move - 5 <br />
  Vertical double move - 6 <br />
  Horizontal double move - 7 <br />

Double moves are allowed only into 2 empty spaces.

# Setting  up:
## Input
Create a file "input.txt" in the directory <br />
With the following format: <br />
Line 1: BFS / DFID / A* / IDA* / DFBnB  - Choose algorithm <br />
Line 2: with time / no time - Out put running time <br />
Line 3: with open / no open - Displaying the open list during run time <br />
Line 4: NxM - Board size <br />
Next N lines: Starting state <br />
Next line: "Goal state:" <br />
Next N lines: Goal state <br />

## Input example
A* <br />
with time <br />
no open <br />
3x3 <br />
1,2,3 <br />
4,7,_ <br />
5,6,_ <br />
Goal state: <br />
1,2,3 <br />
4,5,6 <br />
7,_ ,_

# Output
The output will be written in a file named "output.txt" <br />
In the following format: <br />
Line 1: Steps of the solution <br />
Line 2: Number of nodes generated <br />
Line 3: Cost of the solution <br />
Line 4: (Optional) Running time of the program <br />

Turning "with open" will display the open list in the console. <br />

# Output example
6R-7D-6U-7R-5R-5U-7L-7L <br />
Num: 328 <br />
Cost: 40 <br />
0.048 seconds <br />