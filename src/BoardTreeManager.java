import java.util.ArrayList;
import java.util.LinkedList;

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

    public int minimax(int depth, boolean ourTurn) {
        if (currentBoard.getLongestChain().getLength() >= 5)
            return currentBoard.getLongestChain().isOurChain() ? currentBoard.ourTotalHueristic : currentBoard.theirTotalHueristic;

        if (currentBoard.openMoves.isEmpty()) return 0;

        ArrayList<Integer> scores = new ArrayList<>();

        for (int i = 0; i < currentBoard.openMoves.size(); ++i) {
            Move move = currentBoard.openMoves.get(i);

            if (ourTurn) {
                currentBoard.getBestMove();
                int currentScore = minimax(depth + 1, false);
                scores.add(currentScore);

                if (depth == 0)
                    possibleMoves.add(new GameBoard(currentBoard, move));

            } else if (!ourTurn) {
                currentBoard.saveMove(move);
                scores.add(minimax(depth + 1, true));
            }
            currentBoard.board[move.getRow()][move.getColumn()] = GameBoard.TileType.EMPTY;
        }
        return ourTurn ? returnMax(scores) : returnMin(scores);
    }

    private Integer returnMax(ArrayList<Integer> scores) {
        Integer max = 0;
        for(Integer score : scores) {
            max = score > max ? score : max;
        }

        return max;
    }

    private Integer returnMin(ArrayList<Integer> scores) {
        Integer min = 0;
        for(Integer score : scores) {
            min = score < min ? score : min;
        }

        return min;
    }
}
