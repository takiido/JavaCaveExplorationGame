import java.util.Random;
import java.util.Scanner;

public class CaveExplorationGame {
    // Colors consts for console output
    public static final String NC = "\u001B[0m"; // No color
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    // Game config consts
    private static final int NUM_ROWS = 10;
    private static final int NUM_COLS = 20;
    private static final int MAX_COINS = 10;
    private static final int MAX_ENEMIES = 5;
    private static final int MAX_ROCKS = 7;

    // Scanner for user input
    private static final Scanner scanner = new Scanner(System.in);

    // Game state vars
    private static boolean isRunning;
    private static int score;

    // Arrays for game elements
    private static int[] rocksXs = new int[NUM_ROWS * NUM_COLS]; // X-coords of rocks
    private static int[] rocksYs = new int[NUM_ROWS * NUM_COLS]; // Y-coords of rocks
    private static int rocksCount = 0; // Current n of rocks

    private static int[] coinsXs = new int[NUM_ROWS * NUM_COLS]; // X-coords of coins
    private static int[] coinsYs = new int[NUM_ROWS * NUM_COLS]; // Y-coords of coins
    private static int coinsCount = 0; // Current n of rocks

    private static int[] enemiesXs = new int[MAX_ENEMIES]; // X-coords of enemies
    private static int[] enemiesYs = new int[MAX_ENEMIES]; // Y-coords of enemies
    private static int enemiesCount = 0; // Current n of enemies

    private static int playerX = 0; // Player x position
    private static int playerY = 0; // Player y position

    private static Random random = new Random();
    
