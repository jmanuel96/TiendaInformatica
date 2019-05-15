/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendainformatica;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author josemanuel
 */
public class DetallesProducyosController implements Initializable {

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

    @FXML
    private void onActionBotonGuardar(ActionEvent event) {
    }

    @FXML
    private void onActionBotonCancelar(ActionEvent event) {
    }
    
}
