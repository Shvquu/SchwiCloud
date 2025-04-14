/*
 * this class is by RauchigesEtwas
 */

package eu.schwicloud.webserver.dummys;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;

public class UpdateConfig implements IConfigAdapter {

    String data;

    public UpdateConfig(String data) {
        this.data = data;
    }

    public UpdateConfig() {
    }

    public String getData() {
        return data;
    }
}
