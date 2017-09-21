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
}
