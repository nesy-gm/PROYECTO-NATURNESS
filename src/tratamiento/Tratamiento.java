/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tratamiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * La clase tratamiento se encarga de aglutinar las clases hidratante y antiedad
 * las cuales son "hijas".
 * @author josealberto
 */
public class Tratamiento{
    protected Integer ean;
    protected String marca;
    protected double precio;
    protected int stock;
    
    /**
     * Constructor vacío
     */
    public Tratamiento() {
    }
    /**
     * Constructor del objeto tratamiento
     * @param ean
     * @param marca
     * @param precio
     * @param stock 
     */
    public Tratamiento(Integer ean, String marca, double precio, int stock) {
        this.ean = ean;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
    }

    /**
     * Getter del atributo marca
     * @return marca
     */   
    public String getMarca() {
        return marca;
    }
    
    /**
     * Setter del atributo marca
     * @param marca 
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    /**
     * Getter del Ean del objeto tratamiento
     * @return 
     */
    public Integer getEan() {
        return ean;
    }

    /**
     * Setter del atributo ean
     * @param ean 
     */
    public void setEan(Integer ean) {
        this.ean = ean;
    }
    /**
     * Getter del precio unitario del producto
     * @return 
     */
    public double getPrecio() {
        return precio;
    }
    /**
     * Setter del precio unitario del productp
     * @param precio 
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    /**
     * Getter del atributo stock
     * @return 
     */
    public int getStock() {
        return stock;
    }
    /**
     * Setter del atributo stock
     * @param stock 
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Método que nos muestra los tratamientos existentes en la base de datos
     * En este caso se nos muestran todos, incluyendo además los atributos de 
     * antiedad e hidratante, para mayor claridad.
     * @param con
     * @throws SQLException
     */
    public void mostrarTratamiento(Connection con) throws SQLException{
        Statement st = con.createStatement();
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el EAN del tratamiento:");
        int eantrat = sc.nextInt();
        ResultSet rs = st.executeQuery("select * from tratamiento left outer "
                + "join (SELECT * from hidratante union select * from antiedad) "
                + "as total on ean=id_tratamiento where ean=\""
                +eantrat+"\"");
        while (rs.next()){
            System.out.println("Código EAN del tratamiento: " + rs.getInt("ean"));
            System.out.println("Marca del tratamiento: " + rs.getString("marca"));
            System.out.println("Stock disponible: " + rs.getInt("stock"));
            System.out.println("Precio de venta: " + rs.getFloat("precio_unidad"));
            System.out.println("El id del tratamiento es: " 
                    + rs.getInt("id"));
            System.out.println("La descripción del tratamiento es: " 
                    + rs.getString("descripcion"));
            System.out.println("La zona de aplicación principal de este "
                    + "tratamiento es " + rs.getString("zona_aplicacion"));
        }
    
    }
    /**
     * Método para dar de alta un nuevo tratamiento, no se utilizará por sí
     * solo, si no que lo llamaremos desde los hijos que darán a la vez el 
     * alta en la tabla Tratamiento y en su tabla correspondiente (hidratante
     * o antiedad)
     * @param con 
     * @throws java.sql.SQLException 
     */
    public void introducirTratamiento(Connection con) throws SQLException{
        Scanner sc = new Scanner(System.in);
        String insert = "INSERT INTO tratamiento (ean, marca, stock, "
                + "precio_unidad) VALUES (?, ?, ?, ?);";
        PreparedStatement pst = con.prepareStatement(insert);
        System.out.println("Vamos a dar de alta un nuevo producto");
        System.out.println("Escribe el código ean");
        pst.setInt(1, sc.nextInt());
        System.out.println("Dime la marca del producto a introducir: ");
        //si no introducimos esta línea de entrada, la siguiente no funciona
        sc.nextLine(); 
        pst.setString(2, sc.nextLine());
        System.out.println("¿Cuánto stock ha llegado?");
        pst.setInt(3, sc.nextInt());
        System.out.println("¿Cuál es el PVP?");
        pst.setFloat(4, sc.nextFloat());
        pst.execute();
        System.out.println("El nuevo tratamiento se ha introducido "
                + "correctamente");
        }
    
}
    

