public class CommandDropDown implements Command{
    GameBoard board;
    CommandDropDown(GameBoard board){
        this.board = board;
    }
    @Override
    public void execute(){
        MovementMethods.dropDown(board);
    }
}
