import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * 
 */

/**
 * @author mines
 *
 */
public class factura {
	//constantes
    private final static String f_salida = "/home/mines/Escritorio/fichero.txt";//poner la ruta de salida
// método que hace una consulta sobre facturas,mostrará todas las columnas de la tabla
public static void generar_factura(Connection con) throws SQLException {
	PreparedStatement pst = (PreparedStatement) con.prepareStatement("select * from facturas");
	ResultSet rs = pst.executeQuery();
	mostrar_resultado(rs, 1);
	System.out.println();
	mostrar_resultado(rs,2);
	System.out.println();
	mostrar_resultado(rs,3);
	System.out.println();
	mostrar_resultado(rs,4);
	System.out.println();
	mostrar_resultado(rs,5);
	System.out.println();
	mostrar_resultado(rs,6);
}

/* método para devolver el resultado según la columna */
private static void mostrar_resultado(ResultSet rs, int columna)
		throws SQLException {
	try(FileWriter txt = new FileWriter("/home/mines/Escritorio/fichero.txt", true)){
		while (rs.next()) {
	    System.out.println("-----------------------------------------------------------");
	    txt.write("--------------------------------------------------------------------");
    	System.out.println("##                       Factura                         ##");
		System.out.println("-----------------------------------------------------------");
		 txt.write("##------------------------------ FACTURA -------------------------##");
		System.out.println("##  Número factura:   ##  "+"  "+ rs.getString(1)+"    "+"##"); 
		txt.write("Número factura:" + rs.getString(1)+"\n");
		System.out.println("------------------------------------------------------------");
		 txt.write("--------------------------------------------------------------------");
		System.out.println("##  Nif del cliente : ##  "+"  "+  rs.getString(2)+"   "+"##");
		txt.write("Nif:" + rs.getString(2)+"\n");
		System.out.println("------------------------------------------------------------");
		 txt.write("--------------------------------------------------------------------");
		System.out.println("##  Ean producto:     ##  "+"  "+ rs.getString(3)+"    "+"##");
		txt.write("Ean:" + rs.getString(3)+"\n");
		System.out.println("------------------------------------------------------------");
		 txt.write("--------------------------------------------------------------------");
		System.out.println("##  Cantidad:         ##  "+ "  "+ rs.getString(4)+"   "+"##");
		txt.write("Cantidad:" + rs.getString(4)+"\n");
		System.out.println("------------------------------------------------------------");
		 txt.write("--------------------------------------------------------------------");
		System.out.println("##  Precio:           ##  "+ "   "+ rs.getString(5)+"  "+"##");
		txt.write("Precio:" + rs.getString(5)+"\n");
		System.out.println("------------------------------------------------------------");
		 txt.write("--------------------------------------------------------------------");
		System.out.println("##  Fecha de compra:  ##  "+"     "+rs.getString(6)+"  "+"##");
		txt.write("Fecha de compra:" + rs.getString(6)+"\n");
		System.out.println("------------------------------------------------------------");
		
		}
	} catch (IOException e) {
		System.out.println(e.getMessage());
		System.out.println("Error escribiendo en fichero");
	}
}

}