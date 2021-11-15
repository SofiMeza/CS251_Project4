import java.util.ArrayList;

public class GlobalNet
{
    //creates a global network 
    //O : the original graph
    //regions: the regional graphs
    public static Graph run(Graph O, Graph[] regions) 
    {
	    //TODO
        for (int i = 0; i < regions.length; i++) { // loop through all the regions

            for (int j = 0; j < regions[i].V(); j++) { // loop through all the vertices of given region

                Dijkstra(O, j); // call Dijkstra from every vertex of every region






            }




        }







        return null;
    }

    private static void Dijkstra(Graph G, int source) {
        int[] dist = new int[G.V()];
        int[] prev = new int[G.V()];
        DistQueue pq = new DistQueue(G.V());

        dist[source] = 0;
        for (int i = 0; i < G.V(); i++) {
            if (i != source) {
                dist[i] = Integer.MAX_VALUE;
            } else {
                dist[i] = 0;
            }
            prev[i] = -1;
            pq.insert(i, dist[i]);
        }

        while (!pq.isEmpty()) {
            int current = pq.delMin();
            ArrayList<Integer> adjacents = G.adj(current);

            for (int i = 0; i < adjacents.size(); i++) {
                if (pq.inQueue(adjacents.get(i))) {
                    int d = dist[current] + G.getEdgeWeight(current, adjacents.get(i));
                    if (d < dist[adjacents.get(i)]) {
                        dist[adjacents.get(i)] = d;
                        prev[adjacents.get(i)] = current;
                        pq.set(adjacents.get(i), d);
                    }
                }
            }
        }
//        for (int i = 0; i < dist.length; i++) {
//            System.out.println(dist[i]);
//        }
//        for (int i = 0; i < prev.length; i++) {
//            System.out.println(prev[i]);
//        }
//        System.out.println("test");
    }

    public static void main(String[] args) {
        Graph GT = new Graph("src/testFiles/miTestB.txt");
        Graph region1 = new Graph("src/testFiles/miRegion1.txt");
        Graph region2 = new Graph("src/testFiles/miRegion2.txt");
        Graph region3 = new Graph("src/testFiles/miRegion3.txt");

        Graph[] regions = new Graph[3];
        regions[0] = region1;
        regions[1] = region2;
        regions[2] = region3;

        run(GT, regions);
    }
}
    
    
    