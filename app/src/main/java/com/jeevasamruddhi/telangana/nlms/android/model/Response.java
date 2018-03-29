package com.jeevasamruddhi.telangana.nlms.android.model;

/**
 * 
 * @author prakash
 * @date 29-Aug-2015
 *
 */
public class Response extends BaseModel
{
	private boolean successful;

	private Object requestObject;

	private Object responseObject;

	private String errorMessage;

	public boolean isSuccessful()
	{
		return successful;
	}

	public void setSuccessful(boolean successful)
	{
		this.successful = successful;
	}

	public Object getRequestObject()
	{
		return requestObject;
	}

	public void setRequestObject(Object requestObject)
	{
		this.requestObject = requestObject;
	}

	public Object getResponseObject()
	{
		return responseObject;
	}

	public void setResponseObject(Object responseObject)
	{
		this.responseObject = responseObject;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
}
