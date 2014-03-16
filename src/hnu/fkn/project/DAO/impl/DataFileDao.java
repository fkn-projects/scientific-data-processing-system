package hnu.fkn.project.DAO.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import hnu.fkn.project.DAO.DAO;

public class DataFileDao implements DAO {

	@Override
	public void saveData(Map<Number, Number> data, String path) {	
		BufferedWriter out;
		try {
			FileWriter fstream = new FileWriter(path + "values.csv");
			out = new BufferedWriter(fstream);
			int count = 0;
			Iterator<Entry<Number, Number>> it = data.entrySet().iterator();
			while (it.hasNext() && count < data.size()) {
				 Entry<Number, Number> pairs = it.next(); 
				 out.write(pairs.getKey() + " ");
				 out.write(pairs.getValue() + "\n");
				 count++;
			 }
			out.close();			
		} catch (IOException e) {
			System.out.println("Exception - Error with file");
			
		}

	}

	@Override
	public void saveParameters(Map<String, Object> parameters, String path) {
		BufferedWriter out;
		try {
			FileWriter fstream = new FileWriter(path + "values2.xml");
			out = new BufferedWriter(fstream);
			int count = 0;
			Iterator<Entry<String, Object>> it = parameters.entrySet().iterator();
			while (it.hasNext() && count < parameters.size()) {
				 Entry<String, Object> pairs = it.next(); 
				 out.write(pairs.getKey() + " ");
				 out.write(pairs.getValue() + "\n");
				 count++;
			 }
			out.close();			
		} catch (IOException e) {
			System.out.println("Exception - Error with file");
			
		}

	}

	@Override
	public Map<Number, Number> getData(File dataFile) {
		Map<Number,Number> result = new HashMap<Number,Number>();
		try {
			BufferedReader br= new BufferedReader(new BufferedReader(new FileReader(dataFile)));
			String sCurrentLine = "";
            while ((sCurrentLine = br.readLine()) != null) {
                int first = Integer.parseInt(sCurrentLine.substring(0, sCurrentLine.indexOf(" ")));
                int rest =  Integer.parseInt(sCurrentLine.substring(sCurrentLine.indexOf(" ")).trim());
                result.put(first, rest);
            }
		} catch (FileNotFoundException e) {
			System.out.println("Exception - Error with file");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("Exception - Error with file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception - Error with file");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, Object> getParemeters(File parametersFile) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			BufferedReader br= new BufferedReader(new BufferedReader(new FileReader(parametersFile)));
			String sCurrentLine = "";
            while ((sCurrentLine = br.readLine()) != null) {
                String first = sCurrentLine.substring(0, sCurrentLine.indexOf(" "));
                int rest =  Integer.parseInt(sCurrentLine.substring(sCurrentLine.indexOf(" ")).trim());
                result.put(first, rest);
            }
		} catch (FileNotFoundException e) {
			System.out.println("Exception - Error with file");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("Exception - Error with file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception - Error with file");
			e.printStackTrace();
		}
		return result;
	}

}
