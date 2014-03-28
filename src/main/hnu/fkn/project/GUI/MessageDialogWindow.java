package hnu.fkn.project.GUI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MessageDialogWindow {

	public static void showErrorWindow(String message, String windowHeader, JFrame parentFrame){
		JOptionPane.showMessageDialog(parentFrame,
			    message,
			    windowHeader,
			    JOptionPane.ERROR_MESSAGE);
	}
}
