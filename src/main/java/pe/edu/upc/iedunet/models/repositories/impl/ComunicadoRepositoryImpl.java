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

import pe.edu.upc.iedunet.models.entities.Comunicado;
import pe.edu.upc.iedunet.models.repositories.ComunicadoRepository;

@Named
@ApplicationScoped
public class ComunicadoRepositoryImpl implements ComunicadoRepository, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	
	@Override
	public Comunicado save(Comunicado entity) throws Exception {
		em.persist(entity);

		return entity;
	}

	@Override
	public Comunicado update(Comunicado entity) throws Exception {
		em.merge(entity);

		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Comunicado> optional = findById(id);
		
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}

	@Override
	public Optional<Comunicado> findById(Integer id) throws Exception {
		// Declara la variable a retornar
		Optional<Comunicado> optional = Optional.empty();

		//Elaborar el JPQL
		String qlString = "SELECT c FROM Comunicado c WHERE c.id = ?1";
		
		//Crear la consulta
		TypedQuery<Comunicado> query = em.createQuery(qlString,Comunicado.class);
		
		//Estableciendo parametros: id
		query.setParameter(1, id);
		
		//Obtener el resultado de la consulta
		Comunicado comunicado = query.getSingleResult();
		
		if(comunicado != null) {
			//Agregando el objeto cliente al Optional
			optional = Optional.of(comunicado);
		}
		
		return optional;
	}

	@Override
	public List<Comunicado> findAll() throws Exception {
		List<Comunicado> comunicados = new ArrayList<>();
		
		String qlString = "SELECT c FROM Comunicado c";
		
		TypedQuery<Comunicado> query = em.createQuery(qlString,Comunicado.class);
		
		comunicados = query.getResultList();
		
		return comunicados;
	}

}
