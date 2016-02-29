/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secureMail;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author PC
 */
public class DataInbox extends AbstractTableModel {
    private Vector data;
    private Vector columnname;    
    public DataInbox(){        
    }
    public void DeleteItem(int index){
        data.remove(index);
    }
    public void Add(Message msg){       
        
        Vector dataRow = new Vector();
            try {
                dataRow.addElement(msg.getSubject());
                Address[] add = msg.getFrom();
                String email = add[0].toString();
                if (email.lastIndexOf("<")!=-1){
                    email = email.substring(email.lastIndexOf("<")+1, email.lastIndexOf(">"));
                }
                dataRow.addElement(email);
                dataRow.addElement(msg.getReceivedDate().toString());
            } catch (MessagingException ex) {
                Logger.getLogger(DataInbox.class.getName()).log(Level.SEVERE, null, ex);
            }
        data.add(0, dataRow);
            
    }
    public DataInbox(Message[] msgs){
        int i=msgs.length;    
        data = new Vector();
        columnname = new Vector();
        columnname.addElement("Subject");
        columnname.addElement("From");
        columnname.addElement("Date");
        
        while (i>msgs.length-15){            
            i--;
            Vector dataRow = new Vector();
            try {
                dataRow.addElement(msgs[i].getSubject());
                Address[] add = msgs[i].getFrom();
                String email = add[0].toString();
                if (email.lastIndexOf("<")!=-1){
                    email = email.substring(email.lastIndexOf("<")+1, email.lastIndexOf(">"));
                }
                dataRow.addElement(email);
                dataRow.addElement(msgs[i].getReceivedDate().toString());
            } catch (MessagingException ex) {
                Logger.getLogger(DataInbox.class.getName()).log(Level.SEVERE, null, ex);
            }
            data.addElement(dataRow);
        }
        
    }
    @Override
    public int getRowCount() {  
        return data.size();  
    }  
  
    @Override
    public int getColumnCount() {  
        return 3;
    }  
  
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {  
        Vector rowData = (Vector) (data.elementAt(rowIndex));
        return rowData.elementAt(columnIndex);
    }  
    @Override
    public String getColumnName(int column) {
        return (String) (columnname.elementAt(column));
    }
}
