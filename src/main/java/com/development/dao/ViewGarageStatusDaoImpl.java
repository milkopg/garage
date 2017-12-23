package com.development.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.development.model.ViewGarageStatus;

@Repository("viewGarageStatus")
public class ViewGarageStatusDaoImpl extends AbstractDao<Integer, ViewGarageStatus> implements ViewGarageStatusDao {

	@SuppressWarnings("unchecked")
	public List<ViewGarageStatus> getGarageStatuses() {
		EntityManager entityManager  = getEntityManager();
		//we need to clear entityManager, because data from view is not updated
		entityManager.clear();
		List<ViewGarageStatus> viewGarageStatuses = entityManager
				.createQuery("SELECT o from ViewGarageStatus o ORDER BY o.levelName")
				.getResultList();
		return viewGarageStatuses;
	}

}
