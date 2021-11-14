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
        MST = graphInBudget(MST, max);
        MST = MST.connGraph();

        // Step 3: Calculate the number of stops between each pair of airports
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 0; i < MST.V(); i++) {
            MST = BFS(MST,i, G, nodes);
        }

        nodes = removeDuplicates(nodes);

        for (int i = 0; i < nodes.size(); i++) {
            System.out.println(nodes.get(i).toString());
        }


        nodes = sortNodes(nodes);




        System.out.println(MST.toString());
        return null;
    }

    private static Graph trial(Graph MST, int source, Graph G) {
        boolean[] isVisited = new boolean[MST.V()];
        ArrayList<Integer> queue = new ArrayList<>();

        isVisited[source] = true;
        queue.add(source);


        int i = source;
        int temp = 0;


        while(!queue.isEmpty()) {
            int adjCount = 0;
            ArrayList<Integer> adjacents = MST.adj(MST.getCode(i)); //gets adjacent nodes to source

            i = queue.remove(0);
            //isVisited[i] = true;
            System.out.println(i);


            while (adjacents.size() != adjCount) {
                temp = adjacents.get(adjCount);
                adjCount++;
                //temp = adjacents.remove(0);
                if (!isVisited[temp]) {
                    isVisited[temp] = true;
                    queue.add(temp);
                }
            }
            i = temp;
        }


        return MST;
    }


    private static Graph BFS(Graph MST, int source, Graph G, ArrayList<Node> nodes) {
        boolean[] isVisited = new boolean[MST.V()];
        ArrayList<Integer> queue = new ArrayList<>();
        int[] height = new int[MST.V()];

        isVisited[source] = true;
        queue.add(source);

        height[source] = -1;

        int i = source;
        int temp = 0;


        while(!queue.isEmpty()) {
            int adjCount = 0;
            i = queue.remove(0);
            ArrayList<Integer> adjacents = MST.adj(MST.getCode(i)); //gets adjacent nodes to source


            //isVisited[i] = true;
            //System.out.println(i);

            if (source != i) {
                for (int j = 0; j < adjacents.size(); j++) {
                    if (isVisited[adjacents.get(j)]) {
                        height[i] = height[adjacents.get(j)] + 1;
                    }
                }
            }

            if (height[i] > 0) {
                String sourceString = MST.getCode(source);
                String destString = MST.getCode(i);

                Edge edge = G.getEdge(G.index(sourceString), G.index(destString));

                Node newNode = new Node(G.index(sourceString), G.index(destString), edge.w, height[i]);
                nodes.add(newNode);
            }

            while (adjacents.size() != adjCount) {
                temp = adjacents.get(adjCount);
                adjCount++;
                //temp = adjacents.remove(0);
                if (!isVisited[temp]) {
                    isVisited[temp] = true;
                    queue.add(temp);
                }
            }
           //i = temp;
        }

        return MST;
    }

    public static ArrayList<Node> removeDuplicates(ArrayList<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            Node original = nodes.get(i);
            Node duplicate = new Node(original.v, original.u, original.getDistance(), original.getLayovers());

            for (int j = 0; j < nodes.size(); j++) {
                if (nodes.get(j).compareTo(duplicate) == 0 && i != j) {
                    nodes.remove(j);
                }
            } // end inner for
        } // end outer for
        return nodes;
    }

    private static ArrayList<Node> sortNodes(ArrayList<Node> nodes) {
        ArrayList<Node> sortedNodes = new ArrayList<>();

//        for (int i = 0; i < nodes.size(); i++) {
//            if (i == 0) {
//                sortedNodes.add(nodes.get(i));
//            }
//
//            for (int j = 0; j < sortedNodes.size(); j++) {
//                if (nodes.get(i).compareTo(sortedNodes.get(j)) > 0) {
//                    sortedNodes.add(j - 1, nodes.get(i));
//                }
//            }
//        }


        return nodes;
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
        run(GT, 30);
    }
}

