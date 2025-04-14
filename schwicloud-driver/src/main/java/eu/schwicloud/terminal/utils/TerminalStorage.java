/*
 * this class is by RauchigesEtwas
 */

/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.terminal.utils;

import eu.schwicloud.terminal.enums.Type;

public final class TerminalStorage {

    private final Type type;
    private final String message;

    public TerminalStorage(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
