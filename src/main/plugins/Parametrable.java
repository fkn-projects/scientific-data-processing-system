package plugins;


import java.util.Map;

public interface Parametrable {

	//parameters - map, � ������� �������� ��������� � ������� { <��� ���������>, <�������� ���������> }
	void setParameters(Map<String, ? extends Object> parameters);
	
	boolean checkParameters();
	
	Map<String, String> getParametersForRendering();
}
