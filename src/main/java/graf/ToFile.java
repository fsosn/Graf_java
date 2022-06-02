package graf;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ToFile {

    public void writeToFile(String fileName, int x, int y, double[] value, int[] row, int[] column)
    {
        int currentRow;
        int j = 0;

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            writer.write(x + " " + y + "\n");
            while(j < value.length)
            {
                currentRow = row[j];
                while(currentRow == row[j])
                {
                    writer.write(column[j] + ": " + value[j] +"\t");
                    if(j != value.length-1)
                      j++;
                    if(j == value.length-1)
                        break;
                }
                if(j != value.length-1)
                    writer.write("\n");
                else {
                    writer.write(column[j] + ": " + value[j]);
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
