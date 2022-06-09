package graf;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFileFormat {
    FromFile ff = new FromFile();
    String path1 = "src/test/java/graph_example";
    File file1 = new File(path1);
    String absolutePath1 = file1.getAbsolutePath();
    String path2 = "src/test/java/graph_badformat";
    File file2 = new File(path2);
    String absolutePath2 = file2.getAbsolutePath();

    @Test
    public void testGoodFormat() throws FileNotFoundException {
        assertEquals(true, ff.checkFileFormat(absolutePath1));
    }
    @Test
    public void testBadFormat() throws FileNotFoundException {
        assertEquals(false, ff.checkFileFormat(absolutePath2));
    }
    @Test
    public void testReadX() throws FileNotFoundException {
        assertEquals(5, ff.readX(absolutePath1));
    }
    @Test
    public void testReadY() throws FileNotFoundException {
        assertEquals(5, ff.readY(absolutePath1));
    }
}
