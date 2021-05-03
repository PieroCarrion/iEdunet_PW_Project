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
import pe.edu.upc.iedunet.models.entities.Recurso;
import pe.edu.upc.iedunet.models.repositories.RecursoRepository;
@Named
@ApplicationScoped
public class RecursoRepositoryImpl implements RecursoRepository,Serializable {


	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	@Override
	public Recurso save(Recurso entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Recurso update(Recurso entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Recurso> optional = findById(id);

		if( optional.isPresent() ) {
			em.remove( optional.get() );
		}
	}

	@Override
	public Optional<Recurso> findById(Integer id) throws Exception {
		Optional<Recurso> optional = Optional.empty();
		
		String qlString = "SELECT c FROM Recurso c WHERE c.id = ?1";
			
		TypedQuery<Recurso> query = em.createQuery(qlString, Recurso.class);
			
		query.setParameter(1, id);
			
		Recurso recurso = query.getSingleResult();
				
		if(recurso != null) {
					
					optional = Optional.of(recurso);
				}		
		return optional;
	}

	@Override
	public List<Recurso> findAll() throws Exception {
List<Recurso> recursos = new ArrayList<>();
		
		String qlString = "SELECT c FROM Recurso c";
	
		TypedQuery<Recurso> query = em.createQuery(qlString, Recurso.class);
		
		recursos = query.getResultList();
		return recursos;
	}

}
