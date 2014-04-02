package plugins;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.annotation.processing.FilerException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class XmlCsvFileDAO implements FileDAO {

	private String fileFormatName;
	private String csvDataSeparator;
	
	private String functionNameFromFile;
	private String errorNameFromFile;
	private String generatorParametersNameFromFile;
	
	private Map<String, String> parametersNames = new HashMap<String, String>();
	private List<String> csvSeparators = new ArrayList<>();
	private String pathToGeneratedData;
	
	
	public XmlCsvFileDAO() {

		fileFormatName = "XML-CSV format";
		parametersNames.put("CSV_separator", ";");
		
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		char separator = dfs.getDecimalSeparator();
		if (separator == ',') {
			csvSeparators.add(" \",\" ");
		} else if (separator == '.') {
			csvSeparators.add(" \".\" ");
		}
		csvSeparators.add(" \":\" ");
		csvSeparators.add(" \";\" ");
	}

	@Override
	public void saveData(Map<Double, Double> data, String path)
			throws IOException {
		BufferedWriter out;

		if (!path.endsWith(".csv")) {
			throw new FilerException(
					"Wrong file format. Please, type correct file extention");
		}

		try {

			FileWriter fstream = new FileWriter(path);
			out = new BufferedWriter(fstream);
			int count = 0;
			Iterator<Entry<Double, Double>> it = data.entrySet().iterator();
			while (it.hasNext() && count < data.size()) {
				Entry<Double, Double> pairs = it.next();

				out.write(pairs.getKey() + this.csvDataSeparator);
				out.write(pairs.getValue() + "\n");
				count++;
			}
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("Cann't write data to file");
		}

	}

	@Override
	public void saveParameters(Map<String, Map<String, String>> parameters,
			String path) throws IOException {
		BufferedWriter out;
		
		String dataPath = path;
		path = path.replaceAll(".csv", ".xml");

		try {
//			FileWriter fstream = new FileWriter(path);
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
			int lineCount = 0;

			Map<String, String> functionParameters = parameters.get("function");
			Map<String, String> errorParameters = parameters.get("error");
			Map<String, String> genParameters = parameters.get("generator");

			Map<String, String> saveFormatParameters = new TreeMap<>();
			saveFormatParameters.put("csv-separator type=\"String\"",
					csvDataSeparator);
			
			Date d = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd.MM.yyyy HH:mm");
			

			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
			out.write("<parameters>");
			out.write("\n");
			out.write("\t<date>" + dateFormat.format(d) + "</date>\n");
			
			out.write("\t<function>\n");
			lineCount = writeParameters(functionParameters, out, lineCount);
			out.write("\t</function>\n");

			lineCount = 0;
			out.write("\t<error>\n");
			writeParameters(errorParameters, out, lineCount);
			out.write("\t</error>\n");

			lineCount = 0;
			out.write("\t<generator-parameters>\n");
			writeParameters(genParameters, out, lineCount);
			out.write("\t</generator-parameters>\n");

			lineCount = 0;
			out.write("\t<save-format-parameters>\n");
			writeParameters(saveFormatParameters, out, lineCount);
			out.write("\t</save-format-parameters>\n");
			out.write("<generator-data>"+ dataPath +"</generator-data>");
			out.write("</parameters>\n");
			
			out.close();

		} catch (IOException e) {
			throw new IOException("Exception - Error with file");

		}

	}

	private int writeParameters(Map<String, String> parameters,
			BufferedWriter out, int lineCount) throws IOException {
		Iterator<Entry<String, String>> it = parameters.entrySet().iterator();
		while (it.hasNext() && lineCount < parameters.size()) {
			Entry<String, String> pairs = it.next();
			out.write("\t\t<" + pairs.getKey() + ">");
			out.write(pairs.getValue());
			out.write("</" + pairs.getKey().split(" ")[0] + ">");
			out.write("\n");
			lineCount++;
		}
		return lineCount;
	}

	
	@Override
	public Map<Double, Double> getData(File dataFile) throws IOException {
		Map<Double, Double> result = new TreeMap<Double, Double>();
		
		if(dataFile == null){
			if(!pathToGeneratedData.equals("")){
				dataFile = new File(pathToGeneratedData);
			} else{
				throw new RuntimeException("Cann't open CSV file with data. File not found. \nPerhaps XML-file corrupted");
			}
		}
		
		
		try {
			BufferedReader br = new BufferedReader(new BufferedReader(
					new FileReader(dataFile)));
			String sCurrentLine = "";
			String[] xy = new String[2];
			while ((sCurrentLine = br.readLine()) != null) {
				xy = sCurrentLine.split(csvDataSeparator);
				result.put(Double.parseDouble(xy[0]), Double.parseDouble(xy[1]));
			}
			br.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Cann't get data from file. Data file is missing");
		} catch (NumberFormatException e) {
			throw new RuntimeException("Unable to convert the data. Error in the CSV-file");
			//e.printStackTrace();
		} catch (IOException e) {
			throw new IOException("Cann't get data from file. Error with data file");
		}
		return result;
	}

	public void setParameters(Map<String, ? extends Object> parameters) {

		Object tempObject = parameters.get("CSV_separator");

		if (tempObject instanceof String) {
			this.csvDataSeparator = (String) parameters.get("CSV_separator");
			if (!csvSeparators.contains(" \"" + this.csvDataSeparator + "\" ")) {
				throw new RuntimeException(
						"Parameter \"CSV_separator\" isn't valid. "
								+ "\n Please, set one of the following symbols - "
								+ csvSeparators);
			}

		} else if (tempObject == null) {
			throw new RuntimeException(
					"Set parameter \"CSV_separator\", please");
		} else {
			throw new ClassCastException(
					"Parameter \"CSV_separator\" isn't valid");
		}

	}

	@Override
	public boolean checkParameters() {
		return true;
	}

	@Override
	public Map<String, String> getParametersForRendering() {
		return parametersNames;
	}

	@Override
	public String getFileFormatName() {
		return fileFormatName;
	}

	
	//FileFormat Filters
	@Override
	public String getDataFileFilterDescription() {
		return "Comma-Separated Values";
	}

	@Override
	public String getDataFileFilterExtention() {
		return ".csv";
	}

	@Override
	public String getParametersFileFilterDescription() {
		return "eXtensible Markup Language";
	}

	@Override
	public String getParametersFileFilterExtention() {
		return ".xml";
	}
	
	//Getting plugin's parameters
	@Override
	public Map<String, Object> getFunctionParameters(File parametersFile)
			/*throws FileNotFoundException, IOException*/ {

		Map<String, Object> functionParameters = new TreeMap<>();
		
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(parametersFile.getAbsolutePath()));
			Element rootElement = document.getDocumentElement();
			NodeList nodes = rootElement.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getChildNodes().getLength() > 1 && node instanceof Element){
					Element plugin = (Element) node;
					
					if (plugin.getTagName() == "function") {
						functionParameters = getParametersMapFromDOMElement(plugin);
						this.functionNameFromFile = (String) functionParameters.get("name");
					}
				}					
			}
			
		} catch (SAXException e) {
			throw new RuntimeException("Unable to convert the data. Error in XML-file");
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Unable to convert the data. Error in XML-file");
		} catch (IOException e) {
			throw new RuntimeException("Unable to convert the data. Error in XML-file");
		}
		
		return functionParameters;
	}
	
	@Override
	public Map<String, Object> getErrorParameters(File parametersFile) /*throws IOException */{
		
		Map<String, Object> errorParameters = new TreeMap<>();
		
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(parametersFile.getAbsolutePath()));
			Element rootElement = document.getDocumentElement();
			NodeList nodes = rootElement.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getChildNodes().getLength() > 1 && node instanceof Element){
					Element plugin = (Element) node;
					
					if (plugin.getTagName() == "error") {
						errorParameters = getParametersMapFromDOMElement(plugin);
						this.errorNameFromFile = (String) errorParameters.get("name");

					}
				}					
			}
			
		} catch (SAXException e) {
			throw new RuntimeException("Unable to convert the data. Error in XML-file");
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Unable to convert the data. Error in XML-file");
		} catch (IOException e) {
			throw new RuntimeException("Unable to convert the data. Error in XML-file");
		}
		
		return errorParameters;
	}
	
	@Override
	public Map<String, Object> getGeneratorParameters(File parametersFile) /*throws IOException*/ {

		Map<String, Object> generatorParameters = new TreeMap<>();
		
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(parametersFile.getAbsolutePath()));
			Element rootElement = document.getDocumentElement();
			NodeList nodes = rootElement.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getChildNodes().getLength() > 1 && node instanceof Element){
					Element plugin = (Element) node;
					
					if (plugin.getTagName() == "generator-parameters") {
						generatorParameters = getParametersMapFromDOMElement(plugin);
						this.generatorParametersNameFromFile = (String) generatorParameters.get("name");

					}
				}					
			}
			
		} catch (SAXException e) {
			throw new RuntimeException("Unable to convert the data. Error in XML-file");
		} catch (IOException e) {
			throw new RuntimeException("Unable to convert the data. Error in XML-file");
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Unable to convert the data. Error in XML-file");	
		}
		
		return generatorParameters;
	}
		
	@Override
	public Map<String, Object> getSaveFormatParameters(File parametersFile) /*throws IOException*/ {
		
		Map<String, Object> saveFormatParameters = new TreeMap<>();
		
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(parametersFile.getAbsolutePath()));
			Element rootElement = document.getDocumentElement();
			NodeList nodes = rootElement.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getChildNodes().getLength() > 1 && node instanceof Element){
					Element plugin = (Element) node;
					
					if (plugin.getTagName() == "save-format-parameters") {
						saveFormatParameters = getParametersMapFromDOMElement(plugin);
						csvDataSeparator = (String) saveFormatParameters.get("csv-separator");
					}
				} else if(node instanceof Element && node.getChildNodes().getLength() == 1){
					Element pathNode = (Element) node;
					if(pathNode.getTagName() == "generator-data"){
						saveFormatParameters.put(pathNode.getTagName(), pathNode.getTextContent());
						this.pathToGeneratedData = pathNode.getTextContent();
					}
					
				}
			}
			
		} catch (SAXException e) {
			throw new RuntimeException("Unable to convert the data. Error in XML-file");
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Unable to convert the data. Error in XML-file");
		} catch (IOException e) {
			throw new RuntimeException("Unable to convert the data. Error in XML-file");
		}
		
		return saveFormatParameters;
	}

	private Map<String,Object> getParametersMapFromDOMElement(Element plugin){

		Map<String,Object> parameters = new TreeMap<>();
		
		NodeList pluginsParameters = plugin.getChildNodes();
		for (int j = 0; j < pluginsParameters.getLength(); j++) {
			Node parameterNode = pluginsParameters.item(j);

			if (parameterNode.getChildNodes().getLength() == 1 && parameterNode instanceof Element){
				Element parameter = (Element) parameterNode;
				parameters.put(parameter.getTagName(), parameter.getTextContent());
			}						
		}
		return parameters;
	}

	//Get plugin's names
	@Override
	public String getFunctionNameFromFile() {
		return this.functionNameFromFile;
	}

	@Override
	public String getErrorNameFromFile() {
		return this.errorNameFromFile;
	}

	@Override
	public String getGeneratorParametersNameFromFile() {
		return this.generatorParametersNameFromFile;
	}

	
	

}
