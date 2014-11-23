

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class GotoDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField lineField;
	JPanel gotoPanel;
	
	GotoDialog(NotepadFrame parFrame,String name,Boolean modal)
	{
		super(parFrame,name,modal);
		gotoPanel=new JPanel();
		gotoPanel.setPreferredSize(new Dimension(200,90));
		JLabel lineLabel=new JLabel("�кţ�");
		lineField=new JTextField();
		lineField.setPreferredSize(new Dimension(170,25));
		JButton cancelButton=new JButton("  ȡ��  ");
		JButton gotoButton=new JButton("  ת��  ");
		cancelButton.addActionListener(parFrame);
		cancelButton.setActionCommand("�رնԻ���");
		gotoButton.addActionListener(parFrame);
		gotoButton.setActionCommand("ת������");
		gotoPanel.add(lineLabel);
		gotoPanel.add(lineField);
		gotoPanel.add(gotoButton);
		gotoPanel.add(cancelButton);
		
		this.setLocation(300, 300);
		this.add(gotoPanel);
		this.pack();
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
		try
	    {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
	    }catch(Exception e){}
	}
	
	public String getLine()
	{
		return lineField.getText();
	}
}
