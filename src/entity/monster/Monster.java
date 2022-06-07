package entity.monster;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class Monster extends Entity {
    // Attributes
    private GamePanel gamePanel;
    private int dyingCounter = 0;
    private int hpBarCounter = 0;
    private boolean hpBarOn = false;

    // Constructor
    public Monster(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = TYPE.Monster;
    }

    @Override
    public void update() {
        setAction();
        checkCollision();

        // If collision = false => Monster can move
        if (collisionOn == false)
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "left":
                    facingRight = false;
                    worldX -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "right":
                    facingRight = true;
                    worldX += speed;
                    break;
                case "up-left":
                    facingRight = false;
                    worldX -= speed;
                    worldY -= speed;
                    break;
                case "down-left":
                    facingRight = false;
                    worldX -= speed;
                    worldY += speed;
                    break;
                case "down-right":
                    facingRight = true;
                    worldX += speed;
                    worldY += speed;
                    break;
                case "up-right":
                    facingRight = true;
                    worldX += speed;
                    worldY -= speed;
                    break;
            }

        // Monster's sprite change
        spriteCounter++;
        if (spriteCounter > 15) {
            if (spriteNum == 1)
                spriteNum = 2;
            else if (spriteNum == 2)
                spriteNum = 1;
            spriteCounter = 0;
        }

        // Invincibility frame
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 30) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // Path finding => ALWAYS GO AFTER PLAYER
        onPath = true;
    }

    public void draw(Graphics2D g2d) {
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().SCREEN_X;
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().SCREEN_Y;

        if (worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().SCREEN_X &&
                worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().SCREEN_X &&
                worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().SCREEN_Y &&
                worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().SCREEN_Y) {
            BufferedImage image = null;

            if (facingRight == false) {
                if (spriteNum == 1)
                    image = leftSprites[0];
                if (spriteNum == 2)
                    image = leftSprites[1];
            } else {
                if (spriteNum == 1)
                    image = rightSprites[0];
                if (spriteNum == 2)
                    image = rightSprites[1];
            }

            // Monster HP bar
            if (hpBarOn == true) {
                double oneScale = (double) gamePanel.getTileSize() / maxLife;
                double hpBarValue = oneScale * life;

                g2d.setColor(new Color(35, 35, 35));
                g2d.fillRect(screenX - 1, screenY - 16, gamePanel.getTileSize() + 2, 12);

                g2d.setColor(new Color(255, 0, 30));
                g2d.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2d, 0.4f);
            }
            if (dying == true)
                dyingAnimation(g2d);

            g2d.drawImage(image, screenX, screenY, null);

            changeAlpha(g2d, 1f);
        }
    }

    public void setAction() {
        if (alive == true && dying == false)
            if (onPath == true) {
                int goalCol = (gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSolidArea().x)
                        / gamePanel.getTileSize();
                int goalRow = (gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSolidArea().y)
                        / gamePanel.getTileSize();
                searchPath(goalCol, goalRow);
            }
    }

    public void damageReaction() {
        // if (gamePanel.getPlayer().isFacingRight() == false)
        //     worldX -= 10;
        // else
        //     worldX += 10;
    }

    public void checkDrop() {
    }

    // Internal (Private) method
    private void dyingAnimation(Graphics2D g2d) {
        dyingCounter++;
        int i = 5;

        if (dyingCounter <= i)
            changeAlpha(g2d, 0f);
        if (dyingCounter > i && dyingCounter <= i * 2)
            changeAlpha(g2d, 1f);
        if (dyingCounter > i * 2 && dyingCounter <= i * 3)
            changeAlpha(g2d, 0f);
        if (dyingCounter > i * 3 && dyingCounter <= i * 4)
            changeAlpha(g2d, 1f);
        if (dyingCounter > i * 4 && dyingCounter <= i * 5)
            changeAlpha(g2d, 0f);
        if (dyingCounter > i * 5 && dyingCounter <= i * 6)
            changeAlpha(g2d, 1f);
        if (dyingCounter > i * 6 && dyingCounter <= i * 7)
            changeAlpha(g2d, 0f);
        if (dyingCounter > i * 7 && dyingCounter <= i * 8)
            changeAlpha(g2d, 1f);
        if (dyingCounter > i * 8)
            alive = false;
    }
}