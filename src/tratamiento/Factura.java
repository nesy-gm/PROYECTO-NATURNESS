/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tratamiento;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;




/**
 * La clase Factura incluye todos los métodos que tengan que ver con la venta
 * del producto y con la generación de archivos de escritura.
 * @author josealberto
 */
public class Factura {
    private Integer num_fact; //para poder inicializarlo a null y que la DB se
                              //encargue de asignarle el número, en lugar de 
                              //utilizar int, tenemos que utilizar Integer
    private Date fecha;
    private int ean_tratamiento;
    private String nif_cliente;
    private int cantidad;
    private double precio;
    private static final String QUERY_FACTURA = "SELECT f.num_fact, "
            + "f.nif_cliente, c.nombre, c.apellidos, f.ean_tratamiento, t.marca,"
            + " tot.descripcion, f.cantidad, f.precio, f.fecha_compra FROM "
            + "facturas f, clientes c, tratamiento t, (SELECT * from hidratante"
            + " union select * from antiedad) as tot where c.nif=f.nif_cliente "
                + "and tot.id_tratamiento=f.ean_tratamiento and "
                + "f.ean_tratamiento=t.ean ORDER BY num_fact DESC LIMIT 1";

    /**
     * Constructor vacío
     */
    public Factura() {
    }
    
