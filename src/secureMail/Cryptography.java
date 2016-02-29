package secureMail;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



public class Cryptography {
    private static final String ENCRYPTED_SECRETKEY_FILENAME = "secretKey.encryptedRSA";

    public byte[] plaintext;
    public byte[] ciphertext;
    public Cryptography(){
    }
    public Cryptography(byte[] plaintext, byte[] ciphertext) {
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
    }

    public byte[] getCiphertext() {
        return ciphertext;
    }

    public byte[] getPlaintext() {
        return plaintext;
    }
    
    private void exportByteFile(byte[] input, String outputFileName) {
        try {
            FileOutputStream keyfos = new FileOutputStream(outputFileName);
            keyfos.write(input);
            keyfos.close();

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }
    
    private byte[] importByteFile(String inputFileName) {
        try {
            FileInputStream fis = new FileInputStream(inputFileName);
            byte[] keyBytes = new byte[fis.available()];
            fis.read(keyBytes);
            fis.close();
            return keyBytes;
            
        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
        return null;
    }

    public void symmetricEncrypt(SecretKey secretKey) {
        try {
            
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);            
            this.ciphertext = cipher.doFinal(this.plaintext);
            
        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }

    public void symmetricDecrypt(SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            this.plaintext = cipher.doFinal(this.ciphertext);
            
        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }
    
    public byte[] asymmetricEncrypt(PublicKey publicKey, SecretKey secretKey) {
        try {           

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            
            byte[] secretKeyBytes = secretKey.getEncoded();
            byte[] encryptedKeyBytes = cipher.doFinal(secretKeyBytes);
            this.exportByteFile(encryptedKeyBytes, "encryptedKey");
            return encryptedKeyBytes;

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
        return null;
    }

    public SecretKey asymmetricDecrypt(PrivateKey privKey,String filepath) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            
            byte[] encryptedKeyBytes = this.importByteFile(filepath);
            byte[] decryptedKeyBytes = cipher.doFinal(encryptedKeyBytes);
            
            SecretKey secretKey = new SecretKeySpec(decryptedKeyBytes, 0, decryptedKeyBytes.length, "DESede");
            return secretKey;

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
            return null;
        }
    }
}
