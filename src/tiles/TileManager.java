package tiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    // Attributes
    private GamePanel gamePanel;
    private Tile[] tile;
    private int[][] mapTileNum;
    private boolean drawPath = true;

    // Constructor
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tile = new Tile[50];
        mapTileNum = new int[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];

        getTileImage();
        loadMap("/maps/worldV3.txt");
    }

    // Get images
    private void getTileImage() {
        // Placeholders => Avoid NullPointer exceptions when scanning the array
        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);

        // Real tiles
        // Grass
        setup(10, "grass00", false);
        setup(11, "grass01", false);

        // Water
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);

        // Road
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);

        // Earth
        setup(39, "earth", false);

        // Wall
        setup(40, "wall", true);

        // Tree
        setup(41, "tree", true);
    }

    private void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].setImage(
                    uTool.scaleImage(tile[index].getImage(), gamePanel.getTileSize(), gamePanel.getTileSize()));
            tile[index].setCollision(collision);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int col = 0;
            int row = 0;

            while (col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()) {
                String line = bufferedReader.readLine();

                while (col < gamePanel.getMaxWorldCol()) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gamePanel.getMaxWorldCol()) {
                    col = 0;
                    row++;
                }
            }

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.getMaxWorldCol() && worldRow < gamePanel.getMaxWorldRow()) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gamePanel.getTileSize();
            int worldY = worldRow * gamePanel.getTileSize();
            int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().SCREEN_X;
            int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().SCREEN_Y;

            // Stop moving the camera at the edge
            if (gamePanel.getPlayer().SCREEN_X > gamePanel.getPlayer().getWorldX())
                screenX = worldX;
            if (gamePanel.getPlayer().SCREEN_Y > gamePanel.getPlayer().getWorldY())
                screenY = worldY;
            int rightOffset = gamePanel.getScreenWidth() - gamePanel.getPlayer().SCREEN_X;
            if (rightOffset > gamePanel.getWorldWidth() - gamePanel.getPlayer().getWorldX())
                screenX = gamePanel.getScreenWidth() - (gamePanel.getWorldWidth() - worldX);
            int bottomOffset = gamePanel.getScreenHeight() - gamePanel.getPlayer().SCREEN_Y;
            if (bottomOffset > gamePanel.getWorldHeight() - gamePanel.getPlayer().getWorldY())
                screenY = gamePanel.getScreenHeight() - (gamePanel.getWorldHeight() - worldY);

            if (worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().SCREEN_X &&
                    worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX()
                            + gamePanel.getPlayer().SCREEN_X
                    &&
                    worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY()
                            - gamePanel.getPlayer().SCREEN_Y
                    &&
                    worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY()
                            + gamePanel.getPlayer().SCREEN_Y)
                g2d.drawImage(tile[tileNum].getImage(), screenX, screenY, null);
            else if (gamePanel.getPlayer().SCREEN_X > gamePanel.getPlayer().getWorldX() ||
                    gamePanel.getPlayer().SCREEN_Y > gamePanel.getPlayer().getWorldY() ||
                    rightOffset > gamePanel.getWorldWidth() - gamePanel.getPlayer().getWorldX() ||
                    bottomOffset > gamePanel.getWorldHeight() - gamePanel.getPlayer().getWorldY())
                g2d.drawImage(tile[tileNum].getImage(), screenX, screenY, null);

            worldCol++;

            if (worldCol == gamePanel.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }
        }

        if (drawPath == true) {
            g2d.setColor(new Color(255, 0, 0, 70));

            for (int i = 0; i < gamePanel.getPathFinder().getPathList().size(); i++) {
                int worldX = gamePanel.getPathFinder().getPathList().get(i).getCol() * gamePanel.getTileSize();
                int worldY = gamePanel.getPathFinder().getPathList().get(i).getRow() * gamePanel.getTileSize();
                int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().SCREEN_X;
                int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().SCREEN_Y;

                g2d.fillRect(screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize());
            }
        }
    }

    // Getters
    public Tile[] getTiles() {
        return tile;
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }
}