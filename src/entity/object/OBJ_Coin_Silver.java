package entity.object;

import entity.Entity;
import entity.Player;
import entity.TYPE;
import main.GamePanel;

public class OBJ_Coin_Silver extends Entity{
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public OBJ_Coin_Silver(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = TYPE.PickupOnly;
        name = "Silver Coin";
        value = 2;
        down1 = setup("/objects/coin_silver", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    // Overridden method
    @Override
    public void use(Entity entity) {
        gamePanel.playSoundEffect(1);
        gamePanel.getUserInterface().addMessage("Coin +" + value);
        gamePanel.getPlayer().setCoin(gamePanel.getPlayer().getCoin() + value);
        gamePanel.getCoin().saveCoin();
    }
}
