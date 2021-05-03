package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.iedunet.models.entities.Recurso;
import pe.edu.upc.iedunet.models.repositories.RecursoRepository;
import pe.edu.upc.iedunet.services.RecursosService;

@Named
@ApplicationScoped
public class RecursosServiceImpl implements RecursosService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private RecursoRepository recursoRepository;
	
	@Transactional
	@Override
	public Recurso save(Recurso entity) throws Exception {
		return recursoRepository.save(entity);
	}

	@Transactional
	@Override
	public Recurso update(Recurso entity) throws Exception {
		return recursoRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		recursoRepository.deleteById(id);
	}

	@Override
	public Optional<Recurso> findById(Integer id) throws Exception {
		return recursoRepository.findById(id);
	}

	@Override
	public List<Recurso> findAll() throws Exception {
		return recursoRepository.findAll();
	}

}
