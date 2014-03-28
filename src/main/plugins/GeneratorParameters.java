package plugins;

import java.util.Map;


public interface GeneratorParameters extends Parametrable{

	double getNextX();
	boolean hasNextX();
	String getGeneratorParametersName();
	Map<String, String> getGeneratorParametersForSave();
	
}
