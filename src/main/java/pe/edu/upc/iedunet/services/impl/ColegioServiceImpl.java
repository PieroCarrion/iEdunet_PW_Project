package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.Colegio;
import pe.edu.upc.iedunet.models.repositories.AlumnoRepository;
import pe.edu.upc.iedunet.models.repositories.ColegioRepository;
import pe.edu.upc.iedunet.services.ColegioService;

@Named
@ApplicationScoped
public class ColegioServiceImpl implements ColegioService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ColegioRepository colegioRepository;
	
	@Transactional
	@Override
	public Colegio save(Colegio entity) throws Exception {
		return colegioRepository.save(entity);
	}

	@Transactional
	@Override
	public Colegio update(Colegio entity) throws Exception {
		return colegioRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		colegioRepository.deleteById(id);
	}

	@Override
	public Optional<Colegio> findById(Integer id) throws Exception {
		return colegioRepository.findById(id);
	}

	@Override
	public List<Colegio> findAll() throws Exception {
		return colegioRepository.findAll();
	}
	@Override
	public Optional<Colegio> findByCodigo(String codigo) throws Exception{
		return colegioRepository.findByCodigo(codigo);
	}

}
