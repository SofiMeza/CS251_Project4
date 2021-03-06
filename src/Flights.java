import java.util.ArrayList;

public class Flights {
    public final int source;
    public final int destination;
    public final int sourceRegion;
    public final int destRegion;
    public final int distance;
    private ArrayList<Edge> path;

    public Flights(int source, int destination, int sourceRegion, int destRegion, int distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.sourceRegion = sourceRegion;
        this.destRegion = destRegion;
    }

    public ArrayList<Edge> gePath () {
        return this.path;
    }

    public void setPath(ArrayList<Edge> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Flights{" +
                "source=" + source +
                ", destination=" + destination +
                ", sourceRegion=" + sourceRegion +
                ", destRegion=" + destRegion +
                ", distance=" + distance +
                '}';
    }
}
