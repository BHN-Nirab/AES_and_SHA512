package main.AES;

public class Multiplier {
    int mode=0;
    char [][] mulMat = {
            {2,3,1,1},
            {1,2,3,1},
            {1,1,2,3},
            {3,1,1,2}};
    char [][] invMat = {
            {0x0E,0x0B,0x0D,0x09},
            {0x09,0x0E,0x0B,0x0D},
            {0x0D,0x09,0x0E,0x0B},
            {0x0B,0x0D,0x09,0x0E}};
    SubTable ETable;

    public Multiplier(SubTable ETable)
    {
        this.ETable = ETable;
    }
    private char small(char v1, char v2)
    {
        char t;
        if(v1==0 | v2==0)   return 0;
        if(v1==1)   return v2;
        if(v2==1)   return v1;

        v1= (char) (0xFF&v1);
        v2= (char) (0xFF&v2);

        char LV1 = ETable.getL(v1);
        char LV2 = ETable.getL(v2);

        t = (char) (LV1+LV2);
        if(t>0xFF)  t=(char) (t-0xFF);

        return ETable.getE(t);
    }
    private char addCells(int row, int col, Block block)
    {
        char [][] mat;
        if(mode==0) mat=mulMat;
        else mat=invMat;

        char ret = 0;
        for(int k=0; k<4; k++)
        {
            char t = small(mat[row][k], block.get(k, col));
            ret = (char) (ret^t);
        }
        return (char) (ret&0xFF);
    }
    public Block multiply(Block block)
    {
        char [] tmp = new char[16];
        Block sw = new Block(tmp);

        for(int i=0; i<4; i++)
        {
            for(int j=0; j<4; j++)
            {
                sw.set(i,j,addCells(i,j,block));
            }
        }
        return sw;
    }
    public void setEncryptionMode()
    {
        ETable.setEncryptionMode();
        mode=0;
    }
    public void setDecryptionMode()
    {
        ETable.setDecryptionMode();
        mode=1;
    }
}