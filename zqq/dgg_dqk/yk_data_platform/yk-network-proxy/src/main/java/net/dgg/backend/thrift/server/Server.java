package net.dgg.backend.thrift.server;

public class Server {

    public static void main(String[] args) {
        try {
            Runnable runner = (Runnable) Class.forName("net.dgg.backend.thrift.server.ServerBoot").newInstance();
            runner.run();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
