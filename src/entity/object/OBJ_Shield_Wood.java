package entity.object;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    // Constructor
    public OBJ_Shield_Wood(GamePanel gamePanel) {
        super(gamePanel);

        type = TYPE.Shield;
        name = "Wooden Shield";
        down1 = setup("/objects/shield_wood", gamePanel.getTileSize(), gamePanel.getTileSize());
        defenseValue = 1;
        description = "[" + name + "]\nA wooden shield.\nYou made it yourself.";
    }
}
