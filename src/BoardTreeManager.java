import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ryan on 9/25/2017.
 */
public class BoardTreeManager {
    GameBoard headBoard;
    GameBoard currentBoard;
    LinkedList<GameBoard> possibleMoves = new LinkedList<GameBoard>();
    public BoardTreeManager(GameBoard b){
        headBoard = b;
        currentBoard = b;
    }

    // http://www.codebytes.in/2014/08/minimax-algorithm-tic-tac-toe-ai-in.html
    public void callMinimax(int depth, int turn){
        minimax(depth, turn);
    }

    public int minimax(int depth, int turn) {
        if (haveWeWon()) return +1;
        if (haveTheyWon()) return -1;

        if (openMoves.isEmpty()) return 0;

        List<Integer> scores = new ArrayList<>();

        for (int i = 0; i < openMoves.size(); ++i) {
            Move move = openMoves.get(i);

            if (turn == 1) { //X's turn select the highest from below minimax() call
                getBestMove();
                int currentScore = minimax(depth + 1, 2);
                scores.add(currentScore);

                if (depth == 0)
                    possibleMoves.add(new GameBoard(currentBoard, move.getColumn(), move.getRow(), currentBoard.ourColor));

            } else if (turn == 2) {//O's turn select the lowest from below minimax() call
                placeAMove(move, 2);
                scores.add(minimax(depth + 1, 1));
            }
            board[move.getRow()][move.getColumn()] = GameBoard.TileType.EMPTY; //Reset this move
        }
        return turn == 1 ? returnMax(scores) : returnMin(scores);
    }


}
