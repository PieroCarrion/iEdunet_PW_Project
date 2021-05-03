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

import pe.edu.upc.iedunet.models.entities.AlumnoClase;
import pe.edu.upc.iedunet.models.repositories.AlumnoClaseRepository;

@Named
@ApplicationScoped
class AlumnoClaseRepositoryImpl implements AlumnoClaseRepository, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	
	@Override
	public AlumnoClase save(AlumnoClase entity) throws Exception {
		em.persist(entity);

		return entity;
	}

	@Override
	public AlumnoClase update(AlumnoClase entity) throws Exception {
		em.merge(entity);

		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<AlumnoClase> optional = findById(id);
		
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<AlumnoClase> findById(Integer id) throws Exception {
		// Declara la variable a retornar
		Optional<AlumnoClase> optional = Optional.empty();

		//Elaborar el JPQL
		String qlString = "SELECT a FROM AlumnoClase a WHERE a.id = ?1";
		
		//Crear la consulta
		TypedQuery<AlumnoClase> query = em.createQuery(qlString,AlumnoClase.class);
		
		//Estableciendo parametros: id
		query.setParameter(1, id);
		
		//Obtener el resultado de la consulta
		AlumnoClase alumnoClase = query.getSingleResult();
		
		if(alumnoClase != null) {
			//Agregando el objeto cliente al Optional
			optional = Optional.of(alumnoClase);
		}
		
		return optional;
	}

	@Override
	public List<AlumnoClase> findAll() throws Exception {
		//Declara variable a retornar
		List<AlumnoClase> alumnoClases = new ArrayList<>();
		
		//Elaborar el JPQL
		String qlString = "SELECT a FROM AlumnoClase a";
		
		//Crear la consulta
		TypedQuery<AlumnoClase> query = em.createQuery(qlString,AlumnoClase.class);
		
		alumnoClases = query.getResultList();
		
		return alumnoClases;
	}

}
