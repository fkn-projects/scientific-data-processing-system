package hnu.fkn.project.DAO;

import java.io.File;
import java.util.Map;

public interface DAO {
	    //Map<Number, Number> data - ����� ������, ���������� � ���������� <x, f(x)>
		void saveData(Map<Number, Number> data, String path);
		
		// 	Map<String, Object> data - ����� ���������� ���������� <"�������� ���������", "�������� ���������">
		void saveParameters(Map<String, Object> parameters, String path);
		
		Map<Number, Number> getData(File dataFile);
		
		Map<String, Object> getParemeters(File parametersFile);	
}
