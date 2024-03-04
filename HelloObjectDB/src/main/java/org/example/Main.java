package org.example;

public class Main {
    public static void main(String[] args) {
        Basededatosclientes gestorBD = new Basededatosclientes();

        Cliente cliente1 = new Cliente("Santiago", 30);
        Cliente cliente2 = new Cliente("Antonio", 0);
        Cliente cliente3 = new Cliente("Javier", 4);
        Cliente cliente4 = new Cliente("Francisco", 2);
        Cliente cliente5 = new Cliente("Mario", 12);

        gestorBD.insertarCliente(cliente1);
        gestorBD.insertarCliente(cliente2);
        gestorBD.insertarCliente(cliente3);
        gestorBD.insertarCliente(cliente4);
        gestorBD.insertarCliente(cliente5);


        gestorBD.estadisticas();

        gestorBD.close();
    }
}
