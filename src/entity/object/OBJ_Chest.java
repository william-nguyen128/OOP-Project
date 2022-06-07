package entity.object;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

import java.util.Random;

public class OBJ_Chest extends Entity {
    // Constructor
    public OBJ_Chest(GamePanel gamePanel) {
        super(gamePanel);
        type = TYPE.PickupOnly;

        name = "Chest";
        rightSprites[0] = setup("/objects/chest", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    public void use(Entity entity) {
        int i = new Random().nextInt(3) + 1;

        if (i == 1) {
            gamePanel.playSoundEffect(1);
            gamePanel.getUserInterface().addMessage("Coin +" + 50);
            gamePanel.getPlayer().setCoin(gamePanel.getPlayer().getCoin() + 50);
        }
        if (i == 2) {
            gamePanel.playSoundEffect(1);
            gamePanel.getUserInterface().addMessage("EXP +" + 10);
            gamePanel.getPlayer().setExp(gamePanel.getPlayer().getEXP() + 20);
            gamePanel.getPlayer().checkLevelUp();
        }
        if (i == 3) {
            gamePanel.playSoundEffect(1);
            gamePanel.getUserInterface().addMessage("Fully Healed");
            gamePanel.getPlayer().setLife(gamePanel.getPlayer().getMaxLife());
        }
    }
}