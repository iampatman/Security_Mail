/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secureMail;

import java.security.PublicKey;
import java.util.ArrayList;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author PC
 */
public class ContactList implements Serializable{
    public ArrayList<Contact> list;
    public ContactList(){
        try {
            list = new ArrayList<Contact>(); 
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("contactlist"));
            if (in!=null){
                list = (ArrayList<Contact>)in.readObject();
            }
        } catch (Exception e) {            
        }           
       
    }
    public void AddContact(Contact ct){
        list.add(ct);
    }
    public PublicKey FindPublicKey(String email){
        int i;
        Contact temp=null;
        for (i=0;i<list.size();i++){
            temp = list.get(i);
            if (temp.emailadd.equals(email)==true){
                break;
            }            
        }
        if (i==list.size()){
            return null;
        }
        else {
            return temp.pubkey;
        }
    }
    
    public void SaveData(){
        try {            
            
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("contactlist"));         
            
                
                out.writeObject(list);
            
            
        } catch (IOException ex) {
            Logger.getLogger(ContactList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
