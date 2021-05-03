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


import pe.edu.upc.iedunet.models.entities.Matricula;
import pe.edu.upc.iedunet.models.repositories.MatriculaRepository;
@Named
@ApplicationScoped
public class MatriculaRepositoryImpl implements MatriculaRepository,Serializable {

	
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	@Override
	public Matricula save(Matricula entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Matricula update(Matricula entity) throws Exception {
		em.merge(entity);
		return entity;
	}


	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Matricula> optional = findById(id);

		if( optional.isPresent() ) {
			em.remove( optional.get() );
		}		

	}

	@Override
	public Optional<Matricula> findById(Integer id) throws Exception {
		Optional<Matricula> optional = Optional.empty();
		
		String qlString = "SELECT c FROM Matricula c WHERE c.id = ?1";
			
		TypedQuery<Matricula> query = em.createQuery(qlString, Matricula.class);
			
		query.setParameter(1, id);
			
		Matricula matricula = query.getSingleResult();
				
		if(matricula != null) {
					
					optional = Optional.of(matricula);
				}		
		return optional;
	}

	@Override
	public List<Matricula> findAll() throws Exception {
List<Matricula> matriculas = new ArrayList<>();
		
		String qlString = "SELECT c FROM Matricula c";
	
		TypedQuery<Matricula> query = em.createQuery(qlString, Matricula.class);
		
		matriculas = query.getResultList();
		return matriculas;
	}

}
