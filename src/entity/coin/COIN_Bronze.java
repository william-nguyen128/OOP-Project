package entity.coin;

import entity.TYPE;
import main.GamePanel;

public class COIN_Bronze extends Coin {
    // Constructor
    public COIN_Bronze(GamePanel gamePanel) {
        super(gamePanel);

        type = TYPE.PickupOnly;
        name = "Bronze Coin";
        value = 1;
        rightSprites[0] = setup("/objects/coin_bronze", gamePanel.getTileSize(), gamePanel.getTileSize());
    }
}