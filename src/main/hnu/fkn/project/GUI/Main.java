package hnu.fkn.project.GUI;

import hnu.fkn.project.reflection.ReflectionObject;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import plugins.DataGenerationFunction;
import plugins.GeneratorParameters;
import plugins.MeasurementError;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

	private JFrame frame;
	private JComboBox functionsComboBox;
	private JComboBox errorsComboBox;
	private JComboBox genParamsComboBox;
	
	private ReflectionObject reflection;
	
	private JTable functionParametersTable;
	private JTable errorParametersTable;
	private JTable genParametersTable;
	
	
	private JLabel lblFunctionParameters;
	private JLabel lblErrorParameters;
	private JLabel lblGeneratorParameters;
	
	private JScrollPane scrollPaneF;
	private JScrollPane scrollPaneErr;
	private JScrollPane scrollPaneGen;
	
	private JButton btnGenerateData;
	
	private JPanel panel;
	
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
		btnGenerateData.setBounds(7, 473, 136, 23);
		panel.add(btnGenerateData);
		
		JLabel lblSelectInputMethod = new JLabel("Select  generator parameters");
		lblSelectInputMethod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectInputMethod.setBounds(7, 314, 136, 14);
		panel.add(lblSelectInputMethod);
		
		genParamsComboBox = new JComboBox();
		genParamsComboBox.setBounds(7, 333, 136, 20);
		panel.add(genParamsComboBox);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(158, 3, 464, 437);
		frame.getContentPane().add(panel_1);
		
		
		reflection = new ReflectionObject();
		
		functionsComboBox.addItem("Select ...");
		errorsComboBox.addItem("Select ...");
		genParamsComboBox.addItem("Select ...");
		
		
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
						model.addRow(new Object[]{"  " + parameters.getKey(), });					
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
					
						model.addRow(new Object[]{"  " + parameters.getKey(), });					
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
					
						model.addRow(new Object[]{"  " + parameters.getKey(), });					
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
		
		
		
		
		//обработчик кнопки Generate
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
			functionParameters.put((String) functionParametersTable.getModel().getValueAt(count, 0), 
									functionParametersTable.getModel().getValueAt(count, 1));
		}		
		DataGenerationFunction dataGenerationFunction = reflection.getFunctionInstance(functionParameters);
		
		//получаем объект погрешности
		Map<String, Object> errorParameters = new TreeMap<String, Object>();
		for (int count = 0; count < errorParametersTable.getModel().getRowCount(); count++) {
			errorParameters.put((String) errorParametersTable.getModel().getValueAt(count, 0), 
									errorParametersTable.getModel().getValueAt(count, 1));
		}		
		MeasurementError measurementError = reflection.getMeasurementErrorInstance(errorParameters);
		
		//получаем объект параметров генератора
		Map<String, Object> genParameters = new TreeMap<String, Object>();
		for (int count = 0; count < genParametersTable.getModel().getRowCount(); count++) {
			errorParameters.put((String) genParametersTable.getModel().getValueAt(count, 0), 
								genParametersTable.getModel().getValueAt(count, 1));
		}			
		GeneratorParameters generatorParameters =  reflection.getGeneratorParametersInstance(genParameters);
		
		
		
		
	}
	
	
	
	
}
