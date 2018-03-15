package com.core.service;
import com.core.dao.IBaseDao;

public abstract  class BaseService<T> implements IBaseService<T> {
	public abstract IBaseDao<T> getBaseDao();

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return this.getBaseDao().deleteByPrimaryKey(id);
	}

	@Override
	public int insert(T record) {
		
		return this.getBaseDao().insert(record);
	}

	@Override
	public int insertSelective(T record) {
		// TODO Auto-generated method stub
		return this.getBaseDao().insertSelective(record);
	}

	@Override
	public T selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.getBaseDao().selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		// TODO Auto-generated method stub
		return this.getBaseDao().updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		// TODO Auto-generated method stub
		return this.getBaseDao().updateByPrimaryKey(record);
	}
	
}

