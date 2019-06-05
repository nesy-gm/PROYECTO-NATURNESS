/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tratamiento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import static tratamiento.Factura.realizarVenta;

/**
 * @version 1.0
 * @author josealberto
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        try (Connection con = DriverManager.getConnection
          ("jdbc:mysql://localhost:3306/naturness", "alumne", "alualualu")) {
            while (!salir){    
                System.out.println("Escoge una opción");
                System.out.println("1. Gestión de stock");
                System.out.println("2. Gestión de clientes");
                System.out.println("3. Venta");
                System.out.println("4. Buscar factura por número de factura");
                System.out.println("5. Buscar factura por DNI");
                System.out.println("6. Salir");
                int opcion = sc.nextInt();
                switch(opcion){
                    case(1):{
                        boolean salir2=false;
                        while (!salir2){
                            System.out.println("Escoge una opción");
                            System.out.println("1. Introducir producto nuevo");
                            System.out.println("2. Búsqueda de producto");
                            System.out.println("3. Salir");
                            int opcion2 = sc.nextInt();
                            switch (opcion2){
                                case 1: {
                                    System.out.println("¿Deseas crear un "
                                            + "tratamiento antiedad o uno "
                                            + "hidratante?");
                                    sc.next();
                                    String tipo = sc.nextLine();
                                    if (tipo.equals("antiedad")){
                                        Antiedad anti = new Antiedad();
                                        anti.introducirTratamiento(con);
                                    }else{
                                        Hidratante hidra = new Hidratante();
                                        hidra.introducirTratamiento(con);
                                    }
                                    break;
                                }
                                case 2: {
                                    Tratamiento trat = new Tratamiento();
                                    trat.mostrarTratamiento(con);
                                    break;
                                }
                                case 3: {
                                    salir2=true;
                                    break;
                                    }
                                default:
                                    System.out.println("Por favor, escoge una "
                                            + "opción válida");
                            }

                        }
                        break;
                    }    
                    case 2: {
                        boolean salir2 = false;
                        while (!salir2){
                            System.out.println("Escoge una opción");
                            System.out.println("1. Dar de alta cliente");
                            System.out.println("2. Buscar un cliente por DNI");
                            System.out.println("3. Buscar un cliente por nº de "
                                    + "teléfono");
                            System.out.println("4. Salir");
                            int opcion2 = sc.nextInt();

                            switch (opcion2){
                                case 1: {
                                    Cliente cust = new Cliente();
                                    cust.altaCliente(con);
                                    break;
                                }
                                case 2: {
                                    Cliente.buscarCliente_dni(con);
                                    break;
                                }
                                case 3: {
                                    Cliente.buscarCliente_telf(con);
                                    break;
                                      
                                }
                                case 4: {
                                    salir2=true;
                                    break;
                                }
                                default: {
                                    System.out.println("Por favor, escoge una"
                                            + "opción válida");
                                }
                            }
                        }
                    } 
                    case 3: {
                        realizarVenta(con);
                        break;
                    }
                    case 4: {
                        Factura.buscar_fact_num(con);
                        break;
                    }
                    case 5: {
                        Factura.buscar_fact_dni(con);
                        break;
                                
                    }
                    case 6: {
                        salir=true;
                        break;
                    }
                    default: {
                        System.out.println("Por favor, escoge una opción válida");
                    }
                           

                }

                }
        } catch (SQLException ex) {
            System.out.println("Error SQL " + ex.getMessage());
        } catch (Exception ex){
            System.out.println("Error " + ex.getMessage());
        }
    }
}