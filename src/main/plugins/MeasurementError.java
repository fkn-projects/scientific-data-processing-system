package plugins;

import java.util.Map;



public interface MeasurementError extends Parametrable {

	double getMeasurementError(double y);
	String getErrorName();
	Map<String, String> getErrorParametersForSave();

}
