package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import entity.monster.Monster;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable {
    private static final long serialVersionUID = 541240141319711284L;

    /*
     * =============================================================================
     * ATTRIBUTES (Start)
     * =============================================================================
     */
    // Screen Settings
    private final int ORG_TILE_SIZE = 16; // 16x16 tile
    private final int SCALE = 3; // Scaling value

    private final int TILE_SIZE = ORG_TILE_SIZE * SCALE; // 48x48 tile
    private final int MAX_SCREEN_COL = 20;
    private final int MAX_SCREEN_ROW = 12;
    private final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 48 * 20 = 960 pixels
    private final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 48 * 12 = 576 pixels

    // World Settings
    private final int MAX_WORLD_COL = 50;
    private final int MAX_WORLD_ROW = 50;
    private final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
    private final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;

    // Full Screen Settings
    private int screenWidth2 = SCREEN_WIDTH;
    private int screenHeight2 = SCREEN_HEIGHT;
    private BufferedImage tempScreen;
    private Graphics2D g2d;
    private boolean fullScreenOn = false;

    // FPS
    private int FPS = 60;
    private int currentFPS = 0;

    // System
    private TileManager tileManager = new TileManager(this);
    private KeyHandler keyHandler = new KeyHandler(this);
    private Sound music = new Sound();
    private Sound soundEffect = new Sound();
    private CollisionChecker collisionChecker = new CollisionChecker(this);
    private AssetSetter assetSetter = new AssetSetter(this);
    private UI ui = new UI(this);
    private EventHandler eventHandler = new EventHandler(this);
    private Config config = new Config(this);
    private Coin coin = new Coin(this);
    private Data data = new Data(this);
    private Thread gameThread;

    // Entities & Objects
    private Player player = new Player(this, keyHandler);
    private Entity obj[] = new Entity[20];
    private Entity npc[] = new Entity[10];
    private Monster monster[] = new Monster[20];
    private ArrayList<Entity> entityList = new ArrayList<>();
    private ArrayList<Entity> projectileList = new ArrayList<>();
    private ArrayList<Entity> particleList = new ArrayList<>();

    // Game States
    public GAME_STATE gameState;
    /*
     * =============================================================================
     * ATTRIBUTES (End)
     * =============================================================================
     */

    /*
     * =============================================================================
     * METHODS (Start)
     * =============================================================================
     */
    // Constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    protected void setupGame() {
        assetSetter.setObject();
        assetSetter.setMonster();
        gameState = GAME_STATE.Title;

        tempScreen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2d = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn == true)
            setFullScreen();
    }

    protected void restart() {
        entityList.clear();
        projectileList.clear();
        particleList.clear();

        player.setDefaultValues();
        player.setItems();
        assetSetter.setObject();
        assetSetter.setMonster();

        ui.resetPlayTimeSecond();
        ui.resetPlayTimeMinute();
    }

    private void setFullScreen() {
        // Get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // Get full screen width & height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    protected void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Override Runnable interface's run() method
    @Override
    public void run() {
        // /*
        // * ===========================================================================
        // * GAME LOOP 1 ("SLEEP" method)
        // * ===========================================================================
        // */
        // double drawInterval = 1000000000 / FPS; // 0.01667 seconds
        // double nextDrawTime = System.nanoTime() + drawInterval;

        // while (gameThread != null) {
        // update();
        // repaint();

        // try {
        // double remainingTime = nextDrawTime - System.nanoTime();
        // remainingTime /= 1000000;

        // if (remainingTime < 0)
        // remainingTime = 0;

        // Thread.sleep((long) remainingTime);

        // nextDrawTime += drawInterval;
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // }

        /*
         * =============================================================================
         * GAME LOOP 2 ("DELTA" / "ACCUMULATOR" method)
         * =============================================================================
         */
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                drawToTempScreen(); // Draw everything to the buffered image
                drawToScreen(); // Draw the buffered image to the screen
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                currentFPS = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    private void update() {
        if (gameState == GAME_STATE.Play) {
            // Player
            player.update();

            // NPC
            for (int i = 0; i < npc.length; i++)
                if (npc[i] != null)
                    npc[i].update();

            // Monster
            for (int i = 0; i < monster.length; i++)
                if (monster[i] != null) {
                    if (monster[i].isAlive() == true && monster[i].isDying() == false)
                        monster[i].update();
                    if (monster[i].isAlive() == false) {
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }

            // Projectile
            for (int i = 0; i < projectileList.size(); i++)
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).isAlive() == true)
                        projectileList.get(i).update();
                    if (projectileList.get(i).isAlive() == false)
                        projectileList.remove(i);
                }

            // Particle
            for (int i = 0; i < particleList.size(); i++)
                if (particleList.get(i) != null) {
                    if (particleList.get(i).isAlive() == true)
                        particleList.get(i).update();
                    if (particleList.get(i).isAlive() == false)
                        particleList.remove(i);
                }
        }
        if (gameState == GAME_STATE.Pause) {
            // Do nothing => Stop temporarily
        }
    }

    private void drawToTempScreen() {
        // ============ DEBUG ONLY ============ //
        long drawStart = 0;
        if (keyHandler.isShowDebugTexts() == true)
            drawStart = System.nanoTime();
        // ============ DEBUG ONLY ============ //

        // TITLE SCREEN (MAIN MENU)
        if (gameState == GAME_STATE.Title) {
            ui.draw(g2d);
        }
        // OTHERS
        else {
            // Draw tiles
            tileManager.draw(g2d);

            // Add Player to list
            entityList.add(player);

            // Add NPCs to list
            for (int i = 0; i < npc.length; i++)
                if (npc[i] != null)
                    entityList.add(npc[i]);

            // Add Objects to list
            for (int i = 0; i < obj.length; i++)
                if (obj[i] != null)
                    entityList.add(obj[i]);

            // Add Monsters to list
            for (int i = 0; i < monster.length; i++)
                if (monster[i] != null)
                    entityList.add(monster[i]);

            // Add Projectiles to list
            for (int i = 0; i < projectileList.size(); i++)
                if (projectileList.get(i) != null)
                    entityList.add(projectileList.get(i));

            // Add Particles to list
            for (int i = 0; i < particleList.size(); i++)
                if (particleList.get(i) != null)
                    entityList.add(particleList.get(i));

            // Sort list by entities' getWorldY() (Lower getWorldY() => Draw first)
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.getWorldY(), e2.getWorldY());
                    return result;
                }
            });

            // Draw Entities
            for (int i = 0; i < entityList.size(); i++)
                entityList.get(i).draw(g2d);
            // Empty the list
            entityList.clear();

            // Draw UI
            ui.draw(g2d);
        }

        // ============ DEBUG ONLY ============ //
        // Press 'T' to show Debug Texts
        if (gameState == GAME_STATE.Play && keyHandler.isShowDebugTexts() == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            g2d.setColor(Color.WHITE);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            // Show current FPS
            g2d.drawString("FPS : " + currentFPS, x, y);
            y += lineHeight;

            // Show World's Coordinates
            g2d.drawString("getWorldX() : " + player.getWorldX(), x, y);
            y += lineHeight;
            g2d.drawString("getWorldY() : " + player.getWorldY(), x, y);
            y += lineHeight;
            g2d.drawString("col : " + (player.getWorldX() + player.getSolidArea().x) / TILE_SIZE, x, y);
            y += lineHeight;
            g2d.drawString("row : " + (player.getWorldY() + player.getSolidArea().y) / TILE_SIZE, x, y);
            y += lineHeight;
            g2d.drawString("Draw Time : " + passed, x, y);
            y += lineHeight;

            // Show Player's moving direction & facing direction
            g2d.drawString("Moving Direction : " + player.getDirection(), x, y);
            y += lineHeight;
            g2d.drawString("Facing Right? : " + player.isFacingRight(), x, y);
            y += lineHeight;

            // Show Player's hit box
            g2d.setColor(Color.RED);
            g2d.drawRect(player.SCREEN_X + player.getSolidArea().x, player.SCREEN_Y + player.getSolidArea().y,
                    player.getSolidArea().width, player.getSolidArea().height);

            // Show Player's Invincibility Frame
            g2d.setColor(Color.WHITE);
            g2d.drawString("Invincible Frame : " + player.getInvincibleCounter(), x, y);
        }
        // ============ DEBUG ONLY ============ //
    }

    private void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    // Sounds
    public void playMusic(int index) {
        music.setFile(index);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int index) {
        soundEffect.setFile(index);
        soundEffect.play();
    }

    // Getters & Setters
    public int getOriginalTileSize() {
        return ORG_TILE_SIZE;
    }

    public int getScale() {
        return SCALE;
    }

    public int getTileSize() {
        return TILE_SIZE;
    }

    public int getMaxScreenCol() {
        return MAX_SCREEN_COL;
    }

    public int getMaxScreenRow() {
        return MAX_SCREEN_ROW;
    }

    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public int getMaxWorldCol() {
        return MAX_WORLD_COL;
    }

    public int getMaxWorldRow() {
        return MAX_WORLD_ROW;
    }

    public int getWorldWidth() {
        return WORLD_WIDTH;
    }

    public int getWorldHeight() {
        return WORLD_HEIGHT;
    }

    public boolean isFullScreenOn() {
        return fullScreenOn;
    }

    public void setFullScreenOn(boolean fullScreenOn) {
        this.fullScreenOn = fullScreenOn;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public AssetSetter getAssetSetter() {
        return assetSetter;
    }

    public Sound getMusic() {
        return music;
    }

    public Sound getSoundEffect() {
        return soundEffect;
    }

    public UI getUserInterface() {
        return ui;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public Config getConfig() {
        return config;
    }

    public Coin getCoin(){
        return coin;
    }

    public Data getData(){
        return data;
    }
    public Player getPlayer() {
        return player;
    }

    public Entity[] getObjects() {
        return obj;
    }

    public Entity[] getNPCs() {
        return npc;
    }

    public Monster[] getMonsters() {
        return monster;
    }

    public ArrayList<Entity> getProjectileList() {
        return projectileList;
    }

    public ArrayList<Entity> getParticleList() {
        return particleList;
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }
    /*
     * =============================================================================
     * METHODS (End)
     * =============================================================================
     */
}