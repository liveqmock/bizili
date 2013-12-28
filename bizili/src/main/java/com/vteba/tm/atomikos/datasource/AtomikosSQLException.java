package com.vteba.tm.atomikos.datasource;

import java.sql.SQLException;

import com.atomikos.logging.Logger;
import com.atomikos.logging.LoggerFactory;

public class AtomikosSQLException extends SQLException {

	private static final long serialVersionUID = -5625723958099460181L;
	private static final Logger LOGGER = LoggerFactory.createLogger(AtomikosSQLException.class);
	
	/**
	 * Logs and throws an AtomikosSQLException.
	 * 
	 * @param message The message to use.
	 * @param cause The causing error.
	 * @throws AtomikosSQLException
	 */
	public static void throwAtomikosSQLException ( String message , Throwable cause ) throws AtomikosSQLException 
	{
		LOGGER.logWarning ( message , cause );
		throw new AtomikosSQLException ( message , cause );
	}
	
	/**
	 * Logs and throws an AtomikosSQLException.
	 * 
	 * @param message The message to use.
	 * @throws AtomikosSQLException
	 */
	public static void throwAtomikosSQLException ( String message ) throws AtomikosSQLException 
	{
		throwAtomikosSQLException ( message , null );
	}
	
	private AtomikosSQLException(String message, Throwable cause) {
		super(message);
		
		if (cause instanceof SQLException) {
			setNextException((SQLException) cause);
		}
		initCause(cause);
	}

	private AtomikosSQLException(String message) {
		super(message);
	}

	private AtomikosSQLException(Throwable cause) {
		if (cause instanceof SQLException) {
			setNextException((SQLException) cause);
		}
		initCause(cause);
	}

}
