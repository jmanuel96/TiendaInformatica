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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    @FXML
    private TableView<Productos> tablaViewProductos;
    @FXML
    private TableColumn<Productos, String> columnaNombre;
    @FXML
    private TableColumn<Productos, String> columnaDescripcion;
    @FXML
    private TableColumn<Productos, BigDecimal> columnaPrecio;
    
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
    }    
    
        public void cargarTodosProductos() {
        Query queryProductosFindAll = entityManager.createNamedQuery("Productos.findAll");
        List<Productos> listProductos = queryProductosFindAll.getResultList();
        tablaViewProductos.setItems(FXCollections.observableArrayList(listProductos));
    }
    
}
