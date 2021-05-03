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
import pe.edu.upc.iedunet.models.entities.Periodo;
import pe.edu.upc.iedunet.models.entities.Profesor;
import pe.edu.upc.iedunet.models.repositories.ProfesorRepository;
@Named
@ApplicationScoped
public class ProfesorRepositoryImpl implements ProfesorRepository,Serializable {

	
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	@Override
	public Profesor save(Profesor entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Profesor update(Profesor entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Profesor> optional = findById(id);

		if( optional.isPresent() ) {
			em.remove( optional.get() );
		}		
	}

	@Override
	public Optional<Profesor> findById(Integer id) throws Exception {
		Optional<Profesor> optional = Optional.empty();
		
		String qlString = "SELECT c FROM Profesor c WHERE c.id = ?1";
			
		TypedQuery<Profesor> query = em.createQuery(qlString, Profesor.class);
			
		query.setParameter(1, id);
			
		Profesor profesor = query.getSingleResult();
				
		if(profesor != null) {
					
					optional = Optional.of(profesor);
				}		
		return optional;
	}

	@Override
	public List<Profesor> findAll() throws Exception {
List<Profesor> profesores = new ArrayList<>();
		
		String qlString = "SELECT c FROM Profesor c";
	
		TypedQuery<Profesor> query = em.createQuery(qlString, Profesor.class);
		
		profesores = query.getResultList();
		return profesores;
	}
	
	@Override
	public List<Profesor> findByColegio(int id) throws Exception {
		List<Profesor> profesores = new ArrayList<>();

		String qlString = "SELECT c FROM Profesor c where c.usuario.colegio.id = ?1";

		TypedQuery<Profesor> query = em.createQuery(qlString, Profesor.class);
		
		query.setParameter(1, id);

		profesores = query.getResultList();
		return profesores;
	}

}
