package plugins;

import java.util.Map;

public interface GeneratorParameters extends Parametrable{

	void initialize(Map<String, ? extends Object> parameters);
	double getNextX();
	boolean hasNextX();
	String getGeneratorParametersName();
	
}
