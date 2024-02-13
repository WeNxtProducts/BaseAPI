package com.wenxt.base.sample;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import jakarta.transaction.Transactional;

public class SampleDaoImpl implements SampleDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	private final NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	public SampleDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedJdbcTemplate = namedJdbcTemplate;
	}

	@Override
	@Transactional
	public void insertOrUpdate(InsertRequestModel insertModel, String tableName, Integer id) {

		if (insertModel.getInsertFlag().equals('Y')) {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(tableName)
					.usingGeneratedKeyColumns("COLUMN1");

			Map<String, Object> parameters = new HashMap<>();
			insertModel.getRecords().stream().forEach(records -> {
//				parameters.put(records.getColumnName(), records.getValues());
			});
			Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);
		} else {
			StringBuilder sqlBuilder = new StringBuilder("UPDATE " + tableName + " SET ");

			Map<String, Object> parameters = new HashMap<>();
			insertModel.getRecords().stream().forEach(records -> {
//				sqlBuilder.append(records.getColumnName()).append(" =?, ");
//				parameters.put(records.getColumnName(), "SAMPLE");

			});

			sqlBuilder.delete(sqlBuilder.length() - 2, sqlBuilder.length());

			sqlBuilder.append(" WHERE COLUMN1 =?");

//			parameters.put("COLUMN1", id);
//
			System.out.println(sqlBuilder.toString() + " ");

			int affectedRows = jdbcTemplate.update(sqlBuilder.toString(), "Sample", id);

		}

	}

}
