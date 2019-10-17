package com.kakinari.servlet.typeexecution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.kakinari.database.datatype.DBDataRow;
import com.kakinari.database.datatype.DBProcessRow;
import com.kakinari.servlet.exception.NoTypeFunctionException;

/*
 * 結果で受け取るオブジェクトごとに処理用のクラスを登録し、
 * 結果のタイプごとに処理クラスの処理を実行するクラス。
 */
public class TypeExecution extends  HashMap<Class<?>,Object> {
	private static final long serialVersionUID = -7257233970586352524L;
	/*
	 * コンストラクタ
	 * テーブルの準備
	 */
	public TypeExecution() {
		super();
	}
	
	/*
	 * DBからの初期化を行うためのコンストラクター
	 * 　カラムの1番目にターゲットクラス名
	 * 　カラムの2番目にプロセッサークラス名
	 * が格納されている結果セットを渡す。
	 */
	public TypeExecution(ResultSet rs) {
		super();
		try {
			while(rs.next()) {
				try {
					put (Class.forName(rs.getString(1)), Class.forName(rs.getString(2)));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {}
	}
	/*
	 * 関数　put
	 * 処理ルーチン登録用関数
	 * key : 指定するクラス　(ex: Integer.class)
	 * value : 処理のためのクラス
	 */
	public TypeExecution put(Class<?> key, Object value) {
		if (key == null)
			return null;
		for (Class<?> cls : value.getClass().getInterfaces()) {
			if (cls == TypeExecutionInterface.class) {
		 		put(key, value);
				return this;
			}
		}
    	return this;
	}
	/*
	 * 関数 execute
	 * 引数の型にしたがった処理クラスのexecute関数を実行
	 */
	public DBDataRow  execute(DBProcessRow record, Object result) throws Exception {
		Object funcClass = get(result.getClass());
		if (funcClass == null) {
			throw new NoTypeFunctionException();
		}
		DBDataRow ret;
		try {
			ret = ((TypeExecutionInterface) funcClass).execute(record, result);
		} catch (Exception e) {
			throw new Exception("999:システムエラー");
		}
		return ret;
	}
}
