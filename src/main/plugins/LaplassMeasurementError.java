package plugins;


import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import cern.jet.random.Distributions;

public class LaplassMeasurementError implements MeasurementError {

	private String errorName;
	private Map<String, String> parameters = new TreeMap<String, String>();
	
	//добавить объявление параметров распределения
	private double alpha;//coefficient mashtaba
	private double beta;//coefficient sdviga
	
	
	public LaplassMeasurementError() {
		this.errorName = "Laplass Error";
		this.parameters.put("Alpha", "double");
		this.parameters.put("Beta", "double");
		
	}
	
	
	@Override
	public boolean checkParameters() {
		boolean valid = true;
		if(this.alpha < 0){
			valid =  false;
		}
		return valid;
	}

	@Override
	public Map<String, String> getParametersForRendering() {
		return this.parameters;
	}

	@Override
	public double getMeasurementError() {
		cern.jet.random.engine.RandomEngine generator;
		generator = new cern.jet.random.engine.MersenneTwister(new java.util.Date());
		// I'm not sure about this
		return alpha + (beta - alpha)*Distributions.nextLaplace(generator);  
	}	 
		
		
		/*
		double y;
		double x = Math.random();
		y = (alpha/2)*Math.exp((-alpha)*(x-beta));
		double sv;
		double a = -5, b = 5;
		
		
		while(n < y){
			y = f(x); // формулу подставить
			}
			return n; // n - это сл.в. 
			y=f(x), где f(x) - твой закон распределения
					 
					т.е. генерируешь число, потом сравниваешь его с y, если число < y оно попало под график
					то это число сгенерировано по твоему закону
					
	 
		 
		return sv;
	}
*/
	@Override
	public String getErrorName() {
		return this.errorName;
	}


	@Override
	public void setParameters(Map<String, ? extends Object> parameters) {
		 
		Object tempObject = parameters.get("Alpha");
		if (tempObject instanceof Double){
			this.alpha = (Double)parameters.get("Alpha");
		} else{
			throw new ClassCastException("Параметр \"Alpha\" имеет неверный тип");
		}
		
		tempObject = parameters.get("Beta");
		if (tempObject instanceof Double){
			this.beta = (Double) parameters.get("Beta");
		} else{
			throw new ClassCastException("Параметр \"Beta\" имеет неверный тип");
		}
		
		 
	}

}
