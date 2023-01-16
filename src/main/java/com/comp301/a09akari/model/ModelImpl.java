package com.comp301.a09akari.model;

import javafx.scene.control.Cell;

import java.util.ArrayList;

// remember this is a subject with respect to the observer design pattern
public class ModelImpl implements Model {
  PuzzleLibrary ModelPuzzleLibrary;
  private int index = 0; // this will keep track of which puzzle we are using
  private int[][] lampLocation; // should this be public or private? // this is just
  private final ArrayList<ModelObserver> observerList;

  public ModelImpl(PuzzleLibrary library) {
    // Your constructor code here
    this.ModelPuzzleLibrary = library;
    observerList = new ArrayList<>();
    lampLocation =
        new int[library.getPuzzle(index).getHeight()][library.getPuzzle(index).getWidth()];
  }

  @Override
  public void addLamp(int r, int c) {
    if (r >= ModelPuzzleLibrary.getPuzzle(index).getHeight() || r < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (c >= ModelPuzzleLibrary.getPuzzle(index).getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (ModelPuzzleLibrary.getPuzzle(index).getCellType(r, c) == CellType.CORRIDOR) {
      lampLocation[r][c] = 1;
    } else {
      throw new IllegalArgumentException("You can only place lamps on corridors!");
    }
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r >= ModelPuzzleLibrary.getPuzzle(index).getHeight() || r < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (c >= ModelPuzzleLibrary.getPuzzle(index).getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (lampLocation[r][c] != 1) {
      throw new IllegalArgumentException("There is no lamp here!");
    } else {
      lampLocation[r][c] = 0;
    }
    notifyObservers();
  }

  @Override
  public boolean isLit(int r, int c) {
    // base case: if there is a lamp in the same r or c
    // other cases: - there is a wall on any of the r and c values inbetween the lamp spot and lit
    // spot
    // - there is a wall that does not affect lit spot
    // - there are multiple lamps in the same row
    // - there are no lamps in row
    // determine lamp spot first

    int height = ModelPuzzleLibrary.getPuzzle(index).getHeight();
    int width = ModelPuzzleLibrary.getPuzzle(index).getWidth();

    // new approach. check up, down, left, and right from r,c. see whether there is a wall first or
    // a lamp first
    int upCounter = r;
    int downCounter = r;
    int leftCounter = c;
    int rightCounter = c;

    if (r >= ModelPuzzleLibrary.getPuzzle(index).getHeight() || r < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (c >= ModelPuzzleLibrary.getPuzzle(index).getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (ModelPuzzleLibrary.getPuzzle(index).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Only corridors can be lit!");
    }

    while (upCounter >= 0) {
      if (ModelPuzzleLibrary.getPuzzle(index).getCellType(upCounter, c) == CellType.CLUE
          || ModelPuzzleLibrary.getPuzzle(index).getCellType(upCounter, c) == CellType.WALL) {
        // if it hits the wall we can end this search
        break;
      }
      if (lampLocation[upCounter][c] == 1) {
        return true;
      }
      upCounter -= 1;
    }

    while (downCounter < (height)) {
      if (ModelPuzzleLibrary.getPuzzle(index).getCellType(downCounter, c) == CellType.CLUE
          || ModelPuzzleLibrary.getPuzzle(index).getCellType(downCounter, c) == CellType.WALL) {
        break;
      }
      if (lampLocation[downCounter][c] == 1) {
        return true;
      }
      downCounter += 1;
    }

    while (leftCounter >= 0) {
      if (ModelPuzzleLibrary.getPuzzle(index).getCellType(r, leftCounter) == CellType.CLUE
          || ModelPuzzleLibrary.getPuzzle(index).getCellType(r, leftCounter) == CellType.WALL) {
        break;
      }
      if (lampLocation[r][leftCounter] == 1) {
        return true;
      }
      leftCounter -= 1;
    }

    while (rightCounter < width) {
      if (ModelPuzzleLibrary.getPuzzle(index).getCellType(r, rightCounter) == CellType.CLUE
          || ModelPuzzleLibrary.getPuzzle(index).getCellType(r, rightCounter) == CellType.WALL) {
        break;
      }
      if (lampLocation[r][rightCounter] == 1) {
        return true;
      }
      rightCounter += 1;
    }

    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r >= ModelPuzzleLibrary.getPuzzle(index).getHeight() || r < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (c >= ModelPuzzleLibrary.getPuzzle(index).getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (ModelPuzzleLibrary.getPuzzle(index).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Only corridors can have a lamp!");
    }
    return lampLocation[r][c] == 1;
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r >= ModelPuzzleLibrary.getPuzzle(index).getHeight() || r < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (c >= ModelPuzzleLibrary.getPuzzle(index).getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (lampLocation[r][c] != 1) {
      throw new IllegalArgumentException("There is no lamp here!");
    }

    int upCounter = r;
    int downCounter = r;
    int leftCounter = c;
    int rightCounter = c;
    int heightNavigator = ModelPuzzleLibrary.getPuzzle(index).getHeight() - 1;
    int widthNavigator = ModelPuzzleLibrary.getPuzzle(index).getWidth() - 1;

    while (upCounter >= 0) {
      if (ModelPuzzleLibrary.getPuzzle(index).getCellType(upCounter, c) == CellType.CLUE
          || ModelPuzzleLibrary.getPuzzle(index).getCellType(upCounter, c) == CellType.WALL) {
        // if it hits the wall we can end this search
        break;
      }
      if (lampLocation[upCounter][c] == 1 && upCounter != r) {
        return true;
      }
      upCounter -= 1;
    }

    while (downCounter <= heightNavigator) {
      if (ModelPuzzleLibrary.getPuzzle(index).getCellType(downCounter, c) == CellType.CLUE
          || ModelPuzzleLibrary.getPuzzle(index).getCellType(downCounter, c) == CellType.WALL) {
        break;
      }
      if (lampLocation[downCounter][c] == 1 && downCounter != r) {
        return true;
      }
      downCounter += 1;
    }

    while (leftCounter >= 0) {
      if (ModelPuzzleLibrary.getPuzzle(index).getCellType(r, leftCounter) == CellType.CLUE
          || ModelPuzzleLibrary.getPuzzle(index).getCellType(r, leftCounter) == CellType.WALL) {
        break;
      }
      if (lampLocation[r][leftCounter] == 1 && leftCounter != c) {
        return true;
      }
      leftCounter -= 1;
    }

    while (rightCounter <= widthNavigator) {
      if (ModelPuzzleLibrary.getPuzzle(index).getCellType(r, rightCounter) == CellType.CLUE
          || ModelPuzzleLibrary.getPuzzle(index).getCellType(r, rightCounter) == CellType.WALL) {
        break;
      }
      if (lampLocation[r][rightCounter] == 1 && rightCounter != c) {
        return true;
      }
      rightCounter += 1;
    }

    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return ModelPuzzleLibrary.getPuzzle(index);
  }

  @Override
  public int getActivePuzzleIndex() {
    return index;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index > (ModelPuzzleLibrary.size()) - 1 || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    this.index = index;
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return ModelPuzzleLibrary.size();
  }

  @Override
  public void resetPuzzle() {
    lampLocation = new int[getActivePuzzle().getHeight()][getActivePuzzle().getWidth()];

    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    // every clue needs to be satisfied
    // every corridor needs to be lit up
    // no illegal lamps
    for (int row = 0; row < ModelPuzzleLibrary.getPuzzle(index).getHeight(); row++) {
      for (int col = 0; col < ModelPuzzleLibrary.getPuzzle(index).getWidth(); col++) {
        CellType cell = ModelPuzzleLibrary.getPuzzle(index).getCellType(row, col);
        if (cell == CellType.CLUE) {
          boolean clueStatus = isClueSatisfied(row, col);
          if (!clueStatus) {
            return false;
          }
        }
        if (cell == CellType.CORRIDOR) {
          if (isLamp(row, col)) {
            boolean lampStatus = isLampIllegal(row, col);
            if (lampStatus) {
              return false;
            }
          } else {
            if (!isLit(row, col)) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    int clueCount;

    if (r >= ModelPuzzleLibrary.getPuzzle(index).getHeight() || r < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (c >= ModelPuzzleLibrary.getPuzzle(index).getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (ModelPuzzleLibrary.getPuzzle(index).getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("This is not a clue!");
    }
    clueCount = checkDown(r, c) + checkUp(r, c) + checkLeft(r, c) + checkRight(r, c);

    return clueCount == ModelPuzzleLibrary.getPuzzle(index).getClue(r, c);
  }

  private int checkDown(int r, int c) {
    int heightNavigator = ModelPuzzleLibrary.getPuzzle(index).getHeight() - 1;
    int clueCounter = 0;
    if (r + 1 > heightNavigator) {
      return 0;
    }
    if (lampLocation[r + 1][c] == 1) {
      clueCounter += 1;
    }
    return clueCounter;
  }

  private int checkUp(int r, int c) {
    int clueCounter = 0;
    if (r - 1 < 0) {
      return 0;
    }
    if (lampLocation[r - 1][c] == 1) {
      clueCounter += 1;
    }
    return clueCounter;
  }

  private int checkRight(int r, int c) {
    int widthNavigator = ModelPuzzleLibrary.getPuzzle(index).getWidth() - 1;
    int clueCounter = 0;
    if (c + 1 > widthNavigator) {
      return 0;
    }
    if (lampLocation[r][c + 1] == 1) {
      clueCounter += 1;
    }
    return clueCounter;
  }

  private int checkLeft(int r, int c) {
    int clueCounter = 0;
    if (c - 1 < 0) {
      return 0;
    }
    if (lampLocation[r][c - 1] == 1) {
      clueCounter += 1;
    }
    return clueCounter;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observerList.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observerList.remove(observer);
  }

  public void notifyObservers() {
    for (ModelObserver m : observerList) {
      m.update(this);
    }
  }
}
