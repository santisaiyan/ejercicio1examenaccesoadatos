package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class Basededatosclientes {

    private static Basededatosclientes instancia;
    private EntityManagerFactory emf;

    Basededatosclientes() {
        emf = Persistence.createEntityManagerFactory("data.odb");
    }
    public static Basededatosclientes obtenerInstancia() {
        if (instancia == null) {
            instancia = new Basededatosclientes();
        }
        return instancia;
    }



    public void insertarCliente(Cliente cliente) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
        em.close();
    }

    public void getCliente(long id) {
        EntityManager em = emf.createEntityManager();
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente != null) {
            System.out.println("Info del cliente:");
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Total de ventas: " + cliente.getTotalVentas());
            System.out.println("Estado: " + (cliente.isEstadoActivo() ? "Activo" : "Inactivo"));
        } else {
            System.out.println("No se encuentra el cliente");
        }
        em.close();
    }

    public void listarMejoresClientes(Long cantidad) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Cliente> query = em.createQuery(
                "SELECT c FROM Cliente c WHERE c.estadoActivo = true AND c.totalVentas > :cantidad", Cliente.class);
        query.setParameter("cantidad", cantidad);
        List<Cliente> clientes = query.getResultList();
        if (clientes.isEmpty()) {
            System.out.println("No se ha podido encontrar clientes con ventas superiores a " + 0);
        } else {
            System.out.println("Clientes activos con ventas superiores a " + 0 + ":");
            for (Cliente cliente : clientes) {
                System.out.println("ID: " + cliente.getId() + ", Nombre: " + cliente.getNombre() +
                        ", Total de ventas: " + cliente.getTotalVentas());
            }
        }
        em.close();
    }

    public void estadisticas() {
        EntityManager em = emf.createEntityManager();


        TypedQuery<Long> totalQuery = em.createQuery(
                "SELECT SUM(c.totalVentas) FROM Cliente c", Long.class);
        Long totalVentas = totalQuery.getSingleResult();
        System.out.println("Total de ventas entre todos los clientes: " + totalVentas);


        TypedQuery<Double> promedioQuery = em.createQuery(
                "SELECT AVG(c.totalVentas) FROM Cliente c WHERE c.estadoActivo = true", Double.class);
        Double promedioVentas = promedioQuery.getSingleResult();
        System.out.println("Media de ventas de clientes activos: " + promedioVentas);


        TypedQuery<Long> inactivosQuery = em.createQuery(
                "SELECT COUNT(c) FROM Cliente c WHERE c.estadoActivo = false AND c.totalVentas > 0", Long.class);
        Long cantidadInactivosConVentas = inactivosQuery.getSingleResult();
        System.out.println("clientes inactivos con ventas mayores a 0: " + cantidadInactivosConVentas);

        em.close();
    }


    public void close() {
        emf.close();
    }
}
