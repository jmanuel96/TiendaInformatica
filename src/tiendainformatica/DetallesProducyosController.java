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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
    
    public static final char NUEVO = 'N';
    public static final char USADO = 'U';

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
    private ComboBox<Categorias> comboBoxCategoria;
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
        if (producto.getPrecio() != null) { 
            textFieldPrecio.setText(String.valueOf(producto.getPrecio()));
        }
//        if (producto.getEstado() != null) {
//            
//        switch (producto.getEstado()) {
//            case NUEVO:
//                radioBotonNuevo.setSelected(true);
//                break;
//            case USADO:
//                radioBotonUsado.setSelected(true);
//                break;
//            }
//        }

        if (producto.getFechasalida()!= null) {
            Date date = producto.getFechasalida();
            Instant instant = date.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate localDate = zdt.toLocalDate();
            datePickerFechaSalida.setValue(localDate);
        }
        
        Query queryCategoriasFindAll = entityManager.createNamedQuery("Categorias.findAll");
        List listCategoria = queryCategoriasFindAll.getResultList();
        comboBoxCategoria.setItems(FXCollections.observableList(listCategoria));
        
        if (producto.getIdcategoria() != null) {
            comboBoxCategoria.setValue(producto.getIdcategoria());
        }
            
        comboBoxCategoria.setCellFactory((ListView<Categorias> l) -> new ListCell<Categorias>() {
            @Override
            protected void updateItem(Categorias categoria, boolean empty) {
                super.updateItem(categoria, empty);
                if (categoria == null || empty) {
                    setText("");
                } else {
                    setText(categoria.getNombre());
                }
            }
        });
        
        comboBoxCategoria.setConverter(new StringConverter<Categorias>() {
            @Override
            public String toString(Categorias categoria) {
                if (categoria == null) {
                    return null;
                } else {
                    return categoria.getNombre();
                }
            }
            @Override
            public Categorias fromString(String userId) {
                return null;
            }
        });
//    textFieldTelefono.setText(persona.getTelefono());
//    textFieldEMail.setText(persona.getEmail());

    // Falta implementar el código para el resto de controles
    }

    @FXML
    private void onActionBotonGuardar(ActionEvent event) {
        int numFilaSeleccionada;
        boolean errorFormato = false;
        
        producto.setNombre(textFieldNombre.getText());
        producto.setDescripcion(textAreaDescripcion.getText());
//        producto.setPrecio(BigDecimal.valueOf(Double.valueOf(textFieldPrecio.getText())));
        if (!textFieldPrecio.getText().isEmpty()) {
            try {
                producto.setPrecio(BigDecimal.valueOf(Double.valueOf(textFieldPrecio.getText()).doubleValue()));
            } catch (NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Expresión no valida");
                alert.showAndWait();
                textFieldPrecio.requestFocus();
            }
        }

        if(nuevoProducto) {
            entityManager.persist(producto);
        } else {
            entityManager.merge(producto);
        }
        entityManager.getTransaction().commit();
        

        if(nuevoProducto) {
            tableViewPrevio.getItems().add(producto);
            numFilaSeleccionada = tableViewPrevio.getItems().size() - 1;
            tableViewPrevio.getSelectionModel().select(numFilaSeleccionada);
            tableViewPrevio.scrollTo(numFilaSeleccionada);
        } else {
            numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
            tableViewPrevio.getItems().set(numFilaSeleccionada, producto);
        }
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
        rootTiendaView.setVisible(true);
        rootDetalleView.setVisible(false);
        
    }

    @FXML
    private void onActionBotonCancelar(ActionEvent event) {
        StackPane rootMain = (StackPane)rootDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootDetalleView);
        
        entityManager.getTransaction().rollback();

        int numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
  
        rootTiendaView.setVisible(true);
    }
    
}
