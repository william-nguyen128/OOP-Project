package entity.object;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public OBJ_ManaCrystal(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = TYPE.PickupOnly;
        name = "Mana Crystal";
        value = 1;
        down1 = setup("/objects/manacrystal_full", gamePanel.getTileSize(), gamePanel.getTileSize());
        image = setup("/objects/manacrystal_full", gamePanel.getTileSize(), gamePanel.getTileSize());
        image2 = setup("/objects/manacrystal_blank", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    // Overridden method
    @Override
    public void use(Entity entity) {
        gamePanel.playSoundEffect(2);
        gamePanel.getUserInterface().addMessage("Mana +" + value);
        entity.setMana(entity.getMana() + value);
    }
}