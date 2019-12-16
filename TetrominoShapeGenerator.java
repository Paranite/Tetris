import java.util.Random;

public class TetrominoShapeGenerator {
    private static TetrominoShape getRandomShape(){
        var rand = new Random();
        int x = rand.nextInt(7) +1;
        TetrominoShape[] values = TetrominoShape.values();
        return values[x];
    }
    static void generateNewCoordinates(Shape tetromino) {
        TetrominoShape shape = getRandomShape();
        tetromino.setShape(shape);
        tetromino.setCoords(TetrominoFactory.getShapeCoordinates(shape));
    }
}
