package Node;

import Move.move;

import java.util.ArrayList;

public interface node extends Comparable<node>{

    public ArrayList<move> getMoves();
    public node getParent();
    public String getPrevMove();
    public boolean isOut();
    public void setOut(boolean b);
    public int G();
    public int H();
    public int F();
}
