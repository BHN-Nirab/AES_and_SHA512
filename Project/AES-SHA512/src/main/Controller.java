package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.FileChooser;
import main.AES.MsgEnc;
import main.SHA512.SHA512;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Label aesFileName, aesFileSize;
    @FXML
    Button aesEncrypt,aesDecrypt, aesFileChooser;

    @FXML
    Button aes, sha, hash;
    @FXML
    AnchorPane aesHolder, shaHolder;

    @FXML
     Label shaFileName, shaFileSize, hashResult;

    File selectedFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        aesEncrypt.setDisable(true);
        aesDecrypt.setDisable(true);
        aesFileName.setText("");
        aesFileSize.setText("");
        shaFileName.setText("");
        shaFileSize.setText("");
        hashResult.setText("");
        aesHolder.setVisible(true);
        shaHolder.setVisible(false);
        aes.setStyle("-fx-background-color: #2677a3; ");
        sha.setStyle("-fx-background-color: #9ea1a3; ");


    }

    public void setAes()
    {
        aesEncrypt.setDisable(true);
        aesDecrypt.setDisable(true);
        aesHolder.setVisible(true);
        shaHolder.setVisible(false);
        aes.setStyle("-fx-background-color: #2677a3; ");
        sha.setStyle("-fx-background-color: #9ea1a3; ");
    }

    public void setSha()
    {
        hash.setDisable(true);
        aesHolder.setVisible(false);
        shaHolder.setVisible(true);
        aes.setStyle("-fx-background-color: #9ea1a3; ");
        sha.setStyle("-fx-background-color: #2677a3; ");
    }

    public void setSHAFileChooser()
    {
        hash.setDisable(false);
        hash.setText("Hash");
        hashResult.setText("");
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);
        shaFileName.setText(selectedFile.getName());
        shaFileSize.setText(selectedFile.length() + " bytes");
    }

    public void setHash()
    {
        try {
            String input= new String(Files.readAllBytes(selectedFile.toPath()));
            String result = SHA512.mymain(input);
            hashResult.setText(result);
            hash.setText("Hashed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void setAESFileChooser()
    {
        aesEncrypt.setDisable(false);
        aesDecrypt.setDisable(false);
        aesEncrypt.setText("Encrypt");
        aesDecrypt.setText("Decrypt");

        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);
        aesFileName.setText(selectedFile.getName());
        aesFileSize.setText(selectedFile.length() + " bytes");
    }

    public void setAESEncrypt()
    {
        FileChooser fileChooser = new FileChooser();
        File outputFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null && outputFile != null) {
            aesEncrypt.setText("Encrypting...");
            try {
                byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                String msg = new String(bytes);

                MsgEnc encryptor = new MsgEnc();
                encryptor.setNormalMessage(msg);
                String ss = encryptor.getEncryptedString();

                bytes = ss.getBytes();

                FileOutputStream fos = new FileOutputStream(outputFile);
                fos.write(bytes);
                fos.close();
                aesEncrypt.setText("Encrypted");
            }catch(Exception e){

            }
        }
    }

    public void setDecrypt()
    {
        FileChooser fileChooser = new FileChooser();
        File outputFile = fileChooser.showSaveDialog(null);

        aesDecrypt.setText("Decrypting...");

        try {
            byte[] bytes = Files.readAllBytes(selectedFile.toPath());
            String msg = new String(bytes);
            MsgEnc encryptor = new MsgEnc();

            encryptor.setEncryptedMessage(msg);
            String ss = encryptor.getDecryptedString();

            bytes = ss.getBytes();

            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(bytes);
            fos.close();
            aesDecrypt.setText("Decrypted");
        }catch(Exception e)
        {

        }

    }





}
