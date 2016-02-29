/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package secureMail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class Verify_Signature extends javax.swing.JFrame {

    /**
     * Creates new form Verify_Signature
     */
    byte[] signature;
    PublicKey publicKey;
    String email;
    byte[] data;
    ContactList contactlist;
    Multipart multipart;
    byte[] originaldata;
    public Verify_Signature(String email, byte[] data,ContactList list,Multipart multipart) {
        initComponents();
        this.data = data;
        contactlist = list;
        this.email = email;
        this.multipart = multipart;
        jTextPane1.setText(email);
        originaldata = data;
    }

    private Verify_Signature() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Load Public Key");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Load Signature");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Send From: ");

        jScrollPane1.setViewportView(jTextPane1);

        jButton3.setText("Verify");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setText("Not loaded yet!");

        jLabel3.setText("Not loaded yet!");

        jLabel4.setText("Result: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(102, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1)))
                        .addGap(16, 16, 16))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jLabel4))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void Load_PublicKey(){        
        if (contactlist.list.size()==0||(publicKey = contactlist.FindPublicKey(email))==null) {
            FileInputStream keyfis = null;
            try {
                JFileChooser filechooser = new JFileChooser();
                String filename;
                String filepath="";
                int returnVal = filechooser.showOpenDialog(this);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    filename = filechooser.getSelectedFile().getName();
                    filepath = filechooser.getSelectedFile().getAbsolutePath();
                }
                keyfis = new FileInputStream(filepath);
                byte[] encKey = new byte[keyfis.available()];
                keyfis.read(encKey);
                keyfis.close();
                X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                publicKey = keyFactory.generatePublic(pubKeySpec);
                if (contactlist==null){
                    contactlist = new ContactList();
                }
                contactlist.AddContact(new Contact(email,publicKey));
                contactlist.SaveData();
                jLabel2.setText("Loaded!");                 
            }   
            catch (Exception e) {            
                
                }        
            }
        else    {
            jLabel2.setText("Loaded!");   
        }
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        FileInputStream in = null;
        try {
            // TODO add your handling code here:
            JFileChooser fileChoose = new JFileChooser();
            String filename;
            String filepath="";
            int returnVal = fileChoose.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                filename = fileChoose.getSelectedFile().getName();
                filepath = fileChoose.getSelectedFile().getAbsolutePath();
            }           
            in = new FileInputStream(filepath);
            signature = new byte[in.available()];
            in.read(signature);
            jLabel3.setText("Loaded!");
        } catch (Exception e){
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed
     public byte[] MergeByteArrat(byte[] a, byte[] b){
        byte[] c;
        int count=0;
        for (int i=0;i<b.length;i++){
            if (b[i]==13) count++;
        }
        count = 0;
        c = new byte[a.length + b.length-count];
        int i=0;
        for (i=0;i<a.length;i++){            
            c[i] = a[i];
        }       
        i = a.length;
        for (int j=0;j<b.length;j++){
           // if (b[j]!=13){
                c[i] = b[j];
                i++;
            // }
        }
            
        return c;
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        
        data = originaldata;
        byte buff[];
        try {
            for (int i=0;i<multipart.getCount();i++){          
                BodyPart part = multipart.getBodyPart(i);
                String disposition = part.getDisposition();    
                if ("signature".equals(part.getFileName())||"SecretKey".equals(part.getFileName())
                        ||"encryptedKey".equals(part.getFileName())) continue;
                else if (disposition != null && (disposition.equals("ATTACHMENT"))) {
                     InputStream is = part.getInputStream();                                            
                     buff = new byte[1024];
                     int len;
                     while ((len = is.read(buff, 0, buff.length))!=-1){ 
                         buff = Arrays.copyOf(buff, len);
                         data = MergeByteArrat(data, buff);
                     }                                
                     
                 }                
            } }
            catch (Exception ex) {
                Logger.getLogger(Encrypt.class.getName()).log(Level.SEVERE, null, ex);
            
            } 
        
        
        
        Encryption myEncryption = new Encryption(publicKey);
        if (myEncryption.Verify(signature,  data)==true){
            JOptionPane.showMessageDialog(null, "Verified Successfully", "Confirm", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
        else {
            JOptionPane.showMessageDialog(null, "Verified Unsuccessfully", "Failed", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Load_PublicKey();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Verify_Signature.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Verify_Signature.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Verify_Signature.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Verify_Signature.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Verify_Signature().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}