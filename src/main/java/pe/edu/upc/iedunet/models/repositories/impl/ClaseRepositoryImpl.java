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


import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.repositories.ClaseRepository;

@Named
@ApplicationScoped
class ClaseRepositoryImpl implements ClaseRepository, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	
	@Override
	public Clase save(Clase entity) throws Exception {
		em.persist(entity);

		return entity;
	}

	@Override
	public Clase update(Clase entity) throws Exception {
		em.merge(entity);

		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Clase> optional = findById(id);
		
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<Clase> findById(Integer id) throws Exception {
		// Declara la variable a retornar
		Optional<Clase> optional = Optional.empty();

		//Elaborar el JPQL
		String qlString = "SELECT c FROM Clase c WHERE c.id = ?1";
		
		//Crear la consulta
		TypedQuery<Clase> query = em.createQuery(qlString,Clase.class);
		
		//Estableciendo parametros: id
		query.setParameter(1, id);
		
		//Obtener el resultado de la consulta
		Clase clase = query.getSingleResult();
		
		if(clase != null) {
			//Agregando el objeto cliente al Optional
			optional = Optional.of(clase);
		}
		
		return optional;
	}

	@Override
	public List<Clase> findAll() throws Exception {
		List<Clase> clases = new ArrayList<>();
		
		String qlString = "SELECT a FROM Clase a";
		
		TypedQuery<Clase> query = em.createQuery(qlString,Clase.class);
		
		clases = query.getResultList();
		
		return clases;
	}

}
