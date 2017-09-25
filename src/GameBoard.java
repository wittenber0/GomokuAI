import java.util.LinkedList;

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
            if (board[m.getColumn()][m.getRow()] == TileType.EMPTY) {
                board[m.getColumn()][m.getRow()] = color;
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

    public LinkedList<Move> getPossibleMoves(){

        LinkedList<Move> allMoves = new LinkedList<Move>();

        for (int i=0; i< board.length; i++){
            for(int j=0; j< board[0].length; j++){

                if(board[i][j] == TileType.EMPTY){
                    Move m = new Move(i, j);
                    allMoves.add(m);
                }
            }
        }

        return allMoves;

    }
}
