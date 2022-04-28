package tiles;

import java.awt.image.BufferedImage;

public class Tile {
    // Attributes
    private BufferedImage image;
    private boolean collision = false;

    // Getters & Setters
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
}