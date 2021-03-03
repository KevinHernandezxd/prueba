import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Diccionario {

	// Declaramos nuestro hash y el BufferedReader que necesitaremos para todo
	// el Diccionario.

	private HashMap<String, Palabra> hash = new HashMap<String, Palabra>();
	private static BufferedReader stdin = new BufferedReader(
			new InputStreamReader(System.in));

	// Metodo para iniciar el diccionario.
	public void inicio() {

		// Aqui iniciamos las variables, que vamos a usar en este metodo.
		FileReader fr = null;
		BufferedReader br = null;
		String[] nombre, definicion;
		String linea;

		try {

			// Abrimos el fichero y creamos el bufferReader.
			fr = new FileReader("diccionario.txt");
			br = new BufferedReader(fr);

			// Lectura del fichero linea a linea.
			while ((linea = br.readLine()) != null) {

				// ArrayList para las definiciones.
				ArrayList<String> def = new ArrayList<String>();

				nombre = linea.split("## ; ");
				definicion = nombre[1].split(" ; ");
				for (String z : definicion) {
					def.add(z);
				}
				this.hash.put(nombre[0].toLowerCase(), new Palabra(nombre[0],
						def));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Finalmente cerramos el fichero pase lo que pase.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	// Metodo para introducir palabras.
	public void introducir() throws IOException {

		// Iniciamos las variables a usar en este metodo:

		ArrayList<String> def = new ArrayList<String>();
		String nombre = "";
		String aux = "";
		Palabra p = new Palabra();
		println("");
		println("Introduce el nombre de la palabra que quieres añadir:");
		print("> ");

		nombre = stdin.readLine().toLowerCase();
		// if para controlar que no entran palabras que ya existen.
		if ((hash.get(nombre)) != null) {

			println("");
			println("ERROR:");
			println("Esta palabra ya existe. Usa las distintas opciones de ");
			println("edicion para eliminar la palabra o añadir/eliminar ");
			println("definiciones deseadas.");
			println("");

			// Si no existe, empieza el metodo.
		} else {
			// If para que el usuario no introduzca palabras vacias.
			if (nombre.length() > 1) {

				// Bucle para poder añadir todas las definiciones que queramos.
				while (true) {

					println("Añade definicion:");
					print("> ");

					try {
						def.add(stdin.readLine());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// Bucles dentro de bucles inception style!!!
					while (true) {
						println("");
						println("¿Quieres añadir mas definiciones? s/n");
						print("> ");
						try {
							aux = stdin.readLine().toLowerCase();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						;
						if (aux.equals("s")) {
							break; // Bucle linea 120
						} else if (aux.equals("n")) {
							p.setDefinicion(def);
							p.setPalabra(nombre);

							hash.put(nombre, p);
							break; // Bucle linea 120
						}

						else {

							// Nunca esta de mas interactuar con el usuario.
							println("");
							println("ERROR:");
							println("\nResponde s/n, usuario patoso .\n");
							println("");
							continue; // Bucle linea 120
						}
					}
					if (aux.equals("s")) {
						continue;
					}
					println("");
					println("Tu palabra ha sido guardada en el diccionario.");
					println("");
					break; // Bucle linea 104
				}

				// Else del if del principio del bucle, nos sirve para que el
				// usuario no meta palabras en blanco.

			} else {
				println("\nERROR: \nIntroduce una palabra por favor. \n");

			}

		}
	}

	// Metodo para eliminar una palabra de nuestro diccionario.
	public void eliminar() {
		// Variables + pedir por teclado.
		String eliminar = "";
		String confirmacion = "";
		println("");
		println("¿Que palabra quieres eliminar?");
		print("> ");
		try {
			eliminar = stdin.readLine().toLowerCase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Control por si la palabra a eliminar no existe.
		if ((hash.get(eliminar)) == null) {
			println("");
			println("ERROR:");
			println("Esta palabra no esta en el diccionario.");
			println("");
		} else {

			// Bucle por si no introduce bien S/N que no nos devuelva al menu
			// principal.
			while (true) {
				println("¿Estas seguro que quieres eliminar <" + eliminar
						+ "> del diccionario? (s/n)");
				print("> ");
				try {
					confirmacion = stdin.readLine().toLowerCase();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (confirmacion.equals("s")) {
					// Forma para eliminar palabras del hash.
					Object obj = hash.remove(eliminar);
					System.out.print(obj + "\nHa sido eliminado.\n\n");
					println("");
					break;
				} else if (confirmacion.equals("n")) {

				} else {
					println("");
					println("ERROR:");
					println("Elige s/n, usuario patoso.\n");
					continue;
				}
			}
		}
	}

	// Metodo para añadir todas las definiciones deseadas a las palabra que
	// eligamos.
	public void añadirDefinicion() throws IOException {
		// Variables y array.
		String w;
		ArrayList<String> f = new ArrayList<String>();
		String aux = null;
		println("");
		println("¿Que palabra quieres modificar?");
		print(">");

		w = stdin.readLine().toLowerCase();
		// Control por si no esta en el diccionario.
		if ((hash.get(w)) == null) {
			println("");
			println("ERROR:");
			println("Esta palabra no esta en el diccionario. \n");
			println("");
		} else {
			// Metemos todas las definiciones en un nuevo array haciendo uso de
			// un for each.
			for (String d : hash.get(w).getDefinicion()) {
				f.add(d);
			}
			while (true) {
				println("");
				println("Definicion a añadir:");
				print("> ");

				f.add(stdin.readLine().toLowerCase());

				while (true) {
					println("");
					println("¿Quieres añadir mas definiciones? s/n");
					print("> ");

					aux = stdin.readLine().toLowerCase();

					if (aux.equals("s")) {
						break;
					} else if (aux.equals("n")) {
						// Eliminamos la palabra y la volvemos a generar e
						// introducir al hash.
						hash.remove(w);
						hash.put(w, new Palabra(w, f));
						println("");
						println("Definicion añadida correctamente a la palabra "
								+ w + ".");
						println("");
						break;
					}

					else {

						// Nunca esta de mas interactuar con el usuario.
						println("");
						println("ERROR:");
						println("Responde s/n, usuario patoso .\n");
						continue;
					}
				}
				if (aux.equals("s")) {
					continue;
				}
				break; // bucle linea 253
			}

		}
	}

	// Metodo para eliminar definiciones.
	public void eliminarDefinicion() throws IOException {
		// Variables
		String w = null;
		int i = 0;
		int i2 = 1;
		println("¿De que palabra quieres ver las definiciones?");
		print("> ");

		w = stdin.readLine().toLowerCase();
		// Control por si la palabra no existe.
		if ((hash.get(w)) == null) {
			println("");
			println("ERROR:");
			println("Esta palabra no esta en el diccionario.\n");
		} else {

			int aux = 0;
			ArrayList<String> def = new ArrayList<String>();
			// Metemos todas las definiciones en un nuevo arrayList.
			for (String d : hash.get(w).getDefinicion()) {
				print("(" + i2 + ") " + d + "\n");
				def.add(d);
				i++;
				i2++;
			}
			// Bucle para hacer uso de la confirmacion y que introduzca un
			// numero dentro del rango correcto.
			while (true) {
				println("¿Que numero de definicion quieres borrar?");
				print("> ");

				int d = 0;
				try {
					d = Integer.parseInt(stdin.readLine());
					// Preparo variables para proximas operaciones.
					aux = d;
					d = d - 1;
					String confirmacion = null;
					if ((d <= i) && (d > 0)) {
						while (confirmacion != "n") {
							println("¿Estas seguro que deseas eliminar la definicion Nº"
									+ aux + "? s/n");
							print("> ");
							confirmacion = stdin.readLine().toLowerCase();
							if (confirmacion.equals("s")) {

								// Para borra la definicion uso un
								// arraylist.remove(posicion deseada) y vuelvo a
								// generar la palabra.

								def.remove(d);
								hash.remove(w);
								hash.put(w, new Palabra(w, def));
								println("La definicion Nº" + d + 1
										+ " ha sido eliminada.");
								print("");
								break; // bucle linea 300
							} else if (confirmacion.equals("n")) {
								println("La definicion NO ha sido borrada.");
								break; // bucle linea 300
							} else {
								println("");
								println("ERROR:");
								println("Elige s/n, usuario patoso.");
								println("");
								continue; // bucle linea 300

							}
						}
						break; // bucle linea 289
					} else {
						print("Elige entre 1 y " + i + ".");
						continue;
					}
				} catch (java.lang.NumberFormatException e) {
					println("Introduce un numero entre 1 y " + i + ".");
					continue;
				}
			}

		}
	}

	// Metodo para buscar las palabras que ya han sido insertadas en el
	// diccionario.
	public void buscar() throws IOException {

		// Variables a usar en este metodo
		String buscar = "";
		String aux = "";

		println("¿Que palabra quieres buscar?");
		print("> ");

		try {
			buscar = stdin.readLine().toLowerCase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Control por si la palabra no existe y si no la opcion a añadirla.
		if ((hash.get(buscar)) == null) {
			println("No existe esta palabra. ¿Quieres añadirla? s/n");
			print("> ");
			try {
				aux = stdin.readLine().toLowerCase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (aux.equals("s")) {

				// Si el usuario quiere introducir la palabra que no existe
				// llamo al metodo <introducir> que esta dentro de esta clase
				// para introducirla.

				introducir();
			}
			if (aux.equals("n")) {

				// Vacio para que nos devuelva al menu principal.

			} else {
				println("");
				println("ERROR:");
				println("\nResponde s/n, usuario patoso.\n");
				println("");
			}

		} else {
			// Si existe la mostrara
			System.out.println(hash.get(buscar));
		}
	}

	// Metodo que ara uso de un iterator para recorrer todo el hashmap y nos
	// mostrara toda las palabras del diccionario.
	public void mostrarTodas() {

		java.util.Iterator<Entry<String, Palabra>> it = hash.entrySet()
				.iterator();
		while (it.hasNext()) {

			@SuppressWarnings("rawtypes")
			Map.Entry e = (Map.Entry) it.next();
			System.out.println(e.getValue());

		}

	}

	// Metodo para guardar nuestro diccionario.
	public void guardar() {

		// Si el hash no esta vacio, se iniciara.
		if (!hash.isEmpty()) {
			FileWriter fw = null;
			PrintWriter pw = null;
			Palabra p = new Palabra();
			ArrayList<String> array = new ArrayList<String>();
			try {

				fw = new FileWriter("diccionario.txt", false);
				pw = new PrintWriter(fw);

				String aux2 = "";
				String aux3 = "";

				for (String key : hash.keySet()) {
					p = hash.get(key);
					array = p.getDefinicion();
					for (String s : array) {
						aux2 = aux2 + " ; " + s;
					}
					aux3 = p.getPalabra() + "##";
					pw.println(aux3 + aux2);
					aux2 = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					// Cerramos el archivo tanto si ha funcionado como
					// si no.
					if (null != fw)
						fw.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	// Metodo para salir del programa.
	public void salir() throws IOException {
		while (true) {
			String confirmacion = null;
			println("");
			println("¿Estas seguro que quieres salir? s/n");
			print("> ");
			confirmacion = stdin.readLine().toLowerCase();

			if (confirmacion.equals("s")) {
				println("Gracias por usar mi programa.");
				System.exit(0);
			} else if (confirmacion.equals("n")) {
				break;
			} else {
				println("");
				println("ERROR:");
				println("Introduce s/n, usuario patoso");
				println("");
			}
		}
	}

	// Metodos privados para agilizar System.out.print y System.out.println
	private void print(String str) {
		System.out.print(str);
	}

	private void println(String str) {
		System.out.println(str);
	}

}