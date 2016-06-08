package gov.gdgs.zs.dao;

import java.util.Map;




public interface ILrbDao {
    
	public String addLrb( Map <String,Object >obj);
	public void updateLrb(Map <String,Object >obj);
	
	public String addLrfpb( Map <String,Object >obj);
	public void updateLrfpb(Map <String,Object >obj);
}
