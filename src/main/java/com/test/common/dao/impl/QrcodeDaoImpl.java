package com.test.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.jdbc.JdbcTemplateBaseDao;
import com.common.jdbc.SqlBuilder;
import com.test.common.dao.QrcodeDao;
import com.test.common.entity.Qrcode;

@Repository
public class QrcodeDaoImpl extends JdbcTemplateBaseDao implements QrcodeDao{

	@Override
	protected Class<?> getEntityClass() {
		return Qrcode.class;
	}
	
	@Override
	public long add(Qrcode qrcode) {
		return super.add(qrcode);
	}
	
	@Override
	public void update(Long id, String ticket) {
		SqlBuilder sqlBuilder = new SqlBuilder(
				"update qrcode set gmtModify=current_timestamp()");
		if(sqlBuilder.ifNotNull(ticket)){
			sqlBuilder.set("ticket", ticket);
		}
		super.update(id, sqlBuilder);
	}

	@Override
	public Qrcode get(Long id) {
		return super.queryForObject(id);
	}
	
	@Override
	public Qrcode getBySign(long accountId, String sign) {
		SqlBuilder sqlBuilder=new SqlBuilder("select * from Qrcode where 1=1");
		if(sqlBuilder.ifNotNull(accountId)){
			sqlBuilder.andEqualTo("accountId", accountId);
		}
		if(sqlBuilder.ifNotNull(sign)){
			sqlBuilder.andEqualTo("sign", sign);
		}
		return queryForObject(sqlBuilder);
	}
}
