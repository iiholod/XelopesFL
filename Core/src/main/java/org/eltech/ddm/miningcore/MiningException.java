package org.eltech.ddm.miningcore;

/**
 * Mining exception. Mother class for all other mining exceptions. <p>
 *
 * From PDM CWM extension.
 */
public class MiningException extends Exception//JDMException
{
   private static final String[] errorMessage =
		{"",	//INVALID_ARGUMENT
		 "",		//INVALID_DATA_TYPE
		 "",		// INVALID_OBJECT_TYPE
		 "not supported", //UNSUPPORTED
		 "", //WRONG_IMPLIMENTATION
		 "Index out of bounds. Check offset and length.", // INVALID_INDEX
		 "", //INVALID_URL
		 "Parallel execution error." //PARALLEL_EXECUTION_ERROR
		};

	private final MiningErrorCode code;


    public MiningException( MiningErrorCode code)
    {
        super( errorMessage[code.ordinal()] );
        this.code = code;
    }

	/**
     * Mining exception.
     *
     * @param code - code of error
     * @param msg - description of exception
     */
    public MiningException( MiningErrorCode code, String msg )
    {
        super( msg );
        this.code = code;
    }

}
