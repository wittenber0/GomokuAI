/**
 * Created by Luke on 20-Sep-17.
 */
public class GameBoard {
    public double heuritstic;
    public enum TileType {BLACK, WHITE, EMPTY}
    public TileType[][] board = new TileType[15][15];

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

    public TileType[][] getBoard() {
        return this.board;
    }

    public boolean saveMove(Move m, TileType color){
        if(m != null) {
            if (board[m.column][m.row] == TileType.EMPTY) {
                board[m.column][m.row] = color;
                return true;
            }
        }
        return false;
    }

    public Move getBestMove(){

        for (int i=0; i< board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if(board[i][j] == TileType.EMPTY){
                    return new Move(i, j);
                }
            }
        }

        return null;
    }
}
