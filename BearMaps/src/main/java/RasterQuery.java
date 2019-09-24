import java.util.Map;

public class RasterQuery {
    //given inputs
    double lrlon;
    double ullon;
    double width;
    double height;
    double ullat;
    double lrlat;

    //important ratios
    static final double LONDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / 256;
    static final double LATDPP = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / 256;

    //computed outputs
    private int depth;
    private String[][] grid;
    private double rasterullon;
    private double rasterullat;
    private double rasterlrlon;
    private double rasterlrlat;
    private boolean querysuccess = false;

    public RasterQuery(Map<String, Double> params) {
        lrlon = params.get("lrlon");
        ullon = params.get("ullon");
        width = params.get("w");
        height = params.get("h");
        ullat = params.get("ullat");
        lrlat = params.get("lrlat");

        determineDepth();
        determineGrid();
    }

    private void determineDepth() {
        double londpp = (lrlon - ullon) / width;

        double resolution = LONDPP;
        depth = 0;

        while (londpp < resolution) {
            resolution /= 2;
            depth++;
            if (depth == 7) {
                return;
            }
        }
    }

    private boolean validCoord(int coord) {
        return (coord >= 0) && (coord < Math.pow(2, depth));
    }

    private void determineGrid() {
        double lonPerTile = depthLonDPP() * MapServer.TILE_SIZE;
        double latPerTile = depthLatDPP() * MapServer.TILE_SIZE;

        int initialX = (int) ((ullon - MapServer.ROOT_ULLON) / lonPerTile);
        int initialY = (int) ((MapServer.ROOT_ULLAT - ullat) / latPerTile);

        double initialRightLon = (initialX + 1) * lonPerTile + MapServer.ROOT_ULLON;
        double initialBottomLat = MapServer.ROOT_ULLAT - (initialY + 1) * latPerTile;

        int numHorizTiles = numImages(initialRightLon, lrlon, lonPerTile);
        if (!validCoord(initialX + numHorizTiles)) {
            numHorizTiles = (int) Math.pow(2, depth) + initialX;
        }

        int numVertTiles = numImages(lrlat, initialBottomLat, latPerTile);
        if (!validCoord(initialY + numVertTiles)) {
            numVertTiles = (int) Math.pow(2, depth) - initialY;
        }

        querysuccess = validCoord(initialX)
                && validCoord(initialX + numHorizTiles - 1)
                && validCoord(initialY)
                && validCoord(initialY + numVertTiles - 1);

        rasterullon = MapServer.ROOT_ULLON + initialX * lonPerTile;
        rasterullat = MapServer.ROOT_ULLAT - initialY * latPerTile;


        rasterlrlon = rasterullon + numHorizTiles * lonPerTile;
        if (rasterlrlon > MapServer.ROOT_LRLON) {
            rasterlrlon = MapServer.ROOT_LRLON;
            //numHorizTiles--;
        }
        rasterlrlat = rasterullat - numVertTiles * latPerTile;
        /*
        if (rasterlrlat > MapServer.ROOT_LRLAT) {
            rasterlrlat = MapServer.ROOT_LRLAT;
            numVertTiles--;
        }
        */


        grid = new String[numVertTiles][numHorizTiles];

        for (int i = 0; i < numVertTiles; i++) {
            for (int j = 0; j < numHorizTiles; j++) {
                if (validCoord(initialX + j) && validCoord(initialY + i)) {
                    grid[i][j] = "d" + depth + "_x" + (initialX + j)
                            + "_y" + (initialY + i) + ".png";
                }
            }
        }

    }

    private int numImages(double lowerBound, double upperBound, double lonOrlatPerTile) {
        if (lowerBound < upperBound) {
            return (int) ((upperBound - lowerBound) / lonOrlatPerTile) + 2;
        } else {
            return 1;
        }
    }

    private double depthLonDPP() {
        return LONDPP / Math.pow(2, depth);
    }

    private double depthLatDPP() {
        return LATDPP / Math.pow(2, depth);
    }

    public int getDepth() {
        return depth;
    }

    public String[][] getGrid() {
        return grid;
    }

    public double getRasterullon() {
        return rasterullon;
    }

    public double getRasterullat() {
        return rasterullat;
    }

    public double getRasterlrlon() {
        return rasterlrlon;
    }

    public double getRasterlrlat() {
        return rasterlrlat;
    }

    public boolean getQuerysuccess() {
        return querysuccess;
    }
}