     /**
     * Prints the game board with all elements (rocks, coins, enemies, player).
     */
    private static void printBoard() {
        String gameStatus = isRunning ? "GAME PLAYING" : "GAME OVER";

        System.out.print(PURPLE + "SCORE: " + score + " (" + gameStatus + ", COINS: " + coinsCount + ", ENEMIES: " + MAX_ENEMIES + ")\n" + NC);

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (search(rocksXs, rocksYs, rocksCount, j, i) != -1) {
                    System.out.print("X"); // Wall or rock
                } else if (j == playerX && i == playerY) {
                    System.out.print(CYAN + "P" + NC); // Player
                } else if (search(coinsXs, coinsYs, coinsCount, j, i) != -1) {
                    System.out.print(YELLOW + "."  + NC); // Coin
                } else if (search(enemiesXs, enemiesYs, enemiesCount, j, i) != -1) {
                    System.out.print(RED + "E" + NC); // Enemy
                } else {
                    System.out.print(" "); // Empty space
                }
            }
            System.out.println();
        }
    }

     /**
     * Validates the player's input commands.
     * @param input The input string to validate.
     * @return True if the input is valid, false otherwise.
     */
    private static boolean isInputValid(String input) {
        return input.matches("^[udlrpq]+$"); // Valid commands: up, down, left, right, print, quit
    }

     /**
     * Processes player commands.
     * @param input The string of commands to execute.
     */
    private static void processCommand(String input) {
        for (char command : input.toCharArray()) {
            switch (command) {
                case 'u':
                    movePlayer(0, -1);
                    // System.out.println("CMD: move up");
                    break;

                case 'd':
                    movePlayer(0, 1);
                    // System.out.println("CMD: move down");
                    break;

                case 'l':
                    movePlayer(-1, 0);
                    // System.out.println("CMD: move left");
                    break;

                case 'r':
                    movePlayer(1, 0);
                    // System.out.println("CMD: move right");  
                    break;

                case 'p':
                    // System.out.println("CMD: print board");
                    printBoard();
                    break;

                case 'q':
                    // System.out.println("CMD: quit");
                    isRunning = false;
                    break;
            
                default:
                    break;
            }
        }
    }

     /**
     * Searches for a coordinate in the given arrays.
     * @param xs Array of x-coordinates.
     * @param ys Array of y-coordinates.
     * @param n The number of valid entries in the arrays.
     * @param x The x-coordinate to search for.
     * @param y The y-coordinate to search for.
     * @return Index of the coordinate if found, -1 otherwise.
     */
    private static int search(int[] xs, int[] ys, int n, int x, int y) {
        for (int i = 0; i < n; i++) {
            if (xs[i] == x && ys[i] == y) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Inserts a coordinate into the given arrays if it is not already present.
     * @param xs Array of x-coordinates.
     * @param ys Array of y-coordinates.
     * @param n Current count of valid entries in the arrays.
     * @param x The x-coordinate to insert.
     * @param y The y-coordinate to insert.
     * @return Updated count of valid entries.
     */
    private static int insert(int[] xs, int [] ys, int n, int x, int y) {
        if (n < xs.length && search(xs, ys, n, x, y) ==  -1) {
            xs[n] = x;
            ys[n] = y;
            return n+1;
        }
        return n;
    }

    /**
     * Places walls (rocks) around the edges of the board.
     * @param xs Array of x-coordinates for rocks.
     * @param ys Array of y-coordinates for rocks.
     * @param n Current count of rocks.
     * @return Updated count of rocks.
     */
    private static int placeWalls(int[] xs, int[] ys, int n) {
        for (int x = 0; x < NUM_COLS; x++) {
            n = insert(xs, ys, n, x, 0);
            n = insert(xs, ys, n, x, NUM_ROWS-1);
        }
        for (int y = 1; y < NUM_ROWS - 1; y++) {
            n = insert(xs, ys, n, 0, y);
            n = insert(xs, ys, n, NUM_COLS - 1, y);
        }
        return n;
    }

    /**
     * Places the player at a random empty location on the grid.
     */
    private static void placePlayer() {
        do {
            playerX = (int) (Math.random() * NUM_COLS);
            playerY = (int) (Math.random() * NUM_ROWS);
        }
        while (search(rocksXs, rocksYs, rocksCount, playerX, playerY) != -1);

        System.out.println("Player placed at (" + playerX + ", " + playerY + ")");
    }

    /**
    * Places coins at random empty locations on the grid.
    */
    private static void placeCoins() {
        for (int i = 0; i < MAX_COINS; i++) {
            int x, y;
            do {
                x = (int) (Math.random() * NUM_COLS);
                y = (int) (Math.random() * NUM_ROWS);
            }
            while (search(rocksXs, rocksYs, rocksCount, x, y) != -1 || search(coinsXs, coinsYs, coinsCount, x, y) != -1);

            coinsCount = insert(coinsXs, coinsYs, coinsCount, x, y);
        }
    }
    
    /**
     * Checks if a grid cell is empty (not occupied by rocks, coins, or enemies).
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return True if the cell is empty, false otherwise.
     */
    private static boolean isCellEmpty(int x, int y) {
        return x >= 0 && x < NUM_COLS && y >= 0 && y < NUM_ROWS && 
            search(rocksXs, rocksYs, rocksCount, x, y) == -1 && 
            search(coinsXs, coinsYs, coinsCount, x, y) == -1 && 
            search(enemiesXs, enemiesYs, enemiesCount, x, y) == -1;
    }

    /**
    * Places enemies at random empty locations on the grid.
    */
    private static void placeEnemies() {
        for (int i = 0; i < MAX_ENEMIES; i++) {
            int x, y;
            do {
                x = random.nextInt(NUM_COLS);
                y = random.nextInt(NUM_ROWS);
            }
            while (!isCellEmpty(x,y));
            enemiesCount = insert(enemiesXs, enemiesYs, enemiesCount, x, y);
        }
    }

    /**
     * Attempts to move an enemy in a specified direction.
     * @param index The index of the enemy in the arrays.
     * @param dx The change in x-coordinate.
     * @param dy The change in y-coordinate.
     */
    private static void attemptMoveEnemy(int index, int dx, int dy) {
        int newX = enemiesXs[index] + dx;
        int newY = enemiesYs[index] + dy;

        if (isCellEmpty(newX, newY)) {
            enemiesXs[index] = newX;
            enemiesYs[index] = newY;
        }

        if (newX == playerX && newY == playerY) {
            System.out.println(RED + "An enemy caught the player!" + NC);
            isRunning = false;
        }
    }

    /**
     * Moves all enemies based on proximity to the player.
     */
    private static void moveEnemies() {
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        for (int i = 0; i < enemiesCount; i++) {
            int bestDx = 0, bestDy = 0;

            double minDistance = Double.MAX_VALUE;
            for (int dir = 0; dir < dx.length; dir++) {
                int newX = enemiesXs[i] + dx[dir];
                int newY = enemiesYs[i] + dy[dir];
                
                if (isCellEmpty(newX, newY)) {
                    double distance = Math.sqrt(Math.pow(playerX - newX, 2) + Math.pow(playerY - newY, 2));

                    if (distance < minDistance) {
                        minDistance = distance;
                        bestDx = dx[dir];
                        bestDy = dy[dir];
                    }
                }
            }
            attemptMoveEnemy(i, bestDx, bestDy);
        }
    }

    /**
     * Removes an item from the arrays by replacing it with the last item.
     * @param xs Array of x-coordinates.
     * @param ys Array of y-coordinates.
     * @param n Current count of valid entries in the arrays.
     * @param index The index of the item to remove.
     * @return Updated count of valid entries.
     */
    private static int remove (int[] xs, int[] ys, int n, int index) {
        if (index < n-1) {
            xs[index] = xs[n-1];
            ys[index] = ys[n-1];
        }

        return n-1;
    }

    /**
     * Moves the player in a specified direction, collecting coins if encountered.
     * @param dx The change in x-coordinate.
     * @param dy The change in y-coordinate.
     */
    private static void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;

        if (search(rocksXs, rocksYs, rocksCount, newX, newY) == -1 && 
            newX >= 0 && newX < NUM_COLS && 
            newY >= 0 && newY < NUM_ROWS) {
                playerX = newX;
                playerY = newY;

                int coinIndex = search(coinsXs, coinsYs, coinsCount, playerX, playerY);
                if (coinIndex != -1) {
                    coinsCount = remove(coinsXs, coinsYs, coinsCount, coinIndex);
                    System.out.println(YELLOW + "You collected a coin!" + NC);
                    score++;
        
                    if (coinsCount == 0) {
                        System.out.println(GREEN + "Congratulations! You collected all the coin!" + NC);
                        isRunning = false;
                    }
                }
        }
    }

    /**
     * Places random rocks in empty grid cells.
     */
    private static void placeRocks() {
        for (int i = 0; i < MAX_ROCKS; i++) {
            int x, y;
            do {
                x = random.nextInt(NUM_COLS);
                y = random.nextInt(NUM_ROWS);
            } 
            while (!isCellEmpty(x, y));

            rocksCount = insert(rocksXs, rocksYs, rocksCount, x, y);
        } 
    }

    public static void main(String[] args) {
        System.out.println(YELLOW + 
        "\n ----- Welcome to the Cave Exploration Game ----- \n" +
        "-> In this game, you will explore the cave and face off against various challenges.\n" +
        "-> Your main challenge is to outsmart the enemies and collect all the coins.\n" +
        "-> The game board is filled with rocks (walls), coins, and enemies.\n" +
        "-> Your goal is to collect as many coins as possible without getting caught by the enemies.\n" +
        "-> You can move up, down, left, or right to navigate the board, but be cautious of the enemies!\n" +
        "-> You can play as many times as you want, and to quit the game, simply enter 'q'.\n" +
        "-> Each time you collect a coin, you gain points and your score increases. Be strategic and watch out for enemy movements!\n" +
        "-> GOOD LUCK and enjoy your adventure in the cave!\n" +
        NC);
        do {
            rocksCount = placeWalls(rocksXs, rocksYs, rocksCount);
            placeCoins();
            placeEnemies();
            placeRocks();
            placePlayer();

            isRunning = true;
            score = 0;

            System.out.println(PURPLE + "New game started!" + NC);
            printBoard();

            while (isRunning) {
                System.out.print(PURPLE + "Enter the commands: " + NC);
                String commands = scanner.nextLine().toLowerCase().replaceAll("\\s", ""); // Reading input and rempving whitespaces
                if (!isInputValid(commands)) {
                    System.out.println(RED + "Sorry, that is not a valid command string." + NC);
                    continue;
                }

                processCommand(commands);
                moveEnemies();
                if (isRunning) printBoard();
            }
            System.out.print(PURPLE + "Would you like to play again? (y/n): " + NC);
        }
        while (scanner.nextLine().toLowerCase().equals("y"));
        System.out.println("Okay. Thanks for playing!");
    }
}