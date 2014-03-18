package plugins;


import java.util.HashMap;
import java.util.Map;

public class LaplassMeasurementError implements MeasurementError {

	private String errorName;
	private Map<String, String> parameters = new HashMap<String, String>();
	
	public LaplassMeasurementError() {
		this.errorName = "Laplass Error";
		this.parameters.put("Sigma", "double");
		this.parameters.put("Alpha", "double");
	}
	
	
	@Override
	public boolean checkParameters() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, String> getParameters() {
		return this.parameters;
	}

	@Override
	public double getMeasurementError() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getErrorName() {
		return this.errorName;
	}

}
