package entity.object;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class OBJ_Coin_Golden extends Entity{
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public OBJ_Coin_Golden(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = TYPE.PickupOnly;
        name = "Golden Coin";
        value = 5;
        down1 = setup("/objects/coin_golden", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    // Overridden method
    @Override
    public void use(Entity entity) {
        gamePanel.playSoundEffect(1);
        gamePanel.getUserInterface().addMessage("Coin +" + value);
        coin+=value;
    }
}
