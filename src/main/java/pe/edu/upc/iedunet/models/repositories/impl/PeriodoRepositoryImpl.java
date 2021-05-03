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
import pe.edu.upc.iedunet.models.entities.Periodo;
import pe.edu.upc.iedunet.models.repositories.PeriodoRepository;

@Named
@ApplicationScoped
public class PeriodoRepositoryImpl implements PeriodoRepository, Serializable {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "iEdunetPU")
	private EntityManager em;

	@Override
	public Periodo save(Periodo entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Periodo update(Periodo entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Periodo> optional = findById(id);

		if (optional.isPresent()) {
			em.remove(optional.get());
		}

	}

	@Override
	public Optional<Periodo> findById(Integer id) throws Exception {

		Optional<Periodo> optional = Optional.empty();

		String qlString = "SELECT c FROM Periodo c WHERE c.id = ?1";

		TypedQuery<Periodo> query = em.createQuery(qlString, Periodo.class);

		query.setParameter(1, id);

		Periodo periodo = query.getSingleResult();

		if (periodo != null) {

			optional = Optional.of(periodo);
		}
		return optional;
	}

	@Override
	public List<Periodo> findAll() throws Exception {
		List<Periodo> periodos = new ArrayList<>();

		String qlString = "SELECT c FROM Periodo c";

		TypedQuery<Periodo> query = em.createQuery(qlString, Periodo.class);

		periodos = query.getResultList();
		return periodos;
	}

	@Override
	public List<Periodo> findByColegio(int id) throws Exception {
		List<Periodo> periodos = new ArrayList<>();

		String qlString = "SELECT c FROM Periodo c where c.colegio.id = ?1";

		TypedQuery<Periodo> query = em.createQuery(qlString, Periodo.class);
		
		query.setParameter(1, id);

		periodos = query.getResultList();
		return periodos;
	}

}
