package plugins;


import java.util.Map;
import java.util.TreeMap;

public class LaplassMeasurementError implements MeasurementError {

	private String errorName;
	private Map<String, String> parameters = new TreeMap<String, String>();
	
	//добавить объявление параметров распределения
	
	
	
	public LaplassMeasurementError() {
		this.errorName = "Laplass Error";
	
		//это список тестовых параметров. Их нужно заменить на настоящие
		this.parameters.put("Sigma", "double");
		this.parameters.put("Alpha", "double");
		this.parameters.put("Beta", "double");
		this.parameters.put("Ksi", "double");
		this.parameters.put("Lambda", "double");
		this.parameters.put("Teta", "double");
	}
	
	
	@Override
	public boolean checkParameters() {
		// TODO Auto-generated method stub
		// в этом методе параметры распределения должны проходить проверку
		
		return false;
	}

	@Override
	public Map<String, String> getParametersForRendering() {
		return this.parameters;
	}

	@Override
	public double getMeasurementError() {
		// TODO Auto-generated method stub
		// этот метод возвращает сл. величину, распределенную по закону Лапласса 
		
		return 0;
	}

	@Override
	public String getErrorName() {
		return this.errorName;
	}


	@Override
	public void setParameters(Map<String, ? extends Object> parameters) {
		// TODO Auto-generated method stub
		// тут параметры класса должны инициализироваться значениями, которые "пришли" в коллекции parameters
		// см. реализацию класса BeginStepEnd
	}

}
