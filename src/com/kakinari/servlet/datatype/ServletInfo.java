package com.kakinari.servlet.datatype;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.HttpConstraintElement;
import javax.servlet.HttpMethodConstraintElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import com.kakinari.core.comparator.NameComparator;
import com.kakinari.core.datatype.BaseUnit;
import com.kakinari.core.datatype.BoolUnit;
import com.kakinari.core.datatype.DateUnit;
import com.kakinari.core.datatype.ListUnit;
import com.kakinari.core.datatype.MapUnit;
import com.kakinari.core.datatype.NullUnit;
import com.kakinari.core.datatype.NumberUnit;
import com.kakinari.core.datatype.ObjectUnit;
import com.kakinari.core.datatype.StringUnit;
import com.kakinari.core.datatype.TreeDataUnit;
import com.kakinari.core.datatype.TreeUnit;
import com.kakinari.database.datatype.DBDataRow;
import com.kakinari.servlet.datatype.interfaces.ServletInfoAccess;

public class ServletInfo extends DBDataRow implements ServletInfoAccess  {
	private static final String KEY_SERVLET = "ServletName";
	private static final String KEY_SERVLET_CLASS = "ServletClass";
	private static final String KEY_MAPLIST = "ServletMap";
	private static final String KEY_INITPARAM = "ServletParams";
	private static final String KEY_AUTHINFO = "ServletAuth";

	public ServletInfo() {
		super();
	}

	public ServletInfo(Map<? extends Object, ? extends Object> map) {
		super();
		for(Object key : map.keySet()) {
			if (KEY_SERVLET.equals(key.toString()))
				put(KEY_SERVLET, new StringUnit(map.get(key).toString()));
			else if (KEY_SERVLET.equals(key.toString()))
				put(KEY_SERVLET_CLASS, new StringUnit(map.get(key).toString()));
			else if (KEY_SERVLET.equals(key.toString()))
				put(KEY_MAPLIST, getList(map.get(key).toString()));
			else if (KEY_SERVLET.equals(key.toString()))
				put(KEY_INITPARAM, getMap(map.get(key).toString()));
			else if (KEY_SERVLET.equals(key.toString()))
				put(KEY_AUTHINFO, getMap(map.get(key).toString()));
			else
				put(key.toString(), new StringUnit(map.get(key).toString()));
		}
	}

	public ServletInfo(String name, String clsname, String maplist, String params, String authmap) {
		put(KEY_SERVLET, new StringUnit(name));
		put(KEY_SERVLET_CLASS, new StringUnit(clsname));
		put(KEY_MAPLIST, getList(maplist));
		put(KEY_INITPARAM, getMap(params));
		put(KEY_AUTHINFO, getMap(authmap));
	}
	
	private TreeUnit<BaseUnit<?>> getMap(String map) {
		TreeDataUnit ret = new TreeDataUnit();
		if (map == null || ! map.startsWith("{") || ! map.endsWith("}"))
			return ret;
		map = map.substring(1, map.length()-1).replaceAll("[\t\n\r ]", "");
		if (map == null)
			return ret;
		while (map.length()> 0) {
			String[] kv = map.split("=",2);
			if (kv.length == 2) {
				switch(kv[1].charAt(0)) {
				case '[':
					map = kv[1].substring( kv[1].indexOf(']')+1);
					ret.put(kv[0], getList(kv[1].substring(0, kv[1].indexOf(']') + 1)));
					break;
				case '{':
					map = kv[1].substring( kv[1].indexOf('}')+1);
					ret.put(kv[0], getMap(kv[1].substring(0, kv[1].indexOf('}') + 1)));
					break;
				default:
					if (kv[1].contains(",")) {
						map = kv[1].substring( kv[1].indexOf(','));
						ret.put(kv[0], new StringUnit(kv[1].substring(0, kv[1].indexOf(',')-1)));
					} else {
						map = "";
						ret.put(kv[0], new StringUnit(kv[1]));
					}
				}
				if (map.startsWith(","))
					map = map.substring(1);
			} else {
				ret.put(kv[0], new StringUnit(""));
			}
		}
		return ret;
	}

	private ListUnit<StringUnit> getList(String liststr) {
		ListUnit<StringUnit> ret = new ListUnit<StringUnit>() {
			protected void setListData(List<?> list) {}
		};
		if (liststr == null)
			return ret;
		if (liststr.startsWith("[") && liststr.endsWith("]"))
			liststr = liststr.substring(1, liststr.length()-1);
		liststr = liststr.replaceAll("[\t\n\r ]", "");
		for(String val : liststr.split(","))
			ret.add(new StringUnit(val));
		return ret;
	}

