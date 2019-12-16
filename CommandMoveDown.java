public class CommandMoveDown implements Command{
    GameBoard board;
    CommandMoveDown(GameBoard board){
        this.board = board;
    }
    @Override
    public void execute(){
        MovementMethods.dropOneLineDown(board);
    }
}
