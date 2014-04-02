package plugins;

import java.util.Map;
import java.util.TreeMap;

public class DGFSizikov implements DataGenerationFunction {

	private String functionName;
	private Map<String, String> parameters = new TreeMap<>();

	//@delete
	private TreeMap<Double, TreeMap<Double, Double>> kMatrix = new TreeMap<>();
	
	private double Q;
	
	private double ds;
	private double s0;
	private double sn;
	
	public DGFSizikov() {
		this.functionName = "Sizikov Function";
		
		this.parameters.put("Q", "60");
		this.parameters.put("s0", "-0.85");
		this.parameters.put("sn", "0.85");
		this.parameters.put("ds", "0.025");
	}
		
	@Override
	public boolean checkParameters() {
		if(s0 > sn){
			return false;
		}
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
			this.parameters.remove("Q");
			this.parameters.put("Q", Double.toString(this.Q));
			
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"Q\", please");
		} else{
			throw new ClassCastException("Function parameter \"Q\" isn't valid");
		}
		
		tempObject = parameters.get("s0");
		
		if (tempObject instanceof Object){
			this.s0 = Double.parseDouble((String) parameters.get("s0"));
			this.parameters.remove("s0");
			this.parameters.put("s0", Double.toString(this.s0));
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"s0\", please");
		} else{
			throw new ClassCastException("Function parameter \"s0\" isn't valid");
		}
		
		tempObject = parameters.get("sn");
		
		if (tempObject instanceof Object){
			this.sn = Double.parseDouble((String) parameters.get("sn"));
			this.parameters.remove("sn");
			this.parameters.put("sn", Double.toString(this.sn));
		} else if (tempObject == null){
			throw new RuntimeException("Set parameter \"sn\", please");
		} else{
			throw new ClassCastException("Function parameter \"sn\" isn't valid");
		}
		
		tempObject = parameters.get("ds");
		
		if (tempObject instanceof Object){
			this.ds = Double.parseDouble((String) parameters.get("ds"));
			this.parameters.remove("ds");
			this.parameters.put("ds", Double.toString(this.ds));
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

		//@delete
		TreeMap<Double, Double> matrixRow = new TreeMap<>();

		for (int j = 0; j < (sn-s0) / ds; j++) {
			
			if(j == 0 || j == Math.ceil((sn - s0) / ds) - 1){
				pj = 0.5 * ds;
			} else {
				pj = ds;
			}
		
			
			double k = K (x, sj);
			//@delete
			matrixRow.put(sj, k);
			
			fj = fj + pj * k * y (sj);
			sj = sj + ds;
	
		}
		//@delete
		kMatrix.put(x, matrixRow);
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
	
	//@delete
	public TreeMap<Double, TreeMap<Double, Double>> getkMatrix() {
		return kMatrix;
	}
	
}
