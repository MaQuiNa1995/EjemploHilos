package hilos.ejemplo;

public class Main {
	
	// Constante que define los hilos a usar
	private static final int NUMERO_DE_HILOS = 20;

	public static void main(String[] args) {
		System.out.println("Simulación de un Cofre con capacidad 10 y " + NUMERO_DE_HILOS + " hilos.");
		System.out.println("-------------------------------------------------------------------");

		Cofre cofre = new Cofre();

		// For para crear y lanzar 1 a 1 cada hilo del 1 al NUMERO_DE_HILOS
		for (int i = 1; i <= NUMERO_DE_HILOS; i++) {
			
			// Formamos el nombre del hilo con el contador i
			String nombre = "Hilo-" + i;
			
			// Creamos el objeto ItemThread
			ItemThread item = new ItemThread(cofre, nombre);
			
			// Le ejecutamos para que inicie cada hilo
			new Thread(item).start();
		}

	}
}
