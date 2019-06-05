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
 * La clase hidratante es una especialización de la clase tratamiento.
 * @author josealberto
 */
public class Hidratante extends Tratamiento{
    private Integer id;
    private String descripcion;
    private String zona_aplicacion;
        
    /**
     * Constructor vacío
     */
    public Hidratante() {       
           
    }
    /**
     * Constructor de la clase hidratante sólo con sus atributos.
     * @param descripcion
     * @param zona_aplicacion 
     * @param id iniciamos el parámetro id a null ya que será la base de datos
     * quien nos hará el incremento de este número
     */
    public Hidratante(String descripcion, String zona_aplicacion, Integer id) {
        this.id = null;
        this.descripcion = descripcion;
        this.zona_aplicacion = zona_aplicacion;
        
    }

    /**
     * Constructor de la clase hidratante con sus atributos y los atributos
     * de la clase padre.
     * @param id Iniciamos a null ya que será la propia base de datos que se
     *           encargará de asignarle un número autoincremental.
     * @param descripcion
     * @param zona_aplicacion
     * @param ean
     * @param marca
     * @param precio
     * @param stock 
     */
    public Hidratante(Integer id, String descripcion, String zona_aplicacion, Integer ean, 
            String marca, double precio, int stock) {
        super(ean, marca, precio, stock);
        this.id = null;
        this.descripcion = descripcion;
        this.zona_aplicacion = zona_aplicacion;
    }

    /**
     * Getter de la descripción
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Setter de la descripción
     * @param descripcion 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Getter de la zona de aplicación del producto.
     * @return zona_aplicacion
     */
    public String getZona_aplicacion() {
        return zona_aplicacion;
    }

    /**
     * Setter de la zona de aplicación
     * @param zona_aplicacion 
     */
    public void setZona_aplicacion(String zona_aplicacion) {
        this.zona_aplicacion = zona_aplicacion;
    }

    
   
    /**
     * Método para mostrarnos los atributos del objeto
     * @param con
     * @throws java.sql.SQLException
     */
    @Override
    public void mostrarTratamiento(Connection con) throws SQLException {
        super.mostrarTratamiento(con);
        Statement st = con.createStatement();
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el EAN del tratamiento:");
        int eantrat = sc.nextInt();
        ResultSet rs = st.executeQuery("select * from (SELECT * from hidratante "
                + "union select * from antiedad) as total where "
                + "id_tratamiento=\""+eantrat+"\"");
        while (rs.next()){
            System.out.println("El id del tratamiento es: " 
                    + rs.getInt("id"));
            System.out.println("La descripción del tratamiento es: " 
                    + rs.getString("descripcion"));
            System.out.println("La zona de aplicación principal de este "
                    + "tratamiento es " + rs.getString("zona_aplicacion"));
        }
    }

    /**
     * Método para dar de alta por el usuario un nuevo tratamiento 
     * @throws java.sql.SQLException
     */
    @Override
    public void introducirTratamiento(Connection con) throws SQLException{
        super.introducirTratamiento(con);
        Scanner sc = new Scanner(System.in);
        String insert = "INSERT INTO hidratante (id, id_tratamiento, "
                + "descripcion, zona_aplicacion) "
                + "VALUES (NULL, ?, ?, ?);";
        PreparedStatement pst = con.prepareStatement(insert);
        System.out.println("Vamos a introducir un tratamiento hidratante");
        System.out.println("Por favor, introduce el ean del tratamiento");
        pst.setInt(1, sc.nextInt());
        System.out.println("Escribe una pequeña descripción");
        pst.setString(2, sc.next());
        System.out.println("Ahora la zona de aplicación");
        sc.nextLine();
        pst.setString(3, sc.nextLine());
        pst.execute();
        System.out.println("El nuevo tratamiento hidratante se ha introducido"
                + "correctamente");
    }
    
    
    
}
