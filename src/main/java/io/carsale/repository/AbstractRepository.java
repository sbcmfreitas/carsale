package io.carsale.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractRepository<T, PK extends Serializable> {

	@SuppressWarnings("unchecked")
	private final Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	@PersistenceContext
	private EntityManager entityManager;
    
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public boolean save(T entity) {
		entityManager.persist(entity);
		return entityManager.contains(entity);
	}
	
	public boolean update(T entity) {
		entityManager.merge(entity);
		return entityManager.contains(entity);
	}

	public boolean delete(T entity) {
		entityManager.remove(entity);
		return !entityManager.contains(entity);
	}

	public boolean delete(PK id) {
		entityManager.remove(entityManager.getReference(entityClass, id));
		return !entityManager.contains(entityManager.getReference(entityClass, id));
	}

	public T findById(PK id) {
		return entityManager.find(entityClass, id);
	}

	public List<T> findAll() {
		return entityManager.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
	}

}
