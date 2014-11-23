
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class FindDialog extends JDialog implements ActionListener{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField findField;
	JPanel findPanel;
	JCheckBox caps;
	JRadioButton upButton;
	JRadioButton downButton;
	JButton findNextButton;
	int isUp;
	
	FindDialog(NotepadFrame parFrame,String name,Boolean modal)
	{
		super(parFrame,name,modal);
		findPanel=new JPanel();
		findPanel.setPreferredSize(new Dimension(430,125));
		JLabel findlabel=new JLabel("查找内容(N):      ");
		findField=new JTextField();
		findField.setPreferredSize(new Dimension(180,25));
		findNextButton=new JButton("查找下一个(F)");
		JButton cancelButton=new JButton("    取消    ");
		caps=new JCheckBox("区分大小写(C)",false);
		ButtonGroup group = new ButtonGroup();
		upButton=new JRadioButton("向上(U)",false);
		downButton=new JRadioButton("向下(D)",true);
		isUp=0;
		upButton.addActionListener(this);
		downButton.addActionListener(this);
		group.add(upButton);
		group.add(downButton);
		JPanel groupPanel=new JPanel();
		groupPanel.setPreferredSize(new Dimension(180,70));
		groupPanel.add(upButton);
		groupPanel.add(downButton);
		Border titledBorder = new TitledBorder("方向");
		groupPanel.setBorder(titledBorder);
		findPanel.add(findlabel);
		findPanel.add(findField);
		findPanel.add(findNextButton);
		findPanel.add(caps);
		findPanel.add(groupPanel);
		findPanel.add(cancelButton);
		this.setLocation(300, 300);
		this.add(findPanel);
		this.pack();
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
		try
	    {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
	    }catch(Exception e){}
		
		//为查找对话框组件设置助记符
		findNextButton.setMnemonic(KeyEvent.VK_F);
		caps.setMnemonic(KeyEvent.VK_C);
		upButton.setMnemonic(KeyEvent.VK_U);
		downButton.setMnemonic(KeyEvent.VK_D);
		
		//为查找对话框添加监听.setActionCommand
		findNextButton.addActionListener(parFrame);
		cancelButton.addActionListener(parFrame);
		findNextButton.setActionCommand("查找下一个");
		cancelButton.setActionCommand("关闭对话框");
		
	}
	
	public JTextField getField()
	{
		return findField;
	}
	
	public JPanel getFindPanel()
	{
		return findPanel;
	}
	
	public JCheckBox getCheckBox()
	{
		return caps;
	}
	
	public int getIsUp()
	{
		return isUp;
	}
	
	public JButton getButton()
	{
		return findNextButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==upButton) isUp=1;
		else isUp=0;
		
	}
}
