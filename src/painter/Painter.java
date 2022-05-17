
package painter;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.application.Application;
import static javafx.application.ConditionalFeature.FXML;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.*;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Painter extends Application {
    
    
    
    
    @Override
    public void start(Stage primaryStage) {
        
              
        Stack<Shape> undoHistory = new Stack();
        Stack<Shape> redoHistory = new Stack();
        MenuBar menuBar = new MenuBar();   
        //File menu
        TextArea text1 = new TextArea();
        text1.setPrefRowCount(4);
        text1.setEditable(false);
        text1.setMouseTransparent(true);
        text1.setFocusTraversable(false);
        
        Menu fileMenu = new Menu("File");//create a File menu
        fileMenu.setOnShowing(e -> { text1.setText("Showing File Menu "); });
        //fileMenu.setOnShown  (e -> { System.out.println("Shown File Menu"); });
        fileMenu.setOnHiding (e -> { text1.setText("Hiding File Menu"); });
        //fileMenu.setOnHidden (e -> { System.out.println("Hidden File Menu"); });
        
        Menu editMenu = new Menu("Edit");//create a Edit sub menu with another sub menus
        MenuItem undoMenu = new MenuItem("Undo");
        editMenu.getItems().add(undoMenu);//add undo submenu to edit menu 
        MenuItem exitMenu = new MenuItem("Exit");
        
        //set a menuitem event
        exitMenu.setOnAction(e -> {
        System.exit(0);
        });
        //add open, edit, separator and exit to a File menu

        //build shape menu item
        Menu shapeMenu = new Menu("Shape");
 
        RadioMenuItem circleOption = new RadioMenuItem("Circle");
        circleOption.setSelected(true);
        RadioMenuItem elpsleOption = new RadioMenuItem("Oval");
        RadioMenuItem rectangleOption = new RadioMenuItem("Rectangle");
        RadioMenuItem squareOption = new RadioMenuItem("Square");
        RadioMenuItem lineOption = new RadioMenuItem("Line");

        ToggleGroup shapeGroup = new ToggleGroup();//need the toggle group
        shapeGroup.getToggles().add(elpsleOption);
        shapeGroup.getToggles().add(rectangleOption);
        shapeGroup.getToggles().add(squareOption);
        shapeGroup.getToggles().add(lineOption);
        shapeMenu.getItems().add(elpsleOption);
        shapeMenu.getItems().add(rectangleOption);
        shapeMenu.getItems().add(squareOption);
        shapeMenu.getItems().add(lineOption);
        //end shape menu 
        
        fileMenu.getItems().addAll(shapeMenu, editMenu, new SeparatorMenuItem(), exitMenu);

        //Text menu
        Menu textMenu = new Menu("Text");
                //Color sub menu includes four radio menu items
        Menu colorMenu = new Menu("Color");
        
    	RadioMenuItem blackOption = new RadioMenuItem("Black");
        blackOption.setSelected(true);
        RadioMenuItem blueOption = new RadioMenuItem("Blue");
        RadioMenuItem redOption = new RadioMenuItem("Red");
        RadioMenuItem greenOption = new RadioMenuItem("Green");
        ToggleGroup colorGroup = new ToggleGroup();//need the toggle group
        colorGroup.getToggles().add(blackOption);
        colorGroup.getToggles().add(blueOption);
        colorGroup.getToggles().add(redOption);
        colorGroup.getToggles().add(greenOption);
        colorMenu.getItems().add(blackOption);
        colorMenu.getItems().add(blueOption);
        colorMenu.getItems().add(redOption);
        colorMenu.getItems().add(greenOption);
        
        //Fontsub menu includes two check menu items
        Menu fontMenu = new Menu("Font");
        CheckMenuItem boldMenuItem = new CheckMenuItem("Bold");
        CheckMenuItem italicMenuItem = new CheckMenuItem("Italic");
        fontMenu.getItems().addAll(boldMenuItem, italicMenuItem);
        
        
        textMenu.getItems().addAll(colorMenu, fontMenu);	

        menuBar.getMenus().addAll(fileMenu, textMenu);//adding a fileMenu to a MenuBar 

        VBox vBox = new VBox(menuBar);//Adding a MenuBar to the Scene Graph
        VBox vBox1 = new VBox(text1);
        
        
       
        /* ----------btns---------- */
     
        
        
        
        ToggleButton textbtn = new ToggleButton("Text");
        
        ToggleButton[] toolsArr = { textbtn};
        
        ToggleGroup tools = new ToggleGroup();
        
        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
        
        ColorPicker cpLine = new ColorPicker(Color.BLACK);
        ColorPicker cpFill = new ColorPicker(Color.TRANSPARENT);
        
        TextArea text = new TextArea();
        text.setPrefRowCount(1);
        
        Slider slider = new Slider(1, 50, 3);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        
        Label line_color = new Label("Line Color");
        Label fill_color = new Label("Fill Color");
        Label line_width = new Label("Pen Size");
        
        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        Button save = new Button("Save");
        
        
        Button[] basicArr = {undo, redo, save};

        for(Button btn : basicArr) {
            btn.setMinWidth(90);
            btn.setCursor(Cursor.HAND);
            btn.setTextFill(Color.WHITE);
            btn.setStyle("-fx-background-color: #666;");
        }
        save.setStyle("-fx-background-color: #80334d;");
        
        
        VBox btns = new VBox(10);
        btns.getChildren().addAll(
                textbtn, text, line_color, cpLine, fill_color, cpFill, line_width, slider, undo, redo, save);
        btns.setPadding(new Insets(5));
        btns.setStyle("-fx-background-color: #999");
        btns.setPrefWidth(100);
        
        /* ----------Drow Canvas---------- */
        Canvas canvas = new Canvas(1080, 790);
        GraphicsContext gc;
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        
        Line line = new Line();
        Rectangle rect = new Rectangle();
        Rectangle sqr = new Rectangle();
        Circle circ = new Circle();
        Ellipse elps = new Ellipse();
                        
        canvas.setOnMousePressed(e->{
            
        
            if(squareOption.isSelected()) {
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                sqr.setX(e.getX());                
                sqr.setY(e.getY());
            }
            
            else if(rectangleOption.isSelected()) {
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                rect.setX(e.getX());                
                rect.setY(e.getY());
            }
            else if(circleOption.isSelected()) {
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                circ.setCenterX(e.getX());
                circ.setCenterY(e.getY());
            }
            else if(elpsleOption.isSelected()) {
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                elps.setCenterX(e.getX());
                elps.setCenterY(e.getY());
            }
            else if(textbtn.isSelected()) {
                gc.setLineWidth(1);
                gc.setFont(Font.font(slider.getValue()));
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                gc.fillText(text.getText(), e.getX(), e.getY());
                gc.strokeText(text.getText(), e.getX(), e.getY());
            }
        });
        
        canvas.setOnMouseDragged(e->{
           
            
        });
        
        canvas.setOnMouseReleased(e->{
            
            
            if(lineOption.isSelected()) {
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                
                undoHistory.push(new Line(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()));
            }
            else if(rectangleOption.isSelected()) {
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                //rect.setX((rect.getX() > e.getX()) ? e.getX(): rect.getX());
                if(rect.getX() > e.getX()) {
                    rect.setX(e.getX());
                }
                //rect.setY((rect.getY() > e.getY()) ? e.getY(): rect.getY());
                if(rect.getY() > e.getY()) {
                    rect.setY(e.getY());
                }

                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                
                undoHistory.push(new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));
                
            }
             else if(rectangleOption.isSelected()) {
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                //rect.setX((rect.getX() > e.getX()) ? e.getX(): rect.getX());
                if(rect.getX() > e.getX()) {
                    rect.setX(e.getX());
                }
                //rect.setY((rect.getY() > e.getY()) ? e.getY(): rect.getY());
                if(rect.getY() > e.getY()) {
                    rect.setY(e.getY());
                }

                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                
                undoHistory.push(new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));
                
            }
              else if(squareOption.isSelected()) {
                sqr.setWidth(Math.abs((e.getX() - sqr.getX())));
                sqr.setHeight(Math.abs((e.getY() - sqr.getY())));
                //rect.setX((rect.getX() > e.getX()) ? e.getX(): rect.getX());
                if(sqr.getX() > e.getX()) {
                    sqr.setX(e.getX());
                }
                //rect.setY((rect.getY() > e.getY()) ? e.getY(): rect.getY());
                if(sqr.getY() > e.getY()) {
                    sqr.setY(e.getY());
                }

                gc.fillRect(sqr.getX(), sqr.getY(), sqr.getWidth(), sqr.getHeight());
                gc.strokeRect(sqr.getX(), sqr.getY(), sqr.getWidth(), sqr.getHeight());
                
                undoHistory.push(new Rectangle(sqr.getX(), sqr.getY(), sqr.getWidth(), sqr.getHeight()));
                
            }
           
            else if(elpsleOption.isSelected()) {
                elps.setRadiusX(Math.abs(e.getX() - elps.getCenterX()));
                elps.setRadiusY(Math.abs(e.getY() - elps.getCenterY()));
                
                if(elps.getCenterX() > e.getX()) {
                    elps.setCenterX(e.getX());
                }
                if(elps.getCenterY() > e.getY()) {
                    elps.setCenterY(e.getY());
                }
                
                gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                gc.fillOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                
                undoHistory.push(new Ellipse(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY()));
            }
            redoHistory.clear();
            Shape lastUndo = undoHistory.lastElement();
            lastUndo.setFill(gc.getFill());
            lastUndo.setStroke(gc.getStroke());
            lastUndo.setStrokeWidth(gc.getLineWidth());
            
        });
        // color picker
        cpLine.setOnAction(e->{
                gc.setStroke(cpLine.getValue());
        });
        cpFill.setOnAction(e->{
                gc.setFill(cpFill.getValue());
        });
        
        // slider
        slider.valueProperty().addListener(e->{
            double width = slider.getValue();
            if(textbtn.isSelected()){
                gc.setLineWidth(1);
                gc.setFont(Font.font(slider.getValue()));
                line_width.setText(String.format("%.1f", width));
                return;
            }
            line_width.setText(String.format("%.1f", width));
            gc.setLineWidth(width);
        });
        
        /*------- Undo & Redo ------*/
        // Undo
        undo.setOnAction(e->{
            if(!undoHistory.empty()){
                gc.clearRect(0, 0, 1080, 790);
                Shape removedShape = undoHistory.lastElement();
                if(removedShape.getClass() == Line.class) {
                    Line tempLine = (Line) removedShape;
                    tempLine.setFill(gc.getFill());
                    tempLine.setStroke(gc.getStroke());
                    tempLine.setStrokeWidth(gc.getLineWidth());
                    redoHistory.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));
                    
                }
                else if(removedShape.getClass() == Rectangle.class) {
                    Rectangle tempRect = (Rectangle) removedShape;
                    tempRect.setFill(gc.getFill());
                    tempRect.setStroke(gc.getStroke());
                    tempRect.setStrokeWidth(gc.getLineWidth());
                    redoHistory.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
                }
                else if(removedShape.getClass() == Circle.class) {
                    Circle tempCirc = (Circle) removedShape;
                    tempCirc.setStrokeWidth(gc.getLineWidth());
                    tempCirc.setFill(gc.getFill());
                    tempCirc.setStroke(gc.getStroke());
                    redoHistory.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
                }
                else if(removedShape.getClass() == Ellipse.class) {
                    Ellipse tempElps = (Ellipse) removedShape;
                    tempElps.setFill(gc.getFill());
                    tempElps.setStroke(gc.getStroke());
                    tempElps.setStrokeWidth(gc.getLineWidth());
                    redoHistory.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
                }
                Shape lastRedo = redoHistory.lastElement();
                lastRedo.setFill(removedShape.getFill());
                lastRedo.setStroke(removedShape.getStroke());
                lastRedo.setStrokeWidth(removedShape.getStrokeWidth());
                undoHistory.pop();
                
                for(int i=0; i < undoHistory.size(); i++) {
                    Shape shape = undoHistory.elementAt(i);
                    if(shape.getClass() == Line.class) {
                        Line temp = (Line) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        gc.strokeLine(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
                    }
                    else if(shape.getClass() == Rectangle.class) {
                        Rectangle temp = (Rectangle) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        gc.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                        gc.strokeRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                    }
                    else if(shape.getClass() == Circle.class) {
                        Circle temp = (Circle) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                        gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                    }
                    else if(shape.getClass() == Ellipse.class) {
                        Ellipse temp = (Ellipse) shape;
                        gc.setLineWidth(temp.getStrokeWidth());
                        gc.setStroke(temp.getStroke());
                        gc.setFill(temp.getFill());
                        gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                        gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                    }
                }
            } else {
                text1.setText("there is no action to undo");
            }
        });
        
        // Redo
        redo.setOnAction(e->{
            if(!redoHistory.empty()) {
                Shape shape = redoHistory.lastElement();
                gc.setLineWidth(shape.getStrokeWidth());
                gc.setStroke(shape.getStroke());
                gc.setFill(shape.getFill());
                    
                redoHistory.pop();
                if(shape.getClass() == Line.class) {
                    Line tempLine = (Line) shape;
                    gc.strokeLine(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY());
                    undoHistory.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));
                }
                else if(shape.getClass() == Rectangle.class) {
                    Rectangle tempRect = (Rectangle) shape;
                    gc.fillRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());
                    gc.strokeRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());
                    
                    undoHistory.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
                }
                else if(shape.getClass() == Circle.class) {
                    Circle tempCirc = (Circle) shape;
                    gc.fillOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());
                    gc.strokeOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());
                    
                    undoHistory.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
                }
                else if(shape.getClass() == Ellipse.class) {
                    Ellipse tempElps = (Ellipse) shape;
                    gc.fillOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());
                    gc.strokeOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());
                    
                    undoHistory.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
                }
                Shape lastUndo = undoHistory.lastElement();
                lastUndo.setFill(gc.getFill());
                lastUndo.setStroke(gc.getStroke());
                lastUndo.setStrokeWidth(gc.getLineWidth());
            } else {
                text1.setText("there is no action to redo");
            }
        });
        
        
      
        
        // Save
        save.setOnAction((e)->{
            FileChooser savefile = new FileChooser();
            savefile.setTitle("Save File");
            
            File file = savefile.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage(1080, 790);
                    canvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    text1.setText("Error!");
                }
            }
            
        });

        /* ----------STAGE & SCENE---------- */
        BorderPane pane = new BorderPane();
        pane.setTop(vBox);
        pane.setLeft(btns);
        pane.setBottom(vBox1);
        pane.setCenter(canvas);
    
        Scene scene = new Scene(pane, 1200, 900);
        
        primaryStage.setTitle("Painter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
