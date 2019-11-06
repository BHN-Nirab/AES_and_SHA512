package main.AES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class FileEncryptor {
    public static void main(String [] args)
    {
        MsgEnc encryptor = new MsgEnc();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(800,200);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        panel.setBackground(Color.GRAY);

        JTextField urlField = new JTextField();
        urlField.setToolTipText("paste url of file");
        urlField.setFont(new Font("TimesRoman" ,Font.PLAIN, 25));
        panel.add(urlField);
        urlField.setBounds(100, 50, 600, 50);

        JButton encrypt = new JButton("Encrypt");
        JButton decrypt = new JButton("Decrypt");
        panel.add(encrypt);
        panel.add(decrypt);

        encrypt.setBounds(100, 100, 300, 50);
        decrypt.setBounds(400, 100, 300, 50);

        encrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent){
                try {
                    File file=new File(urlField.getText());

                    byte [] bytes = Files.readAllBytes(file.toPath());
                    String msg = new String(bytes);

                    encryptor.setNormalMessage(msg);
                    String ss = encryptor.getEncryptedString();

                    bytes = ss.getBytes();

                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytes);
                    fos.close();

                    System.out.println(ss);
                }
                catch (Exception e) {}
            }
        });

        decrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    File file=new File(urlField.getText());

                    byte [] bytes = Files.readAllBytes(file.toPath());
                    String msg = new String(bytes);

                    encryptor.setEncryptedMessage(msg);
                    String ss = encryptor.getDecryptedString();

                    bytes = ss.getBytes();

                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytes);
                    fos.close();

                    System.out.println(ss);
                }
                catch (Exception e) {}
            }
        });
    }
}
