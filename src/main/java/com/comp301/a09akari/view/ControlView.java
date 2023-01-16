package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.lang.ModuleLayer.Controller;
import javafx.scene.control.Button;

public class ControlView implements FXComponent {
  private AlternateMvcController controller;
  // private GridPane controlBoard;

  public ControlView(AlternateMvcController controller) {
    this.controller = controller;
    // this.controlBoard = controlBoard;

  }

  @Override
  public Parent render() {

    // NEXT BUTTON
    Button nextPuzzle = new Button("NEXT");
    nextPuzzle.setOnAction(
        actionEvent -> {
          controller.clickResetPuzzle();
          controller.clickNextPuzzle();
        });

    // PREV BUTTON
    Button prevPuzzle = new Button("PREVIOUS");
    prevPuzzle.setOnAction(
        actionEvent -> {
          controller.clickResetPuzzle();
          controller.clickPrevPuzzle();
        });

    // RANDOM PUZZLE
    Button randomPuzzle = new Button("RANDOM");
    randomPuzzle.setOnAction(
        actionEvent -> {
          controller.clickResetPuzzle();
          controller.clickRandPuzzle();
        });

    // RESET PUZZLE
    Button resetPuzzle = new Button("RESET");
    resetPuzzle.setOnAction(
        actionEvent -> {
          controller.clickResetPuzzle();
        });

    String indexLabel = String.valueOf(controller.getActivePuzzleIndex() + 1);

    HBox buttonWindow = new HBox(prevPuzzle, nextPuzzle, randomPuzzle, resetPuzzle);

    return buttonWindow;
  }
}
