package plugins;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import plugins.MeasurementError;

public class ContinuousMeasurementError implements MeasurementError {
	private String errorName;
	private Map<String, String> parameters = new TreeMap<String, String>();
	
	//добавить объ€вление параметров распределени€
	private double alpha;//coefficient mashtaba
	private double beta;//beta-alpha coefficient sdviga
	
	public ContinuousMeasurementError() {
		this.errorName = "Continuous Error";
		this.parameters.put("Alpha", "double");
		this.parameters.put("Beta", "double");
		
	}

	@Override
	public void setParameters(Map<String, ? extends Object> parameters) {
		Object tempObject = parameters.get("Alpha");
		if (tempObject instanceof Double){
			this.alpha = (Double)parameters.get("Alpha");
		} else{
			throw new ClassCastException("ѕараметр \"Alpha\" имеет неверный тип");
		}
		
		tempObject = parameters.get("Beta");
		if (tempObject instanceof Double){
			this.beta = (Double) parameters.get("Beta");
		} else{
			throw new ClassCastException("ѕараметр \"Beta\" имеет неверный тип");
		}

	}

	@Override
	public boolean checkParameters() {
		boolean valid = true;
		if(this.alpha > this.beta){
			valid = false;
		}
		return valid;
	}

	@Override
	public Map<String, String> getParametersForRendering() {
		return this.parameters;
	}

	@Override
	public double getMeasurementError() {
		return alpha + (beta - alpha)*Math.random();
	}

	@Override
	public String getErrorName() {
		return this.errorName;
	}

}
