import Algorithms.Algo;
import Node.node;
import Node.tile_puzzle_node;
import utilities.settings;
import utilities.utils;

public class Ex1 {
    public static void main(String[] args) {
        settings.read_file();
        node start = new tile_puzzle_node(settings.start);
        Algo a = utils.setAlgo(start);
        long time = System.currentTimeMillis();
        a.run();
        if (settings.time)
            utils.add_time((float) (System.currentTimeMillis() - time) / 1000);
    }
}
