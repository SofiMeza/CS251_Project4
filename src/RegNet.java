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
        while (MST.totalWeight() > max) {
            MST = graphInBudget(MST);
        }
        MST = MST.connGraph();

        // Step 3: Calculate the number of stops between each pair of airports
        BFS(MST,0);

        return null;
    }
    private static void BFS(Graph MST, int source) {
        boolean[] isVisited = new boolean[MST.V()];
        ArrayList<Integer> queue = new ArrayList<>();
        isVisited[source] = true;
        queue.add(source);
        int i = 0;
        int temp = 0;

        int count = 0;
        int[][] layoverMatrix = new int[MST.V()+1][MST.V()+1];

        // Matrix to store layover between airports
        for (int j = 1; j < MST.V() + 1; j++) {
            layoverMatrix[j][0] = j - 1;
            layoverMatrix[0][j] = j - 1;
        }

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

    private static Graph graphInBudget(Graph MST) {
        String[] codes = MST.getCodes();
        ArrayList<String> leafs = new ArrayList<>();
        ArrayList<Edge> sortedEdges =  MST.sortedEdges();

        // gets all the vertices that can be removed and leave only one stray vertex generated.
        for (int i = 0; i < codes.length; i++) {
            if (MST.deg(codes[i]) == 1) {
                leafs.add(codes[i]);
            }
        }
        boolean breakOut = false;

        for (int i = sortedEdges.size() - 1; i >= 0 ; i--) { // loop through all the edges
            for (int j = 0; j < leafs.size(); j++) { // loops through the leafs
                if (sortedEdges.get(i).u.equals(leafs.get(j)) || sortedEdges.get(i).v.equals(leafs.get(j))) {
                    MST.removeEdge(sortedEdges.get(i));
                    breakOut = true;
                    break;
                }
                if (breakOut == true) {
                    break;
                }
            }
            if (breakOut == true) {
                break;
            }
        }

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

