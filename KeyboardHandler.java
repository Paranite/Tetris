import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyboardHandler extends KeyAdapter {
    GameBoard gameBoard;
    Map<Integer, Command> commandMap;

    KeyboardHandler(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        this.commandMap = getCommandMap();
    }
    public Map<Integer, Command> getCommandMap() {
        Map<Integer, Command> commandMap = new HashMap<>();
        Command moveDown = new CommandMoveDown(gameBoard);
        commandMap.put(KeyEvent.VK_DOWN, moveDown);

        Command moveLeft = new CommandMoveLeft(gameBoard);
        commandMap.put(KeyEvent.VK_LEFT, moveLeft);

        Command moveRight = new CommandMoveRight(gameBoard);
        commandMap.put(KeyEvent.VK_RIGHT, moveRight);

        Command dropDown = new CommandDropDown(gameBoard);
        commandMap.put(KeyEvent.VK_SPACE, dropDown);

        Command rotate = new CommandRotate(gameBoard);
        commandMap.put(KeyEvent.VK_UP, rotate);

        return commandMap;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameBoard.getCurPiece().getShape() == TetrominoShape.NoShape) {
            return;
        }
        int keycode = e.getKeyCode();
        var command = commandMap.get(keycode);
        if(command != null) {
            command.execute();
        }
    }
}
