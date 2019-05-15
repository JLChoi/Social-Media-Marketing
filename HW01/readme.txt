Tiffany Moi
tmoi
HW01

Question 4:

a. The distance between the two nodes is 3.

b. The graph is connected because my BFS algorithm was able to reach all nodes
without having to restart at another node.

c. Starting from node 0, there were 7 frontiers. Starting from node 10, there
were 8 frontiers, and starting from node 1000, there were 7 frontiers.
The number of frontiers will change slightly based on the neighbors of the
starting node. This is because it may take an extra step at the end to reach the
final nodes in the graph. (???????)

d. There are 3238 nodes that are within a distance of 4 from node 1985.


Assumptions:

- I assumed that the first frontier began with the starting node's children. I
didn't count the first node as its own frontier.

Extra Credit:

My stats for both implementation (in milliseconds)

      Action 	  |   Matrix  |   List
   -------------------------------------
   Loading Graph  |   134.3   |	  90.3
   -------------------------------------
        BFS	  |   116.0   |	 112.5
   -------------------------------------
        DFS	  |    86.6   |	  68.1
   -------------------------------------

I think the Adjacency List is faster for loading because instead of looking up
the index of the array twice as you would in the matrix (since it's a 2D array),
the list only needs to look up the nodes once in the map. In addition, the BFS
and the DFS will only go through the list of nodes that the current node is
next to and it doesn't need to check through all of the possible nodes as it
would in the matrix to find all of the 1's. Thus, the Adjacency List would be
faster in performing all 3 actions.
