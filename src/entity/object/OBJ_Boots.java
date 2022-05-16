package entity.object;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class OBJ_Boots extends Entity {
    // Constructor
    public OBJ_Boots(GamePanel gamePanel) {
        super(gamePanel);

        type = TYPE.Consumable;
        name = "Boots";
        rightSprites[0] = setup("/objects/boots", gamePanel.getTileSize(), gamePanel.getTileSize());
    }
}