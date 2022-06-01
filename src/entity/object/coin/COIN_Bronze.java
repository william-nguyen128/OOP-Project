package entity.object.coin;

import main.GamePanel;

public class COIN_Bronze extends Coin {
    // Constructor
    public COIN_Bronze(GamePanel gamePanel) {
        super(gamePanel);

        name = "Bronze Coin";
        value = 1;
        rightSprites[0] = setup("/objects/coin_bronze", gamePanel.getTileSize(), gamePanel.getTileSize());
    }
}