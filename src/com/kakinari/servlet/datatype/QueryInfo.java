package com.kakinari.servlet.datatype;

/*
 * Database Schema.
 * 
 CREATE TABLE `action_table` (
  `action_name` varchar(24) COLLATE utf8mb4_bin NOT NULL,
  `context` enum('default','database1','database2',database3r',database4') COLLATE utf8mb4_bin NOT NULL DEFAULT 'Console',
  `data_type` enum('TableData','MapData','DataSource') COLLATE utf8mb4_bin NOT NULL DEFAULT 'TableData',
  `query_string` varchar(2048) COLLATE utf8mb4_bin NOT NULL,
  `table_name` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `target_column` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL,
  `condition` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL,
  `group_by` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL,
  `order_by` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL,
  `primary_keys` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`action_name`,`context`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
 * 
 * META-INF/context.xml
 * 
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE Configuration>
<Context docBase="Console" path="/Console" reloadable="true">
    <Resource name="jdbc/Console" auth="Container"
            type="javax.sql.DataSource"
            username="<USER>" password="<PASSWORD>"
            driverClassName="com.mysql.cj.jdbc.Driver"
			jdbcInterceptors="ConnectionState;StatementFinalizer"
			url="jdbc:mysql://<SERVER_NAME>:<PORT>/Console?useUnicode=true&amp;useSSL=false&amp;characterEncoding=utf8"
            removeAbandonedOnBorrow="true"
            removeAbandonedOnMaintenance="true"
            removeAbandonedTimeout="60"
            logAbandoned="true"
            testWhileIdle="true"
            testOnBorrow="true"
            testOnReturn="false"
            validationQuery="SELECT 1"
			maxTotal="100"
			maxIdle="10"
			minIdle="3"
			maxWaitMillis="10000" />
 *
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.kakinari.core.datatype.interfaces.Param;
import com.kakinari.database.datatype.AbstractDBTable;
import com.kakinari.database.datatype.DBProcessRow;

public class QueryInfo extends AbstractDBTable<DBProcessRow> {

	public QueryInfo(String action, String context, Param param) {
		super(param);
		ArrayList<String> cond = new ArrayList<String>();
		cond.add(action);
		cond.add(context);
		execute(cond.toArray(new String[0]));
		if (size() == 0) {
			cond.add(1,	"default");
			execute(cond.toArray(new String[0]));
		}
	}

	public QueryInfo(String action, String context) {
		super();
		ArrayList<String> cond = new ArrayList<String>();
		cond.add(action);
		cond.add(context);
		execute(cond.toArray(new String[0]));
		if (size() == 0) {
			cond.add(1,	"default");
			execute(cond.toArray(new String[0]));
		}
	}

	@Override
	protected String getConnectionName() {
		return "Console";
	}

	@Override
	protected String getQueryString(String[] condition) {
		return "SELECT `action_name`," +
				"    `context`," +
				"    `data_type` + 0 AS data_type," +
				"    `query_string`," +
				"    `table_name`," +
				"    `target_column`," +
				"    `condition`, " +
				"    `group_by`, " +
				"    `order_by`" +
				"    `primary_keys`" +
				" FROM `Console`.`action_table`" +
				" WHERE `action_name` = '" + condition[0] + "'" +
				" AND   `context` = '" + condition[1] + "';";
	}

	@Override
	protected DBProcessRow getRowObject(ResultSet resultset) throws SQLException {
		return new DBProcessRow(resultset);
	}

	public Map<String,Object> getQuery() {
		 Map<String, Object> ret = new HashMap<String,Object>();
		 if (size() == 1) {
			 Map<?, ?> map = get(0).toMap();
			 for (Object key : map.keySet())
				 ret.put(key.toString(), map.get(key));
		 }
		 return ret;
	}

	@Override
	protected String getQueryString() {
		return null;
	}
}
