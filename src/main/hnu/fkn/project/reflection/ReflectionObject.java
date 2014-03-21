package hnu.fkn.project.reflection;

import hnu.fkn.project.utils.ApplicationConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import plugins.DataGenerationFunction;
import plugins.GeneratorParameters;
import plugins.MeasurementError;

public class ReflectionObject {
	
	DataGenerationFunction function = null;
	MeasurementError error = null;
	GeneratorParameters generatorParameters = null;

	private Map<String, String> pluginClassNames = new HashMap<>();
	
	public Map<String, List<String>> loadPluginsNames() 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException {

		File pluginFolder = new File(System.getProperty("java.class.path") + ApplicationConfiguration.getItem("plugin.folder"));
		File[] files = pluginFolder.listFiles();

		if (files == null || files.length == 0){
			throw new FileNotFoundException();
		}		
		
		Map<String, List<String>> pluginsNames = new HashMap<>();
		List<String> functionsNames = new ArrayList<>();
		List<String> errorsNames = new ArrayList<>();
		List<String> paramNames = new ArrayList<>();

		
		for (File file : files) {

			String[] fileName = file.getName().split(".class");
			
			Class clazz = Class.forName("plugins." + fileName[0]);
			Class[] interfaces = clazz.getInterfaces();

			boolean isMeasurenemtErrorPlugin = false;
			boolean isDataGenerationFunctionPlugin = false;
			boolean isGeneratorParametersPlugin = false;
			
			for (Class interfase : interfaces) {
				if (interfase.getCanonicalName() == "plugins.MeasurementError") {
					isMeasurenemtErrorPlugin = true;
				}
				
				if (interfase.getCanonicalName() == "plugins.DataGenerationFunction") {				
					isDataGenerationFunctionPlugin = true;
				}
				
				if(interfase.getCanonicalName() == "plugins.GeneratorParameters"){
					isGeneratorParametersPlugin = true;
				}
			}

			try {

				if (isMeasurenemtErrorPlugin == true && !clazz.isInterface()) {
					MeasurementError error = (MeasurementError) clazz.newInstance();
					errorsNames.add(error.getErrorName());
					
					pluginClassNames.put(error.getErrorName(), clazz.getCanonicalName());
				}
				
				if (isDataGenerationFunctionPlugin  == true && !clazz.isInterface()) {
					DataGenerationFunction  function = (DataGenerationFunction) clazz.newInstance();
					functionsNames.add(function.getFunctionName());
					
					pluginClassNames.put(function.getFunctionName(),clazz.getCanonicalName());
				}
				
				if (isGeneratorParametersPlugin  == true && !clazz.isInterface()) {
					GeneratorParameters generatorParameters = (GeneratorParameters) clazz.newInstance();
					paramNames.add(generatorParameters.getGeneratorParametersName());
					
					pluginClassNames.put(generatorParameters.getGeneratorParametersName(),clazz.getCanonicalName());
				}

			} catch (ClassCastException e) {
				throw new ClassCastException("Не удалось подключить плагин. Плагин некорректно спроектирован");
			}
			
		}
		
		pluginsNames.put("functions", functionsNames);
		pluginsNames.put("errors", errorsNames);
		pluginsNames.put("genParameters", paramNames);
		
		return pluginsNames;
	}

	public Map<String, String> loadFunctionFields(String functionName) 
			throws ClassNotFoundException, NoSuchMethodException, 
				SecurityException, InstantiationException, IllegalAccessException{

	
		Map<String, String> functionFields = new Hashtable<>();
		if (pluginClassNames.containsKey(functionName)){
			String canonicalClassName = pluginClassNames.get(functionName);
					
			Class clazz = Class.forName(canonicalClassName);
			
			this.function = (DataGenerationFunction) clazz.newInstance();
			
			functionFields = this.function.getFunctionParameters();
		}
		
		return functionFields;
	}

	public Map<String, String> loadErrorFields(String errorName) 
			throws ClassNotFoundException, NoSuchMethodException, 
				SecurityException, InstantiationException, IllegalAccessException{

	
		Map<String, String> errorFields = new Hashtable<>();
		if (pluginClassNames.containsKey(errorName)){
			String canonicalClassName = pluginClassNames.get(errorName);
					
			Class clazz = Class.forName(canonicalClassName);
			
			this.error = (MeasurementError) clazz.newInstance();
			
			errorFields = this.error.getParametersForRendering();
		}
		
		return errorFields;
	}

	public Map<String, String> loadGeneratorParamsFields(String genParamsName) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		Map<String, String> genParamsFields = new Hashtable<>();
		if (pluginClassNames.containsKey(genParamsName)){
			String canonicalClassName = pluginClassNames.get(genParamsName);
					
			Class clazz = Class.forName(canonicalClassName);
			
			this.generatorParameters = (GeneratorParameters) clazz.newInstance();
			
			genParamsFields = this.generatorParameters.getParametersForRendering();
		}
		
		return genParamsFields;
	}

	public DataGenerationFunction getFunctionInstance(Map<String, ? extends Object> parameters){
		this.function.setParameters(parameters);
		return this.function;
	}
	
	public MeasurementError getMeasurementErrorInstance(Map<String, ? extends Object> parameters){
		this.error.setParameters(parameters);
		return this.error;
	}
	
	public GeneratorParameters getGeneratorParametersInstance(Map<String, ? extends Object> parameters){
		this.generatorParameters.setParameters(parameters);
		return this.generatorParameters;
	}
	
}
