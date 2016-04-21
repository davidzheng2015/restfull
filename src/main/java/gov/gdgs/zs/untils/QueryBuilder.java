package gov.gdgs.zs.untils;

public class QueryBuilder {
	
	public static final String EQUAL = "EQUAL"; // 相等
	 
    public static final String NOT_EQUAL = "NOT_EQUAL"; // 不相等
 
    public static final String LESS_THEN = "LESS_THEN"; // 小于
 
    public static final String LESS_EQUAL = "LESS_EQUAL"; // 小于等于
 
    public static final String GREATER_EQUAL = "GREATER_EQUAL"; // 大于等于
 
    public static final String GREATER_THEN = "GREATER_THEN"; // 大于
 
    public static final String FUZZY = "FUZZY"; // 模糊匹配 %xxx%
 
    public static final String FUZZY_LEFT = "FUZZY_LEFT"; // 左模糊 %xxx
 
    public static final String FUZZY_RIGHT = "FUZZY_RIGHT"; // 右模糊 xxx%
 
    public static final String NOT_EMPTY = "NOT_EMPTY"; // 不为空值的情况
 
    public static final String EMPTY = "EMPTY"; // 空值的情况
 
    public static final String IN = "IN"; // 在范围内
 
    public static final String NOT_IN = "NOT_IN"; // 不在范围内

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
