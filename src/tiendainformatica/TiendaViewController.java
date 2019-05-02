/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendainformatica;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author josemanuel
 */
public class TiendaViewController implements Initializable {

    private EntityManager entityManager;
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
