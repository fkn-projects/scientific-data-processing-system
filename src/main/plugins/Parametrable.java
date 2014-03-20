package plugins;


import java.util.Map;

public interface Parametrable {

	//parameters - map, в котором хранятся параметры в формате { <имя параметра>, <значение параметра> }
	void setParameters(Map<String, ? extends Object> parameters);
	
	boolean checkParameters();
	
	Map<String, String> getParametersForRendering();
}
