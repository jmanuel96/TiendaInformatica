/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendainformatica;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author josemanuel
 */
public class MainSceneBuilder extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TiendaView.fxml"));
        Parent sceneBuilder = fxmlLoader.load();
        
        StackPane root = new StackPane();
        
        Scene scene = new Scene(root, 300, 250);
        root.getChildren().add(sceneBuilder);
        
        primaryStage.setTitle("Tienda Informática");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
