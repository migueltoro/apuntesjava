package us.lsi.pd.afinidad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import us.lsi.common.Sets2;
import us.lsi.stream.Stream2;

public class ProblemaAfinidad {
	
	public static List<String> trabajadores;
	public static List<Cliente> clientes;	
	
	private ProblemaAfinidad() {
			
		}	
	/**
	 * Define un problemaAfinidad de ejemplo con 8 clientes y 3 trabajadores
	 * @return Un problema de ejemplo
	 */
	public static ProblemaAfinidad createEjemplo(){
		clientes= Lists.newArrayList(
				Cliente.create("Juan", 10, Sets2.newSet("Amparo", "Rosa")),
				Cliente.create("Maria", 10, Sets2.newSet("Rosa")),
				Cliente.create("Sara", 11, Sets2.newSet("Amparo", "Rosa")),
				Cliente.create("Andres", 11, Sets2.newSet("Marco", "Rosa")),
				Cliente.create("Antonio", 11,Sets2.newSet("Marco")),
				Cliente.create("Sonia", 12, Sets2.newSet("Marco")),
				Cliente.create("Marta", 12, Sets2.newSet("Marco")),
				Cliente.create("Ivan", 12, Sets2.newSet("Amparo"))			
				);
		Set<String> trab= new HashSet<>();	
		clientes.stream().forEach(x-> trab.addAll(x.nombresDeTrabajadoresAfines));
		trabajadores = new ArrayList<>(trab);//Elimina repetidos
		clientes.stream().forEach(x->x.calculaTrabajadoresAfines());
		return  new ProblemaAfinidad();
	}
	
	/**
	 * Define un ProblemaAfinidad cogiendo los datos de un fichero de texto.
	 * 
	 * Cada línea del fichero de texto tendrá la estructura:
	 * nombreCliente, franjaHoraria, trabajadororesAfines
	 * 
	 * Por su lado, trabajadoresAfines serán los nombres de los trabajadores separados por ;
	 * 
	 * @param file El archivo para leer los datos
	 * @return El problema creado
	 */
	public static ProblemaAfinidad create(String file){
		Set<String> trab= new HashSet<>();			
		clientes=new ArrayList<>();
		
		Stream2.fromFile(file)
			.map(x-> x.replace(" ","")) //quitar espacios en blanco
			.peek(x -> clientes.add(Cliente.create(x))) //crear clientes
			.forEach(x-> trab.addAll(Arrays.asList(x.split(",")[2].split(";")))); //crear trabajadores
			
		trabajadores = new ArrayList<>(trab);//Elimina repetidos		
		clientes.stream().forEach(x->x.calculaTrabajadoresAfines());
		return new ProblemaAfinidad();
	}
}

