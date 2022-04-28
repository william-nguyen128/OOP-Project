package entity.npc;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class NPC_OldMan extends Entity {
    // Constructor
    public NPC_OldMan(GamePanel gamePanel) {
        super(gamePanel);
        direction = "down";
        speed = 1;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getOldManImage();
        setDialogue();
    }

    // Get Image method
    private void getOldManImage() {
        int width = gamePanel.getTileSize();
        int height = gamePanel.getTileSize();

        up1 = setup("/npc/oldman_up_1", width, height);
        up2 = setup("/npc/oldman_up_2", width, height);
        left1 = setup("/npc/oldman_left_1", width, height);
        left2 = setup("/npc/oldman_left_2", width, height);
        down1 = setup("/npc/oldman_down_1", width, height);
        down2 = setup("/npc/oldman_down_2", width, height);
        right1 = setup("/npc/oldman_right_1", width, height);
        right2 = setup("/npc/oldman_right_2", width, height);
    }

    // Get Dialogue method
    private void setDialogue() {
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "So you've come to this island to find\nthe treasure?";
        dialogues[2] = "I used to be a great wizard, but now...\nI'm a bit too old to take on an adventure.";
        dialogues[3] = "Well, good luck on your journey.";
    }

    // Overridden methods
    @Override
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // Random from 1 to 100

            if (i > 0 && i <= 25)
                direction = "up";
            if (i > 25 && i <= 50)
                direction = "left";
            if (i > 50 && i <= 75)
                direction = "down";
            if (i > 75 && i <= 100)
                direction = "right";

            actionLockCounter = 0;
        }
    }

    @Override
    public void speak() {
        super.speak();
    }
}