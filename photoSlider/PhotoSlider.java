package photoslider;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.ListIterator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;

public class PhotoSlider extends Application {
    
    BorderPane borderPane;
    StackPane stackPane;
    Scene scene;
    Button leftButton, rightButton;
    ImageView viewImg = new ImageView();
    ArrayList<File> picList;
    ListIterator<File> picIterator;
    File file;
    Image image;
    int imgCount = 0;
    
    @Override
    public void start(Stage primaryStage) {
        //Fill array with images
        picList = new ArrayList<File>();
        picList.add(new File("cat-yawn.jpg"));
        picList.add(new File("cat-toast-head.jpg"));
        picList.add(new File("cat-tongue.jpg"));
          
        file = picList.get(imgCount); //Gets first image
        image = new Image("file:"+file.getName());
        
        viewImg.setFitWidth(400);
        viewImg.setFitHeight(400);
        viewImg.setPreserveRatio(true);
        viewImg.setImage(image);
            
        //Create left and right buttons
        leftButton = new Button("Previous");
        rightButton = new Button("Next");
        leftButton.setPrefWidth(80.0);
        rightButton.setPrefWidth(80.0);
        leftButton.setOnAction ( e-> previousImage() );
        rightButton.setOnAction( e-> nextImage() );
        
        //Create stack pane
        stackPane = new StackPane();
        stackPane.getChildren().add(viewImg);
        
        //Create border pane and place nodes in pane
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(12));
        borderPane.setLeft(leftButton);
        borderPane.setRight(rightButton);
        borderPane.setCenter(stackPane);
        borderPane.requestFocus();
        borderPane.setOnKeyPressed(e->{
            switch (e.getCode()) {
                case LEFT  : previousImage(); break;
                case RIGHT : nextImage(); break;
            }
        });
        stackPane.setOnMouseDragged( e -> drag(e) );
    
        //Position the left and right buttons
        BorderPane.setMargin(leftButton, new Insets(215,0,0,30));
        BorderPane.setMargin(rightButton, new Insets(215,30,0,0));
        
        //Create scene and place it in the stage
        scene = new Scene(borderPane, 700, 500); //(paneType,width,height)
        primaryStage.setTitle("Assignment #3: Photo Gallery");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        stackPane.requestFocus();
    }
    public void previousImage () {
        imgCount--;
        if (imgCount == -1) imgCount = picList.size()-1;
        file = picList.get(imgCount);
        image = new Image("file:"+file.getName());
        viewImg.setImage(image);
    }
    public void nextImage() {
        imgCount++;
        if (imgCount == picList.size()) imgCount = 0;
        file = picList.get(imgCount);
        image = new Image("file:"+file.getName());
        viewImg.setImage(image);
    }
    public void drag(MouseEvent e) {
        StackPane img = (StackPane)e.getSource();
        img.setTranslateX(img.getTranslateX() + e.getX());
        img.setTranslateY(img.getTranslateY() + e.getY());
    }
    public static void main(String[] args) {
        launch(args);
    }
}