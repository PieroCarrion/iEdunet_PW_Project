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
import pe.edu.upc.iedunet.models.repositories.CursoRepository;

@Named
@ApplicationScoped
public class CursoRepositoryImpl implements CursoRepository, Serializable {

	
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	@Override
	public Curso save(Curso entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Curso update(Curso entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Curso> optional = findById(id);

		if( optional.isPresent() ) {
			em.remove( optional.get() );
		}		

	}

	@Override
	public Optional<Curso> findById(Integer id) throws Exception {
		
		Optional<Curso> optional = Optional.empty();
				
		String qlString = "SELECT c FROM Curso c WHERE c.id = ?1";
			
		TypedQuery<Curso> query = em.createQuery(qlString, Curso.class);
			
		query.setParameter(1, id);
			
		Curso curso = query.getSingleResult();
				
		if(curso != null) {
					
					optional = Optional.of(curso);
				}		
		return optional;
	}

	@Override
	public List<Curso> findAll() throws Exception {
		
		List<Curso> cursos = new ArrayList<>();
		
		String qlString = "SELECT c FROM Curso c";
	
		TypedQuery<Curso> query = em.createQuery(qlString, Curso.class);
		
		cursos = query.getResultList();
		return cursos;
	}

}
