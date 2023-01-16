package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class InfoView implements FXComponent {

  private AlternateMvcController controller;
  // private GridPane controlBoard;

  public InfoView(AlternateMvcController controller) {
    this.controller = controller;
    // this.controlBoard = controlBoard;

  }

  @Override
  public Parent render() {

    Label solved = new Label("PUZZLE STATUS: UNSOLVED");
    solved.getStyleClass().add("toplabels");
    if (controller.isSolved()) {
      // return solvedSign;
      solved = new Label("PUZZLE STATUS: SOLVED");
      solved.getStyleClass().add("topsolved");
    }

    String indexLabel = String.valueOf(controller.getActivePuzzleIndex() + 1);
    Label puzzleCount = new Label("ACTIVE PUZZLE: " + indexLabel + "/5");
    puzzleCount.getStyleClass().add("toplabels");

    HBox topView = new HBox(solved, puzzleCount);
    return topView;
  }
}
