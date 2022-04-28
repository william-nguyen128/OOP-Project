package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Particle extends Entity {
    // Attributes
    private Color color;
    private int size;
    private int xd;
    private int yd;

    // Constructor
    public Particle(GamePanel gamePanel, Entity generator, Color color, int size,
            int speed, int maxLife, int xd, int yd) {
        super(gamePanel);

        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        int offset = gamePanel.getTileSize() / 2 - size / 2;
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    // Overridden methods
    @Override
    public void update() {
        life--;

        if (life < maxLife / 3)
            yd++;

        worldX += xd * speed;
        worldY += yd * speed;

        if (life == 0)
            alive = false;
    }

    @Override
    public void draw(Graphics2D g2d) {
        int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().SCREEN_X;
        int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().SCREEN_Y;

        g2d.setColor(color);
        g2d.fillRect(screenX, screenY, size, size);
    }
}