package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.repositories.AlumnoRepository;
import pe.edu.upc.iedunet.models.repositories.UsuarioRepository;
import pe.edu.upc.iedunet.services.AlumnoService;

@Named
@ApplicationScoped
public class AlumnoServiceImpl implements AlumnoService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AlumnoRepository alumnoRepository;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	@Override
	public Alumno save(Alumno entity) throws Exception {
		usuarioRepository.save(entity.getUsuario());
		return alumnoRepository.save(entity);
	}

	@Transactional
	@Override
	public Alumno update(Alumno entity) throws Exception {
		usuarioRepository.update(entity.getUsuario());
		return alumnoRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		int x = findById(id).get().getUsuario().getId();
		alumnoRepository.deleteById(id);
		usuarioRepository.deleteById(x);
		System.out.println("ID a Eliminar en Tabla Alumno: " + id + "---" + "ID a Eliminar en Tabla Usuario: " + x);
	}

	@Override
	public Optional<Alumno> findById(Integer id) throws Exception {
		return alumnoRepository.findById(id);
	}

	@Override
	public List<Alumno> findAll() throws Exception {
		return alumnoRepository.findAll();
	}

	@Override
	public List<Alumno> findByColegio(int id) throws Exception {
		// TODO Auto-generated method stub
		return alumnoRepository.findByColegio(id);
	}

}
