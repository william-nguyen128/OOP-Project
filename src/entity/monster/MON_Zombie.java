package entity.monster;

import java.util.Random;

import entity.object.coin.COIN_Bronze;
import main.GamePanel;

public class MON_Zombie extends Monster {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public MON_Zombie(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "ANVC";
        speed = 3;
        maxLife = 10;
        life = maxLife;
        attack = 1;
        defense = 0;
        exp = 3;

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
        int i = new Random().nextInt(100) + 1;

        if (i < 50)
            dropItem(new COIN_Bronze(gamePanel));
    }
}