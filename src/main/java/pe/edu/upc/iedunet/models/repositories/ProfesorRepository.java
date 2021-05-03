package pe.edu.upc.iedunet.models.repositories;

import java.util.List;

import pe.edu.upc.iedunet.models.entities.Profesor;

public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {
	List<Profesor> findByColegio(int id) throws Exception;
}
