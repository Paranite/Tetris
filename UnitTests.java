import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class UnitTests {
    @Test
    public void checkBlockCollision() {
        GameBoard board = new GameBoard();
        board.clearBoard();
        for(int i = 0; i < board.getBoardWidth(); i++){
            board.getBoard()[20 * board.getBoardWidth() + i] = TetrominoShape.OShape;
        }
        TetrominoShapeGenerator.generateNewCoordinates(board.getCurPiece());
        boolean result = MovementMethods.tryMoving(board, board.getCurPiece(), board.getCurX(), board.getCurY());
        assertFalse(result);
    }
    @Test
    public void checkWallCollision(){
        GameBoard board = new GameBoard();
        board.clearBoard();
        TetrominoShapeGenerator.generateNewCoordinates(board.getCurPiece());
        int blocksTillCorner = (board.getBoardWidth()-board.getCurX());
        boolean result = MovementMethods.tryMoving(board, board.getCurPiece(), blocksTillCorner + 10, board.getCurY());
        assertFalse(result);
    }
    @Test
    public void checkMoveLeft(){
        GameBoard board = new GameBoard();
        board.clearBoard();
        TetrominoShapeGenerator.generateNewCoordinates(board.getCurPiece());
        int x = board.getCurX();
        int y = board.getCurY();
        boolean result = MovementMethods.tryMoving(board, board.getCurPiece(), board.getCurX()-1, board.getCurY());
        assertTrue(result);
        assertEquals(x - 1 , board.getCurX());
        assertEquals(y, board.getCurY());
    }
    @Test
    public void checkMoveRight(){
        GameBoard board = new GameBoard();
        board.clearBoard();
        TetrominoShapeGenerator.generateNewCoordinates(board.getCurPiece());
        int x = board.getCurX();
        int y = board.getCurY();
        boolean result = MovementMethods.tryMoving(board, board.getCurPiece(), board.getCurX() + 1, board.getCurY());
        assertTrue(result);
        assertEquals(x + 1, board.getCurX());
        assertEquals(y, board.getCurY());
    }
    @Test
    public void checkMoveOneDown(){
        GameBoard board = new GameBoard();
        board.clearBoard();
        TetrominoShapeGenerator.generateNewCoordinates(board.getCurPiece());
        int x = board.getCurX();
        int y = board.getCurY();
        MovementMethods.dropOneLineDown(board);
        assertEquals(x, board.getCurX());
        assertEquals(y-1, board.getCurY());
    }
}
