package pe.edu.upc.iedunet.utils;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;

public class GradosAcademicos {
	public List<String> listaGrados = new ArrayList<String>();

	public GradosAcademicos() {
		super();
		
		listaGrados.add("1ro de Primaria");
		listaGrados.add("2do de Primaria");
		listaGrados.add("3ro de Primaria");
		listaGrados.add("4to de Primaria");
		listaGrados.add("5to de Primaria");
		listaGrados.add("6to de Primaria");
		
		//SECUNDARIA
		listaGrados.add("1ro de Secundaria");
		listaGrados.add("2do de Secundaria");
		listaGrados.add("3ro de Secundaria");
		listaGrados.add("4to de Secundaria");
		listaGrados.add("5to de Secundaria");
	}
}
