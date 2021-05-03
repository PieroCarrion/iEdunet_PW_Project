package pe.edu.upc.iedunet.models.repositories.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pe.edu.upc.iedunet.models.entities.Curso;
import pe.edu.upc.iedunet.models.entities.Usuario;
import pe.edu.upc.iedunet.models.repositories.UsuarioRepository;
@Named
@ApplicationScoped
public class UsuarioRepositoryImpl implements UsuarioRepository, Serializable {

	
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	@Override
	public Usuario save(Usuario entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Usuario update(Usuario entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Usuario> optional = findById(id);

		if( optional.isPresent() ) {
			System.out.println("eliminando usuario " + optional.get().getId() + " en repository");
			em.remove( optional.get() );
		}		

	}

	@Override
	public Optional<Usuario> findById(Integer id) throws Exception {
		Optional<Usuario> optional = Optional.empty();
		
		String qlString = "SELECT c FROM Usuario c WHERE c.id = ?1";
			
		TypedQuery<Usuario> query = em.createQuery(qlString, Usuario.class);
			
		query.setParameter(1, id);
			
		Usuario usuario = query.getSingleResult();
				
		if(usuario != null) {
					
					optional = Optional.of(usuario);
				}		
		return optional;
	}

	@Override
	public List<Usuario> findAll() throws Exception {
List<Usuario> usuarios = new ArrayList<>();
		
		String qlString = "SELECT c FROM Usuario c";
	
		TypedQuery<Usuario> query = em.createQuery(qlString, Usuario.class);
		
		usuarios = query.getResultList();
		return usuarios;
	}

}
