
public class ServiceRequest {
	String query;
	
	public ServiceRequest(String query) {
		this.query = query;
	}
	
	public String extractWordToCheckFromRequest() {
		return query;
	}
}
