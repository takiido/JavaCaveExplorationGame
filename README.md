# Cave Exploration Game

## Overview

The Cave Exploration Game is a simple text-based game written in Java, where players navigate a grid-based cave to collect coins, avoid enemies, and score points. Players move in four directions (up, down, left, or right) while interacting with the environment, which includes walls, coins, and enemies. The goal is to collect as many coins as possible without being caught by enemies.

## Features

- **Grid-Based Exploration**: Navigate through a grid where each cell can be empty, contain a coin, or be occupied by an enemy or rock (wall).
- **Random Generation**: The board features randomly placed rocks, coins, and enemies, ensuring a different gameplay experience each time.
- **Player Movement**: Use simple commands (u, d, l, r) to move the player around the board.
- **Enemy AI**: Enemies move towards the player and can end the game if they catch the player.
- **Score System**: Collect coins to increase your score. Each coin collected adds points to your score.
- **Replayability**: Play as many rounds as you want and try to beat your high score.

## Installation

1. Clone the repository:
``` bash
git clone https://github.com/yourusername/cave-exploration-game.git
```
2. Navigate to the project directory:
```bash
cd cave-exploration-game
```
3. Compile the Java code:
```bash
javac CaveExplorationGame.java
```
4. Run the game:
```bash 
java CaveExplorationGame
```

## How to Play

- **Start the Game**: Run the CaveExplorationGame.java file to begin.
- **Move the Player**: Use the following commands to navigate:
    - u: Move up
    - d: Move down
    - l: Move left
    - r: Move right
- **Print the Board**: Use the p command to display the current state of the board.
- **Quit the Game**: Enter q to quit the game.
- **Objective**: Collect coins while avoiding enemies. Each coin collected increases your score by 100 points. The game ends if you get caught by an enemy or collect all the coins.

## Gameplay Example

```bash
         ----- Welcome to the Cave Exploration Game -----
        -> In this game, you will explore the cave and face off against various challenges.
        -> Your main challenge is to outsmart the enemies and collect all the coins.
        -> The game board is filled with rocks (walls), coins, and enemies.
        -> Your goal is to collect as many coins as possible without getting caught by the enemies.
        -> You can move up, down, left, or right to navigate the board, but be cautious of the enemies!
        -> You can play as many times as you want, and to quit the game, simply enter 'q'.
        -> Each time you collect a coin, you gain points and your score increases. Be strategic and watch out for enemy movements!
        -> GOOD LUCK and enjoy your adventure in the cave!
```

## Commands:
- u - Move up
- d - Move down
- l - Move left
- r - Move right
- p - Print the current state of the board
- q - Quit the game

## Game Board Legend

- **P**: Player
- **X**: Rock (wall)
- **·**: Coin
- **E**: Enemy
- **Empty space**: Available for movement

## Customization

- **Game Configuration**: You can adjust the game field size, number of coins, enemies, and rocks by modifying the `NUM_ROWS`, `NUM_COLS`, `MAX_COINS`, `MAX_ENEMIES`, and `MAX_ROCKS` constants in the code.