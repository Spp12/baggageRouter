package baggageRouter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vertex implements Comparable<Vertex>{

	private final String name;
	private int time = 99999;
	private Vertex previous;
	private final Map<Vertex, Integer> neighbouts = new HashMap<>();
	
	public Vertex(String name) {
		this.name = name;
		
	}

	@Override
	public int compareTo(Vertex o) {
		// TODO Auto-generated method stub
		int x = 0;
		if(time ==o.time)
		{
			x= name.compareTo(o.name);
		}else
		{
			x=Integer.compare(time,  o.time);
		}
		
		return x;
	}

	public List<Vertex> getShortest()
	{
		List<Vertex> p = new ArrayList<Vertex>();
		p.add(this);
		Vertex v = this.getPrevious();
		
		while(p.contains(v)) {
			p.add(v);
			
			v=v.getPrevious();
		}
		
		Collections.reverse(p);
		return p;
	}
	
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Vertex getPrevious() {
		return previous;
	}

	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}

	public String getName() {
		return name;
	}

	public Map<Vertex, Integer> getNeighbours() {
		return neighbouts;
	}
}
