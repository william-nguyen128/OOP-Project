package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    // Constructor
    public OBJ_Key(GamePanel gamePanel) {
        super(gamePanel);

        name = "Key";
        down1 = setup("/objects/key", gamePanel.getTileSize(), gamePanel.getTileSize());
        description = "[" + name + "]\nA key. Can be used to\nopen doors.";
    }
}