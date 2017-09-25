import java.util.LinkedList;

/**
 * Created by Ryan on 9/25/2017.
 */
public class BoardTreeManager {
    GameBoard headBoard;
    LinkedList<Move> possibleMoves = new LinkedList<Move>();


    public BoardTreeManager(GameBoard b){
        headBoard = b;
        possibleMoves = b.getPossibleMoves();
    }


}
