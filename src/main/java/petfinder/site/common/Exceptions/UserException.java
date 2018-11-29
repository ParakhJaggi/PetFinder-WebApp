package petfinder.site.common.Exceptions;

/**
 * @author Laird
 * The exception for when a user endpoint is incrrectly used.
 */
public class UserException extends Exception{
    public UserException(String e){
        super(e);
    }
    public UserException(){
        super();
    }
}
