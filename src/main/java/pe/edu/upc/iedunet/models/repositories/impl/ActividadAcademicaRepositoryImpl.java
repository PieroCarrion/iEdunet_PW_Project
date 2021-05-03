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

import pe.edu.upc.iedunet.models.entities.ActividadAcademica;
import pe.edu.upc.iedunet.models.repositories.ActividadAcademicaRepository;

@Named
@ApplicationScoped
class ActividadAcademicaRepositoryImpl implements ActividadAcademicaRepository, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	
	@Override
	public ActividadAcademica save(ActividadAcademica entity) throws Exception {
		em.persist(entity);

		return entity;
	}

	@Override
	public ActividadAcademica update(ActividadAcademica entity) throws Exception {
		em.merge(entity);

		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<ActividadAcademica> optional = findById(id);
		
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<ActividadAcademica> findById(Integer id) throws Exception {
		// Declara la variable a retornar
		Optional<ActividadAcademica> optional = Optional.empty();

		//Elaborar el JPQL
		String qlString = "SELECT a FROM ActividadAcademica a WHERE a.id = ?1";
		
		//Crear la consulta
		TypedQuery<ActividadAcademica> query = em.createQuery(qlString,ActividadAcademica.class);
		
		//Estableciendo parametros: id
		query.setParameter(1, id);
		
		//Obtener el resultado de la consulta
		ActividadAcademica actividadAcademica = query.getSingleResult();
		
		if(actividadAcademica != null) {
			//Agregando el objeto cliente al Optional
			optional = Optional.of(actividadAcademica);
		}
		
		return optional;
	}

	@Override
	public List<ActividadAcademica> findAll() throws Exception {
		List<ActividadAcademica> actividadAcademicas = new ArrayList<>();
		
		String qlString = "SELECT a FROM ActividadAcademica a";
		
		TypedQuery<ActividadAcademica> query = em.createQuery(qlString,ActividadAcademica.class);
		
		actividadAcademicas = query.getResultList();
		
		return actividadAcademicas;
	}

}
