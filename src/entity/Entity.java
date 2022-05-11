package entity;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Entity {
    // Attributes
    protected GamePanel gamePanel;
    protected BufferedImage leftSprites[] = new BufferedImage[10];
    protected BufferedImage rightSprites[] = new BufferedImage[10];
    protected Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    protected int solidAreaDefaultX, solidAreaDefaultY;
    protected boolean collision = false;
    protected String dialogues[] = new String[20];

    // State
    protected int worldX, worldY;
    protected String direction = "down";
    protected boolean facingRight = true;
    protected int spriteNum = 1;
    protected int dialogueIndex = 0;
    protected boolean collisionOn = false;
    protected boolean invincible = false;
    protected boolean attacking = false;
    protected boolean alive = true;
    protected boolean dying = false;

    // Counter
    protected int spriteCounter = 0;
    protected int actionLockCounter = 0;
    protected int invincibleCounter = 0;
    protected int shotAvailableCounter = 0;

    // Entity's attributes
    protected TYPE type;
    protected String name;
    protected int speed;
    protected int maxLife;
    protected int life;
    protected int maxMana;
    protected int mana;
    protected int level;
    protected int strength;
    protected int dexterity;
    protected int attack;
    protected int defense;
    protected int exp;
    protected int nextLevelExp;
    protected int coin;
    protected Projectile projectile;

    // Items' attributes
    protected int value;
    protected int attackValue;
    protected int defenseValue;
    protected String description = "";
    protected int castCost;

    // Constructor
    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Parent method (Empty)
    public void use(Entity entity) {
    }

    // For Monsters
    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gamePanel.getObjects().length; i++)
            if (gamePanel.getObjects()[i] == null) {
                gamePanel.getObjects()[i] = droppedItem;
                gamePanel.getObjects()[i].worldX = worldX; // Deceased monster's worldX
                gamePanel.getObjects()[i].worldY = worldY; // Deceased monster's worldY
                break;
            }
    }

    // For NPC
    public void speak() {
        if (dialogues[dialogueIndex] == null)
            dialogueIndex = 0;
        gamePanel.getUserInterface().setCurrentDialogue(dialogues[dialogueIndex]);
        dialogueIndex++;

        switch (gamePanel.getPlayer().direction) {
            case "up":
                direction = "down";
                break;
            case "left":
                direction = "right";
                break;
            case "down":
                direction = "up";
                break;
            case "right":
                direction = "left";
                break;
            case "up-left":
                direction = "down-right";
                break;
            case "down-left":
                direction = "up-right";
                break;
            case "down-right":
                direction = "up-left";
                break;
            case "up-right":
                direction = "down-left";
                break;
        }
    }

    // For Particles
    public Color getParticleColor() {
        Color color = null;
        return color;
    }

    public int getParticleSize() {
        int size = 0;
        return size;
    }

    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }

    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, 1);
        gamePanel.getParticleList().add(p1);
        gamePanel.getParticleList().add(p2);
        gamePanel.getParticleList().add(p3);
        gamePanel.getParticleList().add(p4);
    }

    // For Monsters & Projectiles that hit the Player
    public void damagePlayer(int attack) {
        if (gamePanel.getPlayer().invincible == false) {
            gamePanel.playSoundEffect(6);

            int damage = attack - gamePanel.getPlayer().defense;
            if (damage < 0)
                damage = 1;

            gamePanel.getPlayer().life -= damage;
            gamePanel.getPlayer().invincible = true;
        }
    }

    // Parent methods
    public void update() {
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

            g2d.drawImage(image, screenX, screenY, null);

            changeAlpha(g2d, 1f);
        }
    }

    // Utility methods
    public void changeAlpha(Graphics2D g2d, float alphaValue) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    // Getters
    public BufferedImage getDown1() {
        return rightSprites[0];
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public boolean isCollision() {
        return collision;
    }

    public String getDirection() {
        return direction;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDying() {
        return dying;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getSpeed() {
        return speed;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getLevel() {
        return level;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getAttack() {
        return attack;
    }

    public int getEXP() {
        return exp;
    }

    public int getNextLevelEXP() {
        return nextLevelExp;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getDescription() {
        return description;
    }

    public int getInvincibleCounter() {
        return invincibleCounter;
    }
}