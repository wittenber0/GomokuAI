import java.util.HashMap;
import java.util.LinkedList;

import static java.lang.Math.abs;

public class GameBoard {
    public enum TileType {BLACK, WHITE, EMPTY}
    public TileType[][] board = new TileType[15][15];
    private BoardTreeManager manager = new BoardTreeManager();
    public LinkedList<Move> openMoves;
    TileType ourColor;
    TileType theirColor;
    LinkedList<Chain> chains;
    int totalHeuristic;

    /**
     * This will initialize the gameboard to all blank tiles.
     */
    public GameBoard() {
        for(int i=0; i<15; i++) {
            for(int j=0; j<15; j++) {
                board[i][j] = TileType.EMPTY;
            }
        }
        openMoves = new LinkedList<>();
        chains = new LinkedList<>();
    }

    /**
     * This will take a gameboard and a new move and process it.
     */
    public GameBoard(GameBoard previous, Move newMove) {
        board = previous.getBoard();
        openMoves = previous.openMoves;
        chains = previous.chains;
        ourColor = previous.ourColor;
        theirColor = previous.theirColor;
        totalHeuristic = calculateBoardHeuristic();

        respondToMove(newMove);
    }

    /**
     * This will return the board.
     * @return board The game board.
     */
    public TileType[][] getBoard() {
        return this.board;
    }

    /**
     * This function will check that the move is valid and save it to the game board.
     * @param m The move to be made.
     * @return The boolean value if it saved or not.
     */
    public boolean saveMove(Move m){
        if(m != null) {
            if (board[m.getColumn()][m.getRow()] == TileType.EMPTY) {
                board[m.getColumn()][m.getRow()] = m.isOurMove ? ourColor : theirColor;
                return true;
            }
        }
        return false;
    }

    /**
     * This function will return the best move calculated by the Minimax Algorithm and Alpha Beta Pruning
     * Each considerable move on this game board will be calculated and stored in the hashmap. The move
     * with the highest value will be chosen.
     * @return The best move.
     */
    public Move getBestMove(){
        HashMap<Integer, Move> moves = new HashMap<>();
        int max = 0;
        for (Move move : openMoves) {
            int key = manager.minimax(this, 0,true, Integer.MIN_VALUE, Integer.MAX_VALUE);
            moves.put(key, move);
            max = max > key ? max : key;
        }
        return moves.get(max);
    }

    /**
     * This function will calculate the heuristic value based on a number of characteristics.
     * The most basic is the inverse Manhattan distance so that the game will start off reasonably towards the center.
     * As the game gets more complex, the moves will be more and more based off of chains of related tiles.
     * Each chain a tile is a part of will add to the heuristic value of that tile. Since the game board will never
     * try to move on behalf of the other player, we add the other player's likely heuristic value to ours --
     * --if our enemy wants to go there, so do we---- this play style allows our AI to play both offensively and
     * defensively.
     * @param moveAtThisTile The move to be evaluated
     * @return The integer sum of the heuristic.
     */
    public int calculateMoveHeuristic(Move moveAtThisTile) {
    	int heuristic  = 14 - abs(8-moveAtThisTile.getRow()) - abs(8-moveAtThisTile.getColumn());
    	Chain chainIncludingThisMove;
	    
        for (Chain chain: chains) {
        	if (chain.getLowTerminal().equals(moveAtThisTile) || chain.getHighTerminal().equals(moveAtThisTile)) {
        		moveAtThisTile.isOurMove = chain.isOurChain();
        		chainIncludingThisMove = chain;
        		chainIncludingThisMove.respondToMove(moveAtThisTile);
        		heuristic +=  (5 * chainIncludingThisMove.getLength() * (2 - chainIncludingThisMove.getNumOpenTerminals()));
	        }
	    }

        return heuristic;
    }

    /**
     * This function will total the heuristic value of the game board and serve as both our utility and evaluation functions in
     * almost a piecewise manor. If the game is won, the heuristic will spit out the utility function. Because this game is
     * adversarial, once the goal state is reached, whether or not we found an optimal solution is irrelevant besides the case
     * where we might want to use the game as a training set for machine learning.
     * @return
     */
    public int calculateBoardHeuristic() {
    	int heuristic = 0;
    	
    	if (getLongestChain().getLength() >= 5) {
    		if (getLongestChain().isOurChain()) {
    			heuristic = 10000; // Utility value if we win
		    } else {
    			heuristic = -10000; // Utility value if we lose
		    }
	    } else {
    		for (Move move : openMoves) { // If no open moves (draw) value is 0
    			heuristic += calculateMoveHeuristic(move); // Add heuristic for each open move
		    }
	    }
	    return heuristic;
    }

    /**
     * This function will return the longest chain on the current game board.
     * @return The longest chain.
     */
    public Chain getLongestChain() {
        Chain bigchain = chains.get(1);
        for(Chain chain : chains) {
            if(chain.getLength() > bigchain.getLength())
                bigchain = chain;
        }

        return bigchain;
    }

