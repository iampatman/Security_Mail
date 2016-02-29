/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secureMail;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class Contact implements Serializable{
    public String emailadd;
    public PublicKey pubkey;
    public Contact(String email,PublicKey pub){
        emailadd = email;
        pubkey = pub;
    }         
}
