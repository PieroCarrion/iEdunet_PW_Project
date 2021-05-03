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

import pe.edu.upc.iedunet.models.entities.Calificacion;
import pe.edu.upc.iedunet.models.repositories.CalificacionRepository;

@Named
@ApplicationScoped
public class CalificacionRepositoryImpl implements CalificacionRepository, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	
	@Override
	public Calificacion save(Calificacion entity) throws Exception {
		em.persist(entity);

		return entity;
	}

	@Override
	public Calificacion update(Calificacion entity) throws Exception {
		em.merge(entity);

		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Calificacion> optional = findById(id);
		
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<Calificacion> findById(Integer id) throws Exception {
		// Declara la variable a retornar
		Optional<Calificacion> optional = Optional.empty();

		//Elaborar el JPQL
		String qlString = "SELECT c FROM Calificacion c WHERE c.id = ?1";
		
		//Crear la consulta
		TypedQuery<Calificacion> query = em.createQuery(qlString,Calificacion.class);
		
		//Estableciendo parametros: id
		query.setParameter(1, id);
		
		//Obtener el resultado de la consulta
		Calificacion calificacion = query.getSingleResult();
		
		if(calificacion != null) {
			//Agregando el objeto cliente al Optional
			optional = Optional.of(calificacion);
		}
		
		return optional;
	}

	@Override
	public List<Calificacion> findAll() throws Exception {
		List<Calificacion> calificaciones = new ArrayList<>();
		
		String qlString = "SELECT c FROM Calificacion c";
		
		TypedQuery<Calificacion> query = em.createQuery(qlString,Calificacion.class);
		
		calificaciones = query.getResultList();
		
		return calificaciones;
	}

}
