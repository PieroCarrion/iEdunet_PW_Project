package pe.edu.upc.iedunet.models.repositories;

import java.util.List;

import pe.edu.upc.iedunet.models.entities.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer>{
	List<Alumno> findByColegio(int id) throws Exception;
}
