package fall2018.csc2017.GameCenter.GameManagers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.GameStructure.BoardPipelines;
import fall2018.csc2017.GameCenter.GameStructure.LimitedStack;
import fall2018.csc2017.GameCenter.GameStructure.MovePipelines;
import fall2018.csc2017.GameCenter.Scoring.Score;
import fall2018.csc2017.GameCenter.ScoringActivities.Scoreable;
import fall2018.csc2017.GameCenter.Tiles.Tile;
import fall2018.csc2017.GameCenter.Tiles.TilePipelines;
import fall2018.csc2017.GameCenter.Helper.PipelineBoardParameter;
import fall2018.csc2017.GameCenter.Helper.Position;

import static java.lang.Math.abs;

/**
 *
 */
public class BoardManagerPipelines extends BoardManager implements Scoreable {

    /**
     * If the board is solved or not
     */
    private boolean isWon;

    /**
     * Access point to the AccountManager instance
     */
    private AccountManager accManager = AccountManager.getInstance();

    /**
     * BoardManagerPipelines for managing a BoardPipelines game board.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     */
    public BoardManagerPipelines(int rows, int cols) {
        getRandomBoard(rows, cols);
        createUndoStack(rows);
        isWon = false;
    }

    @Override
    public void calculateUserScore() {
        if (AccountManager.getInstance().currentlyLoggedIn()) {
            this.updateScore(this.getMoves(), this.getBoard().getNumCols());

        }
    }

    @Override
    protected void makeMove(int position) {
        int row = position / getBoard().getNumCols();
        int col = position % getBoard().getNumRows();
        if (!isWon) {
            getBoard().swapTiles(row, col, -1, -1);
            if (isValidMove(position)) {
                this.setMoves(this.getMoves() + 1);
                this.getUndoStack().add(new MovePipelines(row, col));
            }
        }
        if (accManager.currentlyLoggedIn()) {
            accManager.getCurrentAccount().getLastManager(2).setScore(this.getScoreObject());
        }

    }

    @Override
    public void undoMove() {
        if (!this.getUndoStack().isEmpty() & !isWon) {
            MovePipelines move = (MovePipelines) this.getUndoStack().remove();
            for (int i = 0; i < 3; i++) {
                getBoard().swapTiles(move.getRow(), move.getCol(), -1, -1);
            }
            this.setMoves(this.getMoves() - 1);
        }
    }

    @Override
    public void updateScore(int moves, int difficulty) {
        if (accManager.currentlyLoggedIn()) {
            if (moves == 0 || moves >= 500) {
                this.setScore(new Score(0, accManager.getCurrentUser()));
            } else {
                this.setScore(new Score((int) (difficulty * 10 * ((-0.2 * moves) + 100)), accManager.getCurrentUser()));
            }
        }

    }

