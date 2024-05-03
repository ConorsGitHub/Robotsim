package Robotsim;

import java.io.File;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/**
 * Interface class represents the main GUI for the robot simulation.
 * It extends the JavaFX Application class.
 */
public class Interface extends Application {
    private MyCanvas mc;
    private AnimationTimer timer;  // Timer used for animation
    private VBox rtPane;           // Vertical box for putting info
    private RobotArena arena;
    private FileChooser fileChooser;

    /**
     * Displays an information dialog about the robot simulation.
     */
    private void showAbout() {
        Alert alert = new Alert(AlertType.INFORMATION); // Define information dialog
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Robot simulation: 31003040\n The gamerobot scores points on contact with target \n " +
                "the EvilRobot destroys the GameRobot on contact \n " +
                "The Blocker blocks all other Robots \n " +
                "the WhiskerRobot can detect objects with its whiskers \n " +
                "The BeamRobot can detect objects within the beam.");
        alert.showAndWait(); // Show the dialog and wait for user to close
    }
    /**
     * Sets up mouse events for the given canvas.
     *
     * @param canvas Canvas for which mouse events are set up.
     */
    void setMouseEvents(Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Robot selectedRobot = arena.getRobotAtPosition(e.getX(), e.getY());
                if (selectedRobot != null) {
                    // Store the selected robot for moving
                    arena.setSelectedRobot(selectedRobot);
                }
            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (arena.getSelectedRobot() != null) {
                    // Move the selected robot
                    arena.getSelectedRobot().setXY(e.getX(), e.getY());
                    drawWorld(); // Redraw the world with the new robot position
                }
            }
        });
    }
    /**
     * Sets up the menu bar with various menu items.
     * @return MenuBar object.
     */
    MenuBar setMenu() {
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Robot Files", "*.cfg");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        MenuBar menuBar = new MenuBar();  // Create main menu
        Menu mAdd = new Menu("Add");
        Menu mFile = new Menu("File");  // Add File main menu
        
        MenuItem mLoad = new MenuItem("Load");
        mLoad.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                    int loadStatus = arena.loadFile(selectedFile.getAbsolutePath());
                    if (loadStatus == 0) {
                        showMessage("Success", "File loaded successfully.");
                    } else {
                        showMessage("Error", "Failed to load file.");
                    }
                }
            }
        });

        // Add Save button
        MenuItem mSave = new MenuItem("Save");
        mSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                File selectedFile = fileChooser.showSaveDialog(null);
                if (selectedFile != null) {
                    int saveStatus = arena.saveFile(selectedFile.getAbsolutePath());
                    if (saveStatus == 0) {
                        showMessage("Success", "File saved successfully.");
                    } else {
                        showMessage("Error", "Failed to save file.");
                    }
                }
            }
        });
        MenuItem mExit = new MenuItem("Exit");  // Whose sub-menu has Exit
        mExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                timer.stop(); // Stop the timer
                System.exit(0); // Exit the program
            }
        });
        MenuItem mAddG = new MenuItem("GameRobot");
        mAddG.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                arena.addGRobot();  // And its action to stop the timer
                drawWorld();
            }
        });
        MenuItem mAddE = new MenuItem("EvilRobot");
        mAddE.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                arena.addERobot();  // And its action to stop the timer
                drawWorld();
            }
        });
        MenuItem mAddW = new MenuItem("WhiskerRobot");
        mAddW.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                arena.addWRobot();  // And its action to stop the timer
                drawWorld();
            }
        });
        MenuItem mAddBe = new MenuItem("BeamRobot");
        mAddBe.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                arena.addBeRobot();  // And its action to stop the timer
                drawWorld();
            }
        });
        MenuItem mAddBu = new MenuItem("BumperRobot");
        mAddBu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                arena.addBuRobot();  // And its action to stop the timer
                drawWorld();
            }
        });
        MenuItem mAddS = new MenuItem("SliderRobot");
        mAddS.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                arena.addSRobot();  // And its action to stop the timer
                drawWorld();
            }
        });
        mFile.getItems().addAll(mSave, mLoad, mExit);  // Add exit to File menu
        Menu mHelp = new Menu("Help");  // Create Help menu
        MenuItem mAbout = new MenuItem("About");  // Add About sub-menu item
        mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showAbout();  // And its action to print about
            }
        });
        mHelp.getItems().addAll(mAbout);  // Add About to Help main item
        mAdd.getItems().addAll(mAddG, mAddE, mAddW, mAddBu, mAddBe, mAddS);
        menuBar.getMenus().addAll(mFile, mHelp, mAdd);  // Set main menu with File, Help
        return menuBar;  // Return the menu
    }
    /**
     * Sets up buttons for starting, pausing, adding, and deleting robots.
     * @return HBox containing the buttons.
     */
    private HBox setButtons() {
        Button btnStart = new Button("Start");  // Create button for starting
        btnStart.setOnAction(new EventHandler<ActionEvent>() {  // Define event when it is pressed
            @Override
            public void handle(ActionEvent event) {
                timer.start();  // Its action is to start the timer
            }
        });
        Button btnStop = new Button("Pause");  // Now button for stopping
        btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timer.stop();  // And its action to stop the timer
            }
        });
        Button btnAddBl = new Button("Blocker");  // Now button for stopping
        btnAddBl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arena.addBRobot();  // And its action to stop the timer
                drawWorld();
            }
        });
        Button btnDel = new Button("Delete");
        btnDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (arena.getSelectedRobot() != null) {
                    arena.deleteRobot(arena.getSelectedRobot());
                    arena.setSelectedRobot(null); // Clear selection after deletion
                    drawWorld(); // Redraw the world
                }
            }
        });   // Now add these buttons + labels to an HBox
        return new HBox(new Label("Run: "), btnStart, btnStop, new Label("Add: "), btnAddBl, btnDel);
    }
    /**
     * Show the score by writing it at position x, y.
     *
     * @param x     X-coordinate.
     * @param y     Y-coordinate.
     * @param score Score to be displayed.
     */
    public void showScore(double x, double y, int score) {
        mc.showText(x, y, Integer.toString(score));
    }
    /**
     * Draws the world, clearing the canvas and redrawing the arena.
     */
    public void drawWorld() {
        mc.clearCanvas();  // Set beige color
        arena.drawArena(mc);
    }
    /**
     * Draws the status of the robots in the right pane.
     */
    public void drawStatus() {
        rtPane.getChildren().clear();  // Clear right pane
        ArrayList<String> allBs = arena.describeAll();
        for (String s : allBs) {
            Label l = new Label(s);  // Turn description into a label
            rtPane.getChildren().add(l);  // Add label
        }
    }
    /**
     * Start method, initializes and sets up the GUI.
     *
     * @param primaryStage The primary stage for the application.
     * @throws Exception If any exception occurs.
     */
    private void showMessage(String title, String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Conor's attempt at Moving Robot");
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 20, 10, 20));

        bp.setTop(setMenu());  // Put menu at the top

        Group root = new Group();  // Create group with canvas
        Canvas canvas = new Canvas(400, 500);
        root.getChildren().add(canvas);
        bp.setLeft(root);  // Load canvas to the left area

        mc = new MyCanvas(canvas.getGraphicsContext2D(), 400, 500);

        setMouseEvents(canvas);  // Set up mouse events

        arena = new RobotArena(400, 500);  // Set up arena
        drawWorld();

        timer = new AnimationTimer() {  // Set up timer
            public void handle(long currentNanoTime) {  // And its action when on
                arena.checkRobots();  // Check the angle of all Robots
                arena.adjustRobots();  // Move all Robots
                drawWorld();  // Redraw the world
                drawStatus();  // Indicate where Robots are
            }
        };
        rtPane = new VBox();  // Set VBox on the right to list items
        rtPane.setAlignment(Pos.TOP_LEFT);  // Set alignment
        rtPane.setPadding(new Insets(5, 75, 75, 5));  // Padding
        bp.setRight(rtPane);  // Add rtPane to BorderPane right
        bp.setBottom(setButtons());  // Set bottom pane with buttons
        Scene scene = new Scene(bp, 700, 600);  // Set overall scene
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * Main method, launches the GUI.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Application.launch(args);  // Launch the GUI
    }
}