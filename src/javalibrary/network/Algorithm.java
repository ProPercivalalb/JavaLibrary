package javalibrary.network;

import java.util.Arrays;
import java.util.List;

public enum Algorithm {

	KRUSKAL(),
	PRIM(),
	DIJKSTRA();
	
	public static List<Algorithm> MIN_SPANNING_TREE = Arrays.asList(KRUSKAL, PRIM);
	public static List<Algorithm> SHORTEST_PATH = Arrays.asList(DIJKSTRA);
}
