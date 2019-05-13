/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendainformatica;

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
import javafx.scene.control.TableColumn;
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
    private TextField textFieldCategoria;
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
                textFieldDescripcion.setText(productoSeleccionado.getDescripcion());
                textFieldPrecio.setText(productoSeleccionado.getPrecio());
                textFieldCategoria.setText(productoSeleccionado.getCategoria());
            } else {
                textFieldNombre.setText("");
                textFieldDescripcion.setText("");
                textFieldPrecio.setText("");
                textFieldCategoria.setText("");
            }
        });
    }
    
    public void cargarTodosProductos() {
    Query queryProductosFindAll = entityManager.createNamedQuery("Productos.findAll");
    List<Productos> listProductos = queryProductosFindAll.getResultList();
    tablaViewProductos.setItems(FXCollections.observableArrayList(listProductos));
    }

    @FXML
    private void onActionBotonGuardar(ActionEvent event) {
    }

    @FXML
    private void onActionBotonDeshacer(ActionEvent event) {
    }
    
}
