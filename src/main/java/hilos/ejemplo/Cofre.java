package hilos.ejemplo;

import java.util.ArrayList;
import java.util.List;


/**
 * Aqui tenemos el recurso donde se van a guardar o meter cierto objeto es decir es el punto común
 * 
 * En este caso se intentan meter 20 items a la vez en un cofre en el que el maximo de capacidad es 10
 * 
 * Entonces siempre van a poder entrar 10 hilos y van a tener que esperar 10 a que esos que están en el cofre sean sacados 
 * 
 * @author MaQuiNa1995
 *
 */
public class Cofre {
	
	// Constante para definir el tamaño del cofre 
    private final int CAPACIDAD_MAXIMA = 10;
    // Es nuestra lista donde metemos nuestros items simula un cofre con tamaño 10
    private List<String> items = new ArrayList<>();

    /**
     * Este metodo es el encargado de interactuar con la lista que tenemos para meter los items
     * 
     * El porque se devuelve un boolean es porque queremos informar al hilo de si se pudo insertar en el cofre o no para en el caso de:
     * - true: le decimos al hilo que si pudo entrar al cofre para seguir su lógica en este caso esperar 5 segundos simulando alguien que coge ese objeto
     * - false: por ahora el cofre está lleno por lo que se debe de intentar meter al cofre mas tarde cuando este tenga espacio esto se hace con el wait(); y posteriormente el notifyAll(); (Está en el hilo la lógica InsertadorThread)
     * 
     * Hay explicaciones mas adelante de esto
     * 
     * @param nombreHilo Nombre del hilo que realiza el intento.
     * @return true si se añadió, false si estaba lleno.
     */
    public synchronized boolean meterItem(String nombreHilo) {
    	
    	// mensaje informativo
        System.out.println(nombreHilo + " intenta entrar al cofre objetos actuales dentro: " + items.size());
        
        // booleano control para informar al hilo si el cofre está lleno con false o true si en ese caso había sitio y fue insertado dentro
        boolean anadido = false;
        
        // Verificamos si hay espacio 
        if (items.size() < CAPACIDAD_MAXIMA) {
        	
        	// Si hay espacio entra aqui y añade el item al cofre osea a la lista
            items.add(nombreHilo);
            // Mensaje informativo
            System.out.println(nombreHilo + " ha entrado al cofre");
            
            // cambiamos el booleano a true
            anadido = true;
        } else {
        	try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        // Devolvemos el booleano para informar al hilo del exito o no de la inserción
        return anadido;
    }
    
    /**
     * Este metodo se encarga de liberar el hueco que estuviera ocupando X item 
     * 
     * @param nombreHilo
     */
    public synchronized void sacarItem(String nombreHilo) {
    	
    	// se elimina el item del cofre (en este caso de la lista)
        items.remove(nombreHilo);
        
        // mensaje informativo
        System.out.println(nombreHilo + " sale del cofre");
        
        // El notifyAll() se usa para que todos los hilos que no pudieron insertar como están esperando a que haya hueco libre y en este punto de la ejecución digamos que
        // se libera al menos 1 espacio lo que hacemos es notifyAll() -> notificar a todos los hilos que estuvieran esperando (wait();) que pueden volver a intentarlo
        // ya que hubo un cambio en el cofre relevante para que al menos uno de ellos pueda entrar
        notifyAll();
        notifyAll();
    }
}