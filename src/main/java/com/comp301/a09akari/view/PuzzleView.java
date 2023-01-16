package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class PuzzleView implements FXComponent {
  private final AlternateMvcController controller;

  private final GridPane puzzleGrid = new GridPane();

  public PuzzleView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {

    return createGrid();
  }

  public Parent createGrid() {

    GridPane gameGrid = new GridPane();

    gameGrid.gridLinesVisibleProperty();

    int width = controller.getActivePuzzle().getWidth();
    int height = controller.getActivePuzzle().getHeight();

    // differennt types of tiles for boards: lamp (legal, illegal), clues (0, 1, 2, 3, 4), wall,
    // corridor (lit, unlit)

    // scans each part of board
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        // checks what each cell is
        CellType cell = controller.getActivePuzzle().getCellType(row, col);

        // if the cell is a clue, distinguishes the different clues
        if (cell == CellType.CLUE) {
          int clue = controller.getActivePuzzle().getClue(row, col);
          if (clue == 0) {
            Label clueZero = new Label("0");
            clueZero.getStyleClass().add("clue");
            gameGrid.add(clueZero, col, row);
            if (controller.isClueSatisfied(row, col)) {
              clueZero.getStyleClass().add("happyclue");
            }
          }
          if (clue == 1) {
            Label clueOne = new Label("1");
            clueOne.getStyleClass().add("clue");
            gameGrid.add(clueOne, col, row);
            if (controller.isClueSatisfied(row, col)) {
              clueOne.getStyleClass().add("happyclue");
            }
          }
          if (clue == 2) {
            Label clueTwo = new Label("2");
            clueTwo.getStyleClass().add("clue");
            gameGrid.add(clueTwo, col, row);
            if (controller.isClueSatisfied(row, col)) {
              clueTwo.getStyleClass().add("happyclue");
            }
          }
          if (clue == 3) {
            Label clueThree = new Label("3");
            clueThree.getStyleClass().add("clue");
            gameGrid.add(clueThree, col, row);
            if (controller.isClueSatisfied(row, col)) {
              clueThree.getStyleClass().add("happyclue");
            }
          }
          if (clue == 4) {
            Label clueFour = new Label("4");
            clueFour.getStyleClass().add("clue");
            gameGrid.add(clueFour, col, row);
            if (controller.isClueSatisfied(row, col)) {
              clueFour.getStyleClass().add("happyclue");
            }
          }
        }

        // If the cell is a wall, it is labeled as a "Wall".
        if (cell == CellType.WALL) {
          Label wallLabel = new Label();
          wallLabel.getStyleClass().add("clue");
          gameGrid.add(wallLabel, col, row);
        }

        // If the cell is a wall, it is labeled as a "Corridor".
        if (cell == CellType.CORRIDOR) {
          Button corridorButton = new Button();
          corridorButton.getStyleClass().add("corridor");
          gameGrid.add(corridorButton, col, row);

          // if a corridor is lit, changes the label to "lit"
          if (controller.isLit(row, col) && !controller.isLamp(row, col)) {
            corridorButton.getStyleClass().add("litcorridor");
          }

          int r = row;
          int c = col;
          if (controller.isLamp(r, c)) {
            if (controller.isLampIllegal(r, c)) {
              // corridorButton.setText("BAD LAMP");
              corridorButton.getStyleClass().add("unhappylamp");
              Image bulb = new Image("light-bulb.png");
              ImageView bulbView = new ImageView();
              bulbView.setImage(bulb);
              bulbView.setFitHeight(20);
              bulbView.setFitWidth(20);
              corridorButton.setGraphic(bulbView);

            } else {
              // corridorButton.setText("GOOD LAMP");
              corridorButton.getStyleClass().add("happylamp");
              Image bulb = new Image("light-bulb.png");
              ImageView bulbView = new ImageView();
              bulbView.setImage(bulb);
              bulbView.setFitHeight(20);
              bulbView.setFitWidth(20);
              corridorButton.setGraphic(bulbView);
            }
          }
          corridorButton.setOnAction(
              actionEvent -> {
                if (controller.isLit(r, c)) {
                  corridorButton.setText("BAD LAMP");
                  controller.clickCell(r, c);
                } else {
                  corridorButton.setText("GOOD LAMP");
                  controller.clickCell(r, c);
                }
              });
        }
      }
    }

    Label solvedLabel = new Label();

    StackPane everythingPuzzle = new StackPane();

    everythingPuzzle.setAlignment(solvedLabel, Pos.CENTER);

    everythingPuzzle.getChildren().addAll(gameGrid);

    return everythingPuzzle;
  }
}