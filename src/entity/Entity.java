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
    protected BufferedImage image, image2, image3;
    protected BufferedImage up1, up2, up3, up4;
    protected BufferedImage left1, left2, left3, left4;
    protected BufferedImage down1, down2, down3, down4;
    protected BufferedImage right1, right2, right3, right4;
    protected BufferedImage up_left1, up_left2, up_left3, up_left4;
    protected BufferedImage down_left1, down_left2, down_left3, down_left4;
    protected BufferedImage down_right1, down_right2, down_right3, down_right4;
    protected BufferedImage up_right1, up_right2, up_right3, up_right4;
    protected BufferedImage attackUp1, attackUp2, attackLeft1, attackLeft2,
            attackDown1, attackDown2, attackRight1, attackRight2,
            attackUpLeft1, attackUpLeft2, attackDownLeft1, attackDownLeft2,
            attackDownRight1, attackDownRight2, attackUpRight1, attackUpRight2;
    protected Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    protected Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    protected int solidAreaDefaultX, solidAreaDefaultY;
    protected boolean collision = false;
    protected String dialogues[] = new String[20];

    // State
    protected int worldX, worldY;
    protected String direction = "down";
    protected int spriteNum = 1;
    protected int dialogueIndex = 0;
    protected boolean collisionOn = false;
    protected boolean invincible = false;
    protected boolean attacking = false;
    protected boolean alive = true;
    protected boolean dying = false;
    private boolean hpBarOn = false;

    // Counter
    protected int spriteCounter = 0;
    protected int actionLockCounter = 0;
    protected int invincibleCounter = 0;
    protected int shotAvailableCounter = 0;
    private int dyingCounter = 0;
    private int hpBarCounter = 0;

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
    protected Entity currentWeapon;
    protected Entity currentShield;
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

    // Parent methods (Empty)
    public void setAction() {
    }

    public void damageReaction() {
    }

    public void use(Entity entity) {
    }

    public void checkDrop() {
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
                damage = 0;

            gamePanel.getPlayer().life -= damage;
            gamePanel.getPlayer().invincible = true;
        }
    }

    // Parent methods
    public void update() {
        setAction();

        collisionOn = false;
        gamePanel.getCollisionChecker().checkTile(this);
        gamePanel.getCollisionChecker().checkObject(this, false);
        gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNPCs());
        gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonsters());
        boolean contactPlayer = gamePanel.getCollisionChecker().checkPlayer(this);

        if (type == TYPE.Monster && contactPlayer == true)
            damagePlayer(attack);

        // If collision = false => Player can move
        if (collisionOn == false)
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
                case "up-left":
                    worldX -= speed;
                    worldY -= speed;
                    break;
                case "down-left":
                    worldX -= speed;
                    worldY += speed;
                    break;
                case "down-right":
                    worldX += speed;
                    worldY += speed;
                    break;
                case "up-right":
                    worldX += speed;
                    worldY -= speed;
                    break;
            }

        // Player's sprite change
        spriteCounter++;
        if (spriteCounter > 15) {
            if (spriteNum == 1)
                spriteNum = 2;
            else if (spriteNum == 2)
                spriteNum = 1;
            spriteCounter = 0;
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter < 30)
            shotAvailableCounter++;
    }

    public void draw(Graphics2D g2d) {
        int screenX = worldX - gamePanel.getPlayer().worldX + gamePanel.getPlayer().SCREEN_X;
        int screenY = worldY - gamePanel.getPlayer().worldY + gamePanel.getPlayer().SCREEN_Y;

        if (worldX + gamePanel.getTileSize() > gamePanel.getPlayer().worldX - gamePanel.getPlayer().SCREEN_X &&
                worldX - gamePanel.getTileSize() < gamePanel.getPlayer().worldX + gamePanel.getPlayer().SCREEN_X &&
                worldY + gamePanel.getTileSize() > gamePanel.getPlayer().worldY - gamePanel.getPlayer().SCREEN_Y &&
                worldY - gamePanel.getTileSize() < gamePanel.getPlayer().worldY + gamePanel.getPlayer().SCREEN_Y) {
            BufferedImage image = null;

            switch (direction) {
                case "up":
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                    break;
                case "left":
                    if (spriteNum == 1)
                        image = left1;
                    if (spriteNum == 2)
                        image = left2;
                    break;
                case "down":
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                    break;
                case "right":
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                    break;
                case "up-left":
                    if (spriteNum == 1)
                        image = up_left1;
                    if (spriteNum == 2)
                        image = up_left2;
                    break;
                case "down-left":
                    if (spriteNum == 1)
                        image = down_left1;
                    if (spriteNum == 2)
                        image = down_left2;
                    break;
                case "down-right":
                    if (spriteNum == 1)
                        image = down_right1;
                    if (spriteNum == 2)
                        image = down_right2;
                    break;
                case "up-right":
                    if (spriteNum == 1)
                        image = up_right1;
                    if (spriteNum == 2)
                        image = up_right2;
                    break;
            }

            // Monster HP bar
            if (type == TYPE.Monster && hpBarOn == true) {
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

    // Getters
    public BufferedImage getDown1() {
        return down1;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getImage2() {
        return image2;
    }

    public BufferedImage getImage3() {
        return image3;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public Rectangle getAttackArea() {
        return attackArea;
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

    public Entity getCurrentWeapon() {
        return currentWeapon;
    }

    public Entity getCurrentShield() {
        return currentShield;
    }

    public String getDescription() {
        return description;
    }
}