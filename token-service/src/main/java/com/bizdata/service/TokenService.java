package com.bizdata.service;

/**
 * TokenService接口
 *
 * @author sdevil507
 *
 */
public interface TokenService {

	/**
	 * 传入userID创建token
	 * 
	 * @param userID
	 *            用户ID
	 * @return String类型token字符串
	 */
	public String createToken(String userID);

	/**
	 * 注销token
	 * 
	 * @param tokenID
	 *            tokenID
	 * @return
	 */
	public boolean removeToken(String tokenID);

	/**
	 * token时间续租
	 * 
	 * @param token
	 *            token
	 * @return
	 */
	public boolean tokenAutoPay(String token);
	
	/**
	 *  根据token获取userID
	 * 
	 * @param token token
	 * @return
	 */
	public String getUserID(String token);
	
	/**
	 * 判断token是否存在
	 * 
	 * @param token token
	 * @return
	 */
	public boolean checkTokenExist(String token);

}
