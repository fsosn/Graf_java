package graf;

import java.io.FileNotFoundException;

public class Connectivity extends FromFile
{
    public String CheckConnectivity(String filename, int ps) throws FileNotFoundException {
        int arrayLenght = numOfLinks(filename);

        double[] value = new double[arrayLenght];
        int[] row = new int[arrayLenght];
        int[] column = new int[arrayLenght];

        reader(filename, value, row, column);

        int n = readX(filename) * readY(filename);

        BreadthFirstSearch g = new BreadthFirstSearch(n);
        g.addAllEdges(row,column);

        return g.BFS(ps);
    }
}
