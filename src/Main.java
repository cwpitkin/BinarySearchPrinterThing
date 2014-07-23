import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Main extends JFrame implements ActionListener{
	
	  private JTextArea textArea;
	  private JButton button;
	  private JTextField textField;
	  private BinarySearchTreeADT bsa = new BinarySearchTreeADT();
	
	public static void main(String[] args) {
		Main main = new Main();
		main.setSize(425,425);
		main.createGUI();
		main.setVisible(true);
	}

	private void createGUI() {
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    Container window = getContentPane();
	    window.setLayout(new FlowLayout());

	    textArea = new JTextArea("");
	    textArea.setSize(400,400);
	    window.add(textArea);

	    button = new JButton("go");
	    window.add(button);
	    button.addActionListener(this);

	    textField = new JTextField(9);
	    window.add(textField);

	  }

	@Override
	public void actionPerformed(ActionEvent event) {
		bsa.add(textField.getText());
		textField.setText("");
		textArea.setText(bsa.printTree());
	}
}
