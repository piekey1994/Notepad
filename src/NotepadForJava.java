

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
	//菜单栏
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
	
	//工具栏
	JToolBar toolBar;
	JButton buttonNew,buttonOpen,buttonSave,buttonSaveAs,buttonPrint;
	JButton buttonUndo,buttonRedo,buttonCut,buttonCopy,buttonPaste;
	JButton buttonZoomIn,buttonZoomOut;
	JButton buttonRun;
	
	//文本区
	MyTextPanel myTextPanel;
	Font f;
	
	//中面板
	JTabbedPane centerPanel;
	
	//状态栏
	JLabel statusBar;
	
	//寄存器
	String copyStr;
	Boolean pro;
	
	//对话框
	FindDialog findDialog;
	JButton findInDialog;
	ReplaceDialog replaceDialog;
	GotoDialog gotoDialog;
	FontDialog fontDialog;
	
	//右键菜单
	JPopupMenu popupMenu;
	JMenuItem pitemUndo;
	JMenuItem pitemRedo;
	JMenuItem pitemCut;
	JMenuItem pitemCopy;
	JMenuItem pitemPaste;
	JMenuItem pitemDelete;
	JMenuItem pitemAll;
		
	//构造函数
	NotepadFrame()
	{
		pro=true;
		
		setSize(800,600);
		setLocation(250,250);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(true);
		this.addWindowListener(this);
		
		//创建菜单
		menuBar=new JMenuBar();
		menuFile = new JMenu("文件(F)"); 
		menuEdit = new JMenu("编辑(E)"); 
		menuStyle = new JMenu("格式(O)");
		menuView = new JMenu("查看(V)");
		menuFunction = new JMenu("功能(G)");
		menuHelp = new JMenu("帮助(H)"); 
		
		itemNew = new JMenuItem("新建(N)",KeyEvent.VK_N);
		itemOpen = new JMenuItem("打开(O)",KeyEvent.VK_O);
		itemSave = new JMenuItem("保存(S)",KeyEvent.VK_S);
		itemSaveAs = new JMenuItem("另存为(A)",KeyEvent.VK_A);		
		itemPrint = new JMenuItem("打印(P)",KeyEvent.VK_P);
		itemExit = new JMenuItem("退出(X)",KeyEvent.VK_X);
		
		itemUndo = new JMenuItem("撤销(U)",KeyEvent.VK_U);
		itemRedo = new JMenuItem("反撤销(E)",KeyEvent.VK_E);
		itemCut = new JMenuItem("剪切(T)",KeyEvent.VK_T);	
		itemCopy = new JMenuItem("复制(C)",KeyEvent.VK_C);
		itemPaste = new JMenuItem("粘贴(P)",KeyEvent.VK_P);
		itemDelete = new JMenuItem("删除(L)",KeyEvent.VK_L);
		itemFind = new JMenuItem("查找(F)",KeyEvent.VK_F);
		itemFindNext = new JMenuItem("查找下一个(N)",KeyEvent.VK_N);
		itemReplace = new JMenuItem("替换(R)",KeyEvent.VK_R);
		itemGoto = new JMenuItem("转到(G)",KeyEvent.VK_G);
		itemAll = new JMenuItem("全选(A)",KeyEvent.VK_A);
		itemTime = new JMenuItem("时间/日期(D)",KeyEvent.VK_D);
		
		itemFont = new JMenuItem("字体(F)",KeyEvent.VK_F);
		itemPro = new JCheckBoxMenuItem("专业模式(P)",true);
		
		itemStatusBar = new JCheckBoxMenuItem("状态栏(S)",true);
		itemSideBar = new JCheckBoxMenuItem("侧边栏(C)",true);
		
		itemZoomIn = new JMenuItem("放大(I)",KeyEvent.VK_I);
		itemZoomOut = new JMenuItem("缩小(O)",KeyEvent.VK_O);
		
		itemRun = new JMenuItem("运行(R)",KeyEvent.VK_R);

		itemHelp = new JMenuItem("查看帮助(H)",KeyEvent.VK_H);
		itemAbout = new JMenuItem("关于 NFJ(A)",KeyEvent.VK_A);
		
		//设置菜单项命令
		itemNew.setActionCommand("新建");
		itemOpen.setActionCommand("打开");
		itemSave.setActionCommand("保存");
		itemSaveAs.setActionCommand("另存为");		
		itemPrint.setActionCommand("打印");
		itemExit.setActionCommand("退出");
		
		itemUndo.setActionCommand("撤销");
		itemRedo.setActionCommand("反撤销");
		itemCut.setActionCommand("剪切");
		itemCopy.setActionCommand("复制");
		itemPaste.setActionCommand("粘贴");
		itemDelete.setActionCommand("删除");
		itemFind.setActionCommand("查找");
		itemFindNext.setActionCommand("查找下一个");
		itemReplace.setActionCommand("替换");
		itemGoto.setActionCommand("转到");
		itemAll.setActionCommand("全选");
		itemTime.setActionCommand("时间");
		
		itemFont.setActionCommand("字体");
		itemPro.setActionCommand("专业");
		
		itemStatusBar.setActionCommand("状态栏");
		itemSideBar.setActionCommand("侧边栏");
		
		itemZoomIn.setActionCommand("放大");
		itemZoomOut.setActionCommand("缩小");
		
		itemRun.setActionCommand("运行");

		itemHelp.setActionCommand("帮助");
		itemAbout.setActionCommand("关于");
		
		//设置助记符
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuEdit.setMnemonic(KeyEvent.VK_E);
		menuStyle.setMnemonic(KeyEvent.VK_O);
		menuView.setMnemonic(KeyEvent.VK_V);
		menuFunction.setMnemonic(KeyEvent.VK_G);
		menuHelp.setMnemonic(KeyEvent.VK_H);
		itemPro.setMnemonic(KeyEvent.VK_P);
		itemStatusBar.setMnemonic(KeyEvent.VK_S);
		itemSideBar.setMnemonic(KeyEvent.VK_C);
		
		
		//设置菜单项快捷键
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

		
		//为菜单项增加事件响应
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
		
		
		//为菜单增加菜单项	
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
		
		//将菜单增加到菜单栏中
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuStyle);
		menuBar.add(menuView);
		menuBar.add(menuFunction);
		menuBar.add(menuHelp);
		
		//为窗口设置菜单栏
		this.setJMenuBar(menuBar);
		
		//创建工具栏
		toolBar = new JToolBar();
		
		//创建按钮
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
		
		//设置按钮命令
		buttonNew.setActionCommand("新建");
		buttonOpen.setActionCommand("打开");
		buttonSave.setActionCommand("保存");
		buttonSaveAs.setActionCommand("另存为");
		buttonPrint.setActionCommand("打印");
		
		buttonUndo.setActionCommand("撤销");
		buttonRedo.setActionCommand("反撤销");
		buttonCut.setActionCommand("剪切");
		buttonCopy.setActionCommand("复制");
		buttonPaste.setActionCommand("粘贴");
		buttonZoomIn.setActionCommand("放大");
		buttonZoomOut.setActionCommand("缩小");
		buttonRun.setActionCommand("运行");
		
		//为按钮增加辅助文字
		buttonNew.setToolTipText("新建标签");
		buttonOpen.setToolTipText("打开文件");
		buttonSave.setToolTipText("保存");
		buttonSaveAs.setToolTipText("另存为");
		buttonPrint.setToolTipText("打印");
		
		buttonUndo.setToolTipText("撤销");
		buttonRedo.setToolTipText("反撤销");
		buttonCut.setToolTipText("剪切");
		buttonCopy.setToolTipText("复制");
		buttonPaste.setToolTipText("粘贴");
		buttonZoomIn.setToolTipText("放大");
		buttonZoomOut.setToolTipText("缩小");
		buttonRun.setToolTipText("编译并运行Java程序");
		
		
		//为工具栏按钮增加事件响应
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
		
		//将按钮增加到工具栏中
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
		
		//新建右键菜单
		popupMenu=new JPopupMenu();
		pitemUndo = new JMenuItem("撤销(U)",KeyEvent.VK_U);
		pitemRedo = new JMenuItem("反撤销(E)",KeyEvent.VK_E);
		pitemCut = new JMenuItem("剪切(T)",KeyEvent.VK_T);	
		pitemCopy = new JMenuItem("复制(C)",KeyEvent.VK_C);
		pitemPaste = new JMenuItem("粘贴(P)",KeyEvent.VK_P);
		pitemDelete = new JMenuItem("删除(L)",KeyEvent.VK_L);
		pitemAll = new JMenuItem("全选(A)",KeyEvent.VK_A);
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
		pitemAll.setActionCommand("全选");
		pitemUndo.setActionCommand("撤销");
		pitemRedo.setActionCommand("反撤销");
		pitemCut.setActionCommand("剪切");
		pitemCopy.setActionCommand("复制");
		pitemPaste.setActionCommand("粘贴");
		pitemDelete.setActionCommand("删除");
		
		//添加菜单栏到窗口
		this.add(toolBar,BorderLayout.NORTH);
		
		//创建文本区
		myTextPanel=new MyTextPanel(this,pro);
		myTextPanel.setSave(false);
		myTextPanel.setUndo(false);
		myTextPanel.setRedo(false);
		//创建中间面板
		centerPanel=new JTabbedPane();
		JPanel tabPanel=new JPanel();
		tabPanel.setBackground(new Color(255,255,255));
		JLabel tabName=new JLabel("new_1");//String.valueOf(centerPanel.getTabCount())
		JButton closeButton=new JButton(new ImageIcon(NotepadFrame.class.getResource("close.png")));
		closeButton.addActionListener(this);
		closeButton.setActionCommand("关闭选项卡");
		closeButton.setPreferredSize(new Dimension(15,15));
		tabPanel.add(tabName);
		tabPanel.add(closeButton);
		
		//监听中间面板
		centerPanel.addChangeListener(this);
		
		//添加文本区到中间面板
		centerPanel.add(myTextPanel);
		centerPanel.setEnabledAt(0, true);//设置标签可用	
		centerPanel.setToolTipTextAt(0, "new_1");
		centerPanel.setTabComponentAt(0,tabPanel);//设置标签名
		//添加中间面板到窗口
		this.add(centerPanel,BorderLayout.CENTER);
		
		//状态栏
		statusBar = new JLabel(" ");
		this.add(statusBar, BorderLayout.SOUTH);
		
		
		
		try
	    {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(popupMenu);
	    }catch(Exception e){}
		
		//对话框
		findDialog=new FindDialog(this,"查找",false);
		replaceDialog=new ReplaceDialog(this,"替换",false);
		
		gotoDialog=new GotoDialog(this,"转到指定行",true);
		
		
		//设置不可点击的选项
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
		

		
		//设置可见
		setTitle(centerPanel.getToolTipTextAt(centerPanel.getSelectedIndex())+" - NotepadForJava");
		validate();
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String src = e.getActionCommand();
		switch(src)
		{
			case "关闭选项卡":
			{
				int nowNum=centerPanel.getTabCount();
				int selTab=centerPanel.indexOfTabComponent(((Component) e.getSource()).getParent());
				if(nowNum>1)
				{
					this.closeTab(selTab);
				}
				break;
			}
			case "新建" :
				newTab();
				break;
			case "打开" :
				openFile("");
				break;
			case "保存" :
				saveField();
			    break;
			case "另存为" :
			{
				int selectedIndex = centerPanel.getSelectedIndex();
				String fileName=centerPanel.getToolTipTextAt(selectedIndex);
				int rVal;
				JFileChooser c=new JFileChooser();
				if(fileName.indexOf('\\')!=-1)
				{	
					rVal = c.showSaveDialog(this);
					if (rVal == JFileChooser.APPROVE_OPTION) {
						//获取文件名
						fileName = c.getSelectedFile().getAbsolutePath();
						
						try{
							JTextPane nowText=((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane();
							FileWriter fw = new FileWriter(fileName);
							fw.write(nowText.getText());
							fw.close();
						}
						catch(IOException ep){
							JOptionPane.showConfirmDialog(this, ep.toString(), "保存文件出错", JOptionPane.YES_NO_OPTION);
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
			
			case "未完成":
			{
				JOptionPane.showMessageDialog(this,"功能未完成");
				break;
			}
			case "退出":
			{
				int tabNum=centerPanel.getTabCount();
				for(int i=tabNum-1;i>0;i--)
				{
					centerPanel.setSelectedIndex(i);
					this.closeTab(i);
				}
				if(itemSave.isEnabled())
				{
					int opt=JOptionPane.showConfirmDialog(this,"是否保存文件： "+centerPanel.getToolTipTextAt(0),"退出",JOptionPane.YES_NO_CANCEL_OPTION);
					switch(opt)
					{
					case JOptionPane.YES_OPTION:this.saveField();this.dispose();break;
					case JOptionPane.NO_OPTION:this.dispose();break;
					}
				}
				else this.dispose();
				break;
			}				
			case "撤销":
				((MyTextPanel) centerPanel.getSelectedComponent()).myUndo();
				break;				
				
			case "反撤销":
				((MyTextPanel) centerPanel.getSelectedComponent()).myRedo();
				break;
				
			case "剪切":
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().cut();
				break;				
				
			case "复制":
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().copy();
				break;				
			
			case "粘贴":
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().paste();
				itemCut.setEnabled(false);	
				itemCopy.setEnabled(false);	
				itemDelete.setEnabled(false);	
				buttonCut.setEnabled(false);	
				buttonCopy.setEnabled(false);
				break;
				
			case "删除":
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().replaceSelection("");
				itemCut.setEnabled(false);	
				itemCopy.setEnabled(false);	
				itemDelete.setEnabled(false);	
				buttonCut.setEnabled(false);	
				buttonCopy.setEnabled(false);
				break;				
			
			case "查找":
				findDialog.setVisible(true);
				break;				
			
			case "查找下一个":
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
			
			case "替换":
				replaceDialog.setVisible(true);
				break;				
			
			case "转到":
				gotoDialog.setVisible(true);
				break;		
				
			case "转到该行":
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
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					int nowline=0;
					for(int i=0;i<linenum;i++)
					{
						nowline=nowDocStr.indexOf('\n', nowline);
						if(nowline==-1) break;
						else nowline++;
					}
					if(nowline==-1) JOptionPane.showMessageDialog(this,"行数超过了总行数");
					else if(linenum<=-1) JOptionPane.showMessageDialog(this,"输入不合法");
					else
					{
						gotoDialog.dispose();
						((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().setCaretPosition(nowline);
					}
				}
				else JOptionPane.showMessageDialog(this,"输入不合法");
				break;
				
			case "全选":
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().selectAll();
				break;				
			
			case "时间":
				GregorianCalendar calendar=new GregorianCalendar();
				String nowTime=String.valueOf(calendar.get(Calendar.HOUR))+":"+String.valueOf(calendar.get(Calendar.MINUTE))+" "+String.valueOf(calendar.get(Calendar.YEAR))+"/"+String.valueOf(calendar.get(Calendar.MONTH))+"/"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().replaceSelection(nowTime);
				break;				
			
			case "字体":
				fontDialog=new FontDialog(this,"字体",true,((MyTextPanel) centerPanel.getSelectedComponent()));
				fontDialog.setVisible(true);
				break;				
			
			case "专业":
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
			
			case "状态栏":
				if(itemStatusBar.isSelected()) statusBar.setVisible(true);
				else statusBar.setVisible(false);
				break;				
			
			case "侧边栏":
				if(itemSideBar.isSelected()) ((MyTextPanel) centerPanel.getSelectedComponent()).addSideBar();
				else ((MyTextPanel) centerPanel.getSelectedComponent()).removeSideBar();
				break;				
			
			case "放大":
				((MyTextPanel) centerPanel.getSelectedComponent()).zoominFont();
				break;				
			
			case "缩小":
				((MyTextPanel) centerPanel.getSelectedComponent()).zoomoutFont();
				break;				
			
			case "运行":
				String s=saveField();	
				if(s.lastIndexOf(".java")!=s.length()-5)
				{
					JOptionPane.showMessageDialog(this,"运行的文件不符合规范","失败",JOptionPane.INFORMATION_MESSAGE);
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
					// TODO 自动生成的 catch 块
					e2.printStackTrace();
				}
				try {
					Runtime r = Runtime.getRuntime();
					r.exec("cmd /c start "+batFile);
				} catch (IOException e1) {e1.printStackTrace();}
				break;				
			
			case "帮助":
				String helpStr="简介：\n        NotepadForJava是一款简单的JavaIDE，拥有关键词高亮，自动缩进和自动补全括号等方便编程的功能。\n如果你在系统的环境变量里面将Java的Path设置好，那么可以直接在NotepadForJava里面点击“编译并运行按\n钮”进行编译运行。\n        如果关闭专业模式，它将成为一个普通的记事本，并且支持多标签和行数显示。\n\n";
				helpStr+="常见问题：\n>>什么是专业模式？\n专业模式启动后，文本将自动进行Java代码的语法高亮，按回车键时会根据当前行的状态对下一行进行缩进，\n当输入{[(这几个符号时会将对应的另一半括号补全。\n\n";
				helpStr+=">>为什么我无法编译和运行Java程序？\n请在“我的电脑”右键-属性-高级系统设置-环境变量-系统变量-Path 里添加你的JDK的Bin地址后再做尝试。\n\n";
				helpStr+="注意：该版本并未实现打印功能。\n";
				JOptionPane.showMessageDialog(this,helpStr,"帮助",JOptionPane.PLAIN_MESSAGE);
				break;				
			
			case "关于":
				JOptionPane.showMessageDialog(this,new JLabel("<html>NotepadForJava Beta1<br /><b>By 小神弟弟</b></html>"),"关于",JOptionPane.PLAIN_MESSAGE);
				break;					
				
			case "关闭对话框":
				((JDialog) ((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent()).dispose();
				break;					
				
			case "替换该字符串":
				if(replaceDialog.getFindStr().equals(""))
				{
					JOptionPane.showMessageDialog(replaceDialog,"要查找的字符串为空","消息",JOptionPane.PLAIN_MESSAGE);
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
				
			case "替换全部":
			{
				String findStr=replaceDialog.getFindStr();
				if(findStr.equals("")) 
				{
					JOptionPane.showMessageDialog(replaceDialog,"要查找的字符串为空","消息",JOptionPane.PLAIN_MESSAGE);
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
					// TODO 自动生成的 catch 块
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
				JOptionPane.showMessageDialog(replaceDialog,"替换完成","消息",JOptionPane.PLAIN_MESSAGE);
					
			}
			break;	
			case "打印":
			try {
				((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane().print();
			} catch (PrinterException e1) {
				// TODO 自动生成的 catch 块
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
				JOptionPane.showConfirmDialog(this, ep.toString(), "保存文件出错", JOptionPane.YES_NO_OPTION);
			}
			return fileName;
		}
		else
		{
			fileName=new String();
			c = new JFileChooser();
			//显示保存对话框
			rVal = c.showSaveDialog(this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				//获取文件名
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
					closeButton.setActionCommand("关闭选项卡");
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
					JOptionPane.showConfirmDialog(this, ep.toString(), "保存文件出错", JOptionPane.YES_NO_OPTION);
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
		closeButton.setActionCommand("关闭选项卡");
		closeButton.setPreferredSize(new Dimension(15,15));
		tabPanel.add(tabName);
		tabPanel.add(closeButton);
		centerPanel.add(myTextPanel);
		centerPanel.setTabComponentAt(centerPanel.getTabCount()-1,tabPanel);//设置标签名
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
			closeButton.setActionCommand("关闭选项卡");
			closeButton.setPreferredSize(new Dimension(15,15));
			tabPanel.add(tabName);
			tabPanel.add(closeButton);
			centerPanel.add(myTextPanel);
			centerPanel.setTabComponentAt(centerPanel.getTabCount()-1,tabPanel);//设置标签名
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
			
			statusBar.setText("文本字符数：" + nowText.getDocument().getLength()+"    总行数："+map.getElementCount());
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
			JOptionPane.showConfirmDialog(this, ep.toString(), "打开文件出错", JOptionPane.YES_NO_OPTION);
		}	
	}	
	
	//文本区更新
	public void changedUpdate(DocumentEvent e)
	{
		JTextPane nowText=((MyTextPanel) centerPanel.getSelectedComponent()).getTextPane();
		Element map = nowText.getDocument().getDefaultRootElement();				    
		statusBar.setText("文本字符数：" + nowText.getDocument().getLength()+"    总行数："+map.getElementCount());
		//如文本区进行了更改，则运行保存
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
	//文本区删除字符
	public void removeUpdate(DocumentEvent e)
	{
		changedUpdate(e);
	}
	//文本区增加字符
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
		// TODO 自动生成的方法存根
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
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
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
		// TODO 自动生成的方法存根

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		if (e.getButton() == MouseEvent.BUTTON3 ) {
			popupMenu.show((Component) e.getSource(), e.getX(), e.getY());  
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自动生成的方法存根
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
		// TODO 自动生成的方法存根
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
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO 自动生成的方法存根
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO 自动生成的方法存根
		int tabNum=centerPanel.getTabCount();
		for(int i=tabNum-1;i>0;i--)
		{
			centerPanel.setSelectedIndex(i);
			this.closeTab(i);
		}
		if(itemSave.isEnabled())
		{
			int opt=JOptionPane.showConfirmDialog(this,"是否保存文件： "+centerPanel.getToolTipTextAt(0),"退出",JOptionPane.YES_NO_CANCEL_OPTION);
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
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 自动生成的方法存根
		
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
			int opt=JOptionPane.showConfirmDialog(this,"是否保存文件： "+centerPanel.getToolTipTextAt(selTab),"退出",JOptionPane.YES_NO_CANCEL_OPTION);
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
	//绘制JFrame
	static void createAndShowGUI()
	{
		
		JFrame frame=new NotepadFrame();
	    //设置界面风格为Windows风格
		try
	    {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(frame);
	    }catch(Exception e){}
	}
	public static void main(String args[])
	{
		//事件调度线程绘制面板
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
