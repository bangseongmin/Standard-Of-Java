package chapter8.try_with_resource;

import java.io.Closeable;

public class CloseableResource implements AutoCloseable {

    public void exceptionWork(boolean exception) throws WorkException {
        System.out.println("called exceptionWork ("+ exception + ")");

        if(exception) {
            throw new WorkException("occurred WorkException");
        }
    }

    @Override
    public void close() throws CloseException {
        System.out.println("called close()");
        throw new CloseException("occurred CloseException");
    }
}
