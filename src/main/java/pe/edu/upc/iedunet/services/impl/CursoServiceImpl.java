package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.iedunet.models.entities.Curso;
import pe.edu.upc.iedunet.models.repositories.CursoRepository;
import pe.edu.upc.iedunet.services.CursoService;

@Named
@ApplicationScoped
public class CursoServiceImpl implements CursoService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CursoRepository cursoRepository;
	
	@Transactional
	@Override
	public Curso save(Curso entity) throws Exception {
		return cursoRepository.save(entity);
	}

	@Transactional
	@Override
	public Curso update(Curso entity) throws Exception {
		return cursoRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		cursoRepository.deleteById(id);
	}

	@Override
	public Optional<Curso> findById(Integer id) throws Exception {
		return cursoRepository.findById(id);
	}

	@Override
	public List<Curso> findAll() throws Exception {
		return cursoRepository.findAll();
	}


}
