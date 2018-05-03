package service;

/**
 * Created by jinhua.chen on 2018/5/1.
 */
public class ApplicationException extends RuntimeException {
    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }
}
