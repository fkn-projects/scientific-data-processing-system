package plugins;

import java.util.Map;
import java.util.TreeMap;

public class GPBeginStepEnd implements GeneratorParameters {

	private double begin;
	private double end;
	private double step;
	
	private double runner;
	
	
	private final int MAX_COUNT_OF_POINT = 10_000;
	private String pluginName;
	private Map<String, String> parametersNames = new TreeMap<String, String>();
	
	
	public GPBeginStepEnd() {
		pluginName = "Begin Step End";
		parametersNames.put("begin", "double");
		parametersNames.put("end", "double");
		parametersNames.put("step", "double");
	}

	
	@Override
	public Map<String, String> getGeneratorParametersForSave() {
	
		Map<String, String> parametersForSave = new TreeMap<String, String>();
	
		parametersForSave.put("begin type=\"double\"", Double.toString(begin));
		parametersForSave.put("end type=\"double\"", Double.toString(end));
		parametersForSave.put("step type=\"double\"", Double.toString(step));
		parametersForSave.put("name type=\"String\"", pluginName);
		
		return parametersForSave;
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
		return parametersNames;
	}
	
	@Override
	public boolean hasNextX() {
	
		if(this.runner <= this.end){
			return true;
		} else {		
			return false;
		}
	}
	
	@Override
	public double getNextX() {

		double x = this.runner;
		this.runner = this.runner + this.step;
		return x;
	}
	
	@Override
	public String getGeneratorParametersName() {
		
		return this.pluginName;
	}

	@Override
	public void setParameters(Map<String, ? extends Object> parameters) {
		
		Object tempObject = parameters.get("begin");
		if (tempObject instanceof Object ){
			this.begin = Double.parseDouble((String) parameters.get("begin"));
			this.runner = this.begin;
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"begin\", please");
		} else{
			throw new ClassCastException("Parameter \"begin\" isn't valid");
		}
		
		tempObject = parameters.get("end");
		if (tempObject instanceof Object){
			this.end = Double.parseDouble((String) parameters.get("end"));
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"end\", please");
		} else{
			throw new ClassCastException("Parameter \"end\" isn't valid");
		}
		
		tempObject = parameters.get("step");
		if (tempObject instanceof Object){
			this.step = Double.parseDouble((String) parameters.get("step"));
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"step\", please");
		} else{
			throw new ClassCastException("Parameter \"step\" isn't valid");
		}		
		
	}


	
	
	
}
