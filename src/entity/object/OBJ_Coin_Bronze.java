package entity.object;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public OBJ_Coin_Bronze(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = TYPE.PickupOnly;
        name = "Bronze Coin";
        value = 1;
        down1 = setup("/objects/coin_bronze", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    // Overridden method
    @Override
    public void use(Entity entity) {
        gamePanel.playSoundEffect(1);
        gamePanel.getUserInterface().addMessage("Coin +" + value);
        gamePanel.getPlayer().setCoin(gamePanel.getPlayer().getCoin() + value);
    }
}