package ai;

import java.util.ArrayList;

import main.GamePanel;

public class PathFinder {
    // Attributes
    private GamePanel gamePanel;
    private Node[][] node;
    private ArrayList<Node> openList = new ArrayList<>();
    private ArrayList<Node> pathList = new ArrayList<>();
    private Node startNode, goalNode, currentNode;
    private boolean goalReached = false;
    private int step = 0;

    // Constructor
    public PathFinder(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        instantiateNodes();
    }

    // Public methods
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        // Set Start & Goal nodes
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while (col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()) {
            // Set Solid node
            // Check Tiles
            int tileNum = gamePanel.getTileManager().getMapTileNum()[col][row];
            if (gamePanel.getTileManager().getTiles()[tileNum].isCollision() == true)
                node[col][row].setSolid(true);

            // Set Cost
            getCost(node[col][row]);

            col++;
            if (col == gamePanel.getMaxWorldCol()) {
                col = 0;
                row++;
            }
        }
    }

    public boolean search() {
        while (goalReached == false && step < 500) {
            int col = currentNode.getCol();
            int row = currentNode.getRow();

            // Check current node
            currentNode.setChecked(true);
            openList.remove(currentNode);

            // Open the Up node
            if (row - 1 >= 0)
                openNode(node[col][row - 1]);
            // Open the Left node
            if (col - 1 >= 0)
                openNode(node[col - 1][row]);
            // Open the Down node
            if (row + 1 < gamePanel.getMaxWorldRow())
                openNode(node[col][row + 1]);
            // Open the Right node
            if (col + 1 < gamePanel.getMaxWorldCol())
                openNode(node[col + 1][row]);

            // Find BEST node
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                // Check if this node's F cost is better
                if (openList.get(i).getFCost() < bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).getFCost();
                }
                // If F cost is equal, check the G cost
                else if (openList.get(i).getFCost() == bestNodeFCost)
                    if (openList.get(i).getGCost() < openList.get(bestNodeIndex).getGCost())
                        bestNodeIndex = i;
            }

            // If there is no node in the openList, end the loop
            if (openList.size() == 0)
                break;

            // After the loop, openList[bestNodeIndex] is the next step (= currentNode)
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }

        return goalReached;
    }

    // Internal (Private) methods
    private void instantiateNodes() {
        node = new Node[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];

        int col = 0;
        int row = 0;

        while (col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()) {
            node[col][row] = new Node(col, row);
            col++;
            if (col == gamePanel.getMaxWorldCol()) {
                col = 0;
                row++;
            }
        }
    }

    private void resetNodes() {
        int col = 0;
        int row = 0;

        while (col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()) {
            // Reset open, checked, and solid state
            node[col][row].setOpen(false);
            node[col][row].setChecked(false);
            node[col][row].setSolid(false);

            col++;
            if (col == gamePanel.getMaxWorldCol()) {
                col = 0;
                row++;
            }
        }

        // Reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    private void getCost(Node node) {
        // G cost
        int xDistance = Math.abs(node.getCol() - startNode.getCol());
        int yDistance = Math.abs(node.getRow() - startNode.getRow());
        node.setGCost(xDistance + yDistance);

        // H cost
        xDistance = Math.abs(node.getCol() - goalNode.getCol());
        yDistance = Math.abs(node.getRow() - goalNode.getRow());
        node.setHCost(xDistance + yDistance);

        // F cost
        node.setFCost(node.getGCost() + node.getHCost());
    }

    private void openNode(Node node) {
        if (node.isOpen() == false && node.isChecked() == false && node.isSolid() == false) {
            node.setOpen(true);
            node.setParent(currentNode);
            openList.add(node);
        }
    }

    private void trackThePath() {
        Node current = goalNode;

        while (current != startNode) {
            pathList.add(0, current);
            current = current.getParent();
        }
    }

    // Getter
    public ArrayList<Node> getPathList() {
        return pathList;
    }
}