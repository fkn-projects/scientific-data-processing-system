package hnu.fkn.project.GUI;

import hnu.fkn.project.generator.DataGenerator;
import hnu.fkn.project.reflection.ReflectionObject;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.processing.FilerException;
import javax.swing.CellEditor;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JButton;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.Legend;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import plugins.DataGenerationFunction;
import plugins.FileDAO;
import plugins.GeneratorParameters;
import plugins.MeasurementError;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

	private JFrame frame;
	private JComboBox functionsComboBox;
	private JComboBox errorsComboBox; 
	private JComboBox genParamsComboBox;
	private JComboBox saveFileFormatComboBox;
	private JComboBox openFileFormatComboBox;
	
	private ReflectionObject reflection;
	private DataGenerator dataGenerator;
	
	private JTable functionParametersTable;
	private JTable errorParametersTable;
	private JTable genParametersTable; 
	private JTable saveParametersTable;
	
	
	
	private JLabel lblFunctionParameters;
	private JLabel lblErrorParameters;
	private JLabel lblGeneratorParameters;
	
	private JScrollPane scrollPaneF;
	private JScrollPane scrollPaneErr;
	private JScrollPane scrollPaneGen;
	private JScrollPane scrollPaneSave;
	
	private JButton btnGenerateData;
	
	private JPanel panel;
	private JPanel chart_panel;
	private JButton btnNewButton;
	private JPanel save_panel;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());

					Main window = new Main();
					window.frame.setVisible(true);
					window.frame.setTitle("Tikhonov Regularization - Generator");
				} catch (Exception e) {
					//System.out.println(e.getMessage());
				}
			}
		});
	}

	public Main() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 630, 535);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(3, 3, 152, 501);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblSelectFunction = new JLabel("Select function");
		lblSelectFunction.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectFunction.setBounds(7, 7, 136, 14);
		panel.add(lblSelectFunction);
		
		functionsComboBox = new JComboBox();
		functionsComboBox.setBackground(SystemColor.control);
		
		functionsComboBox.setBounds(7, 25, 136, 20);
		panel.add(functionsComboBox);
		
		
		JLabel lblSelectMeasurementError = new JLabel("Select error");
		lblSelectMeasurementError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectMeasurementError.setBounds(7, 162, 136, 14);
		panel.add(lblSelectMeasurementError);
		
		errorsComboBox = new JComboBox();
		errorsComboBox.setBackground(SystemColor.control);
		errorsComboBox.setBounds(7, 180, 136, 20);
		panel.add(errorsComboBox);
		
		btnGenerateData = new JButton("Generate Data");
		
		
		btnGenerateData.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGenerateData.setBounds(7, 467, 136, 23);
		panel.add(btnGenerateData);
		
		JLabel lblSelectInputMethod = new JLabel("Select  generator parameters");
		lblSelectInputMethod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectInputMethod.setBounds(7, 314, 136, 14);
		panel.add(lblSelectInputMethod);
		
		genParamsComboBox = new JComboBox();
		genParamsComboBox.setBounds(7, 333, 136, 20);
		panel.add(genParamsComboBox);
		
		chart_panel = new JPanel();
		chart_panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		chart_panel.setBounds(158, 3, 464, 393);
		frame.getContentPane().add(chart_panel);
		
		
		reflection = new ReflectionObject();
		
		functionsComboBox.addItem("Select ...");
		errorsComboBox.addItem("Select ...");
		genParamsComboBox.addItem("Select ...");
		
		save_panel = new JPanel();
		save_panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		save_panel.setBounds(158, 398, 211, 106);
		frame.getContentPane().add(save_panel);
		save_panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select save file format");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 8, 136, 14);
		save_panel.add(lblNewLabel);
		
		saveFileFormatComboBox = new JComboBox();
		saveFileFormatComboBox.setBounds(10, 28, 127, 21);
		save_panel.add(saveFileFormatComboBox);
		saveFileFormatComboBox.addItem("Select ...");
		
		
		btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		btnNewButton.setBounds(140, 27, 61, 23);
		save_panel.add(btnNewButton);
		
		JPanel open_panel = new JPanel();
		open_panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		open_panel.setBounds(371, 398, 251, 106);
		frame.getContentPane().add(open_panel);
		open_panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Select open file format");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 8, 231, 14);
		open_panel.add(lblNewLabel_1);
		
		JButton btnOpenFile = new JButton("Open File");
		
		btnOpenFile.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnOpenFile.setBounds(152, 27, 89, 23);
		open_panel.add(btnOpenFile);
		
		openFileFormatComboBox = new JComboBox();
		openFileFormatComboBox.setBounds(10, 28, 138, 21);
		openFileFormatComboBox.addItem("Select ...");
		open_panel.add(openFileFormatComboBox);
		
		
		
		
		//загрузка всех плагинов
		Map<String, List<String>> pluginNames = null;
		try {
			pluginNames = reflection.loadPluginsNames();

			for (Map.Entry<String, List<String>> entry : pluginNames.entrySet()) {
				
				if (entry.getKey() == "functions") {
					List<String> functions = entry.getValue();
					for (String functionName : functions) {
						functionsComboBox.addItem(functionName);
					}
				}
				if (entry.getKey() == "errors") {
					List<String> errors = entry.getValue();
					for (String errorName : errors) {
						errorsComboBox.addItem(errorName);
					}
				}
				if (entry.getKey() == "genParameters") {
					List<String> genParams = entry.getValue();
					for (String genParamName : genParams) {
						genParamsComboBox.addItem(genParamName);
					}
				}

				if (entry.getKey() == "fileFormats") {
					List<String> fileFormats = entry.getValue();
					for (String fileFormatsName : fileFormats) {
						saveFileFormatComboBox.addItem(fileFormatsName);
						openFileFormatComboBox.addItem(fileFormatsName);
					}
				}
			}			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			////System.out.println(e.getMessage());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			////System.out.println(e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			////System.out.println(e.getMessage());
		} catch (FileNotFoundException e1){
			////System.out.println("Not one plugin was not found. The system can not continue its work");
		} catch (ClassCastException e){
			////System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
		}

	
		
		
		//обработчик выбора функции
		functionsComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				Map<String, String> functionParameters = null;
				try {
					//получение параметров функции
					functionParameters = reflection.loadFunctionFields(functionsComboBox.getSelectedItem().toString());
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
								
				if(functionParameters != null){				
					
					if(scrollPaneF != null && functionParametersTable != null && scrollPaneF.getParent() == panel){

						panel.remove(scrollPaneF);
						panel.remove(functionParametersTable);
						panel.revalidate();
						panel.repaint();
						
						scrollPaneF = null;
						functionParametersTable = null;
					}
					
					if(lblFunctionParameters != null && lblFunctionParameters.getParent() == panel){
						
						panel.remove(lblFunctionParameters);
						panel.revalidate();
						panel.repaint();
						lblFunctionParameters = null;
					}

					if(!functionsComboBox.getSelectedItem().toString().equals("Select ...")){	
					
						lblFunctionParameters = new JLabel("Function Parameters");
						
						lblFunctionParameters.setBounds(7, 47, 136, 14);
						panel.add(lblFunctionParameters);
						
						panel.revalidate();
						panel.repaint();
						
						functionParametersTable = new JTable();
						functionParametersTable.setForeground(SystemColor.infoText);
						functionParametersTable.setBorder(new LineBorder(SystemColor.textInactiveText));
						functionParametersTable.setModel(new DefaultTableModel(new Object[]{"", ""}, 0) {
							Class[] columnTypes = new Class[] {
									String.class, Double.class
							};
							public Class getColumnClass(int columnIndex) {
								return columnTypes[columnIndex];
							}
							
							boolean[] columnEditables = new boolean[] {
								false, true
							};
							public boolean isCellEditable(int row, int column) {
								return columnEditables[column];
							}
						});
	
						functionParametersTable.setSelectionBackground(SystemColor.controlHighlight);
						functionParametersTable.setSelectionForeground(SystemColor.black);
	
						DefaultTableModel model = (DefaultTableModel) functionParametersTable.getModel();
						
						functionParametersTable.setTableHeader(null);
						scrollPaneF = new JScrollPane(functionParametersTable,
						           JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						           JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					    scrollPaneF.setBounds(7, 65, 136, 0);
						
						for (Map.Entry<String, String> parameters : functionParameters.entrySet()) {
							if(scrollPaneF.getHeight() < 80){
								scrollPaneF.setSize(scrollPaneF.getWidth(), scrollPaneF.getHeight()+16);
							} 
							model.addRow(new Object[]{" "+parameters.getKey(), Double.parseDouble(parameters.getValue())});					
						}
						panel.add(scrollPaneF);
						panel.revalidate();
						panel.repaint();
					}
					
				} else {
					if(scrollPaneF != null && functionParametersTable != null && scrollPaneF.getParent() == panel){
						panel.remove(scrollPaneF);
						panel.remove(functionParametersTable);
						panel.revalidate();
						panel.repaint();
						scrollPaneF = null;
						functionParametersTable = null;
					}
					
					if(lblFunctionParameters != null && lblFunctionParameters.getParent() == panel){
						panel.remove(lblFunctionParameters);
						panel.revalidate();
						panel.repaint();
						lblFunctionParameters = null;
					}
				}
			}
		});
		
		//обработчик выбора ошибки
		errorsComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Map<String, String> errorParameters = null;
				try {
					errorParameters = reflection.loadErrorFields(errorsComboBox.getSelectedItem().toString());
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
		
				if(errorParameters != null){
					
					if(scrollPaneErr != null && errorParametersTable != null && scrollPaneErr.getParent() == panel){
						
						panel.remove(scrollPaneErr);
						panel.remove(errorParametersTable);
						panel.revalidate();
						panel.repaint();
						
						scrollPaneErr = null;
						errorParametersTable = null;
					}
					
					if(lblErrorParameters != null && lblErrorParameters.getParent() == panel){
						
						panel.remove(lblErrorParameters);
						panel.revalidate();
						panel.repaint();
						lblErrorParameters = null;
					}
					
					if(!errorsComboBox.getSelectedItem().toString().equals("Select ...")){	
					
						lblErrorParameters = new JLabel("Error Parameters");
								
						lblErrorParameters.setBounds(7, 205, 136, 14);
						panel.add(lblErrorParameters);
						
						panel.revalidate();
						panel.repaint();
						
						errorParametersTable = new JTable();
						errorParametersTable.setForeground(SystemColor.infoText);
						errorParametersTable.setBorder(new LineBorder(SystemColor.textInactiveText));
						errorParametersTable.setModel(new DefaultTableModel(new Object[]{"Column1", "Column2"}, 0) {
							Class[] columnTypes = new Class[] {
									String.class, Double.class
							};
							public Class getColumnClass(int columnIndex) {
								return columnTypes[columnIndex];
							}
							
							boolean[] columnEditables = new boolean[] {
								false, true
							};
							public boolean isCellEditable(int row, int column) {
								return columnEditables[column];
							}
						});
	
						errorParametersTable.setSelectionBackground(SystemColor.controlHighlight);
						errorParametersTable.setSelectionForeground(SystemColor.black);
						
						DefaultTableModel model = (DefaultTableModel) errorParametersTable.getModel();
	
						errorParametersTable.setTableHeader(null);
						scrollPaneErr = new JScrollPane(errorParametersTable,
						           JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						           JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					    scrollPaneErr.setBounds(7, 220, 136, 0);
						
						for (Map.Entry<String, String> parameters : errorParameters.entrySet()) {
							
							if(scrollPaneErr.getHeight() < 80){
								scrollPaneErr.setSize(scrollPaneErr.getWidth(), scrollPaneErr.getHeight()+16);
							} 
							model.addRow(new Object[]{" "+parameters.getKey(), Double.parseDouble(parameters.getValue())});					
						}
						
						panel.add(scrollPaneErr);
						panel.revalidate();
						panel.repaint();
					}					
					
					
				} else {
					if(scrollPaneErr != null && errorParametersTable != null && scrollPaneErr.getParent() == panel){
						panel.remove(scrollPaneErr);
						panel.remove(errorParametersTable);
						panel.revalidate();
						panel.repaint();
						errorParametersTable = null;
						scrollPaneErr = null;
					}
					
					if(lblErrorParameters != null && lblErrorParameters.getParent() == panel){
						panel.remove(lblErrorParameters);
						panel.revalidate();
						panel.repaint();
						lblErrorParameters = null;
					}
				}
			}
		});

		//обработчик выбора параметров генератора
		genParamsComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Map<String, String> genParameters = null;
				try {
					genParameters = reflection.loadGeneratorParamsFields(genParamsComboBox.getSelectedItem().toString());
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		
				if(genParameters != null){
					
					if(scrollPaneGen != null && genParametersTable != null && scrollPaneGen.getParent() == panel){
						
						panel.remove(scrollPaneGen);
						panel.remove(genParametersTable);
						panel.revalidate();
						panel.repaint();
						
						genParametersTable = null;
						scrollPaneGen = null;
					}
					
					if(lblGeneratorParameters != null && lblGeneratorParameters.getParent() == panel){
						
						panel.remove(lblGeneratorParameters);
						panel.revalidate();
						panel.repaint();
						lblGeneratorParameters = null;
					}

					if(!genParamsComboBox.getSelectedItem().toString().equals("Select ...")){	
									
						lblGeneratorParameters = new JLabel("Generetor Parameters");
						lblGeneratorParameters.setBounds(7, 357, 136, 14);
						panel.add(lblGeneratorParameters);
						
						genParametersTable = new JTable();
						genParametersTable.setForeground(SystemColor.infoText);
						genParametersTable.setBorder(new LineBorder(SystemColor.textInactiveText));
											
						genParametersTable.setModel(new DefaultTableModel(new Object[]{"Column1", "Column2"}, 0) {
							Class[] columnTypes = new Class[] {
									String.class, Double.class
							};
							public Class getColumnClass(int columnIndex) {
								return columnTypes[columnIndex];
							}
							
							boolean[] columnEditables = new boolean[] {
								false, true
							};
							public boolean isCellEditable(int row, int column) {
								return columnEditables[column];
							}
						});
	
						genParametersTable.setSelectionBackground(SystemColor.controlHighlight);
						genParametersTable.setSelectionForeground(SystemColor.black);
	
						DefaultTableModel model = (DefaultTableModel) genParametersTable.getModel();
	
						genParametersTable.setTableHeader(null);
						scrollPaneGen = new JScrollPane(genParametersTable,
						           JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						           JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					    scrollPaneGen.setBounds(7, 375, 136, 0);
						
						for (Map.Entry<String, String> parameters : genParameters.entrySet()) {
							
							if(scrollPaneGen.getHeight() < 80){
								scrollPaneGen.setSize(scrollPaneGen.getWidth(), scrollPaneGen.getHeight()+16);
							} 
							model.addRow(new Object[]{" "+parameters.getKey(), Double.parseDouble(parameters.getValue())});					
						}
						
						panel.add(scrollPaneGen);
						panel.revalidate();
						panel.repaint();
					}
					
				} else {
					if(scrollPaneGen != null && genParametersTable != null && scrollPaneGen.getParent() == panel){
						panel.remove(genParametersTable);
						panel.remove(scrollPaneGen);
						panel.revalidate();
						panel.repaint();
						genParametersTable = null;
						scrollPaneGen = null;
					}
					
					if(lblGeneratorParameters != null && lblGeneratorParameters.getParent() == panel){
						panel.remove(lblGeneratorParameters);
						panel.revalidate();
						panel.repaint();
						lblGeneratorParameters = null;
					}
				}
			}
		});
		
		
		//обработчик выбора типа файлов для сохранения
		saveFileFormatComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Map<String, String> saveParameters = null;
				try {
					saveParameters = reflection.loadSaveParametersFields(saveFileFormatComboBox.getSelectedItem().toString());
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
				if(saveParameters != null){
					
					if(scrollPaneSave != null && saveParametersTable != null && scrollPaneSave.getParent() == save_panel){
						
						save_panel.remove(scrollPaneSave);
						save_panel.remove(saveParametersTable);
						save_panel.revalidate();
						save_panel.repaint();
						
						saveParametersTable = null;
						scrollPaneSave = null;
					}
					
					if(!saveFileFormatComboBox.getSelectedItem().toString().equals("Select ...")){	
						
						saveParametersTable = new JTable();
						saveParametersTable.setForeground(SystemColor.infoText);
						saveParametersTable.setBorder(new LineBorder(SystemColor.textInactiveText));
						saveParametersTable.setModel(new DefaultTableModel(new Object[]{"Column1", "Column2"}, 0) {
							Class[] columnTypes = new Class[] {
									String.class, String.class
							};
							public Class getColumnClass(int columnIndex) {
								return columnTypes[columnIndex];
							}
							
							boolean[] columnEditables = new boolean[] {
								false, true
							};
							public boolean isCellEditable(int row, int column) {
								return columnEditables[column];
							}
						});
						
						saveParametersTable.getColumnModel().getColumn(0).setPreferredWidth(240);
						saveParametersTable.setSelectionBackground(SystemColor.controlHighlight);
						saveParametersTable.setSelectionForeground(SystemColor.black);
						
	
						DefaultTableModel model = (DefaultTableModel) saveParametersTable.getModel();
						
						saveParametersTable.setTableHeader(null);
						scrollPaneSave = new JScrollPane(saveParametersTable,
						           JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						           JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					    scrollPaneSave.setBounds(10, 52, 189, 0);
						for (Map.Entry<String, String> parameters : saveParameters.entrySet()) {
							
							if(scrollPaneSave.getHeight() < 54){
								scrollPaneSave.setSize(scrollPaneSave.getWidth(), scrollPaneSave.getHeight()+17);
							} 
						
							model.addRow(new Object[]{" "+parameters.getKey(), parameters.getValue()});					

							if(saveParameters.size()==1){
								scrollPaneSave.setSize(scrollPaneSave.getWidth(), scrollPaneSave.getHeight()+17);
								
								//model.addRow(new Object[]{,});					
							}
						
						}
						
						
						save_panel.add(scrollPaneSave);
						save_panel.revalidate();
						save_panel.repaint();
					}
					
					
				} else {
					if(scrollPaneSave != null && saveParametersTable != null && scrollPaneSave.getParent() == save_panel){
						save_panel.remove(saveParametersTable);
						save_panel.remove(scrollPaneSave);
						save_panel.revalidate();
						save_panel.repaint();
						saveParametersTable = null;
						scrollPaneSave = null;
					}
				}
			}
		});
	
		//обработчик выбора типа файла для открытия
		openFileFormatComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					reflection.loadOpenParametersFields(openFileFormatComboBox.getSelectedItem().toString());
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});
		
		
		
		//обработчик нажатия кнопки Generate
		btnGenerateData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				
				
				try{
					generateDataHandler();
				} catch (Exception ex){
					MessageDialogWindow.showErrorWindow(ex.getMessage(), "Error Message", frame);
				} 
			}
		});
		
		//обработчик кнопки Save
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try{
					saveDataToFile();
				} catch (Exception ex){
					MessageDialogWindow.showErrorWindow(ex.getMessage(), "Error Message", frame);
				}
				
			}
		});
		
		//обработчик кнопки Open File
		btnOpenFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					openDataFile();
				} catch (Exception ex){
					//MessageDialogWindow.showErrorWindow(ex.getMessage(), "Error Message", frame);
					ex.printStackTrace();
				}
			}
		});
		
		
	}	

	private void saveDataToFile(){
		//получаем объект dao
		
		if(saveParametersTable == null){
			throw new RuntimeException("Select save file format, please!");
		}
		
		stopEditing(saveParametersTable);
		
		Map<String, Object> daoParameters = new TreeMap<String, Object>();
		
		for (int count = 0; count < saveParametersTable.getModel().getRowCount(); count++) {
			daoParameters.put(saveParametersTable.getModel().getValueAt(count, 0).toString().replace(" ", ""), 
									saveParametersTable.getModel().getValueAt(count, 1));
		}		
		FileDAO dao = reflection.getFileDAOInstance(daoParameters);
		if(!dao.checkParameters()){
			throw new RuntimeException("File format parameters is not valid. \nPlease, set correct parameters!");
		}
		
		
		if(dataGenerator != null){
		
			Date d = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd.MM.yyyy HH.mm");

			JFileChooser fileChooser = new JFileChooser();
			
			FileFilter filter = new FileTypeFilter(dao.getDataFileFilterExtention(), dao.getDataFileFilterDescription());
			fileChooser.addChoosableFileFilter(filter);
			fileChooser.setFileFilter(filter);
			fileChooser.setSelectedFile( new File("generator["+ dateFormat.format(d) + "]" + dao.getDataFileFilterExtention()));
			
			
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			
				File file = fileChooser.getSelectedFile();
				String path = file.getAbsolutePath();
				
				Map<Double, Double> dataForSave = dataGenerator.getData();
				Map<String, Map<String, String>> parametersForSave = dataGenerator.getParametersForSave();
				
				try {
					dao.saveData(dataForSave, path);
					dao.saveParameters(parametersForSave, path);
				} catch (FilerException e) {
					////System.out.println(e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					////System.out.println(e.getMessage());
				}
				
			}
		} else {
			throw new RuntimeException("There are no data to saving");
		}		
	}
	
	private void generateDataHandler(){
		
		
		
		if(functionParametersTable == null || errorParametersTable == null || genParametersTable == null){
			throw new RuntimeException("Select function, error and generator parameters, please");
		}
		
		stopEditing(genParametersTable);
		stopEditing(errorParametersTable);
		stopEditing(functionParametersTable);
		
		//получаем объект функции-генеретора
		Map<String, Object> functionParameters = new TreeMap<String, Object>();
		for (int count = 0; count < functionParametersTable.getModel().getRowCount(); count++) {
			try{
			functionParameters.put(functionParametersTable.getModel().getValueAt(count, 0).toString().replace(" ", ""), 
									functionParametersTable.getModel().getValueAt(count, 1).toString());
			} catch(NullPointerException ex){
				throw new RuntimeException("Set function parameters, please");
			}
		}		
		DataGenerationFunction dataGenerationFunction = reflection.getFunctionInstance(functionParameters);
		
		if(!dataGenerationFunction.checkParameters()){
			throw new RuntimeException("Data generation function parameters is not valid. \nPlease, set correct parameters!");
		}
		
		//получаем объект погрешности
		Map<String, Object> errorParameters = new TreeMap<String, Object>();
		for (int count = 0; count < errorParametersTable.getModel().getRowCount(); count++) {
			try{
			errorParameters.put(errorParametersTable.getModel().getValueAt(count, 0).toString().replace(" ", ""), 
									errorParametersTable.getModel().getValueAt(count, 1).toString());
			} catch(NullPointerException ex){
				throw new RuntimeException("Set error parameters, please");
			}			
		}
		MeasurementError measurementError = reflection.getMeasurementErrorInstance(errorParameters);
		
		if(!measurementError.checkParameters()){
			throw new RuntimeException("Measurement error parameters is not valid. \nPlease, set correct parameters!");
		}
		
		//получаем объект параметров генератора
		Map<String, Object> genParameters = new TreeMap<String, Object>();
		for (int count = 0; count < genParametersTable.getModel().getRowCount(); count++) {
			try{
			genParameters.put( genParametersTable.getModel().getValueAt(count, 0).toString().replace(" ", ""), 
								genParametersTable.getModel().getValueAt(count, 1).toString());
			} catch(NullPointerException ex){
				throw new RuntimeException("Set generator parameters, please");
			}
		}			
		GeneratorParameters generatorParameters =  reflection.getGeneratorParametersInstance(genParameters);
		if(!generatorParameters.checkParameters()){
			throw new RuntimeException("Generator parameters is not valid. \nPlease, set correct parameters!");
		}
		
		//создаем генератор
		dataGenerator = new DataGenerator(dataGenerationFunction, measurementError, generatorParameters);
		dataGenerator.generateData();
		
		//получаем "чистые" данные и данные с погрешностью
		Map<Double, Double> origDataForChart = dataGenerator.getOriginalData();
		Map<Double, Double> dataForChart = dataGenerator.getData();
		
		//строим график из полученных данных
		buildChart(dataForChart, origDataForChart);
			
	}

	private void openDataFile(){
		
		if(openFileFormatComboBox.getSelectedIndex() == 0){
			throw new RuntimeException("Select open file format, please!");
		}
		
		JFileChooser fileChooser = new JFileChooser();
		FileDAO dao = reflection.getFileDAOInstance(null);
		
		FileFilter filter = new FileTypeFilter(dao.getParametersFileFilterExtention(), dao.getParametersFileFilterDescription());
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setFileFilter(filter);
		
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		
			File file = fileChooser.getSelectedFile();
			String path = file.getAbsolutePath();
			
			Map<String, Object> functionParametersFromFile = null;
			Map<String, Object> errorParametersFromFile = null;
			Map<String, Object> generatorParametersFromFile = null;
			Map<String, Object> saveFormatParametersFromFile = null;
			
			
			functionParametersFromFile = dao.getFunctionParameters(file);
			errorParametersFromFile = dao.getErrorParameters(file);
			generatorParametersFromFile = dao.getGeneratorParameters(file);
			saveFormatParametersFromFile = dao.getSaveFormatParameters(file);
		
			
			try {
				reflection.loadFunctionFields(dao.getFunctionNameFromFile());
				reflection.loadErrorFields(dao.getErrorNameFromFile());
				reflection.loadGeneratorParamsFields(dao.getGeneratorParametersNameFromFile());
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			DataGenerationFunction dataGenerationFunction = reflection.getFunctionInstance(functionParametersFromFile);
			if(!dataGenerationFunction.checkParameters()){
				throw new RuntimeException("Data generation function parameters is not valid. Please, choose correct parameters file!");
			}
			
			MeasurementError measurementError = reflection.getMeasurementErrorInstance(errorParametersFromFile);
			if(!measurementError.checkParameters()){
				throw new RuntimeException("Measurement error parameters is not valid. \nPlease, select correct parameters file!");
			}
			GeneratorParameters generatorParameters =  reflection.getGeneratorParametersInstance(generatorParametersFromFile);
			if(!generatorParameters.checkParameters()){
				throw new RuntimeException("Generator parameters is not valid. \nPlease, select correct parameters file!");
			}
		
			
			functionsComboBox.setSelectedItem(dao.getFunctionNameFromFile());
			errorsComboBox.setSelectedItem(dao.getErrorNameFromFile());
			genParamsComboBox.setSelectedItem(dao.getGeneratorParametersNameFromFile());

			
			//создаем генератор
			dataGenerator = new DataGenerator(dataGenerationFunction, measurementError, generatorParameters);
			dataGenerator.generateData();
			
			//получаем "чистые" данные и данные с погрешностью
			Map<Double, Double> origDataForChart = dataGenerator.getOriginalData();
			Map<Double, Double> dataForChart = null;
			
			try {
				//path to dataFile is in the parameters XML-file
				dataForChart = dao.getData(null);
			}catch (FileNotFoundException e) {
				throw new RuntimeException("Cann't get data from file. Data file is missing");
			} catch (IOException e) {
				throw new RuntimeException("Cann't get data from file. Error with the data file");
			}
		
			
			//строим график из полученных данных
			buildChart(dataForChart, origDataForChart);
		
		
			
		}
		
		
	}

	
	public void stopEditing(JTable table)
	{
	    if (table.isEditing())
	    {
	        int col = table.getEditingColumn(), 
	            row = table.getEditingRow();
	        CellEditor cellEditor = table.getCellEditor();
	        if (cellEditor != null)
	              cellEditor.stopCellEditing();
	        table.changeSelection(row, col, false, false);
	    }
	}
	
	private void buildChart(Map<Double, Double> dataForChart, Map<Double, Double> origDataForChart){
	
		chart_panel.removeAll();
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		DefaultCategoryDataset origDataset = new DefaultCategoryDataset();

		

		Double maxValueInData = (Collections.max(dataForChart.values()));
		Double maxValueInOrigData = (Collections.max(origDataForChart.values()));
		Double minValueInData = (Collections.min(dataForChart.values()));
		Double minValueInOrigData = (Collections.min(origDataForChart.values()));
		
		double max = 0;
		if(maxValueInData > maxValueInOrigData){
			max = maxValueInData;
		} else{
			max = maxValueInOrigData;
		}
		
		double min = 0;
		if(minValueInData < minValueInOrigData){
			min = minValueInData;
		} else{
			min = minValueInOrigData;
		}
		
		
		
		
		for (Entry<Double, Double> entry : dataForChart.entrySet()) {
			double x = entry.getKey();
			double y = entry.getValue();
			dataset.setValue(y, "F*(x) - with noise", entry.getKey());
		}
		
		for (Entry<Double, Double> entry : origDataForChart.entrySet()) {
			double x = entry.getKey();
			double y = entry.getValue();
			origDataset.setValue(y, "F(x) - original", entry.getKey());
		}
		
		// create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "", // chart title
            "", 		        // domain axis label
            "",           	   // range axis label
            origDataset,      // data
            PlotOrientation.VERTICAL,
            true,           // include legend
            false,
            false
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        //chart.setBackgroundPaint(new Color(0xCC, 0xFF, 0xCC));
        chart.getLegend().setAnchor(Legend.SOUTH);

        
        final CategoryPlot plot = chart.getCategoryPlot();
        final CategoryItemRenderer renderer1 = plot.getRenderer();
        renderer1.setSeriesPaint(0, new Color(100, 180, 255));
        
        plot.getRangeAxis().setRange(min, max);

       

        
        final ValueAxis axis2 = new NumberAxis("");
        axis2.setRange(min, max);
        
        plot.setRangeAxis(1, axis2);
        plot.setDataset(1, dataset);
        plot.mapDatasetToRangeAxis(1, 1);
        
        
        final CategoryItemRenderer renderer2 = new LineAndShapeRenderer(2); 
        renderer2.setSeriesPaint(0, new Color(16, 78, 139));
        
        plot.setRenderer(1, renderer2);

        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
   
        
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

        chart_panel.setLayout(new java.awt.BorderLayout());
        chart_panel.add(chartPanel,BorderLayout.CENTER);

        chart_panel.validate();
        
    
	}
}
