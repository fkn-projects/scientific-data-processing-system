package plugins;


import java.util.Map;

public interface Parametrable {

	boolean checkParameters();
	Map<String, String> getParameters();
}
