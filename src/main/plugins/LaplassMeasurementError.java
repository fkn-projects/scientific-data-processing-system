package plugins;


import java.util.Map;
import java.util.TreeMap;

public class LaplassMeasurementError implements MeasurementError {

	private String errorName;
	private Map<String, String> parameters = new TreeMap<String, String>();
	
	//�������� ���������� ���������� �������������
	
	
	
	public LaplassMeasurementError() {
		this.errorName = "Laplass Error";
	
		//��� ������ �������� ����������. �� ����� �������� �� ���������
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
		// � ���� ������ ��������� ������������� ������ ��������� ��������
		
		return false;
	}

	@Override
	public Map<String, String> getParametersForRendering() {
		return this.parameters;
	}

	@Override
	public double getMeasurementError() {
		// TODO Auto-generated method stub
		// ���� ����� ���������� ��. ��������, �������������� �� ������ �������� 
		
		return 0;
	}

	@Override
	public String getErrorName() {
		return this.errorName;
	}


	@Override
	public void setParameters(Map<String, ? extends Object> parameters) {
		// TODO Auto-generated method stub
		// ��� ��������� ������ ������ ������������������ ����������, ������� "������" � ��������� parameters
		// ��. ���������� ������ BeginStepEnd
	}

}
