package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.repositories.ClaseRepository;
import pe.edu.upc.iedunet.services.ClaseService;

@Named
@ApplicationScoped
public class ClaseServiceImpl implements ClaseService, Serializable  {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClaseRepository claseRepository;
	
	@Transactional
	@Override
	public Clase save(Clase entity) throws Exception {
		return claseRepository.save(entity);
	}

	@Transactional
	@Override
	public Clase update(Clase entity) throws Exception {
		return claseRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		claseRepository.deleteById(id);
	}

	@Override
	public Optional<Clase> findById(Integer id) throws Exception {
		return claseRepository.findById(id);
	}

	@Override
	public List<Clase> findAll() throws Exception {
		return claseRepository.findAll();
	}

}
