package model;

import java.util.*;

/**
 * Class representing a BSP dungeon generator.
 *
 * @author Tiger Schueler
 * @version 1.2
 */
public class Dungeon {

    private final int MAP_SIZE = 50;
    private final Tile[][] MAP;
    private static final int MIN_ROOM_SIZE = 10;
    private final Node ROOT;
    private final List<Node> rooms;
    private int myTotalRooms = 0;

    public Dungeon() {
        MAP = new Tile[MAP_SIZE][MAP_SIZE];
        ROOT = new Node(1, 1, MAP_SIZE - 1, MAP_SIZE - 1);
        rooms = new ArrayList<>();
        generateDungeon();
    }

    /**
     * Initializes the ROOT to have null children when regenerating a dungeon.
     */
    private void initializeRoot() {
        ROOT.myLeftChild = null;
        ROOT.myRightChild = null;
    }

    /**
     * Initializes the dungeon map with walls.
     */
    private void initializeMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                MAP[i][j] = Tile.WALL;
            }
        }
    }

    /**
     * Recursively splits the map using BSP algorithm.
     *
     * @param theNode The current node to split.
     */
    private static void splitMap(final Node theNode) {
        if (theNode == null) {
            return;
        }

        if (theNode.canSplit()) {
            final boolean splitDirection = theNode.splitDirection();
            if (splitDirection) {
                theNode.splitHorizontally();
            } else {
                theNode.splitVertically();
            }
        }
        splitMap(theNode.myLeftChild);
        splitMap(theNode.myRightChild);
    }

    /**
     * Creates rooms at the leaf nodes of the BSP tree.
     *
     * @param theNode The current node to check for room creation.
     */
    private void createRooms(final Node theNode) {
        if (theNode == null) {
            return;
        }

        if (theNode.myLeftChild == null && theNode.myRightChild == null) {
            myTotalRooms++;
            rooms.add(theNode);
            placeFloorTiles(theNode);
        } else {
            createRooms(theNode.myLeftChild);
            createRooms(theNode.myRightChild);
        }
    }

    /**
     * Creates doorways connecting adjacent doors on the North, South, East, and West walls.
     */
    private void createDoors() {
        for (final Node room : rooms) {
            createEastDoor(room);
            createSouthDoor(room);
        }
    }

    private void createEastDoor(final Node theNode) {
        final int nodeX = theNode.getX();
        final int nodeY = theNode.getY();
        final int width = theNode.getWidth();
        final int height = theNode.getHeight();
        final int rand = new Random().nextInt(height / 2);
        for (int i = rand; i <= height; i++) {
            if (nodeY + i < MAP_SIZE - 1
                    && nodeX + width < MAP_SIZE - 1
                    && MAP[nodeY + i][nodeX + width - 2] == Tile.FLOOR
                    && MAP[nodeY + i][nodeX + width - 1] == Tile.WALL
                    && MAP[nodeY + i][nodeX + width] == Tile.FLOOR) {
                MAP[nodeY + i][nodeX + width - 1] = Tile.DOOR;
                break;
            }
        }
    }

    private void createSouthDoor(final Node theNode) {
        final int nodeX = theNode.getX();
        final int nodeY = theNode.getY();
        final int width = theNode.getWidth();
        final int height = theNode.getHeight();
        final int rand = new Random().nextInt(width / 2);
        for (int i = rand; i <= width; i++) {
            if (nodeX + i < MAP_SIZE - 1
                    && nodeY + height < MAP_SIZE - 1
                    && MAP[nodeY + height - 2][nodeX + i] == Tile.FLOOR
                    && MAP[nodeY + height - 1][nodeX + i] == Tile.WALL
                    && MAP[nodeY + height][nodeX + i] == Tile.FLOOR) {
                MAP[nodeY + height - 1][nodeX + i] = Tile.DOOR;
                break;
            }
        }
    }

    /**
     * Creates a room within the node.
     *
     * @param theNode The node to create a room in.
     */
    private void placeFloorTiles(final Node theNode) {
        for (int i = theNode.getY(); i < theNode.getY() + theNode.getHeight() - 1; i++) {
            for (int j = theNode.getX(); j < theNode.getX() + theNode.getWidth() - 1; j++) {
                if (i > 0 && j > 0) {
                    MAP[i][j] = Tile.FLOOR;
                }
            }
        }
    }

    /**
     * Prints the dungeon map to the console.
     */
    public void printMap() {
        StringBuilder mapBuilder = new StringBuilder();
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (MAP[i][j] == Tile.WALL) {
                    mapBuilder.append('#');
                } else if (MAP[i][j] == Tile.FLOOR){
                    mapBuilder.append('.');
                } else {
                    mapBuilder.append('D');
                }
                mapBuilder.append(" ");
            }
            mapBuilder.append("\n");
        }
        System.out.println(mapBuilder);
    }

    public Tile[][] getMap() {
        return MAP;
    }

    private void generateDungeon() {
        rooms.clear();
        initializeRoot();
        initializeMap();
        splitMap(ROOT);
        createRooms(ROOT);
        createDoors();
        if (myTotalRooms < 15) {
            myTotalRooms = 0;
            generateDungeon();
        }
    }

    /**
     * Class representing a node in the BSP tree.
     */
    private static class Node {
        private final int myX;
        private final int myY;
        private final int myWidth;
        private final int myHeight;
        private Node myLeftChild;
        private Node myRightChild;

        /**
         * Constructor for creating a Node.
         *
         * @param theX       The x-coordinate of the node.
         * @param theY       The y-coordinate of the node.
         * @param theWidth   The width of the node.
         * @param theHeight  The height of the node.
         */
        Node(final int theX, final int theY, final int theWidth, final int theHeight) {
            myX = theX;
            myY = theY;
            myWidth = theWidth;
            myHeight = theHeight;
            myLeftChild = null;
            myRightChild = null;
        }

        /**
         * Gets the x-coordinate of the node.
         *
         * @return The x-coordinate.
         */
        public int getX() {
            return myX;
        }

        /**
         * Gets the y-coordinate of the node.
         *
         * @return The y-coordinate.
         */
        public int getY() {
            return myY;
        }

        /**
         * Gets the width of the node.
         *
         * @return The width.
         */
        public int getWidth() {
            return myWidth;
        }

        /**
         * Gets the height of the node.
         *
         * @return The height.
         */
        public int getHeight() {
            return myHeight;
        }

        /**
         * Checks if the node can be split.
         *
         * @return True if the node can be split, false otherwise.
         */
        private boolean canSplit() {
            // ignore this red squiggly for readability purposes
            if (myLeftChild == null && myRightChild == null
                    && (getWidth() >= MIN_ROOM_SIZE || getHeight() >= MIN_ROOM_SIZE)
                    && (getWidth() > 1 && getHeight() > 1)
                    && getWidth() * getHeight() > MIN_ROOM_SIZE) {
                return true;
            }
            return false;
        }

        /**
         * Determines the split direction (horizontal or vertical) for the node.
         *
         * @return True if split horizontally, false otherwise.
         */
        private boolean splitDirection() {
            final boolean splitHorizontally;
            if (myWidth >= MIN_ROOM_SIZE && myHeight >= MIN_ROOM_SIZE) {
                splitHorizontally = new Random().nextBoolean();
            } else {
                splitHorizontally = myWidth < MIN_ROOM_SIZE;
            }
            return splitHorizontally;
        }

        /**
         * Splits the node horizontally.
         */
        private void splitHorizontally() {
            final int split = new Random().nextInt(myHeight) + myY + 1;
            if (myY + split + 1 < myY + myHeight
                    && split - 1 > myY + 1
                    && (myWidth * split > MIN_ROOM_SIZE)
                    && (myWidth * (myHeight - split) > MIN_ROOM_SIZE)) {
                myLeftChild = new Node(myX, myY, myWidth, split);
                myRightChild = new Node(myX, myY + split,
                        myWidth, myHeight - split);
            }
        }

        /**
         * Splits the node vertically.
         */
        private void splitVertically() {
            final int split = new Random().nextInt(myWidth) + myX + 1;
            if (myX + split + 1 < myX + myWidth
                    && myX + split - 1 > myX + 1
                    && (myHeight * split > MIN_ROOM_SIZE)
                    && (myHeight * (myWidth - split) > MIN_ROOM_SIZE)) {
                myLeftChild = new Node(myX, myY, split, myHeight);
                myRightChild = new Node(myX + split, myY,myWidth - split, myHeight);
            }
        }
    }
}
