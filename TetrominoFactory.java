import java.util.NoSuchElementException;
public class TetrominoFactory {

    public static int[][] getShapeCoordinates(TetrominoShape shape) {
        switch (shape) {
            case NoShape:
                return getNoShapeCoordinates();
            case ZShape:
                return getZShapeCoordinates();
            case SShape:
                return getSShapeCoordinates();
            case IShape:
                return getIShapeCoordinates();
            case TShape:
                return getTShapeCoordinates();
            case OShape:
                return getOShapeCoordinates();
            case LShape:
                return getLShapeCoordinates();
            case JShape:
                return getJShapeCoordinates();
            default:
                throw new NoSuchElementException(getErrorMessage(shape.toString()));
        }
    }
    private static String getErrorMessage(String shape) {
        return "No such shape: " + shape;
    }

    private static int[][] getNoShapeCoordinates(){

        return new int[][]{{0,0}, {0,0}, {0,0}, {0,0}};
    }

    private static int[][] getZShapeCoordinates(){
        return new int[][]{{0, -1}, {0, 0}, {-1, 0}, {-1, 1}};
    }

    private static int[][] getSShapeCoordinates(){

        return new int[][]{{0, -1}, {0, 0}, {1, 0}, {1, 1}};
    }

    private static int[][] getIShapeCoordinates(){

        return new int[][]{{0, -1}, {0, 0}, {0, 1}, {0, 2}};
    }

    private static int[][] getTShapeCoordinates(){

        return new int[][]{{-1, 0}, {0, 0}, {1, 0}, {0, 1}};
    }

    private static int[][] getOShapeCoordinates(){

        return new int[][]{{0, 0}, {1, 0}, {0, 1}, {1, 1}};
    }
    private static int[][] getLShapeCoordinates(){

        return new int[][]{{-1, -1}, {0, -1}, {0, 0}, {0, 1}};
    }

    private static int[][] getJShapeCoordinates(){

        return new int[][]{{1, -1}, {0, -1}, {0, 0}, {0, 1}};
    }

}
