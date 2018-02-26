package baggageRouter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/*
 * BaggageRouter class - routing of baggage based on flight number, entry and destination from file input1.txt
 * Baggage class has the bagid, flight id and gate id
 * Using dijkstra algorithm to find the shortest route.
 * */

/**
 * @author simha
 *
 */
/**
 * @author simha
 *
 */
/**
 * @author simha
 *
 */
public class BaggageRouter {

	 private final static String SECTION="# Section:";
	    private final static String ARRIVAL ="ARRIVAL";
	    private final static String CLAIM ="BaggageClaim";
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		 Scanner scan=readFile();
	    
	        if(scan != null){
	            List<Edge> edges= graphparser(scan);
	            Map<String,String> departuresMap=parseInputDepartures(scan); //Map with the flight as the key and the destination gate as the value
	            
	            System.out.println("departuresMap size is"+departuresMap.size());
	            List<Baggage> bagList = parseInputBags(scan);
	            scan.close();
	         /*   for(Baggage item : bagList){
	            	System.out.println("Inside Baggage "+item.getBagId()+item.getFlightId()+item.getGateId());
	            }*/
	            for(Baggage bag:bagList){
	                String bagId=bag.getBagId();
	                String entryGate=bag.getGateId();
	                String flight = bag.getFlightId();
	                System.out.println("Flight is. " +flight+ "entry gate is "+ entryGate+"bagId is "+bagId);
	                String destGate;
	                if(flight.equals(ARRIVAL)){
	                    destGate=CLAIM;
	                }else{
	                    destGate=departuresMap.get(flight);
	                }
	             // System.out.println("ENTRY "+ entryGate+destGate);
	                String pathLine=findShortestPath(entryGate,destGate,edges);

	                System.out.println(bagId+SINGLE_WHITE_SPACE+pathLine);
	            }
	        }

	}
	
	//for adding blank space
	private final static String SINGLE_WHITE_SPACE=" ";

    static Map<String, Graph> visitedMap=new ConcurrentHashMap<>();     
    /**
     * Read the input file
     * @return Scanner
     */
    private static Scanner readFile()
    {
    	
    	File file = new File("input1.txt");
    	Scanner sc = null;
        try {
        
            sc = new Scanner(file);
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sc;
    }
    
    /**
     * To find the shortest path
     * @param String, String, List
     * @return String
     */
    public static String findShortestPath(String entryGate, String destGate, List<Edge> edges) {
        Graph graph;
        if(visitedMap.containsKey(entryGate)){
        	graph = visitedMap.get(entryGate);
        }else {
        	graph = new Graph(edges);
        	graph.dijkstra(entryGate);
            visitedMap.put(entryGate,graph);
        }

        System.out.println("Entry" +entryGate + "Dest"+destGate);
        List<Vertex> shortestPath= graph.getShortestPath(destGate);
        return generatePathLine(shortestPath);
    }

   
    /**
     * To generate the shortest path
     * @param  List
     * @return String
     */private static String generatePathLine(List<Vertex> path){
        StringBuffer line = new StringBuffer();

        for(Vertex vertex:path){
            line.append(vertex.getName()).append(SINGLE_WHITE_SPACE);
        }
        line.append(": ").append(path.get(path.size()-1).getTime());
        return line.toString();
    }
    
   
     /**
      * Parser
      * @param Scanner
      * @return List
      */
    private static List<Edge> graphparser(Scanner scanner){
        String graphSection=scanner.nextLine();
        if(!graphSection.startsWith("# Section:")){
            throw new IllegalArgumentException("Illegal arguments or inputs. Please refer to readme for the input data format.");
        }
        String next=scanner.nextLine();
        List<Edge> edges=new ArrayList<>();
        while (!next.startsWith(SECTION)){
            String[] nodes=next.trim().split("\\s+");
            if(nodes.length>=3) {
                Edge directedEdge = new Edge(new Vertex(nodes[0]),new Vertex(nodes[1]),Integer.valueOf(nodes[2]));
                edges.add(directedEdge);
                //Since it is bi-direction edge, will add another direction edge too.
                Edge newEdge = new Edge(new Vertex(nodes[1]),new Vertex(nodes[0]),Integer.valueOf(nodes[2]));
                edges.add(newEdge);
            }else{
                throw new IllegalArgumentException("Illegal arguments or inputs. Please refer to readme for the input data format.");
            }
            next=scanner.nextLine();
        }
        return edges;
    }

    private static Map<String,String> parseInputDepartures(Scanner scanner){
        String next=scanner.nextLine();
        
        Map<String,String> departuresMap=new HashMap<>();
        while(!next.startsWith(SECTION)){
        //	System.out.println("Inside While ..");
        	String [] nodes=next.trim().split("\\s+");
        	
            if(nodes.length >= 2){
            //	System.out.println("Inside if "+ nodes.length);
            	departuresMap.put(nodes[0],nodes[1]);
            }else{
                throw new IllegalArgumentException("Illegal arguments or inputs. Please refer to readme for the input data format.");
            }
            next=scanner.nextLine();
        }
       /* for(String key : departuresMap.keySet()){
        	System.out.println("departuresMap contains .."+key);}
        for(String key : departuresMap.values()){
        	System.out.println("departuresMap contains values .."+key);}
        */
        return departuresMap;
    }

    private static List<Baggage> parseInputBags(Scanner scanner){
        String next ;
        List<Baggage> bagList= new ArrayList<>();
        do{
            next = scanner.nextLine();
            String [] parts = next.trim().split("\\s+");
            if(parts.length >=3){
                Baggage bag= new Baggage(parts[0],parts[1],parts[2]);
                System.out.println("Adding bags in parseInputBags"+ parts[0]+parts[1]);
                bagList.add(bag);
            }else{
                scanner.close();
                break;
            }
        }while(scanner.hasNextLine());
        return bagList;
    }

}
