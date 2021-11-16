import java.util.ArrayList;

public class GlobalNet
{
    //creates a global network 
    //O : the original graph
    //regions: the regional graphs
    public static Graph run(Graph O, Graph[] regions) 
    {
        ArrayList<Flights>[] min = new ArrayList[regions.length];
        for (int i = 0; i < min.length; i++) {
            min[i] = new ArrayList<Flights>();
        }
        for (int i = 0; i < regions.length; i++) { // loop through all the regions
            for (int j = 0; j < regions[i].V(); j++) { // loop through all the vertices of given region
//                System.out.println(regions[i].getCode(j));
//                System.out.println(O.index(regions[i].getCode(j)));

                int sourceRegions = getRegion(regions, regions[i].getCode(j));
//                System.out.println("Sending " + regions[i].getCode(j));
                min = Dijkstra(O, O.index(regions[i].getCode(j)), sourceRegions, regions, min); // call Dijkstra from every vertex of every region
            }
        }
        System.out.println();
        return null;
    }

    /*
    returns -1 if vertex is not in any region
    else return the region that the vertex belongs in
     */
    private static int getRegion(Graph[] regions, String vertex) {
        for (int i = 0; i < regions.length; i++) {
            for (int j = 0; j < regions[i].V(); j++) {

                if (regions[i].getCode(j).equals(vertex)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static ArrayList<Flights>[] Dijkstra(Graph G, int source, int sourceRegion, Graph[] regions, ArrayList<Flights>[] min) {
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

        min = getMin(dist, prev, sourceRegion, regions, G, source, min);
        return min;
    }

    private static ArrayList<Flights>[] getMin(int dist[], int[] prev, int sourceRegion, Graph[] regions, Graph G, int source, ArrayList<Flights>[] min) {
        //System.out.println("received" + sourceRegion);
        //ArrayList<Flights>[] min = new ArrayList[regions.length];


        int[] counter = new int[min.length];
        for (int i = 0; i < dist.length; i++) {
            String destination = G.getCode(i);
            int destRegion = getRegion(regions, destination);
            if (destRegion != -1 && destRegion != sourceRegion) { // if destination region is not same as source
                Flights newFlight = new Flights(source, i, sourceRegion, destRegion, dist[i]);
//                System.out.println(newFlight.toString());
//                System.out.println();

                boolean entered = false;
                if (min[sourceRegion].isEmpty()) {
                    min[sourceRegion].add(newFlight);
                } else {
                    for (int j = 0; j < min[sourceRegion].size(); j++) {
                        if (min[sourceRegion].get(j).destRegion == newFlight.destRegion) {
                            entered = true;
                            counter[sourceRegion] = counter[sourceRegion] + 1;
                            if (min[sourceRegion].get(j).distance > newFlight.distance) {
                                min[sourceRegion].remove(min[sourceRegion].get(j));
                                min[sourceRegion].add(newFlight);
                            }
                        }
                    }

                    if (!entered) {
                        if (counter[sourceRegion] < min.length) {
                            counter[sourceRegion] = counter[sourceRegion] + 1;
                            min[sourceRegion].add(newFlight);
                            entered = true;
                        } else {
                            min[sourceRegion].add(newFlight);
                        }

                    }
                }

            }
        }
        return min;
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
    
    
    