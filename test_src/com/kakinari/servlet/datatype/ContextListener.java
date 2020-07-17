package com.kakinari.servlet.datatype;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

// import javax.servlet.annotation.WebListener;

import com.kakinari.servlet.listener.ServiceContentListener;

// @WebListener
public class ContextListener extends ServiceContentListener<ServletInfo> {

	@Override
	protected String getConnectionName() {
		return "serverContext";			// set context name on the context.xml
	}

	@Override
	protected ServletInfo getRowObject(ResultSet rs) throws SQLException {
		return new ServletInfo(rs);
	}

	@Override
	protected String getInsertQueryString(String tableName, String[] dupexpress, Map<String, String> colmap) {
		return null;
	}

	@Override
	protected String getUpdateQueryString(String tableName, String primary, Map<String, String> colmap) {
		return null;
	}

	@Override
	protected String getQueryString() {
		return "SELECT `servlet_contents`.`ServletName`," + 
				"    `servlet_contents`.`ServletClass`," + 
				"    `servlet_contents`.`ServletMap`," + 
				"    `servlet_contents`.`ServletParams`," + 
				"    `servlet_contents`.`ServletAuth`" + 
				"FROM `servlet_contents`";
	}

}
