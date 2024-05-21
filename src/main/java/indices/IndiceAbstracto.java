package indices;

import java.util.LinkedList;
import java.util.List;

/**
 * Clase abstracta que implementa (parcialmente9 la interfaz Indice, 
 * y que establece el almacenamiento de las frases en una lista. 
 */
public abstract class IndiceAbstracto  implements Indice {
	// Variable protegida para almacenar el texto, organizado en líneas.
	protected List<String> frases;
	
	/**
	 * Constructor que inicializa a una lista vacía el almacenamiento del texto.
	 */
	public IndiceAbstracto() {
		frases = new LinkedList<String>();
	}

	/**
	 * Definición del método para añadir una línea al texto.
	 */
	@Override
	public void agregarFrase(String linea) {
		frases.add(linea);
	}

}
