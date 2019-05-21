/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendainformatica;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author josemanuel
 */
public class MainSceneBuilder extends Application {
    
    private EntityManagerFactory emf;
    private EntityManager em;   
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        StackPane rootMain = new StackPane();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TiendaView.fxml"));
        Pane rootTiendaView = fxmlLoader.load();
        rootMain.getChildren().add(rootTiendaView);
        
        emf = Persistence.createEntityManagerFactory("TiendaInformaticaPU");
        em = emf.createEntityManager();
        
        TiendaViewController tiendaViewController = (TiendaViewController) fxmlLoader.getController();
        
        // Después de obtener el objeto del controlador y del EntityManager:
        tiendaViewController.setEntityManager(em);
        tiendaViewController.cargarTodosProductos();
        
        Scene scene = new Scene(rootMain, 950, 600);
        
        primaryStage.setTitle("Tienda Informática");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        em.close();
        emf.close();
        try{
            DriverManager.getConnection("jdbc:derby:TiendaInformatica;shutdown=true"); 
        } catch (SQLException ex){
        }
    }

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
}


