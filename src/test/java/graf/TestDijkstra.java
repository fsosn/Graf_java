package graf;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDijkstra {
    String path1 = "src/test/java/graph_dijkstra";
    File file1 = new File(path1);
    String absolutePath1 = file1.getAbsolutePath();

    ShortestPath sp = new ShortestPath();

    @Test
    public void testDijkstra() throws FileNotFoundException {
        ArrayList<Number> arr = sp.getShortestPath(absolutePath1, 0, 3);
        assertEquals(0.30449515067891536+0.6584189660138557, arr.get(0));
    }
}
