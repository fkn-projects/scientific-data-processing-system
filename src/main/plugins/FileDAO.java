package plugins;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.annotation.processing.FilerException;

public interface FileDAO extends Parametrable {
	    //Map<Number, Number> data - набор данных, полученных с генератора <x, f(x)>
		void saveData(Map<Double, Double> data, String path) throws FilerException, IOException;
		
		// 	Map<String, Object> data - набор параметров генератора <"название параметра", "значение параметра">
		void saveParameters(Map<String, Map<String, String>> parameters, String path) throws IOException;
		
		Map<Double, Double> getData(File dataFile) throws FileNotFoundException, IOException;
		
		Map<String,Object> getFunctionParameters(File parametersFile) throws IOException;
		Map<String,Object> getErrorParameters(File parametersFile) throws IOException;
		Map<String,Object> getGeneratorParameters(File parametersFile) throws IOException;
		Map<String,Object> getSaveFormatParameters(File parametersFile) throws IOException;
		
	
		String getFileFormatName();
		
		String getDataFileFilterDescription();
		String getDataFileFilterExtention();
		String getParametersFileFilterDescription();
		String getParametersFileFilterExtention();
		
		String getFunctionNameFromFile();
		String getErrorNameFromFile();
		String getGeneratorParametersNameFromFile();
		
			
		
		
		
}
