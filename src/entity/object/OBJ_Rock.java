package entity.object;

import java.awt.Color;
import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {

    // Attribute
    private GamePanel gamePanel;

    public OBJ_Rock(GamePanel gamePanel) {
        super(gamePanel);
        // TODO Auto-generated constructor stub

        name = "Rock";
        speed = 10;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        castCost = 1;
        alive = false;
        getRockImage();
    }

    // Get images
    private void getRockImage() {
        int width = gamePanel.getTileSize();
        int height = gamePanel.getTileSize();

        for (int i = 0; i < 2; i++) {
            leftSprites[i] = setup("/projectile/rock_down_" + (i + 1), width, height);
            rightSprites[i] = setup("/projectile/rock_down_" + (i + 1), width, height);
        }
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
