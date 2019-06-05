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
import java.util.Date;
import java.util.Scanner;

/**
 * La clase Cliente es la que se encarga de crear los clientes
 * @author infor15
 */
public class Cliente {
    private String nif;
    private String nombre;
    private String apellido;
    private String codigo_postal;
    private int telefono;
    private Date fecha_nacimiento;
    private Date fecha_registro;

    /**
     * Constructor vacío
     */
    public Cliente() {
    }
    /**
     * Constructor
     * @param nif
     * @param nombre
     * @param apellido
     * @param codigo_postal
     * @param telefono
     * @param fecha_nacimiento
     * @param fecha_registro, el parámetro se inicia a null ya que al
     *        introducirlo en la base de datos se le asigna el valor que le dé
     *        la función now() (el timestamp de momento en el que se genera)
     */
    public Cliente(String nif, String nombre, String apellido, 
            String codigo_postal, int telefono, Date fecha_nacimiento, 
            Date fecha_registro) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellido = apellido;
        this.codigo_postal = codigo_postal;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.fecha_registro = null;
    }

    /**
     * Getter del nif
     * @return nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * Setter del nif
     * @param nif 
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Getter del nombre del cliente
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter del nombre del cliente
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter del apellido
     * @return apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Setter del apellido
     * @param apellido 
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Getter del código postal
     * @return codigo_postal
     */
    public String getCodigo_postal() {
       return codigo_postal;
    }

    /**
     * Setter del código postal
     * @param codigo_postal 
     */
    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    /**
     * Getter del número de teléfono
     * @return telefono
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * Setter del número de teléfono
     * @param telefono 
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    /**
     * Getter de la fecha de nacimiento
     * @return fecha_nacimiento
     */
    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    /**
     * Setter de la fecha de nacimiento
     * @param fecha_nacimiento 
     */
    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    

    /**
     * Getter de la fecha de registro
     * @return fecha_registro
     */
    public Date getFecha_registro() {
        return fecha_registro;
    }

    /**
     * Setter de la fecha de registro
     * @param fecha_registro 
     */
    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
    
    /**
     * Método para crear un nuevo cliente y volcarlo en la Base de Datos.
     * @param con
     * @throws SQLException 
     */
    public void altaCliente(Connection con) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String insert = "INSERT INTO clientes (nif, nombre, apellidos,"
                + " cp, telefono, fecha_nacimiento, fecha_registro)"
                        + " VALUES (?, ?, ?, ?, ?, ?, now());";
        PreparedStatement pst = con.prepareStatement(insert);
        System.out.println("Vamos a dar de alta un cliente nuevo");
        System.out.println("Por favor, introduce el documento del cliente");
        pst.setString(1, sc.nextLine());
        System.out.println("Escribe el nombre");
        pst.setString(2, sc.nextLine());
        System.out.println("Ahora el apellido");
        pst.setString(3, sc.nextLine());
        System.out.println("Introduce el código postal");
        pst.setString(4, sc.nextLine());
        System.out.println("Escribe el número de contacto");
        pst.setInt(5, sc.nextInt());
        System.out.println("Escribe la fecha de nacimiento del cliente");
        sc.nextLine();
        pst.setString(6, sc.nextLine());
        pst.execute();
        System.out.println("El nuevo cliente ha sido dado de alta correctamente");             
    }
    
    /**
     * Método de búsqueda de clientes dentro de la base de datos a través del 
 número de nif.
     * @param con
     * @throws SQLException 
     */
    public static void buscarCliente_dni(Connection con) throws SQLException {
        Statement st = con.createStatement();
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el DNI del cliente para poder buscarlo:");
        String nif = sc.nextLine();
        ResultSet rs = st.executeQuery("select * from clientes where nif="
                + "\""+nif+"\"");
        while (rs.next()){
            System.out.println("DNI del cliente: " + rs.getString("nif"));
            System.out.println("Nombre del cliente: " + rs.getString("nombre"));
            System.out.println("Apellido del cliente: " + rs.getString
        ("apellidos"));
            System.out.println("Código Postal: " + rs.getString("cp"));
            System.out.println("Teléfono: " + rs.getInt("telefono"));
            System.out.println("Fecha de nacimiento: " + rs.getString
        ("fecha_nacimiento"));
            System.out.println("Fecha de registro: "+ rs.getString
        ("fecha_registro"));
        }
    }
    
    /**
     * Método de búsqueda de clientes en la base de datos a través de su número 
     * de teléfono.
     * @param con
     * @throws SQLException 
     */
    public static void buscarCliente_telf(Connection con) throws SQLException {
        Statement st = con.createStatement();
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el teléfono del cliente para poder buscarlo:");
        int telefono = sc.nextInt();
        ResultSet rs = st.executeQuery
        ("select * from clientes where telefono="+telefono);
        while (rs.next()){
            System.out.println("DNI del cliente: " + rs.getString("nif"));
            System.out.println("Nombre del cliente: " + rs.getString("nombre"));
            System.out.println("Apellido del cliente: " + rs.getString("apellidos"));
            System.out.println("Código Postal: " + rs.getString("cp"));
            System.out.println("Teléfono: " + rs.getInt("telefono"));
            System.out.println("Fecha de nacimiento: " + rs.getString("fecha_nacimiento"));
            System.out.println("Fecha de registro: "+ rs.getString("fecha_registro"));
        }
    }
  
}
