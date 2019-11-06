package main.AES;

public class MsgEnc {
    Block [] blocks;
    Key key;
    SubTable table;
    final int blockSize=16;
    char padding=0;

    public MsgEnc()
    {
        key=new Key(10);
        table=new SubTable(blockSize);
    }
    public void setNormalMessage(String str)
    {
        padding=0;
        while (str.length()%16!=0)
        {
            str+=" ";
            padding++;
        }

        prepareMessage(str);
    }
    public void setEncryptedMessage(String str)
    {
        String b = str.substring(1, str.length());

        padding=str.charAt(0);
        padding=(char) (padding & (char) 0xf);

        //System.out.println(padding+" "+b);

        prepareMessage(b);
    }
    public void prepareMessage(String str)
    {
        blocks = new Block[(int) Math.ceil((double)str.length()/blockSize)];

        for(int i=0; i<blocks.length; i++)
        {
            blocks[i]=new Block(str.substring(i*blockSize, (i+1)*blockSize));
        }
    }
    public void setBytes(byte [] bytes)
    {
        setNormalMessage(new String(bytes));
    }
    public String getEncryptedString()
    {
        String ret="";
        for(Block block : blocks)
        {
            AES aes = new AES(block);
            ret+=aes.getEncrypted();
        }

        return padding+ret;
    }
    public byte [] getEncryptedBytes()
    {
        return getEncryptedString().getBytes();
    }
    public String getDecryptedString()
    {
        String ret="";
        for(Block block : blocks)
        {
            AES aes = new AES(block);
            ret+=aes.getDecrypted();
        }

        return ret.substring(0,ret.length()-padding);
    }
    public byte [] getDecryptedBytes()
    {
        return getDecryptedString().getBytes();
    }
    public void printBlock()
    {
        for(Block block : blocks)
            block.print();
    }
}