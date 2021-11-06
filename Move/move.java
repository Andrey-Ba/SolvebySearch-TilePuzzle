package Move;

public interface move extends Comparable<move>{
    public char getMove_type();
    public int[][] do_move(int[][] prev_stage);
    public String describe(int[][] prev_stage);
    public int cost();
}
