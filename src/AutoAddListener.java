
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class AutoAddListener implements DocumentListener{

	JTextPane isListenedPane;
	String insertStr;
	int offset;
	Document myDoc;
	
	int beg;
	String docStr="";
	String lineStr="";
	int end;
	String spaceStr="";
	
	AutoAddListener(JTextPane isListened)
	{
		isListenedPane=isListened;
	}
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO 自动生成的方法存根
		myDoc=e.getDocument();
		try {
			docStr=myDoc.getText(0,myDoc.getLength());
		} catch (BadLocationException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		offset=e.getOffset();
		insertStr=docStr.substring(offset, offset+e.getLength());
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                add();
            }
        });
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO 自动生成的方法存根
		
	}
	
	public void add()
	{
		switch(insertStr)
		{
		case "{":
			isListenedPane.replaceSelection("}");
			isListenedPane.setCaretPosition(offset);
			break;
			
		case "[":
			isListenedPane.replaceSelection("]");
			isListenedPane.setCaretPosition(offset);
			break;
			
		case "(":
			isListenedPane.replaceSelection(")");
			isListenedPane.setCaretPosition(offset);
			break;		
			
		case "\"":
			if(docStr.length()>offset+1 && docStr.substring(offset+1,offset+2).equals("\""))
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		                try {
							myDoc.remove(offset+1, 1);
						} catch (BadLocationException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}		       
		            }
		        });	
			}
			else
			{
				isListenedPane.getDocument().removeDocumentListener(this);
				isListenedPane.replaceSelection("\"");
				isListenedPane.setCaretPosition(offset+1);
				isListenedPane.getDocument().addDocumentListener(this);
			}
			break;
			
		case "\'":
			if(docStr.length()>offset+1 && docStr.substring(offset+1,offset+2).equals("\'"))
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		                try {
							myDoc.remove(offset+1, 1);
						} catch (BadLocationException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}		       
		            }
		        });	
			}
			else
			{
				isListenedPane.getDocument().removeDocumentListener(this);
				isListenedPane.replaceSelection("\'");
				isListenedPane.setCaretPosition(offset+1);
				isListenedPane.getDocument().addDocumentListener(this);
			}
			break;
			
		case "}":
			if(docStr.length()>offset+1 && docStr.substring(offset+1,offset+2).equals("}"))
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		                try {
							myDoc.remove(offset+1, 1);
						} catch (BadLocationException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}		       
		            }
		        });
				
			}
			break;
			
		case "]":
			if(docStr.length()>offset+1 && docStr.substring(offset+1,offset+2).equals("]"))
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		                try {
							myDoc.remove(offset+1, 1);
						} catch (BadLocationException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}		       
		            }
		        });
				
			}
			break;
			
		case ")":
			if(docStr.length()>offset+1 && docStr.substring(offset+1,offset+2).equals(")"))
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		                try {
							myDoc.remove(offset+1, 1);
						} catch (BadLocationException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}		       
		            }
		        });
				
			}
			break;
			
			
		case "\n":		
			beg=docStr.lastIndexOf("\n", offset-1)+1;
			lineStr=docStr.substring(beg, offset+1);	
			int len=lineStr.length();
			for(int i=0;i<len;i++)
			{
				if(lineStr.charAt(i)!=' ' && lineStr.charAt(i)!='\t')
				{
					end=i;
					break;
				}
			}
			if(end!=0) spaceStr=lineStr.substring(0, end);
			else spaceStr="";
			if(offset>0 && docStr.substring(offset-1,offset).equals("{")) 
			{
				int backlen=spaceStr.length()+1;
				spaceStr=spaceStr+"\t\n"+spaceStr;
				isListenedPane.replaceSelection(spaceStr);
				isListenedPane.setCaretPosition(isListenedPane.getCaretPosition()-backlen);
			}
			else isListenedPane.replaceSelection(spaceStr);
		}
	}
}
