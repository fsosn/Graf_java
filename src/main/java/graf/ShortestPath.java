package graf;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ShortestPath extends FromFile
{
    public ArrayList<Number> getShortestPath(String filename, int ps, int pk) throws FileNotFoundException
    {
        int arrayLenght = numOfLinks(filename);

        double[] value = new double[arrayLenght];
        int[] row = new int[arrayLenght];
        int[] column = new int[arrayLenght];

        reader(filename, value, row, column);

        int n = readX(filename) * readY(filename);

        if(pk == -1)
            pk = n-1;

        ArrayList<ArrayList<Dijkstra.AdjacencyList>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++)
        {
            graph.add(new ArrayList<>());
        }

        Dijkstra edges = new Dijkstra();
        edges.addEdgeWeights(graph, value, row, column);

        return Dijkstra.dijkstra(n, graph, ps, pk);
    }
}
