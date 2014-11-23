
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class FontDialog extends JDialog implements ListSelectionListener,ActionListener,DocumentListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JList <String>nameList;
	JList <String>styleList;
	JList <String>sizeList;
	JScrollPane nameScroll;
	JScrollPane styleScroll;
	JScrollPane sizeScroll;
	JLabel nameLabel;
	JLabel styleLabel;
	JLabel sizeLabel;
	JTextField nameText;
	JTextField styleText;
	JTextField sizeText;
	JPanel exPanel;
	JLabel exText;
	JButton yesButton;
	JButton cancelButton;
	
	JPanel fontPanel;
	Font f;
	String fontsize;
	String fontstyle;
	String fontname;
	MyTextPanel textPanel;
	
	String []names;
	String []sizes;
	String []styles;
	
	FontDialog(NotepadFrame parFrame,String name,Boolean modal,MyTextPanel t)
	{
		super(parFrame,name,modal);
		textPanel=t;
		f = t.getTextPane().getFont();
		fontsize=String.valueOf(f.getSize());
		switch(f.getStyle())
		{
		case Font.BOLD :fontstyle="粗体";break;
		case Font.ITALIC:fontstyle="斜体";break;
		case Font.PLAIN:fontstyle="常规";
		}
		fontname=f.getName();
		
		fontPanel=new JPanel();
		fontPanel.setPreferredSize(new Dimension(427,357));
		fontPanel.setLayout(null);
		
		nameLabel=new JLabel("字体：");
		styleLabel=new JLabel("字形：");
		sizeLabel=new JLabel("大小：");
		nameLabel.setBounds(13, 17, 42, 23);
		styleLabel.setBounds(200, 17, 42, 23);
		sizeLabel.setBounds(350, 17, 42, 23);
		
		nameText=new JTextField();
		nameText.setBounds(13, 35, 170, 23);
		styleText=new JTextField();
		styleText.setBounds(200, 35, 130, 23);
		sizeText=new JTextField();
		sizeText.setBounds(350, 35, 60, 23);
		
		nameText.getDocument().addDocumentListener(this);
		styleText.getDocument().addDocumentListener(this);
		sizeText.getDocument().addDocumentListener(this);
		
		names=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		nameList=new JList<String>(names);
		styles=new String[]{"常规","斜体","粗体"};
		styleList=new JList<String>(styles);
		sizes=new String[]{"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72","初号","小初","一号","小一","二号","小二","三号","小三","四号","小四","五号","小五","六号","小六","七号","八号"};
		sizeList=new JList<String>(sizes);
		
		nameList.addListSelectionListener(this);
		styleList.addListSelectionListener(this);
		sizeList.addListSelectionListener(this);
		
		
		
		nameScroll=new JScrollPane(nameList);
		styleScroll=new JScrollPane(styleList);
		sizeScroll=new JScrollPane(sizeList);
		
		nameScroll.setBounds(13, 60, 170, 135);
		styleScroll.setBounds(200, 60, 130, 135);
		sizeScroll.setBounds(350, 60, 60, 135);
		
		exPanel=new JPanel();
		exPanel.setBounds(200, 200, 210, 80);
		Border titledBorder = new TitledBorder("示例");
		exPanel.setBorder(titledBorder);
		//exPanel.setLayout(new BorderLayout());
		exText=new JLabel("NotepadForJava赛高");
		exPanel.add(exText);
		
		yesButton=new JButton("确定");
		cancelButton=new JButton("取消");
		yesButton.setBounds(250, 318, 75, 25);
		cancelButton.setBounds(335, 318, 75, 25);
		
		yesButton.addActionListener(this);
		cancelButton.setActionCommand("关闭对话框");
		cancelButton.addActionListener(parFrame);
		
		
		
		fontPanel.add(nameLabel);
		fontPanel.add(styleLabel);
		fontPanel.add(sizeLabel);
		fontPanel.add(nameText);
		fontPanel.add(styleText);
		fontPanel.add(sizeText);
		fontPanel.add(nameScroll);
		fontPanel.add(styleScroll);
		fontPanel.add(sizeScroll);
		fontPanel.add(exPanel);
		fontPanel.add(yesButton);
		fontPanel.add(cancelButton);
		
		this.setLocation(300, 300);
		this.add(fontPanel);
		this.pack();
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
		nameText.setText(fontname);
		styleText.setText(fontstyle);
		sizeText.setText(fontsize);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==nameList)
		{
			fontname=nameList.getSelectedValue();
			nameText.getDocument().removeDocumentListener(this);
			nameText.setText(fontname);
			nameText.getDocument().addDocumentListener(this);
		}
		else if(e.getSource()==styleList)
		{
			fontstyle=styleList.getSelectedValue();
			styleText.getDocument().removeDocumentListener(this);
			styleText.setText(fontstyle);
			styleText.getDocument().addDocumentListener(this);
		}
		else if(e.getSource()==sizeList)
		{
			fontsize=sizeList.getSelectedValue();
			sizeText.getDocument().removeDocumentListener(this);
			sizeText.setText(fontsize);
			sizeText.getDocument().addDocumentListener(this);
		}
		int stylenum=0,sizenum=0;
		switch(fontstyle)
		{
		case "常规": stylenum=Font.PLAIN;break;
		case "斜体": stylenum=Font.BOLD;break;
		case "粗体": stylenum=Font.ITALIC;break;
		}
		switch(fontsize)
		{
		case "初号":sizenum=42;break;
		case "小初":sizenum=36;break;
		case "一号":sizenum=26;break;
		case "小一":sizenum=24;break;
		case "二号":sizenum=22;break;
		case "小二":sizenum=18;break;
		case "三号":sizenum=16;break;
		case "小三":sizenum=15;break;
		case "四号":sizenum=14;break;
		case "小四":sizenum=12;break;
		case "五号":sizenum=11;break;
		case "小五":sizenum=9;break;
		case "六号":sizenum=8;break;
		case "小六":sizenum=7;break;
		case "七号":sizenum=6;break;
		case "八号":sizenum=5;break;
		default:sizenum=Integer.parseInt(fontsize);
		}
		f=new Font(fontname,stylenum,sizenum);
		exText.setFont(f);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==yesButton)
		{
			textPanel.resetFont(f);
			this.dispose();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO 自动生成的方法存根
		Document nowDoc=e.getDocument();
		String docStr=null;
		int len=nowDoc.getLength();
		if(len!=0)
		{
			try {
				docStr=nowDoc.getText(0, len);
			} catch (BadLocationException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			if(nowDoc==nameText.getDocument())
			{
				int nameLen=names.length;
				int end=0;
				boolean flag=false;
				for(int i=0;i<nameLen;i++)
				{
					if(names[i].indexOf(docStr)==0)
					{
						if(names[i].equals(docStr)) flag=true;
						end=i;
						break;
					}
				}
				if(flag==true)
				{
					nameList.removeListSelectionListener(this);
					JScrollBar sBar = nameScroll.getVerticalScrollBar();
					Point p=nameList.getUI().indexToLocation(nameList,end);
					sBar.setValue(p.y);
					nameList.setSelectedIndex(end);
					nameList.addListSelectionListener(this);
				}
				else
				{
					JScrollBar sBar = nameScroll.getVerticalScrollBar();
					Point p=nameList.getUI().indexToLocation(nameList,end);
					sBar.setValue(p.y);
				} 
			}
			else if(nowDoc==sizeText.getDocument())
			{
				int sizeLen=sizes.length;
				int end=0;
				boolean flag=false;
				for(int i=0;i<sizeLen;i++)
				{
					if(sizes[i].indexOf(docStr)==0)
					{
						if(sizes[i].equals(docStr)) flag=true;
						end=i;
						break;
					}
				}
				if(flag==true)
				{
					sizeList.removeListSelectionListener(this);
					JScrollBar sBar = sizeScroll.getVerticalScrollBar();
					Point p=sizeList.getUI().indexToLocation(sizeList,end);
					sBar.setValue(p.y);
					sizeList.setSelectedIndex(end);
					sizeList.addListSelectionListener(this);
				}
				else
				{
					JScrollBar sBar = sizeScroll.getVerticalScrollBar();
					Point p=sizeList.getUI().indexToLocation(sizeList,end);
					sBar.setValue(p.y);
				} 
			}
			else if(nowDoc==styleText.getDocument())
			{
				int styleLen=styles.length;
				int end=0;
				boolean flag=false;
				for(int i=0;i<styleLen;i++)
				{
					if(styles[i].indexOf(docStr)==0)
					{
						if(styles[i].equals(docStr)) flag=true;
						end=i;
						break;
					}
				}
				if(flag==true)
				{
					styleList.removeListSelectionListener(this);
					JScrollBar sBar = styleScroll.getVerticalScrollBar();
					Point p=styleList.getUI().indexToLocation(styleList,end);
					sBar.setValue(p.y);
					styleList.setSelectedIndex(end);
					styleList.addListSelectionListener(this);
				}
				else
				{
					JScrollBar sBar = styleScroll.getVerticalScrollBar();
					Point p=styleList.getUI().indexToLocation(styleList,end);
					sBar.setValue(p.y);
				} 
			}
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO 自动生成的方法存根
		changedUpdate(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO 自动生成的方法存根
		changedUpdate(e);
	}

}
