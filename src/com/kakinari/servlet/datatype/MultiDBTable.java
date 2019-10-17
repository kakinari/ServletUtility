package com.kakinari.servlet.datatype;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.kakinari.database.datatype.DBDataRow;
import com.kakinari.database.datatype.DBTableUnit;
import com.kakinari.database.datatype.DataSource;
import com.kakinari.database.datatype.MappedTable;

public class MultiDBTable extends DBTableUnit {
	private Map<String, Object> querymap = null;
	private String context = null;

	public MultiDBTable(String action, String context, String[] target) {
		super();
		this.context  =context;
		this.querymap = new QueryInfo(action,context).getQuery();
		if (querymap.size() > 0) {
			execute(target);
		}
	}

	@Override
	protected String getConnectionName() {
		return this.context;
	}

	@Override
	protected String getQueryString() {
		StringBuffer buffer = new StringBuffer();
		if (querymap.get("query_string") != null)
			buffer.append(querymap.get("query_string").toString());
		if (querymap.get("table_name") != null)
			buffer.append(" FROM ").append(querymap.get("table_name").toString());
		return buffer.toString();
	}

	@Override
	protected String getConditionTerget() {
		Object ret = querymap.get("target_column");
		return ret == null ? null : ret.toString();
	}

	@Override
	protected String getExtraCondition() {
		Object ret =  querymap.get("condition");
		return ret == null ? null : ret.toString();
	}

	@Override
	protected String getOrderCondition() {
		Object ret =  querymap.get("order_by");
		return ret == null ? null : ret.toString();
	}

	@Override
	protected String getGroupCondition() {
		Object ret =  querymap.get("group_by");
		return ret == null ? null : ret.toString();
	}

/*	@Override
	protected String getQueryString(String[] param) {
		if (querymap == null || querymap.size() == 0)
			return "";
		StringBuffer buffer = new StringBuffer();
		if (querymap.get("query_string") != null)
			buffer.append(querymap.get("query_string").toString());
		if (querymap.get("table_name") != null)
			buffer.append(" FROM ").append(querymap.get("table_name").toString());
		if (querymap.get("condition") != null) {
			buffer.append(" WHERE (").append(querymap.get("condition")).append(")");
			if (param != null && querymap.get("target_column") != null)
				buffer.append(" AND (").append(AbstractTableAccess.getCondition(querymap.get("target_column").toString(), param).replace("WHERE ", "")).append(" ) ");
		} else {
			if (param != null && querymap.get("target_column") != null)
				buffer.append(AbstractTableAccess.getCondition(querymap.get("target_column").toString(), param));
		}
		if (querymap.get("group_by") != null)
			buffer.append("GROUP BY ").append(querymap.get("group_by").toString());
		if (querymap.get("order_by") != null)
			buffer.append("ORDER BY ").append(querymap.get("order_by").toString());
		System.out.println(buffer.toString());
		return buffer.toString();
	}
 */
	public int copyDataTo(String targetContext, Map<String,String> colmap) {
		return InsertData(targetContext, querymap.get("table_name").toString(), colmap);
	}
	
	public int updateDataTo(String targetContext, Map<String,String> colmap) {
		return updateData(targetContext,  querymap.get("primary_keys").toString(), querymap.get("table_name").toString(), colmap);
	}
	
	@Override
	public String toJSONString(int indent, boolean beautify) {
		if (querymap != null && querymap.get("data_type")!= null) {
			int type = ((Number)querymap.get("data_type")).intValue();
			switch(type) {
			case 2: // MapData
				return new MappedTable(this).toJSONString(indent, beautify);
			case 3: // DataSource
				return new DataSource(this).toJSONString(indent, beautify);
			default: // TableData
				return super.toJSONString(indent, beautify);
			}
		}
		return super.toJSONString(indent, beautify);
	}

	@Override
	protected DBDataRow getRowObject(ResultSet resultset) throws SQLException {
		return new DBDataRow(resultset);
	}
}
