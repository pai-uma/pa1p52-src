package indices;

import java.io.PrintWriter;

/**
* Interfaz que define las operaciones comunes a diferentes tipos de índices
* que permiten palabras con enteros, representando diferentes tipos de información
* (veces que aparecen en un texto, líneas en las que aparecen, etc.)
*/
public interface Indice {
	/**
	 * Método para añadir las lineas sobre las que se construirá el índice.
	 * 
	 * @param linea	Cadena de caracteres con una linea
	 */
    void agregarFrase(String linea);

	/**
	 * Genera el índice correspondiente a partir de todas las líneas que se tengan.
	 * @param delim Separadores que determinan las palabras en cada línea
	 */
    void resolver(String delim);
    
	/**
	 * Presenta el índice sobre el PrintWriter que se pasa como argumento.
	 * @param pw	PrintWriter
	 */
    void presentarIndice(PrintWriter pw);

	/**
	 * Presenta el índice sobre la consola. 
	 */
	default void presentarIndiceConsola() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(System.out, true);
			presentarIndice(pw);
		} finally {
			if (pw != null) {
				pw.flush(); // flush fuerza el volcado de los datos a consola
			}
		}		
	}
}
