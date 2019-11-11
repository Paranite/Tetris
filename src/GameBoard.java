
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel{
    private final int boardWidth = 10,
                      boardHeight = 22;
    private final int periodInterval = 300;

    private Timer timer;
    private boolean isFallingFinished = false;
    private int score = 0;
    private int curX = 0,
                curY = 0;
    private JLabel statusbar;
    private Shape curPiece;
    private Shape.Tetromino[] board;

    public GameBoard(StartGame parent){
        setFocusable(true);
        statusbar = parent.getStatusBar();
        addKeyListener(new TAdapter());
    }

    private int tetrominoSquareWidth() {
        return (int) getSize().getWidth() / boardWidth;
    }

    private int tetrominoSquareHeight() {
        return (int) getSize().getHeight() / boardHeight;
    }

    private Shape.Tetromino getBoardSquare(int x, int y){
        return board[(y * boardWidth) + x];
    }

    void start(){
        curPiece = new Shape();
        board = new Shape.Tetromino[boardWidth * boardHeight];

        clearBoard();
        placeNewPiece();

        timer = new Timer(periodInterval, new GameCycle());
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

                    var size = getSize();
                    int boardTop = (int) size.getHeight() - boardHeight * tetrominoSquareHeight();

                    for (int i = 0; i < boardHeight; i++) {

                        for (int j = 0; j < boardWidth; j++) {

                            Shape.Tetromino shape = getBoardSquare(j, boardHeight - i - 1);

                            if (shape != Shape.Tetromino.NoShape) {

                    drawSquare(g, j * tetrominoSquareWidth(),
                            boardTop + i * tetrominoSquareHeight(), shape);
                }
            }
        }

        if (curPiece.getShape() != Shape.Tetromino.NoShape) {

            for (int i = 0; i < 4; i++) {

                int x = curX + curPiece.getX(i);
                int y = curY - curPiece.getY(i);

                drawSquare(g, x * tetrominoSquareWidth(),
                        boardTop + (boardHeight - y - 1) * tetrominoSquareHeight(),
                        curPiece.getShape());
            }
        }
    }

    private void dropDown() {

        int newY = curY;

        while (newY > 0) {

            if (!tryMoving(curPiece, curX, newY - 1)) {
                break;
            }

            newY--;
        }
        pieceDropped();
    }

    private void dropOneLineDown() {

        if (!tryMoving(curPiece, curX, curY - 1)) {
            pieceDropped();
        }
    }

    private void clearBoard() {

        for (int i = 0; i < boardHeight * boardWidth; i++) {

            board[i] = Shape.Tetromino.NoShape;
        }
    }

    private void pieceDropped() {

        for (int i = 0; i < 4; i++) {

            int x = curX + curPiece.getX(i);
            int y = curY - curPiece.getY(i);
            board[(y * boardWidth) + x] = curPiece.getShape();
        }

        removeFullLines();

        if (!isFallingFinished) {
            placeNewPiece();
        }
    }

    private void placeNewPiece() {

        curPiece.setRandomShape();
        curX = boardWidth / 2 + 1;
        curY = boardHeight - 1 + curPiece.minY();

        if (!tryMoving(curPiece, curX, curY)) {

            curPiece.setShape(Shape.Tetromino.NoShape);
            timer.stop();

            var msg = String.format("Game over. Score: %d.", score);
            statusbar.setText(msg);
        }
    }

    private boolean tryMoving(Shape newPiece, int newX, int newY) {

        for (int i = 0; i < 4; i++) {

            int x = newX + newPiece.getX(i);
            int y = newY - newPiece.getY(i);

            if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) {

                return false;
            }

            if (getBoardSquare(x, y) != Shape.Tetromino.NoShape) {

                return false;
            }
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;

        repaint();

        return true;
    }

    private void removeFullLines() {

        int numFullLines = 0;

        for (int i = boardHeight - 1; i >= 0; i--) {

            boolean lineIsFull = true;

            for (int j = 0; j < boardWidth; j++) {

                if (getBoardSquare(j, i) == Shape.Tetromino.NoShape) {

                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {

                numFullLines++;

                for (int k = i; k < boardHeight - 1; k++) {
                    for (int j = 0; j < boardWidth; j++) {
                        board[(k * boardWidth) + j] = getBoardSquare(j, k + 1);
                    }
                }
            }
        }

        if (numFullLines > 0) {

            score = score + (numFullLines * numFullLines * 5);

            statusbar.setText(String.valueOf(score));
            isFallingFinished = true;
            curPiece.setShape(Shape.Tetromino.NoShape);
        }
    }

    private void drawSquare(Graphics g, int x, int y, Shape.Tetromino shape) {

        Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102),
                new Color(102, 204, 102), new Color(102, 102, 204),
                new Color(204, 204, 102), new Color(204, 102, 204),
                new Color(102, 204, 204), new Color(218, 170, 0)
        };

        var color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, tetrominoSquareWidth() - 2, tetrominoSquareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + tetrominoSquareHeight() - 1, x, y);
        g.drawLine(x, y, x + tetrominoSquareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + tetrominoSquareHeight() - 1,
                x + tetrominoSquareWidth() - 1, y + tetrominoSquareHeight() - 1);
        g.drawLine(x + tetrominoSquareWidth() - 1, y + tetrominoSquareHeight() - 1,
                x + tetrominoSquareWidth() - 1, y + 1);
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private void doGameCycle() {
        update();
        repaint();
    }

    private void update() {

        if (isFallingFinished) {

            isFallingFinished = false;
            placeNewPiece();
        } else {
            dropOneLineDown();
        }
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (curPiece.getShape() == Shape.Tetromino.NoShape) {

                return;
            }

            int keycode = e.getKeyCode();

            if(keycode == KeyEvent.VK_LEFT) {
                tryMoving(curPiece, curX - 1, curY);
            }
            if(keycode == KeyEvent.VK_RIGHT) {
                tryMoving(curPiece, curX + 1, curY);
            }
            if(keycode == KeyEvent.VK_DOWN){
                dropOneLineDown();
            }
            if(keycode == KeyEvent.VK_UP){
                tryMoving(curPiece.rotateRight(), curX, curY);
            }
            if(keycode == KeyEvent.VK_SPACE){
                dropDown();
            }
        }
    }
}
