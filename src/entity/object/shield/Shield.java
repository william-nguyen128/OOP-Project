package entity.object.shield;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class Shield extends Entity {
    // Attribute
    protected int defenseValue;

    // Constructor
    public Shield(GamePanel gamePanel) {
        super(gamePanel);
        type = TYPE.Shield;
    }

    // Getter
    public int getDefenseValue() {
        return defenseValue;
    }
}