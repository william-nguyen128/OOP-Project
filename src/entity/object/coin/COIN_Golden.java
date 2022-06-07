package entity.object.coin;

import entity.TYPE;
import main.GamePanel;

public class COIN_Golden extends Coin {
    // Constructor
    public COIN_Golden(GamePanel gamePanel) {
        super(gamePanel);

        type = TYPE.PickupOnly;
        name = "Golden Coin";
        value = 5;
        rightSprites[0] = setup("/objects/coin_golden", gamePanel.getTileSize(), gamePanel.getTileSize());
    }
}
