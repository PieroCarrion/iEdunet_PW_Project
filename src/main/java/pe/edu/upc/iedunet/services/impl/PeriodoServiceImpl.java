package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;


import pe.edu.upc.iedunet.models.entities.Periodo;
import pe.edu.upc.iedunet.models.repositories.PeriodoRepository;
import pe.edu.upc.iedunet.services.PeriodoService;

@Named
@ApplicationScoped
public class PeriodoServiceImpl implements PeriodoService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PeriodoRepository periodoRepository;
	
	@Transactional
	@Override
	public Periodo save(Periodo entity) throws Exception {
		return periodoRepository.save(entity);
	}

	@Transactional
	@Override
	public Periodo update(Periodo entity) throws Exception {
		return periodoRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		periodoRepository.deleteById(id);
	}

	@Override
	public Optional<Periodo> findById(Integer id) throws Exception {
		return periodoRepository.findById(id);
	}

	@Override
	public List<Periodo> findAll() throws Exception {
		return periodoRepository.findAll();
	}
	
	@Override
	public List<Periodo> findByColegio(int id) throws Exception {
		return periodoRepository.findByColegio(id);
	}


}
