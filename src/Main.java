import java.util.LinkedList;

/**
 * Created by Ryan on 9/20/2017.
 */
public class Main {

    public static void main(String[] args){
        GameBoard.TileType myColor;
        GameBoard.TileType oponentColor;
        LinkedList<Move> openMoves = new LinkedList<Move>();

        GameBoard board = new GameBoard();
        board.setOpenMoves(openMoves);
        FileManager f = new FileManager("move_file_test", "test.go");

        if(f.readMove() == null){
            myColor = GameBoard.TileType.WHITE;
            oponentColor = GameBoard.TileType.BLACK;
        }else{
            myColor = GameBoard.TileType.BLACK;
            oponentColor = GameBoard.TileType.WHITE;
        }
        board.ourColor = myColor;
        board.theirColor = oponentColor;

        boolean boardFull = false;
        boolean win = false;
        while(boardFull == false && win == false) {



            while (!f.checkForGo()) {}
            System.out.println("Found Go File");

            Move lastMove = f.readMove();
            win = board.checkWin(lastMove, oponentColor);

            board.saveMove(lastMove, oponentColor);
            board.setLastEnemyMove(lastMove);


            Move myMove = board.getBestMove();
            boardFull = board.saveMove(myMove, myColor);

            if(boardFull == false) {
                f.writeMove(myMove);
            }
        }


    }


}
