package main;

import entity.Entity;

public class CollisionChecker {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Collision checking methods
    // Tile collision
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX / gamePanel.getTileSize();
        int entityRightCol = entityRightWorldX / gamePanel.getTileSize();
        int entityTopRow = entityTopWorldY / gamePanel.getTileSize();
        int entityBottomRow = entityBottomWorldY / gamePanel.getTileSize();

        int tileNum1, tileNum2, tileNum3, tileNum4;

        // Solid area that 4 corners of the entity's solid area is/are hitting
        tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
        tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
        tileNum3 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
        tileNum4 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];

        // Check if any of them are solid tiles
        if (gamePanel.getTileManager().getTiles()[tileNum1].isCollision() == true ||
                gamePanel.getTileManager().getTiles()[tileNum2].isCollision() == true ||
                gamePanel.getTileManager().getTiles()[tileNum3].isCollision() == true ||
                gamePanel.getTileManager().getTiles()[tileNum4].isCollision() == true)
            entity.setCollisionOn(true);
    }

    // Object collision
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gamePanel.getObjects().length; i++)
            if (gamePanel.getObjects()[i] != null) {
                // Get entity's solid area position
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                // Get getObjects()ect's solid area position
                gamePanel.getObjects()[i].getSolidArea().x = gamePanel.getObjects()[i].getWorldX()
                        + gamePanel.getObjects()[i].getSolidArea().x;
                gamePanel.getObjects()[i].getSolidArea().y = gamePanel.getObjects()[i].getWorldY()
                        + gamePanel.getObjects()[i].getSolidArea().y;

                switch (entity.getDirection()) {
                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();
                        break;
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        break;
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        break;
                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();
                        break;
                    case "up-left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        entity.getSolidArea().y -= entity.getSpeed();
                        break;
                    case "down-left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        entity.getSolidArea().y += entity.getSpeed();
                        break;
                    case "down-right":
                        entity.getSolidArea().x += entity.getSpeed();
                        entity.getSolidArea().y += entity.getSpeed();
                        break;
                    case "up-right":
                        entity.getSolidArea().x += entity.getSpeed();
                        entity.getSolidArea().y -= entity.getSpeed();
                        break;
                }

                if (entity.getSolidArea().intersects(gamePanel.getObjects()[i].getSolidArea())) {
                    if (gamePanel.getObjects()[i].isCollision() == true)
                        entity.setCollisionOn(true);
                    if (player == true)
                        index = i;
                }

                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                gamePanel.getObjects()[i].getSolidArea().x = gamePanel.getObjects()[i].getSolidAreaDefaultX();
                gamePanel.getObjects()[i].getSolidArea().y = gamePanel.getObjects()[i].getSolidAreaDefaultY();
            }

        return index;
    }

    // NPCs & Monsters collision
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++)
            if (target[i] != null) {
                // Get entity's solid area position
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                // Get getObjects()ect's solid area position
                target[i].getSolidArea().x = target[i].getWorldX() + target[i].getSolidArea().x;
                target[i].getSolidArea().y = target[i].getWorldY() + target[i].getSolidArea().y;

                switch (entity.getDirection()) {
                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();
                        break;
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        break;
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        break;
                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();
                        break;
                    case "up-left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        entity.getSolidArea().y -= entity.getSpeed();
                        break;
                    case "down-left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        entity.getSolidArea().y += entity.getSpeed();
                        break;
                    case "down-right":
                        entity.getSolidArea().x += entity.getSpeed();
                        entity.getSolidArea().y += entity.getSpeed();
                        break;
                    case "up-right":
                        entity.getSolidArea().x += entity.getSpeed();
                        entity.getSolidArea().y -= entity.getSpeed();
                        break;
                }

                if (entity.getSolidArea().intersects(target[i].getSolidArea()))
                    if (target[i] != entity) {
                        entity.setCollisionOn(true);
                        index = i;
                    }

                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                target[i].getSolidArea().x = target[i].getSolidAreaDefaultX();
                target[i].getSolidArea().y = target[i].getSolidAreaDefaultY();
            }

        return index;
    }

    // Player collision
    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        // Get entity's solid area position
        entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
        entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

        // Get getObjects()ect's solid area position
        gamePanel.getPlayer().getSolidArea().x = gamePanel.getPlayer().getWorldX()
                + gamePanel.getPlayer().getSolidArea().x;
        gamePanel.getPlayer().getSolidArea().y = gamePanel.getPlayer().getWorldY()
                + gamePanel.getPlayer().getSolidArea().y;

        switch (entity.getDirection()) {
            case "up":
                entity.getSolidArea().y -= entity.getSpeed();
                break;
            case "left":
                entity.getSolidArea().x -= entity.getSpeed();
            case "down":
                entity.getSolidArea().y += entity.getSpeed();
                break;
            case "right":
                entity.getSolidArea().x += entity.getSpeed();
                break;
            case "up-left":
                entity.getSolidArea().x -= entity.getSpeed();
                entity.getSolidArea().y -= entity.getSpeed();
                break;
            case "down-left":
                entity.getSolidArea().x -= entity.getSpeed();
                entity.getSolidArea().y += entity.getSpeed();
                break;
            case "down-right":
                entity.getSolidArea().x += entity.getSpeed();
                entity.getSolidArea().y += entity.getSpeed();
                break;
            case "up-right":
                entity.getSolidArea().x += entity.getSpeed();
                entity.getSolidArea().y -= entity.getSpeed();
                break;
        }

        if (entity.getSolidArea().intersects(gamePanel.getPlayer().getSolidArea())) {
            entity.setCollisionOn(true);
            contactPlayer = true;
        }

        entity.getSolidArea().x = entity.getSolidAreaDefaultX();
        entity.getSolidArea().y = entity.getSolidAreaDefaultY();
        gamePanel.getPlayer().getSolidArea().x = gamePanel.getPlayer().getSolidAreaDefaultX();
        gamePanel.getPlayer().getSolidArea().y = gamePanel.getPlayer().getSolidAreaDefaultY();

        return contactPlayer;
    }
}