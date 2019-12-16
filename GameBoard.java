import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private TetrominoShape[] board;

    public GameBoard(StartGame parent){
        setFocusable(true);
        statusbar = parent.getStatusBar();
        addKeyListener(new KeyboardHandler(this));
    }
    public GameBoard(){
        curPiece = Shape.getInstance();
        board = new TetrominoShape[boardWidth * boardHeight];
        curX = boardWidth / 2 + 1;
        curY = boardHeight - 3;
    }

    public TetrominoShape[] getBoard() {
        return board;
    }

    public int getCurX() {
        return curX;
    }

    public int getCurY() {
        return curY;
    }

    public Shape getCurPiece() {
        return curPiece;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setCurX(int curX) { this.curX = curX; }

    public void setCurY(int curY) { this.curY = curY; }

    public void setCurPiece(Shape curPiece) { this.curPiece = curPiece;}

    private int tetrominoSquareWidth() {
        return (int) getSize().getWidth() / boardWidth;
    }

    private int tetrominoSquareHeight() {
        return (int) getSize().getHeight() / boardHeight;
    }

    public TetrominoShape getBoardSquare(int x, int y){
        return board[(y * boardWidth) + x];
    }

    void start(){
        curPiece = Shape.getInstance();
        board = new TetrominoShape[boardWidth * boardHeight];

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

                            TetrominoShape shape = getBoardSquare(j, boardHeight - i - 1);

                            if (shape != TetrominoShape.NoShape) {

                    drawSquare(g, j * tetrominoSquareWidth(),
                            boardTop + i * tetrominoSquareHeight(), shape);
                }
            }
        }

        if (curPiece.getShape() != TetrominoShape.NoShape) {

            for (int i = 0; i < 4; i++) {

                int x = curX + curPiece.getX(i);
                int y = curY - curPiece.getY(i);

                drawSquare(g, x * tetrominoSquareWidth(),
                        boardTop + (boardHeight - y - 1) * tetrominoSquareHeight(),
                        curPiece.getShape());
            }
        }
    }


    public void clearBoard() {

        for (int i = 0; i < boardHeight * boardWidth; i++) {

            board[i] = TetrominoShape.NoShape;
        }
    }

    public void pieceDropped() {

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

    public void placeNewPiece() {

        TetrominoShapeGenerator.generateNewCoordinates(curPiece);
        curX = boardWidth / 2 + 1;
        curY = boardHeight - 1 + curPiece.minY();

        if (!MovementMethods.tryMoving(this, curPiece, curX, curY)) {

            curPiece.setNoShape();
            timer.stop();

            var msg = String.format("Game over. Score: %d.", score);
            statusbar.setText(msg);
        }
    }

    private void removeFullLines() {

        int numFullLines = 0;

        for (int i = boardHeight - 1; i >= 0; i--) {

            boolean lineIsFull = true;

            for (int j = 0; j < boardWidth; j++) {

                if (getBoardSquare(j, i) == TetrominoShape.NoShape) {

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
            curPiece.setNoShape();
        }
    }

    private void drawSquare(Graphics g, int x, int y, TetrominoShape shape) {

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
    private void update() {

        if (isFallingFinished) {

            isFallingFinished = false;
            placeNewPiece();
        } else {
            MovementMethods.dropOneLineDown(this);
        }
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


}
