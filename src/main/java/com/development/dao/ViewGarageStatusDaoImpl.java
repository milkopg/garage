package com.development.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.development.model.ViewGarageStatus;

@Repository("viewGarageStatus")
public class ViewGarageStatusDaoImpl extends AbstractDao<Integer, ViewGarageStatus> implements ViewGarageStatusDao {

	@SuppressWarnings("unchecked")
	public List<ViewGarageStatus> getGarageStatuses() {
		List<ViewGarageStatus> viewGarageStatuses = getEntityManager()
				.createQuery("SELECT o from ViewGarageStatus o ORDER BY o.levelName")
				.getResultList();
		return viewGarageStatuses;
	}

}
