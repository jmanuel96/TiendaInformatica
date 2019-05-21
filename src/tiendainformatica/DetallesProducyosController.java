/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendainformatica;

import BaseDatos.Categorias;
import BaseDatos.Productos;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
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

    
    public static final String CARPETA_FOTOS = "Fotos";

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
    private ImageView imageViewFoto;
    
    private Pane rootTiendaView;
    @FXML
    private AnchorPane rootDetalleView;
    @FXML
    private ToggleGroup estadoCivilGroup;
    

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
        try {
            if (producto.getEstado()){
                radioBotonNuevo.setSelected(true);
            } else if (producto.getEstado()== false){
                radioBotonUsado.setSelected(true);
            }
                
    } catch (Exception e){}


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
            if (producto.getFoto() != null) {
            String imageFileName = producto.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encuentra la imagen");
                alert.showAndWait();
            }
        }


    }

    @FXML
    private void onActionBotonGuardar(ActionEvent event) {
        int numFilaSeleccionada;
        boolean errorFormato = false;
        
        producto.setNombre(textFieldNombre.getText());
        producto.setDescripcion(textAreaDescripcion.getText());
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
        try {

        if (radioBotonNuevo.isSelected()) {
            producto.setEstado(true);
        } else if (radioBotonUsado.isSelected()) {
            producto.setEstado(false);
        } 
        } catch (Exception e){}
        
        
        if (comboBoxCategoria.getValue() != null) {
            producto.setIdcategoria(comboBoxCategoria.getValue());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Debe indicar una categoria");
            alert.showAndWait();
            errorFormato = true;
        }
        if (datePickerFechaSalida.getValue() != null) {
            LocalDate localDate = datePickerFechaSalida.getValue();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            producto.setFechasalida(date);
        } else {
            producto.setFechasalida(null);
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

    @FXML
    private void onActionButtonExaminar(ActionEvent event) {
        File carpetaFotos = new File(CARPETA_FOTOS);
        if (!carpetaFotos.exists()) {
            carpetaFotos.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Imágenes (jpg, png)", "*.jpg", "*.png"),
        new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        File file = fileChooser.showOpenDialog(rootDetalleView.getScene().getWindow());
        if (file != null) {
            try {
                Files.copy(file.toPath(), new File(CARPETA_FOTOS + "/" + file.getName()).toPath());
                producto.setFoto(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } catch (FileAlreadyExistsException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Nombre de archivo duplicado");
                alert.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No se ha podido guardar la imagen");
                alert.showAndWait();
            }
        }
    }
}
