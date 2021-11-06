package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import Move.*;
import Node.*;
import utilities.*;

public class DFBnB implements Algo {
    private final node start;

    public DFBnB(node start) {
        this.start = start;
    }

    @Override
    public boolean run() {
        Stack<node> L = new Stack<>();
        HashMap<node, node> H = new HashMap<>();
        L.add(start);
        H.put(start, start);
        node result = null;
        int t = Integer.MAX_VALUE;
        while (!L.isEmpty()) {
            if (settings.open)
                utils.print_open_list(H);
            node n = L.pop();
            if (n.isOut())
                H.remove(n);
            else {
                n.setOut(true);
                L.add(n);
                ArrayList<node> N = new ArrayList<>();
                for (move m : n.getMoves()) {
                    N.add(new tile_puzzle_node(n, m));
                }
                Collections.sort(N);
                for (int i = 0; i < N.size(); i++) {
                    node g = N.get(i);
                    if (g.F() >= t) {
                        while (i < N.size())
                            N.remove(i);
                    } else if (H.containsKey(g) && H.get(g).isOut())
                        N.remove(i);
                    else if (H.containsKey(g) && !H.get(g).isOut())
                        if (H.get(g).F() <= g.F()) {
                            N.remove(i);
                        } else {
                            L.remove(H.get(g));
                            H.remove(g);
                        }
                    else if (utils.goal(g)) {
                        t = g.F();
                        result = g;
                        while (i < N.size())
                            N.remove(i);
                    }
                }
                for (int i = N.size() - 1; i >= 0; i--) {
                    L.add(N.get(i));
                    H.put(N.get(i), N.get(i));
                }
            }
        }
        if (result == null) {
            utils.output(null);
            return false;
        }
        utils.output(result);
        return true;
    }
}
