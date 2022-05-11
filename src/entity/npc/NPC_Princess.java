package entity.npc;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class NPC_Princess extends Entity {
    // Constructor
    public NPC_Princess(GamePanel gamePanel) {
        super(gamePanel);

        type = TYPE.NPC;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getPrincessImage();
        setDialogue();
    }

    // Get Image method
    private void getPrincessImage() {
        int width = gamePanel.getTileSize();
        int height = gamePanel.getTileSize();

        for (int i = 0; i < 2; i++) {
            leftSprites[i] = setup("/npc/oldman_left_" + (i + 1), width, height);
            rightSprites[i] = setup("/npc/oldman_right_" + (i + 1), width, height);
        }
    }

    // Set Dialogue method
    private void setDialogue() {
        dialogues[0] = "Hello, o brave warrior!\nI am the princess of the XXX kingdom.";
        dialogues[1] = "Thank you for rescuing me from these monsters.";
    }

    // Overridden method
    @Override
    public void speak() {
        super.speak();
    }
}