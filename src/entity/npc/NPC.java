package entity.npc;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class NPC extends Entity {
    // Attribute
    protected String dialogues[] = new String[20];
    protected int dialogueIndex = 0;

    // Constructor
    public NPC(GamePanel gamePanel) {
        super(gamePanel);
        type = TYPE.NPC;
    }

    // Parent method
    public void speak() {
        if (dialogues[dialogueIndex] == null)
            dialogueIndex = 0;
        gamePanel.getUserInterface().setCurrentDialogue(dialogues[dialogueIndex]);
        dialogueIndex++;
    }

    // Overridden method
    @Override
    public void update() {
        collisionOn = false;

        // NPC's sprite change
        spriteCounter++;
        if (spriteCounter > 15) {
            if (spriteNum == 1)
                spriteNum = 2;
            else if (spriteNum == 2)
                spriteNum = 1;
            spriteCounter = 0;
        }
    }

    @Override
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

            g2d.drawImage(image, screenX, screenY, null);

            changeAlpha(g2d, 1f);
        }
    }
}