

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class ReplaceDialog extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel findLabel;
	JLabel replaceLabel;
	JTextField findText;
	JTextField replaceText;
	
	JButton findButton;
	JButton replaceButton;
	JButton replaceAllButton;
	JButton cancelButton;
	
	JCheckBox capsCheckBox;
	JCheckBox inSelAreaCheckBox;
	
	JPanel replacePanel;
	
	ReplaceDialog(NotepadFrame parFrame,String name,Boolean modal)
	{
		super(parFrame,name,modal);
		
		replacePanel=new JPanel();
		replacePanel.setPreferredSize(new Dimension(325,140));
		
		findLabel=new JLabel("查找内容:");
		replaceLabel=new JLabel("替换内容:");
		findText=new JTextField();
		replaceText=new JTextField();
		findText.setPreferredSize(new Dimension(250,25));
		replaceText.setPreferredSize(new Dimension(250,25));
		
		findButton=new JButton("查找下一个");
		replaceButton=new JButton("替换");
		replaceAllButton=new JButton("全部替换");
		cancelButton=new JButton("取消");
		
		findButton.addActionListener(parFrame);
		cancelButton.addActionListener(parFrame);
		findButton.setActionCommand("查找下一个");
		cancelButton.setActionCommand("关闭对话框");
		replaceButton.addActionListener(parFrame);
		replaceAllButton.addActionListener(parFrame);
		replaceButton.setActionCommand("替换该字符串");
		replaceAllButton.setActionCommand("替换全部");
		
		capsCheckBox=new JCheckBox("区分大小写",false);
		inSelAreaCheckBox=new JCheckBox("在选择区域内",false);
		
		replacePanel.add(findLabel);
		replacePanel.add(findText);
		replacePanel.add(replaceLabel);
		replacePanel.add(replaceText);
		replacePanel.add(findButton);
		replacePanel.add(replaceButton);
		replacePanel.add(replaceAllButton);
		replacePanel.add(cancelButton);
		replacePanel.add(capsCheckBox);
		replacePanel.add(inSelAreaCheckBox);
		
		this.setLocation(300, 300);
		this.add(replacePanel);
		this.pack();
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
		
		try
	    {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
	    }catch(Exception e){}
		
	}
	
	public JButton getfindButton()
	{
		return findButton;
	}
	
	public String getFindStr()
	{
		return findText.getText();
	}
	
	public String getReplaceStr()
	{
		return replaceText.getText();
	}
	
	public Boolean getcaps()
	{
		return capsCheckBox.isSelected();
	}
	
	public Boolean getinArea()
	{
		return inSelAreaCheckBox.isSelected();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		
	}

}
