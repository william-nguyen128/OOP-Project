package entity.monster;

import java.util.Random;

import entity.object.OBJ_Coin_Bronze;
import entity.object.OBJ_Heart;
import entity.object.OBJ_ManaCrystal;
import main.GamePanel;

public class MON_GreenSlime extends Monster {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public MON_GreenSlime(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Green Slime";
        speed = 1;
        maxLife = 5;
        life = maxLife;
        attack = 5;
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

        up1 = setup("/monster/greenslime_down_1", width, height);
        up2 = setup("/monster/greenslime_down_2", width, height);
        left1 = setup("/monster/greenslime_down_1", width, height);
        left2 = setup("/monster/greenslime_down_2", width, height);
        down1 = setup("/monster/greenslime_down_1", width, height);
        down2 = setup("/monster/greenslime_down_2", width, height);
        right1 = setup("/monster/greenslime_down_1", width, height);
        right2 = setup("/monster/greenslime_down_2", width, height);

        up_left1 = setup("/monster/greenslime_down_1", width, height);
        up_left2 = setup("/monster/greenslime_down_2", width, height);
        down_left1 = setup("/monster/greenslime_down_1", width, height);
        down_left2 = setup("/monster/greenslime_down_2", width, height);
        down_right1 = setup("/monster/greenslime_down_1", width, height);
        down_right2 = setup("/monster/greenslime_down_2", width, height);
        up_right1 = setup("/monster/greenslime_down_1", width, height);
        up_right2 = setup("/monster/greenslime_down_2", width, height);
    }

    // Overridden methods
    @Override
    public void damageReaction() {
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