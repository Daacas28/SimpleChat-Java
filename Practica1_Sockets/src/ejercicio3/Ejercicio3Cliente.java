package ejercicio3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Ejercicio3Cliente implements Runnable{
	
	private static String mensaje;
	private static Socket socketCliente;
	private static DataOutputStream salidaDatos;
	private static DataInputStream entradaDatos;
	private static BufferedReader entradaTeclado;

	public static void main(String argv[]) {
		Socket socketCliente;

		// Crea una instancia de BufferedReader para guardar la entrada por
		// teclado de datos.
		entradaTeclado = new BufferedReader(new InputStreamReader(System.in));

		// Variable donde se guardará el mensaje
		String mensaje = "";

		try {

			// Se instancia el Socket que se conectará a nuestro servidor por el
			// puerto 3100
			socketCliente = new Socket("127.0.0.1", 3100);

			// Declaramos e instanciamos el objeto DataOutputStream
			// que nos valdrá para enviar datos al servidor destino
			 salidaDatos = new DataOutputStream(socketCliente.getOutputStream());

			 entradaDatos = new DataInputStream(socketCliente.getInputStream());

			// Creamos un bucle do while en el que enviamos al servidor el
			// mensaje
			// los datos que hemos obtenido despues de ejecutar la función
			// "readLine" en la instancia "in"

			Ejercicio3Cliente cliente = new Ejercicio3Cliente();
			Thread hilo = new Thread(cliente);
			hilo.start();
		/*	
			do {
				
				// mientras el mensaje no encuentre la cadena fin, seguiremos
				// ejecutando
				// el bucle do-while

				// mostrar mensajes del servidor
				String mensajeServidor = "";
			//	while (!mensajeServidor.isEmpty()){
				mensajeServidor = entradaDatos.readUTF();
				System.out.println(mensajeServidor);
			//	}
				mensaje = entradaTeclado.readLine();
				// enviamos el mensaje codificado en UTF
				salidaDatos.writeUTF(mensaje);

			} while (!mensaje.startsWith("fin"));
*/
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		  try{
	            //Ciclo infinito que escucha por mensajes del servidor y los muestra en el panel
	            while(true){
	                mensaje = entradaDatos.readUTF();
	                System.out.println(mensaje);
	                mensaje = entradaTeclado.readLine();
	                enviarMsg(mensaje);
	            }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	    }
	
	
	
	 public void enviarMsg(String msg){
	        try {
	            salidaDatos.writeUTF(msg);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		
	}
