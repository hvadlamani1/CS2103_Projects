import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;
import java.io.*;

/**
 * Code to test an <tt>GraphSearchEngine</tt> implementation.
 */
public class GraphSearchEngineTester {
	@Test
	@Timeout(5)
	void testShortestPath1 () {
		final GraphSearchEngine searchEngine = new GraphSearchEngineImpl();
		final IMDBGraph graph;
		try {
			graph = new IMDBGraphImpl(IMDBGraphImpl.IMDB_DIRECTORY + "/testActors.tsv", 
					IMDBGraphImpl.IMDB_DIRECTORY + "/testMovies.tsv");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			assertTrue(false);
			return;
		}
		final Node actor1 = graph.getActor("Kris");
		final Node actor2 = graph.getActor("Sandy");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		assertEquals(5, shortestPath.size());
		final String[] correctNames = { "Kris", "Blah2", "Sara", "Blah3", "Sandy" };
		int idx = 0;
		for (Node node : shortestPath) {
			assertEquals(correctNames[idx++], node.getName());
		}
	}
	
	
	/**
	 * A method that test all of the constrains required to check if the path returned by the findShortestPath
	 * method are true (checks to see whether findShortestPath works as intended)
	 */
	@Test
	@Timeout(5)
	void testShortestPath2() {
		final GraphSearchEngine searchEngine = new GraphSearchEngineImpl();
		final IMDBGraph graph;
		try {
			graph = new IMDBGraphImpl(IMDBGraphImpl.IMDB_DIRECTORY + "/testActors.tsv",
					IMDBGraphImpl.IMDB_DIRECTORY + "/testMovies.tsv");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			assertTrue(false);
			return;
		}
		final Node actor1 = graph.getActor("Obama");
		final Node actor2 = graph.getActor("Trump");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		final String[] correctNames = { "Obama", "Blah7", "Trump"};
		int idx = 0;
		for (Node node : shortestPath) {
			assertEquals(correctNames[idx++], node.getName());
		}
		assertEquals(3, shortestPath.size());
		
	}
	

	/*
	 * checks to see if the findShortestPath method returns a single node list containing the actor for the
	 * case when both the from node, and the to node are the same. 
	 */
	@Test
	void testSameActor() {
		final GraphSearchEngine searchEngine = new GraphSearchEngineImpl();
		final IMDBGraph graph;
		try {
			graph = new IMDBGraphImpl(IMDBGraphImpl.IMDB_DIRECTORY + "/testActors.tsv", 
					IMDBGraphImpl.IMDB_DIRECTORY + "/testMovies.tsv");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			assertTrue(false);
			return;
		}
		final Node actor1 = graph.getActor("Kris");
		final Node actor2 = graph.getActor("Kris");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		String[] correctedNames = { "Kris"};
		int index = 0;
		for(Node node: shortestPath) {
			assertEquals(correctedNames[index++], node.getName());	
		}
		assertEquals(1, shortestPath.size());
	}
	
	/**
	 * This test checks to see whether the correct output is returned for the case wehen one of the findShortestPath
	 * method is given a actor whose value is null
	 */
	@Test
	void testNullActor() {
		final GraphSearchEngine searchEngine = new GraphSearchEngineImpl();
		final IMDBGraph graph;
		try {
			graph = new IMDBGraphImpl(IMDBGraphImpl.IMDB_DIRECTORY + "/testActors.tsv", 
					IMDBGraphImpl.IMDB_DIRECTORY + "/testMovies.tsv");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			assertTrue(false);
			return;
		}
		final Node actor1 = null;
		final Node actor2 = graph.getActor("Sandy");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		assertEquals(null, shortestPath);
	}
	
	/**
	 * Checks the findShortestPath method to see whether the function returns the correct output for the case
	 * where the actors are not in the file
	 */
	@Test
	void testNoActorInFile() {
		final GraphSearchEngine searchEngine = new GraphSearchEngineImpl();
		final IMDBGraph graph;
		try {
			graph = new IMDBGraphImpl(IMDBGraphImpl.IMDB_DIRECTORY + "/testActors.tsv", 
					IMDBGraphImpl.IMDB_DIRECTORY + "/testMovies.tsv");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			assertTrue(false);
			return;
		}
		final Node actor1 = graph.getActor("AAA");
		final Node actor2 = graph.getActor("BBB");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		assertEquals(null, shortestPath);
	}
	
}
