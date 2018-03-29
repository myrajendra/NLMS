package com.jeevasamruddhi.telangana.nlms.android.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Prakash K
 * @date 29-Aug-2015
 *
 */
public class JsonUtils
{
	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * 
	 * @param object
	 * @return
	 */
	public static String toString(Object object)
	{
		String str = null;

		try
		{
			str = mapper.writeValueAsString(object);
		}
		catch (JsonProcessingException e)
		{
			str = "" + object;
		}

		return str;
	}
}
