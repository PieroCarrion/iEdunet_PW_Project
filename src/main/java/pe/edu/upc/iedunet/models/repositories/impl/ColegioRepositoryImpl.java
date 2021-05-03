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


import pe.edu.upc.iedunet.models.entities.Colegio;
import pe.edu.upc.iedunet.models.repositories.ColegioRepository;

@Named
@ApplicationScoped
class ColegioRepositoryImpl implements ColegioRepository, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;
	
	@Override
	public Colegio save(Colegio entity) throws Exception {
		em.persist(entity);

		return entity;
	}

	@Override
	public Colegio update(Colegio entity) throws Exception {
		em.merge(entity);

		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Colegio> optional = findById(id);
		
		if (optional.isPresent()) {
			em.remove(optional.get());
		}
	}
//	@Override
//	public void deleteByCodigo(String codigo) throws Exception {
//		Optional<Colegio> optional = findByCodigo(codigo);
//		if (optional.isPresent()) {
//			em.remove(optional.get());
//		}
//	}
	@Override
	public Optional<Colegio> findByCodigo(String codigo) throws Exception{
		// Declara la variable a retornar
				Optional<Colegio> optional = Optional.empty();
				// Elaborar el JPQL
				String qlString = "SELECT c FROM Colegio c WHERE c.codigo = ?1";
				// Crear la consulta
				TypedQuery<Colegio> query = em.createQuery(qlString, Colegio.class);
				// Estableciendo los paremetros: id
				query.setParameter(1, codigo);
				// Obtener el resultado de la consulta
				//Cliente cliente = query.getSingleResult();
				Colegio colegio = query.getResultList().stream().findFirst().orElse(null);
				// Verificar la existencia del objeto
				if(colegio != null) {
					// Agregando el objeto cliente al Optional
					optional = Optional.of(colegio);
				}		
				return optional;
	}
	@Override
	public Optional<Colegio> findById(Integer id) throws Exception {
		// Declara la variable a retornar
		Optional<Colegio> optional = Optional.empty();

		//Elaborar el JPQL
		String qlString = "SELECT c FROM Colegio c WHERE c.id = ?1";
		
		//Crear la consulta
		TypedQuery<Colegio> query = em.createQuery(qlString,Colegio.class);
		
		//Estableciendo parametros: id
		query.setParameter(1, id);
		
		//Obtener el resultado de la consulta
		Colegio colegio = query.getSingleResult();
		
		if(colegio != null) {
			//Agregando el objeto cliente al Optional
			optional = Optional.of(colegio);
		}
		
		return optional;
	}

	@Override
	public List<Colegio> findAll() throws Exception {
		List<Colegio> colegios = new ArrayList<>();
		
		String qlString = "SELECT c FROM Colegio c";
		
		TypedQuery<Colegio> query = em.createQuery(qlString,Colegio.class);
		
		colegios = query.getResultList();
		
		return colegios;
	}
}
