package entity.monster;

import java.util.Random;

import entity.object.OBJ_Potion_Red;
import entity.object.coin.COIN_Bronze;
import entity.object.coin.COIN_Gold;
import entity.object.coin.COIN_Silver;
import main.GamePanel;

public class MON_GreenSlime extends Monster {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public MON_GreenSlime(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Green Slime";
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 50;
        life = maxLife;
        attack = 1;
        defense = 0;
        exp = 2;

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

        for (int i = 0; i < 2; i++) {
            leftSprites[i] = setup("/monster/greenslime_down_" + (i + 1), width, height);
            rightSprites[i] = setup("/monster/greenslime_down_" + (i + 1), width, height);
        }
    }

    // Overridden method
    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        if (i < 20)
            dropItem(new OBJ_Potion_Red(gamePanel));
        if (i < 80)
            dropItem(new COIN_Bronze(gamePanel));
        if (i < 85 && i >= 50)
            dropItem(new COIN_Silver(gamePanel));
        if (i < 99 && i >= 85)
            dropItem(new COIN_Gold(gamePanel));
        if (i < 20)
            dropItem(new OBJ_Potion_Red(gamePanel));
    }
}