package entity.object;

import entity.Entity;
import entity.TYPE;
import main.GAME_STATE;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public OBJ_Potion_Red(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = TYPE.Consumable;
        name = "Red Potion";
        value = 5;
        down1 = setup("/objects/potion_red", gamePanel.getTileSize(), gamePanel.getTileSize());
        description = "[" + name + "]\nHeals you for " + value + " HP.";
    }

    // Overridden method
    @Override
    public void use(Entity entity) {
        gamePanel.gameState = GAME_STATE.Dialogue;
        gamePanel.getUserInterface().setCurrentDialogue("You drank the " + name + "!\n"
                + "Your HP has been recovered by " + value + " HP!");
        entity.setLife(entity.getLife() + value);
        gamePanel.playSoundEffect(2);
    }
}
