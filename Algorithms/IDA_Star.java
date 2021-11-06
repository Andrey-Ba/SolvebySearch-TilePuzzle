package Algorithms;

import Move.*;
import Node.*;
import utilities.*;

import java.util.HashMap;
import java.util.Stack;

public class IDA_Star implements Algo {
    private final node start;

    public IDA_Star(node start) {
        this.start = start;
    }

    @Override
    public boolean run() {
        Stack<node> L = new Stack<>();
        HashMap<node, node> H = new HashMap<>();
        int t = start.H();
        while (t != Integer.MAX_VALUE) {
            int minF = Integer.MAX_VALUE;
            // Reset start to be false.
            start.setOut(false);
            L.add(start);
            H.put(start, start);
            while (!L.isEmpty()) {
                if(settings.open)
                    utils.print_open_list(H);
                node n = L.pop();
                if (n.isOut())
                    H.remove(n);
                else {
                    n.setOut(true);
                    L.add(n);
                    for (move m : n.getMoves()) {
                        node g = new tile_puzzle_node(n, m);
                        if (g.F() > t) {
                            minF = Math.min(minF, g.F());
                            continue;
                        }
                        if (H.containsKey(g) && H.get(g).isOut())
                            continue;
                        if (H.containsKey(g) && !H.get(g).isOut())
                            if (H.get(g).F() > g.F()) {
                                L.remove(H.get(g));
                                H.remove(g);
                            } else
                                continue;
                        if (utils.goal(g)) {
                            utils.output(g);
                            return true;
                        }
                        L.add(g);
                        H.put(g, g);
                    }
                }
            }
            t = minF;
        }
        return false;
    }

}
