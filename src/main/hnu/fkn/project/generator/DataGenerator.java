package hnu.fkn.project.generator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import plugins.DataGenerationFunction;
import plugins.GeneratorParameters;
import plugins.MeasurementError;

public class DataGenerator {

	private DataGenerationFunction dataGenerationFunction;
	private MeasurementError measurementError;
	private GeneratorParameters generatorParameters;

	private Map<Double, Double> data = new TreeMap<Double, Double>();
	private Map<Double, Double> originalData = new TreeMap<Double, Double>();

	public DataGenerator(DataGenerationFunction dataGenerationFunction,
			MeasurementError measurementError,
			GeneratorParameters generatorParameters) {
		
		this.dataGenerationFunction = dataGenerationFunction;
		this.measurementError = measurementError;
		this.generatorParameters = generatorParameters;
	}
	
	public void generateData(){
		double x = 0.0;
		double y = 0.0;
		double delta = 0.0;
		
		while (generatorParameters.hasNextX()){
			x = generatorParameters.getNextX();	
			y = dataGenerationFunction.getValue(x);
			delta = measurementError.getMeasurementError(y);
			
			originalData.put(x, y);
			data.put(x, y + delta);
		}		
	}
	
	public Map<Double, Double> getData(){
		return data;
	}

	public Map<Double, Double> getOriginalData() {
		return originalData;
	}

	public Map<String, Map<String,String>> getParametersForSave(){
		
		Map<String, String> functionParameters = dataGenerationFunction.getFunctionParametersForSave();
        //functionParameters.put("name", dataGenerationFunction.getClass().getName());
		
        Map<String, String> errorParameters = measurementError.getErrorParametersForSave();
        //errorParameters.put("name", measurementError.getClass().getName());
		
        Map<String, String> genParameters = generatorParameters.getGeneratorParametersForSave();
        //genParameters.put("name", generatorParameters.getClass().getName());
		
        Map<String, Map<String,String>> allParameters = new HashMap<String, Map<String,String>>();
        allParameters.put("function", functionParameters);
        allParameters.put("error", errorParameters);
        allParameters.put("generator", genParameters);
        
		return allParameters;
	}

	
	
	
	
	

}
