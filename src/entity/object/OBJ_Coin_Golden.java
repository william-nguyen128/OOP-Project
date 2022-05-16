package entity.object;

import entity.Entity;
import entity.Player;
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
        rightSprites[0] = setup("/objects/coin_golden", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    // Overridden method
    @Override
    public void use(Entity entity) {
        gamePanel.playSoundEffect(1);
        gamePanel.getUserInterface().addMessage("Coin +" + value);
        gamePanel.getPlayer().setCoin(gamePanel.getPlayer().getCoin() + value);

        gamePanel.getPlayer().setCoin2(gamePanel.getPlayer().getCoin2() +value);
        gamePanel.getCoin().saveCoin();
    }
}
