package baggageRouter;

public class Edge {

		public Vertex getDestination() {
		return destination;
	}

	public Vertex getSource() {
		return source;
	}

	public int getTime() {
		return time;
	}

		private final Vertex destination;
		private final Vertex source;
		private final int time;
		
		public Edge(Vertex source, Vertex destination, int time)
		{
			this.source = source;
			this.destination = destination;
			this.time = time;
		}
		
}
