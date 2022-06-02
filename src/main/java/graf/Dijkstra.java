package graf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {
    static class AdjacencyList
    {
        int vertex; //wierzchołek
        double weight; //waga przejścia między wierzchołkami

        //lista sąsiedztwa
        AdjacencyList(int v, double w)
        {
            vertex = v;
            weight = w;
        }
        int getVertex()
        {
            return vertex;
        }
        double getWeight()
        {
            return weight;
        }
    }

    public static double dijkstra(int V, ArrayList<ArrayList<AdjacencyList> > graph, int ps, int pk)
    {
        double[] distance = new double[V]; //przebyty dystans od danego wierzchołka do określnego innego wierzchołka

        for (int i = 0; i < V; i++)
            distance[i] = Integer.MAX_VALUE;

        distance[ps] = 0; //ustawiam wartość dystansu jako 0 dla wierzchołka początkowego 'ps'

        //kolejka priorytetowa porównująca wartości krawędzi
        PriorityQueue<AdjacencyList> pq = new PriorityQueue<>(Comparator.comparingDouble(AdjacencyList::getWeight));
        pq.add(new AdjacencyList(ps, 0)); //dodaję do kolejki wierzchołek 'ps' jako pierwszy element

        while (pq.size() > 0)
        {
            AdjacencyList current = pq.poll();

            for (AdjacencyList n : graph.get(current.getVertex()))
            {
                //jeśli nowy dystans ma mniejszą wartość
                if (distance[current.getVertex()] + n.getWeight() < distance[n.getVertex()])
                {
                    distance[n.getVertex()] = n.getWeight() + distance[current.getVertex()];

                    //dodaję obecny wierzchołek do kolejki
                    pq.add(new AdjacencyList(n.getVertex(), distance[n.getVertex()]));
                }
            }
        }

        return distance[pk];
    }

    void addEdgeWeights(ArrayList<ArrayList<AdjacencyList> > graph, double[] value, int[] row, int[] column)
    {
        for(int i = 0; i < value.length; i++){
            graph.get(row[i]).add(new AdjacencyList(column[i], value[i]));
        }
    }
}
