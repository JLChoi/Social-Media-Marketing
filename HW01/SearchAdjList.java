//Tiffany Moi
//tmoi
//HW01

import java.io.File; 
import java.util.*;

public class SearchAdjList {

    int nodes;

    //Adjacency Map
    Map<Integer, ArrayList<Integer>> graph;
    
    /* Sets up the list and set the number of vertices in the graph */
    public SearchAdjList(int n) {
	graph = new TreeMap<Integer,ArrayList<Integer>>();
       	nodes = n;
	load();
		
    }

    public SearchAdjList(int n, Map<Integer,ArrayList<Integer>> g) {
	graph = g;
	nodes = n;
    }


    /* Read in the information and add to graph */
    public void load() {

	for (int i = 0; i < nodes; i ++) {
	    graph.put(i, new ArrayList<Integer>());
	}
	
	File file = new File("facebook_combined.txt");
	
	try { 
	    Scanner sc = new Scanner(file);
	    while (sc.hasNextLine()) {
		String[] strNodes = sc.nextLine().split(" ");
		//Convert to int
		int[] nodes = new int[2];
		nodes[0] = Integer.parseInt(strNodes[0]);
		nodes[1] = Integer.parseInt(strNodes[1]);
		graph.get(nodes[0]).add(nodes[1]);
	        graph.get(nodes[1]).add(nodes[0]);
	    }
	}
	catch (Exception e) {
	}
    }

    /* For debugging to see the adjacency list */
    public void printGraph() {
	for (int i = 0; i < nodes; i ++) {
	    for (int j = 0; j < graph.get(i).size(); j ++) {
		System.out.print(graph.get(i).get(j));
	    }
	    System.out.println("");
	}
    }

    /* Helper function for BFS, loops through the component of the starting node until all
     * nodes in the components have been visted. 
     */
    private void breadthHelp(int start, int[] seen, List<Integer> ret, Queue<Integer> q) {

	//Add starting node to queue
	q.add(start);
	
	while (q.size() > 0) {
	    
	    int curr = q.remove();
	    ret.add(curr);

	    //Mark as seen
	    seen[curr] = 1;

	    //Add neighbors into the queue
	    for (int j = 0; j < graph.get(curr).size(); j ++) {
		int n = graph.get(curr).get(j);
		if (seen[n] == 0 && !(q.contains(n))) {
		    q.add(n);
		}
	    }
	}

    }

    
    /* BFS implementation */
    public List<Integer> breadthSearch(int start) {

	if (start < 0 || start >= nodes) {
	    throw new IllegalArgumentException();
	}
	//Initialize values
	Queue<Integer> q = new LinkedList<Integer>();
	int[] seen = new int[nodes];
        List<Integer> ret = new ArrayList<Integer>();

	breadthHelp(start, seen, ret, q);

	//If all nodes have not been visited, jump to another component
	while (ret.size() < nodes) {
	    for (int i = 0; i < seen.length; i++) {
		if (seen[i] == 0) {
		    start = i;
		    break;
		}
	    }
	    breadthHelp(start, seen, ret, q);
	}
	
	return ret;
	
    }

    /* Helper function for DFS, loops through the component of the starting node until all
     * nodes in the components have been visted. 
     */
    private void depthHelp(int start, int[] seen, List<Integer> ret, Stack<Integer> s) {

	s.push(start);
	
	while (s.size() > 0) {
	    
	    int curr = s.pop();
	    ret.add(curr);

	    //Mark as seen
	    seen[curr] = 1;
	    
	    for (int j = 0; j < graph.get(curr).size(); j ++) {
		int n = graph.get(curr).get(j);
		if (seen[n] == 0 && !(s.contains(n))) {
		    s.push(n);
		}
	    }
	}

    }

    /* DFS implementation */
    public List<Integer> depthSearch(int start) {

	if (start < 0 || start >= nodes) {
	    throw new IllegalArgumentException();
	}

	//Initialize values
	Stack<Integer> s = new Stack<Integer>();
        List<Integer> ret = new ArrayList<Integer>();
	int[] seen = new int[nodes];

	depthHelp(start, seen, ret, s);

	//If all nodes have not been visited, jump to another component
	while (ret.size() < nodes) {
	    for (int i = 0; i < seen.length; i++) {
		if (seen[i] == 0) {
		    start = i;
		    break;
		}
	    }
	    depthHelp(start, seen, ret, s);
	}
	
	return ret;
	
    }

    /* Checks to see if running BFS on one component visits all of the 
     * nodes in the graph 
     */
    public boolean isConnected() {
	Queue<Integer> q = new LinkedList<Integer>();
	int[] seen = new int[nodes];
        List<Integer> ret = new ArrayList<Integer>();
	breadthHelp(0, seen, ret, q);

	return (ret.size() == nodes);
    }

