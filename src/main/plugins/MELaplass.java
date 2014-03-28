package plugins;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import jsc.distributions.Distribution;
import jsc.distributions.Laplace;

public class MELaplass implements MeasurementError {

	private String errorName;
	private Map<String, String> parameters = new HashMap<String, String>();
	
	private double mean;//coefficient mashtaba
	private double scale;//coefficient sdviga
	private double percent; 

	
	public MELaplass() {
		this.errorName = "Laplass Error";
		this.parameters.put("Percent", "double");
		this.parameters.put("Scale", "double");
		this.parameters.put("Mean", "double");

	}
	 
	@Override
	public boolean checkParameters() {
		boolean valid = true;
		if(this.mean < 0){
			valid =  false;
		}
		return valid;
	}

	@Override
	public Map<String, String> getParametersForRendering() {
		return this.parameters;
	}

	@Override
	public Map<String, String> getErrorParametersForSave() {
		Map<String, String> parametersForSave = new TreeMap<String, String>();
		
		parametersForSave.put("Percent type=\"double\"", Double.toString(percent));
		parametersForSave.put("Scale type=\"double\"", Double.toString(scale));
		parametersForSave.put("Mean type=\"double\"", Double.toString(mean));
		parametersForSave.put("name type=\"String\"", errorName);
		
		
		return parametersForSave;
	}
	
	
	@Override
	public double getMeasurementError(double y) {
	
		Distribution distribution = new Laplace(this.mean, this.scale);
		double ksi = distribution.random();
		return (percent / 100) * ksi * y;
	}	 
		
	
	@Override
	public String getErrorName() {
		return this.errorName;
	}


	@Override
	public void setParameters(Map<String, ? extends Object> parameters) {
		 
		Object tempObject = parameters.get("Mean");
		if (tempObject instanceof Object){
			this.mean = Double.parseDouble((String) parameters.get("Mean"));
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"Mean\", please");
		} else{
			throw new ClassCastException("Measurement error parameter \"Mean\" isn't valid");
		}
		
		tempObject = parameters.get("Scale");
		if (tempObject instanceof Object){
			this.scale = Double.parseDouble((String) parameters.get("Scale"));
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"Scale\", please");
		} else{
			throw new ClassCastException("Measurement error parameter \"Scale\" isn't valid");
		}
		
		tempObject = parameters.get("Percent");
		if (tempObject instanceof Object){
			this.percent = Double.parseDouble((String) parameters.get("Percent"));
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"Percent\", please");
		} else{
			throw new ClassCastException("Measurement error parameter \"Percent\" isn't valid");
		}		 
	}

	
}
