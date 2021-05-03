package pe.edu.upc.iedunet.services;

import java.util.List;

import pe.edu.upc.iedunet.models.entities.Periodo;

public interface PeriodoService extends CrudService<Periodo, Integer> {
	List<Periodo> findByColegio(int id) throws Exception;
}
