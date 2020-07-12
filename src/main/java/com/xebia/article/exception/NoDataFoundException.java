package com.xebia.article.exception;
public class NoDataFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -177956571548822627L;

	public NoDataFoundException() {

        super("No data found");
    }
}