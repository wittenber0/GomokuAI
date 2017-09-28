enum ChainDirection {
	VERTICAL, HORIZONTAL, DIAG_UP, DIAG_DOWN
}

public class Chain {

    private int length;
    private Move moves[];
    private Chain neighbors[];
    private Chain intersections[];
    private ChainDirection direction;
    private int numOpenTerminals;
    private Move lowTerminal;
    private Move highTerminal;
    private boolean isOurChain;
    

    public Chain(boolean isOurChain) {
        this.length = 0;
        this.moves = new Move[5];
        this.neighbors = new Chain[10];
        this.intersections = new Chain[10];
        this.numOpenTerminals = 0;
        this.isOurChain = isOurChain;
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
    
    public Move getLowTerminal() {return lowTerminal;}
    
    public Move getHighTerminal() {return highTerminal;}
    
    public void respondToMove(Move newMove) {
	    if (newMove.isOurMove == isOurChain) {
    		if (lowTerminal.equals(newMove)) {
    			moves[length] = newMove;
    			length += 1;
    			switch(direction) {
				    case HORIZONTAL:
				    	if (newMove.getColumn() <= 0) {
				    		numOpenTerminals -= 1;
				    		lowTerminal = null;
					    } else {
				    		// Access tile type from parent board to determine if to end chain (other player's tile is adjacent),
						    // extend chain (our tile is adjacent), or set new terminal (empty tile is adjacent)
					    }
					    break;
				    case VERTICAL:
					    break;
				    case DIAG_UP:
					    break;
				    case DIAG_DOWN:
				    default:
					    break;
			    }
		    } else if (highTerminal.equals(newMove)) {
    			length += 1;
    			switch(direction) {
				    case HORIZONTAL:
					    break;
				    case VERTICAL:
					    break;
				    case DIAG_UP:
					    break;
				    case DIAG_DOWN:
				    default:
					    break;
			    }
		    }
	    } else {
		    if (lowTerminal.equals(newMove)) {
			    lowTerminal = null;
			    numOpenTerminals -= 1;
		    } else if (highTerminal.equals(newMove)) {
			    highTerminal = null;
			    numOpenTerminals -= 1;
		    }
	    }
    }

    public int getNumOpenTerminals() {
        return numOpenTerminals;
    }
}
