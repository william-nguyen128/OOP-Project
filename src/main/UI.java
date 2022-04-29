package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;

// import java.text.DecimalFormat;
import java.util.ArrayList;

import entity.Entity;
import entity.object.OBJ_Heart;
import entity.object.OBJ_ManaCrystal;

public class UI {
    private GamePanel gamePanel;
    private Graphics2D g2d;
    private Font maruMonica_40;
    private BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;
    // private boolean messageOn = false;
    private ArrayList<String> message = new ArrayList<>();
    private ArrayList<Integer> messageCounter = new ArrayList<>();
    // private boolean gameFinished = false;
    private String currentDialogue = "";
    private int commandNum = 0;
    private int slotCol = 0;
    private int slotRow = 0;
    private int subState = 0;

    // DEBUG ONLY
    // private double playTime;
    // private DecimalFormat dFormat = new DecimalFormat("#0.00");

    // Constructor
    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica_40 = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // HUD
        Entity heart = new OBJ_Heart(gamePanel);
        heart_full = heart.getImage();
        heart_half = heart.getImage2();
        heart_blank = heart.getImage3();

        Entity crystal = new OBJ_ManaCrystal(gamePanel);
        crystal_full = crystal.getImage();
        crystal_blank = crystal.getImage2();
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    // Draw all screens
    public void draw(Graphics2D g2d) {
        this.g2d = g2d;

        g2d.setFont(maruMonica_40);
        g2d.setColor(Color.WHITE);

        // Title State
        if (gamePanel.gameState == GAME_STATE.Title) {
            drawTitleScreen();
        }

        // Play State
        if (gamePanel.gameState == GAME_STATE.Play) {
            drawPlayerLife();
            drawPlayerMana();
            drawMessage();
        }

        // Pause State
        if (gamePanel.gameState == GAME_STATE.Pause) {
            drawPlayerLife();
            drawPlayerMana();
            drawPauseScreen();
        }

        // Dialogue State
        if (gamePanel.gameState == GAME_STATE.Dialogue) {
            drawPlayerLife();
            drawPlayerMana();
            drawDialogueScreen();
        }

        // Character State
        if (gamePanel.gameState == GAME_STATE.Character) {
            drawCharacterScreen();
            drawInventoryScreen();
        }

        // Options State
        if (gamePanel.gameState == GAME_STATE.Options)
            drawOptionsScreen();
    }

    // Methods to draw each screen
    private void drawTitleScreen() {
        g2d.setColor(new Color(0, 0, 0));
        g2d.fillRect(0, 0, gamePanel.getScreenWidth(), gamePanel.getScreenHeight());

        // Title Name
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Princess Rescue";
        int x = getXForCenteredText(text);
        int y = gamePanel.getTileSize() * 3;
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();

        // Shadow
        g2d.setColor(Color.GRAY);
        g2d.drawString(text, x + 5, y + 5);

        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);

