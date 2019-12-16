public class MovementMethods {
    public static boolean tryMoving(GameBoard board, Shape newPiece, int newX, int newY) {

        for (int i = 0; i < 4; i++) {

            int x = newX + newPiece.getX(i);
            int y = newY - newPiece.getY(i);

            if (x < 0 || x >= board.getBoardWidth()|| y < 0 || y >= board.getBoardHeight()) {
                return false;
            }
            if (board.getBoardSquare(x, y) != TetrominoShape.NoShape) {

                return false;
            }
        }

        board.setCurPiece(newPiece);
        board.setCurX(newX);
        board.setCurY(newY);

        board.repaint();

        return true;
    }
    public static void dropOneLineDown(GameBoard board) {

        if (!tryMoving(board, board.getCurPiece(), board.getCurX(), board.getCurY() - 1)) {
            board.pieceDropped();
        }
    }
    public static void dropDown(GameBoard board) {

        int newY = board.getCurY();

        while (newY > 0) {

            if (!tryMoving(board, board.getCurPiece(), board.getCurX(), newY - 1)) {
                break;
            }

            newY--;
        }
        board.pieceDropped();
    }
}
