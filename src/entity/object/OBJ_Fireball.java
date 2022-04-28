package entity.object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public OBJ_Fireball(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Fireball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        castCost = 1;
        alive = false;

        getFireballImage();
    }

    // Get images
    private void getFireballImage() {
        up1 = setup("/projectile/fireball_up_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        up2 = setup("/projectile/fireball_up_2", gamePanel.getTileSize(), gamePanel.getTileSize());
        left1 = setup("/projectile/fireball_left_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        left2 = setup("/projectile/fireball_left_2", gamePanel.getTileSize(), gamePanel.getTileSize());
        down1 = setup("/projectile/fireball_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        down2 = setup("/projectile/fireball_down_2", gamePanel.getTileSize(), gamePanel.getTileSize());
        right1 = setup("/projectile/fireball_right_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        right2 = setup("/projectile/fireball_right_2", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    // Overridden methods
    @Override
    public boolean haveResource(Entity caster) {
        boolean haveResource = false;
        if (caster.getMana() >= castCost)
            haveResource = true;
        return haveResource;
    }

    @Override
    public void subtractResource(Entity caster) {
        caster.setMana(caster.getMana() - castCost);
    }

    @Override
    public Color getParticleColor() {
        Color color = new Color(240, 50, 0);
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