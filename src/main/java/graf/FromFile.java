package graf;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FromFile {
    private int numberOfLines(String filename)
    {
        int count = 0;

        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            while(sc.hasNextLine())
            {
                sc.nextLine();
                count++;
            }

            sc.close();

        } catch (Exception e)
        {
            e.getStackTrace();
        }

        return count;
    }

    public int numOfLinks(String filename)
    {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            int count = 0;
            int c;
            while ((c = in.read()) >= 0) {
                if (c == ':') {
                    count++;
                }
            }
            return count;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public int readX(String filename) throws FileNotFoundException {
        File f = new File(filename);
        Scanner sc1 = new Scanner(f);

        String firstLine = sc1.nextLine();
        sc1.close();

        String[] dimensions = firstLine.split(" ");

        return Integer.parseInt(dimensions[0]);
    }

    public int readY(String filename) throws FileNotFoundException {
        File f = new File(filename);
        Scanner sc1 = new Scanner(f);

        String firstLine = sc1.nextLine();
        sc1.close();

        String[] dimensions = firstLine.split(" ");

        return Integer.parseInt(dimensions[1]);
    }

    private void readVals(String filename, double[] value, int[] row, int[] column)
    {
        try
        {
            File f = new File(filename);
            Scanner sc1 = new Scanner(f);

            String firstLine = sc1.nextLine();
            sc1.close();

            String[] dimensions = firstLine.split(" ");

            int x_axis = Integer.parseInt(dimensions[0]);
            int y_axis = Integer.parseInt(dimensions[1]);
            int matrix_dim = x_axis * y_axis;

            File file = new File(filename);
            Scanner sc = new Scanner(file);
            sc.nextLine(); //pomijam pierwszą linię

            String line;
            String[] data;

            int row_index = 0;
            int value_index = 0;
            int column_index = 0;

            int j;
            int i = 0;
            int links;

            while(sc.hasNextLine())
            {
                j = 0;
                links = 0;

                line = sc.nextLine();
                line = line.replaceAll(": "," ");
                line = line.replaceAll("\t"," ");
                data = line.split(" ");

                while (j< data.length)
                {
                    row [row_index] = i;
                    row_index++;

                    column[column_index] = Integer.parseInt(data[j]);
                    if(column[column_index] < 0 || column[column_index] > matrix_dim-1)
                        System.exit(1);
                    column_index++;
                    j++;

                    value[value_index] = Double.parseDouble(data[j]);
                    if(value[value_index] < 0 || value[value_index] > 1)
                        System.exit(1);
                    value_index++;
                    j++;

                    links++;
                    if(links > 4)
                        System.exit(1);
                }
                i++;
            }
            sc.close();
        }
        catch(IOException e)
        {
            e.getStackTrace();
        }
    }

    public boolean checkFileFormat(String filename)
    {
        try
        {
            File file = new File(filename);
            Scanner sc1 = new Scanner(file);

            String firstLine = sc1.nextLine();
            sc1.close();

            String[] dimensions = firstLine.split(" ");

            int howManyDims = dimensions.length;

            if(howManyDims != 2)
                return false;

            int x_axis = Integer.parseInt(dimensions[0]);
            int y_axis = Integer.parseInt(dimensions[1]);
            int matrix_dim = x_axis * y_axis;

            int lines = numberOfLines(filename) - 1; // pomijam pierwszą linię pliku (zawierającą x oraz y)

            if( lines != matrix_dim)
                return false;

            Scanner sc2 = new Scanner(file);
            sc2.nextLine();

            int links = numOfLinks(filename);

            if(links == 0)
                return false;

            IncidenceMatrix elements = new IncidenceMatrix();
            int max_links = elements.CountElements(x_axis, y_axis);

            if(links > max_links)
                return false;

        }
        catch(IOException e)
        {
            e.getStackTrace();
        }

        return true;
    }


    public void reader(String filename, double[] value, int[] row, int[] column)
    {
        if(checkFileFormat(filename)) {
            readVals(filename, value, row, column);
        }
    }
}
