/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendainformatica;

import BaseDatos.Productos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author josemanuel
 */
public class DetallesProducyosController implements Initializable {
    
    private TableView tableViewPrevio;
    private Productos producto;
    private boolean nuevoProducto;
    private EntityManager entityManager;

    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextArea textAreaDescripcion;
    @FXML
    private TextField textFieldPrecio;
    @FXML
    private RadioButton radioBotonNuevo;
    @FXML
    private RadioButton radioBotonUsado;
    @FXML
    private DatePicker datePickerFechaSalida;
    @FXML
    private ComboBox<?> comboBoxCategoria;
    @FXML
    private Button imageViewFoto;
    
    private Pane rootTiendaView;
    @FXML
    private AnchorPane rootDetalleView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void setRootTiendaView(Pane rootTiendaView) {
    this.rootTiendaView = rootTiendaView;
    }
    
    public void setTableViewPrevio(TableView tableViewPrevio) {
    this.tableViewPrevio = tableViewPrevio;
    }
    
    public void setProducto(EntityManager entityManager, Productos producto, boolean nuevoProducto) {
    this.entityManager = entityManager;
    entityManager.getTransaction().begin();
        if(!nuevoProducto) {
            this.producto = entityManager.find(Productos.class, producto.getIdproducto());
        } else {
            this.producto = producto;
        }
        this.nuevoProducto = nuevoProducto;
    }
    
    public void mostrarDatos() {
    textFieldNombre.setText(producto.getNombre());
    textAreaDescripcion.setText(producto.getDescripcion());
//    textFieldTelefono.setText(persona.getTelefono());
//    textFieldEMail.setText(persona.getEmail());
    // Falta implementar el código para el resto de controles
}

    @FXML
    private void onActionBotonGuardar(ActionEvent event) {
        producto.setNombre(textFieldNombre.getText());
        producto.setDescripcion(textAreaDescripcion.getText());

        if(nuevoProducto) {
            entityManager.persist(producto);
        } else {
            entityManager.merge(producto);
        }
        entityManager.getTransaction().commit();
        
    }

    @FXML
    private void onActionBotonCancelar(ActionEvent event) {
        StackPane rootMain = (StackPane)rootDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootDetalleView);      
  
        rootTiendaView.setVisible(true);
    }
    
}
