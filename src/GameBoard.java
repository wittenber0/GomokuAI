import java.util.LinkedList;

import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
 * Created by Luke on 20-Sep-17.
 */
public class GameBoard {
    public enum TileType {BLACK, WHITE, EMPTY}
    public TileType[][] board = new TileType[15][15];
    public LinkedList<Move> openMoves;
    TileType ourColor;
    TileType theirColor;
    LinkedList<Chain> chains;

    public GameBoard() {
        for(int i=0; i<15; i++) {
            for(int j=0; j<15; j++) {
                board[i][j] = TileType.EMPTY;
            }
        }
        openMoves = new LinkedList<>();
        chains = new LinkedList<>();
    }

    public GameBoard(GameBoard previous, Move newMove) {
        board = previous.getBoard();
        openMoves = previous.openMoves;
        chains = previous.chains;
        ourColor = previous.ourColor;
        theirColor = previous.theirColor;
	    
        respondToMove(newMove);
    }

    public void setOpenMoves (LinkedList<Move> openMoves) {
        this.openMoves = openMoves;
    }

    public TileType[][] getBoard() {
        return this.board;
    }

    public boolean saveMove(Move m, TileType color){
        if(m != null) {
            if (board[m.getColumn()][m.getRow()] == TileType.EMPTY) {
                board[m.getColumn()][m.getRow()] = color;
                return true;
            }
        }
        return false;
    }

    public Move getBestMove(){
        int max = 0, i = 0, j = 0;
        for (int k=0; k< board.length; k++){
            for(int l=0; l<board[k].length; l++) {
                int temp = calculateHeuristic(k, l);
                if (temp > max) {
                    max = temp;
                    i = k;
                    j = l;
                }
            }
        }

        return new Move(i, j, true);
    }

    public boolean checkWin(Move m, TileType color){
        if(countUp(m, color) + countDown(m, color) >= 4){
            return true;
        }

        if(countLeft(m, color) + countRight(m, color) >= 4){
            return true;
        }

        if(countUpLeft(m, color) + countDownRight(m, color) >= 4){
            return true;
        }

        if(countDownLeft(m, color) + countUpRight(m, color) >= 4){
            return true;
        }


        return false;
    }

    private int countLeft(Move m, TileType color){
        int count= 0;

        int i=1;
        while(m.getColumn()-i>=0){
            TileType  cellValue = board[m.getRow()][m.getColumn()-i];
            if(cellValue != color){
                return count;
            }
            i++;
        }

        return count;

    }

    private int countRight(Move m, TileType color){
        int count= 0;

        int i=1;
        while(m.getColumn()+i<board[0].length){
            TileType  cellValue = board[m.getRow()][m.getColumn()+i];
            if(cellValue != color){
                return count;
            }
            i++;
        }

        return count;
    }

    private int countUp(Move m, TileType color){
        int count= 0;

        int i=1;
        while(m.getRow()-i>=0){
            TileType  cellValue = board[m.getRow()-i][m.getColumn()];
            if(cellValue != color){
                return count;
            }
            i++;
        }

        return count;
    }

    private int countDown(Move m, TileType color){
        int count= 0;

        int i=1;
        while(m.getRow()+i<board.length){
            TileType  cellValue = board[m.getRow()+i][m.getColumn()];
            if(cellValue != color){
                return count;
            }
            i++;
        }

        return count;
    }

    private int countUpRight(Move m, TileType color){
        int count= 0;

        int i=1;
        while(m.getRow()-i>=0 && m.getColumn() +i < board[0].length){
            TileType  cellValue = board[m.getRow()-i][m.getColumn()+i];
            if(cellValue != color){
                return count;
            }
            i++;
        }

        return count;
    }

    private int countUpLeft(Move m, TileType color){
        int count= 0;

        int i=1;
        while(m.getRow()-i>=0 && m.getColumn() -i >= 0){
            TileType  cellValue = board[m.getRow()-i][m.getColumn()-i];
            if(cellValue != color){
                return count;
            }
            i++;
        }

        return count;
    }

    private int countDownLeft(Move m, TileType color){
        int count= 0;

        int i=1;
        while(m.getRow()+i < board.length && m.getColumn() -i >= 0){
            TileType  cellValue = board[m.getRow()+i][m.getColumn()-i];
            if(cellValue != color){
                return count;
            }
            i++;
        }

        return count;
    }

    private int countDownRight(Move m, TileType color){
        int count= 0;

        int i=1;
        while(m.getRow()+i < board.length && m.getColumn() +i < board[0].length){
            TileType  cellValue = board[m.getRow()+i][m.getColumn()+i];
            if(cellValue != color){
                return count;
            }
            i++;
        }

        return count;
    }
	

    public int calculateHeuristic(int i, int j) {
        int heuristic = 14 - abs(8-i) - abs(8-j);
	    
        

        return heuristic;
    }
    
    public void respondToMove(Move newMove) {
	    Move moveToAdd;
	    
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
	    	chain.respondToMove(newMove);
	    }
    }
}
