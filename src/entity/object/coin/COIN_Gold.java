package entity.object.coin;

import entity.TYPE;
import main.GamePanel;

public class COIN_Gold extends Coin {
    // Constructor
    public COIN_Gold(GamePanel gamePanel) {
        super(gamePanel);

        type = TYPE.PickupOnly;
        name = "Golden Coin";
        value = 5;
        rightSprites[0] = setup("/objects/coin_gold", gamePanel.getTileSize(), gamePanel.getTileSize());
    }
}
