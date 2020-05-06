package exceptions;

public class DoublonException extends Exception {

    public DoublonException() {
        super("La question se trouve déjà dans la liste");
    }
}
