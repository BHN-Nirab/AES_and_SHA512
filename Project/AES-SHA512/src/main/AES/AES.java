package main.AES;

public class AES {
    Block block;
    Key key;
    SubTable STable;
    Multiplier multiplier;
    int numberOfRound=10;
    int mode=0;

    public AES(Block block)
    {
        this.block=block;
        this.key=Values.getKey();
        this.STable=Values.getSTable();
        this.multiplier=Values.getMultiplier();
    }
    public void addRoundKey()
    {
        block.xor(key.retrieveKeyByBlock());
    }
    public void byteSub()
    {
        for(int i=0; i<block.size; i++)
        {
            block.set(i, STable.get(block.get(i)));
        }
    }
    public void shiftRows()
    {
        if(mode==0) block.shiftLeft();
        else block.shiftRight();
    }
    public void mixColum()
    {
        block = multiplier.multiply(block);
    }
    public String getEncrypted()
    {
        mode=0;
        key.setEncryptionMode();
        STable.setEncryptionMode();
        multiplier.setEncryptionMode();

        block.xor(key.retrieveMainKey());

        for(int i=0; i<numberOfRound; i++)
        {
            shiftRows();
            mixColum();
            addRoundKey();
            byteSub();
        }

        return block.toString();
    }
    public String getDecrypted()
    {
        mode=1;
        key.setDecryptionMode();
        STable.setDecryptionMode();
        multiplier.setDecryptionMode();

        for(int i=0; i<numberOfRound; i++)
        {
            byteSub();
            addRoundKey();
            mixColum();
            shiftRows();
        }
        block.xor(key.retrieveMainKey());

        return block.toString();
    }
}