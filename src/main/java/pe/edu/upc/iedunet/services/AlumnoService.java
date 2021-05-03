package pe.edu.upc.iedunet.services;

import java.util.List;

import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.Periodo;

public interface AlumnoService extends CrudService<Alumno, Integer> {
	List<Alumno> findByColegio(int id) throws Exception;
}
