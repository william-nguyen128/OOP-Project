package main;

public class EventHandler {
    // Attributes
    private GamePanel gamePanel;
    private EventRect eventRect[][];
    private int previousEventX, previousEventY;
    private boolean canTouchEvent = false;

    // Constructor
    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        eventRect = new EventRect[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];

        int col = 0;
        int row = 0;
        while (col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gamePanel.getMaxWorldCol()) {
                col = 0;
                row++;
            }
        }
    }

    // Check for events
    public void checkEvent() {
        // Check if getPlayer()'s character is more than 1 tile away from the last event
        int xDistance = Math.abs(gamePanel.getPlayer().getWorldX() - previousEventX);
        int yDistance = Math.abs(gamePanel.getPlayer().getWorldY() - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gamePanel.getTileSize())
            canTouchEvent = true;

        if (canTouchEvent == true) {
            if (hit(27, 16, "any") == true)
                damagePit(27, 16, GAME_STATE.Dialogue);
            if (hit(22, 7, "any") == true)
                teleport(22, 7, GAME_STATE.Dialogue);
            if (hit(23, 12, "up") == true)
                healingPool(23, 12, GAME_STATE.Dialogue);
        }
    }

    // Set a triggering point for an event on the map
    private boolean hit(int col, int row, String requiredDirection) {
        boolean hit = false;

        gamePanel.getPlayer().getSolidArea().x = gamePanel.getPlayer().getWorldX()
                + gamePanel.getPlayer().getSolidArea().x;
        gamePanel.getPlayer().getSolidArea().y = gamePanel.getPlayer().getWorldY()
                + gamePanel.getPlayer().getSolidArea().y;
        eventRect[col][row].x = col * gamePanel.getTileSize() + eventRect[col][row].x;
        eventRect[col][row].y = row * gamePanel.getTileSize() + eventRect[col][row].y;

        if (gamePanel.getPlayer().getSolidArea().intersects(eventRect[col][row])
                && eventRect[col][row].eventDone == false)
            if (gamePanel.getPlayer().getDirection().contentEquals(requiredDirection)
                    || requiredDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gamePanel.getPlayer().getWorldX();
                previousEventY = gamePanel.getPlayer().getWorldY();
            }

        gamePanel.getPlayer().getSolidArea().x = gamePanel.getPlayer().getSolidAreaDefaultX();
        gamePanel.getPlayer().getSolidArea().y = gamePanel.getPlayer().getSolidAreaDefaultY();
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    // Events
    private void damagePit(int col, int row, GAME_STATE gameState) {
        gamePanel.setGameState(gameState);
        gamePanel.playSoundEffect(6);
        gamePanel.getUserInterface().setCurrentDialogue("You fell into a pit!");
        gamePanel.getPlayer().setLife(gamePanel.getPlayer().getLife() - 1);
        canTouchEvent = false;
    }

    private void teleport(int col, int row, GAME_STATE gameState) {
        gamePanel.setGameState(gameState);
        gamePanel.playSoundEffect(6);
        gamePanel.getUserInterface().setCurrentDialogue("T E L E P O R T E D !!!");
        gamePanel.getPlayer().setWorldX(gamePanel.getTileSize() * 37);
        gamePanel.getPlayer().setWorldY(gamePanel.getTileSize() * 10);
    }

    private void healingPool(int col, int row, GAME_STATE gameState) {
        if (gamePanel.getKeyHandler().isInteractPressed() == true) {
            gamePanel.setGameState(gameState);
            gamePanel.playSoundEffect(2);
            gamePanel
                    .getUserInterface()
                    .setCurrentDialogue("You drank the water from the pond.\nYour HP and Mana have been recovered!");
            gamePanel.getPlayer().setLife(gamePanel.getPlayer().getMaxLife());
            gamePanel.getPlayer().setMana(gamePanel.getPlayer().getMaxMana());

            if (gamePanel.getNPCs()[0] == null)
                gamePanel.getAssetSetter().setMonster();
        }
    }
}