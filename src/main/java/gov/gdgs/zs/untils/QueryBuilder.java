package gov.gdgs.zs.untils;

public class QueryBuilder {

	private StringBuffer sb;
	
	private Object[] params;
	
	public QueryBuilder(String str) {
		super();
		this.sb.append(str);
	}
	
	public String getSql (){
		return this.sb.toString();
	}
	
	public Object[] getParams (){
		return this.params;
	}
}
