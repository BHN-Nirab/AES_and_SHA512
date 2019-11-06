package main.AES;

public class Block {
    final int size=16;
    private char [] buffer;

    public Block(String str)
    {
        while (str.length()<16) str+=" ";

        buffer = new char[size];
        for(int i=0; i<size; i++)
        {
            buffer[i]=str.charAt(i);
        }
    }
    public Block(char [] buffer)
    {
        this.buffer=buffer;
    }
    public void shiftLeft()
    {
        char [] tmp = new char[16];
        for(int i=0; i<4; i++)
        {
            for(int j=0; j<4; j++)
            {
                tmp[i*4+j]=buffer[i*4+(i+j)%4];
            }
        }
        buffer=tmp;
    }
    public void shiftRight()
    {
        char [] tmp = new char[16];
        for(int i=0; i<4; i++)
        {
            for(int j=0; j<4; j++)
            {
                tmp[i*4+j]=buffer[i*4+(4+j-i)%4];
            }
        }
        buffer=tmp;
    }
    public void xor(Block keyBlock)
    {
        for(int i=0; i<size; i++)
            buffer[i]=(char)(buffer[i]^keyBlock.get(i));
    }
    public char get(int index)
    {
        return buffer[index];
    }
    public char get(int i, int j)
    {
        return buffer[j*4+i];
    }
    public void set(int index, char c)
    {
        buffer[index]=c;
    }
    public void set(int i, int j, char c)
    {
        buffer[j*4+i]=c;
    }
    public void print()
    {
        for(int i=0; i<4; i++)
        {
            for(int j=0; j<4; j++)
            {
                System.out.printf("%x ", (int) buffer[j*4+i]);
            }
            System.out.println();
        }
    }
    public String toString()
    {
        return new String(buffer);
    }
}