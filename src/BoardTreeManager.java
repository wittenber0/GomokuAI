import javax.swing.tree.TreeNode;

public class BoardTreeManager {
    private int MAX_DEPTH = 2;
    boolean known;
    private TreeNode rootNode;

    /**
     * This function will calculate the minimax value of all of the moves on a given board with alpha-beta pruning.
     * @param board The game board to calculate the values for.
     * @param depth The depth of recursion. Initially called with 0.
     * @param max Whether this is a maximizing round or a minimizing round.
     * @return The value of the move.
     */
    public int minimax(GameBoard board, int depth, boolean max, int alpha, int beta) {
        if (board.getLongestChain().getLength() >= 5 || depth == MAX_DEPTH) {
            return board.totalHeuristic;
        }

        if (board.openMoves.isEmpty()) return 0;

        if (max) {
            int bestValue = Integer.MIN_VALUE;

            for (int i = 0; i < board.openMoves.size(); i++) {
                Move move = board.openMoves.get(i);
                int value = minimax(new GameBoard(board, move), depth+1, false, alpha, beta);
                bestValue =  returnMax(bestValue, value);
                alpha = returnMax(alpha, bestValue);
                if(beta <= alpha) {break;}
            }
            return bestValue;

        } else {
            int bestValue = Integer.MAX_VALUE;

            for (int i = 0; i < board.openMoves.size(); i++) {
                Move move = board.openMoves.get(i);
                int value = minimax(new GameBoard(board, move), depth+1, true, alpha, beta);
                bestValue =  returnMin(bestValue, value);
                beta = returnMin(beta, bestValue);
                if(beta <= alpha) {break;}
            }
            return bestValue;
        }
    }

    // Returns the max of two numbers
    private Integer returnMax(int i, int j) {
        return i > j ? i : j;
    }

    // Returns the min of two numbers
    private Integer returnMin(int i, int j) {
        return i < j ? i : j;
    }
}
