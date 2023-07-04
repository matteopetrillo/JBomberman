package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.GameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameMap {
    private MapTile[][] mapTiles;
    private int rows;
    private int columns;
    private String mapFilePath;

    public GameMap(String mapFilePath) {
        try {
            loadMapFromFile(mapFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MapTile getTileFromCoordinates(int x, int y) {
        int tileSize = GameUtils.Tile.SIZE.getValue();
        int row = (int) (y/tileSize);
        int column = (int) (x/tileSize);
        return mapTiles[column][row];
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
    public void setColumns(int columns) {
        this.columns = columns;
    }

    private void loadMapFromFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        // Conta il numero di righe e colonne nel file
        int numRows = 0;
        int numCols = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            numRows++;
            numCols = Math.max(numCols, line.length());
        }

        // Crea la matrice di tiles con le dimensioni lette
        this.mapTiles = new MapTile[numRows][numCols];

        // Resetta il lettore per leggere di nuovo dal file
        scanner = new Scanner(file);

        int row = 0;
        while (scanner.hasNextLine() && row < mapTiles.length) {
            String line = scanner.nextLine();

            for (int col = 0; col < line.length() && col < mapTiles[row].length; col++) {
                char symbol = line.charAt(col);
                if (symbol == '0')
                    mapTiles[row][col] = new MapTile(true);
                else if (symbol == '1')
                    mapTiles[row][col] = new MapTile(false);
            }

            row++;
        }

        scanner.close();
    }

    public MapTile[][] getMapTiles() {
        return mapTiles;
    }
}
