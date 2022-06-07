package ai;

public class Node {
    // Attributes
    private Node parent;
    private int col;
    private int row;
    private int gCost;
    private int hCost;
    private int fCost;
    private boolean solid;
    private boolean open;
    private boolean checked;

    // Constructor
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }

    // Getters & Setters
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getGCost() {
        return gCost;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public int getHCost() {
        return hCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }

    public int getFCost() {
        return fCost;
    }

    public void setFCost(int fCost) {
        this.fCost = fCost;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}