package plugins;

import java.util.Map;
import java.util.TreeMap;

public class DGFSizikov implements DataGenerationFunction {

	private String functionName;
	private Map<String, String> parameters = new TreeMap<>();
	
	private double Q;
	private double dx;
	
	private double ds;
	private double s0;
	private double sn;
	
	public DGFSizikov() {
		this.functionName = "Sizikov Function";
		
		this.parameters.put("Q", "double");
		this.parameters.put("s0", "double");
		this.parameters.put("sn", "double");
		this.parameters.put("ds", "double");
	}
		
	@Override
	public boolean checkParameters() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Map<String, String> getParametersForRendering() {
		return parameters;
	}

	@Override
	public String getFunctionName() {
		return this.functionName;
	}

	@Override
	public Map<String, String> getFunctionParametersForSave() {
	
		Map<String,String> parametersForSave = new TreeMap<>();
		parametersForSave.put("Q type=\"double\"", Double.toString(Q));
		parametersForSave.put("s0 type=\"double\"", Double.toString(s0));
		parametersForSave.put("sn type=\"double\"", Double.toString(sn));
		parametersForSave.put("ds type=\"double\"", Double.toString(ds));
		parametersForSave.put("name type=\"String\"", functionName);

		return parametersForSave;
	}

	@Override
	public void setParameters(Map<String, ? extends Object> parameters) {
		Object tempObject = parameters.get("Q");
		
		if (tempObject instanceof Object){
			this.Q = Double.parseDouble((String) parameters.get("Q"));
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"Q\", please");
		} else{
			throw new ClassCastException("Function parameter \"Q\" isn't valid");
		}
		
		tempObject = parameters.get("s0");
		
		if (tempObject instanceof Object){
			this.s0 = Double.parseDouble((String) parameters.get("s0"));
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"s0\", please");
		} else{
			throw new ClassCastException("Function parameter \"s0\" isn't valid");
		}
		
		tempObject = parameters.get("sn");
		
		if (tempObject instanceof Object){
			this.sn = Double.parseDouble((String) parameters.get("sn"));
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"sn\", please");
		} else{
			throw new ClassCastException("Function parameter \"sn\" isn't valid");
		}
		
		tempObject = parameters.get("ds");
		
		if (tempObject instanceof Object){
			this.ds = Double.parseDouble((String) parameters.get("ds"));
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"ds\", please");
		} else{
			throw new ClassCastException("Function parameter \"ds\" isn't valid");
		}
	}
	
	//f_i function
	@Override
	public double getValue(double x) {
		double fj = 0;
		double sj = s0;
		double pj = 0;

		for (int j = 0; j < (sn-s0) / ds; j++) {
			
			if(j == 0 || j == Math.ceil((sn - s0) / ds) - 1){
				pj = 0.5 * ds;
			} else {
				pj = ds;
			}
			
			fj = fj + pj * K (x, sj) * y (sj);
			sj = sj + ds;
		}
		return fj;
	}
	private double y (double s){
		double y = (Math.pow((1 - Math.pow(s/0.85,2)), 2) + 
					0.5 * Math.pow((Math.sin((Math.PI * s) / 0.85)), 4) *
					Math.cos((6.5 * Math.PI * s) / 0.85))*(1 - (s/4));
		
		return y;
	}
	private double K (double x, double s){
		double K = Math.sqrt(Q/Math.PI) * 
				   Math.exp(-Q * Math.pow((x - s), 2) / (1 + Math.pow(x, 2)));
		
		return K;		
	}
}
