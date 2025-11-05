package hilos.ejemplo;

/**
 * para poder hacer hilos existen 2 formas la mas correcta es tener una clase que implemente Runnable pero tambien puedes extender de Thread
 * 
 * El porque es mejor hacer el implements de Runnable es porque en java no existe herencia multiple (es decir tener mas de 1 padre) por lo que si tu hilo requiere de extender por X razón
 * de otro padre obligatoriamente no podrías hacerlo
 * 
 * Por eso es buena practica poner implements ya que de esta forma si quieres implementar varias interfaces es completamente valido el implementar mas de 1 
 * 
 * @author MaQuiNa1995
 *
 */
public class ItemThread implements Runnable {
	// variable para poder interactuar con el cofre donde el hilo va a intentar entrar
    private final Cofre cofre;
    
    // Nombre del hilo
    private final String nombreHilo;

    public ItemThread(Cofre cofre, String nombreHilo) {
        this.cofre = cofre;
        this.nombreHilo = nombreHilo;
    }

    /**
     * 
     * Lógica principal de cada hilo
     * 
     */
    @Override
    public void run() {
        
        // Lo primero que necesitamos hacer es un bucle do while en el que se intente entrar al cofre 
    	//usamos un booleano para controlar si debe intentar entrar de nuevo o no
    	boolean metido;
    	
        do {
        	// recogemos el booleano true si pudo entrar o false si lo intentó pero estaba lleno (En cuyo caso debe esperar a que otro hilo le notifique para saber si hay sitio y que lo vuelva a intentar)
        	metido = cofre.meterItem(nombreHilo);
        	// Si no se pudo meter al cofre debe volver a intentarlo
        }while (metido == false);
        
        // Si es true quiere decir que pudo entrar de primeras o cuando hubiera sitio posteriormente
        
        // Simulamos que espera 5 segundos antes de salir
        try {
            Thread.sleep(5000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Una vez que pasan esos 5 segundos el item sale del cofre y libera su espacio 
        cofre.sacarItem(nombreHilo);
    }
}
