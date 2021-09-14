package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		
		/** COMPLETE THIS METHOD **/
		// get rid of cases for null
		if( g == null || p1 == null || p2 == null) {
			return null;
		}
		if (g.map.get(p1)== null || g.map.get(p2)== null) {
			return null;
		}
		ArrayList<String> sChain = new ArrayList<String>();
		
		if (p2.equals(p1)) {
			sChain.add(p1);
			return sChain;
		}
		
		// inititalizing the stack, queues, and other variables
		int p1Ind = g.map.get(p1);
		int p2Ind = g.map.get(p2);
		ArrayList<Integer> checked = new ArrayList<Integer>(); // ***maybe change to check integer, cause there could be repeated names
		Queue<String> namesQueue = new Queue<String>();
		Stack<Edge> pathEdges = new Stack<Edge>();
		String dequeue = null;
		Friend ptr = null; 
		Edge currEdge = null;
		checked.add(p1Ind);
		namesQueue.enqueue(g.members[p1Ind].name); // put p1's name in	
		int currInd = p1Ind; //starting point
		
		while(!namesQueue.isEmpty()) { //check all friends
			dequeue = namesQueue.dequeue(); 
			currInd = g.map.get(dequeue); //need to save start of edge
			ptr = g.members[currInd].first;
			
			while(ptr!= null) {  //Breadth-first search 
				if(!checked.contains(ptr.fnum)) { // if we hit an never been hit yet vertex
					namesQueue.enqueue(g.members[ptr.fnum].name); //add it next to the queue
					checked.add(ptr.fnum); //add it to the checked vertex bank
					pathEdges.push(new Edge(currInd, ptr.fnum)); // put it to the bank of edges so we can trace at the end
				}
				if(g.members[ptr.fnum].name.equals(p2)) { //if we find target name
					int edgeInd = ptr.fnum; // v2 of the last edge
					Stack<String> fromEnd = new Stack<String>();
					while(!pathEdges.isEmpty()) { //check all the edges
					currEdge = pathEdges.pop();
						if(currEdge.v2 == edgeInd) { //check if the right next edge was popped out
							fromEnd.push(g.members[edgeInd].name); //build stack to pop from at the end
							edgeInd = currEdge.v1; //the next edge v2 is the current's v1
						}
					}
					fromEnd.push(p1); //need to get p1 in there since the while loop will only fill up to right before it
					while(!fromEnd.isEmpty()) {
						sChain.add(fromEnd.pop()); //add the names in order to the chain
					}
					return sChain; //found p2 returns the path
				}
				ptr= ptr.next;  //no path found from the first friend check the next until the origin has no friends
			}
			
		}
		
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return sChain; //if it exits the while loop no path was found returns null
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		/** COMPLETE THIS METHOD **/
		if (g == null || school == null) {
			return null;
		}
		int gSize = g.members.length;
		ArrayList<Integer> checked = new ArrayList<Integer>();
		ArrayList<ArrayList<String>> cliques = new ArrayList<ArrayList<String>>();
		Queue<Friend> friendQueue = new Queue<Friend>();
		while(checked.size()<gSize) {
			for(int i = 0; i<gSize; i++) { // go through every node
				if(!checked.contains(i)) { // need to add to checked if not looked at yet
					checked.add(i);
					if(g.members[i].student && g.members[i].school.equals(school)) { //if school match time to make a clique
						cliques.add(new ArrayList<String>()); // new clique to create
						int currClique = cliques.size()-1;  //added cliques are always at the end
						cliques.get(currClique).add(g.members[i].name);
						builder(i, g, school, checked,cliques.get(currClique)); //build clique
					}
				}
			}
		}
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return cliques;	
	}
	
	private static void builder(int index, Graph g, String school,ArrayList<Integer> checked, ArrayList<String> clique) {
		Friend ptr = g.members[index].first;
		while (ptr != null) {
			int currInd = ptr.fnum;
			if (!checked.contains(ptr.fnum)) {
				checked.add(ptr.fnum);
				if(g.members[currInd].student && g.members[currInd].school.equals(school)) {
					clique.add(g.members[currInd].name);
					int nextInd = ptr.fnum;
					builder(nextInd, g, school, checked, clique); //recursively check all of the first/each friends school matching nodes
				}
			}
			ptr = ptr.next; // go to the next friend from the origin
		}
		return;
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		
		/** COMPLETE THIS METHOD **/
		if (g == null) {
			return null;
		}
		
		// initializing variables
		ArrayList<String> allConnectors = new ArrayList<String>(); // holds connectprs
		int count = 0; //start value for dfs and back
		int gSize = g.members.length;
		int[] backupC = new int[gSize]; 
		boolean[] visited =  new boolean[gSize];//holds checked vertices
		int[] dfsnum = new int[gSize]; //holds dfsnum values
		int[] back = new int[gSize]; // holds the back values
		for(int i=0; i<gSize; i++) {
			if(!visited[i]) {
				backupC[i] = 0;
				DFS(g, visited, count, i, back, dfsnum, allConnectors,backupC, true);
			}
		}
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		
		return allConnectors;
		
	}
	
	private static void DFS(Graph g,boolean[] visited, int count, int vertex, int[] back,int[] dfsnum,ArrayList<String> connectors,int[] backupC, boolean startP) {
		visited[vertex] = true;
		dfsnum[vertex] = count;
		back[vertex] = count;
		int dfsVal = count +1;
		Friend ptr = g.members[vertex].first; 
		while(ptr != null) {
			int currVert = ptr.fnum;
			if(!visited[currVert]) {
				DFS(g, visited, dfsVal, currVert, back, dfsnum, connectors,backupC, false);
				// now it starts backing up
				if(dfsnum[vertex] <= back[ptr.fnum] && !startP) { // dfsnum(v) <= back(w) v is a connector point
					if(!connectors.contains(g.members[vertex].name)) {
						connectors.add(g.members[vertex].name);
					}
				}
				else if(dfsnum[vertex] <= back[ptr.fnum] && startP) { // dfsnum(v) <= back(w) but v is start and can be connector point
					//if it has 2 or more vertices it is a connector
					backupC[vertex] = backupC[vertex]+1;
					if(backupC[vertex] >=2) {
						if(!connectors.contains(g.members[vertex].name)) {
							connectors.add(g.members[vertex].name);
						}
					}			
				}
				if(dfsnum[vertex] > back[ptr.fnum]) { //dfsnum(v) > back(w)
					back[vertex] = Math.min(back[vertex], back[ptr.fnum]);
				}

			}
			if(visited[currVert]) { //neighbor has already been visited
				back[vertex] = Math.min(back[vertex], dfsnum[ptr.fnum]);
			}
			
			ptr = ptr.next;
		}
		return;
	}
}




