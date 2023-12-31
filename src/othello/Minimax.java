package othello;

import java.util.List;

public class Minimax {

    private static final int MAX_DEPTH = 5; // Độ sâu tối đa của cây Minimax

    public static Spot findBestMove(SpotBoard board, Spot.Player currentPlayer) {
        int bestScore = Integer.MIN_VALUE;
        Spot bestMove = null;

        List<Spot> legalMoves = getLegalMoves(board, currentPlayer);

        for (Spot move : legalMoves) {
            SpotBoard newBoard = board.copyBoard();
            newBoard.placePiece(move, currentPlayer);

            int score = minimax(newBoard, currentPlayer, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private static int minimax(SpotBoard board, Spot.Player currentPlayer, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (depth == 0 || isTerminalNode(board)) {
            // Đánh giá điểm cho trạng thái hiện tại của bàn cờ
            return evaluate(board, currentPlayer);
        }

        List<Spot> legalMoves = getLegalMoves(board, currentPlayer);

        if (isMaximizingPlayer) {
            int maxScore = Integer.MIN_VALUE;
            for (Spot move : legalMoves) {
                SpotBoard newBoard = board.copyBoard();
                newBoard.placePiece(move, currentPlayer);

                int score = minimax(newBoard, currentPlayer, depth - 1, alpha, beta, false);
                maxScore = Math.max(maxScore, score);
                alpha = Math.max(alpha, score);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (Spot move : legalMoves) {
                SpotBoard newBoard = board.copyBoard();
                newBoard.placePiece(move, getOpponentPlayer(currentPlayer));

                int score = minimax(newBoard, currentPlayer, depth - 1, alpha, beta, true);
                minScore = Math.min(minScore, score);
                beta = Math.min(beta, score);
                if (beta <= alpha) {
                    break;
                }
            }
            return minScore;
        }
    }

    private static boolean isTerminalNode(SpotBoard board) {
        // Kiểm tra xem trò chơi đã kết thúc hay chưa
        // Ví dụ: hết ô trống, không có nước đi hợp lệ...
        // Trả về true nếu trạng thái là trạng thái kết thúc, ngược lại trả về false
        return false;
    }

    private static int evaluate(SpotBoard board, Spot.Player currentPlayer) {
        // Đánh giá điểm cho trạng thái hiện tại của bàn cờ
        // Có thể sử dụng các heuristics để tính điểm
        // Trả về giá trị điểm của trạng thái
        return 0;
    }

    private static List<Spot> getLegalMoves(SpotBoard board, Spot.Player currentPlayer) {
        // Lấy danh sách các nước đi hợp lệ cho người chơi hiện tại
        // Trả về danh sách các ô trống có thể đi
        return null;
    }

    private static Spot.Player getOpponentPlayer(Spot.Player currentPlayer) {
        // Lấy người chơi đối thủ của người chơi hiện tại
        return (currentPlayer == Spot.Player.BLACK) ? Spot.Player.WHITE : Spot.Player.BLACK;
    }
}