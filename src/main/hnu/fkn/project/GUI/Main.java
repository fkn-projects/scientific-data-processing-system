package hnu.fkn.project.GUI;

import hnu.fkn.project.reflection.ReflectionObject;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

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

public class Main {

	private JFrame frame;
	private JComboBox functionsComboBox;
	private JComboBox errorsComboBox;
	private ReflectionObject reflection;
	private JTable functionParametersTable;
	private JTable errorParametersTable;
	
	private JLabel lblFunctionParameters;
	private JLabel lblErrorParameters;

	private JScrollPane scrollPaneF;
	private JScrollPane scrollPaneErr;
	
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
		frame.setBounds(100, 100, 648, 582);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(3, 3, 152, 536);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblSelectFunction = new JLabel("Select function");
		lblSelectFunction.setBounds(7, 7, 136, 14);
		panel.add(lblSelectFunction);
		
		functionsComboBox = new JComboBox();
		functionsComboBox.setBackground(SystemColor.control);
		
		functionsComboBox.setBounds(7, 25, 136, 20);
		panel.add(functionsComboBox);
		
		
		JLabel lblSelectMeasurementError = new JLabel("Select measurement error");
		lblSelectMeasurementError.setBounds(7, 162, 136, 14);
		panel.add(lblSelectMeasurementError);
		
		errorsComboBox = new JComboBox();
		errorsComboBox.setBackground(SystemColor.control);
		errorsComboBox.setBounds(7, 180, 136, 20);
		panel.add(errorsComboBox);
		
		
		reflection = new ReflectionObject();
		
		//загрузка всех плагинов
		Map<String, List<String>> functionsAndErrorsNames = null;
		try {
			functionsAndErrorsNames = reflection.loadFunctionAndMeasurementErrorNames();

			for (Map.Entry<String, List<String>> entry : functionsAndErrorsNames.entrySet()) {
				
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
				
					
					if(scrollPaneErr != null && errorParametersTable != null && errorParametersTable.getParent() == panel){
						
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

					
					lblErrorParameters = new JLabel("Error Parameters");
							
					lblErrorParameters.setBounds(7, 205, 136, 14);
					panel.add(lblErrorParameters);
					
					
					
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
					if(scrollPaneErr != null && errorParametersTable != null && errorParametersTable.getParent() == panel){
						panel.remove(errorParametersTable);
						panel.remove(scrollPaneErr);
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


	
		
		
		
	
	}
}
