package petfinder.site.common.Exceptions;

/**
 * @author Laird
 * Exception for when a pet endpoint is incorrectly used.
 */
public class PetException extends Exception {
    public PetException(String e){
        super(e);
    }
    public PetException(){
        super();
    }
}
