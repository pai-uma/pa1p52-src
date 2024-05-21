package indices;

import java.io.PrintWriter;
import java.util.Map;
import java.util.SortedMap;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * La clase IndiceContador hereda de la clase abstracta IndiceAbstracto, 
 * y tendrá, además de la variable frases que hereda de 
 * IndiceAbstracto, una variable indice donde se almacenará el índice que 
 * se va a construir a partir del texto disponible en la variable de 
 * instancia frases y los delimitadores que se especifiquen. 
 * El índice a construir se guardará en una correspondencia en la que a 
 * cada palabra del texto se le asocia el número de apariciones de dicha 
 * palabra (véase la salida en el caso del ejemplo anterior). 
 * Las palabras se deben mantener ordenadas lexicográficamente.
 */
public class IndiceContador extends IndiceAbstracto {
	// Variable privada para almacenar la correspondencia entre palabras y enteros.
	private SortedMap<String, Integer> indice;

	/**
	 * Constructor que inicializa las variables correspondientes. 
	 * Se invoca a super para inicializar el texto a una lista de líneas vacía, 
	 * y se inicializa a una correspondencia vacía la variable que representa el índice.
	 */
	public IndiceContador() {
		super();
		indice = new TreeMap<>();
	}

	/**
	 * Redefine el método que construye el índice, calculando el número de
	 * apariciones de cada palabra significativa en el texto frases y completando 
	 * la estructura indice para mantener esta información.
	 */
	@Override
	public void resolver(String delim) {
		indice.clear();
		for(String linea : frases) {
			try (Scanner sc = new Scanner(linea)) {
				sc.useDelimiter(delim);
				while (sc.hasNext()) {
					agregar(sc.next());
				}
			}
		}
	}

	/**
	 * Método privado auxiliar para añadir una palabra al índice.
	 * @param palabra
	 */
	private void agregar(String palabra) {
		if ( ! palabra.isEmpty() ) {
			palabra = palabra.toLowerCase();
			Integer cnt = indice.get(palabra);
			if (cnt == null) {
				indice.put(palabra, 1);
			} else {
				indice.put(palabra, cnt + 1);
			}
			//Integer cnt = indice.getOrDefault(palabra, 0);
			//indice.put(palabra, cnt+1);
		}
	}

	/**
	 * Método para presentar el índice con el formato esperado. Para cada asociación,
	 * se incluirá una línea con
	 * el formato siguiente:
	 * Palabra   N
	 * Donde, para cada palabra, le sigue el número de veces que aparece en el texto.
	 */
    @Override
	public void presentarIndice(PrintWriter pw) {
		// for(String palabra : indice.keySet()) {
		//	  pw.printf("%-10s %4d\n", palabra, indice.get(palabra));
		// }
		for(Map.Entry<String,Integer> e : indice.entrySet()) {
			pw.printf("%-10s %4d\n", e.getKey(), e.getValue());
		}
	}
}
