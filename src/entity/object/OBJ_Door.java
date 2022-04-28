package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
    // Constructor
    public OBJ_Door(GamePanel gamePanel) {
        super(gamePanel);

        name = "Door";
        down1 = setup("/objects/door", gamePanel.getTileSize(), gamePanel.getTileSize());
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}