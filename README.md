# Comparing-BSTs
Comparing the costs of self-organizing BSTs using a static competitive ratio

To test the code, run the TestingTrees.java file. The data from 10 experiments building binary search trees and accessing elements using each of the four algorithms will be printed, as well as information about the key array and key access sequence used for each experiment. I interpreted the assignment as requiring a single key array and key access sequence to be generated (per experiment) that would be tested on the algorithms.

![Figure 1](https://github.com/sarukalva/Comparing-BSTs/blob/master/Figure%201.png)
Figure 1: Bar graph depicting the static competitive ratios of the Single Rotation, Splay, and Dynamic Monotone trees.

![Figure 2](https://github.com/sarukalva/Comparing-BSTs/blob/master/Figure%202.png)
Figure 2: The static competitive ratio was calculated by dividing the cost of each online algorithm over the cost of each offline algorithm.

Note: All trees are printed using a preorder DFS traversal.

The average static competitive ratio over 10 runs for each algorithm reveals that relative to the off-line algorithm that I implemented as the Optimal BST, the Dynamic Monotone Tree has the lowest ratio. It is better than Splay Tree and Single Rotation, which end up having very similar costs (Figure 2) and a similar static competitive ratio (Figure 1), as evidenced in the tables below (Figure 3). Cost was defined as 1+depth+number of rotations in the online algorithms. 

![Figure 3: Table of the cost of the key access sequence for each tree.](https://github.com/sarukalva/Comparing-BSTs/blob/master/Figure%203.png)
