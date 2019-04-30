/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendainformatica;

import BaseDatos.Categorias;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
        Query queryCategorias = em.createNamedQuery("Categorias.findAll");
        List<Categorias> listCategorias = queryCategorias.getResultList();
        
        for(Categorias categoria : listCategorias) {
            System.out.println(categoria.getNombre());
        }
        
        for(int i=0; i<listCategorias.size(); i++) {
            Categorias categoria = listCategorias.get(i);
            System.out.println(categoria.getNombre());
}

//            Query queryProvinciaCadiz = em.createNamedQuery("Categorias.findByNombre");
//            queryProvinciaCadiz.setParameter("nombre", "Cádiz");
//            List<Categorias> listProvinciasCadiz = queryProvinciaCadiz.getResultList();
//            for(Categorias provinciaCadiz : listProvinciasCadiz) {
//                System.out.print(provinciaCadiz.getId() + ": ");
//                System.out.println(provinciaCadiz.getNombre());
//}
        
        // Iniciar y finalizar transaciones
        em.getTransaction().begin();
        
        // Añadir aquí las operaciones de modificación de la base de datos
        Categorias ordenadores = new Categorias();
        ordenadores.setNombre("Ordenadores");
        Categorias moviles = new Categorias();
        moviles.setNombre("Móviles");
        Categorias consolas = new Categorias();
        consolas.setNombre("Consolas");
        Categorias televisores = new Categorias();
        televisores.setNombre("Televisores");
        em.persist(ordenadores);
        em.persist(moviles);
        em.persist(consolas);
        em.persist(televisores);
        
        
        
        em.getTransaction().commit();
    
        // Cerrar la conexión con la base de datos
        em.close(); 
        emf.close(); 
        try { 
            DriverManager.getConnection("jdbc:derby:TiendaInformatica;shutdown=true"); 
        } catch (SQLException ex) { 
        }
    }
   
}
