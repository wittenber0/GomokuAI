import java.util.LinkedList;

/**
 * Created by Ryan on 9/20/2017.
 */
public class Main {

    public static void main(String[] args){
        GameBoard.TileType myColor;
        GameBoard.TileType opponentColor;
        LinkedList<Move> openMoves = new LinkedList<Move>();
        LinkedList<Chain> chains = new LinkedList<Chain>();

        GameBoard board = new GameBoard();
        board.setOpenMoves(openMoves);
        FileManager f = new FileManager("move_file", "gg_no_re.go");

        if(f.readMove() == null){
            myColor = GameBoard.TileType.WHITE;
            opponentColor = GameBoard.TileType.BLACK;
        }else{
            myColor = GameBoard.TileType.BLACK;
            opponentColor = GameBoard.TileType.WHITE;
        }
        board.ourColor = myColor;
        board.theirColor = opponentColor;

        boolean boardFull = false;
        boolean win = false;
        while(boardFull == false && win == false) {



            while (!f.checkForGo()) {}
            System.out.println("Found Go File");

            Move lastMove = f.readMove();
            win = board.checkWin(lastMove, opponentColor);

            board.saveMove(lastMove, opponentColor);


            Move myMove = board.getBestMove();
            boardFull = board.saveMove(myMove, myColor);

            if(boardFull == false) {
                f.writeMove(myMove);
            }
        }


    }


}
