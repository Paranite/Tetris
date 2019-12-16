public class CommandMoveLeft implements Command{
    GameBoard board;
    CommandMoveLeft(GameBoard board){
        this.board = board;
    }
    @Override
    public void execute(){
        MovementMethods.tryMoving(board, board.getCurPiece(), board.getCurX() - 1, board.getCurY());
    }
}
