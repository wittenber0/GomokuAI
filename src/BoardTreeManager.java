import javax.swing.tree.TreeNode;

public class BoardTreeManager {
    private int MAX_DEPTH = 2;
    boolean known;
    private TreeNode rootNode;

    public int minimax(GameBoard board, int depth, boolean max) {
        if (board.getLongestChain().getLength() >= 5 || depth == MAX_DEPTH) {
            return board.totalHeuristic;
        }

        if (board.openMoves.isEmpty()) return 0;

        if (max) {
            int bestValue = Integer.MIN_VALUE;

            for (int i = 0; i < board.openMoves.size(); i++) {
                Move move = board.openMoves.get(i);
                bestValue =  returnMax(bestValue, minimax(new GameBoard(board, move), depth+1, false));
            }
            return bestValue;

        } else {
            int bestValue = Integer.MAX_VALUE;

            for (int i = 0; i < board.openMoves.size(); i++) {
                Move move = board.openMoves.get(i);
                bestValue =  returnMin(bestValue, minimax(new GameBoard(board, move), depth+1, true));
            }
            return bestValue;
        }
    }

    private Integer returnMax(int i, int j) {
        return i > j ? i : j;
    }

    private Integer returnMin(int i, int j) {
        return i < j ? i : j;
    }
}
