package plugins;

import java.util.Map;
import java.util.TreeMap;

public class MyFunction1 implements DataGenerationFunction{

	
	private String functionName;
	private Map<String, String> parameters = new TreeMap<>();
	
	
	public MyFunction1() {
		this.functionName = "My Function 1";
		this.parameters.put("k", "int");
		this.parameters.put("a", "double");
		this.parameters.put("b", "double");
		
	}
	
	
	@Override
	public boolean checkParameters() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, String> getParametersForRendering() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getValue(double x) {

		double y = Math.cos(x/5)*10 + Math.cos(x)*2;
		return y;
	}

	@Override
	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Override
	public Map<String, String> getFunctionParameters() {
		return this.parameters;
	}


	@Override
	public void setParameters(Map<String, ? extends Object> parameters) {
		// TODO Auto-generated method stub
		
	}

}
