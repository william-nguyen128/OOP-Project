package entity.object;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public OBJ_Heart(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = TYPE.PickupOnly;
        name = "Heart";
        value = 2;
        down1 = setup("/objects/heart_full", gamePanel.getTileSize(), gamePanel.getTileSize());
        image = setup("/objects/heart_full", gamePanel.getTileSize(), gamePanel.getTileSize());
        image2 = setup("/objects/heart_half", gamePanel.getTileSize(), gamePanel.getTileSize());
        image3 = setup("/objects/heart_blank", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    // Overridden methods
    @Override
    public void use(Entity entity) {
        gamePanel.playSoundEffect(2);
        gamePanel.getUserInterface().addMessage("HP +" + value);
        entity.setLife(entity.getLife() + value);
    }
}