/*
 * 

Finding the Largest Island
Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)

Example 1:

[[0,0,1,0,0,0,0,1,0,0,0,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,1,1,0,1,0,0,0,0,0,0,0,0],
 [0,1,0,0,1,1,0,0,1,0,1,0,0],
 [0,1,0,0,1,1,0,0,1,1,1,0,0],
 [0,0,0,0,0,0,0,0,0,0,1,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,0,0,0,0,0,0,1,1,0,0,0,0]]

Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.

Example 2:

[[0,0,0,0,0,0,0,0]]

Given the above grid, return 0.

Note: The length of each dimension in the given grid does not exceed 50.
*/

/*
0,0,1,
1,0,1,
1,1,1,
*/
/*
class Solution(object){
    public static int maxAreaOfIsland(grid){
      if (grid == null){
        return;
      }
      int columns = grid.length();
      int rows = grid[0].length();
      int largest = 0;
      int[][] booleanGrid = new int[rows][columns];
      for(int i = 0; i< columns; i++){
        for (int j = 0; j< rows; j++){
          if(grid[i][j] == 1 && booleanGrid[i][j] == false){
            booleanGrid[i][j] = true; //represent point is checked already
            int count = islandArea(grid, booleanGrid, row, col);
            if (count > largest){
              largest = count;
            }
          }
        }
      }
      return largest;

	}
  private static int islandArea(grid, booleanGrid, row, col){
      int count = 1; // area
      int columns = grid.length();
      int rows = grid[0].length();
      Queue<int> holderR = new Queue<int>; // row 
      Queue<int> holderC = new Queue<int>; // col 
      holderR.enqueue(row);
      holderC.enqueue(col);
      while(!holderR.isEmpty()){ //N.B take care of out of bounds exceptions
        int cRow = holderR.dequeue() ;//row col pair
        int cCol = holderC.dequeue();
        if (grid[cRow+1][cCol]==1 && booleanGrid[cRow+1][cCol] == false){ //down
            count++;
            holderR.enqueue(cRow+1);
            holderC.enqueue(cCol);
            booleanGrid[cRow+1][cCol] = true;
        }
            if (grid[cRow][cCol+1]==1 && booleanGrid[cRow][cCol+1] == false){ //right
            count++;
            holderR.enqueue(cRow);
            holderC.enqueue(cCol+1);
             booleanGrid[cRow][cCol+1] = true;
        }
             if (grid[cRow-1][cCol]==1 && booleanGrid[cRow-1][cCol] == false){ //up
            count++;
            holderR.enqueue(cRow-1);
            holderC.enqueue(cCol);
            booleanGrid[cRow-1][cCol] = true;
        }
            if (grid[cRow][cCol-1]==1 && booleanGrid[cRow][cCol-1] == false){ //left
            count++;
            holderR.enqueue(cRow);
            holderC.enqueue(cCol-1);
            booleanGrid[cRow][cCol-1] = true;
        }
      }
      return count;
  }
}
*/
/*
 */