        // Image
        x = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() * 2) / 2;
        y += gamePanel.getTileSize() * 2;
        g2d.drawImage(gamePanel.getPlayer().getDown1(), x, y, gamePanel.getTileSize() * 2, gamePanel.getTileSize() * 2,
                null);

        // Menu
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXForCenteredText(text);
        y += gamePanel.getTileSize() * 3.5;
        length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        g2d.drawString(text, x, y);
        if (commandNum == 0) {
            g2d.drawString(">", x - gamePanel.getTileSize(), y);
            g2d.drawString("<", x + length + gamePanel.getTileSize() / 2, y);
        }

        text = "LOAD GAME";
        x = getXForCenteredText(text);
        y += gamePanel.getTileSize();
        length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        g2d.drawString(text, x, y);
        if (commandNum == 1) {
            g2d.drawString(">", x - gamePanel.getTileSize(), y);
            g2d.drawString("<", x + length + gamePanel.getTileSize() / 2, y);
        }

        text = "QUIT";
        x = getXForCenteredText(text);
        y += gamePanel.getTileSize();
        length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        g2d.drawString(text, x, y);
        if (commandNum == 2) {
            g2d.drawString(">", x - gamePanel.getTileSize(), y);
            g2d.drawString("<", x + length + gamePanel.getTileSize() / 2, y);
        }
    }

    private void drawPlayerLife() {
        int x = gamePanel.getTileSize() / 2;
        int y = gamePanel.getTileSize() / 2;
        int i = 0;

        // Draw MAX LIFE
        while (i < gamePanel.getPlayer().getMaxLife() / 2) {
            g2d.drawImage(heart_blank, x, y, null);
            i++;
            x += gamePanel.getTileSize();
        }

        // Reset
        x = gamePanel.getTileSize() / 2;
        y = gamePanel.getTileSize() / 2;
        i = 0;

        // Draw CURRENT LIFE
        while (i < gamePanel.getPlayer().getLife()) {
            g2d.drawImage(heart_half, x, y, null);
            i++;
            if (i < gamePanel.getPlayer().getLife())
                g2d.drawImage(heart_full, x, y, null);
            i++;
            x += gamePanel.getTileSize();
        }
    }

    private void drawPlayerMana() {
        int x = gamePanel.getTileSize() / 2 - 5;
        int y = (int) (gamePanel.getTileSize() * 1.5);
        int i = 0;

        // Draw MAX MANA
        while (i < gamePanel.getPlayer().getMaxMana()) {
            g2d.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }

        // Reset
        x = gamePanel.getTileSize() / 2 - 5;
        y = (int) (gamePanel.getTileSize() * 1.5);
        i = 0;

        // Draw CURRENT MANA
        while (i < gamePanel.getPlayer().getMana()) {
            g2d.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }

    private void drawPauseScreen() {
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gamePanel.getScreenHeight() / 2;

        g2d.drawString(text, x, y);
    }

    private void drawDialogueScreen() {
        // Window
        int x = gamePanel.getTileSize() * 2;
        int y = gamePanel.getTileSize() / 2;
        int width = gamePanel.getScreenWidth() - gamePanel.getTileSize() * 4;
        int height = gamePanel.getTileSize() * 4;

        drawSubWindow(x, y, width, height);

        x += gamePanel.getTileSize();
        y += gamePanel.getTileSize();
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 28F));
        for (String line : currentDialogue.split("\n")) {
            g2d.drawString(line, x, y);
            y += 40;
        }
    }

    private void drawCharacterScreen() {
        final int frameX = gamePanel.getTileSize() * 2;
        final int frameY = gamePanel.getTileSize();
        final int frameWidth = gamePanel.getTileSize() * 5;
        final int frameHeight = gamePanel.getTileSize() * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Texts
        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.getTileSize();
        final int lineHeight = 35;

        // Names
        g2d.drawString("Level", textX, textY);
        textY += lineHeight;
        g2d.drawString("Life", textX, textY);
        textY += lineHeight;
        g2d.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2d.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2d.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2d.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2d.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2d.drawString("EXP", textX, textY);
        textY += lineHeight;
        g2d.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2d.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        g2d.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2d.drawString("Shield", textX, textY);

        // Values
        int tailX = frameX + frameWidth - 30;
        textY = frameY + gamePanel.getTileSize(); // Reset textY
        String value;

        value = String.valueOf(gamePanel.getPlayer().getLevel());
        textX = getXForRightAlignedText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getLife() + "/" + gamePanel.getPlayer().getMaxLife());
        textX = getXForRightAlignedText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getMana() + "/" + gamePanel.getPlayer().getMaxMana());
        textX = getXForRightAlignedText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getStrength());
        textX = getXForRightAlignedText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getDexterity());
        textX = getXForRightAlignedText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getAttack());
        textX = getXForRightAlignedText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getDefense());
        textX = getXForRightAlignedText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getEXP());
        textX = getXForRightAlignedText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getNextLevelEXP());
        textX = getXForRightAlignedText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getCoin());
        textX = getXForRightAlignedText(value, tailX);
        g2d.drawString(value, textX, textY);
        textY += lineHeight;

        g2d.drawImage(gamePanel.getPlayer().getCurrentWeapon().getDown1(), tailX - gamePanel.getTileSize(), textY - 24,
                null);
        textY += gamePanel.getTileSize();

        g2d.drawImage(gamePanel.getPlayer().getCurrentShield().getDown1(), tailX - gamePanel.getTileSize(), textY - 24,
                null);
    }

    private void drawInventoryScreen() {
        final int frameX = gamePanel.getTileSize() * 12;
        final int frameY = gamePanel.getTileSize();
        final int frameWidth = gamePanel.getTileSize() * 6;
        final int frameHeight = gamePanel.getTileSize() * 5;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Item slots
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gamePanel.getTileSize() + 3;

        // Draw Player's Items
        for (int i = 0; i < gamePanel.getPlayer().getInventory().size(); i++) {
            // Equip Cursor
            if (gamePanel.getPlayer().getInventory().get(i) == gamePanel.getPlayer().getCurrentWeapon() ||
                    gamePanel.getPlayer().getInventory().get(i) == gamePanel.getPlayer().getCurrentShield()) {
                g2d.setColor(new Color(240, 190, 90));
                g2d.fillRoundRect(slotX, slotY, gamePanel.getTileSize(), gamePanel.getTileSize(), 10, 10);
            }

            g2d.drawImage(gamePanel.getPlayer().getInventory().get(i).getDown1(), slotX, slotY, null);

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // Cursor
        int cursorX = slotXStart + slotSize * slotCol;
        int cursorY = slotYStart + slotSize * slotRow;
        int cursorWidth = gamePanel.getTileSize();
        int cursorHeight = gamePanel.getTileSize();

        // Draw Cursor
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // Description frame
        int descFrameX = frameX;
        int descFrameY = frameY + frameHeight;
        int descFrameWidth = frameWidth;
        int descFrameHeight = gamePanel.getTileSize() * 3;

        // Draw description texts
        int descTextX = descFrameX + 20;
        int descTextY = descFrameY + gamePanel.getTileSize();
        g2d.setFont(g2d.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlot();
        if (itemIndex < gamePanel.getPlayer().getInventory().size()) {
            drawSubWindow(descFrameX, descFrameY, descFrameWidth, descFrameHeight);

            for (String line : gamePanel.getPlayer().getInventory().get(itemIndex).getDescription().split("\n")) {
                g2d.drawString(line, descTextX, descTextY);
                descTextY += 32;
            }
        }
    }

    private void drawOptionsScreen() {
        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(32F));

        // Sub window
        int frameX = gamePanel.getTileSize() * 6;
        int frameY = gamePanel.getTileSize();
        int frameWidth = gamePanel.getTileSize() * 8;
        int frameHeight = gamePanel.getTileSize() * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                options_fullScreenNotification(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                options_endGameConfirmation(frameX, frameY);
                break;
        }

        gamePanel.getKeyHandler().setInteractPressed(false);
    }

    private void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Options";
        textX = getXForCenteredText(text);
        textY = frameY + gamePanel.getTileSize();
        g2d.drawString(text, textX, textY);

        // Full Screen On/Off
        text = "Full Screen";
        textX = frameX + gamePanel.getTileSize();
        textY += gamePanel.getTileSize() * 2;
        g2d.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2d.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isInteractPressed() == true) {
                if (gamePanel.isFullScreenOn() == false)
                    gamePanel.setFullScreenOn(true);
                else if (gamePanel.isFullScreenOn() == true)
                    gamePanel.setFullScreenOn(false);
                subState = 1;
            }
        }

        // Music
        text = "Music";
        textY += gamePanel.getTileSize();
        g2d.drawString(text, textX, textY);
        if (commandNum == 1)
            g2d.drawString(">", textX - 25, textY);

        // Sound Effects
        text = "Sound Effects";
        textY += gamePanel.getTileSize();
        g2d.drawString(text, textX, textY);
        if (commandNum == 2)
            g2d.drawString(">", textX - 25, textY);

        // Controls
        text = "Controls";
        textY += gamePanel.getTileSize();
        g2d.drawString(text, textX, textY);
        if (commandNum == 3) {
            g2d.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isInteractPressed() == true) {
                subState = 2;
                commandNum = 0;
            }
        }

        // End Game
        text = "End Game";
        textY += gamePanel.getTileSize();
        g2d.drawString(text, textX, textY);
        if (commandNum == 4) {
            g2d.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isInteractPressed() == true) {
                subState = 3;
                commandNum = 0;
            }
        }

        // Back
        text = "Back";
        textY += gamePanel.getTileSize() * 2;
        g2d.drawString(text, textX, textY);
        if (commandNum == 5) {
            g2d.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isInteractPressed() == true) {
                gamePanel.gameState = GAME_STATE.Play;
                commandNum = 0;
            }
        }

        // Full Screen checkbox
        textX = frameX + (int) (gamePanel.getTileSize() * 4.5);
        textY = frameY + gamePanel.getTileSize() * 2 + 24;
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(textX, textY, 24, 24);
        if (gamePanel.isFullScreenOn() == true)
            g2d.fillRect(textX, textY, 24, 24);

        // Music Volume
        textY += gamePanel.getTileSize();
        g2d.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gamePanel.getMusic().getVolumeScale();
        g2d.fillRect(textX, textY, volumeWidth, 24);

        // Sound Effect Volume
        textY += gamePanel.getTileSize();
        g2d.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gamePanel.getSoundEffect().getVolumeScale();
        g2d.fillRect(textX, textY, volumeWidth, 24);

        // Saving the game's configurations
        gamePanel.getConfig().saveConfig();
    }

    private void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gamePanel.getTileSize() - 15;
        int textY = frameY + gamePanel.getTileSize() * 3;

        currentDialogue = "The change will take effect\nafter you restart the game.";
        for (String line : currentDialogue.split("\n")) {
            g2d.drawString(line, textX, textY);
            textY += 40;
        }

        // Back
        textX = frameX + gamePanel.getTileSize();
        textY = frameY + gamePanel.getTileSize() * 9;
        g2d.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2d.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isInteractPressed() == true)
                subState = 0;
        }
    }

    private void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Control";
        textX = getXForCenteredText(text);
        textY = frameY + gamePanel.getTileSize();
        g2d.drawString(text, textX, textY);

        textX = frameX + gamePanel.getTileSize();
        textY += gamePanel.getTileSize();
        g2d.drawString("Move", textX, textY);
        textY += gamePanel.getTileSize();
        g2d.drawString("Confirm/Interact", textX, textY);
        textY += gamePanel.getTileSize();
        g2d.drawString("Attack", textX, textY);
        textY += gamePanel.getTileSize();
        g2d.drawString("Shoot/Cast", textX, textY);
        textY += gamePanel.getTileSize();
        g2d.drawString("Character Screen", textX, textY);
        textY += gamePanel.getTileSize();
        g2d.drawString("Pause", textX, textY);
        textY += gamePanel.getTileSize();
        g2d.drawString("Options", textX, textY);
        textY += gamePanel.getTileSize();

        textX = frameX + gamePanel.getTileSize() * 6;
        textY = frameY + gamePanel.getTileSize() * 2;
        g2d.drawString("WASD", textX, textY);
        textY += gamePanel.getTileSize();
        g2d.drawString("E", textX, textY);
        textY += gamePanel.getTileSize();
        g2d.drawString("J", textX, textY);
        textY += gamePanel.getTileSize();
        g2d.drawString("K", textX, textY);
        textY += gamePanel.getTileSize();
        g2d.drawString("I", textX, textY);
        textY += gamePanel.getTileSize();
        g2d.drawString("P", textX, textY);
        textY += gamePanel.getTileSize();
        g2d.drawString("ESC", textX, textY);
        textY += gamePanel.getTileSize();

        // Back
        textX = frameX + gamePanel.getTileSize();
        textY = frameY + gamePanel.getTileSize() * 9;
        g2d.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2d.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isInteractPressed() == true) {
                subState = 0;
                commandNum = 3;
            }
        }
    }

    private void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gamePanel.getTileSize();
        int textY = frameY + gamePanel.getTileSize() * 3;

        currentDialogue = "Quit the game and\nreturn to the tile screen?";

        for (String line : currentDialogue.split("\n")) {
            g2d.drawString(line, textX, textY);
            textY += 40;
        }

        // No
        String text = "No";
        textX = getXForCenteredText(text);
        textY += gamePanel.getTileSize() * 3;
        g2d.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2d.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isInteractPressed() == true) {
                subState = 0;
                commandNum = 4;
            }
        }

        // Yes
        text = "Yes";
        textX = getXForCenteredText(text);
        textY += gamePanel.getTileSize();
        g2d.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2d.drawString(">", textX - 25, textY);
            if (gamePanel.getKeyHandler().isInteractPressed() == true) {
                subState = 0;
                gamePanel.stopMusic();
                gamePanel.gameState = GAME_STATE.Title;
            }
        }
    }

    public int getItemIndexOnSlot() {
        int itemIndex = slotCol + slotRow * 5;
        return itemIndex;
    }

    // Private (Internal) methods
    private void drawMessage() {
        int messageX = gamePanel.getTileSize() / 2;
        int messageY = gamePanel.getTileSize() * 4;
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 20F));

        for (int i = 0; i < message.size(); i++)
            if (message.get(i) != null) {
                g2d.setColor(Color.BLACK);
                g2d.drawString(message.get(i), messageX + 2, messageY + 2);
                g2d.setColor(Color.WHITE);
                g2d.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
    }

    private void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2d.setColor(c);
        g2d.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2d.setColor(c);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    private int getXForCenteredText(String text) {
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = gamePanel.getScreenWidth() / 2 - length / 2;
        return x;
    }

    private int getXForRightAlignedText(String text, int tailX) {
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = tailX - length;
        return x;
    }

    // Getters & Setters
    public int getSubState() {
        return subState;
    }

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

    public int getCommandNum() {
        return commandNum;
    }

    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
    }

    public int getSlotCol() {
        return slotCol;
    }

    public void setSlotCol(int slotCol) {
        this.slotCol = slotCol;
    }

    public int getSlotRow() {
        return slotRow;
    }

    public void setSlotRow(int slotRow) {
        this.slotRow = slotRow;
    }
}