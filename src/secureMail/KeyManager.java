package secureMail;

import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeyManager {

    private SecretKey secretKey;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    public KeyManager(){
    }
    public KeyManager(PublicKey pub, PrivateKey priv){
        this.publicKey = pub;
        this.privateKey = priv;
    }
    public SecretKey getSecretKey() {
        return secretKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void generateSecretKey() {
        // Key 3DES
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            keyGenerator.init(168);
            this.secretKey = keyGenerator.generateKey();

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }

    public void exportKey(String secKeyFileName, Key key) {
        try {
            byte[] keyByte = key.getEncoded();
            FileOutputStream keyfos = new FileOutputStream(secKeyFileName);
            keyfos.write(keyByte);
            keyfos.close();

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }
    
    public SecretKey importSecretKey(String secKeyFileName) {
        try {
            FileInputStream keyfis = new FileInputStream(secKeyFileName);
            byte[] encKey = new byte[keyfis.available()];
            keyfis.read(encKey);
            keyfis.close();
            
            SecretKey secKey = new SecretKeySpec(encKey, 0, encKey.length, "DESede");
            
            return secKey;

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
        return null;
    }


}
