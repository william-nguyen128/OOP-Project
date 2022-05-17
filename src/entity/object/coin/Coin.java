package entity.object.coin;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class Coin extends Entity {
    // Constructor
    public Coin(GamePanel gamePanel) {
        super(gamePanel);
        type = TYPE.PickupOnly;
    }

    // Overridden method
    @Override
    public void use(Entity entity) {
        gamePanel.playSoundEffect(1);
        gamePanel.getUserInterface().addMessage("Coin +" + value);
        gamePanel.getPlayer().setCoin(gamePanel.getPlayer().getCoin() + value);
    }
}