package Move;

import utilities.utils;

import java.util.ArrayList;

public class double_move implements move {
    private final char move_type;
    private final ArrayList<int[]> emptytiles;

    public double_move(char movetype, ArrayList<int[]> etiles) {
        move_type = movetype;
        emptytiles = etiles;
    }

    @Override
    public char getMove_type() {
        return move_type;
    }

    @Override
    public int[][] do_move(int[][] prev_stage) {
        int[][] new_stage = utils.copy_matrix(prev_stage);
        for (int[] etile : emptytiles)
            utils.move_one_tile(move_type, new_stage, etile);
        return new_stage;
    }

    @Override
    public String describe(int[][] state) {
        String st = "";
        int[] etile1 = emptytiles.get(0);
        int[] etile2 = emptytiles.get(1);
        st += state[etile1[0]][etile1[1]];
        st += "&";
        st += state[etile2[0]][etile2[1]];
        st += move_type;
        return st;
    }

    @Override
    public int cost() {
        if (move_type == 'U' || move_type == 'D')
            return 7;
        return 6;
    }

    @Override
    public String toString() {
        return "Moves.double_move{" +
                "move_type=" + move_type +
                '}';
    }

    @Override
    public int compareTo(move o) {
        // Double Moves.move has higher priority.
        if(o instanceof single_move)
            return 1;
        return utils.CompareMoves(move_type, o.getMove_type());
    }
}
