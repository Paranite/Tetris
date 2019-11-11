import java.util.Random;
public class Shape {

    protected enum Tetromino {
        NoShape, ZShape, SShape, IShape, TShape, OShape, LShape, JShape;
    }

    private Tetromino pieceShape;
    private int[][] coords;

    public Shape() {
        coords = new int[4][2];
        setShape(Tetromino.NoShape);
    }

    void setShape(Tetromino shape) {
        int [][][] coordTable = new int[][][]{
                {{0,0}, {0,0}, {0,0}, {0,0}},
                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
        };

        System.arraycopy(coordTable[shape.ordinal()], 0, coords, 0, 4);
        pieceShape = shape;
    }

    private void setX(int index, int x) {
        coords[index][0] = x;
    }
    private void setY(int index, int y) {
        coords[index][1] = y;
    }
    int getX(int index) {
        return coords[index][0];
    }
    int getY(int index){
        return coords[index][1];
    }
    Tetromino getShape(){
        return pieceShape;
    }
    void setRandomShape(){
        var rand = new Random();
        int x = rand.nextInt(7) +1;
        Tetromino[] values = Tetromino.values();
        setShape(values[x]);
    }

    int minX(){
        int m = coords[0][0];
        for (int i = 0; i < 4; i++){
            m=Math.min(m, coords[i][0]);
        }
        return m;
    }

    int minY() {

        int m = coords[0][1];

        for (int i = 0; i < 4; i++) {

            m = Math.min(m, coords[i][1]);
        }

        return m;
    }

    Shape rotateRight() {

        if (pieceShape == Tetromino.OShape) {

            return this;
        }

        var result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; i++) {

            result.setX(i, -getY(i));
            result.setY(i, getX(i));
        }

        return result;
    }
}
