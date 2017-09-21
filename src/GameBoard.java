/**
 * Created by Luke on 20-Sep-17.
 */
public class GameBoard {
    public enum TileType {black, white, empty}
    public TileType[][] board = new TileType[15][15];

    public GameBoard() {
        for(int i=0; i<15; i++) {
            for(int j=0; j<15; j++) {
                this.board[i][j] = TileType.empty;
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
}
