package entity.object.weapon;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.TYPE;
// import entity.monster.Monster;
import main.GamePanel;

public class Weapon extends Entity {
    // Attributes
    protected BufferedImage attackLeft[] = new BufferedImage[10];
    protected BufferedImage attackRight[] = new BufferedImage[10];

    // Constructor
    public Weapon(GamePanel gamePanel) {
        super(gamePanel);
        type = TYPE.Weapon;
    }

    // Set starting values
    public void set(int worldX, int worldY, boolean facingRight) {
        int playerWidth = gamePanel.getPlayer().getSolidArea().width;
        int playerHeight = gamePanel.getPlayer().getSolidArea().height;

        if (facingRight == false)
            this.worldX = worldX - (solidArea.width - 3 * playerWidth / 4);
        else
            this.worldX = worldX + 3 * playerWidth / 4;
        this.worldY = worldY - playerHeight / 2;
        this.facingRight = facingRight;
    }

    // Overridden methods
    @Override
    public void update() {
        // Weapon's sprite change
        spriteCounter++;
        if (spriteCounter <= 5) {
            gamePanel.playSoundEffect(7);
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 10)
            spriteNum = 2;
        if (spriteCounter > 10 && spriteCounter <= 15) {
            spriteNum = 3;

            // Check Monster collision w/ the updated worldX, worldY, and solidArea
            int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonsters());
            gamePanel.getPlayer().damageMonster(monsterIndex, attack * gamePanel.getPlayer().getStrength());
            // if (monsterIndex != 999)
            // knockBack(gamePanel.getMonsters()[monsterIndex]);

            int currentWorldX = worldX;

            if (facingRight == false)
                worldX -= solidArea.width;
            else
                worldX += solidArea.width;

            // Restore original values
            worldX = currentWorldX;
        }
        if (spriteCounter > 15 && spriteCounter <= 20)
            spriteNum = 4;
        if (spriteCounter > 20 && spriteCounter <= 25)
            spriteNum = 5;
        if (spriteCounter > 25 && spriteCounter <= 30)
            spriteNum = 6;
        if (spriteCounter > 30) {
            spriteCounter = 0;
            gamePanel.getPlayer().setAttacking(false);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage image = null;
        int playerWidth = gamePanel.getPlayer().getSolidArea().width;
        int playerHeight = gamePanel.getPlayer().getSolidArea().height;
        int x = gamePanel.getPlayer().SCREEN_X + 3 * playerWidth / 4;
        int y = gamePanel.getPlayer().SCREEN_Y - playerHeight / 2;

        // Whip's Attacking Image based on Player's Directions
        if (facingRight == false) {
            x = gamePanel.getPlayer().SCREEN_X - (solidArea.width - 3 * playerWidth / 4);
            if (spriteNum == 1)
                image = attackLeft[0];
            if (spriteNum == 2)
                image = attackLeft[1];
            if (spriteNum == 3)
                image = attackLeft[2];
            if (spriteNum == 4)
                image = attackLeft[3];
            if (spriteNum == 5)
                image = attackLeft[4];
            if (spriteNum == 6)
                image = attackLeft[5];
        } else {
            x = gamePanel.getPlayer().SCREEN_X + 3 * playerWidth / 4;
            if (spriteNum == 1)
                image = attackRight[0];
            if (spriteNum == 2)
                image = attackRight[1];
            if (spriteNum == 3)
                image = attackRight[2];
            if (spriteNum == 4)
                image = attackRight[3];
            if (spriteNum == 5)
                image = attackRight[4];
            if (spriteNum == 6)
                image = attackRight[5];
        }

        // ============ DEBUG ONLY ============ //
        if (gamePanel.getKeyHandler().isShowDebugTexts() == true)
            drawWeaponHitBox(g2d, x, y);
        // ============ DEBUG ONLY ============ //

        changeAlpha(g2d, 0.6f);
        g2d.drawImage(image, x, y, null);
        changeAlpha(g2d, 1f); // Reset alpha
    }

    // Private (Internal) method

    // ============ DEBUG ONLY ============ //
    private void drawWeaponHitBox(Graphics2D g2d, int startX, int startY) {
        g2d.setColor(Color.RED);
        g2d.drawRect(startX, startY, solidArea.width, solidArea.height);
    }
    // ============ DEBUG ONLY ============ //
}