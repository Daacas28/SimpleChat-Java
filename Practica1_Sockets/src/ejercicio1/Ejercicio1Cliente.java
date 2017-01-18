package ejercicio1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Ejercicio1Cliente {

	public static void main(String argv[]) {
		Socket socketCliente;

		// Crea una instancia de BufferedReader para guardar la entrada por
		// teclado de datos.
		BufferedReader entradaTeclado = new BufferedReader(new InputStreamReader(System.in));

		// Variable donde se guardará el mensaje
		String mensaje = "";

		try {

			// Se instancia el Socket que se conectará a nuestro servidor por el puerto 3100
			socketCliente = new Socket("127.0.0.1", 3100);

			// Declaramos e instanciamos el objeto DataOutputStream
			// que nos valdrá para enviar datos al servidor destino
			DataOutputStream salidaDatos = new DataOutputStream(socketCliente.getOutputStream());

			DataInputStream entradaDatos = new DataInputStream(socketCliente.getInputStream());

			// Creamos un bucle do while en el que enviamos al servidor el
			// mensaje
			// los datos que hemos obtenido despues de ejecutar la función
			// "readLine" en la instancia "in"

			do {
				mensaje = entradaTeclado.readLine();
				// enviamos el mensaje codificado en UTF
				salidaDatos.writeUTF(mensaje);
				// mientras el mensaje no encuentre la cadena fin, seguiremos
				// ejecutando
				// el bucle do-while

				// mostrar mensajes del servidor
				String mensajeServidor = "";
				mensajeServidor = entradaDatos.readUTF();
				System.out.println(mensajeServidor);

			} while (!mensaje.startsWith("fin"));

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
