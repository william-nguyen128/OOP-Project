package entity.object.weapon;

import java.awt.Rectangle;

import main.GamePanel;

public class WPN_Whip extends Weapon {
    // Constructor
    public WPN_Whip(GamePanel gamePanel) {
        super(gamePanel);

        name = "Whip";
        rightSprites[0] = setup("/objects/weapon/whip", gamePanel.getTileSize(), gamePanel.getTileSize());
        attack = 10;
        solidArea = new Rectangle(0, 0, gamePanel.getTileSize() * 4, gamePanel.getTileSize() * 2);
        description = "[" + name + "]\n"
                + "Damage: " + attack + "\n"
                + "Slash horizontally.";

        setWhipAttackImage();
    }

    // Set Whip's attacking sprites
    private void setWhipAttackImage() {
        int width = solidArea.width;
        int height = solidArea.height;

        for (int i = 0; i < 6; i++) {
            attackLeft[i] = setup("/objects/weapon/slash_left_" + (i + 1), width, height);
            attackRight[i] = setup("/objects/weapon/slash_right_" + (i + 1), width, height);
        }
    }
}