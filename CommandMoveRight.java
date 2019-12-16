public class CommandMoveRight implements Command {
    GameBoard board;
    CommandMoveRight(GameBoard board){
        this.board = board;
    }
    @Override
    public void execute(){
        MovementMethods.tryMoving(board, board.getCurPiece(), board.getCurX() + 1, board.getCurY());
    }
}
