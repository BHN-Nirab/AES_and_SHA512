package main.AES;

import java.util.Random;

public class Key{
    SubTable subTable;
    int numberOfRound=10;
    int roundNumber=0;
    int mode=0;
    Block [] blocks;
    Block mainKey;
    Random random;
    int blockSize=16;
    char [][] buffer176;
    int [][] words;
    int [] rc = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36};

    public Key(int numberOfRound)
    {
        subTable=Values.getSTable();
        random = new Random();
        this.numberOfRound=numberOfRound;

        buffer176=new char[11][16];
        words=new int[11][4];

        buffer176[0] = generateRandomBuffer(blockSize);
        words[0] = chatToIntArr(buffer176[0]);
        mainKey=new Block(buffer176[0]);

        prepareWords();
        for(int i=1; i<=10; i++)
            buffer176[i]=intToCharArr(words[i]);

        blocks = new Block [numberOfRound];

        for(int i=0; i<numberOfRound; i++)
        {
            blocks[i] = new Block(buffer176[i+1]);
        }
    }

    private void prepareWords() {
        for(int i=1; i<=10; i++)
        {
            for(int j=0; j<4; j++)
            {
                if(j>0) words[i][j]=words[i-1][j]^words[i][j-1];
                else words[i][j]=words[i-1][j]^rotWord(subWord(words[i-1][3]))^rc[i-1];
            }
        }
    }
    private int rotWord(int word)
    {
        int tmp = (word>>24)&0xff;
        word = word << 8;
        word = word | tmp;
        return word;
    }
    private int subWord(int word)
    {
        char [] tmp = new char[4];
        for(int i=0; i<4; i++)
            tmp[i]=(char)((word>>(i*8))&0xff);
        for(int i=0; i<4; i++)  tmp[i]=subTable.getE(tmp[i]);
        word=(tmp[3]<<24)|(tmp[2]<<16)|(tmp[1]<<9)|tmp[0];
        return word;
    }

    private int [] chatToIntArr(char [] rowBuffer)
    {
        int [] rowWords = new int[4];
        for(int i=0; i<4; i++)
        {
            rowWords[i]=rowBuffer[i*4];
            for(int j=1; j<4; j++)
                rowWords[i]=(rowWords[i]<<8)|rowBuffer[i+j];
        }
        return rowWords;
    }
    private char [] intToCharArr(int [] rowWords)
    {
        char [] rowBuffer = new char[blockSize];
        for(int i=0; i<4; i++)
        {
            rowBuffer[i*4]=(char) (rowWords[i]&0xff);
            for(int j=1; j<4; j++)
                rowBuffer[i*4+j]=(char) ((rowWords[i]>>(j*8)&0xff));
        }
        return rowBuffer;
    }
    private char[] generateRandomBuffer(int blockSize){
        char [] buffer = new char[blockSize];
        for(int i=0; i<blockSize; i++)
            buffer[i]= (char) ((random.nextInt())&0xf);
        return buffer;
    }
    public Block retrieveMainKey()
    {
        return mainKey;
    }
    public Block retrieveKeyByBlock()
    {
        if(roundNumber<0 | roundNumber>numberOfRound)
            return null;
        if(mode==0)
        {
            return blocks[roundNumber++];
        }
        else
        {
            return blocks[--roundNumber];
        }
    }

    public void setEncryptionMode() {
        mode=0;
        roundNumber=0;
    }
    public void setDecryptionMode() {
        mode=1;
        roundNumber=numberOfRound;
    }
    public void print()
    {
        for(Block block : blocks)
            block.print();
    }
}