	public ServletInfo(ResultSet resultset) throws SQLException {
		super(new NameComparator(resultset));
		 ResultSetMetaData meta = resultset.getMetaData();
		for(int i=1; i<=meta.getColumnCount(); i++) {
			String name =meta.getColumnLabel(i);
			Object val = resultset.getObject(i);
			if ((name.equals(KEY_INITPARAM) ||
				 name.equals(KEY_AUTHINFO)) && val instanceof String)
				put(name, getMap(val.toString()));
			else if (name.equals(KEY_MAPLIST) && val instanceof String)
				put(name, getList(val.toString()));
			else if (val == null)
				put(name,new NullUnit());
			else if (val instanceof Boolean)
				put(name,new BoolUnit((Boolean) val));
			else if (val instanceof Number)
				put(name,new NumberUnit((Number) val));
			else if (val instanceof Date)
				put(name,new DateUnit((Date) val));
			else if (val instanceof Calendar)
				put(name,new DateUnit((Calendar) val));
			else if (val instanceof Timestamp)
				put(name,new DateUnit((Timestamp) val));
			else if (val instanceof String)
				put(name,new StringUnit((String) val));
			else
				put(name,new ObjectUnit(val));
		}
	}

	@Override
	public String getServletName() {
		BaseUnit<?> val = get(KEY_SERVLET);
		return val == null ? null : val.toString();
	}

	@Override
	public String getServletClass() {
		BaseUnit<?> val = get(KEY_SERVLET_CLASS);
		return val == null ? null : val.toString();
	}

	@Override
	public String[] getServletMap() {
		BaseUnit<?> val = get(KEY_MAPLIST);
		if (val == null || ! (val instanceof ListUnit))
			return null;
		return ((ListUnit<?>)val).toArray(new String[0]);
	}

	@Override
	public String[] getServletAuthKey() {
		Map<?, ?> auth = getServletAuth();
		return auth == null ? null : auth.keySet().toArray(new String[0]);
	}

	@Override
	public Map<?,?> getServletParams() {
		BaseUnit<?> val = get(KEY_INITPARAM);
		if (val == null || !( val instanceof MapUnit))
			return null;
		return ((MapUnit<?>)val).toMap();
	}

	@Override
	public Map<?,?> getServletAuth() {
		BaseUnit<?> val = get(KEY_AUTHINFO);
		if (val == null || ! (val instanceof MapUnit))
			return null;
		return ((MapUnit<?>)val).toMap();
	}

	@Override
	public void addServlet(ServletContext context) {
		Dynamic registration = null;
		try {
			registration = context.addServlet(getServletName(), getServletClass());
		} catch(IllegalStateException | UnsupportedOperationException  e) {
		}
		if (registration == null)	// already registered.
			return;
		registration.addMapping(getServletMap());
		Map<?, ?> param = getServletParams();
		if (param != null) {
			for (Object key :param.keySet()) {
				registration.setInitParameter(key.toString(), param.get(key).toString());
			}
		}
		if (getServletAuth() != null) {
			Map<?, ?> auth = getServletAuth();
			HttpConstraintElement delem = null;
			ArrayList<HttpMethodConstraintElement>list = new ArrayList<HttpMethodConstraintElement>();
			for (Object method : auth.keySet()) {
				HttpConstraintElement elem = new HttpConstraintElement(
						EmptyRoleSemantic.PERMIT, 
						TransportGuarantee.NONE, 
						getServletAuth(method.toString()));
				if ("ALL".equals(method)) {
					delem = elem;
				} else {
					list.add(new HttpMethodConstraintElement(method.toString(), elem));
				}
			}
			if (delem != null) {
				if (list.size()>0)
					registration.setServletSecurity(new ServletSecurityElement(delem, list));
				else
					registration.setServletSecurity(new ServletSecurityElement(delem));
			} else if (list.size()>0) {
					registration.setServletSecurity(new ServletSecurityElement(list));
			}
		}
	}

	private String[] getServletAuth(String method) {
		if ( getServletAuth() == null)
			return new String[0];
		Map<?,?> map = getServletAuth();
		if (map != null && map.get(method) != null)
			return map.get(method).toString().split(",");
		return new String[0];
	}

	public  void setServletName(String name) {
		put(KEY_SERVLET, new StringUnit(name));
	}

	public void setServletClass(String name) {
		put(KEY_SERVLET_CLASS, new StringUnit(name));
	}

	public void setServletMap(String maplist) {	// pass conma separated map list
		put(KEY_MAPLIST, getList(maplist));
	}

	public  void setServletParams(String parammapJSON) {
		put(KEY_INITPARAM, getMap(parammapJSON));
	}

	public void setServletAuth(String authmapJSON) {
		put(KEY_INITPARAM, getMap(authmapJSON));
	}

}
