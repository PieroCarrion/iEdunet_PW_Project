package pe.edu.upc.iedunet.models.repositories;

import java.util.Optional;

import pe.edu.upc.iedunet.models.entities.Colegio;

public interface ColegioRepository extends JpaRepository<Colegio, Integer>{
	Optional<Colegio> findByCodigo(String codigo) throws Exception;
}
