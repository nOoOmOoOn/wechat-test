package com.test.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.jdbc.JdbcTemplateBaseDao;
import com.common.jdbc.SqlBuilder;
import com.test.common.dao.BindingDao;
import com.test.common.entity.Binding;

@Repository
public class BindingDaoImpl extends JdbcTemplateBaseDao implements BindingDao{

	@Override
	protected Class<?> getEntityClass() {
		return Binding.class;
	}
	
	@Override
	public long add(Binding binding) {
		return super.add(binding);
	}

	@Override
	public Binding get(Long id) {
		return super.queryForObject(id);
	}

	@Override
	public Binding getByOpenId(String openId) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from Binding where 1=1");
		if(sqlBuilder.ifNotNull(openId)){
			sqlBuilder.andEqualTo("openId", openId);
		}
		return queryForObject(sqlBuilder);
	}
	

}
