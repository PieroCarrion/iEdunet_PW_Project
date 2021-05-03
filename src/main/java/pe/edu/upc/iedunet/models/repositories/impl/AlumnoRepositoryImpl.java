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


import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.Periodo;
import pe.edu.upc.iedunet.models.repositories.AlumnoRepository;

@Named
@ApplicationScoped
public class AlumnoRepositoryImpl implements AlumnoRepository, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	
	@Override
	public Alumno save(Alumno entity) throws Exception {
		em.persist(entity);

		return entity;
	}

	@Override
	public Alumno update(Alumno entity) throws Exception {
		em.merge(entity);

		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Alumno> optional = findById(id);
		
		if (optional.isPresent()) {
			System.out.println("eliminando alumno " + optional.get().getId() + " en repository");
				em.remove(optional.get());
		}
	}

	@Override
	public Optional<Alumno> findById(Integer id) throws Exception {
		// Declara la variable a retornar
		Optional<Alumno> optional = Optional.empty();

		//Elaborar el JPQL
		String qlString = "SELECT a FROM Alumno a WHERE a.id = ?1";
		
		//Crear la consulta
		TypedQuery<Alumno> query = em.createQuery(qlString,Alumno.class);
		
		//Estableciendo parametros: id
		query.setParameter(1, id);
		
		//Obtener el resultado de la consulta
		Alumno alumno = query.getSingleResult();
		
		if(alumno != null) {
			//Agregando el objeto cliente al Optional
			optional = Optional.of(alumno);
		}
		
		return optional;
	}

	@Override
	public List<Alumno> findAll() throws Exception {
		//Declara variable a retornar
		List<Alumno> alumnos = new ArrayList<>();
		
		//Elaborar el JPQL
		String qlString = "SELECT a FROM Alumno a";
		
		//Crear la consulta
		TypedQuery<Alumno> query = em.createQuery(qlString,Alumno.class);
		
		alumnos = query.getResultList();
		
		return alumnos;
	}
	
	@Override
	public List<Alumno> findByColegio(int id) throws Exception {
		List<Alumno> alumnos = new ArrayList<>();

		String qlString = "SELECT c FROM Alumno c where c.usuario.colegio.id = ?1";

		TypedQuery<Alumno> query = em.createQuery(qlString, Alumno.class);
		
		query.setParameter(1, id);

		alumnos = query.getResultList();
		return alumnos;
	}

}
