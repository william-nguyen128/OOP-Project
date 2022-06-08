package entity.monster;

import java.util.Random;

import entity.object.OBJ_Chest;
import entity.object.coin.COIN_Bronze;
import entity.object.coin.COIN_Gold;
import entity.object.coin.COIN_Silver;
import main.GamePanel;

public class MON_Ghost extends Monster {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public MON_Ghost(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Ghost";
        speed = 3;
        maxLife = 70;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 4;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getCrowImage();
    }

    // Get images
    private void getCrowImage() {
        int width = gamePanel.getTileSize();
        int height = gamePanel.getTileSize();

        for (int i = 0; i < 2; i++) {
            leftSprites[i] = setup("/monster/bat_left", width, height);
            rightSprites[i] = setup("/monster/bat_right", width, height);
        }
    }

    // Overridden method
    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        if (i < 60)
            dropItem(new COIN_Bronze(gamePanel));
        if (i < 85 && i >= 50)
            dropItem(new COIN_Silver(gamePanel));
        if (i < 99 && i >= 65)
            dropItem(new COIN_Gold(gamePanel));
        if (i < 17)
            dropItem(new OBJ_Chest(gamePanel));
    }
}