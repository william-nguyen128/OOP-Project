package entity.object;

import java.awt.Color;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public OBJ_Rock(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        castCost = 1;
        alive = false;

        getRockImage();
    }

    // Get images
    private void getRockImage() {
        up1 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        up2 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        left1 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        left2 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        down1 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        down2 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        right1 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        right2 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());

        up_left1 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        up_left2 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        down_left1 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        down_left2 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        down_right1 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        down_right2 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        up_right1 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        up_right2 = setup("/projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    // Overridden methods
    @Override
    public Color getParticleColor() {
        Color color = new Color(40, 50, 0);
        return color;
    }

    @Override
    public int getParticleSize() {
        int size = 10; // 10 pixels
        return size;
    }

    @Override
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    @Override
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}