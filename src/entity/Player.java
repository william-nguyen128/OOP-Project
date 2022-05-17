package entity;

import java.awt.Rectangle;
// import java.awt.Color;
import java.awt.Graphics2D;
// import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.object.OBJ_Fireball;
import entity.object.OBJ_Shield_Wood;
import entity.object.weapon.WPN_Whip;
import entity.object.weapon.Weapon;
import main.GAME_STATE;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    // Attributes
    private KeyHandler keyHandler;
    private int standCounter = 0;
    private ArrayList<Entity> inventory = new ArrayList<>();
    private Weapon currentWeapon;
    private Entity currentShield;
    private int attackCounter = 0;
    public final int SCREEN_X;
    public final int SCREEN_Y;
    public final int MAX_INV_SIZE = 20;

    // Constructor
    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;

        SCREEN_X = gamePanel.getScreenWidth() / 2 - gamePanel.getTileSize() / 2;
        SCREEN_Y = gamePanel.getScreenHeight() / 2 - gamePanel.getTileSize() / 2;

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
        setItems();
    }

    // Set initial (default) character's attributes
    public void setDefaultValues() {
        worldX = gamePanel.getTileSize() * 23;
        worldY = gamePanel.getTileSize() * 21;
        speed = 4;
        direction = "down";
        invincible = false;

        // Player's Status
        type = TYPE.Player;
        level = 1;
        maxLife = 100;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        strength = 1; // More strength = More damage dealt
        dexterity = 1; // More dexterity = Less damage received
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new WPN_Whip(gamePanel);
        currentShield = new OBJ_Shield_Wood(gamePanel);
        projectile = new OBJ_Fireball(gamePanel);
        defense = getDefense(); // Determined by dexterity & current shield
    }

    // Set player's initial items
    public void setItems() {
        inventory.clear(); // Restart the game
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }

    // Get images
    private void getPlayerImage() {
        int width = gamePanel.getTileSize();
        int height = gamePanel.getTileSize();

        for (int i = 0; i < 4; i++) {
            leftSprites[i] = setup("/player/player_left_" + (i + 1), width, height);
            rightSprites[i] = setup("/player/player_right_" + (i + 1), width, height);
        }
    }

    // Public methods
    public int getAttack() {
        return attack = strength * currentWeapon.attack;
    }

    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    public void selectItem() {
        int itemIndex = gamePanel.getUserInterface().getItemIndexOnSlot();

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == TYPE.Weapon)
                currentWeapon = (Weapon) selectedItem;
            if (selectedItem.type == TYPE.Shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == TYPE.Consumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    // Overridden methods
    @Override
    public void update() {
        int tempWorldX = worldX;
        int tempWorldY = worldY;
        int diagonalSpeed = speed / gamePanel.getTileSize() - 1;

        if (keyHandler.isUpPressed() == true || keyHandler.isLeftPressed() == true ||
                keyHandler.isDownPressed() == true || keyHandler.isRightPressed() == true ||
                keyHandler.isInteractPressed() == true) {
            // Set directions
            if (keyHandler.isUpPressed() == true) {
                direction = "up";
                worldY -= speed;
            }
            if (keyHandler.isLeftPressed() == true) {
                direction = "left";
                facingRight = false;
                worldX -= speed;
            }
            if (keyHandler.isDownPressed() == true) {
                direction = "down";
                worldY += speed;
            }
            if (keyHandler.isRightPressed() == true) {
                direction = "right";
                facingRight = true;
                worldX += speed;
            }
            if (keyHandler.isUpPressed() == true && keyHandler.isLeftPressed() == true) {
                direction = "up-left";
                facingRight = false;
                worldX -= diagonalSpeed;
                worldY -= diagonalSpeed;
            }
            if (keyHandler.isDownPressed() == true && keyHandler.isLeftPressed() == true) {
                direction = "down-left";
                facingRight = false;
                worldX -= diagonalSpeed;
                worldY += diagonalSpeed;
            }
            if (keyHandler.isDownPressed() == true && keyHandler.isRightPressed() == true) {
                direction = "down-right";
                facingRight = true;
                worldX += diagonalSpeed;
                worldY += diagonalSpeed;
            }
            if (keyHandler.isUpPressed() == true && keyHandler.isRightPressed() == true) {
                direction = "up-right";
                facingRight = true;
                worldX += diagonalSpeed;
                worldY -= diagonalSpeed;
            }

            // Check tile collision
            collisionOn = false;
            gamePanel.getCollisionChecker().checkTile(this);

            // Check object collision
            int objIndex = gamePanel.getCollisionChecker().checkObject(this, true);
            pickUpObject(objIndex);

            // Check NPC collision
            int npcIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNPCs());
            interactNPC(npcIndex);

            // Check Monster collision
            int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonsters());
            contactMonster(monsterIndex);

            // Check event
            gamePanel.getEventHandler().checkEvent();

            // If collision = false => Player can move | Else if collision = true => No
            if (collisionOn == true) {
                worldX = tempWorldX;
                worldY = tempWorldY;
            }

            keyHandler.setInteractPressed(false);

            // Player's sprite change
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1)
                    spriteNum = 2;
                else if (spriteNum == 2)
                    spriteNum = 3;
                else if (spriteNum == 3)
                    spriteNum = 4;
                else if (spriteNum == 4)
                    spriteNum = 1;
                spriteCounter = 0;
            }
        } else {
            // Standing still
            standCounter++;
            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        // Attacking
        if (attacking == true) {
            attackCounter = 0;
            currentWeapon.set(worldX, worldY, facingRight);
            gamePanel.getEntityList().add(currentWeapon);
            currentWeapon.update();
        }

        // Casting
        if (keyHandler.isCastPressed() == true && projectile.alive == false &&
                shotAvailableCounter == 30 && projectile.haveResource(this) == true) {
            projectile.set(worldX, worldY, direction, true);
            projectile.subtractResource(this);
            gamePanel.getProjectileList().add(projectile);
            shotAvailableCounter = 0;
            gamePanel.playSoundEffect(10);
        }

        // Invincibility frame
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 30) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // Attacking counter
        if (attackCounter < 60)
            attackCounter++;
        else if (attackCounter == 60)
            attacking = true;

        if (shotAvailableCounter < 30)
            shotAvailableCounter++;

        if (life > maxLife)
            life = maxLife;

        if (mana > maxMana)
            mana = maxMana;

        // Player got rekt
        if (life <= 0) {
            gamePanel.gameState = GAME_STATE.GameOver;
            gamePanel.stopMusic();
            gamePanel.playSoundEffect(11);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage image = null;
        int x = SCREEN_X;
        int y = SCREEN_Y;

        // Player's HP bar
        double oneScale = (double) gamePanel.getTileSize() / maxLife;
        double hpBarValue = oneScale * life;
        int height = gamePanel.getTileSize();

        g2d.setColor(new Color(35, 35, 35));
        g2d.fillRect(SCREEN_X - 1, SCREEN_Y + height + 10, gamePanel.getTileSize() + 2, 8);

        g2d.setColor(new Color(255, 0, 30));
        g2d.fillRect(SCREEN_X, SCREEN_Y + height + 11, (int) hpBarValue, 6);

        // Player's Image based on Directions
        if (facingRight == false) {
            if (spriteNum == 1)
                image = leftSprites[0];
            if (spriteNum == 2)
                image = leftSprites[1];
            if (spriteNum == 3)
                image = leftSprites[2];
            if (spriteNum == 4)
                image = leftSprites[3];
        } else {
            if (spriteNum == 1)
                image = rightSprites[0];
            if (spriteNum == 2)
                image = rightSprites[1];
            if (spriteNum == 3)
                image = rightSprites[2];
            if (spriteNum == 4)
                image = rightSprites[3];
        }

        if (SCREEN_X > worldX)
            x = worldX;
        if (SCREEN_Y > worldY)
            y = worldY;
        int rightOffset = gamePanel.getScreenWidth() - SCREEN_X;
        if (rightOffset > gamePanel.getWorldWidth() - worldX)
            x = gamePanel.getScreenWidth() - (gamePanel.getWorldWidth() - worldX);
        int bottomOffset = gamePanel.getScreenHeight() - SCREEN_Y;
        if (bottomOffset > gamePanel.getWorldHeight() - worldY)
            y = gamePanel.getScreenHeight() - (gamePanel.getWorldHeight() - worldY);

        if (invincible == true)
            changeAlpha(g2d, 0.3f);

        g2d.drawImage(image, x, y, null);

        // Reset alpha
        changeAlpha(g2d, 1f);
    }

    // Restricted method (Used for Weapons and Projectiles only)
    public void damageMonster(int index, int attack) {
        if (index != 999)
            if (gamePanel.getMonsters()[index].invincible == false) {
                gamePanel.playSoundEffect(5);

                int damage = attack - gamePanel.getMonsters()[index].defense;
                if (damage < 0)
                    damage = 1;

                gamePanel.getMonsters()[index].life -= damage;
                gamePanel.getUserInterface().addMessage(damage + " damage!");

                gamePanel.getMonsters()[index].invincible = true;
                gamePanel.getMonsters()[index].damageReaction();

                if (gamePanel.getMonsters()[index].life <= 0) {
                    gamePanel.getMonsters()[index].dying = true;
                    gamePanel.getUserInterface().addMessage(gamePanel.getMonsters()[index].name + " was killed!");
                    gamePanel.getUserInterface().addMessage(
                            gamePanel.getMonsters()[index].exp + " EXP gained from "
                                    + gamePanel.getMonsters()[index].name + "!");
                    exp += gamePanel.getMonsters()[index].exp;
                    checkLevelUp();
                }
            }
    }

    // Private (Internal) methods
    private void interactNPC(int index) {
        if (index != 999 && keyHandler.isInteractPressed() == true) {
            gamePanel.gameState = GAME_STATE.Dialogue;
            gamePanel.getNPCs()[index].speak();
        }
    }

    private void contactMonster(int index) {
        if (index != 999)
            if (invincible == false && gamePanel.getMonsters()[index].dying == false) {
                gamePanel.playSoundEffect(6);

                int damage = gamePanel.getMonsters()[index].attack;

                life -= damage;
                invincible = true;
            }
    }

    private void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            exp -= nextLevelExp;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gamePanel.playSoundEffect(8);
            gamePanel.gameState = GAME_STATE.Dialogue;
            gamePanel.getUserInterface().setCurrentDialogue("Level up!");
        }
    }

    private void pickUpObject(int index) {
        if (index != 999) {
            // Coins (Pickup Only Item)
            if (gamePanel.getObjects()[index].type == TYPE.PickupOnly) {
                gamePanel.getObjects()[index].use(this);
                gamePanel.getObjects()[index] = null;
            }
            // Inventory Items
            else {
                String text;

                if (inventory.size() != MAX_INV_SIZE) {
                    inventory.add(gamePanel.getObjects()[index]);
                    gamePanel.playSoundEffect(1);
                    text = "Obtained a " + gamePanel.getObjects()[index].name + "!";
                } else
                    text = "You cannot carry anymore";

                gamePanel.getUserInterface().addMessage(text);
                gamePanel.getObjects()[index] = null;
            }
        }
    }

    // Getters
    public ArrayList<Entity> getInventory() {
        return inventory;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public Entity getCurrentShield() {
        return currentShield;
    }

    public int getAttackCounter() {
        return attackCounter;
    }
}