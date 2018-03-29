package com.jeevasamruddhi.telangana.nlms.android.model;

import java.io.Serializable;


/**
 * 
 * @author Prakash K
 * @date 29-Aug-2015
 *
 */
public class BaseModel implements Serializable
{
	@Override
	public String toString()
	{
		return JsonUtils.toString(this);
	}
}
