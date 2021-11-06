package Algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import Move.*;
import Node.*;
import utilities.*;

public class BFS implements Algo {
    private final node start;

    public BFS(node n) {
        start = n;
    }

    @Override
    public boolean run() {
        Queue<node> L = new LinkedList<>();
        L.add(start);
        HashMap<node, node> L2 = new HashMap<>();
        L2.put(start, start);
        HashMap<node, node> C = new HashMap<>();
        while (!L.isEmpty()) {
            if (settings.open)
                utils.print_open_list(L2);
            node n = L.remove();
            L2.remove(n);
            C.put(n, n);
            for (move m : n.getMoves()) {
                node new_node = new tile_puzzle_node(n, m);
                if (!C.containsKey(new_node) && !L2.containsKey(new_node)) {
                    if (utils.goal(new_node)) {
                        utils.output(new_node);
                        return true;
                    }
                    L.add(new_node);
                    L2.put(new_node, new_node);
                }
            }
        }
        utils.output(null);
        return false;
    }
}
