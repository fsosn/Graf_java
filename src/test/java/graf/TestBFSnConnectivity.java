package graf;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TestBFSnConnectivity
{
    String path1 = "src/test/java/graph_example";
    File file1 = new File(path1);
    String absolutePath1 = file1.getAbsolutePath();
    String path2 = "src/test/java/graph_example_split";
    File file2 = new File(path2);
    String absolutePath2 = file2.getAbsolutePath();
    Connectivity c = new Connectivity();
    @Test
    public void testBFS_noSplit() throws FileNotFoundException {
        assertEquals("Graf jest spójny", c.CheckConnectivity(absolutePath1, 1));
    }
    @Test
    public void testBFS_Split() throws FileNotFoundException {
        assertNotEquals("Graf jest spójny", c.CheckConnectivity(absolutePath2, 1));
    }

}