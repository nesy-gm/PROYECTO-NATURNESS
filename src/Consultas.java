import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Consultas {

	private static Scanner lector = new Scanner(System.in);

	// private static String f_salida = "ruta del fichero";

	public static void consulta(Connection con) throws SQLException {
		int opcion;

		boolean salir = false;
		while (!salir) {
			System.out.println("Has selecionado la opción consulta, "
					+ "indica el tipo de consulta a realizar: ");
			System.out.println("-------------------------------------");
			System.out.println("       Tipos de consulta             ");
			System.out.println("-------------------------------------");
			System.out.println("1.Opcion 1 - Consulta sobre tablas sin "
					+ "clave primaria");
			System.out.println("2.Opcion 2 - Consulta con Prepared statement "
					+ "sin clave primaria");
			System.out.println("3.Opcion 3 - Consulta sobre tablas "
					+ "con clave primaria ");
			System.out.println("4.Opcion 4 - Consulta con Prepared statement "
					+ "con clave primaria");
			System.out.println("5.Opcion 5 - Salir y volver al menú ");
			System.out.println("-------------------------------------");

			opcion = lector.nextInt();
			switch (opcion) {
			case 1:
				consulta_sinclave(con);
				break;
			case 2:
				prepare_st_sinclave(con);
				break;
			case 3:
				// consulta_conclave(con);
				break;
			case 4:
				// prepare_st_conclave(con);
				break;
			case 5:
				salir = true;
				break;

			default:
				System.out.println("Solo números entre 1 y 5");
			}
		}
	}

	private static void consulta_sinclave(Connection con) throws SQLException {
		Statement st = con.createStatement();
		System.out
				.print("Introduce nombre para conocer el código postal del cliente:");
		ResultSet rs = st.executeQuery("select * from clientes where nombre=\""
				+ lector.next() + "\"");
		mostrar_resultado(rs, 4);
	}

	// método que hace una consulta sobre tablas, pedirá el campo a mostrar y se
	// lo indicaremos
	private static void prepare_st_sinclave(Connection con) throws SQLException {
		PreparedStatement pst = con
				.prepareStatement("select * from tratamiento where zona_aplicación=?");
		System.out
				.println("Introduce la zona de aplicación del tratamiento y te diremos los productos disponibles");
		System.out.println("Las zonas que dispones son:");
		System.out.println("----------------------------");
		System.out.println("          Zonas             ");
		System.out.println("----------------------------");
		System.out.println("         rostro             ");
		System.out.println("         ojos               ");
		System.out.println("         cuerpo             ");
		System.out.println("         manos              ");
		System.out.println("         piernas            ");
		System.out.println("----------------------------");
		pst.setString(1, lector.next());
		ResultSet rs = pst.executeQuery();
		mostrar_resultado(rs, 3);
		System.out.println();
	}

	/* método para devolver el resultado según la columna */
	private static void mostrar_resultado(ResultSet rs, int columna)
			throws SQLException {
		try(FileWriter txt = new FileWriter("/home/mines/Escritorio/fichero.txt", true)){
			while (rs.next()) {
			System.out.println("Nombre del producto: "+rs.getString(3)+" "); 
			System.out.println("Dirección : " + rs.getString(3));
			txt.write("nombre:" + rs.getString(columna)+"\n");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Error escribiendo en fichero");
		}
	}

	/* método para actualizar */
	public static void actualiza(final Connection con) throws SQLException {
		PreparedStatement pst= null;
		try {
			Statement st = con.createStatement();
			con.setAutoCommit(false);
			pst = con.prepareStatement("update clientes set cp=? where nombre=?");
			System.out.println("Introduce el nombre del cliente de quien quieres modificar el cp:");
			pst.setString(2, lector.next());
			System.out.print("El nuevo cp será :");
			pst.setString(1, lector.next());
			pst.executeUpdate();
			con.commit();
			System.out.println("La actualización se ha realizado correctamente");
		} catch (SQLException ex) {
			System.out.println("SQLSTATE " + ex.getSQLState() + "SQLMESSAGE"
					+ ex.getMessage());
			System.out.println("Hago rollback");
			con.rollback();
		} finally {
			con.setAutoCommit(true);

			/*
			PreparedStatement pst = con.prepareStatement("update cp=? from Beer where nombre=?");
			System.out.println("nombre= ");
			pst.setString(2, lector.next());
			System.out.println("Nuevo CP:");
			System.out.print("cp= ");
			pst.setString(1, lector.next());
			
			pst.executeUpdate();
			*/
			if (pst!=null){pst.close();}
					
			
		}
	}
}
// poner try catch y si no va cerrarlo

// poner try catch y si no va cerrarlo

/*
 * pst.setString(2, lector.next()); System.out.print("brewery= ");
 * pst.setString(1, lector.next()); pst.executeUpdate(); *
 * 
 * 
 * PreparedStatement pst =
 * con.prepareStatement("update brewery=? from Beer where name=?");
 * System.out.println("Modificar cerveza:"); pst.setString(2, lector.next());
 * System.out.print("brewery= "); pst.setString(1, lector.next());
 * pst.executeUpdate(); pst.close(); poner try catch y si no va cerrarlo } / } }
 */
