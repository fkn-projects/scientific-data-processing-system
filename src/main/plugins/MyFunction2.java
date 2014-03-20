package plugins;

import java.util.Map;
import java.util.TreeMap;

public class MyFunction2 implements DataGenerationFunction {

	private String functionName;
	private Map<String, String> parameters = new TreeMap<>();
	
	
	public MyFunction2() {
		this.functionName = "My Function 2";
		this.parameters.put("C", "int");
		this.parameters.put("M", "double");
		this.parameters.put("P", "double");
		this.parameters.put("L", "double");

		this.parameters.put("Z", "double");
		this.parameters.put("G", "double");
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getFunctionName() {
		return this.functionName;
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
