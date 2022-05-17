package entity.coin;

import entity.TYPE;
import main.GamePanel;

public class COIN_Silver extends Coin {
    // Constructor
    public COIN_Silver(GamePanel gamePanel) {
        super(gamePanel);

        type = TYPE.PickupOnly;
        name = "Silver Coin";
        value = 2;
        rightSprites[0] = setup("/objects/coin_silver", gamePanel.getTileSize(), gamePanel.getTileSize());
    }
}
