package plugins;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class BeginStepEnd implements GeneratorParameters {

	private double begin;
	private double end;
	private double step;
	
	private final int MAX_COUNT_OF_POINT = 10_000;
	private String pluginName = "Begin Step End";
	
	
	@Override
	public void initialize(Map<String, ? extends Object> parameters) {
		
		this.begin = (Double) parameters.get("begin");
		this.end = (Double) parameters.get("end");
		this.step = (Double) parameters.get("step");
	}
	
	@Override
	public boolean checkParameters() {

		boolean valid = true;
		
		if(this.begin >= this.end){
			valid = false;
		}
		if(this.begin + this.step > this.end){
			valid = false;
		}
		if((this.end - this.begin)/this.step > MAX_COUNT_OF_POINT*1.0){
			valid = false;
		}
		return valid;
	}

	@Override
	public Map<String, String> getParametersForRendering() {
		
		Map<String, String> parametersNames = new TreeMap<String, String>();
		
		parametersNames.put("begin", "double");
		parametersNames.put("end", "double");
		parametersNames.put("step", "double");
		
		return parametersNames;
	}

	@Override
	public double getNextX() {

		if(this.begin <= this.end){

			double x = this.begin;
			this.begin += this.step;
			return x;
		}
		return -1;
	}

	@Override
	public String getGeneratorParametersName() {
		
		return this.pluginName;
	}

	@Override
	public void setParameters(Map<String, ? extends Object> parameters) {
		
		Object tempObject = parameters.get("begin");
		if (tempObject instanceof Double){
			this.begin = (Double) parameters.get("begin");
		} else{
			throw new ClassCastException("Параметр \"begin\" имеет неверный тип");
		}
		
		tempObject = parameters.get("end");
		if (tempObject instanceof Double){
			this.end = (Double) parameters.get("end");
		} else{
			throw new ClassCastException("Параметр \"end\" имеет неверный тип");
		}
		
		tempObject = parameters.get("step");
		if (tempObject instanceof Double){
			this.step = (Double) parameters.get("begin");
		} else{
			throw new ClassCastException("Параметр \"step\" имеет неверный тип");
		}		
		
	}
	
	
	
}
