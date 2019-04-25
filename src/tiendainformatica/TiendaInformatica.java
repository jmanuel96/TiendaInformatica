/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendainformatica;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author josemanuel
 */
public class TiendaInformatica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Conectar con la base de datos
        Map<String, String> emfProperties = new HashMap<String, String>();
        emfProperties.put("javax.persistence.schema-generation.database.action", "create");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiendaInformaticaPU", emfProperties); // Comprobar nombre de la PU
        EntityManager em = emf.createEntityManager();
    
        em.close(); 
        emf.close(); 
        try { 
            DriverManager.getConnection("jdbc:derby:TiendaInformatica;shutdown=true"); 
        } catch (SQLException ex) { 
        }
    }

    
}
