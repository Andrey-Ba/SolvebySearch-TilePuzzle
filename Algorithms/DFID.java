package Algorithms;

import Move.*;
import Node.*;
import utilities.*;

import java.util.HashMap;

public class DFID implements Algo {
    private final node start;

    public DFID(node n) {
        start = n;
    }

    @Override
    public boolean run() {
        for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
            HashMap<node, node> H = new HashMap<>();
            int result = LimitedDFS(start, depth, H);
            if (result != 0)
                return result == 1;
        }
        utils.output(null);
        return false;
    }

    // 1 success
    // 0 cutoff
    // -1 fail
    private int LimitedDFS(node n, int limit, HashMap<node, node> H) {
        if (utils.goal(n)) {
            utils.output(n);
            return 1;
        } else if (limit == 0) return 0;
        else {
            H.put(n, n);
            boolean isCutoff = false;
            for (move m : n.getMoves()) {
                node new_node = new tile_puzzle_node(n, m);
                if (H.containsKey(new_node))
                    continue;
                int result = LimitedDFS(new_node, limit - 1, H);
                if (result == 0)
                    isCutoff = true;
                else if (result != -1)
                    return result;
            }
            if (settings.open)
                utils.print_open_list(H);
            H.remove(n);
            if (isCutoff) {
                return 0;
            } else
                return -1;
        }
    }
}
