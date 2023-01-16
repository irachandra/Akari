package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class AppLauncher extends Application {

  @Override
  public void start(Stage stage) {
    PuzzleLibraryImpl library = new PuzzleLibraryImpl();

    stage.setTitle("AKARI");

    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01)); // adding to library
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));

    Model model = new ModelImpl(library); // instantiates model with library (state of the app)
    AlternateMvcController controller = new ControllerImpl(model); // instantiates controller
    PuzzleView puzzleView = new PuzzleView(controller); // instantiates puzzleView
    ControlView controlView = new ControlView(controller); // instantiates controlView
    InfoView infoView = new InfoView(controller);

    VBox gui = new VBox(infoView.render(), controlView.render(), puzzleView.render());
    Scene scene =
        new Scene(
            gui, 500,
            500); // represents the complete contents of the window. displayed on the stage
    scene.getStylesheets().add("main.css");

    stage.setScene(scene);

    // observer to update model. if model changes at all clear and update the gui
    model.addObserver(
        (Model m) -> {
          gui.getChildren().clear();
          gui.getChildren().add(infoView.render());
          gui.getChildren().add(controlView.render());
          gui.getChildren().add(puzzleView.render());
        });
    stage.show();

  }
}
