package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.iedunet.models.entities.Periodo;
import pe.edu.upc.iedunet.models.entities.Profesor;
import pe.edu.upc.iedunet.models.repositories.ProfesorRepository;
import pe.edu.upc.iedunet.models.repositories.UsuarioRepository;

import pe.edu.upc.iedunet.services.ProfesorService;

@Named
@ApplicationScoped
public class ProfesorServiceImpl implements ProfesorService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProfesorRepository profesorRepository;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	@Override
	public Profesor save(Profesor entity) throws Exception {
		usuarioRepository.save(entity.getUsuario());
		return profesorRepository.save(entity);
		
	}

	@Transactional
	@Override
	public Profesor update(Profesor entity) throws Exception {
		usuarioRepository.update(entity.getUsuario());
		System.out.println("Update" + entity.getId());
		return profesorRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		int x = findById(id).get().getUsuario().getId();
		profesorRepository.deleteById(id);
		usuarioRepository.deleteById(x);
		System.out.println("ID a Eliminar en Tabla Profesor: " + id + "---" + "ID a Eliminar en Tabla Usuario: " + x);
	}

	@Override
	public Optional<Profesor> findById(Integer id) throws Exception {
		return profesorRepository.findById(id);
	}

	@Override
	public List<Profesor> findAll() throws Exception {
		return profesorRepository.findAll();
	}
	
	@Override
	public List<Profesor> findByColegio(int id) throws Exception {
		return profesorRepository.findByColegio(id);
	}


}
