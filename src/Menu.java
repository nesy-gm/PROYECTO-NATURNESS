import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author mines
 *
 */
public class Menu {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Apéndice de método generado automáticamente
	
			int opcion;
			Scanner lector = new Scanner(System.in);
			boolean salir = false;
			Connection con = null;
			
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NATURNESS",
						"alumne", "alualualu");
				System.out.println();

				while (!salir) {
					System.out.println("Has entrado en la bases de datos Naturness, "
							+ "que opción deseas? ");
					System.out.println("----------------------------");
					System.out.println("        Menú    	   		");
					System.out.println("----------------------------");
					System.out.println("1.Opcion 1 - Consulta");
					System.out.println("2.Opcion 2 - Actualización");
					System.out.println("3.Opcion 3 - Insert");
					System.out.println("4.Opcion 4 - Salir");
					System.out.println("----------------------------");
					System.out.println("Escribe una de las opciones");
					
					opcion = lector.nextInt();
					
					switch (opcion) {
					case 1:
						Consultas.consulta(con);
						break;
					case 2:
						Consultas.actualiza(con);
						break;
					case 3:
						// insert();

					case 4:
						// transaction();
						
					case 5:
						salir = true;
						System.out.println("Has cerrado la base de datos Naturness");
						break;
						
					default:
						System.out.println("Solo números entre 1 y 5");
					}
				}
			} catch (SQLException ex) {
				System.out.println("Error");
				System.out.println(ex.getSQLState());
				System.out.print(ex.getLocalizedMessage());
			}
		}
	

	}


