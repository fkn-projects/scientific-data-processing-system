package plugins;

import java.util.HashMap;
import java.util.Map;

public class MyFunction1 implements DataGenerationFunction{

	
	private String functionName;
	private Map<String, String> parameters = new HashMap<>();
	
	
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
	public Map<String, String> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getValue(double x) {
		// TODO Auto-generated method stub
		return 0;
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

}
