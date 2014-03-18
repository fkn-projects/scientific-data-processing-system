package plugins;

import java.util.Map;

public interface DataGenerationFunction extends Parametrable {

		double getValue(double x);
		
		String getFunctionName();

		Map<String, String> getFunctionParameters();
}
