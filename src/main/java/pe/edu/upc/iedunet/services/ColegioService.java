package pe.edu.upc.iedunet.services;

import java.util.Optional;

import pe.edu.upc.iedunet.models.entities.Colegio;

public interface ColegioService extends CrudService<Colegio, Integer> {
	Optional<Colegio> findByCodigo(String codigo) throws Exception;
}
