/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendainformatica;

import BaseDatos.Categorias;
import BaseDatos.Productos;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TableColumn<Productos, BigDecimal> columnaPrecio;
    @FXML
    private TableColumn<Productos, String> columnaCategoria;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldDescripcion;
    @FXML
    private TextField textFieldPrecio;
    @FXML
    private ComboBox<Categorias> comboBoxCategoria;
    @FXML
    private Button botonGuardar;
    @FXML
    private Button botonDeshacer;

    
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
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaCategoria.setCellValueFactory(
        cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            if (cellData.getValue().getIdcategoria()!= null) {
                property.setValue(cellData.getValue().getIdcategoria().getNombre());
            }
            return property;
        });
    

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
    private void onActionBotonDeshacer(ActionEvent event) {
        
    }
    
}