    @Override
    public boolean puzzleSolved() {
        int direction = -1;

        if (((BoardPipelines) getBoard()).getStartCol() == 0) {
            direction = 3;
        } else if (((BoardPipelines) getBoard()).getStartCol() == getBoard().getNumCols() - 1) {
            direction = 1;
        } else if (((BoardPipelines) getBoard()).getStartRow() == 0) {
            direction = 0;
        } else if (((BoardPipelines) getBoard()).getStartRow() == getBoard().getNumRows() - 1) {
            direction = 2;
        }

        if (pathToEnd(((BoardPipelines) getBoard()).getStartRow(), ((BoardPipelines) getBoard()).getStartCol(), direction, new ArrayList<TilePipelines>())) {
            isWon = true;
            calculateUserScore();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return whether there is a path to the end of the board.
     *
     * @param row         the row of the tile currently being checked
     * @param col         teh column of the tile currently being checked
     * @param arrivedFrom the direction index from which this tile arrived from, relative to this tile
     *                    0: up, 1: right, 2: bottom, 3: left
     * @param seenTiles   the tiles that have been seen on the recursive path
     * @return whether there is a path to the end of the board
     */
    private boolean pathToEnd(int row, int col, int arrivedFrom, ArrayList<TilePipelines> seenTiles) {
        TilePipelines currentTile = (TilePipelines) getBoard().getTiles()[row][col];
        if (row == ((BoardPipelines) getBoard()).getEndRow() & col == ((BoardPipelines) getBoard()).getEndCol()) {
            return true;
        } else if (isDeadEnd(arrivedFrom, currentTile.getHoles(), row, col)) {
            return false;
        } else if (seenTiles.contains(currentTile)) {
            return false;
        } else {
            ArrayList pathOutcomes = new ArrayList<Boolean>();
            for (int i = 0; i < 4; i++) {
                if (i != arrivedFrom && currentTile.getHoles()[i] == 1 && hasConnectionInDirection(i, row, col)) {
                    if (!((i == 0 & row == 0) | (i == 1 & col == getBoard().getNumCols() - 1) |
                            (i == 2 & row == getBoard().getNumRows() - 1) | (i == 3 & col == 0))) {
                        int dir = 0;
                        int newRow = 0;
                        int newCol = 0;
                        switch (i) {
                            case 0:
                                dir = 2;
                                newRow = row - 1;
                                newCol = col;
                                break;
                            case 1:
                                dir = 3;
                                newCol = col + 1;
                                newRow = row;
                                break;
                            case 2:
                                dir = 0;
                                newRow = row + 1;
                                newCol = col;
                                break;
                            case 3:
                                dir = 1;
                                newCol = col - 1;
                                newRow = row;
                                break;
                        }
                        seenTiles.add(currentTile);
                        pathOutcomes.add(pathToEnd(newRow, newCol, dir, seenTiles));
                    }
                }
            }
            return pathOutcomes.contains(true);
        }
    }

    /**
     * Return whether this tile is a dead end.
     *
     * @param arrivedFrom the direction that got to this tile, relative to this tile.
     *                    0: up, 1: right, 2: bottom, 3: left
     * @param holes       the direction in which this tile has holes
     * @param row         the row of this tile in the board
     * @param col         the column of this tile in the board
     * @return whether this tile is a dead end
     */
    private boolean isDeadEnd(int arrivedFrom, int[] holes, int row, int col) {
        for (int i = 0; i < holes.length; i++) {
            if (i != arrivedFrom & holes[i] == 1 & hasConnectionInDirection(i, row, col)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return whether there is a connection to another tile in this direction.
     *
     * @param direction the direction to be checked for a connection
     *                  0: up, 1: right, 2: down, 3: left
     * @param row       the row of this tile
     * @param col       the column of this tile
     * @return Return whether there is a connection to another tile in this direction.
     */
    private boolean hasConnectionInDirection(int direction, int row, int col) {
        int newCol = 0;
        int newRow = 0;
        switch (direction) {
            case 0:
                newRow = row - 1;
                newCol = col;
                break;
            case 1:
                newRow = row;
                newCol = col + 1;
                break;
            case 2:
                newRow = row + 1;
                newCol = col;
                break;
            case 3:
                newRow = row;
                newCol = col - 1;
                break;
        }

        if (newRow >= getBoard().getNumRows() | newCol >= getBoard().getNumCols() | newRow < 0 | newCol < 0) {
            return false;
        } else {
            int[] newTileHoles = ((TilePipelines) getBoard().getTiles()[newRow][newCol]).getHoles();
            int newTileArrivedFrom = newTileArrivedFrom(direction);
            return newTileHoles[newTileArrivedFrom] == 1;
        }
    }

    /**
     * Return the adjacency index that the new tile was reached from.
     *
     * @param num the adjacency index the old tile is going to
     * @return the adjacency index that the new tile was reached from
     */
    private int newTileArrivedFrom(int num) {
        switch (num) {
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 0;
            case 3:
                return 1;
            default:
                return 3;
        }
    }

    @Override
    public boolean isValidMove(int position) {
        int row = position / getBoard().getNumCols();
        int col = position % getBoard().getNumRows();

        return (!((row == ((BoardPipelines) getBoard()).getStartRow() && col == ((BoardPipelines) getBoard()).getStartCol()) ||
                (row == ((BoardPipelines) getBoard()).getEndRow() && col == ((BoardPipelines) getBoard()).getEndCol())));
    }

    /**
     * Return a list of tiles that is a solvable pipelines board
     * with a random start and end position that are opposite each other
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @return a list of tiles that is a solvable pipelines board
     */
    private BoardPipelines genSolvableBoard(int rows, int cols) {
        PipelineBoardParameter para = new PipelineBoardParameter(rows, cols);
        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < rows * cols; i++)
            tiles.add(new TilePipelines());

        int side = para.getStartSide();
        Position pos = new Position(para.getStartCol(), para.getStartRow());

        makeConnectedPath(tiles, pos, para, side);
        fillWithRandomTiles(tiles, rows, cols);

        return new BoardPipelines(tiles, para);
    }

    /**
     * Make a connected path of tiles form the start to the end of the board
     *
     * @param tiles List of tiles
     * @param pos   Current position of the tiles
     * @param para  Parameters of the board
     * @param side  The side there is a connection to
     */
    private void makeConnectedPath(List<Tile> tiles, Position pos, PipelineBoardParameter para, int side) {
        while (pos.getX() != para.getEndCol() & pos.getY() != para.getEndRow()) {
            makeRandomTileWithConnection(tiles, side, para, pos);
            side = chooseNewValidDirection(tiles, side, para, pos);
            switch (side) {
                case 2:
                    pos.incY(false);
                    break;
                case 3:
                    pos.incX(true);
                    break;
                case 0:
                    pos.incY(true);
                    break;
                case 1:
                    pos.incX(false);
                    break;
            }
        }
        addEndTile(tiles, side, para, pos);
        para.setEndCol(pos.getX());
        para.setEndRow(pos.getY());
    }

    /**
     * Given a current tile, choose a new direction to generate a tile int such that it is
     * guaranteed there can be a path from the new tile to the end of the board
     *
     * @param tiles list of tiles
     * @param side  current side connected to
     * @param para  parameter list for the board
     * @param pos   current x, y position
     * @return direction to move to
     */
    private int chooseNewValidDirection(List<Tile> tiles, int side, PipelineBoardParameter para, Position pos) {
        Random r = new Random();
        int newSide = r.nextInt(4);
        int startSide = para.getStartSide();
        int rows = para.getRows();
        int cols = para.getCols();
        int x = pos.getX();
        int y = pos.getY();

        while (side == newSide | ((TilePipelines) tiles.get(x + rows * y)).getHoles()[newSide] == 0 |
                newSide == startSide | (newSide == 0 & y == 0) | (newSide == 1 & x == cols - 1) |
                (newSide == 2 & y == rows - 1) | (newSide == 3 & x == 0)) {
            newSide = r.nextInt(4);
        }
        return newTileArrivedFrom(newSide);
    }

    /**
     * Gives a list of tiles that already had a connected path, add another
     * random tile to the connected path
     *
     * @param tiles list of tiles
     * @param side  side the current tile is connected to
     * @param para  parameter list for the board
     * @param pos   current x, y position
     */
    private void makeRandomTileWithConnection(List<Tile> tiles, int side, PipelineBoardParameter para, Position pos) {
        int[] newId = generateNewId(side, pos, para);
        addNewTile(tiles, pos, para, newId);
    }

    /**
     * Generates a new id for a tile given the side it has arrived from
     *
     * @param side The side with a connection to the tile
     * @param pos  Position of the tile
     * @param para Parameters of the board
     * @return A new id
     */
    private int[] generateNewId(int side, Position pos, PipelineBoardParameter para) {
        Random r = new Random();
        int[] newId = new int[4];
        do {
            for (int i = 0; i < 4; i++) {
                if (i == side)
                    newId[i] = 1;
                else
                    newId[i] = r.nextInt(2);
            }
        }
        while (validConnection(newId, pos.getX(), 0, side, para.getStartSide(), 3) |
                validConnection(newId, pos.getX(), para.getCols() - 1, side, para.getStartSide(), 1) |
                validConnection(newId, pos.getY(), 0, side, para.getStartSide(), 0) |
                validConnection(newId, pos.getY(), para.getRows() - 1, side, para.getStartSide(), 2) |
                (newId[newTileArrivedFrom(para.getStartSide())] == 0 & newId[newTileArrivedFrom(side)] == 0));
        return newId;
    }

    /**
     * Checks if a new tile will be able to make any connections to more tiles
     *
     * @param newId     Id proposed
     * @param pos       current position of tile
     * @param edge      adjacent edge of the board
     * @param side      Side the connection is coming from
     * @param startSide Starting side of the board
     * @param otherSide Side opposite to the adjacent side
     * @return If a valid connection can be made
     */
    private boolean validConnection(int[] newId, int pos, int edge, int side, int startSide, int otherSide) {
        int[] sides = otherSides(side, otherSide, startSide);
        return (pos == edge && newId[sides[0]] != 1 && newId[sides[1]] != 1);
    }

    /**
     * Adds a new tile with a given ID to a list of tiles at the correct position
     *
     * @param tiles List of tiles representing board
     * @param pos   Position of new tile
     * @param para  Parameters of board
     * @param id    Id of new tile
     */
    private void addNewTile(List<Tile> tiles, Position pos, PipelineBoardParameter para, int[] id) {
        if (pos.getX() == para.getStartCol() & pos.getY() == para.getStartRow()) {
            TilePipelines t = new TilePipelines(id, true);
            tiles.set(pos.getX() + pos.getY() * para.getRows(), t);
        } else {
            TilePipelines t = new TilePipelines(id, false);
            tiles.set(pos.getX() + pos.getY() * para.getRows(), t);
        }
    }

    /**
     * Adds an ending tile to a board
     *
     * @param tiles list of tiles
     * @param side  current side
     * @param para  parameter list for the board
     * @param pos   current x, y position
     */
    private void addEndTile(List<Tile> tiles, int side, PipelineBoardParameter para, Position pos) {
        int startSide = para.getStartSide();
        int x = pos.getX();
        int y = pos.getY();
        int rows = para.getRows();

        Random r = new Random();
        int[] newId = new int[4];
        for (int i = 0; i < 4; i++) {
            newId[i] = (i == side | abs(i - startSide) == 2) ? 1 : r.nextInt(2);
        }
        tiles.set(y * rows + x, new TilePipelines(newId, true));

    }

    /**
     * Fill an list of tiles with random tiles where there are empty tiles
     *
     * @param tiles list of tiles
     * @param rows  row size of board
     * @param cols  column size of board
     */
    private void fillWithRandomTiles(List<Tile> tiles, int rows, int cols) {
        Random r = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int tileIndex = i * rows + j;
                TilePipelines check = (TilePipelines) tiles.get(tileIndex);
                if (check.getHoles()[0] == -1) {
                    genRandomTile(tiles, r, tileIndex);
                }
            }
        }
    }

    /**
     * Generate a random tile to be put into the tiles at the appropriate location.
     *
     * @param tiles     the list of tiles
     * @param r         the randomly chosen number for creating new tiles.
     * @param tileIndex the index to insert a tile at
     */
    private void genRandomTile(List<Tile> tiles, Random r, int tileIndex) {
        int[] newTile = new int[4];
        while (newTile[0] + newTile[1] + newTile[2] + newTile[3] < 2) {
            for (int m = 0; m < 4; m++)
                newTile[m] = r.nextInt(2);
        }
        tiles.set(tileIndex, new TilePipelines(newTile, false));
    }

    /**
     * Returns the set {0, 1, 2, 3} not including sides passed in.
     *
     * @param side1 A side. Must be in {0, 1, 2, 3}
     * @param side2 Other side. Must be in {0, 1, 2, 3}.
     * @param side3 Last side. Must be in {0, 1, 2, 3}.
     * @return the set {0, 1, 2, 3} not including sides passed in
     */
    private int[] otherSides(int side1, int side2, int side3) {
        int[] sides4 = {0, 1, 2, 3};
        int[] sides2 = new int[3];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (i != side1 & i != side2 & i != side3) {
                sides2[count] = sides4[i];
                count++;
            }
        }

        switch (count) {
            case 1:
                return new int[]{sides2[0], sides2[0]};
            case 2:
                return sides2;
            default:
                return new int[]{sides2[0], sides2[0]};
        }
    }

    /**
     * Initialize an UndoStack to store the proper number of undos based on number of rows.
     *
     * @param rows number of rows in the current board
     */
    private void createUndoStack(int rows) {
        if (rows == 3) {
            this.setUndoStack(new LimitedStack(-1));
        } else if (rows == 4) {
            this.setUndoStack(new LimitedStack(5));
        } else {
            this.setUndoStack(new LimitedStack(3));
        }
        setChanged();
        notifyObservers();
    }


    /**
     * Generate a randomized, unsolved board of size rows*cols
     *
     * @param rows number of rows in board
     * @param cols number of cols in board
     */
    private void getRandomBoard(int rows, int cols) {
        setBoard(genSolvableBoard(rows, cols));
        int unsolvableCheck = 5;
        while (puzzleSolved()) {
            if (unsolvableCheck > 0) {
                ((BoardPipelines) getBoard()).randomizeTiles();
                unsolvableCheck--;
            } else {
                setBoard(genSolvableBoard(rows, cols));
                unsolvableCheck = 5;
            }
        }
    }
}
