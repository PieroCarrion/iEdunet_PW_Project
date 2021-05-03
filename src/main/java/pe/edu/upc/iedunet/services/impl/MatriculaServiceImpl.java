package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pe.edu.upc.iedunet.models.entities.Matricula;
import pe.edu.upc.iedunet.models.repositories.MatriculaRepository;
import pe.edu.upc.iedunet.services.MatriculaService;

@Named
@ApplicationScoped
public class MatriculaServiceImpl implements MatriculaService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MatriculaRepository matriculaRepository;
	
	@Override
	public Matricula save(Matricula entity) throws Exception {
		// TODO Auto-generated method stub
		return matriculaRepository.save(entity);
	}

	@Override
	public Matricula update(Matricula entity) throws Exception {
		// TODO Auto-generated method stub
		return matriculaRepository.update(entity);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		matriculaRepository.deleteById(id);
	}

	@Override
	public Optional<Matricula> findById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return matriculaRepository.findById(id);
	}

	@Override
	public List<Matricula> findAll() throws Exception {
		// TODO Auto-generated method stub
		return matriculaRepository.findAll();
	}

}
