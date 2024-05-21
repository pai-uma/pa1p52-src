package indices;

import java.io.PrintWriter;
import java.util.StringJoiner;
import java.util.Scanner;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * la clase IndicePosicionesEnLineas hereda de IndiceAbstracto.
 * El indice será una correspondencia en la que a cada palabra se le asocia una
 * segunda correspondencia en la que a cada número de línea se le asocia un 
 * conjunto con las posiciones de la palabra en cada número de línea. 
 * Las palabras estarán ordenadas lexicográficamente y las líneas y 
 * posiciones de menor a mayor.
 */
public class IndicePosicionesEnLineas extends IndiceAbstracto {
	/*
	 * Variable privada que representa el índice, donde a cada palabra
	 * se le asocia una correspondencia entre líneas en que aparece la palabra
	 * y un conjunto con las posiciones en las que aparece la palbra dentro 
	 * de la línea. Todas las colecciones son ordenadas.
	*/ 
	private SortedMap<String, SortedMap<Integer, SortedSet<Integer> > > indice;

	/**
	 * Constructor que inicializa las variables correspondientes. 
	 * Se invoca a super para inicializar el texto a una lista de líneas vacía, 
	 * y se inicializa a una correspondencia vacía la variable que representa el índice.
	 */
	public IndicePosicionesEnLineas() {
		super();
		indice = new TreeMap<>();
	}

	/**
	 * Redefine el método que construye el índice, asociando a cada palabra
	 * en el texto frases una correspondencia que asocia a cada línea en 
	 * que aparece la palabra, el conjunto con las posiciones en que dicha
	 * palabra aparece en la línea.
	 */
	@Override
	public void resolver(String delim) {
		indice.clear();
		int numLinea = 0;
		for (String linea : frases) {
			++numLinea;
			try (Scanner sc = new Scanner(linea)) {
				sc.useDelimiter(delim);
				int numPos = 0;
				while (sc.hasNext()) {
					++numPos;
					agregar(sc.next(), numLinea, numPos);
				}
			}
		}
	}

	/**
	 * Método auxiliar para añadir al índice la posición (tercer argumento) 
	 * en la que una palabra (primer argumento) aparece en determinada 
	 * línea (segundo argumento).
	 * 
	 * @param palabra	Palabra a actualizar en el índice
	 * @param numLinea	Línea en la que aparece la palabra
	 * @param numPos	Posición de la palabra dentro de la línea
	 */
	private void agregar(String palabra, int numLinea, int numPos) {
		if ( ! palabra.isEmpty() ) {
			palabra = palabra.toLowerCase();
			SortedMap<Integer, SortedSet<Integer>> map = indice.get(palabra);
			if (map == null) {
				map = new TreeMap<>();
				indice.put(palabra, map);
			}
			SortedSet<Integer> set = map.get(numLinea);
			if (set == null) {
				set = new TreeSet<>();
				map.put(numLinea, set);
			}
			set.add(numPos);
		}
	}

	/**
	 * Método para presentar el índice con el formato esperado. Para cada asociación, 
	 * se incluirán varias línea con el formato siguiente:
	 * Palabra  
	 * 		L1 <P11,P2...P1K1>
	 * 		...
	 * 		Lm <Pm1,Pm2...PmKm>
	 * Donde, para cada Palabra, se añade una fila con la línea en que aparece la palabra (Li)
	 * y las posiciones en que la palabra aparece en esa línea: <Pi1,Pi2...PiKi> 
	 * (separadas por comas y entre ángulos).
	 */
    @Override
	public void presentarIndice(PrintWriter pw) {
		for(Map.Entry<String, SortedMap<Integer, SortedSet<Integer>>> e : indice.entrySet()) {
			pw.println(e.getKey());
			presLnPos(pw, e.getValue());
		}
	}
	
    /**
     * Método auxiliar para presentar una fila en la que se indica la línea en que
     * aparece una palabra seguida del conjunto de posiciones que ocupa en esa línea.
     * 
     * @param pw	PrintWriter
     * @param mp	Correspondencia que asocia conjunto de posiciones a líneas.
     */
	private void presLnPos(PrintWriter pw, SortedMap<Integer, SortedSet<Integer>> mp) {
		for(Map.Entry<Integer, SortedSet<Integer>> e : mp.entrySet()) {
			pw.printf("%10s %4d %s\n", "", e.getKey(), col2str(e.getValue()));
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
