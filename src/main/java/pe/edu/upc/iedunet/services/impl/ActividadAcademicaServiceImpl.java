package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.iedunet.models.entities.ActividadAcademica;
import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.repositories.ActividadAcademicaRepository;
import pe.edu.upc.iedunet.models.repositories.AlumnoRepository;
import pe.edu.upc.iedunet.services.ActividadAcademicaService;

@Named
@ApplicationScoped
public class ActividadAcademicaServiceImpl implements ActividadAcademicaService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ActividadAcademicaRepository actividadAcademicaRepository;
	
	@Transactional
	@Override
	public ActividadAcademica save(ActividadAcademica entity) throws Exception {
		return actividadAcademicaRepository.save(entity);
	}

	@Transactional
	@Override
	public ActividadAcademica update(ActividadAcademica entity) throws Exception {
		return actividadAcademicaRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		actividadAcademicaRepository.deleteById(id);
	}

	@Override
	public Optional<ActividadAcademica> findById(Integer id) throws Exception {
		return actividadAcademicaRepository.findById(id);
	}

	@Override
	public List<ActividadAcademica> findAll() throws Exception {
		return actividadAcademicaRepository.findAll();
	}

}
