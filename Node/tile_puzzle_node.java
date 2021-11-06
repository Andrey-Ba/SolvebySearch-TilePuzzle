package Node;

import Move.*;
import utilities.*;

import java.util.ArrayList;
import java.util.Arrays;

public class tile_puzzle_node implements node {
    private final int[][] state;
    private final node parent;
    private move prev_move;
    private boolean out;
    private int cost;
    private int heuristic_value = 0;
    private int appearance;
    public static long count = 0;

    // The first Node.node.
    public tile_puzzle_node(int[][] state) {
        count++;
        this.state = state;
        parent = null;
        cost = 0;
        appearance = 0;
        heuristic_value = utils.GetHeuristicValue(state);
    }

    // Build a Node.node after giving the previous Node.node and the Moves.move that was taken.
    public tile_puzzle_node(node parent, move prev_move) {
        state = prev_move.do_move(((tile_puzzle_node) parent).state);
        this.prev_move = prev_move;
        this.parent = parent;
        count++;
        appearance = ((tile_puzzle_node) parent).appearance++;
        cost += parent.G() + prev_move.cost();
        heuristic_value = utils.GetHeuristicValue(state);
    }

    public int[][] getState() {
        return state;
    }

    @Override
    public void setOut(boolean b) {
        out = b;
    }

    @Override
    public boolean isOut() {
        return out;
    }

    // Returns a list with all legal moves.
    @Override
    public ArrayList<move> getMoves() {
        ArrayList<move> moves = new ArrayList<>();
        // Given the previous Moves.move and current state, adding to a list all legal moves.
        add_moves move_addition = new add_moves(moves, prev_move, state);
        move_addition.addMoves();
        return moves;
    }

    @Override
    public node getParent() {
        return parent;
    }

    @Override
    public int G() {
        return cost;
    }

    public int H() {
        return heuristic_value;
    }

    @Override
    public int F() {
        return cost + heuristic_value;
    }

    @Override
    public String getPrevMove() {
        return prev_move.describe(state);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        tile_puzzle_node node = (tile_puzzle_node) o;
        for (int i = 0; i < settings.n; i++)
            for (int j = 0; j < settings.m; j++)
                if (state[i][j] != node.state[i][j])
                    return false;
        return true;
    }

    @Override
    public String toString() {
        return utils.matrix_string(state);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }

    @Override
    public int compareTo(node o) {
        // Comparison level 1 (by f(n)).
        int level1 = F() - o.F();
        if (level1 != 0)
            return level1;
        // Comparison level 2 (by who was created first).
        int level2 = ((tile_puzzle_node) o).appearance - appearance;
        if (level2 != 0)
            return level2;
        // Comparison level 3 (by move type).
        return prev_move.compareTo(((tile_puzzle_node) o).prev_move);
    }
}
