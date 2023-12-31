package othello;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
public class OthelloHuristic extends JPanel {
    // Đánh giá tình trạng hiện tại của bảng dựa trên các đặc điểm nhất định
    public static int evaluateBoard(SpotBoard board, Player currentPlayer) {
        int score = 0;
     // Đặc điểm 1: Đếm số quân cờ cho mỗi người chơi
        int blackCount = 0;
        int whiteCount = 0;
        for (Spot spot : board) {
            if (!spot.isEmpty()) {
                if (spot.getSpotColor() == Color.BLACK) {
                    blackCount++;
                } else {
                    whiteCount++;
                }
            }
        }
     // Điều chỉnh điểm dựa trên sự chênh lệch trong số quân cờ
        score += (blackCount - whiteCount);

        // Đặc điểm 2: Kiểm tra sự kiểm soát của góc và mép
        score += controlCornersAndEdges(board, currentPlayer);

        // Đặc điểm 3: Sức di động - số nước đi hợp lệ cho người chơi hiện tại
        int mobility = countLegalMoves(board, currentPlayer);
        score += mobility;

        return score;
    }
    
 // Kiểm tra và điều chỉnh điểm dựa trên sự kiểm soát góc và mép
    private static int controlCornersAndEdges(SpotBoard board, Player currentPlayer) {
        int score = 0;
        // Góc rất quan trọng, nên ưu tiên chúng
        if (board.getSpotAt(0, 0).getSpotColor() == currentPlayer.getColor()) {
            score += 10;
        }
        if (board.getSpotAt(0, board.getSpotHeight() - 1).getSpotColor() == currentPlayer.getColor()) {
            score += 10;
        }
        if (board.getSpotAt(board.getSpotWidth() - 1, 0).getSpotColor() == currentPlayer.getColor()) {
            score += 10;
        }
        if (board.getSpotAt(board.getSpotWidth() - 1, board.getSpotHeight() - 1).getSpotColor() == currentPlayer.getColor()) {
            score += 10;
        }
     // Mép ít quan trọng hơn nhưng vẫn quan trọng
        for (int i = 1; i < board.getSpotWidth() - 1; i++) {
            if (board.getSpotAt(i, 0).getSpotColor() == currentPlayer.getColor()) {
                score += 3;
            }
            if (board.getSpotAt(i, board.getSpotHeight() - 1).getSpotColor() == currentPlayer.getColor()) {
                score += 3;
            }
        }
        for (int j = 1; j < board.getSpotHeight() - 1; j++) {
            if (board.getSpotAt(0, j).getSpotColor() == currentPlayer.getColor()) {
                score += 3;
            }
            if (board.getSpotAt(board.getSpotWidth() - 1, j).getSpotColor() == currentPlayer.getColor()) {
                score += 3;
            }
        }

        return score;
    }
 // Đếm số nước đi hợp lệ cho người chơi hiện tại
    private static int countLegalMoves(SpotBoard board, Player currentPlayer) {
        int legalMoves = 0;
        for (Spot spot : board) {
            if (spot.isLegalMove(currentPlayer)) {
                legalMoves++;
            }
        }
        return legalMoves;
    }
}
