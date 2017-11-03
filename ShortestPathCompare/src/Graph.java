// A Java program for Bellman-Ford's single source shortest path
// algorithm.
import java.util.*;
import java.lang.*;
import java.io.*;
 
// A class to represent a connected, directed and weighted graph
class Graph
{
    // A class to represent a weighted edge in graph
    class Edge {
        int src, dest;
        String name;
        double weight;
        Edge() {
            src = dest = 0;
            weight = 0;
            name = "";
        }
    };
 
    int V, E;
    Edge edge[];
 
    // Creates a graph with V vertices and E edges
    Graph(int v, int e)
    {
        V = v;
        E = e;
        edge = new Edge[e];
        for (int i=0; i<e; ++i)
            edge[i] = new Edge();
    }
 
    // The main function that finds shortest distances from src
    // to all other vertices using Bellman-Ford algorithm.  The
    // function also detects negative weight cycle
    void BellmanFord(Graph graph,int src)
    {
        int V = graph.V, E = graph.E;
        double dist[] = new double[V];
 
        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE
        for (int i=0; i<V; ++i)
            dist[i] = Integer.MAX_VALUE;
        dist[src] = 0;
 
        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for (int i=1; i<V; ++i)
        {
            for (int j=0; j<E; ++j)
            {
                int u = graph.edge[j].src;
                int v = graph.edge[j].dest;
                double weight = graph.edge[j].weight;
                if (dist[u]!=Integer.MAX_VALUE &&
                    dist[u]+weight<dist[v])
                    dist[v]=dist[u]+weight;
            }
        }
 
        // Step 3: check for negative-weight cycles.  The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        //  path, then there is a cycle.
        for (int j=0; j<E; ++j)
        {
            int u = graph.edge[j].src;
            int v = graph.edge[j].dest;
            double weight = graph.edge[j].weight;
            if (dist[u]!=Integer.MAX_VALUE &&
                dist[u]+weight<dist[v])
              System.out.println("Graph contains negative weight cycle");
        }
        printArr(dist, V);
    }
 
    // A utility function used to print the solution
    void printArr(double dist[], int V)
    {
        System.out.println("Vertex   Distance from Source");
        for (int i=0; i<V; ++i)
            System.out.println(edge[i].name+"\t\t"+dist[i]);
    }
 
    // Driver method to test above function
    public static void main(String[] args)
    {
        int V = 5;  // Number of vertices in graph
        int E = 10;  // Number of edges in graph
 
        Graph graph = new Graph(V, E);
 
       // Hobbiton 0  > Loth 1
        graph.edge[0].src = 0;
        graph.edge[0].name = "Hobbiton";
        graph.edge[0].dest = 1;
        graph.edge[0].weight = 100.2;
 
       //Hobbiton 0  > Bree 3
        graph.edge[1].src = 0;
        graph.edge[1].name = "Lothlorien";

        graph.edge[1].dest = 3;
        graph.edge[1].weight = 5.2;
 
//       Loth 1 to Gondor 2 
        graph.edge[2].src = 1;
        graph.edge[2].name = "Gondor";
        graph.edge[2].dest = 2;
        graph.edge[2].weight = 10.9;
 
        // Loth 1 to Bree 3
        graph.edge[3].name = "Bree";

        graph.edge[3].src = 1;
        graph.edge[3].dest = 3;
        graph.edge[3].weight = 20.7;
 
        // Gondor 2 to Riven 4
        graph.edge[4].src = 2;
        graph.edge[4].name = "Rivendell";
        graph.edge[4].dest = 4;
        graph.edge[4].weight = 40.98;
 
        // Bree 3 to Loth 1
        graph.edge[5].src = 3;
        graph.edge[5].name = "Rivendell";
        graph.edge[5].dest = 1;
        graph.edge[5].weight = 30.3;
 
        // Bree 3 to Gondor 2

        graph.edge[6].src = 3;
        graph.edge[6].dest = 2;
        graph.edge[6].weight = 92;
 
        // Bree 3 to Rivendell 4

        graph.edge[7].src = 3;
        graph.edge[7].dest = 4;
        graph.edge[7].weight = 23.6;
        
        //Riven 4 to Gondor 2
        graph.edge[8].src = 4;
        graph.edge[8].dest = 2;
        graph.edge[8].weight = 68;
        
        //Riven 4 to Hobbiton 0

        graph.edge[9].src = 4;
        graph.edge[9].dest = 0;
        graph.edge[9].weight = 77.7;
 
        graph.BellmanFord(graph, 0);
    }
}