package entity.object;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {
    // Constructor
    public OBJ_Sword_Normal(GamePanel gamePanel) {
        super(gamePanel);

        type = TYPE.Sword;
        name = "Normal Sword";
        down1 = setup("/objects/sword_normal", gamePanel.getTileSize(), gamePanel.getTileSize());
        attackValue = 2;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn old sword, from your\nold man's weaponry.";
    }
}