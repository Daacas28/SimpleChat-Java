package ejercicio1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Ejercicio1Servidor {

	public static void main(String[] args) {
		ServerSocket socketServidor;
		Socket socketServicio;
		DataInputStream entradaDatos;
		DataOutputStream salidaDatos;
		int clientesMaximos = 0;
		String mensaje = "";
		OutputStream canalSalida;
		
		
		try {
			
		
				// Puerto por el que escuchará el servidor
				socketServidor = new ServerSocket(3100);

				// Tras 40 segundos, si no se ha recibido ninguna conexión, se
				// cierra el puerto.
				socketServidor.setSoTimeout(40000);

				System.out.println("Esperando una conexión entrante");
				
				while (clientesMaximos < 5) {
				// Acepta las conexiones entrantes por el puerto del socket
				// servidor
				socketServicio = socketServidor.accept();
			
				// Recogemos los datos enviados por el cliente al servidor
				entradaDatos = new DataInputStream(socketServicio.getInputStream());
				
				canalSalida = socketServicio.getOutputStream();
				salidaDatos = new DataOutputStream(canalSalida);
				
				BufferedReader entradaTeclado = new BufferedReader(new InputStreamReader(System.in));
				
				do {
				
				// mostrar mensajes del cliente
				
				mensaje = entradaDatos.readUTF();
				System.out.println(mensaje);
				

				salidaDatos.writeUTF(entradaTeclado.readLine());
				} while (!mensaje.equals("fin"));
				
				
				clientesMaximos++;
				socketServicio.close();
				
			} 
			
		} catch (IOException e) {
			System.out.println(e);
		}

	}

}
