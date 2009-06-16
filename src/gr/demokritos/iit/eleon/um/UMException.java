package gr.demokritos.iit.eleon.um;

/**
* Used to signal errors related to user modeling.
* @author Ion Androutsopoulos (ionandr@iit.demokritos.gr).
* @version 0.1
**/
public class UMException extends Exception {
    public UMException() {
        super();
    }
    public UMException(String msg) {
        super(msg);
    }
}
