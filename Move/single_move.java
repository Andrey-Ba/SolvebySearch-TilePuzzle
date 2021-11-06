package Move;

import utilities.utils;

import java.util.Arrays;

public class single_move implements move {
    private final char move_type;
    private final int[] etile_pos;

    public single_move(char mt, int[] pos)
    {
        move_type = mt;
        etile_pos = pos;
    }

    @Override
    public char getMove_type() {
        return move_type;
    }

    @Override
    public int[][] do_move(int[][] prev_stage) {
        int[][] new_stage = utils.copy_matrix(prev_stage);
        utils.move_one_tile(move_type, new_stage, etile_pos);
        return new_stage;
    }

    @Override
    public String describe(int[][] state) {
        String st = "";
        st += state[etile_pos[0]][etile_pos[1]];
        st += move_type;
        return st;
    }

    @Override
    public int cost() {
        return 5;
    }

    public int[] getEmptyTilePos() {
        return etile_pos;
    }

    @Override
    public String toString() {
        return "Moves.single_move{" +
                "move_type=" + move_type +
                ", etile_pos=" + Arrays.toString(etile_pos) +
                '}';
    }

    // Compare by Moves.move type.
    @Override
    public int compareTo(move o) {
        // Double Moves.move has higher priority.
        if(o instanceof double_move)
            return -1;
        return utils.CompareMoves(move_type, o.getMove_type());
    }
}
