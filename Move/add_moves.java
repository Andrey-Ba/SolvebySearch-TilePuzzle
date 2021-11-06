package Move;

import utilities.*;

import java.util.ArrayList;

public class add_moves {
    private final ArrayList<move> moves;
    private final ArrayList<int[]> empty_tiles;
    private final move prev_move;
    private final int[][] state;

    public add_moves(ArrayList<move> m, move pmove, int[][] state) {
        moves = m;
        empty_tiles = utils.findEmptySpaces(state);
        prev_move = pmove;
        this.state = state;
    }

    // Add all of the possible moves.
    private void addFirstMoves() {
        if (empty_tiles.size() == 2) {
            double_move_type type = utils.TypeOf2EmptyTiles(empty_tiles);
            switch (type) {
                case Horizontal:
                    addDoubleMoveUp();
                    addDoubleMoveDown();
                    break;
                case Vertical:
                    addDoubleMoveLeft();
                    addDoubleMoveRight();
                    break;
            }
        }
        for (int[] empty_tile : empty_tiles) {
            addSingleMoves(empty_tile);
        }
    }

    public void addMoves() {
        // If first node, add all possible moves.
        if (prev_move == null) {
            addFirstMoves();
            return;
        }

        // If it has only one empty tile, add all possible single moves.
        if (empty_tiles.size() == 1) {
            addConditionedSingleMoves(empty_tiles.get(0));
            return;
        }

        // Get type of double move.
        double_move_type type = utils.TypeOf2EmptyTiles(empty_tiles);
        //If there are 2 empty tiles, add all possible double moves.
        switch (type) {
            case Horizontal:
                if (!(prev_move instanceof double_move) || prev_move.getMove_type() != 'D')
                    addDoubleMoveUp();
                if (!(prev_move instanceof double_move) || prev_move.getMove_type() != 'U')
                    addDoubleMoveDown();
                break;
            case Vertical:
                if (!(prev_move instanceof double_move) || prev_move.getMove_type() != 'R')
                    addDoubleMoveLeft();
                if (!(prev_move instanceof double_move) || prev_move.getMove_type() != 'L')
                    addDoubleMoveRight();
                break;
        }
        // If the Moves.move before was a double move then add all legal moves to each empty tile.
        if (prev_move instanceof double_move) {
            for (int[] empty_tile : empty_tiles)
                addConditionedSingleMoves(empty_tile);
        } else {
            for (int[] empty_tile : empty_tiles) {
                // Check if the current tile was the one that moves.
                if (isMovedEmptyTile(empty_tile))
                    addConditionedSingleMoves(empty_tile);
                else {
                    // Otherwise, add all possible moves.
                    addSingleMoves(empty_tile);
                }
            }
        }
    }

    // Given coordinates of empty tile returns true if it's the one moved.
    private boolean isMovedEmptyTile(int[] empty_tile) {
        int[] from = ((single_move) prev_move).getEmptyTilePos();
        switch (prev_move.getMove_type()) {
            case 'L':
                return empty_tile[0] == from[0] && empty_tile[1] == from[1] + 1;
            case 'U':
                return empty_tile[0] == from[0] + 1 && empty_tile[1] == from[1];
            case 'R':
                return empty_tile[0] == from[0] && empty_tile[1] + 1 == from[1];
            case 'D':
                return empty_tile[0] + 1 == from[0] && empty_tile[1] == from[1];
        }
        return false;
    }

    private void addSingleMoves(int[] empty_tile)
    {
        addLeftMove(empty_tile);
        addUpperMove(empty_tile);
        addRightMove(empty_tile);
        addDownMove(empty_tile);
    }

    private void addConditionedSingleMoves(int[] empty_tile) {
        if (prev_move.getMove_type() != 'R')
            addLeftMove(empty_tile);
        if (prev_move.getMove_type() != 'D')
            addUpperMove(empty_tile);
        if (prev_move.getMove_type() != 'L')
            addRightMove(empty_tile);
        if (prev_move.getMove_type() != 'U')
            addDownMove(empty_tile);
    }

    private void addDoubleMoveLeft() {
        if (empty_tiles.get(0)[1] == settings.m - 1)
            return;
        moves.add(new double_move('L', empty_tiles));
    }

    private void addDoubleMoveUp() {
        if (empty_tiles.get(0)[0] == settings.n - 1)
            return;
        moves.add(new double_move('U', empty_tiles));
    }

    private void addDoubleMoveRight() {
        if (empty_tiles.get(0)[1] == 0)
            return;
        moves.add(new double_move('R', empty_tiles));
    }

    private void addDoubleMoveDown() {
        if (empty_tiles.get(0)[0] == 0)
            return;
        moves.add(new double_move('D', empty_tiles));
    }

    private void addLeftMove(int[] empty_tile) {
        if (empty_tile[1] == settings.m - 1 || state[empty_tile[0]][empty_tile[1] + 1] == -1)
            return;
        moves.add(new single_move('L', empty_tile));
    }

    private void addUpperMove(int[] empty_tile) {
        if (empty_tile[0] == settings.n - 1 || state[empty_tile[0] + 1][empty_tile[1]] == -1)
            return;
        moves.add(new single_move('U', empty_tile));
    }

    private void addRightMove(int[] empty_tile) {
        if (empty_tile[1] == 0 || state[empty_tile[0]][empty_tile[1] - 1] == -1)
            return;
        moves.add(new single_move('R', empty_tile));
    }

    private void addDownMove(int[] empty_tile) {
        if (empty_tile[0] == 0 || state[empty_tile[0] - 1][empty_tile[1]] == -1)
            return;
        moves.add(new single_move('D', empty_tile));
    }
}
