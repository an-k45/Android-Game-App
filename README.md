# CSC207 Project Phase 2

Phase 2 of the CSC207 Term Project. This project implements all required functionalities,
as well as various bonuses from Phase 1 including unlimited undos, and selecting images from both the downloads directory and directly from the internet through a URL.

## Getting Started (Modified A2 Instructions)

1. Install Android Studio
2. Run, then check out project with version control using <https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0636>
3. Set up the project through Gradle, using the default Gradle wrapper
4. Let the project build
5. Create an Android Virtual Device that uses Pixel2, with the device OS as Android 8.1 API 27
6. Run the file "StartingActivity.java", which will initiate the application and game menus.

For further instructions on how to play, navigate, and so on, review sections "Game Information" and "Functionalities" below.

## Game Information

Information about the games and how they are played are listed below to explain how the user can interact with mechanics and win/lose the games.

### Sliding Tiles

The Sliding Tiles game will take an image and separate it into tiles.  The bottom-right-most tile of the original image will be replaced with a blank tile, and these tiles will be shuffled to create the gameboard.  The goal of the game is to recreate the original image (with the blank tile at the bottom right) by switching the tiles around.  The only way to switch tiles around are to tap on tiles adjacent to the blank tile.  Doing so will switch the tile that was tapped on, with the blank tile.  The game ends when the image is reconstructed, and score is calculated based off how many moves were made during the game.

### Pipelines

The Pipelines game will randomly generate a board of pipes that has a beginning and end tile, indicated by the presence of water within these special pipes.  The goal of the game is to create a path between the beginning and end tiles by rotating the other pipes.  Pipes can be rotated 90 degrees by tapping on their tile.  The game ends when the path is completed, and score is calculated based off how many moves were made during the game.

### 4096

The 4096 game will start with a 4x4 grid with a '2' tile in the top left.  The goal of the game is to create a '4096' tile by adding tiles together on the board.  Tiles will be added together when one comes in contact with another, but only if both tiles contain the same value.  Tiles move when the user swipes in a direction, but only if the direction allows at least one tile to either move in that direction, or combine with another tile.  Each time the user makes a valid move, a new '2' or '4' block will enter play somewhere in the board.  The game ends when either the board is full, in which case the player loses, or the '4096' tile is created, in which case the player wins.  In either case, score is calculated based off the number and value of tiles combined.  Upon the game ending, the user will be unable to make any more moves.

## Functionalities

Functionalities are listed below so as to explain how the user can properly use them in this program.

### Sign-in and Sign-up

Sign-in and sign-ups are dealt with in the "Your Account" button from the "Welcome Back" menu.  From the resulting menu, pressing the "Log In" button will bring the user to a sign-in screen,  where they have the option of either logging in by entering a stored account's credentials in the username and password fields and pressing the "Log In" button, or creating a new account by pressing the button "New Around Here?", which prompts the user to enter a username that is not already stored within the system, and a corresponding password.  Once these fields are filled properly, pressing "Create Account" will bring the user back to the log-in menu, where they can use their newly created account to log in.

### Autosave and Saving

Two games offer saving functionality, namely **Sliding Tiles** and **4096**.

Game saves are stored on accounts, and are separate for both games.  The user must be logged in to save the game.  If the user attempts to press the "Save" button present in the game activities while not logged in, a Toast will be prompted notifying the user that they must be logged in to save.

If the user is logged in, game states are automatically saved in these two games every three moves made.  The game can also be saved by pressing the "Save" button.  Once a save file exists for the game, the user can now load the game to resume from the last point of saving.

### Loading Saved Games

Two games offer loading functionality, namely **Sliding Tiles** and **4096**.

Games can be loaded by pressing the "Games" button, then the button corresponding to the desired game, and then the "Load Game" button.  If the user is not logged in, a Toast will be prompted that notifies the user that they must be logged in to load a game.  If the user does not have a save file for the chosen game, pressing the button will prompt another Toast that notifies the user that they do not have a save file for the game.  Otherwise, the saved game will be loaded, and the game will resume from the last point where it was saved.

### Difficulty and Game Complexity

Sliding Tiles is the only game that offers a choice between game complexities.  In this game,
difficulty can be selected before the game starts upon pressing the "Games" button, then the "Sliding Tiles" button and then the "New Game" button, starting from the main "Welcome Back" menu.  In this screen, the user can choose a difficulty from directly below the "Select Difficulty" text, between Easy, Medium, and Hard.  The difficulty selection will affect the board size, and the maximum number of stored undos.  Once the game begins with a given difficulty, the difficulty cannot be changed for the current game.  If no difficulty is selected, the default complexity is Easy.