    /**
     * This function will respond to a given move by not only updating the current open or relevant moves, but also
     * updating the chains. The way that we pre-prune the tree is by discounting values more than 1 away from any other
     * piece. Through hours of play... I mean errrr study, we did not find a single instance of it being advantageous to
     * move outside of this boundry.
     * @param newMove The new move.
     */
    public void respondToMove(Move newMove) {
	    Move moveToAdd;
	    TileType newTerminalType;
	    boolean newMoveToProcess;
	    Move moveToProcess;
	    
	    int lowerRowLimit = Math.max(0, newMove.getRow() - 1);
	    int upperRowLimit = Math.min(14, newMove.getRow() + 1);
	    int lowerColumnLimit = Math.max(14, newMove.getColumn() - 1);
	    int upperColumnLimit = Math.min(14, newMove.getColumn() + 1);
    	
	    if (newMove.isOurMove) {
		    board[newMove.getRow()][newMove.getColumn()] = ourColor;
	    } else {
		    board[newMove.getRow()][newMove.getColumn()] = theirColor;
	    }
	    
	    openMoves.remove(newMove);
	    
	    for (int i = lowerRowLimit; i <= upperRowLimit; i++) {
	        for (int j = lowerColumnLimit; j <= upperColumnLimit; j++) {
	        	if (board[i][j] == TileType.EMPTY) {
	        		moveToAdd = new Move(i, j, true);
	        		
	        		if (!openMoves.contains(moveToAdd)) {
	        			openMoves.add(moveToAdd);
			        }
		        }
	        }
	    }
	
	    for (Chain chain: chains) {
	    	newMoveToProcess = true;
	    	moveToProcess = newMove;
	    	
	    	while (newMoveToProcess) {
			    moveToProcess = chain.respondToMove(moveToProcess);
			    newMoveToProcess = (moveToProcess != null);
			    
			    if (newMoveToProcess) {
				    newTerminalType = board[moveToProcess.getRow()][moveToProcess.getColumn()];
				    newMoveToProcess = (newTerminalType != TileType.EMPTY);
				    
				    if (newMoveToProcess) {
				    	moveToProcess.isOurMove = (newTerminalType == ourColor);
				    }
			    }
		    }
	    }
	    
	    addChains(newMove);
    }

    /**
     * The calling function to check for chains in each direction.
     * @param newMove The new move on the game board.
     */
    public void addChains(Move newMove) {
    	addChainsInDirection(newMove, ChainDirection.HORIZONTAL);
    	addChainsInDirection(newMove, ChainDirection.VERTICAL);
    	addChainsInDirection(newMove, ChainDirection.DIAG_UP);
    	addChainsInDirection(newMove, ChainDirection.DIAG_DOWN);
    }

