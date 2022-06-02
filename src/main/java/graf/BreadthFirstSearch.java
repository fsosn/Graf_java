package graf;

import java.util.Iterator;
import java.util.LinkedList;

class BreadthFirstSearch {
    private int V; // liczba wierzchołków
    private LinkedList<Integer> adj[]; //lista sąsiedztwa

    // utworzenie listy sąsiedztwa (adj)
    BreadthFirstSearch(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    // dodanie pojedynczej krawędzi
    void addEdge(int v, int w)
    {
        adj[v].add(w);
    }

    // dodanie wszystkich krawędzi i przejść
    void addAllEdges(int[] row, int[] column)
    {
        for (int j = 0; j < row.length; j++)
        {
            addEdge(row[j], column[j]);
        }
    }

    // algorytm breadth-first search
    public String BFS(int ps)
    {
        String result = "Graf nie jest spójny";

        //oznaczam wszystkie wierzchołki jako nieodwiedzone
        boolean visited[] = new boolean[V];
        int checked = 0;

        // kolejka
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // punkt startowy został oznaczony jako odwiedzony oraz dodany do kolejki
        visited[ps] = true;
        queue.add(ps);

        while (queue.size() != 0) {
            // usunięcie punktu startowego z kolejki
            ps = queue.poll();

            Iterator<Integer> i = adj[ps].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
            checked++; //zwiększam liczbę odwiedzonych wierzchołków
        }
        if (V == checked) //jeśli liczba wierzchołków jest równa liczbie odwiedzonych, to graf jest spójny
        {
            result = "Graf jest spójny";
        }

        return result;
    }
}