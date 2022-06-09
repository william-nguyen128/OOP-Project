package entity.monster;

// import java.util.Random;

import entity.object.OBJ_Chest;
import main.GamePanel;

public class MON_Zombie extends Monster {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public MON_Zombie(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Zombie BOSS";
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 200;
        life = maxLife;
        attack = 20;
        defense = 0;
        exp = 100;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getZombieImage();
    }

    // Get images
    private void getZombieImage() {
        int width = gamePanel.getTileSize();
        int height = gamePanel.getTileSize();

        for (int i = 0; i < 2; i++) {
            leftSprites[i] = setup("/monster/zombie_left", width, height);
            rightSprites[i] = setup("/monster/zombie_right", width, height);
        }
    }

    // Overridden method
    @Override
    public void checkDrop() {
        dropItem(new OBJ_Chest(gamePanel));
        dropItem(new OBJ_Chest(gamePanel));
        dropItem(new OBJ_Chest(gamePanel));
    }
}