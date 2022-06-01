package entity.object.shield;

import main.GamePanel;

public class SHIELD_Wood extends Shield {
    // Constructor
    public SHIELD_Wood(GamePanel gamePanel) {
        super(gamePanel);

        name = "Wooden Shield";
        rightSprites[0] = setup("/objects/shield_wood", gamePanel.getTileSize(), gamePanel.getTileSize());
        defenseValue = 1;
        description = "[" + name + "]\nA wooden shield.\nYou made it yourself.";
    }
}