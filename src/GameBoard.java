import java.util.LinkedList;

import static java.lang.Math.abs;

/**
 * Created by Luke on 20-Sep-17.
 */
public class GameBoard {
    public TileType ourColor;
    public TileType theirColor;
    public int totalHeuritstic;
    public enum TileType {BLACK, WHITE, EMPTY}
    public TileType[][] board = new TileType[15][15];
    public LinkedList<Move> openMoves = new LinkedList<Move>();
    public LinkedList<Chain> chains = new LinkedList<Chain>();
    public Move lastEnemyMove;

    public GameBoard() {
        for(int i=0; i<15; i++) {
            for(int j=0; j<15; j++) {
                this.board[i][j] = TileType.EMPTY;
            }
        }
    }

    public GameBoard(GameBoard previous, int i, int j, TileType type) {
        board = previous.getBoard();
        board[i][j] = type;
    }

    public void setOpenMoves (LinkedList<Move> openMoves) {
        this.openMoves = openMoves;
    }

    public void setLastEnemyMove(Move lastEnemyMove) {
        this.lastEnemyMove = lastEnemyMove;
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
        for (int k = 0; k < board.length; k++){
            for(int l = 0; l < board[k].length; l++) {
                int temp = calculateHeuristic(k, l);
                if (temp > max) {
                    max = temp;
                    i = k;
                    j = l;
                }
            }
        }

        return new Move(i, j);
    }

    public boolean checkWin(Move m, TileType color){
        return false;
    }

    public LinkedList<Move> getPossibleMoves(){

        LinkedList<Move> allMoves = new LinkedList<Move>();

        for (int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){

                if(board[i][j] != TileType.EMPTY){
                    Move m = new Move(i, j);
                    allMoves.add(m);
                }
            }
        }

        return allMoves;
    }

    public void getOpenMoves() {
        if(this.openMoves.contains(lastEnemyMove)) {
            this.openMoves.remove(lastEnemyMove);
        }
        int k = lastEnemyMove.getColumn();
        int l = lastEnemyMove.getRow();

        for(int i=-1; i<2; i++) {
            for(int j=-1; j<2; j++) {
                if((k < 15 && k > 1) && (l < 15 && l > 1) && !openMoves.contains(new Move(k+i,l+j)))
                    openMoves.add(new Move(k+i,l+j));
            }
        }
    }

    public int calculateHeuristic(int i, int j) {
        int heuristic = 0;
        heuristic = 14- abs(8-i) - abs(8-j);

        return heuristic;
    }
}

