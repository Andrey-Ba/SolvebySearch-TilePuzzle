package utilities;

import Algorithms.*;
import Move.*;
import Node.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class utils {

    public static Algo setAlgo(node start) {
        Algo a = null;
        switch (settings.algo) {
            case BFS:
                a = new BFS(start);
                break;
            case DFID:
                a = new DFID(start);
                break;
            case A_STAR:
                a = new A_Star(start);
                break;
            case IDA_STAR:
                a = new IDA_Star(start);
                break;
            case DFBNB:
                a = new DFBnB(start);
                break;
        }
        return a;
    }

    // Given the position of the empty tiles returns if the empty tiles are horizontal, vertical or neither.
    public static double_move_type TypeOf2EmptyTiles(ArrayList<int[]> tilepos) {
        if (tilepos.size() == 1)
            return double_move_type.Neither;
        int[] empty_tile1 = tilepos.get(0);
        int[] empty_tile2 = tilepos.get(1);
        if (empty_tile1[0] == empty_tile2[0] && empty_tile2[1] - empty_tile1[1] <= 1 && empty_tile2[1] - empty_tile1[1] >= -1)
            return double_move_type.Horizontal;
        if (empty_tile1[1] == empty_tile2[1] && empty_tile2[0] - empty_tile1[0] <= 1 && empty_tile2[0] - empty_tile1[0] >= -1)
            return double_move_type.Vertical;
        return double_move_type.Neither;
    }

    // Finds empty spaces in matrix (Empty spaces denoted by -1)
    public static ArrayList<int[]> findEmptySpaces(int[][] state) {
        ArrayList<int[]> empty_tiles = new ArrayList<>();
        for (int i = 0; i < settings.n; i++)
            for (int j = 0; j < settings.m; j++)
                if (state[i][j] == -1) {
                    int[] position = {i, j};
                    empty_tiles.add(position);
                }
        return empty_tiles;
    }

    // Returns a copy of a given matrix
    public static int[][] copy_matrix(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;
        int[][] copy = new int[n][m];
        for (int i = 0; i < n; i++)
            System.arraycopy(mat[i], 0, copy[i], 0, m);
        return copy;
    }

    // Gets a matrix, type of Moves.move and coordinates of empty space, moving the tiles accordingly
    public static void move_one_tile(char move_type, int[][] stage, int[] etile) {
        switch (move_type) {
            case 'L':
                stage[etile[0]][etile[1]] = stage[etile[0]][etile[1] + 1];
                stage[etile[0]][etile[1] + 1] = -1;
                break;
            case 'U':
                stage[etile[0]][etile[1]] = stage[etile[0] + 1][etile[1]];
                stage[etile[0] + 1][etile[1]] = -1;
                break;
            case 'R':
                stage[etile[0]][etile[1]] = stage[etile[0]][etile[1] - 1];
                stage[etile[0]][etile[1] - 1] = -1;
                break;
            case 'D':
                stage[etile[0]][etile[1]] = stage[etile[0] - 1][etile[1]];
                stage[etile[0] - 1][etile[1]] = -1;
                break;
        }
    }

    public static void print_matrix(int[][] mat) {
        int m = mat[0].length;
        for (int[] ints : mat) {
            for (int j = 0; j < m; j++)
                System.out.print(ints[j] + ",");
            System.out.println();
        }
    }

    public static String matrix_string(int[][] mat) {
        int m = mat[0].length;
        StringBuilder st = new StringBuilder();
        for (int[] ints : mat) {
            for (int j = 0; j < m; j++)
                if (j < m - 1)
                    if (ints[j] == -1)
                        st.append("_").append(",");
                    else
                        st.append(ints[j]).append(",");
                else if (ints[j] == -1)
                    st.append("_");
                else
                    st.append(ints[j]);
            st.append('\n');
        }
        return st.toString();
    }

    public static boolean goal(node n) {
        int[][] state = ((tile_puzzle_node) n).getState();
        for (int i = 0; i < settings.n; i++)
            for (int j = 0; j < settings.m; j++)
                if (state[i][j] != settings.goal[i][j])
                    return false;
        return true;
    }

    public static String path(node n) {
        if (n == null)
            return "no path";
        if (n.getParent() == null) {
            return "";
        }
        String st = path(n.getParent());
        if (!st.equals(""))
            st += "-";
        return st + n.getPrevMove();
    }

    // The vertical and horizontal distance to it's goal state
    // If one empty tile multiply by 5
    // If two empty tiles multiply by 3
    // (Otherwise might not be admissible)
    public static int GetHeuristicValue(int[][] state) {
        int h = 0;
        for (int i = 0; i < settings.n; i++)
            for (int j = 0; j < settings.m; j++) {
                int tile = state[i][j];
                if (tile != -1)
                    h += TileDistanceToGoal(tile, i, j);
            }
        if (settings.one_tile) {
            return 5 * h;
        }
        return 3 * h;
    }

    public static int TileDistanceToGoal(int tile, int i, int j) {
        if (settings.ordered_goal)
            return Math.abs(i - ((tile - 1) / settings.m)) + Math.abs(j - ((tile - 1) % settings.m));
        return Math.abs(i - settings.unordered_goal.get(tile)[0]) + Math.abs(j - settings.unordered_goal.get(tile)[1]);
    }

    // Fills output.txt with the result
    public static void output(node n) {
        String line = "";
        line += utils.path(n);
        line += '\n';
        line += "Num: ";
        line += tile_puzzle_node.count;
        line += '\n';
        if (n != null) {
            line += "Cost: ";
            line += n.G();
        }
        try {
            FileWriter f = new FileWriter("output.txt");
            f.write(line);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Adds time if needed
    public static void add_time(float time) {
        try {
            FileWriter f = new FileWriter("output.txt", true);
            BufferedWriter bw = new BufferedWriter(f);
            bw.newLine();
            bw.write(time + " seconds");
            bw.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Prints open lists.
    static int num_of_open_lists = 1;

    public static void print_open_list(HashMap<node, node> hm) {
        System.out.println("Start of open list: " + num_of_open_lists);
        System.out.println();
        for (node n : hm.keySet()) {
            System.out.println(n);
        }
        System.out.println("End of open list: " + num_of_open_lists);
        System.out.println();
        num_of_open_lists++;
    }

    public static int CompareMoves(char m1, char m2) {
        if (m1 == m2)
            return 0;
        if (m1 == 'L')
            return 1;
        if (m2 == 'L')
            return -1;
        if (m1 == 'U')
            return 1;
        if (m2 == 'U')
            return -1;
        if (m1 == 'R')
            return 1;
        if (m2 == 'R')
            return -1;
        if (m1 == 'D')
            return 1;
        return -1;
    }
}
