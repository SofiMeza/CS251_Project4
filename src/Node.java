import java.util.ArrayList;

public class Node {
    public final String u;
    public final String v;
    private int distance;
    private int layovers;

    public Node(String u, String v, int distance, int layovers) {
        this.u = u;
        this.v = v;
        this.distance = distance;
        this.layovers = layovers;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getLayovers() {
        return layovers;
    }

    public void setLayovers(int layovers) {
        this.layovers = layovers;
    }

    public compare (Node one, Node two) {

    }



    public ArrayList<Node> sort(ArrayList<Node> nodes) {

        return null;
    }

}
