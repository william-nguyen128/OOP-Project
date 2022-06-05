package main;

public class CharacterUpgrade {
    // Attributes
    private GamePanel gamePanel;
    private final int BASE_STRENGTH_COST = 50;
    private final int BASE_SPEED_COST = 50;
    private final int BASE_HP_COST = 50;
    private int strengthCost = BASE_STRENGTH_COST;
    private int speedCost = BASE_SPEED_COST;
    private int hpCost = BASE_HP_COST;
    private int totalCoin = 0;

    // Constructor
    public CharacterUpgrade(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Methods
    public void upgradeStrength() {
        int remaining = totalCoin - strengthCost;
        if (remaining >= 0) {
            gamePanel.getPlayer().setStrength(gamePanel.getPlayer().getStrength() + 1);
            totalCoin = remaining;
            strengthCost += 10;
        }
    }

    public void upgradeSpeed() {
        int remaining = totalCoin - speedCost;
        if (remaining >= 0) {
            gamePanel.getPlayer().setSpeed(gamePanel.getPlayer().getSpeed() + 1);
            totalCoin = remaining;
            speedCost += 10;
        }
    }

    public void upgradeMaxLife() {
        int remaining = totalCoin - hpCost;
        if (remaining >= 0) {
            gamePanel.getPlayer().setMaxLife(gamePanel.getPlayer().getMaxLife() + 10);
            totalCoin = remaining;
            hpCost += 10;
        }
    }

    // Getters & Setters
    public int getTotalCoin() {
        return totalCoin;
    }

    public void setTotalCoin(int totalCoin) {
        this.totalCoin = totalCoin;
    }

    public int getStrengthCost() {
        return strengthCost;
    }

    public void setStrengthCost(int strengthCost) {
        this.strengthCost = strengthCost;
    }

    public int getSpeedCost() {
        return speedCost;
    }

    public void setSpeedCost(int speedCost) {
        this.speedCost = speedCost;
    }

    public int getHpCost() {
        return hpCost;
    }

    public void setHpCost(int hpCost) {
        this.hpCost = hpCost;
    }
}