

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Locale;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;


public class MyTextPanel extends JScrollPane implements DocumentListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//�����
	JList<String> numList;
	String[] num;
	JTextPane sidePane;
	
	//�ı���
	JTextPane textPane;
	JTextPane shadowPane;//û�и������ı���
	JTextPane nowPane;
	JPanel panel;
	
	//����
	Font f;
	FontMetrics fontmetrics;
	
	//������
	SyntaxHighlighter highlighterListener;
	UndoManager undomg;
	NotepadFrame listenFrame;
	AutoAddListener autoaddlistener;
	
	boolean saveState;
	boolean undoState;
	boolean redoState;
	
	MyTextPanel(NotepadFrame l,Boolean proState)
	{
		listenFrame=l;
		undomg=new UndoManager();
		
		//�����ı�����
		textPane = new JTextPane();
		shadowPane = new JTextPane();
		panel = new JPanel();
		
		if(proState) nowPane=textPane;
		else nowPane=shadowPane;
		
		//Ϊ�ı��������ü���
		highlighterListener=new SyntaxHighlighter(textPane);
		autoaddlistener=new AutoAddListener(textPane);
		textPane.getDocument().addDocumentListener(highlighterListener);
		textPane.getDocument().addDocumentListener(autoaddlistener);
		
		//ΪӰ���ı������ü���
		shadowPane.getDocument().addUndoableEditListener(undomg);
		
		nowPane.getDocument().addDocumentListener(this);
		nowPane.getDocument().addDocumentListener(listenFrame);
		nowPane.addMouseMotionListener(listenFrame);
		nowPane.addKeyListener(listenFrame);
		nowPane.addMouseListener(listenFrame);
		
		//���ò����
		num=new String[9999];
		for(int i=0;i<9999;i++) num[i]=String.valueOf(i+1);
		numList=new JList<String>(num);
		numList.setBackground(new Color(228, 228, 228));//����ɫ
		numList.setForeground(new Color(128,128,128));//������ɫ
		
		//��������
		f = new Font("����", Font.PLAIN, 16);
		textPane.setFont(f);
		shadowPane.setFont(f);
		numList.setFont(f);
		
		//���ò�����߶�
		fontmetrics = nowPane.getFontMetrics(f);//��ȡ�ı�����������
		numList.setFixedCellHeight(fontmetrics.getHeight());
		
		//�Ȳ�����ֻ�ܰѲ������Jlist�ŵ�һ��JTextPane���ˣ��������ܶ���
		sidePane=new JTextPane();
		sidePane.insertComponent(numList);
		sidePane.setBackground(new Color(228,228,228));
		sidePane.setEnabled(false);
		numList.setEnabled(true);
		
		//�Ѳ������ӵ��ı���
		this.setRowHeaderView(sidePane);		
		panel.setLayout(new BorderLayout());
		
		panel.add(nowPane);
		this.setViewportView(panel);
		
	}
	
	public JTextPane getTextPane()
	{
		return nowPane;
	}
	
	public void removePro()
	{
		if(nowPane==textPane)
		{
			nowPane.getDocument().removeDocumentListener(this);
			nowPane.getDocument().removeDocumentListener(listenFrame);
			nowPane.removeMouseMotionListener(listenFrame);
			nowPane.removeKeyListener(listenFrame);
			nowPane.removeMouseListener(listenFrame);
			nowPane=shadowPane;
			nowPane.getDocument().addDocumentListener(this);
			nowPane.getDocument().addDocumentListener(listenFrame);
			nowPane.addMouseMotionListener(listenFrame);
			nowPane.addKeyListener(listenFrame);
			nowPane.addMouseListener(listenFrame);
			
			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(nowPane);
			this.setViewportView(panel);
		}	
	}
	
	public void addPro()
	{
		if(nowPane==shadowPane)
		{
			nowPane.getDocument().removeDocumentListener(this);
			nowPane.getDocument().removeDocumentListener(listenFrame);
			nowPane.removeMouseMotionListener(listenFrame);
			nowPane.removeKeyListener(listenFrame);
			nowPane.removeMouseListener(listenFrame);
			nowPane=textPane;
			nowPane.getDocument().addDocumentListener(this);
			nowPane.getDocument().addDocumentListener(listenFrame);
			nowPane.addMouseMotionListener(listenFrame);
			nowPane.addKeyListener(listenFrame);
			nowPane.addMouseListener(listenFrame);
			
			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(nowPane);
			this.setViewportView(panel);
		}	
	}

	public void removeSideBar()
	{
		this.setRowHeader(null);
	}
	
	public void addSideBar()
	{
		this.setRowHeaderView(sidePane);
	}
	
	public void myUndo()
	{
		if(nowPane==shadowPane)
		{
			if(undomg.canUndo()) undomg.undo();
			if(!undomg.canUndo())
			{
				listenFrame.itemUndo.setEnabled(false);
				listenFrame.buttonUndo.setEnabled(false);
				listenFrame.pitemUndo.setEnabled(false);
				undoState=false;
				
			}
		}
		else
		{
			shadowPane.getDocument().addDocumentListener(this);
			textPane.getDocument().removeDocumentListener(this);
			if(undomg.canUndo()) undomg.undo();
			if(!undomg.canUndo())
			{
				listenFrame.itemUndo.setEnabled(false);
				listenFrame.buttonUndo.setEnabled(false);
				listenFrame.pitemUndo.setEnabled(false);
				undoState=false;
			}
			textPane.getDocument().addDocumentListener(this);
			shadowPane.getDocument().removeDocumentListener(this);
		}
	}
	
	public void myRedo()
	{
		if(nowPane==shadowPane)
		{
			if(undomg.canRedo()) undomg.redo();
			if(!undomg.canRedo())
			{
				listenFrame.itemRedo.setEnabled(false);
				listenFrame.buttonRedo.setEnabled(false);
				listenFrame.pitemRedo.setEnabled(false);
				redoState=false;
			}
		}
		else
		{
			shadowPane.getDocument().addDocumentListener(this);
			textPane.getDocument().removeDocumentListener(this);
			if(undomg.canRedo()) undomg.redo();
			if(!undomg.canRedo())
			{
				listenFrame.itemRedo.setEnabled(false);
				listenFrame.buttonRedo.setEnabled(false);
				listenFrame.pitemRedo.setEnabled(false);
				redoState=false;
			}
			textPane.getDocument().addDocumentListener(this);
			shadowPane.getDocument().removeDocumentListener(this);
		}
	}
	
	public void findStr(String willFindStr,Boolean cpasState,int isUp)//toLowerCase(),indexOf(String str, int fromIndex),lastIndexOf(String str, int fromIndex)
	{
		if(willFindStr.equals(""))
		{
			JOptionPane.showMessageDialog(this,"�ַ���Ϊ��");
		}
		else 
		{
			int beg;
			int len;
			String myDoc="";
			int resBeg;
			if(nowPane.getSelectedText()==null)
			{
				if(isUp==0)
				{
					beg=nowPane.getCaretPosition();
					len=nowPane.getDocument().getLength()-beg;
				}
				else
				{
					beg=0;
					len=nowPane.getCaretPosition();
				}
			}
			else
			{
				if(nowPane.getSelectedText().equals(willFindStr))
				{
					if(isUp==0)
					{
						beg=nowPane.getSelectionEnd();
						len=nowPane.getDocument().getLength()-beg;
					}
					else
					{
						beg=0;
						len=nowPane.getSelectionStart();
					}
				}
				else
				{
					if(isUp==0)
					{
						beg=nowPane.getSelectionStart();
						len=nowPane.getDocument().getLength()-beg;
					}
					else
					{
						beg=0;
						len=nowPane.getSelectionEnd();
					}
				}
			}
			if(len==0) JOptionPane.showMessageDialog(this,"�Ҳ����ַ���\""+willFindStr+"\"");
			else
			{
				try {
					myDoc=nowPane.getText(beg, len);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				if(cpasState==false)
				{
					myDoc=myDoc.toLowerCase(Locale.ENGLISH);
					willFindStr=willFindStr.toLowerCase(Locale.ENGLISH);
				}
				if(isUp==0) resBeg=myDoc.indexOf(willFindStr);
				else resBeg=myDoc.lastIndexOf(willFindStr);
				if(resBeg==-1) JOptionPane.showMessageDialog(this,"�Ҳ����ַ���\""+willFindStr+"\"");
				else
				{
					nowPane.select(beg+resBeg, beg+resBeg+willFindStr.length());
				}
			}
		}
	}

	
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO �Զ����ɵķ������
		int start=e.getOffset();
		int  len=e.getLength();
		if(e.getDocument()==textPane.getDocument())
		{
			try
			{
				shadowPane.getDocument().insertString(start, e.getDocument().getText(start, len), null);
			} catch (BadLocationException e1){}
		}
		else
		{
			try
			{
				textPane.getDocument().insertString(start, e.getDocument().getText(start, len), null);
			} catch (BadLocationException e1){}
		}
	}

	
	
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO �Զ����ɵķ������
		int start=e.getOffset();
		int  len=e.getLength();
		if(e.getDocument()==textPane.getDocument())
		{
			try
			{
				shadowPane.getDocument().remove(start, len);
			} catch (BadLocationException e1){}
		}
		else
		{
			try
			{
				textPane.getDocument().remove(start, len);
			} catch (BadLocationException e1){}
		}
	}

	public void resetFont(Font newFont)
	{
		f=newFont;
		textPane.setFont(newFont);
		shadowPane.setFont(newFont);
		numList.setFont(newFont);
		fontmetrics = nowPane.getFontMetrics(f);//��ȡ�ı�����������
		numList.setFixedCellHeight(fontmetrics.getHeight());
	}
	
	public void zoominFont()
	{
		f=new Font(f.getFontName(),f.getStyle(),f.getSize()+2);
		resetFont(f);
	}
	
	public void zoomoutFont()
	{
		if(f.getSize()<2) return;
		f=new Font(f.getFontName(),f.getStyle(),f.getSize()-2);
		resetFont(f);
	}
	
	public void setSave(boolean state)
	{
		saveState=state;
	}
	
	public void setUndo(boolean state)
	{
		undoState=state;
	}
	
	public void setRedo(boolean state)
	{
		redoState=state;
	}
	
	public boolean getSaveState()
	{
		return saveState;
	}
	
	public boolean getUndoState()
	{
		return undoState;
	}
	
	public boolean getRedoState()
	{
		return redoState;
	}
}
