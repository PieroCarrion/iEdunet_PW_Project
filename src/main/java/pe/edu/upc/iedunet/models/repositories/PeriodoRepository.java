package pe.edu.upc.iedunet.models.repositories;

import java.util.List;

import pe.edu.upc.iedunet.models.entities.Periodo;

public interface PeriodoRepository extends JpaRepository<Periodo, Integer>{
	List<Periodo> findByColegio(int id) throws Exception;
}
