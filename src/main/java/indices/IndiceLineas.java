package indices;

import java.io.PrintWriter;
import java.util.StringJoiner;
import java.util.Scanner;
import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * La clase IndiceLineas hereda de IndiceAbstracto. Incluye una variable indice
 * que almacena una correspondencia en la que a cada palabra del texto se le asocia 
 * un conjunto con los números de líneas en donde aparece. 
 * Las palabras estarán ordenadas lexicográficamente y los números de línea de menor a mayor.
 */
public class IndiceLineas extends IndiceAbstracto {
	// Variable privada para almacenar el índice, una correspondencia que asocia 
	//a cada palabra un conjunto con las líneas en que aparece.
	private SortedMap<String, SortedSet<Integer> > indice;

	/**
	 * Constructor que inicializa las variables correspondientes. 
	 * Se invoca a super para inicializar el texto a una lista de líneas vacía, 
	 * y se inicializa a una correspondencia vacía la variable que representa el índice.
	 */
	public IndiceLineas() {
		super();
		indice = new TreeMap<>();
	}

	/**
	 * Redefine el método que construye el índice, asociando a cada palabra
	 * en el texto frases el conjunto con los números de línea en que aparece.
	 */
	@Override
	public void resolver(String delim) {
		indice.clear();
		int numLinea = 0;
		for (String linea : frases) {
			++numLinea;
			try (Scanner sc = new Scanner(linea)) {
				sc.useDelimiter(delim);
				while (sc.hasNext()) {
					agregar(sc.next(), numLinea);
				}
			}
		}
	}
	
	/**
	 * Método privado auxiliar para añadir en el índice el número 
	 * de línea (segundo argumento) a la palabra que se pasa como
	 * primer argumento.
	 * 
	 * @param palabra	Palabra a actualizar en el índice
	 * @param numLinea	Número de línea a añadir
	 */
	private void agregar(String palabra, int numLinea) {
		if ( ! palabra.isEmpty() ) {
			palabra = palabra.toLowerCase();
			SortedSet<Integer> set = indice.get(palabra);
			if (set == null) {
				set = new TreeSet<>();
				indice.put(palabra, set);
			}
			set.add(numLinea);
		}
	}

	/**
	 * Método para presentar el índice con el formato esperado. Para cada asociación, 
	 * se incluirá una línea con el formato siguiente:
	 * Palabra   <L1,L2,...,Ln>
	 * Donde, para cada palabra, le seguirá las líneas en que aparece la palabra en el texto,
	 * separadas por comas y entre ángulos.
	 */
	@Override
	public void presentarIndice(PrintWriter pw) {
		for(Map.Entry<String, SortedSet<Integer>> e : indice.entrySet()) {
			pw.printf("%-10s %s\n", e.getKey(), col2str(e.getValue()));
		}
	}

	/** 
	 * Método auxiliar que convierte una colección de enteros a un String, separando
	 * por comas los enteros, y encerrándolos entre ángulos.
	 * 
	 * @param c		Colección de enteros
	 * @return		Cadena de enteros separadas por comas y entre ángulos
	 */
	private String col2str(Collection<Integer> c) {
		StringJoiner sj = new StringJoiner(",", "<", ">");
		for (Integer i : c) {
			sj.add(i.toString());
		}
		return sj.toString();
	}
}
