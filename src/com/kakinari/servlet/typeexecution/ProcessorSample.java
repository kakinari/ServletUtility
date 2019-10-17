package com.kakinari.servlet.typeexecution;

import com.kakinari.core.datatype.NumberUnit;
import com.kakinari.database.datatype.DBDataRow;
import com.kakinari.database.datatype.DBProcessRow;

public class ProcessorSample implements TypeExecutionInterface {

	@Override
	public DBDataRow execute(DBProcessRow record, Object result) {
		if (! (result instanceof Integer))
			return null;
		Integer value = (Integer) result;
		/*
		 * record に設定されているパラメータを利用して結果を設定する
		 */
		DBDataRow returnValue = new DBDataRow();
		returnValue.put("value", new NumberUnit(value));
		return returnValue;
	}
}
