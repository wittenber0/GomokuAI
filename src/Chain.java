public class Chain {

    int length;
    Move moves[];
    Chain neighbors[];
    Chain intersections[];
    int openTerminals;

    public Chain() {
        this.length = 0;
        this.moves = new Move[5];
        this.neighbors = new Chain[10];
        this.intersections = new Chain[10];
        this.openTerminals = 0;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Move[] getMoves() {
        return moves;
    }

    public void setMoves(Move[] moves) {
        this.moves = moves;
    }

    public Chain[] getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Chain[] neighbors) {
        this.neighbors = neighbors;
    }

    public Chain[] getIntersections() {
        return intersections;
    }

    public void setIntersections(Chain[] intersections) {
        this.intersections = intersections;
    }

    public int getOpenTerminals() {
        return openTerminals;
    }

    public void setOpenTerminals(int openTerminals) {
        this.openTerminals = openTerminals;
    }
}
