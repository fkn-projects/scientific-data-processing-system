package hnu.fkn.project.DAO;

import java.io.File;
import java.util.Map;

public interface DAO {
	    //Map<Number, Number> data - набор данных, полученных с генератора <x, f(x)>
		void saveData(Map<Number, Number> data, String path);
		
		// 	Map<String, Object> data - набор параметров генератора <"название параметра", "значение параметра">
		void saveParameters(Map<String, Object> parameters, String path);
		
		Map<Number, Number> getData(File dataFile);
		
		Map<String, Object> getParemeters(File parametersFile);	
}
