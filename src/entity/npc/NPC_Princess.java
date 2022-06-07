package entity.npc;

import main.GamePanel;

public class NPC_Princess extends NPC {
    // Constructor
    public NPC_Princess(GamePanel gamePanel) {
        super(gamePanel);

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
            leftSprites[i] = setup("/npc/princess_lol", width, height);
            rightSprites[i] = setup("/npc/princess_lol", width, height);
        }
    }

    // Set Dialogue method
    private void setDialogue() {
        dialogues[0] = "Sup bro! Tks 4 saving meh frum thoose monstas.";
        dialogues[1] = "Where da princess? Why need one when u have meh?";
        dialogues[2] = "Besides, I dunno if u can even get a gurl with dat face of urs.";
        dialogues[3] = "Anyweis, why don't cha stay here with meh?\nI'd <3 sum cuddling (U w U)";
    }
}