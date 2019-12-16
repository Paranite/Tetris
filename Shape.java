public class Shape {

    private TetrominoShape pieceShape;
    private int[][] coords;
    private static Shape single_instance = null;

    private Shape() {
        coords = new int[4][2];
        setNoShape();
    }
    public static Shape getInstance(){
        if(single_instance == null) {
            single_instance = new Shape();
        }
        return single_instance;
    }
    void setNoShape(){
        pieceShape = TetrominoShape.NoShape;
        coords = TetrominoFactory.getShapeCoordinates(pieceShape);
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
    TetrominoShape getShape(){
        return pieceShape;
    }
    void setShape(TetrominoShape newShape){
        pieceShape = newShape;
    }
    void setCoords(int[][] newCoords){
        coords = newCoords;
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

        if (pieceShape == TetrominoShape.OShape) {

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
