package entity.object;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    // Constructor
    public OBJ_Axe(GamePanel gamePanel) {
        super(gamePanel);

        type = TYPE.Axe;
        name = "Woodcutter's Axe";
        down1 = setup("/objects/axe", gamePanel.getTileSize(), gamePanel.getTileSize());
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nA bit rusty but can\nstill cut some trees.";
    }
}