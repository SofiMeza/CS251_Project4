import java.util.ArrayList;

public class RegNet
{
    //creates a regional network
    //G: the original graph
    //max: the budget
    public static Graph run(Graph G, int max) 
    {
	    //TODO
        // Step 1: Get MSP using Kruskal
        Graph MST = kruskalMST(G);

        // Step 2: Make MSP fit budget & delete stray vertices
//        while (MST.totalWeight() > max) {
//            MST = graphInBudget(MST, max);
//        }
        MST = graphInBudget(MST, max);
        MST = MST.connGraph();

        System.out.println(MST.toString());

        // Step 3: Calculate the number of stops between each pair of airports
        BFS(MST,0);

        return null;
    }
    private static void BFS(Graph MST, int source) {
        boolean[] isVisited = new boolean[MST.V()];
        ArrayList<Integer> queue = new ArrayList<>();

        isVisited[source] = true;
        queue.add(source);

        int i = source;
        int temp = 0;

        int count = 0;

        while(!queue.isEmpty()) {
            ArrayList<Integer> adjacents = MST.adj(MST.getCode(i)); //gets adjacent nodes to source

            int current = queue.remove(0);
            System.out.print( current + " - ");
            count++;

            while (!adjacents.isEmpty()) {
                temp = adjacents.remove(0);
                if (!isVisited[temp]) {
                    isVisited[temp] = true;
                    queue.add(temp);
                }
            }
           i = temp;
        }
    }

    private static Graph graphInBudget(Graph MST, int max) {
        ArrayList<Edge> sortedEdges =  MST.sortedEdges();
        boolean removed = false;
        int lastEdge = sortedEdges.size() -1;
        int weight = MST.totalWeight();

        while (weight > max) {
            Edge currentEdge = sortedEdges.get(lastEdge);
            if (MST.deg(currentEdge.u) < 2 || MST.deg(currentEdge.v) < 2) {
                MST.removeEdge(currentEdge);
                weight = weight - currentEdge.w;
                sortedEdges.remove(currentEdge);
                lastEdge = sortedEdges.size() -1;
            } else {
                lastEdge--;
            }
        }
//        // gets all the vertices that can be removed and leave only one stray vertex generated.
//        for (int i = 0; i < codes.length; i++) {
//            if (MST.deg(codes[i]) == 1) {
//                leafs.add(codes[i]);
//            }
//        }
//        boolean breakOut = false;
//
//        for (int i = sortedEdges.size() - 1; i >= 0 ; i--) { // loop through all the edges
//            for (int j = 0; j < leafs.size(); j++) { // loops through the leafs
//                if (sortedEdges.get(i).u.equals(leafs.get(j)) || sortedEdges.get(i).v.equals(leafs.get(j))) {
//                    MST.removeEdge(sortedEdges.get(i));
//                    breakOut = true;
//                    break;
//                }
//                if (breakOut == true) {
//                    break;
//                }
//            }
//            if (breakOut == true) {
//                break;
//            }
//        }
        return MST;
    }

    private static Graph kruskalMST(Graph G) {
        ArrayList<Edge> sortedEdges =  G.sortedEdges();
        UnionFind unionFind = new UnionFind(G.V());
        Graph MST = new Graph(G.V());
        MST.setCodes(G.getCodes());

        while (MST.E() < (MST.V() - 1)) {
            Edge edge = sortedEdges.remove(0);

            if (unionFind.find(edge.ui()) != unionFind.find(edge.vi())) {
                MST.addEdge(edge);
                unionFind.union(edge.ui(), edge.vi());
            }
        }
        return MST;
    }

    public static void main(String[] args) {
        Graph GT = new Graph("src/testFiles/miTest.txt");
        run(GT, 10);
    }
}

