package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
    // Constructor
    public OBJ_Chest(GamePanel gamePanel) {
        super(gamePanel);

        name = "Chest";
        rightSprites[0] = setup("/objects/chest", gamePanel.getTileSize(), gamePanel.getTileSize());
    }
}