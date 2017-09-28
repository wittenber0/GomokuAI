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
    
    public Move respondToMove(Move newMove) {
	    
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
				    		lowTerminal = new Move(newMove.getRow(), newMove.getColumn() - 1, newMove.isOurMove);
				    		return lowTerminal;
					    }
					    break;
				    case VERTICAL:
					    if (newMove.getRow() <= 0) {
						    numOpenTerminals -= 1;
						    lowTerminal = null;
					    } else {
						    lowTerminal = new Move(newMove.getRow() - 1, newMove.getColumn(), newMove.isOurMove);
						    return lowTerminal;
					    }
					    break;
				    case DIAG_UP:
					    if (newMove.getRow() >= 14 || newMove.getColumn() <= 0) {
						    numOpenTerminals -= 1;
						    lowTerminal = null;
					    } else {
						    lowTerminal = new Move(newMove.getRow() + 1, newMove.getColumn() - 1, newMove.isOurMove);
						    return lowTerminal;
					    }
					    break;
				    case DIAG_DOWN:
				    default:
					    if (newMove.getRow() <= 0 || newMove.getColumn() <= 0) {
						    numOpenTerminals -= 1;
						    lowTerminal = null;
					    } else {
						    lowTerminal = new Move(newMove.getRow() - 1, newMove.getColumn() - 1, newMove.isOurMove);
						    return lowTerminal;
					    }
					    break;
			    }
		    } else if (highTerminal.equals(newMove)) {
    			length += 1;
    			switch(direction) {
				    case HORIZONTAL:
					    if (newMove.getColumn() >= 14) {
						    numOpenTerminals -= 1;
						    highTerminal = null;
					    } else {
						    highTerminal = new Move(newMove.getRow(), newMove.getColumn() + 1, newMove.isOurMove);
						    return highTerminal;
					    }
					    break;
				    case VERTICAL:
					    if (newMove.getRow() >= 14) {
						    numOpenTerminals -= 1;
						    highTerminal = null;
					    } else {
						    highTerminal = new Move(newMove.getRow() + 1, newMove.getColumn(), newMove.isOurMove);
						    return highTerminal;
					    }
					    break;
				    case DIAG_UP:
					    if (newMove.getRow() <= 0 || newMove.getColumn() >= 14) {
						    numOpenTerminals -= 1;
						    highTerminal = null;
					    } else {
						    highTerminal = new Move(newMove.getRow() - 1, newMove.getColumn() + 1, newMove.isOurMove);
						    return highTerminal;
					    }
					    break;
				    case DIAG_DOWN:
				    default:
					    if (newMove.getRow() >= 14 || newMove.getColumn() >= 14) {
						    numOpenTerminals -= 1;
						    highTerminal = null;
					    } else {
						    highTerminal = new Move(newMove.getRow() + 1, newMove.getColumn() + 1, newMove.isOurMove);
						    return highTerminal;
					    }
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
	    return null;
    }

    public int getNumOpenTerminals() {
        return numOpenTerminals;
    }
}
