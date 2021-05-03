package pe.edu.upc.iedunet.services;

import java.util.List;

import pe.edu.upc.iedunet.models.entities.Periodo;
import pe.edu.upc.iedunet.models.entities.Profesor;

public interface ProfesorService extends CrudService<Profesor, Integer>{
	List<Profesor> findByColegio(int id) throws Exception;
}