The complexities and their corresponding board sizes and undo capacities are as follows:

- **Easy:**  3x3 board, unlimited undos
- **Medium:**  4x4 board, 5 undos
- **Hard:** 5x5 board, 3 undos

### Background Image

Sliding Tiles is the only game that offers a choice for background images.  In this game, difficulty can be selected before the game starts upon pressing the "Games" button, then the "Sliding Tiles" button and then the "New Game" button, starting from the main "Welcome Back" menu.  In this screen, the user can choose an image to use for the background of the tiles by pressing either the "Select Image From Gallery" or "Get Image From URL" buttons.

- **To use an image from the gallery:**
  Download an image from the internet.  By default, this should be downloaded to the "Downloads" directory.  If it is not, move the image into this directory.  Pressing the "Select Image From Gallery" button will prompt the user to select an image from all of the images stored within the directory.  The user can press the desired image to select it for use, and upon the game starting when the user presses the "Start Game!" button, that image will be used as the background.

- **To use an image from a URL:**
  Enter a valid image URL into the field in the top left.  Press the "Get Image From URL" button to retrieve the image from the URL, and select it for use.  Upon the game starting when the user presses the "Start Game!" button, that image will be used as the background. Entering an invalid URL into the field and attempting to select it will prompt a Toast that notifies the user of the invalid URL.

If the user has chosen an image and wants to return to the default background, the user may press the "Use Original" button to reset the background to default.

### Scoring and Scoreboard

Users are scored by how many moves they solve the puzzle in - the more moves, the worse they are rated.

Scoring is done in different ways for different games, and will only be stored when logged in.

- **For Sliding Tiles:**
  Score is based off the number of moves made.  The maximum scores will be earned when move numbers are low, and minimum scores will be earned when move numbers are high.  Scores are calculated when the user wins by puts the images in the correct order.

- **For Pipelines:**
  Score is based off the number of moves made.  The maximum scores will be earned when move numbers are low, and minimum scores will be earned when move numbers are high.  Scores are calculated when the user wins by completing a path from the beginning to end pipes.

- **For 4096:**
  Score is based off the number of tiles combined and the value of the tiles combined.  Scores are calculated when the user wins by combining up to the tile 4096, or loses by completely filling the board with tiles.

If a user plays a game and earns a score higher than their currently held high-score for that game, the high-score is replaced by the score.

The scores are stored within the accounts, and separate scoreboards for each game display the high-scores from each account from high to low.  The scoreboard activity can be accessed from the main "Welcome Back!" menu by pressing the "All Scores" button while logged in.  Scoreboards for each of the three games can be accessed through the bottom navigation display, with the default being the Sliding Tiles scoreboard.  The currently logged in user's high score for the game selected will be displayed, alongside a list of the high-scores for all the accounts stored.

### Undo Moves

Two games offer undo functionality, namely **Sliding Tiles** and **Pipelines**.  These two games have slightly different implementations of their undo functionality.

- **For Sliding Tiles:**
  The number of maximum undos stored is dependent on the complexity chosen before the game starts.  Easy allows unlimited undos, Medium allows five undos, and Hard allows three undos.
- **For Pipelines:**
  The number of maximum undos stored is set at a constant three.

As the user makes moves in these two games, a list of the most recent moves will be stored. When the user presses the "Undo" button within the games, the most recently made move will be undone, and the move will be removed from the list.  If the list is empty, then no move will be undone, and a Toast will be prompted, notifying the user that no undos are available.  Pressing the undo button will not reduce the number of points received from moves, as it will also decrement the move counter.

## Built With

- [Android Studio](https://developer.android.com/studio/) - The framework through which the whole project was built.

## Authors

- **[Viktar Chyhir](https://github.com/vicchig)**
- **[Christopher Wong](https://github.com/obelisk-c)**
- **[Thomas MacDonald](https://github.com/thomasdmacdonald)**
- **[Bryan Jiang](https://github.com/bry-jiang)**
- **[Aniket Kali](https://github.com/an-k45)**

## Acknowledgments

* README Template from https://gist.github.com/PurpleBooth/109311bb0361f32d87a2
