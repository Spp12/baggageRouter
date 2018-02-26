package baggageRouter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Graph {

	Map<String, Vertex> graphMap;
	//map the vertex names
	public Graph(List<Edge> edges) {
	
		graphMap = new HashMap<>(edges.size());
	
	for(Edge e : edges) {
		
		if (!graphMap.containsKey(e.getSource().getName())) graphMap.put(e.getSource().getName(), new Vertex(e.getSource().getName()));
        if (!graphMap.containsKey(e.getDestination().getName())) graphMap.put(e.getDestination().getName(), new Vertex(e.getDestination().getName()));
    }

    //Set all the neighbours
    for (Edge e : edges) {
        graphMap.get(e.getSource().getName()).getNeighbours().put(graphMap.get(e.getDestination().getName()), e.getTime());
    }
	}
	
	/**
     * Runs dijkstra algorithm 
     */
    public void dijkstra(String startName) {
      
        final Vertex source = graphMap.get(startName);
        NavigableSet<Vertex> queue = new TreeSet<>();

        // populate vertices to the queue
        for (Vertex v : graphMap.values()) {
            v.setPrevious( v == source ? source : null);
            v.setTime(v == source ? 0 : Integer.MAX_VALUE);
            queue.add(v);
        }

        dijkstra(queue);
    }

    /**
     * Get the shortest path as a list of Vertex 
     * @return the shortest path as a List<Vertex>
     */

    public List<Vertex> getShortestPath(String endName){
        
System.out.println("In get shortes path "+ endName);
        return graphMap.get(endName).getShortest();
    }

    // Implementation of dijkstra's algorithm using a binary heap.
    private void dijkstra(final NavigableSet<Vertex> que) {
        Vertex source, neighbour;
        while (!que.isEmpty()) {

            source = que.pollFirst();
            if (source.getTime() == Integer.MAX_VALUE) break; 
            
            
            
            for (Map.Entry<Vertex, Integer> a : source.getNeighbours().entrySet()) {
                neighbour = a.getKey();

                final int alternateTime = source.getTime() + a.getValue();
                if (alternateTime < neighbour.getTime()) { // shorter path
                    que.remove(neighbour);
                    neighbour.setTime(alternateTime);
                    neighbour.setPrevious(source);
                    que.add(neighbour);
                }
            }
        }
    }
}