    /**
     * This function will search the game board for new chains that might have been created on the last move. The reason for the switch statement
     * is so that we can break out of searches more easily to save time.
     * @param newMove The new move on the game board.
     * @param direction The direction to search for the new chain in.
     */
    public void addChainsInDirection(Move newMove, ChainDirection direction) {
	    int column = newMove.getColumn();
	    int row = newMove.getRow();
	
	    boolean atLeftEdge = column <= 0;
	    boolean atRightEdge = column >= 14;
	    boolean atTopEdge = row <= 0;
	    boolean atBottomEdge = row >= 14;
	
	    boolean canCreateChain = false;
	    LinkedList<Move> movesToAdd = new LinkedList<>();
	    movesToAdd.add(newMove);
	    Move lowerTerminal = null;
	    Move upperTerminal = null;
	    TileType testValue;
	
	    switch(direction) {
		    case HORIZONTAL:
		    if (!atLeftEdge) {
			    testValue = board[row][column - 1];
			    if (testValue == TileType.EMPTY) {
				    lowerTerminal = new Move(row, column - 1, newMove.isOurMove);
			    } else if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
				    canCreateChain = true;
				    movesToAdd.add(lowerTerminal);
				    if (column >= 2) {
					    testValue = board[row][column - 2];
					    if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
						    return;
					    } else if (testValue == TileType.EMPTY) {
					    	lowerTerminal = new Move(row, column - 2, newMove.isOurMove);
					    } else {
					    	lowerTerminal = null;
					    }
				    } else {
				    	lowerTerminal = null;
				    }
			    }
		    }
		    if (!atRightEdge) {
		    	testValue = board[row][column + 1];
			    if (testValue == TileType.EMPTY) {
			    	if (canCreateChain) {
					    upperTerminal = new Move(row, column + 1, newMove.isOurMove);
				    } else {
			    		return;
				    }
			    } else if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
				    canCreateChain = true;
				    movesToAdd.add(new Move(row, column + 1, newMove.isOurMove));
				    if (column <= 12) {
					    testValue = board[row][column + 2];
					    if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
						    return;
					    } else if (testValue == TileType.EMPTY) {
						    upperTerminal = new Move(row, column + 2, newMove.isOurMove);
					    } else {
					    	upperTerminal = null;
					    }
				    } else {
				    	upperTerminal = null;
				    }
			    }
		    }
		    break;
		    case VERTICAL:
			    if (!atTopEdge) {
				    testValue = board[row - 1][column];
				    if (testValue == TileType.EMPTY) {
					    lowerTerminal = new Move(row - 1, column, newMove.isOurMove);
				    } else if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
					    canCreateChain = true;
					    movesToAdd.add(new Move(row - 1, column, newMove.isOurMove));
					    if (row >= 2) {
						    testValue = board[row - 2][column];
						    if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
							    return;
						    } else if (testValue == TileType.EMPTY) {
							    lowerTerminal = new Move(row - 2, column, newMove.isOurMove);
						    } else {
							    lowerTerminal = null;
						    }
					    } else {
						    lowerTerminal = null;
					    }
				    }
			    }
			    if (!atBottomEdge) {
				    testValue = board[row + 1][column];
				    if (testValue == TileType.EMPTY) {
					    if (canCreateChain) {
						    upperTerminal = new Move(row + 1, column, newMove.isOurMove);
					    } else {
						    return;
					    }
				    } else if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
					    canCreateChain = true;
					    movesToAdd.add(new Move(row + 1, column, newMove.isOurMove));
					    if (row <= 12) {
						    testValue = board[row + 2][column];
						    if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
							    return;
						    } else if (testValue == TileType.EMPTY) {
							    upperTerminal = new Move(row + 2, column, newMove.isOurMove);
						    } else {
							    upperTerminal = null;
						    }
					    } else {
						    upperTerminal = null;
					    }
				    }
			    }
		    	break;
		    case DIAG_UP:
			    if (!atLeftEdge && !atBottomEdge) {
				    testValue = board[row + 1][column - 1];
				    if (testValue == TileType.EMPTY) {
					    lowerTerminal = new Move(row + 1, column - 1, newMove.isOurMove);
				    } else if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
					    canCreateChain = true;
					    movesToAdd.add(new Move(row + 1, column - 1, newMove.isOurMove));
					    if (row >= 12 && column <= 2) {
						    testValue = board[row + 2][column - 2];
						    if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
							    return;
						    } else if (testValue == TileType.EMPTY) {
							    lowerTerminal = new Move(row + 2, column - 2, newMove.isOurMove);
						    } else {
							    lowerTerminal = null;
						    }
					    } else {
						    lowerTerminal = null;
					    }
				    }
			    }
			    if (!atRightEdge && !atTopEdge) {
				    testValue = board[row - 1][column + 1];
				    if (testValue == TileType.EMPTY) {
					    if (canCreateChain) {
						    upperTerminal = new Move(row - 1, column + 1, newMove.isOurMove);
					    } else {
						    return;
					    }
				    } else if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
					    canCreateChain = true;
					    movesToAdd.add(new Move(row - 1, column + 1, newMove.isOurMove));
					    if (row >= 2 && column <= 12) {
						    testValue = board[row - 2][column + 2];
						    if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
							    return;
						    } else if (testValue == TileType.EMPTY) {
							    upperTerminal = new Move(row - 2, column + 2, newMove.isOurMove);
						    } else {
							    upperTerminal = null;
						    }
					    } else {
						    upperTerminal = null;
					    }
				    }
			    }
		    	break;
		    case DIAG_DOWN:
			    if (!atLeftEdge && !atTopEdge) {
				    testValue = board[row - 1][column - 1];
				    if (testValue == TileType.EMPTY) {
					    lowerTerminal = new Move(row - 1, column - 1, newMove.isOurMove);
				    } else if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
					    canCreateChain = true;
					    movesToAdd.add(new Move(row - 1, column - 1, newMove.isOurMove));
					    if (row >= 2 && column >= 2) {
						    testValue = board[row - 2][column - 2];
						    if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
							    return;
						    } else if (testValue == TileType.EMPTY) {
							    lowerTerminal = new Move(row - 2, column - 2, newMove.isOurMove);
						    } else {
							    lowerTerminal = null;
						    }
					    } else {
						    lowerTerminal = null;
					    }
				    }
			    }
			    if (!atRightEdge && !atBottomEdge) {
				    testValue = board[row + 1][column + 1];
				    if (testValue == TileType.EMPTY) {
					    if (canCreateChain) {
						    upperTerminal = new Move(row + 1, column + 1, newMove.isOurMove);
					    } else {
						    return;
					    }
				    } else if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
					    canCreateChain = true;
					    movesToAdd.add(new Move(row + 1, column + 1, newMove.isOurMove));
					    if (row <= 12 && column <= 12) {
						    testValue = board[row + 2][column + 2];
						    if (testValue == (newMove.isOurMove ? ourColor : theirColor)) {
							    return;
						    } else if (testValue == TileType.EMPTY) {
							    upperTerminal = new Move(row + 2, column + 2, newMove.isOurMove);
						    } else {
							    upperTerminal = null;
						    }
					    } else {
						    upperTerminal = null;
					    }
				    }
			    }
		    	break;
	    }
	
	    if (canCreateChain) {
		    int openTerminals = (lowerTerminal == null) ? 0 : 1;
		    openTerminals = (upperTerminal == null) ? openTerminals : openTerminals + 1;
		    chains.add(new Chain(movesToAdd, lowerTerminal, upperTerminal, openTerminals, newMove.isOurMove));
	    }
	    return;
    }
}