    /**
     * Constructor del objeto Factura
     * @param num_fact
     * @param fecha
     * @param ean_tratamiento
     * @param nif_cliente
     * @param cantidad
     * @param precio 
     */
    public Factura(Integer num_fact, Date fecha, int ean_tratamiento, 
            String nif_cliente, int cantidad, double precio) {
        this.num_fact = null;
        this.fecha = fecha;
        this.ean_tratamiento = ean_tratamiento;
        this.nif_cliente = nif_cliente;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    
    /**
     * Getter del número de factura
     * @return num_fact
     */
    public Integer getNum_fact() {
        return num_fact;
    }
    
    /**
     * Setter del número de factura.
     * @param num_fact 
     */
    public void setNum_fact(Integer num_fact) {
        this.num_fact = num_fact;
    }

    /**
     * Getter de la fecha de la venta/factura
     * @return fecha
     */
    public Date getFecha() {
        return fecha;
    }
    
    /**
     * Setter del atributo fecha
     * @param fecha 
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    /**
     * Getter del ean del tratamiento de la venta
     * @return ean_tratamiento
     */
    public int getEan_tratamiento() {
        return ean_tratamiento;
    }
    
    /**
     * Setter del atributo ean_tratamiento
     * @param ean_tratamiento 
     */
    public void setEan_tratamiento(int ean_tratamiento) {
        this.ean_tratamiento = ean_tratamiento;
    }

    /**
     * Getter del nif del cliente en la venta
     * @return nif_cliente
     */
    public String getNif_cliente() {
        return nif_cliente;
    }
    
    /**
     * Setter del nif del cliente
     * @param nif_cliente 
     */
    public void setNif_cliente(String nif_cliente) {
        this.nif_cliente = nif_cliente;
    }
    
    /**
     * Getter de la cantidad de unidades facturadas en la venta
     * @return cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Setter de la cantidad
     * @param cantidad 
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Getter del precio total de la venta
     * @return precio
     */
    public double getPrecio() {
        return precio;
    }
    
    /**
     * Setter del atributo precio.
     * @param precio 
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    /**
     * Método para realizar venta, si no encuentra al cliente, llamamos al
     * método para crearlo, si no encuentra el producto, para, ya que "por 
     * lógica", durante una venta sólo un "jefe" puede crear un producto nuevo.
     * @param con
     * @throws SQLException 
     */
    public static void realizarVenta(Connection con) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String select = "INSERT INTO facturas VALUES (NULL, ?, ?, ?, ?, now());";
        PreparedStatement pst = con.prepareStatement(select);
        boolean autocom = con.getAutoCommit();
        con.setAutoCommit(false);
        try {
            System.out.println("Introduce el dni del cliente");
            String dni = sc.nextLine();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from clientes where nif=\""
                    + dni +"\"");
            if (rs.next()){
                pst.setString(1, dni);  
            }else{
                System.out.println("El cliente no existe, vamos a crearlo");
                Cliente cli = new Cliente();
                cli.altaCliente(con);
                 
            }
            System.out.println("Introduce el ean del producto");
            int ean = sc.nextInt();
            ResultSet rs2 = st.executeQuery("Select * from tratamiento where "
                    + "ean="+ean);
            if (rs2.next()){
                pst.setInt(2, ean);
                
            }else{
                System.out.println("El producto no existe, por favor, revisa"
                        + "el EAN y confirma que es correcto.");
                System.out.println("Haremos rollback y nos dirigiremos a la "
                        + "creación del producto");
                con.rollback();
                System.out.println("Indica si se trata de un tratamiento "
                        + "hidratante o de uno antiedad");
                sc.nextLine();
                String tipo = sc.nextLine();
                if (tipo.equals("antiedad")){
                    Antiedad anti = new Antiedad();
                    anti.introducirTratamiento(con);
                }else{
                    Hidratante hidra = new Hidratante();
                    hidra.introducirTratamiento(con);
                }                      
            }
            System.out.println("¿Qué cantidad se lleva el cliente de este "
                    + "producto?");
            int cantidad = sc.nextInt();
            ResultSet rs3 = st.executeQuery("Select * from tratamiento where "
                    + "ean="+ean);            
            if (rs3.next()){
                while (cantidad>rs3.getInt("stock")){
                    System.out.println("No hay unidades suficientes, las unidades "
                        + "disponibles son " + rs3.getInt("stock"));
                    cantidad = sc.nextInt();
                }
                pst.setInt(3, cantidad);
                pst.setFloat(4, rs3.getFloat("precio_unidad")*cantidad);
            }   
            int result = st.executeUpdate("update tratamiento set "
                        + "stock=stock-" + cantidad +" where ean="+ean);
            if (result == 1){
                System.out.println("Se han restado "+ cantidad + " unidades "
                        + "del stock");
            }
            pst.execute();
            System.out.println("La venta se ha realizado correctamente revisa"
                    + "la factura y entrégasela al cliente");
        }catch (SQLException ex){
            System.out.println("Algo ha fallado: Error " +ex.getMessage());
            System.out.println("Hacemos rollback");
            con.rollback();
        }catch (Exception ex){
            System.out.println("Algo ha fallado: Error " +ex.getMessage());
            System.out.println("Hacemos rollback");
            con.rollback();
        }finally{
            pst.close();
            con.setAutoCommit(autocom);     
            generar_factura(con);
        }
                
    }
    
       
   /**
    * Método para generar la factura inmediata de la venta que se genera en
    * el método realizarVenta().
    * @param con
    * @throws SQLException 
    */
    public static void generar_factura(Connection con) throws SQLException {
	PreparedStatement pst = con.prepareStatement(QUERY_FACTURA);
	ResultSet rs = pst.executeQuery();
	try(FileWriter txt = new FileWriter("/home/josealberto/Escriptori/"
                + "factura.txt")){
		while (rs.next()) {
			txt.write("\n----------------------------FACTURA--------"
                                + "----------------");
			txt.write("\n Número de factura :"+rs.getString(1));
			txt.write("\n-------------------------------------------"
                                + "----------------");
			txt.write("\n Nif del CLiente :" +rs.getString(2));
                        txt.write("\n Nombre y apellidos del cliente: " 
                                + rs.getString(3) + " " + rs.getString(4));
			txt.write("\n Ean :"+ rs.getString(5));
                        txt.write("\n Marca y descripción: " + rs.getString(6)
                        + " " + rs.getString(7));
			txt.write("\n Cantidad : "+rs.getString(8));
			txt.write("\n Precio :"+rs.getString(9));
			txt.write("\n Fecha de compra :"+rs.getString(10));
			txt.write("\n-------------------------------------------"
                                + "----------------");
		}
	}catch (IOException e) {
		System.out.println(e.getMessage());
		System.out.println("Error escribiendo en fichero");
	}
}
    /**
     * Método de búsqueda de factura a través del número de factura, lo busca
     * en la base de datos y nos la devuelve impresa.
     * @param con
     * @throws SQLException 
     */
    public static void buscar_fact_num(Connection con) throws SQLException {
        Scanner lector = new Scanner(System.in);
	PreparedStatement pst = con.prepareStatement("select * from facturas "
                + "where num_fact=?" );
	try(BufferedWriter txt = new BufferedWriter(new FileWriter
        ("/home/josealberto/Escriptori/factura.txt"))){
            System.out.println("Introduce el número de factura");
            pst.setString(1, lector.next());
            ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                            txt.write("\n--------------FACTURA------------------"
                                    + "--");
                            txt.write("\n Número de factura :"+rs.getString(1));
                            txt.write("\n Nif del CLiente :" +rs.getString(2));
                            txt.write("\n Ean :" +rs.getString(3));
                            txt.write("\n Cantidad :" +rs.getString(4));
                            txt.write("\n Precio :" +rs.getString(5));
                            txt.write("\n Fecha_compra :" +rs.getString(6)); 
                            txt.write("\n--------------------------------------"
                                    + "----");

                    }
	}catch (IOException e) {
		System.out.println(e.getMessage());
		System.out.println("Error escribiendo en fichero");
	}
}
   /**
    * Método de búsqueda de facturas en la base de datos utilizando el dni,
    * nos imprime todas las facturas del cliente indicado en un archivo.
    * @param con
    * @throws SQLException 
    */
   public static void buscar_fact_dni(Connection con) throws SQLException {
        Scanner lector = new Scanner(System.in);
        PreparedStatement pst = con.prepareStatement
        ("select * from facturas where nif_cliente=?" );
	try(BufferedWriter txt = new BufferedWriter(new FileWriter("/home/"
                + "josealberto/Escriptori/factura.txt"))){
	System.out.println("Introduce el DNI del cliente");
	pst.setString(1, lector.next());
		ResultSet rs = pst.executeQuery();
	
		while (rs.next()) {
                        txt.write("\n--------------FACTURA--------------------");
			txt.write("\n Número de factura :"+rs.getString(1));
			txt.write("\n Nif del CLiente :" +rs.getString(2));
			txt.write("\n Ean :" +rs.getString(3));
			txt.write("\n Cantidad :" +rs.getString(4));
			txt.write("\n Precio :" +rs.getString(5));
			txt.write("\n Fecha_compra :" +rs.getString(6)); 
			txt.write("\n------------------------------------------");
		
		}
	}catch (IOException e) {
		System.out.println(e.getMessage());
		System.out.println("Error escribiendo en fichero");
	}
}
}

