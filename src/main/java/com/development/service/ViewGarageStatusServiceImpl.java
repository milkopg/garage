package com.development.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.development.dao.ViewGarageStatusDao;
import com.development.model.ViewGarageStatus;

@Service("viewGarageStatusService")
public class ViewGarageStatusServiceImpl implements ViewGarageStatusService {

	@Autowired
	ViewGarageStatusDao dao;
	public List<ViewGarageStatus> getGarageStatuses() {
		return dao.getGarageStatuses();
	}

}
