package com.kakinari.servlet.typeexecution;

import com.kakinari.database.datatype.DBDataRow;
import com.kakinari.database.datatype.DBProcessRow;

public interface TypeExecutionInterface {
	/*
	 * パラメータのオブジェクトのクラスタイプに従った処理を行うためのインターフェース
	 */
	public DBDataRow execute(DBProcessRow record, Object result);
}
