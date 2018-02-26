package baggageRouter;

// Class used to identify the baggage details, flight id and the gate

public class Baggage {

	private final String flightId;
	private final String bagId;
	private final String gateId;
	
	public Baggage(String bagId, String gateId,String flightId) { 
		this.flightId=flightId;
		this.bagId =bagId;
		this.gateId = gateId;
				
	}

	public String getFlightId() {
		return flightId;
	}

	public String getBagId() {
		return bagId;
	}

	public String getGateId() {
		return gateId;
	}
	
	
}
