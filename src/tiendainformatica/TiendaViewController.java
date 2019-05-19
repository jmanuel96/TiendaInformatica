/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendainformatica;

import BaseDatos.Categorias;
import BaseDatos.Productos;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author josemanuel
 */
public class TiendaViewController implements Initializable {

    private EntityManager entityManager;
    private Productos productoSeleccionado;
    
    @FXML
    private TableView<Productos> tablaViewProductos;
    @FXML
    private TableColumn<Productos, String> columnaNombre;
    @FXML
    private TableColumn<Productos, String> columnaDescripcion;
//    @FXML
//    private TableColumn<Productos, BigDecimal> columnaPrecio;
//    @FXML
//    private TableColumn<Productos, String> columnaCategoria;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldDescripcion;
    @FXML
    private AnchorPane rootTiendaView;
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
//        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
//        columnaCategoria.setCellValueFactory(
//        cellData -> {
//            SimpleStringProperty property = new SimpleStringProperty();
//            if (cellData.getValue().getIdcategoria()!= null) {
//                property.setValue(cellData.getValue().getIdcategoria().getNombre());
//            }
//            return property;
//        });
    

        tablaViewProductos.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> {
            productoSeleccionado = newValue;
             if (productoSeleccionado != null) {
                textFieldNombre.setText(productoSeleccionado.getNombre());
             }
             else {
                 textFieldNombre.setText("");
             }
             if (productoSeleccionado != null) {
                textFieldDescripcion.setText(productoSeleccionado.getDescripcion());
             }
//             else {
//                textFieldDescripcion.setText("");
//             }
//             if (productoSeleccionado != null) { 
//                textFieldPrecio.setText(String.valueOf(productoSeleccionado.getPrecio()));
//             }
//             else {
//                textFieldPrecio.setText("");
//             }
//             if (productoSeleccionado.getIdcategoria() != null) {
//                comboBoxCategoria.setValue(productoSeleccionado.getIdcategoria());
//             }
        });
    }
    
    public void cargarTodosProductos() {
    Query queryProductosFindAll = entityManager.createNamedQuery("Productos.findAll");
    List<Productos> listProductos = queryProductosFindAll.getResultList();
    tablaViewProductos.setItems(FXCollections.observableArrayList(listProductos));
    }


    @FXML
    private void onActionBotonGuardar(ActionEvent event) {
        if (productoSeleccionado != null) {
        productoSeleccionado.setNombre(textFieldNombre.getText());
        productoSeleccionado.setDescripcion(textFieldDescripcion.getText());
//        textFieldPrecio.setText(String.valueOf(productoSeleccionado.getPrecio()));
//        comboBoxCategoria.setValue(productoSeleccionado.getIdcategoria());
        entityManager.getTransaction().begin();
        entityManager.merge(productoSeleccionado);
        entityManager.getTransaction().commit();

        int numFilaSeleccionada = tablaViewProductos.getSelectionModel().getSelectedIndex();
        tablaViewProductos.getItems().set(numFilaSeleccionada, productoSeleccionado);
        TablePosition pos = new TablePosition(tablaViewProductos, numFilaSeleccionada, null);
        tablaViewProductos.getFocusModel().focus(pos);
        tablaViewProductos.requestFocus();
        
        }
    }

    @FXML
    private void onActionBotonNuevo(ActionEvent event) {
        try {
            // Cargar la vista de detalle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetallesProducyos.fxml"));
            Parent rootDetalleView = fxmlLoader.load();     

            // Ocultar la vista de la lista
            rootTiendaView.setVisible(false);
	
            // Añadir la vista de detalle al StackPane principal para que se muestre
            StackPane rootMain = (StackPane)rootTiendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);
            
            
            
            DetallesProducyosController detallesProducyosController = (DetallesProducyosController) fxmlLoader.getController();  
            detallesProducyosController.setRootTiendaView(rootTiendaView);
            detallesProducyosController.setTableViewPrevio(tablaViewProductos);
            // Para el botón Nuevo:
            productoSeleccionado = new Productos();
            detallesProducyosController.setProducto(entityManager, productoSeleccionado, true);
            detallesProducyosController.mostrarDatos();
            
        } catch (IOException ex) {
            Logger.getLogger(TiendaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onActionBotonEditar(ActionEvent event) {
        if(productoSeleccionado != null) {
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetallesProducyos.fxml"));
            Parent rootDetalleView = fxmlLoader.load();     

            // Ocultar la vista de la lista
            rootTiendaView.setVisible(false);
	
            // Añadir la vista de detalle al StackPane principal para que se muestre
            StackPane rootMain = (StackPane)rootTiendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);
            
            
            
            DetallesProducyosController detallesProducyosController = (DetallesProducyosController) fxmlLoader.getController();  
            detallesProducyosController.setRootTiendaView(rootTiendaView);
            detallesProducyosController.setTableViewPrevio(tablaViewProductos);
            // Para el botón Editar:
            detallesProducyosController.setProducto(entityManager, productoSeleccionado, false);
            detallesProducyosController.mostrarDatos();
  
            
        } catch (IOException ex) {
            Logger.getLogger(TiendaViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    @FXML
    private void onActionBotonDeshacer(ActionEvent event) {
    }

        

    
}
