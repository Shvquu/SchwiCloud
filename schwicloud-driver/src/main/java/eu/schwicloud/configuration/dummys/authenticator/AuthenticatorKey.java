package eu.schwicloud.configuration.dummys.authenticator;

import eu.schwicloud.configuration.interfaces.IConfigAdapter;

public class AuthenticatorKey implements IConfigAdapter {

    @lombok.Setter
    @lombok.Getter
    private String key;
    public AuthenticatorKey() {}

}
