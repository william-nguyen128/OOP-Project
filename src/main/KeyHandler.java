package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    // Attributes
    private GamePanel gamePanel;
    private boolean upPressed, leftPressed, downPressed, rightPressed, interactPressed, castPressed;

    // DEBUG
    private boolean showDebugTexts = false;

    // Constructor
    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Title State
        if (gamePanel.gameState == GAME_STATE.Title)
            titleState(code);

        // Upgrade State
        else if (gamePanel.gameState == GAME_STATE.Upgrade)
            upgradeState(code);

        // Play State
        else if (gamePanel.gameState == GAME_STATE.Play)
            playState(code);

        // Pause State
        else if (gamePanel.gameState == GAME_STATE.Pause)
            pauseState(code);

        // Dialogue State
        else if (gamePanel.gameState == GAME_STATE.Dialogue)
            dialogueState(code);

        // Character State
        else if (gamePanel.gameState == GAME_STATE.Character)
            characterState(code);

        // Options State
        else if (gamePanel.gameState == GAME_STATE.Options)
            optionsState(code);

        // Game Over State
        else if (gamePanel.gameState == GAME_STATE.GameOver)
            gameOverState(code);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
            upPressed = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)
            leftPressed = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
            downPressed = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)
            rightPressed = false;
        if (code == KeyEvent.VK_K)
            castPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Unused
    }

    // Set keys' actions for each game state
    private void titleState(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gamePanel.getUserInterface().setCommandNum(gamePanel.getUserInterface().getCommandNum() - 1);
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUserInterface().getCommandNum() < 0)
                gamePanel.getUserInterface().setCommandNum(2);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gamePanel.getUserInterface().setCommandNum(gamePanel.getUserInterface().getCommandNum() + 1);
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUserInterface().getCommandNum() > 2)
                gamePanel.getUserInterface().setCommandNum(0);
        }
        if (code == KeyEvent.VK_E || code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
            if (gamePanel.getUserInterface().getCommandNum() == 0) {
                gamePanel.gameState = GAME_STATE.Play;
                gamePanel.playMusic(0);
            }
            if (gamePanel.getUserInterface().getCommandNum() == 1) {
                gamePanel.playSoundEffect(9);
                gamePanel.getUserInterface().setCommandNum(0);
                gamePanel.gameState = GAME_STATE.Upgrade;
            }
            if (gamePanel.getUserInterface().getCommandNum() == 2)
                System.exit(0);
        }
    }

    private void upgradeState(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if (upPressed == true) {
                gamePanel.getUserInterface().setCommandNum(gamePanel.getUserInterface().getCommandNum() - 1);
                gamePanel.playSoundEffect(9);
                if (gamePanel.getUserInterface().getCommandNum() < 0)
                    gamePanel.getUserInterface().setCommandNum(3);
            } else {
                gamePanel.playSoundEffect(9);
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if (downPressed == true) {
                gamePanel.getUserInterface().setCommandNum(gamePanel.getUserInterface().getCommandNum() + 1);
                gamePanel.playSoundEffect(9);
                if (gamePanel.getUserInterface().getCommandNum() > 3)
                    gamePanel.getUserInterface().setCommandNum(0);
            } else {
                gamePanel.playSoundEffect(9);
            }
        }
        if (code == KeyEvent.VK_E || code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUserInterface().getCommandNum() == 0) {
                interactPressed = true;
                gamePanel.getUserInterface().setRemaining(gamePanel.getCharacterUpgrade().getTotalCoin()
                        - gamePanel.getCharacterUpgrade().getStrengthCost());
                gamePanel.getCharacterUpgrade().upgradeStrength();
            }
            if (gamePanel.getUserInterface().getCommandNum() == 1) {
                interactPressed = true;
                gamePanel.getUserInterface().setRemaining(gamePanel.getCharacterUpgrade().getTotalCoin()
                        - gamePanel.getCharacterUpgrade().getSpeedCost());
                gamePanel.getCharacterUpgrade().upgradeSpeed();
            }
            if (gamePanel.getUserInterface().getCommandNum() == 2) {
                interactPressed = true;
                gamePanel.getUserInterface().setRemaining(gamePanel.getCharacterUpgrade().getTotalCoin()
                        - gamePanel.getCharacterUpgrade().getHpCost());
                gamePanel.getCharacterUpgrade().upgradeMaxLife();
            }
            if (gamePanel.getUserInterface().getCommandNum() == 3) {
                upPressed = false;
                downPressed = false;
                gamePanel.getUserInterface().setCommandNum(0);
                gamePanel.gameState = GAME_STATE.Title;
            }
        }
    }

    private void playState(int code) {
        // Vertical & Horizontal movements
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
            upPressed = true; // NORTH
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)
            leftPressed = true; // WEST
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
            downPressed = true; // SOUTH
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)
            rightPressed = true; // EAST

        // Other key bindings
        if (code == KeyEvent.VK_I)
            gamePanel.gameState = GAME_STATE.Character; // Character Screen/Inventory
        if (code == KeyEvent.VK_E || code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER)
            interactPressed = true; // Interacting
        if (code == KeyEvent.VK_K)
            castPressed = true; // (Spell) Casting
        if (code == KeyEvent.VK_P)
            gamePanel.gameState = GAME_STATE.Pause; // Pause the game
        if (code == KeyEvent.VK_ESCAPE)
            gamePanel.gameState = GAME_STATE.Options; // Option Screen

        // ============ DEBUG ONLY ============ //
        if (code == KeyEvent.VK_T)
            if (showDebugTexts == false)
                showDebugTexts = true;
            else
                showDebugTexts = false;
        if (code == KeyEvent.VK_R)
            gamePanel.getTileManager().loadMap("/maps/worldV3.txt");
        // ============ DEBUG ONLY ============ //
    }

    private void optionsState(int code) {
        if (code == KeyEvent.VK_ESCAPE)
            gamePanel.gameState = GAME_STATE.Play;
        if (code == KeyEvent.VK_E || code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER)
            interactPressed = true;

        int maxCommandNum = 0;
        switch (gamePanel.getUserInterface().getSubState()) {
            case 0:
                maxCommandNum = 5;
                break;
            case 3:
                maxCommandNum = 1;
                break;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gamePanel.getUserInterface().setCommandNum(gamePanel.getUserInterface().getCommandNum() - 1);
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUserInterface().getCommandNum() < 0)
                gamePanel.getUserInterface().setCommandNum(maxCommandNum);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gamePanel.getUserInterface().setCommandNum(gamePanel.getUserInterface().getCommandNum() + 1);
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUserInterface().getCommandNum() > maxCommandNum)
                gamePanel.getUserInterface().setCommandNum(0);
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (gamePanel.getUserInterface().getSubState() == 0) {
                if (gamePanel.getUserInterface().getCommandNum() == 1 && gamePanel.getMusic().getVolumeScale() > 0) {
                    gamePanel.getMusic().setVolumeScale(gamePanel.getMusic().getVolumeScale() - 1);
                    gamePanel.getMusic().checkVolume();
                    gamePanel.playSoundEffect(9);
                }
                if (gamePanel.getUserInterface().getCommandNum() == 2
                        && gamePanel.getSoundEffect().getVolumeScale() > 0) {
                    gamePanel.getSoundEffect().setVolumeScale(gamePanel.getSoundEffect().getVolumeScale() - 1);
                    gamePanel.playSoundEffect(9);
                }
            }
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (gamePanel.getUserInterface().getSubState() == 0) {
                if (gamePanel.getUserInterface().getCommandNum() == 1 && gamePanel.getMusic().getVolumeScale() < 5) {
                    gamePanel.getMusic().setVolumeScale(gamePanel.getMusic().getVolumeScale() + 1);
                    gamePanel.getMusic().checkVolume();
                    gamePanel.playSoundEffect(9);
                }
                if (gamePanel.getUserInterface().getCommandNum() == 2
                        && gamePanel.getSoundEffect().getVolumeScale() < 5) {
                    gamePanel.getSoundEffect().setVolumeScale(gamePanel.getSoundEffect().getVolumeScale() + 1);
                    gamePanel.playSoundEffect(9);
                }
            }
        }
    }

    private void pauseState(int code) {
        if (code == KeyEvent.VK_P)
            gamePanel.gameState = GAME_STATE.Play;
    }

    private void dialogueState(int code) {
        if (code == KeyEvent.VK_E || code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER)
            gamePanel.gameState = GAME_STATE.Play;
    }

    private void characterState(int code) {
        if (code == KeyEvent.VK_I)
            gamePanel.gameState = GAME_STATE.Play;
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
            if (gamePanel.getUserInterface().getSlotRow() != 0) {
                gamePanel.getUserInterface().setSlotRow(gamePanel.getUserInterface().getSlotRow() - 1);
                gamePanel.playSoundEffect(9);
            }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)
            if (gamePanel.getUserInterface().getSlotCol() != 0) {
                gamePanel.getUserInterface().setSlotCol(gamePanel.getUserInterface().getSlotCol() - 1);
                gamePanel.playSoundEffect(9);
            }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
            if (gamePanel.getUserInterface().getSlotRow() != 3) {
                gamePanel.getUserInterface().setSlotRow(gamePanel.getUserInterface().getSlotRow() + 1);
                gamePanel.playSoundEffect(9);
            }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)
            if (gamePanel.getUserInterface().getSlotCol() != 4) {
                gamePanel.getUserInterface().setSlotCol(gamePanel.getUserInterface().getSlotCol() + 1);
                gamePanel.playSoundEffect(9);
            }
        if (code == KeyEvent.VK_E || code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER)
            gamePanel.getPlayer().selectItem();
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_E || code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER)
            if (gamePanel.getUserInterface().getCommandNum() == 0) {
                gamePanel.playSoundEffect(9);
                gamePanel.getCharacterUpgrade()
                        .setTotalCoin(gamePanel.getCharacterUpgrade().getTotalCoin() + gamePanel.getPlayer().getCoin());
                gamePanel.gameState = GAME_STATE.Title;
                gamePanel.restart();
            }
    }

    // Getters & Setters
    public boolean isShowDebugTexts() {
        return showDebugTexts;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isInteractPressed() {
        return interactPressed;
    }

    public void setInteractPressed(boolean interactPressed) {
        this.interactPressed = interactPressed;
    }

    public boolean isCastPressed() {
        return castPressed;
    }
}