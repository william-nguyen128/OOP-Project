package entity.monster;

import java.util.Random;

import entity.Entity;
import entity.TYPE;
import entity.object.OBJ_Coin_Bronze;
import entity.object.OBJ_Heart;
import entity.object.OBJ_ManaCrystal;
import entity.object.OBJ_Rock;
import main.GamePanel;

public class MON_GreenSlime extends Entity {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public MON_GreenSlime(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = TYPE.Monster;
        name = "Green Slime";
        speed = 1;
        maxLife = 5;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gamePanel);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getGreenSlimeImage();
    }

    // Get images
    private void getGreenSlimeImage() {
        int width = gamePanel.getTileSize();
        int height = gamePanel.getTileSize();

        up1 = setup("/monster/greenslime_down_1", width, height);
        up2 = setup("/monster/greenslime_down_2", width, height);
        left1 = setup("/monster/greenslime_down_1", width, height);
        left2 = setup("/monster/greenslime_down_2", width, height);
        down1 = setup("/monster/greenslime_down_1", width, height);
        down2 = setup("/monster/greenslime_down_2", width, height);
        right1 = setup("/monster/greenslime_down_1", width, height);
        right2 = setup("/monster/greenslime_down_2", width, height);
    }

    // Overridden methods
    @Override
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // Random from 1 to 100

            if (i > 0 && i <= 25)
                direction = "up";
            if (i > 25 && i <= 50)
                direction = "left";
            if (i > 50 && i <= 75)
                direction = "down";
            if (i > 75 && i <= 100)
                direction = "right";

            actionLockCounter = 0;
        }

        int i = new Random().nextInt(100) + 1;
        if (i > 99 && projectile.isAlive() == false && shotAvailableCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gamePanel.getProjectileList().add(projectile);
            shotAvailableCounter = 0;
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gamePanel.getPlayer().getDirection();
    }

    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        if (i < 50)
            dropItem(new OBJ_Coin_Bronze(gamePanel));
        if (i >= 50 && i < 75)
            dropItem(new OBJ_Heart(gamePanel));
        if (i >= 75 && i < 100)
            dropItem(new OBJ_ManaCrystal(gamePanel));
    }
}