package Algorithms;

import Move.*;
import Node.*;
import utilities.*;

import java.util.HashMap;
import java.util.PriorityQueue;

public class A_Star implements Algo {
    private final node start;

    public A_Star(node n) {
        start = n;
    }

    @Override
    public boolean run() {
        PriorityQueue<node> L = new PriorityQueue<>();
        HashMap<node, node> L2 = new HashMap<>();
        L.add(start);
        L2.put(start, start);
        HashMap<node, Integer> C = new HashMap<>();
        while (!L.isEmpty()) {
            if(settings.open)
                utils.print_open_list(L2);
            node n =  L.remove();
            L2.remove(n);
            if (utils.goal(n)) {
                utils.output(n);
                return true;
            }
            C.put(n, 1);
            for(move m : n.getMoves())
            {
                node new_node = new tile_puzzle_node(n, m);
                if(!C.containsKey(new_node) && !L2.containsKey(new_node))
                {
                    L.add(new_node);
                    L2.put(new_node, new_node);
                }
                else if(L2.containsKey(new_node) && L2.get(new_node).F() > new_node.F())
                {
                    L.remove(new_node);
                    L.add(new_node);
                    L2.remove(new_node);
                    L2.put(new_node,new_node);
                }
            }
        }
        utils.output(null);
        return false;
    }
}
