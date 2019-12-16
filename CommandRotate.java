public class CommandRotate implements Command{
    GameBoard board;
    CommandRotate(GameBoard board){
        this.board = board;
    }
    @Override
    public void execute(){
        MovementMethods.tryMoving(board, board.getCurPiece().rotateRight(), board.getCurX(), board.getCurY());
    }
}
