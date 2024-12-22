package com.tubes_pbo._d_platformer.tilemap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import com.tubes_pbo._d_platformer.main.GamePanel;
import com.tubes_pbo._d_platformer.handlers.LoggingHelper;

public class TileMap {
    private double x; // Current x position of the tile map
    private double y; // Current y position of the tile map

    // Bounds for scrolling limits
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    private double tween; // Smoothing factor for transitions

    // Map properties
    private int[][] map; // 2D array representing the tile map
    private int tileSize; // Size of each tile in pixels
    private int numRows; // Number of rows in the map
    private int numCols; // Number of columns in the map
    private int width; // Width of the entire map in pixels
    private int height; // Height of the entire map in pixels

    // TileSet properties
    private int numTilesAcross; // Number of tiles across in the tileset
    private Tile[][] tiles; // Array storing tiles based on their type

    // Rendering properties
    private int rowOffset; // Offset for the rows to render
    private int colOffset; // Offset for the columns to render
    private int numRowsToDraw; // Number of rows to render on the screen
    private int numColsToDraw; // Number of columns to render on the screen

    // Shake effect properties
    private boolean shaking; // Whether the tile map is shaking
    private int intensity; // Intensity of the shake effect

    // Constructor initializing the tile map with the given tile size
    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2; // Calculate rows to draw
        numColsToDraw = GamePanel.WIDTH / tileSize + 2; // Calculate columns to draw
        tween = 0.07; // Default tweening value
    }

    // Load tileset from an image file
    public void loadTiles(String s) {
        try {
            BufferedImage tileset = ImageIO.read(getClass().getResourceAsStream(s)); // Load tileset image
            numTilesAcross = tileset.getWidth() / tileSize; // Calculate number of tiles across
            tiles = new Tile[2][numTilesAcross]; // Initialize tiles array

            LoggingHelper.LOGGER.log(Level.SEVERE, "NumTileAcross" + numTilesAcross);

            // Extract tiles from the tileset
            for (int col = 0; col < numTilesAcross; col++) {
                BufferedImage subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subimage, Tile.NORMAL); // Normal tiles
                subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subimage, Tile.BLOCKED); // Blocked tiles
            }

        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    // Load the map layout from a file
    public void loadMap(String s) {
        try {
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            numCols = Integer.parseInt(br.readLine()); // Read number of columns
            numRows = Integer.parseInt(br.readLine()); // Read number of rows
            map = new int[numRows][numCols]; // Initialize map array
            width = numCols * tileSize; // Calculate map width
            height = numRows * tileSize; // Calculate map height

            // Set scrolling bounds
            xmin = GamePanel.WIDTH - width;
            xmax = 0;
            ymin = GamePanel.HEIGHT - height;
            ymax = 0;

            // Parse the map layout
            String delims = "\\s+";
            for (int row = 0; row < numRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for (int col = 0; col < numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public int getTileSize() {
        return tileSize;
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    // Get the type of a specific tile
    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc / numTilesAcross; // Row index in the tiles array
        int c = rc % numTilesAcross; // Column index in the tiles array
        return tiles[r][c].getType();
    }

    public boolean isShaking() {
        return shaking;
    }

    public void setTween(double d) {
        tween = d;
    }

    public void setShaking(boolean b, int i) {
        shaking = b;
        intensity = i;
    }

    public void setBounds(int i1, int i2, int i3, int i4) {
        xmin = GamePanel.WIDTH - i1;
        ymin = GamePanel.WIDTH - i2;
        xmax = i3;
        ymax = i4;
    }

    public void setPosition(double x, double y) {
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        fixBounds();

        colOffset = (int) -this.x / tileSize;
        rowOffset = (int) -this.y / tileSize;
    }

    // Adjust the map position to stay within bounds
    public void fixBounds() {
        if (x < xmin) x = xmin;
        if (y < ymin) y = ymin;
        if (x > xmax) x = xmax;
        if (y > ymax) y = ymax;
    }

    // Update the map position (e.g., for shaking effect)
    public void update() {
        if (shaking) {
            this.x += Math.random() * intensity - intensity / 2.0;
            this.y += Math.random() * intensity - intensity / 2.0;
        }
    }

    // Draw the visible part of the map
    public void draw(Graphics2D g) {
        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
            if (row >= numRows) break;

            for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
                if (col >= numCols) break;
                if (map[row][col] != 0) {
                    int rc = map[row][col];
                    int r = rc / numTilesAcross; // Row index in tiles array
                    int c = rc % numTilesAcross; // Column index in tiles array
                    g.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);
                }
            }
        }
    }
}
