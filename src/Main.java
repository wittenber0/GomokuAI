import java.io.File;
import java.util.Scanner;

/**
 * Created by Ryan on 9/20/2017.
 */
public class Main {

    public static void main(String[] args){

        

        GameBoard board = new GameBoard();
        FileManager f = new FileManager("move_file_test", "test.go");


        //f.writeMove(new Move(3, 5));


        while(!f.checkForGo()){

        }

        Move lastMove = f.readMove();
        board.saveMove(lastMove, GameBoard.TileType.BLACK);
        Move myMove = board.getBestMove();
        board.saveMove(myMove, GameBoard.TileType.WHITE);
        f.writeMove(myMove);
        System.out.println("Found Go File");


    }


}
