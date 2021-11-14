import java.util.ArrayList;

public class Node {
    public final int u;
    public final int v;
    private int distance;
    private int layovers;
    private int height;

    public Node(int u, int v, int distance, int layovers) {
        this.u = u;
        this.v = v;
        this.distance = distance;
        this.layovers = layovers;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public int compareTo (Node node) {
        if (this == node) {
            return 0;
        }
        if (this.u == node.u && this.v == node.v || this.u == node.v && this.v == node.u) {
            return 0;
        }
        if (this.layovers == node.layovers) {
            return node.getDistance() - this.distance;
        }
        return this.layovers - node.layovers;
    }

    @Override
    public String toString() {
        return "Node{" +
                "u=" + u +
                ", v=" + v +
                ", distance=" + distance +
                ", layovers=" + layovers +
                '}';
    }

}
