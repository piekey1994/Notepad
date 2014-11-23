

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.awt.*;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.awt.Color;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

class NotepadFrame extends JFrame implements ActionListener, DocumentListener,ChangeListener,MouseMotionListener,MouseListener,KeyListener,WindowListener
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//�˵���
	JMenuBar menuBar;
	JMenu menuFile,menuEdit,menuStyle,menuView,menuFunction,menuHelp;
	JMenuItem itemNew,itemOpen,itemSave,itemSaveAs,itemPrint,itemExit;
	JMenuItem itemUndo,itemRedo,itemCut,itemCopy,itemPaste,itemDelete;
	JMenuItem itemFind,itemFindNext,itemReplace,itemGoto,itemAll,itemTime;
	JMenuItem itemFont;
	JCheckBoxMenuItem itemPro,itemStatusBar,itemSideBar;
	JMenuItem itemZoomIn,itemZoomOut;
	JMenuItem itemRun;
	JMenuItem itemHelp,itemAbout;
	
	//������
	JToolBar toolBar;
	JButton buttonNew,buttonOpen,buttonSave,buttonSaveAs,buttonPrint;
	JButton buttonUndo,buttonRedo,buttonCut,buttonCopy,buttonPaste;
	JButton buttonZoomIn,buttonZoomOut;
	JButton buttonRun;
	
	//�ı���
	MyTextPanel myTextPanel;
	Font f;
	
	//�����
	JTabbedPane centerPanel;
	
	//״̬��
	JLabel statusBar;
	
	//�Ĵ���
	String copyStr;
	Boolean pro;
	
	//�Ի���
	FindDialog findDialog;
	JButton findInDialog;
	ReplaceDialog replaceDialog;
	GotoDialog gotoDialog;
	FontDialog fontDialog;
	
	//�Ҽ��˵�
	JPopupMenu popupMenu;
	JMenuItem pitemUndo;
	JMenuItem pitemRedo;
	JMenuItem pitemCut;
	JMenuItem pitemCopy;
	JMenuItem pitemPaste;
	JMenuItem pitemDelete;
	JMenuItem pitemAll;
		
	//���캯��
	NotepadFrame()
	{
		pro=true;
		
		setSize(800,600);
		setLocation(250,250);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(true);
		this.addWindowListener(this);
		
		//�����˵�
		menuBar=new JMenuBar();
		menuFile = new JMenu("�ļ�(F)"); 
		menuEdit = new JMenu("�༭(E)"); 
		menuStyle = new JMenu("��ʽ(O)");
		menuView = new JMenu("�鿴(V)");
		menuFunction = new JMenu("����(G)");
		menuHelp = new JMenu("����(H)"); 
		
		itemNew = new JMenuItem("�½�(N)",KeyEvent.VK_N);
		itemOpen = new JMenuItem("��(O)",KeyEvent.VK_O);
		itemSave = new JMenuItem("����(S)",KeyEvent.VK_S);
		itemSaveAs = new JMenuItem("���Ϊ(A)",KeyEvent.VK_A);		
		itemPrint = new JMenuItem("��ӡ(P)",KeyEvent.VK_P);
		itemExit = new JMenuItem("�˳�(X)",KeyEvent.VK_X);
		
		itemUndo = new JMenuItem("����(U)",KeyEvent.VK_U);
		itemRedo = new JMenuItem("������(E)",KeyEvent.VK_E);
		itemCut = new JMenuItem("����(T)",KeyEvent.VK_T);	
		itemCopy = new JMenuItem("����(C)",KeyEvent.VK_C);
		itemPaste = new JMenuItem("ճ��(P)",KeyEvent.VK_P);
		itemDelete = new JMenuItem("ɾ��(L)",KeyEvent.VK_L);
		itemFind = new JMenuItem("����(F)",KeyEvent.VK_F);
		itemFindNext = new JMenuItem("������һ��(N)",KeyEvent.VK_N);
		itemReplace = new JMenuItem("�滻(R)",KeyEvent.VK_R);
		itemGoto = new JMenuItem("ת��(G)",KeyEvent.VK_G);
		itemAll = new JMenuItem("ȫѡ(A)",KeyEvent.VK_A);
		itemTime = new JMenuItem("ʱ��/����(D)",KeyEvent.VK_D);
		
		itemFont = new JMenuItem("����(F)",KeyEvent.VK_F);
		itemPro = new JCheckBoxMenuItem("רҵģʽ(P)",true);
		
		itemStatusBar = new JCheckBoxMenuItem("״̬��(S)",true);
		itemSideBar = new JCheckBoxMenuItem("�����(C)",true);
		
		itemZoomIn = new JMenuItem("�Ŵ�(I)",KeyEvent.VK_I);
		itemZoomOut = new JMenuItem("��С(O)",KeyEvent.VK_O);
		
		itemRun = new JMenuItem("����(R)",KeyEvent.VK_R);

		itemHelp = new JMenuItem("�鿴����(H)",KeyEvent.VK_H);
		itemAbout = new JMenuItem("���� NFJ(A)",KeyEvent.VK_A);
		
		//���ò˵�������
		itemNew.setActionCommand("�½�");
		itemOpen.setActionCommand("��");
		itemSave.setActionCommand("����");
		itemSaveAs.setActionCommand("���Ϊ");		
		itemPrint.setActionCommand("��ӡ");
		itemExit.setActionCommand("�˳�");
		
		itemUndo.setActionCommand("����");
		itemRedo.setActionCommand("������");
		itemCut.setActionCommand("����");
		itemCopy.setActionCommand("����");
		itemPaste.setActionCommand("ճ��");
		itemDelete.setActionCommand("ɾ��");
		itemFind.setActionCommand("����");
		itemFindNext.setActionCommand("������һ��");
		itemReplace.setActionCommand("�滻");
		itemGoto.setActionCommand("ת��");
		itemAll.setActionCommand("ȫѡ");
		itemTime.setActionCommand("ʱ��");
		
		itemFont.setActionCommand("����");
		itemPro.setActionCommand("רҵ");
		
		itemStatusBar.setActionCommand("״̬��");
		itemSideBar.setActionCommand("�����");
		
		itemZoomIn.setActionCommand("�Ŵ�");
		itemZoomOut.setActionCommand("��С");
		
		itemRun.setActionCommand("����");

		itemHelp.setActionCommand("����");
		itemAbout.setActionCommand("����");
		
		//�������Ƿ�
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuEdit.setMnemonic(KeyEvent.VK_E);
		menuStyle.setMnemonic(KeyEvent.VK_O);
		menuView.setMnemonic(KeyEvent.VK_V);
		menuFunction.setMnemonic(KeyEvent.VK_G);
		menuHelp.setMnemonic(KeyEvent.VK_H);
		itemPro.setMnemonic(KeyEvent.VK_P);
		itemStatusBar.setMnemonic(KeyEvent.VK_S);
		itemSideBar.setMnemonic(KeyEvent.VK_C);
		
		
		//���ò˵����ݼ�
		itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		itemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		
		itemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		itemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		itemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		itemDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
		itemFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		itemFindNext.setAccelerator(KeyStroke.getKeyStroke("F3"));
		itemReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		itemGoto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		itemAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		itemTime.setAccelerator(KeyStroke.getKeyStroke("F5"));
		itemRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, InputEvent.CTRL_MASK));
		itemZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_MASK));
		itemZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_MASK));

		
		//Ϊ�˵��������¼���Ӧ
		itemNew.addActionListener(this);
		itemOpen.addActionListener(this);
		itemSave.addActionListener(this);
		itemSaveAs.addActionListener(this);
		itemPrint.addActionListener(this);
		itemExit.addActionListener(this);
		
		itemUndo.addActionListener(this);
		itemRedo.addActionListener(this);
		itemCut.addActionListener(this);
		itemCopy.addActionListener(this);
		itemPaste.addActionListener(this);
		itemDelete.addActionListener(this);
		itemFind.addActionListener(this);
		itemFindNext.addActionListener(this);
		itemReplace.addActionListener(this);
		itemGoto.addActionListener(this);
		itemAll.addActionListener(this);
		itemTime.addActionListener(this);
		
		itemFont.addActionListener(this);
		itemPro.addActionListener(this);
		
		itemStatusBar.addActionListener(this);
		itemSideBar.addActionListener(this);
		itemZoomIn.addActionListener(this);
		itemZoomOut.addActionListener(this);
		
		itemRun.addActionListener(this);
		
		itemHelp.addActionListener(this);
		itemAbout.addActionListener(this);
		
		
		//Ϊ�˵����Ӳ˵���	
		menuFile.add(itemNew);
		menuFile.add(itemOpen);
		menuFile.add(itemSave);
		menuFile.add(itemSaveAs);
		menuFile.addSeparator();
		menuFile.add(itemPrint);
		menuFile.addSeparator();
		menuFile.add(itemExit);		
		
		menuEdit.add(itemUndo);
		menuEdit.add(itemRedo);
		menuEdit.addSeparator();
		menuEdit.add(itemCut);
		menuEdit.add(itemCopy);
		menuEdit.add(itemPaste);
		menuEdit.add(itemDelete);
		menuEdit.addSeparator();
		menuEdit.add(itemFind);
		menuEdit.add(itemFindNext);
		menuEdit.add(itemReplace);
		menuEdit.add(itemGoto);
		menuEdit.addSeparator();
		menuEdit.add(itemAll);
		menuEdit.add(itemTime);
		
		menuStyle.add(itemFont);
		menuStyle.addSeparator();
		menuStyle.add(itemPro);	
		
		menuView.add(itemStatusBar);
		menuView.add(itemSideBar);
		menuView.addSeparator();
		menuView.add(itemZoomIn);
		menuView.add(itemZoomOut);
		
		menuFunction.add(itemRun);
		
		menuHelp.add(itemHelp);
		menuHelp.addSeparator();
		menuHelp.add(itemAbout);
		
		//���˵����ӵ��˵�����
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuStyle);
		menuBar.add(menuView);
		menuBar.add(menuFunction);
		menuBar.add(menuHelp);
		
		//Ϊ�������ò˵���
		this.setJMenuBar(menuBar);
		
		//����������
		toolBar = new JToolBar();
		
		//������ť
		buttonNew = new JButton(new ImageIcon(NotepadFrame.class.getResource("tab_new.png")));
		buttonOpen = new JButton(new ImageIcon(NotepadFrame.class.getResource("folder.png")));
		buttonSave = new JButton(new ImageIcon(NotepadFrame.class.getResource("filesave.png")));
		buttonSaveAs = new JButton(new ImageIcon(NotepadFrame.class.getResource("filesaveas.png")));
		buttonPrint = new JButton(new ImageIcon(NotepadFrame.class.getResource("fileprint.png")));
		
		buttonUndo = new JButton(new ImageIcon(NotepadFrame.class.getResource("undo.png")));
		buttonRedo = new JButton(new ImageIcon(NotepadFrame.class.getResource("redo.png")));
		buttonCut = new JButton(new ImageIcon(NotepadFrame.class.getResource("editcut.png")));
		buttonCopy = new JButton(new ImageIcon(NotepadFrame.class.getResource("editcopy.png")));
		buttonPaste = new JButton(new ImageIcon(NotepadFrame.class.getResource("editpaste.png")));
		buttonZoomIn = new JButton(new ImageIcon(NotepadFrame.class.getResource("zoomin.png")));
		buttonZoomOut = new JButton(new ImageIcon(NotepadFrame.class.getResource("zoomout.png")));
		buttonRun = new JButton(new ImageIcon(NotepadFrame.class.getResource("player_play.png")));
		
		//���ð�ť����
		buttonNew.setActionCommand("�½�");
		buttonOpen.setActionCommand("��");
		buttonSave.setActionCommand("����");
		buttonSaveAs.setActionCommand("���Ϊ");
		buttonPrint.setActionCommand("��ӡ");
		
		buttonUndo.setActionCommand("����");
		buttonRedo.setActionCommand("������");
		buttonCut.setActionCommand("����");
		buttonCopy.setActionCommand("����");
		buttonPaste.setActionCommand("ճ��");
		buttonZoomIn.setActionCommand("�Ŵ�");
		buttonZoomOut.setActionCommand("��С");
		buttonRun.setActionCommand("����");
		
		//Ϊ��ť���Ӹ�������
		buttonNew.setToolTipText("�½���ǩ");
		buttonOpen.setToolTipText("���ļ�");
		buttonSave.setToolTipText("����");
		buttonSaveAs.setToolTipText("���Ϊ");
		buttonPrint.setToolTipText("��ӡ");
		
		buttonUndo.setToolTipText("����");
		buttonRedo.setToolTipText("������");
		buttonCut.setToolTipText("����");
		buttonCopy.setToolTipText("����");
		buttonPaste.setToolTipText("ճ��");
		buttonZoomIn.setToolTipText("�Ŵ�");
		buttonZoomOut.setToolTipText("��С");
		buttonRun.setToolTipText("���벢����Java����");
		
		
		//Ϊ��������ť�����¼���Ӧ
		buttonNew.addActionListener(this);
		buttonOpen.addActionListener(this);
		buttonSave.addActionListener(this);
		buttonSaveAs.addActionListener(this);
		buttonPrint.addActionListener(this);
		
		buttonUndo.addActionListener(this);
		buttonRedo.addActionListener(this);
		buttonCut.addActionListener(this);
		buttonCopy.addActionListener(this);
		buttonPaste.addActionListener(this);
		buttonZoomIn.addActionListener(this);
		buttonZoomOut.addActionListener(this);

		buttonRun.addActionListener(this);
		
		//����ť���ӵ���������
		toolBar.add(buttonNew);
		toolBar.add(buttonOpen);
		toolBar.add(buttonSave);
		toolBar.add(buttonSaveAs);
		toolBar.add(buttonPrint);
		toolBar.addSeparator();
		toolBar.add(buttonUndo);
		toolBar.add(buttonRedo);
		toolBar.addSeparator();
		toolBar.add(buttonCut);
		toolBar.add(buttonCopy);
		toolBar.add(buttonPaste);
		toolBar.addSeparator();
		toolBar.add(buttonZoomIn);
		toolBar.add(buttonZoomOut);
		toolBar.addSeparator();
		toolBar.add(buttonRun);
		
		//�½��Ҽ��˵�
		popupMenu=new JPopupMenu();
		pitemUndo = new JMenuItem("����(U)",KeyEvent.VK_U);
		pitemRedo = new JMenuItem("������(E)",KeyEvent.VK_E);
		pitemCut = new JMenuItem("����(T)",KeyEvent.VK_T);	
		pitemCopy = new JMenuItem("����(C)",KeyEvent.VK_C);
		pitemPaste = new JMenuItem("ճ��(P)",KeyEvent.VK_P);
		pitemDelete = new JMenuItem("ɾ��(L)",KeyEvent.VK_L);
		pitemAll = new JMenuItem("ȫѡ(A)",KeyEvent.VK_A);
		popupMenu.add(pitemUndo);
		popupMenu.add(pitemRedo);
		popupMenu.addSeparator();
		popupMenu.add(pitemCut);
		popupMenu.add(pitemCopy);
		popupMenu.add(pitemPaste);
		popupMenu.add(pitemDelete);
		popupMenu.addSeparator();
		popupMenu.add(pitemAll);
		pitemUndo.addActionListener(this);
		pitemRedo.addActionListener(this);
		pitemCut.addActionListener(this);
		pitemCopy.addActionListener(this);
		pitemPaste.addActionListener(this);
		pitemDelete.addActionListener(this);
		pitemAll.addActionListener(this);
		pitemAll.setActionCommand("ȫѡ");
		pitemUndo.setActionCommand("����");
		pitemRedo.setActionCommand("������");
		pitemCut.setActionCommand("����");
		pitemCopy.setActionCommand("����");
		pitemPaste.setActionCommand("ճ��");
		pitemDelete.setActionCommand("ɾ��");
		
		//��Ӳ˵���������
		this.add(toolBar,BorderLayout.NORTH);
		
		//�����ı���
		myTextPanel=new MyTextPanel(this,pro);
		myTextPanel.setSave(false);
		myTextPanel.setUndo(false);
		myTextPanel.setRedo(false);
		//�����м����
		centerPanel=new JTabbedPane();
		JPanel tabPanel=new JPanel();
		tabPanel.setBackground(new Color(255,255,255));
		JLabel tabName=new JLabel("new_1");//String.valueOf(centerPanel.getTabCount())
		JButton closeButton=new JButton(new ImageIcon(NotepadFrame.class.getResource("close.png")));
		closeButton.addActionListener(this);
		closeButton.setActionCommand("�ر�ѡ�");
		closeButton.setPreferredSize(new Dimension(15,15));
		tabPanel.add(tabName);
		tabPanel.add(closeButton);
		
		//�����м����
		centerPanel.addChangeListener(this);
		
		//����ı������м����
		centerPanel.add(myTextPanel);
		centerPanel.setEnabledAt(0, true);//���ñ�ǩ����	
		centerPanel.setToolTipTextAt(0, "new_1");
		centerPanel.setTabComponentAt(0,tabPanel);//���ñ�ǩ��
		//����м���嵽����
		this.add(centerPanel,BorderLayout.CENTER);
		
		//״̬��
		statusBar = new JLabel(" ");
		this.add(statusBar, BorderLayout.SOUTH);
		
		
		
		try
	    {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(popupMenu);
	    }catch(Exception e){}
		
		//�Ի���
		findDialog=new FindDialog(this,"����",false);
		replaceDialog=new ReplaceDialog(this,"�滻",false);
		
		gotoDialog=new GotoDialog(this,"ת��ָ����",true);
		
		
		//���ò��ɵ����ѡ��
		itemSave.setEnabled(false);
		itemUndo.setEnabled(false);
		itemRedo.setEnabled(false);
		itemCut.setEnabled(false);
		itemCopy.setEnabled(false);
		itemDelete.setEnabled(false);
		itemFind.setEnabled(false);
		itemFindNext.setEnabled(false);
		buttonUndo.setEnabled(false);
		buttonRedo.setEnabled(false);
		buttonCut.setEnabled(false);
		buttonCopy.setEnabled(false);
		buttonSave.setEnabled(false);
		pitemUndo.setEnabled(false);
		pitemRedo.setEnabled(false);
		pitemCut.setEnabled(false);
		pitemCopy.setEnabled(false);
		pitemDelete.setEnabled(false);
		

		
		//���ÿɼ�
		setTitle(centerPanel.getToolTipTextAt(centerPanel.getSelectedIndex())+" - NotepadForJava");
		validate();
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String src = e.getActionCommand();
		switch(src)
		{
			case "�ر�ѡ�":
			{
				int nowNum=centerPanel.getTabCount();
				int selTab=centerPanel.indexOfTabComponent(((Component) e.getSource()).getParent());
				if(nowNum>1)
				{
					this.closeTab(selTab);
				}
				break;
			}
			case "�½�" :
				newTab();
				break;
			case "��" :
				openFile("");
				break;
			case "����" :
				saveField();
			    break;
			case "���Ϊ" :
			{
				int selectedIndex = centerPanel.getSelectedIndex();
				String fileName=centerPanel.getToolTipTextAt(selectedIndex);
				int rVal;
				JFileChooser c=new JFileChooser();
				if(fileName.indexOf('\\')!=-1)
				{	
					rVal = c.showSaveDialog(this);
					if (rVal == JFileChooser.APPROVE_OPTION) {
						//��ȡ�ļ���
						fileName = c.getSelectedFile().getAbsolutePath();
						
						try{
							JTextPane nowText=((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane();
							FileWriter fw = new FileWriter(fileName);
							fw.write(nowText.getText());
							fw.close();
						}
						catch(IOException ep){
							JOptionPane.showConfirmDialog(this, ep.toString(), "�����ļ�����", JOptionPane.YES_NO_OPTION);
						}			
						openFile(fileName);
					}	
				}
				else
				{
					saveField();
				}
			   
			}
			break;
			
			case "δ���":
			{
				JOptionPane.showMessageDialog(this,"����δ���");
				break;
			}
			case "�˳�":
			{
				int tabNum=centerPanel.getTabCount();
				for(int i=tabNum-1;i>0;i--)
				{
					centerPanel.setSelectedIndex(i);
					this.closeTab(i);
				}
				if(itemSave.isEnabled())
				{
					int opt=JOptionPane.showConfirmDialog(this,"�Ƿ񱣴��ļ��� "+centerPanel.getToolTipTextAt(0),"�˳�",JOptionPane.YES_NO_CANCEL_OPTION);
					switch(opt)
					{
					case JOptionPane.YES_OPTION:this.saveField();this.dispose();break;
					case JOptionPane.NO_OPTION:this.dispose();break;
					}
				}
				else this.dispose();
				break;
			}				
			case "����":
				((MyTextPanel) centerPanel.getSelectedComponent()).myUndo();
				break;				
				
			case "������":
				((MyTextPanel) centerPanel.getSelectedComponent()).myRedo();
				break;
				
			case "����":
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().cut();
				break;				
				
			case "����":
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().copy();
				break;				
			
			case "ճ��":
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().paste();
				itemCut.setEnabled(false);	
				itemCopy.setEnabled(false);	
				itemDelete.setEnabled(false);	
				buttonCut.setEnabled(false);	
				buttonCopy.setEnabled(false);
				break;
				
			case "ɾ��":
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().replaceSelection("");
				itemCut.setEnabled(false);	
				itemCopy.setEnabled(false);	
				itemDelete.setEnabled(false);	
				buttonCut.setEnabled(false);	
				buttonCopy.setEnabled(false);
				break;				
			
			case "����":
				findDialog.setVisible(true);
				break;				
			
			case "������һ��":
			{
				if(e.getSource()==replaceDialog.getfindButton())
					((MyTextPanel) centerPanel.getSelectedComponent()).findStr(replaceDialog.getFindStr(),replaceDialog.getcaps(),0);
				else
				{
					Boolean isnull=false;
					String a=null;
					try
					{
						a=findDialog.getField().getText();
					} catch(NullPointerException ne){
						isnull=true;
					}
					if(isnull==false && a.equals("")) isnull=true;
					if(isnull==true && !findDialog.isActive()) findDialog.setVisible(true);
					else
					{
						String willFindStr=findDialog.getField().getText();
						Boolean cpasState=findDialog.getCheckBox().isSelected();
						int upOfDown=findDialog.getIsUp();
						((MyTextPanel) centerPanel.getSelectedComponent()).findStr(willFindStr,cpasState,upOfDown);
					}	
				}	
				break;
			}
			
			case "�滻":
				replaceDialog.setVisible(true);
				break;				
			
			case "ת��":
				gotoDialog.setVisible(true);
				break;		
				
			case "ת������":
				String lineStr=gotoDialog.getLine();
				int linenum;
				if(isNumeric(lineStr))
				{
					linenum=Integer.parseInt(lineStr)-1;
					Document nowDoc=((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().getDocument();
					String nowDocStr="";
					try {
							nowDocStr=nowDoc.getText(0, nowDoc.getLength());
					} catch (BadLocationException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
					int nowline=0;
					for(int i=0;i<linenum;i++)
					{
						nowline=nowDocStr.indexOf('\n', nowline);
						if(nowline==-1) break;
						else nowline++;
					}
					if(nowline==-1) JOptionPane.showMessageDialog(this,"����������������");
					else if(linenum<=-1) JOptionPane.showMessageDialog(this,"���벻�Ϸ�");
					else
					{
						gotoDialog.dispose();
						((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().setCaretPosition(nowline);
					}
				}
				else JOptionPane.showMessageDialog(this,"���벻�Ϸ�");
				break;
				
			case "ȫѡ":
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().selectAll();
				break;				
			
			case "ʱ��":
				GregorianCalendar calendar=new GregorianCalendar();
				String nowTime=String.valueOf(calendar.get(Calendar.HOUR))+":"+String.valueOf(calendar.get(Calendar.MINUTE))+" "+String.valueOf(calendar.get(Calendar.YEAR))+"/"+String.valueOf(calendar.get(Calendar.MONTH))+"/"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().replaceSelection(nowTime);
				break;				
			
			case "����":
				fontDialog=new FontDialog(this,"����",true,((MyTextPanel) centerPanel.getSelectedComponent()));
				fontDialog.setVisible(true);
				break;				
			
			case "רҵ":
				if(itemPro.isSelected())
				{
					pro=true;
					((MyTextPanel) centerPanel.getSelectedComponent()).addPro();
				}
				else
				{
					pro=false;
					((MyTextPanel) centerPanel.getSelectedComponent()).removePro();
				}
				break;				
			
			case "״̬��":
				if(itemStatusBar.isSelected()) statusBar.setVisible(true);
				else statusBar.setVisible(false);
				break;				
			
			case "�����":
				if(itemSideBar.isSelected()) ((MyTextPanel) centerPanel.getSelectedComponent()).addSideBar();
				else ((MyTextPanel) centerPanel.getSelectedComponent()).removeSideBar();
				break;				
			
			case "�Ŵ�":
				((MyTextPanel) centerPanel.getSelectedComponent()).zoominFont();
				break;				
			
			case "��С":
				((MyTextPanel) centerPanel.getSelectedComponent()).zoomoutFont();
				break;				
			
			case "����":
				String s=saveField();	
				if(s.lastIndexOf(".java")!=s.length()-5)
				{
					JOptionPane.showMessageDialog(this,"���е��ļ������Ϲ淶","ʧ��",JOptionPane.INFORMATION_MESSAGE);
					break;
				}
				String letter=s.substring(0,1);
				String path=s.substring(0, s.lastIndexOf('\\')+1);
				String javaFile=s.substring(s.lastIndexOf('\\')+1);
				String classFile=javaFile.substring(0,javaFile.lastIndexOf(".java"));
				String batFile=path+classFile+".bat";
				
				String com0=letter+": \r\n";
				String com1="cd "+path+" \r\n";
				String com2="javac "+javaFile+" \r\n";
				String com3="java "+classFile+" \r\n";
				String com4="pause"+" \r\n";
				String com5="exit";
				
				String bat=com0+com1+com2+com3+com4+com5;
				try {
					FileWriter fw = new FileWriter(batFile);
					fw.write(bat);
					fw.close();
				} catch (IOException e2) {
					// TODO �Զ����ɵ� catch ��
					e2.printStackTrace();
				}
				try {
					Runtime r = Runtime.getRuntime();
					r.exec("cmd /c start "+batFile);
				} catch (IOException e1) {e1.printStackTrace();}
				break;				
			
			case "����":
				String helpStr="��飺\n        NotepadForJava��һ��򵥵�JavaIDE��ӵ�йؼ��ʸ������Զ��������Զ���ȫ���ŵȷ����̵Ĺ��ܡ�\n�������ϵͳ�Ļ����������潫Java��Path���úã���ô����ֱ����NotepadForJava�����������벢���а�\nť�����б������С�\n        ����ر�רҵģʽ��������Ϊһ����ͨ�ļ��±�������֧�ֶ��ǩ��������ʾ��\n\n";
				helpStr+="�������⣺\n>>ʲô��רҵģʽ��\nרҵģʽ�������ı����Զ�����Java������﷨���������س���ʱ����ݵ�ǰ�е�״̬����һ�н���������\n������{[(�⼸������ʱ�Ὣ��Ӧ����һ�����Ų�ȫ��\n\n";
				helpStr+=">>Ϊʲô���޷����������Java����\n���ڡ��ҵĵ��ԡ��Ҽ�-����-�߼�ϵͳ����-��������-ϵͳ����-Path ��������JDK��Bin��ַ���������ԡ�\n\n";
				helpStr+="ע�⣺�ð汾��δʵ�ִ�ӡ���ܡ�\n";
				JOptionPane.showMessageDialog(this,helpStr,"����",JOptionPane.PLAIN_MESSAGE);
				break;				
			
			case "����":
				JOptionPane.showMessageDialog(this,new JLabel("<html>NotepadForJava Beta1<br /><b>By С��ܵ�</b></html>"),"����",JOptionPane.PLAIN_MESSAGE);
				break;					
				
			case "�رնԻ���":
				((JDialog) ((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent()).dispose();
				break;					
				
			case "�滻���ַ���":
				if(replaceDialog.getFindStr().equals(""))
				{
					JOptionPane.showMessageDialog(replaceDialog,"Ҫ���ҵ��ַ���Ϊ��","��Ϣ",JOptionPane.PLAIN_MESSAGE);
				}
				else if(replaceDialog.getFindStr().equals(((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().getSelectedText()))
				{
					((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().replaceSelection(replaceDialog.getReplaceStr());
					((MyTextPanel) centerPanel.getSelectedComponent()).findStr(replaceDialog.getFindStr(),replaceDialog.getcaps(),0);
				}
				else if(((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().getSelectedText()==null)
				{
					((MyTextPanel) centerPanel.getSelectedComponent()).findStr(replaceDialog.getFindStr(),replaceDialog.getcaps(),0);
				}
				break;
				
			case "�滻ȫ��":
			{
				String findStr=replaceDialog.getFindStr();
				if(findStr.equals("")) 
				{
					JOptionPane.showMessageDialog(replaceDialog,"Ҫ���ҵ��ַ���Ϊ��","��Ϣ",JOptionPane.PLAIN_MESSAGE);
					break;
				}
				JTextPane nowTextPane=((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane();
				Document nowDoc=nowTextPane.getDocument();
				String nowDocStr=null;
				int textLen=nowDoc.getLength();
				int beg=0;
				int end=nowDoc.getLength();
				if(replaceDialog.getinArea())
				{
					beg=nowTextPane.getSelectionStart();
					end=nowTextPane.getSelectionEnd();
				}
				String replaceStr=replaceDialog.getReplaceStr();
				int len=end-beg;
				try {
					nowDocStr=nowDoc.getText(beg, len);
				} catch (BadLocationException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				int nowbeg=0;
				if(replaceDialog.getcaps()==false)
				{
					nowDocStr=nowDocStr.toLowerCase(Locale.ENGLISH);
					findStr=findStr.toLowerCase(Locale.ENGLISH);
				}
				while(true)
				{
					nowbeg=nowDocStr.indexOf(findStr,nowbeg);
					if(nowbeg==-1) break;
					else
					{ 
						nowTextPane.select(beg+nowbeg-(textLen-nowTextPane.getDocument().getLength()),beg+nowbeg-(textLen-nowTextPane.getDocument().getLength())+findStr.length());
						nowTextPane.replaceSelection(replaceStr);
						nowbeg=nowbeg+findStr.length();
					}
				}
				JOptionPane.showMessageDialog(replaceDialog,"�滻���","��Ϣ",JOptionPane.PLAIN_MESSAGE);
					
			}
			break;	
			case "��ӡ":
			try {
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().print();
			} catch (PrinterException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			break;
		}
	}
	
	public String saveField()
	{
		int selectedIndex = centerPanel.getSelectedIndex();
		String fileName=centerPanel.getToolTipTextAt(selectedIndex);
		int rVal;
		JFileChooser c;
		if(fileName.indexOf('\\')!=-1)
		{	
			try{
				JTextPane nowText=((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane();
				FileWriter fw = new FileWriter(fileName);
				fw.write(nowText.getText());
				fw.close();
				itemSave.setEnabled(false);
				buttonSave.setEnabled(false);
				((MyTextPanel) centerPanel.getSelectedComponent()).setSave(false);
			}
			catch(IOException ep){
				JOptionPane.showConfirmDialog(this, ep.toString(), "�����ļ�����", JOptionPane.YES_NO_OPTION);
			}
			return fileName;
		}
		else
		{
			fileName=new String();
			c = new JFileChooser();
			//��ʾ����Ի���
			rVal = c.showSaveDialog(this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				//��ȡ�ļ���
				fileName = c.getSelectedFile().getAbsolutePath();
				int begin=0,end=fileName.length();
				for(int i=end-1;i>0;i--)
					if(fileName.charAt(i)=='\\')
					{
						begin=i+1;
						break;
					}
				String partName=fileName.substring(begin, end);
				
				try{
					JTextPane nowText=((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane();
					FileWriter fw = new FileWriter(fileName);
					fw.write(nowText.getDocument().getText(0,nowText.getDocument().getLength()));
					fw.close();
										
					
					JPanel tabPanel=new JPanel();
					tabPanel.setBackground(new Color(255,255,255));
					JLabel tabName=new JLabel(partName);
					JButton closeButton=new JButton(new ImageIcon(NotepadFrame.class.getResource("close.png")));
					closeButton.addActionListener(this);
					closeButton.setActionCommand("�ر�ѡ�");
					closeButton.setPreferredSize(new Dimension(15,15));
					tabPanel.add(tabName);
					tabPanel.add(closeButton);
					
					centerPanel.setTabComponentAt(selectedIndex,tabPanel);
					centerPanel.setToolTipTextAt(selectedIndex,fileName);
					setTitle(centerPanel.getToolTipTextAt(selectedIndex)+" - NotepadForJava");
					
					itemSave.setEnabled(false);
					buttonSave.setEnabled(false);
					((MyTextPanel) centerPanel.getSelectedComponent()).setSave(false);
				}
				catch(IOException ep){
					JOptionPane.showConfirmDialog(this, ep.toString(), "�����ļ�����", JOptionPane.YES_NO_OPTION);
				}
				catch(BadLocationException ep){}
				return fileName;
			}	
		}
		return fileName;
	}
	
	public void newTab()
	{
		myTextPanel=new MyTextPanel(this,pro);
		JPanel tabPanel=new JPanel();
		tabPanel.setBackground(new Color(255,255,255));
		JLabel tabName=new JLabel("new_"+String.valueOf(centerPanel.getTabCount()+1));				
		JButton closeButton=new JButton(new ImageIcon(NotepadFrame.class.getResource("close.png")));
		closeButton.addActionListener(this);
		closeButton.setActionCommand("�ر�ѡ�");
		closeButton.setPreferredSize(new Dimension(15,15));
		tabPanel.add(tabName);
		tabPanel.add(closeButton);
		centerPanel.add(myTextPanel);
		centerPanel.setTabComponentAt(centerPanel.getTabCount()-1,tabPanel);//���ñ�ǩ��
		centerPanel.setSelectedIndex(centerPanel.getTabCount()-1);
		
		centerPanel.setToolTipTextAt(centerPanel.getTabCount()-1,tabName.getText());
		setTitle(centerPanel.getToolTipTextAt(centerPanel.getSelectedIndex())+" - NotepadForJava");
		
		itemSave.setEnabled(false);
		itemUndo.setEnabled(false);
		itemRedo.setEnabled(false);
		itemFind.setEnabled(false);
		itemFindNext.setEnabled(false);
		buttonSave.setEnabled(false);
		
		((MyTextPanel) centerPanel.getSelectedComponent()).setSave(false);
		((MyTextPanel) centerPanel.getSelectedComponent()).setUndo(false);
		((MyTextPanel) centerPanel.getSelectedComponent()).setRedo(false);
	}
	
	public void openFile(String willOpen)
	{			
		String fileName;
		int rVal;
		JFileChooser c;
		fileName=new String();
		c = new JFileChooser();
		
		if(willOpen.equals(""))
		{
			rVal = c.showOpenDialog(this);
			if (rVal == JFileChooser.APPROVE_OPTION) fileName = c.getSelectedFile().getAbsolutePath();
			else return;
		}
		else fileName=willOpen;
		int begin=0,end=fileName.length();
		for(int i=end-1;i>0;i--)
			if(fileName.charAt(i)=='\\')
			{
				begin=i+1;
				break;
			}
		String partName=fileName.substring(begin, end);
		try{
			myTextPanel=new MyTextPanel(this,pro);
			JPanel tabPanel=new JPanel();
			tabPanel.setBackground(new Color(255,255,255));
			JLabel tabName=new JLabel(partName);				
			JButton closeButton=new JButton(new ImageIcon(NotepadFrame.class.getResource("close.png")));
			closeButton.addActionListener(this);
			closeButton.setActionCommand("�ر�ѡ�");
			closeButton.setPreferredSize(new Dimension(15,15));
			tabPanel.add(tabName);
			tabPanel.add(closeButton);
			centerPanel.add(myTextPanel);
			centerPanel.setTabComponentAt(centerPanel.getTabCount()-1,tabPanel);//���ñ�ǩ��
			centerPanel.setSelectedIndex(centerPanel.getTabCount()-1);
			((MyTextPanel) centerPanel.getSelectedComponent()).setSave(false);
			((MyTextPanel) centerPanel.getSelectedComponent()).setUndo(false);
			((MyTextPanel) centerPanel.getSelectedComponent()).setRedo(false);
			centerPanel.setToolTipTextAt(centerPanel.getTabCount()-1,fileName);
			setTitle(centerPanel.getToolTipTextAt(centerPanel.getSelectedIndex())+" - NotepadForJava");
			
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			
			byte b[] = new byte[(int)file.length()];
			fis.read(b);
			fis.close();
			JTextPane nowText=((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane();
			nowText.replaceSelection(new String(b));
			
			Element map = nowText.getDocument().getDefaultRootElement();				    
			
			statusBar.setText("�ı��ַ�����" + nowText.getDocument().getLength()+"    ��������"+map.getElementCount());
			setTitle(fileName+" - NotepadForJava");
			
			itemFind.setEnabled(true);
			itemFindNext.setEnabled(true);
			itemSave.setEnabled(false);
			itemUndo.setEnabled(false);
			itemRedo.setEnabled(false);
			buttonSave.setEnabled(false);
			
			((MyTextPanel) centerPanel.getSelectedComponent()).setSave(false);
			((MyTextPanel) centerPanel.getSelectedComponent()).setUndo(false);
			((MyTextPanel) centerPanel.getSelectedComponent()).setRedo(false);
			
		}
		catch(IOException ep){
			JOptionPane.showConfirmDialog(this, ep.toString(), "���ļ�����", JOptionPane.YES_NO_OPTION);
		}	
	}	
	
	//�ı�������
	public void changedUpdate(DocumentEvent e)
	{
		JTextPane nowText=((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane();
		Element map = nowText.getDocument().getDefaultRootElement();				    
		statusBar.setText("�ı��ַ�����" + nowText.getDocument().getLength()+"    ��������"+map.getElementCount());
		//���ı��������˸��ģ������б���
		itemSave.setEnabled(true);
		buttonSave.setEnabled(true);
		buttonUndo.setEnabled(true);
		buttonRedo.setEnabled(true);
		itemFind.setEnabled(true);
		itemFindNext.setEnabled(true);
		itemUndo.setEnabled(true);
		buttonUndo.setEnabled(true);
		itemRedo.setEnabled(true);
		buttonRedo.setEnabled(true);
		pitemUndo.setEnabled(true);
		pitemRedo.setEnabled(true);
		pitemCut.setEnabled(true);
		pitemCopy.setEnabled(true);
		pitemDelete.setEnabled(true);
		
		((MyTextPanel) centerPanel.getSelectedComponent()).setSave(true);
		((MyTextPanel) centerPanel.getSelectedComponent()).setUndo(true);
		((MyTextPanel) centerPanel.getSelectedComponent()).setRedo(true);
		if(((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().getDocument().getLength()==0)
		{
			itemFind.setEnabled(false);
			itemFindNext.setEnabled(false);
		}
	}
	//�ı���ɾ���ַ�
	public void removeUpdate(DocumentEvent e)
	{
		changedUpdate(e);
	}
	//�ı��������ַ�
	public void insertUpdate(DocumentEvent e)
	{
		changedUpdate(e);
	}

	public void stateChanged(ChangeEvent e) 
	{
		setTitle(centerPanel.getToolTipTextAt(centerPanel.getSelectedIndex())+" - NotepadForJava");
		MyTextPanel nowPanel=((MyTextPanel) centerPanel.getSelectedComponent());
		itemUndo.setEnabled(nowPanel.getUndoState());
		buttonUndo.setEnabled(nowPanel.getUndoState());
		pitemUndo.setEnabled(nowPanel.getUndoState());
		itemRedo.setEnabled(nowPanel.getRedoState());		
		buttonRedo.setEnabled(nowPanel.getRedoState());
		pitemRedo.setEnabled(nowPanel.getRedoState());
		itemSave.setEnabled(nowPanel.getSaveState());
		buttonSave.setEnabled(nowPanel.getSaveState());
		
		if(((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().getDocument().getLength()==0)
		{
			itemFind.setEnabled(false);
			itemFindNext.setEnabled(false);
		}
		else
		{
			itemFind.setEnabled(true);
			itemFindNext.setEnabled(true);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO �Զ����ɵķ������
		if(((JTextPane) e.getSource()).getSelectedText()!=null)
		{
			itemCut.setEnabled(true);	
			itemCopy.setEnabled(true);	
			itemDelete.setEnabled(true);	
			buttonCut.setEnabled(true);	
			buttonCopy.setEnabled(true);
			pitemCut.setEnabled(true);
			pitemCopy.setEnabled(true);
			pitemDelete.setEnabled(true);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO �Զ����ɵķ������
		if(((JTextPane) e.getSource()).getSelectedText()==null)
		{
			itemCut.setEnabled(false);	
			itemCopy.setEnabled(false);	
			itemDelete.setEnabled(false);	
			buttonCut.setEnabled(false);	
			buttonCopy.setEnabled(false);
			pitemCut.setEnabled(false);
			pitemCopy.setEnabled(false);
			pitemDelete.setEnabled(false);
		}
		else
		{
			itemCut.setEnabled(true);	
			itemCopy.setEnabled(true);	
			itemDelete.setEnabled(true);	
			buttonCut.setEnabled(true);	
			buttonCopy.setEnabled(true);
			pitemCut.setEnabled(true);
			pitemCopy.setEnabled(true);
			pitemDelete.setEnabled(true);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO �Զ����ɵķ������
		if (e.getButton() == MouseEvent.BUTTON3 ) {
			popupMenu.show((Component) e.getSource(), e.getX(), e.getY());  
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO �Զ����ɵķ������
		if(((JTextPane) e.getSource()).getSelectedText()==null)
		{
			itemCut.setEnabled(false);	
			itemCopy.setEnabled(false);	
			itemDelete.setEnabled(false);	
			buttonCut.setEnabled(false);	
			buttonCopy.setEnabled(false);
			pitemCut.setEnabled(false);
			pitemCopy.setEnabled(false);
			pitemDelete.setEnabled(false);
		}
		else
		{
			itemCut.setEnabled(true);	
			itemCopy.setEnabled(true);	
			itemDelete.setEnabled(true);	
			buttonCut.setEnabled(true);	
			buttonCopy.setEnabled(true);
			pitemCut.setEnabled(true);
			pitemCopy.setEnabled(true);
			pitemDelete.setEnabled(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO �Զ����ɵķ������
		if(((JTextPane) e.getSource()).getSelectedText()==null)
		{
			itemCut.setEnabled(false);	
			itemCopy.setEnabled(false);	
			itemDelete.setEnabled(false);	
			buttonCut.setEnabled(false);	
			buttonCopy.setEnabled(false);
			pitemCut.setEnabled(false);
			pitemCopy.setEnabled(false);
			pitemDelete.setEnabled(false);
		}
		else
		{
			itemCut.setEnabled(true);	
			itemCopy.setEnabled(true);	
			itemDelete.setEnabled(true);	
			buttonCut.setEnabled(true);	
			buttonCopy.setEnabled(true);
			pitemCut.setEnabled(true);
			pitemCopy.setEnabled(true);
			pitemDelete.setEnabled(true);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO �Զ����ɵķ������
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO �Զ����ɵķ������
		int tabNum=centerPanel.getTabCount();
		for(int i=tabNum-1;i>0;i--)
		{
			centerPanel.setSelectedIndex(i);
			this.closeTab(i);
		}
		if(itemSave.isEnabled())
		{
			int opt=JOptionPane.showConfirmDialog(this,"�Ƿ񱣴��ļ��� "+centerPanel.getToolTipTextAt(0),"�˳�",JOptionPane.YES_NO_CANCEL_OPTION);
			switch(opt)
			{
			case JOptionPane.YES_OPTION:this.saveField();this.dispose();break;
			case JOptionPane.NO_OPTION:this.dispose();break;
			}
		}
		else this.dispose();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	
	public static boolean isNumeric(String str){ 
		try { 
		Integer.parseInt(str); 
		return true; 
		} catch (NumberFormatException e) { 
		// TODO Auto-generated catch block 
		return false; 
		} 
		} 
	
	public void closeTab(int selTab)
	{
		
		if(itemSave.isEnabled())
		{
			int opt=JOptionPane.showConfirmDialog(this,"�Ƿ񱣴��ļ��� "+centerPanel.getToolTipTextAt(selTab),"�˳�",JOptionPane.YES_NO_CANCEL_OPTION);
			switch(opt)
			{
			case JOptionPane.YES_OPTION:this.saveField();centerPanel.remove(selTab);break;
			case JOptionPane.NO_OPTION:centerPanel.remove(selTab);break;
			}
		}
		else centerPanel.remove(selTab);
	}

}

public class NotepadForJava 
{
	//����JFrame
	static void createAndShowGUI()
	{
		
		JFrame frame=new NotepadFrame();
	    //���ý�����ΪWindows���
		try
	    {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(frame);
	    }catch(Exception e){}
	}
	public static void main(String args[])
	{
		//�¼������̻߳������
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
