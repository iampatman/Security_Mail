/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secureMail;

/**
 *
 * @author PC
 */
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
public class Encryption {
    public PublicKey pub;
    public PrivateKey priv;    
    private Message srcMsg;
    private byte[] data;
    public Encryption(byte[] pub,byte[] priv) {
        //Import Private Key and Public Key from file
    }
    public Encryption(PublicKey pub){
        this.pub = pub;    
    }
    public Encryption(){
        //Generate Private Key and Public Key
        //Export into files        
    }
    public void GenerateKeyPair(){
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(1024, random);
            KeyPair pair = keyGen.generateKeyPair();           
            priv = pair.getPrivate();
            pub = pair.getPublic();            
            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream("MyPublicKey");
            keyfos.write(key);
            keyfos.close();
            
            
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priv.getEncoded());
            
            key = pkcs8EncodedKeySpec.getEncoded();                        
            keyfos = new FileOutputStream("MyPrivateKey");
            keyfos.write(key);
            keyfos.close();            
        }
        catch (Exception e){
        }
    }
    public void Sign(byte[] content){
        try{
            Signature dsa = Signature.getInstance("SHA1withRSA");            
            dsa.initSign(priv);
            dsa.update(content, 0, content.length);
            byte[] signedContent;
            signedContent = dsa.sign();
            FileOutputStream out = new FileOutputStream("signature");
            out.write(signedContent);
            out.flush();
            out.close();
            
        } catch (Exception e){
        }
    }
    public boolean Verify(byte[] sigToVerify, byte[] data){    
        boolean verifies = false;
        try {               
               /* input the signature bytes */                            
                         
               /* create a Signature object and initialize it with the public key */
               Signature sig = Signature.getInstance("SHA1withRSA");
               sig.initVerify(this.pub);
               /* Update and verify the data */                                             
               int len = data.length;
               sig.update(data, 0, len);
               verifies = sig.verify(sigToVerify);               
        } catch (Exception ex) {
           
        }
        return verifies;
        
        
    }
    public PublicKey ImportPublicKey(String name){
        try {
              FileInputStream keyfis = new FileInputStream(name);
              byte[] encKey = new byte[keyfis.available()];
              keyfis.read(encKey);
              keyfis.close();
              X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
              KeyFactory keyFactory = KeyFactory.getInstance("RSA");
              PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
              return pubKey;
        } catch (Exception ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public PrivateKey ImportPrivateKey(String name){
        try {
            FileInputStream keyfis = new FileInputStream(name);
              byte[] encKey = new byte[keyfis.available()];
              keyfis.read(encKey);

              keyfis.close();
              
              PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(encKey);              
              KeyFactory keyFactory = KeyFactory.getInstance("RSA");
              PrivateKey privKey = keyFactory.generatePrivate(privKeySpec);
              return privKey;
        } catch (Exception ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public void ExportPublicKey(String name){
        try {
            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream(name);
            keyfos.write(key);
            keyfos.close();
        } catch (IOException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    public void ExportPrivateKey(String name){
        try {
            byte[] key = priv.getEncoded();
            FileOutputStream keyfos = new FileOutputStream(name);
            keyfos.write(key);
            keyfos.close();
        } catch (IOException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
