package sk.kadlecek.mle.preprocessing.input;

import java.io.IOException;

public interface InputReader {

    public InputValue[] readInputValues(String filename) throws IOException;

}
