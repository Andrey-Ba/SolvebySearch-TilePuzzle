package utilities;

import Algorithms.Algo_type;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class settings {
    public static Algo_type algo;
    public static boolean time;
    public static boolean open;
    public static int n;
    public static int m;
    public static int[][] start;
    public static int[][] goal;

    // In case that the goal is not ordered, the way of calculating the heuristic function is different.
    public static boolean one_tile = false;
    public static boolean ordered_goal = false;
    public static HashMap<Integer, int[]> unordered_goal;

    public static void read_file() {
        try {
            File input = new File("input.txt");
            Scanner reader = new Scanner(input);
            setAlgo(reader.nextLine());
            setTime(reader.nextLine());
            setOpen(reader.nextLine());
            setNxM(reader.nextLine());
            start = new int[n][m];
            goal = new int[n][m];
            scanner_to_matrix(reader, start);
            reader.nextLine();
            scanner_to_matrix(reader, goal);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        checkifordered();
        if (!ordered_goal)
            setUnordered_goal();
    }

    private static void scanner_to_matrix(Scanner reader, int[][] matrix) {
        for (int i = 0; i < n; i++) {
            String line = reader.nextLine();
            int line_i = 0;
            for (int j = 0; j < m - 1; j++) {
                String num = "";
                while (line.charAt(line_i) != ',')
                    num += line.charAt(line_i++);
                if (num.equals("_")) {
                    matrix[i][j] = -1;
                    if (matrix == goal)
                        one_tile = !one_tile;
                } else {
                    matrix[i][j] = Integer.parseInt(num);
                }
                line_i++;
            }
            String last_num = "";
            while (line_i < line.length()) {
                last_num += line.charAt(line_i++);
            }
            if (last_num.equals("_")) {
                matrix[i][m - 1] = -1;
                if (matrix == goal)
                    one_tile = !one_tile;
            } else {
                matrix[i][m - 1] = Integer.parseInt(last_num);
            }
        }
    }

    private static void setAlgo(String line) {
        switch (line) {
            case "BFS":
                algo = Algo_type.BFS;
                break;
            case "DFID":
                algo = Algo_type.DFID;
                break;
            case "A*":
                algo = Algo_type.A_STAR;
                break;
            case "IDA*":
                algo = Algo_type.IDA_STAR;
                break;
            case "DFBnB":
                algo = Algo_type.DFBNB;
                break;
        }
    }

    private static void setTime(String line) {
        time = line.equals("with time");
    }

    private static void setOpen(String line) {
        open = line.equals("with open");
    }

    private static void setNxM(String line) {
        String sn = "";
        String sm = "";
        int i = 0;
        while (line.charAt(i) != 'x')
            sn += line.charAt(i++);
        i++;
        while (i < line.length())
            sm += line.charAt(i++);
        n = Integer.parseInt(sn);
        m = Integer.parseInt(sm);
    }

    private static void checkifordered() {
        if (n == 1 && m == 1) {
            ordered_goal = true;
            one_tile = true;
            return;
        }
        if (goal[n - 1][m - 1] != -1) {
            ordered_goal = false;
            return;
        }
        if (!one_tile) {
            if (m <= 1 && goal[n - 2][0] != -1) {
                ordered_goal = false;
                return;
            } else {
                if (goal[n - 1][m - 2] != -1) {
                    ordered_goal = false;
                    return;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (goal[i][j] != -1 && goal[i][j] != j + i * m + 1) {
                    ordered_goal = false;
                    return;
                }
            }
        }
        ordered_goal = true;
    }

    private static void setUnordered_goal() {
        unordered_goal = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (goal[i][j] != -1) {
                    unordered_goal.put(goal[i][j], new int[]{i, j});
                }
            }
        }
    }
}
