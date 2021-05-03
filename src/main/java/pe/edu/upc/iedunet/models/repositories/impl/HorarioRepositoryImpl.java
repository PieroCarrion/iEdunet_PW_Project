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


import pe.edu.upc.iedunet.models.entities.Horario;
import pe.edu.upc.iedunet.models.repositories.HorarioRepository;
@Named
@ApplicationScoped
public class HorarioRepositoryImpl implements HorarioRepository, Serializable {


	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	@Override
	public Horario save(Horario entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Horario update(Horario entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Horario> optional = findById(id);

		if( optional.isPresent() ) {
			em.remove( optional.get() );
		}		

	}

	@Override
	public Optional<Horario> findById(Integer id) throws Exception {
		Optional<Horario> optional = Optional.empty();
		
		String qlString = "SELECT c FROM Horario c WHERE c.id = ?1";
			
		TypedQuery<Horario> query = em.createQuery(qlString, Horario.class);
			
		query.setParameter(1, id);
			
		Horario horario = query.getSingleResult();
				
		if(horario != null) {
					
					optional = Optional.of(horario);
				}		
		return optional;
	}


	@Override
	public List<Horario> findAll() throws Exception {
	List<Horario> horarios = new ArrayList<>();
		
		String qlString = "SELECT c FROM Horario c";
	
		TypedQuery<Horario> query = em.createQuery(qlString, Horario.class);
		
		horarios = query.getResultList();
		return horarios;
	}

}
