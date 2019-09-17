package net.dgg.yk.platform.backend.command;

public interface CommandReceiver {

    String getSupport();

    <T> T execute(Command command);

}
