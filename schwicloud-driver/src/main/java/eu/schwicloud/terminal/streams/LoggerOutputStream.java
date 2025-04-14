/*
 * this class is by RauchigesEtwas
 */

/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.terminal.streams;

import eu.schwicloud.Driver;
import eu.schwicloud.networking.NettyDriver;
import eu.schwicloud.networking.packet.packets.in.node.PacketInSendConsole;
import eu.schwicloud.terminal.enums.Type;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public final class LoggerOutputStream extends ByteArrayOutputStream {

    private final Type logType;

    public LoggerOutputStream(final Type logType) {
        this.logType = logType;
    }

    @Override
    public void flush() {
        final var input = this.toString(StandardCharsets.UTF_8);
        this.reset();
        if (input != null && !input.isEmpty()) {
            if (Driver.getInstance().getMessageStorage().sendConsoleToManager){
                NettyDriver.getInstance().nettyClient.sendPacketsAsynchronous(new PacketInSendConsole(Driver.getInstance().getMessageStorage().sendConsoleToManagerName, input));
            }
            Driver.getInstance().getTerminalDriver().log(this.logType, input.split("\n"));
            Driver.getInstance().getTerminalDriver().getLineReader().getTerminal().flush();
        }
    }
}
