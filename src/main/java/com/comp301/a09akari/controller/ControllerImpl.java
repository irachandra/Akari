package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Puzzle;

import com.comp301.a09akari.model.Model;

public class ControllerImpl implements AlternateMvcController {
  private Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    if ((model.getActivePuzzleIndex() + 1) >= model.getPuzzleLibrarySize()) {
      return;
    }
    model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
  }

  @Override
  public void clickPrevPuzzle() {
    if ((model.getActivePuzzleIndex() - 1) < 0) {
      return;
    }
    model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
  }

  @Override
  public void clickRandPuzzle() {
    int upperRange = model.getPuzzleLibrarySize();
    int activeIndex = model.getActivePuzzleIndex();
    int randomIndex = getRandomNumber(upperRange);

    while (randomIndex == activeIndex) {
      randomIndex = getRandomNumber(upperRange);
    }

    model.setActivePuzzleIndex(randomIndex);
  }

  public int getRandomNumber(int max) {
    return (int) (Math.random() * max);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    // this may not be right
    if (model.isLamp(r, c)) {
      model.removeLamp(r, c);
    } else {
      model.addLamp(r, c);
    }
    // model.removeLamp(r, c);

  }

  @Override
  public boolean isLit(int r, int c) {
    return model.isLit(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    return model.isLamp(r, c);
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    return model.isClueSatisfied(r, c);
  }

  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    return model.isLampIllegal(r, c);
  }

  @Override
  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }

  @Override
  public int getActivePuzzleIndex() {
    return model.getActivePuzzleIndex();
  }
}
