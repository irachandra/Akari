package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private int[][] board;

  public PuzzleImpl(int[][] board) {
    this.board = board;
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r >= getHeight() || c >= getWidth()) { // change that i made
      throw new IndexOutOfBoundsException();
    }
    int cell = board[r][c];
    // need to throw Throws an IndexOutOfBounds
    //   * exception if r or c is out of bounds

    if (cell <= 4) {
      return CellType.CLUE;
    } else if (cell == 5) {
      return CellType.WALL;
    } else if (cell == 6) {
      return CellType.CORRIDOR;
    }
    return null;
  }

  @Override
  public int getClue(int r, int c) {
    int clue = board[r][c];
    if (clue > 4 || clue < 0) {
      throw new IllegalArgumentException();
    }
    return clue;
  }
}
