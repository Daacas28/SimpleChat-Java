package ejercicio3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Ejercicio3Servidor implements Runnable {

	private static Ejercicio3Servidor hiloCliente;
	private DataInputStream entradaDatos;
	private DataOutputStream salidaDatos;
	private String mensaje = "";
	private OutputStream canalSalida;
	private static ServerSocket socketServidor;
	private static Socket socketServicio;
	private static LinkedList<Socket> usuarios = new LinkedList<Socket>();

	// private List<socketServicio> usuarios;

	public static void main(String[] args) {

		hiloCliente = new Ejercicio3Servidor();

		int numeroCliente = 0;

		try {
			// Puerto por el que escuchará el servidor

			socketServidor = new ServerSocket(3100);

			// Tras 40 segundos, si no se ha recibido ninguna conexión, se
			// cierra el puerto.

			// socketServidor.setSoTimeout(40000);

			System.out.println("Esperando una conexión entrante");

			// Bucle infinito
			do {
				// Acepta al cliente, llamándo al método para asignarle un hilo
				socketServicio = socketServidor.accept();
				usuarios.add(socketServicio);
				numeroCliente++;
				creaHilos(numeroCliente, usuarios, socketServicio);

				System.out.println("Conexión nueva");
				System.out.println("Número de conexiones totales: " + numeroCliente);

			} while (true);

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// Método que crea un hilo por cada cliente
	private static void creaHilos(int numeroCliente, LinkedList<Socket> usuarios2, Socket socketServicio2) {

		Thread hilo = new Thread(hiloCliente);
		hilo.setName("Cliente " + numeroCliente);
		hilo.start();

	}

	@Override
	public void run() {
		try {
			entradaDatos = new DataInputStream(socketServicio.getInputStream());

			canalSalida = socketServicio.getOutputStream();
			salidaDatos = new DataOutputStream(canalSalida);

			BufferedReader entradaTeclado = new BufferedReader(new InputStreamReader(System.in));
			salidaDatos.writeUTF("Bienvenido al servidor de chat");
			do {
				String recibido = entradaDatos.readUTF();
				System.out.println(Thread.currentThread().getName() + ": " +recibido);
	               //Cuando se recibe un mensaje se envia a todos los usuarios conectados 
	                for (int i = 0; i < usuarios.size(); i++) {
	                	salidaDatos = new DataOutputStream(usuarios.get(i).getOutputStream());
	                    salidaDatos.writeUTF(Thread.currentThread().getName() + ": " +recibido);
	                }
			

			} while (true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

	



// BIBLIOFRAFIA
/*
 * http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.
 * html
 */