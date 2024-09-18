import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/**
 * Implements the GraphSearchEngine interface.
 */
public class GraphSearchEngineImpl implements GraphSearchEngine {
	public GraphSearchEngineImpl () {
	}

	/**
	 * Process the shortest path between two IMDBNode(s) using breadFirstSearch() method
	 * @param Node s which is a Node passed in
	 * @param Node t which is a Node passed in 
	 * @return shortestPath which is the shortestPath between s and t
	 */
	public List<Node> findShortestPath (Node s, Node t) {
		// returns null if either Node is null
		if(s == null || t == null) {
			return null;
		}		
		//sets shortestPath
		List<Node> shortestPath = GraphSearchEngineImpl.breadthFirstSearch(s,t); 
		return shortestPath;  
		
	}
	
	/**
	 * Computes shortestPath using Breadth First Search algorithm
	 * @param s
	 * @param t
	 * @return shortest path, between the two nodes using Breadth First Search algorithm
	 */
	private static List<Node> breadthFirstSearch(Node s, Node t) {
		//distances is also the visisted map, it keeps track of the minimum distance
		        Map<Node, Integer> distances = new HashMap<>();
		        //stores the parent node of the nodes being traversed, so the track isn't lost
		        Map<Node, Node> parentMap = new HashMap<>();
		        //order of nodes being processed
		        Queue<Node> queue = new LinkedList<>();
		        //stores the nodes of the shortestPath from s to t
		        List<Node> shortestPath = new ArrayList<>();
		        //initializing breadth first search
		        queue.add(s);
		        distances.put(s, 0);
		        parentMap.put(s, null);
		        // if queue is empty, set the current Node to the first element
		        while (!queue.isEmpty()) {
		            Node current = queue.poll();
		            // checks if destination node is current
		            if (current.equals(t)) {
		                Node node = t;
		                while (node != null) {
		                	//adds the node to shortestPath
		                    shortestPath.add(0, node);
		                    node = parentMap.get(node);
		                }
		                return shortestPath;
		            }
		            // getting neighbors for current Node
		            for (Node neighbor : current.getNeighbors()) {
		            	// if distances doesn't contain neighbor
		                if (!distances.containsKey(neighbor)) {
		                	// add neighbor with a value of current's value plus 1
		                    distances.put(neighbor, distances.get(current) + 1);
		                    //adds neighbor and current to parentMap
		                    parentMap.put(neighbor, current);
		                    // adding neigbor to queue
		                    queue.add(neighbor);
		                }
		            }
		        }		
		        //returns empty path is no path is found
		        return shortestPath; 	    	
	}
}
