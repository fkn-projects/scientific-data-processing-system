package plugins;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import plugins.MeasurementError;

public class MEUniform implements MeasurementError {
	private String errorName;
	private Map<String, String> parameters = new TreeMap<String, String>();

	// добавить объ€вление параметров распределени€
	private double a;// coefficient mashtaba
	private double b;// beta-alpha coefficient sdviga
	private double percent; 

	public MEUniform() {
		this.errorName = "Uniform Error";
		this.parameters.put("A", "0");
		this.parameters.put("B", "1");
		this.parameters.put("Percent", "5");

	}

	@Override
	public Map<String, String> getErrorParametersForSave() {
		Map<String, String> parametersForSave = new TreeMap<String, String>();
		
		parametersForSave.put("A type=\"double\"", Double.toString(a));
		parametersForSave.put("B type=\"double\"", Double.toString(b));
		parametersForSave.put("Percent type=\"double\"", Double.toString(percent));
		parametersForSave.put("name type=\"String\"", errorName);		
		
		return parametersForSave;
	}
	
	@Override
	public void setParameters(Map<String, ? extends Object> parameters) {
		Object tempObject = parameters.get("A");
		if (tempObject instanceof Object) {
			this.a = Double.parseDouble((String) parameters.get("A"));
			this.parameters.remove("A");
			this.parameters.put("A", Double.toString(this.a));
		
		} else {
			throw new ClassCastException(
					"Measurement error parameter \"A\" isn't valid");
		}

		tempObject = parameters.get("B");
		if (tempObject instanceof Object) {
			this.b = Double.parseDouble((String) parameters.get("B"));
			this.parameters.remove("B");
			this.parameters.put("B", Double.toString(this.b));
		
		} else {
			throw new ClassCastException("Measurement error parameter \"B\" isn't valid");
		}
		
		tempObject = parameters.get("Percent");
		if (tempObject instanceof Object) {
			this.percent = Double.parseDouble((String) parameters.get("Percent"));
			this.parameters.remove("Percent");
			this.parameters.put("Percent", Double.toString(this.percent));
		
		} else {
			throw new ClassCastException("Measurement error parameter \"Percent\" isn't valid");
		}

	}

	@Override
	public boolean checkParameters() {
		boolean valid = true;
		if (this.percent > 100 || this.percent < 0) {
			valid = false;
		}
		return valid;
	}

	@Override
	public Map<String, String> getParametersForRendering() {
		return this.parameters;
	}

	@Override
	public double getMeasurementError(double y) {

		double ksi = 0;
		if (b > a)
			ksi =  a + (b - a) * Math.random();
		else
			ksi =  b + (a - b) * Math.random();
		
		return (percent / 100) * ksi * y;
	}

	@Override
	public String getErrorName() {
		return this.errorName;
	}
}
