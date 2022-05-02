package entity;

import java.awt.Rectangle;
// import java.awt.Color;
import java.awt.Graphics2D;
// import java.awt.Font;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.object.OBJ_Fireball;
import entity.object.OBJ_Key;
import entity.object.OBJ_Shield_Wood;
import entity.object.OBJ_Sword_Normal;
import main.GAME_STATE;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    // Attributes
    private KeyHandler keyHandler;
    private int standCounter = 0;
    private ArrayList<Entity> inventory = new ArrayList<>();
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
        getPlayerAttackImage();
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
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        strength = 1; // More strength = More damage dealt
        dexterity = 1; // More dexterity = Less damage received
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gamePanel);
        currentShield = new OBJ_Shield_Wood(gamePanel);
        projectile = new OBJ_Fireball(gamePanel);
        attack = getAttack(); // Determined by strength & current weapon
        defense = getDefense(); // Determined by dexterity & current shield
    }

    // Set player's initial items
    public void setItems() {
        inventory.clear(); // Restart the game
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(new OBJ_Key(gamePanel));
    }

    // Get images
    private void getPlayerImage() {
        int width = gamePanel.getTileSize();
        int height = gamePanel.getTileSize();

        up1 = setup("/player/player_left_1", width, height);
        up2 = setup("/player/player_left_2", width, height);
        up3 = setup("/player/player_left_3", width, height);
        up4 = setup("/player/player_left_4", width, height);
        left1 = setup("/player/player_left_1", width, height);
        left2 = setup("/player/player_left_2", width, height);
        left3 = setup("/player/player_left_3", width, height);
        left4 = setup("/player/player_left_4", width, height);
        down1 = setup("/player/player_right_1", width, height);
        down2 = setup("/player/player_right_2", width, height);
        down3 = setup("/player/player_right_3", width, height);
        down4 = setup("/player/player_right_4", width, height);
        right1 = setup("/player/player_right_1", width, height);
        right2 = setup("/player/player_right_2", width, height);
        right3 = setup("/player/player_right_3", width, height);
        right4 = setup("/player/player_right_4", width, height);

        up_left1 = setup("/player/player_left_1", width, height);
        up_left2 = setup("/player/player_left_2", width, height);
        up_left3 = setup("/player/player_left_3", width, height);
        up_left4 = setup("/player/player_left_4", width, height);
        down_left1 = setup("/player/player_left_1", width, height);
        down_left2 = setup("/player/player_left_2", width, height);
        down_left3 = setup("/player/player_left_3", width, height);
        down_left4 = setup("/player/player_left_4", width, height);
        down_right1 = setup("/player/player_right_1", width, height);
        down_right2 = setup("/player/player_right_2", width, height);
        down_right3 = setup("/player/player_right_3", width, height);
        down_right4 = setup("/player/player_right_4", width, height);
        up_right1 = setup("/player/player_right_1", width, height);
        up_right2 = setup("/player/player_right_2", width, height);
        up_right3 = setup("/player/player_right_3", width, height);
        up_right4 = setup("/player/player_right_4", width, height);
    }

    private void getPlayerAttackImage() {
        if (currentWeapon.type == TYPE.Sword) {
            int width = gamePanel.getTileSize();
            int height = gamePanel.getTileSize() * 2;
            attackUp1 = setup("/player/boy_attack_up_1", width, height);
            attackUp2 = setup("/player/boy_attack_up_2", width, height);
            attackDown1 = setup("/player/boy_attack_down_1", width, height);
            attackDown2 = setup("/player/boy_attack_down_2", width, height);

            width = gamePanel.getTileSize() * 2;
            height = gamePanel.getTileSize();
            attackLeft1 = setup("/player/boy_attack_left_1", width, height);
            attackLeft2 = setup("/player/boy_attack_left_2", width, height);
            attackRight1 = setup("/player/boy_attack_right_1", width, height);
            attackRight2 = setup("/player/boy_attack_right_2", width, height);

            attackUpLeft1 = setup("/player/boy_attack_left_1", width, height);
            attackUpLeft2 = setup("/player/boy_attack_left_2", width, height);
            attackDownLeft1 = setup("/player/boy_attack_left_1", width, height);
            attackDownLeft2 = setup("/player/boy_attack_left_2", width, height);
            attackDownRight1 = setup("/player/boy_attack_right_1", width, height);
            attackDownRight2 = setup("/player/boy_attack_right_2", width, height);
            attackUpRight1 = setup("/player/boy_attack_right_1", width, height);
            attackUpRight2 = setup("/player/boy_attack_right_2", width, height);
        }

        if (currentWeapon.type == TYPE.Axe) {
            int width = gamePanel.getTileSize();
            int height = gamePanel.getTileSize() * 2;
            attackUp1 = setup("/player/boy_axe_up_1", width, height);
            attackUp2 = setup("/player/boy_axe_up_2", width, height);
            attackDown1 = setup("/player/boy_axe_down_1", width, height);
            attackDown2 = setup("/player/boy_axe_down_2", width, height);

            width = gamePanel.getTileSize() * 2;
            height = gamePanel.getTileSize();
            attackLeft1 = setup("/player/boy_axe_left_1", width, height);
            attackLeft2 = setup("/player/boy_axe_left_2", width, height);
            attackRight1 = setup("/player/boy_axe_right_1", width, height);
            attackRight2 = setup("/player/boy_axe_right_2", width, height);

            attackUpLeft1 = setup("/player/boy_axe_left_1", width, height);
            attackUpLeft2 = setup("/player/boy_axe_left_2", width, height);
            attackDownLeft1 = setup("/player/boy_axe_left_1", width, height);
            attackDownLeft2 = setup("/player/boy_axe_left_2", width, height);
            attackDownRight1 = setup("/player/boy_axe_right_1", width, height);
            attackDownRight2 = setup("/player/boy_axe_right_2", width, height);
            attackUpRight1 = setup("/player/boy_axe_right_1", width, height);
            attackUpRight2 = setup("/player/boy_axe_right_2", width, height);
        }
    }

    // Public methods
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    public void selectItem() {
        int itemIndex = gamePanel.getUserInterface().getItemIndexOnSlot();

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == TYPE.Sword || selectedItem.type == TYPE.Axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
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

        if (attacking == true)
            attacking();
        else if (keyHandler.isUpPressed() == true || keyHandler.isLeftPressed() == true ||
                keyHandler.isDownPressed() == true || keyHandler.isRightPressed() == true ||
                keyHandler.isInteractPressed() == true || keyHandler.isAttackPressed() == true) {
            // Set direction
            if (keyHandler.isUpPressed() == true) {
                direction = "up";
                worldY -= speed;
            }
            if (keyHandler.isLeftPressed() == true) {
                direction = "left";
                worldX -= speed;
            }
            if (keyHandler.isDownPressed() == true) {
                direction = "down";
                worldY += speed;
            }
            if (keyHandler.isRightPressed() == true) {
                direction = "right";
                worldX += speed;
            }
            if (keyHandler.isUpPressed() == true && keyHandler.isLeftPressed() == true) {
                direction = "up-left";
                worldX -= diagonalSpeed;
                worldY -= diagonalSpeed;
            }
            if (keyHandler.isDownPressed() == true && keyHandler.isLeftPressed() == true) {
                direction = "down-left";
                worldX -= diagonalSpeed;
                worldY += diagonalSpeed;
            }
            if (keyHandler.isDownPressed() == true && keyHandler.isRightPressed() == true) {
                direction = "down-right";
                worldX += diagonalSpeed;
                worldY += diagonalSpeed;
            }
            if (keyHandler.isUpPressed() == true && keyHandler.isRightPressed() == true) {
                direction = "up-right";
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

            // If collision = false => Player can move
            if (collisionOn == true) {
                worldX = tempWorldX;
                worldY = tempWorldY;
            }

            gamePanel.getKeyHandler().setInteractPressed(false);
            gamePanel.getKeyHandler().setAttackPressed(false);

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
            standCounter++;
            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        // Casting projectiles (spells)
        if (gamePanel.getKeyHandler().isCastPressed() == true && projectile.alive == false &&
                shotAvailableCounter == 30 && projectile.haveResource(this) == true) {
            projectile.set(worldX, worldY, direction, true, this);
            projectile.subtractResource(this);
            gamePanel.getProjectileList().add(projectile);
            shotAvailableCounter = 0;
            gamePanel.playSoundEffect(10);
        }

        // Invincibility frame
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

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

        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                    if (spriteNum == 3)
                        image = up3;
                    if (spriteNum == 4)
                        image = up4;
                }
                if (attacking == true) {
                    y = SCREEN_Y - gamePanel.getTileSize();
                    if (spriteNum == 1)
                        image = attackUp1;
                    if (spriteNum == 2)
                        image = attackUp2;
                    // ============ DEBUG ONLY ============ //
                    drawWeaponHitBox(g2d, SCREEN_X + solidArea.x,
                            SCREEN_Y + solidArea.y - currentWeapon.attackArea.height);
                    // ============ DEBUG ONLY ============ //
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = left1;
                    if (spriteNum == 2)
                        image = left2;
                    if (spriteNum == 3)
                        image = left3;
                    if (spriteNum == 4)
                        image = left4;
                }
                if (attacking == true) {
                    x = SCREEN_X - gamePanel.getTileSize();
                    if (spriteNum == 1)
                        image = attackLeft1;
                    if (spriteNum == 2)
                        image = attackLeft2;
                    // ============ DEBUG ONLY ============ //
                    drawWeaponHitBox(g2d, SCREEN_X + solidArea.x - currentWeapon.attackArea.width,
                            SCREEN_Y + solidArea.y);
                    // ============ DEBUG ONLY ============ //
                }
                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                    if (spriteNum == 3)
                        image = down3;
                    if (spriteNum == 4)
                        image = down4;
                }
                if (attacking == true) {
                    if (spriteNum == 1)
                        image = attackDown1;
                    if (spriteNum == 2)
                        image = attackDown2;
                    // ============ DEBUG ONLY ============ //
                    drawWeaponHitBox(g2d, SCREEN_X + solidArea.x,
                            SCREEN_Y + solidArea.y + currentWeapon.attackArea.height);
                    // ============ DEBUG ONLY ============ //
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                    if (spriteNum == 3)
                        image = right3;
                    if (spriteNum == 4)
                        image = right4;
                }
                if (attacking == true) {
                    if (spriteNum == 1)
                        image = attackRight1;
                    if (spriteNum == 2)
                        image = attackRight2;
                    // ============ DEBUG ONLY ============ //
                    drawWeaponHitBox(g2d, SCREEN_X + solidArea.x + currentWeapon.attackArea.width,
                            SCREEN_Y + solidArea.y);
                    // ============ DEBUG ONLY ============ //
                }
                break;
            case "up-left":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = up_left1;
                    if (spriteNum == 2)
                        image = up_left2;
                    if (spriteNum == 3)
                        image = up_left3;
                    if (spriteNum == 4)
                        image = up_left4;
                }
                if (attacking == true) {
                    x = SCREEN_X - gamePanel.getTileSize();
                    if (spriteNum == 1)
                        image = attackUpLeft1;
                    if (spriteNum == 2)
                        image = attackUpLeft2;
                    // ============ DEBUG ONLY ============ //
                    drawWeaponHitBox(g2d, SCREEN_X + solidArea.x - currentWeapon.attackArea.width,
                            SCREEN_Y + solidArea.y);
                    // ============ DEBUG ONLY ============ //
                }
                break;
            case "down-left":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = down_left1;
                    if (spriteNum == 2)
                        image = down_left2;
                    if (spriteNum == 3)
                        image = down_left3;
                    if (spriteNum == 4)
                        image = down_left4;
                }
                if (attacking == true) {
                    x = SCREEN_X - gamePanel.getTileSize();
                    if (spriteNum == 1)
                        image = attackDownLeft1;
                    if (spriteNum == 2)
                        image = attackDownLeft2;
                    // ============ DEBUG ONLY ============ //
                    drawWeaponHitBox(g2d, SCREEN_X + solidArea.x - currentWeapon.attackArea.width,
                            SCREEN_Y + solidArea.y);
                    // ============ DEBUG ONLY ============ //
                }
                break;
            case "down-right":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = down_right1;
                    if (spriteNum == 2)
                        image = down_right2;
                    if (spriteNum == 3)
                        image = down_right3;
                    if (spriteNum == 4)
                        image = down_right4;
                }
                if (attacking == true) {
                    if (spriteNum == 1)
                        image = attackDownRight1;
                    if (spriteNum == 2)
                        image = attackDownRight2;
                    // ============ DEBUG ONLY ============ //
                    drawWeaponHitBox(g2d, SCREEN_X + solidArea.x + currentWeapon.attackArea.width,
                            SCREEN_Y + solidArea.y);
                    // ============ DEBUG ONLY ============ //
                }
                break;
            case "up-right":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = up_right1;
                    if (spriteNum == 2)
                        image = up_right2;
                    if (spriteNum == 3)
                        image = up_right3;
                    if (spriteNum == 4)
                        image = up_right4;
                }
                if (attacking == true) {
                    if (spriteNum == 1)
                        image = attackUpRight1;
                    if (spriteNum == 2)
                        image = attackUpRight2;
                    // ============ DEBUG ONLY ============ //
                    drawWeaponHitBox(g2d, SCREEN_X + solidArea.x + currentWeapon.attackArea.width,
                            SCREEN_Y + solidArea.y);
                    // ============ DEBUG ONLY ============ //
                }
                break;
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
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));

        g2d.drawImage(image, x, y, null);

        // Reset alpha
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    // Restricted method (Used for Player and Projectile only)
    protected void damageMonster(int index, int attack) {
        if (index != 999)
            if (gamePanel.getMonsters()[index].invincible == false) {
                gamePanel.playSoundEffect(5);

                int damage = attack - gamePanel.getMonsters()[index].defense;
                if (damage < 0)
                    damage = 0;

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
    private void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5)
            spriteNum = 1;
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
                case "up-left":
                    worldX -= attackArea.width;
                    break;
                case "down-left":
                    worldX -= attackArea.width;
                    break;
                case "down-right":
                    worldX += attackArea.width;
                    break;
                case "up-right":
                    worldX += attackArea.width;
                    break;
            }

            // Attack area -> Solid area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // Check Monster collision w/ the updated worldX, worldY, and solidArea
            int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonsters());
            damageMonster(monsterIndex, attack);

            // Restore original values
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void interactNPC(int index) {
        if (index != 999 && gamePanel.getKeyHandler().isInteractPressed() == true) {
            gamePanel.gameState = GAME_STATE.Dialogue;
            gamePanel.getNPCs()[index].speak();
        } else if (index == 999 && gamePanel.getKeyHandler().isAttackPressed() == true) {
            gamePanel.playSoundEffect(7);
            attacking = true;
        }
    }

    private void contactMonster(int index) {
        if (index != 999)
            if (invincible == false && gamePanel.getMonsters()[index].dying == false) {
                gamePanel.playSoundEffect(6);

                int damage = gamePanel.getMonsters()[index].attack - defense;
                if (damage < 0)
                    damage = 0;

                life -= damage;
                invincible = true;
            }
    }

    private void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
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

    // ============ DEBUG ONLY ============ //
    private void drawWeaponHitBox(Graphics2D g2d, int startX, int startY) {
        if (gamePanel.gameState == GAME_STATE.Play && keyHandler.isShowDebugTexts() == true) {
            g2d.setColor(Color.RED);
            g2d.drawRect(startX, startY, currentWeapon.attackArea.width, currentWeapon.attackArea.height);
        }
    }
    // ============ DEBUG ONLY ============ //

    // Getter
    public ArrayList<Entity> getInventory() {
        return inventory;
    }

    public int getInvincibleCounter() {
        return invincibleCounter;
    }

    public boolean isAttacking() {
        return attacking;
    }
}