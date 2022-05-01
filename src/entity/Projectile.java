package entity;

import main.GamePanel;

public class Projectile extends Entity {
    // Attribute
    private Entity caster;

    // Constructor
    public Projectile(GamePanel gamePanel) {
        super(gamePanel);
    }

    // Set starting values
    public void set(int worldX, int worldY, String direction, boolean alive, Entity caster) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.caster = caster;
        this.life = this.maxLife;
    }

    // Overridden method
    @Override
    public void update() {
        if (caster == gamePanel.getPlayer()) {
            int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonsters());
            if (monsterIndex != 999) {
                gamePanel.getPlayer().damageMonster(monsterIndex, attack);
                generateParticle(caster.projectile, gamePanel.getMonsters()[monsterIndex]);
                alive = false;
            }
        }
        if (caster != gamePanel.getPlayer()) {
            boolean contactPlayer = gamePanel.getCollisionChecker().checkPlayer(this);
            if (gamePanel.getPlayer().invincible == false && contactPlayer == true) {
                damagePlayer(attack);
                generateParticle(caster.projectile, gamePanel.getPlayer());
                alive = false;
            }
        }

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

        life--;
        if (life <= 0)
            alive = false;

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1)
                spriteNum = 2;
            else if (spriteNum == 2)
                spriteNum = 1;
            spriteCounter = 0;
        }
    }

    // Parent methods
    public boolean haveResource(Entity caster) {
        boolean haveResource = false;
        return haveResource;
    }

    public void subtractResource(Entity caster) {
    }
}