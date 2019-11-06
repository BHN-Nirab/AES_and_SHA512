package main.AES;

import java.util.ArrayList;
import java.util.Collections;

public class SubTable {
    ArrayList<Character>SBox;
    ArrayList<Character>IBox;
    int mode=0;

    public SubTable(int blockSize)
    {
        SBox=new ArrayList<>();
        IBox=new ArrayList<>();
        for(char i=0; i<blockSize*blockSize; i++)
        {
            SBox.add(i);
            IBox.add(i);
        }

        Collections.shuffle(SBox);
        for(char i=0; i<blockSize*blockSize; i++)
            IBox.set(SBox.get(i),i);
    }
    public SubTable(int blockSize, char [] mat, char [] mat2)
    {
        SBox=new ArrayList<>();
        IBox=new ArrayList<>();
        for(char i=0; i<blockSize*blockSize; i++)
        {
            SBox.add(mat[i]);
            IBox.add(mat2[i]);
        }
    }
    public void setEncryptionMode()
    {
        mode=0;
    }
    public void setDecryptionMode()
    {
        mode=1;
    }
    public char get(char c)
    {
        if(mode==0) return SBox.get(c);
        return IBox.get(c);
    }
    public char getE(char c)
    {
        return SBox.get(c);
    }
    public char getL(char c)
    {
        return IBox.get(c);
    }
}