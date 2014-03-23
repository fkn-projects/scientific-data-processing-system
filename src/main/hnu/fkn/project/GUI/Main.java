package hnu.fkn.project.GUI;

import hnu.fkn.project.generator.DataGenerator;
import hnu.fkn.project.reflection.ReflectionObject;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Shape;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

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
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.DefaultCategoryItemRenderer;
import org.jfree.chart.renderer.category.LevelRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import plugins.DataGenerationFunction;
import plugins.GeneratorParameters;
import plugins.MeasurementError;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class Main {

	private JFrame frame;
	private JComboBox functionsComboBox;
	private JComboBox errorsComboBox;
	private JComboBox genParamsComboBox;
	private JComboBox fileFormatComboBox;
	
	private ReflectionObject reflection;
	
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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 644, 548);
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
		lblNewLabel.setBounds(10, 11, 136, 14);
		save_panel.add(lblNewLabel);
		
		fileFormatComboBox = new JComboBox();
		fileFormatComboBox.setBounds(10, 28, 127, 20);
		save_panel.add(fileFormatComboBox);
		fileFormatComboBox.addItem("Select ...");
		
		
		btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(140, 27, 61, 23);
		save_panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(371, 398, 251, 106);
		frame.getContentPane().add(panel_1);
		
		
		
		
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
						fileFormatComboBox.addItem(fileFormatsName);
					}
				}

			
				
				
				
				
			
			}			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e1){
			System.out.println("Not one plugin was not found. The system can not continue its work");
		} catch (ClassCastException e){
			System.out.println(e.getMessage());
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

					lblFunctionParameters = new JLabel("Function Parameters");
		
					lblFunctionParameters.setBounds(7, 47, 136, 14);
					panel.add(lblFunctionParameters);
					
					panel.revalidate();
					panel.repaint();
					
					
					functionParametersTable = new JTable();
					functionParametersTable.setForeground(SystemColor.infoText);
					//functionParametersTable.setBackground(SystemColor.control);
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
						model.addRow(new Object[]{" "+parameters.getKey(), });					
					}
					
					
					
					panel.add(scrollPaneF);

					panel.revalidate();
					panel.repaint();
					
					
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

					
					lblErrorParameters = new JLabel("Error Parameters");
							
					lblErrorParameters.setBounds(7, 205, 136, 14);
					panel.add(lblErrorParameters);
					
					panel.revalidate();
					panel.repaint();
					
					
					errorParametersTable = new JTable();
					errorParametersTable.setForeground(SystemColor.infoText);
					//errorParametersTable.setBackground(SystemColor.control);
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
					
						model.addRow(new Object[]{" "+parameters.getKey(), });					
					}
					
					
					panel.add(scrollPaneErr);
					panel.revalidate();
					panel.repaint();
					
					
					
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

					
					lblGeneratorParameters = new JLabel("Generetor Parameters");
							
					lblGeneratorParameters.setBounds(7, 357, 136, 14);
					panel.add(lblGeneratorParameters);
					
					
					
					genParametersTable = new JTable();
					genParametersTable.setForeground(SystemColor.infoText);
					//genParametersTable.setBackground(SystemColor.control);
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
					
						model.addRow(new Object[]{" "+parameters.getKey(), });					
					}
					
					
					panel.add(scrollPaneGen);
					panel.revalidate();
					panel.repaint();
					
					
					
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
		fileFormatComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Map<String, String> saveParameters = null;
				try {
					saveParameters = reflection.loadSaveParametersFields(fileFormatComboBox.getSelectedItem().toString());
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
					
					saveParametersTable = new JTable();
					saveParametersTable.setForeground(SystemColor.infoText);
					//genParametersTable.setBackground(SystemColor.control);
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
					
					/*
					JComboBox csvSeparators = new JComboBox();
					csvSeparators.addItem("  ;");
					csvSeparators.addItem("  ,");
					csvSeparators.addItem("  :");
					csvSeparators.addItem("  |");
					csvSeparators.addItem("  _");
					csvSeparators.addItem("  /");
					
					
					saveParametersTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(csvSeparators));
					*/			
					
					saveParametersTable.setTableHeader(null);
					scrollPaneSave = new JScrollPane(saveParametersTable,
					           JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					           JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				    scrollPaneSave.setBounds(10, 52, 189, 0);
					for (Map.Entry<String, String> parameters : saveParameters.entrySet()) {
						
						if(scrollPaneSave.getHeight() < 54){
							scrollPaneSave.setSize(scrollPaneSave.getWidth(), scrollPaneSave.getHeight()+17);
						} 
					
						model.addRow(new Object[]{" "+parameters.getKey(), });					
					}
					
					
					save_panel.add(scrollPaneSave);
					save_panel.revalidate();
					save_panel.repaint();
					
					
					
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
	
		
		//обработчик нажатия кнопки Generate
		btnGenerateData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				generateDataHandler();
			}
		});
	}
	
	
	
	private void generateDataHandler(){
		
		//получаем объект функции-генеретора
		Map<String, Object> functionParameters = new TreeMap<String, Object>();
		for (int count = 0; count < functionParametersTable.getModel().getRowCount(); count++) {
			functionParameters.put(functionParametersTable.getModel().getValueAt(count, 0).toString().replace(" ", ""), 
									functionParametersTable.getModel().getValueAt(count, 1));
		}		
		DataGenerationFunction dataGenerationFunction = reflection.getFunctionInstance(functionParameters);
		dataGenerationFunction.checkParameters();
		
		//получаем объект погрешности
		Map<String, Object> errorParameters = new TreeMap<String, Object>();
		for (int count = 0; count < errorParametersTable.getModel().getRowCount(); count++) {
			errorParameters.put(errorParametersTable.getModel().getValueAt(count, 0).toString().replace(" ", ""), 
									errorParametersTable.getModel().getValueAt(count, 1));
		}		
		MeasurementError measurementError = reflection.getMeasurementErrorInstance(errorParameters);
		measurementError.checkParameters();
		
		//получаем объект параметров генератора
		Map<String, Double> genParameters = new TreeMap<String, Double>();
		for (int count = 0; count < genParametersTable.getModel().getRowCount(); count++) {
			genParameters.put( genParametersTable.getModel().getValueAt(count, 0).toString().replace(" ", ""), 
								(Double) genParametersTable.getModel().getValueAt(count, 1));
		}			
		GeneratorParameters generatorParameters =  reflection.getGeneratorParametersInstance(genParameters);
		generatorParameters.checkParameters();
		
		//создаем генератор
		DataGenerator dataGenerator = new DataGenerator(dataGenerationFunction, measurementError, generatorParameters);
		dataGenerator.generateData();
		
		//получаем "чистые" данные и данные с погрешностью
		Map<Double, Double> origDataForChart = dataGenerator.getOriginalData();
		Map<Double, Double> dataForChart = dataGenerator.getData();
		
		//строим график из полученных данных
		buildChart(dataForChart, origDataForChart);
			
	}
	
	private void buildChart(Map<Double, Double> dataForChart, Map<Double, Double> origDataForChart){
	
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		DefaultCategoryDataset origDataset = new DefaultCategoryDataset();

		for (Entry<Double, Double> entry : dataForChart.entrySet()) {
			double x = entry.getKey();
			double y = entry.getValue();
			dataset.setValue(y, "", entry.getKey());
		}
		
		for (Entry<Double, Double> entry : origDataForChart.entrySet()) {
			double x = entry.getKey();
			double y = entry.getValue();
			origDataset.setValue(y, "", entry.getKey());
		}
		
		// create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "Experimental data", // chart title
            "X", 		        // domain axis label
            "F (X)",           // range axis label
            dataset,          // data
            PlotOrientation.VERTICAL,
            false,          // include legend
            true,
            false
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        //chart.setBackgroundPaint(new Color(0xCC, 0xFF, 0xCC));
        //chart.getLegend().setAnchor(Legend.SOUTH);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        final CategoryItemRenderer renderer1 = plot.getRenderer();
        renderer1.setSeriesPaint(0, Color.darkGray);
        renderer1.setSeriesPaint(1, Color.yellow);
        renderer1.setSeriesPaint(2, Color.green);


        final ValueAxis axis2 = new NumberAxis("F'(X)");
        plot.setRangeAxis(1, axis2);
        plot.setDataset(1, origDataset);
        plot.mapDatasetToRangeAxis(1, 1);
        
        
        final CategoryItemRenderer renderer2 = new LineAndShapeRenderer(2); 
        renderer2.setSeriesPaint(0, Color.green);
        plot.setRenderer(1, renderer2);

        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
   
        
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

        chart_panel.setLayout(new java.awt.BorderLayout());
        chart_panel.add(chartPanel,BorderLayout.CENTER);

        chart_panel.validate();
    
	}
}