    /* Finds the distance between two given nodes using BFS. It uses
     * frontiers as the distance. 
     * If the start and end nodes are not in the same component, then
     * return -1.
     * If runFull is true, it will make the program find all frontiers
     * in the graph starting from the start node.
     */
    public int distance(int start, int end, boolean runFull) {

	if (start < 0 || start >= nodes) {
	    throw new IllegalArgumentException();
	}

	Queue<Integer> q = new LinkedList<Integer>();

	q.add(start);
	int[] seen = new int[nodes];
	int frontiers = 0;
	int startNextFrontier = 0;
	int curr = 0;
	// i keeps track of the number of nodes seen
	int i = 0;
	
	while (q.size() > 0) {

	    curr = q.remove();
	    if (!runFull && curr == end) {
		break;
	    }
	    //Mark as seen
	    seen[curr] = 1;
	    
	    for (int j = 0; j < graph.get(curr).size(); j ++) {
		int n = graph.get(curr).get(j);
		if (seen[n] == 0 && !(q.contains(n))) {
		    q.add(n);
		}	    
	    }

	    //Move on to the next frontier
	    if (i == startNextFrontier) {
		frontiers ++;
		//Index where the children will be added
		startNextFrontier = i + q.size();
	    }
	    
	    i ++;
	}

	//If the curr != end, then that means that the end node could not be found
	if (!runFull && curr != end) {
	    return -1;
	} else {
	    return frontiers;
	}
	
    }

    /* Counts the number of nodes within a certain distance of a given node */
     public List<Integer> nodesDistanceFrom(int start, int distance) {

	 if (start < 0 || start >= nodes) {
	    throw new IllegalArgumentException();
	}

	Queue<Integer> q = new LinkedList<Integer>();

	q.add(start);
	int[] seen = new int[nodes];
	int frontiers = 0;
	int startNextFrontier = 0;
	// i keeps track of the number of nodes seen
	int i = 0;
	// Contains all of the nodes within the distance from the start
        List<Integer> ret = new ArrayList<Integer>();
	
	while (q.size() > 0) {

	    int curr = q.remove();
	    if (frontiers <= distance) {
		ret.add(curr);
	    }

	    if (frontiers > distance) {
		break;
	    }
	    //Mark as seen
	    seen[curr] = 1;
	    
	    for (int j = 0; j < graph.get(curr).size(); j ++) {
		int n = graph.get(curr).get(j);
		if (seen[n] == 0 && !(q.contains(n))) {
		    q.add(n);
		}	    
	    }
	    //Move on to the next frontier
	    if (i == startNextFrontier) {
		frontiers ++;
		//Index where the children will be added
		startNextFrontier = i + q.size();
	    }
	    i ++;
	}

	return ret;
	
    }

    public static void main(String[] args) {

	long startTime;
	long endTime;

	System.out.println("==============Runtimes==============");

	/*Loading Info*/

	long loadTimes = 0;
	SearchAdjList obj = null;

	for (int i = 0; i < 20; i ++) {
	
	    startTime = System.nanoTime();
	    obj = new SearchAdjList(4039);
	    endTime = System.nanoTime();
	    loadTimes += endTime - startTime;
	}

	long loadTime = loadTimes / 20;
	System.out.println("Load: " + loadTime);
	

	/* BFS */

	List<Integer> bSearch = null;
	long bfsTimes = 0;

	for (int i = 0; i < 20; i ++) {
	
	    startTime = System.nanoTime();
	    bSearch = obj.breadthSearch(0);
	    endTime = System.nanoTime();
	    bfsTimes += endTime - startTime;
	}
	
	long bfsTime = bfsTimes / 20;

	System.out.println("BFS: " + bfsTime);

	
	/* DFS */

	List<Integer> dSearch = null;
	long dfsTimes = 0;

	for (int i = 0; i < 20; i ++) {
	
	    startTime = System.nanoTime();
	    dSearch = obj.depthSearch(0);
	    endTime = System.nanoTime();
	    dfsTimes += endTime - startTime;
	}
	
	long dfsTime = dfsTimes / 20;

	System.out.println("DFS: " + dfsTime);

	
	/* Questions */
	System.out.println("==============Distance==============");
	System.out.println(obj.distance(10, 1050, false));
	
	System.out.println("==============Connected?==============");
	System.out.println(obj.isConnected());

	System.out.println("==============Frontiers==============");
	System.out.println(obj.distance(0, 0, true));
	System.out.println(obj.distance(10, 0, true));
	System.out.println(obj.distance(1000, 0, true));

	System.out.println("==============Nodes Distance from Other==============");
	System.out.println(obj.nodesDistanceFrom(1985, 4).size());
	
        
    }
}
