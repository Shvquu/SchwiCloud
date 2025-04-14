package eu.schwicloud;


import eu.schwicloud.cloudplayer.offlineplayer.OfflinePlayerCacheDriver;
import eu.schwicloud.groups.GroupDriver;
import eu.schwicloud.groups.TemplateDriver;
import eu.schwicloud.language.LanguageDriver;
import eu.schwicloud.module.ModuleDriver;
import eu.schwicloud.storage.MessageStorage;
import eu.schwicloud.terminal.TerminalDriver;
import eu.schwicloud.webserver.WebServer;
import lombok.Getter;

@Getter
public final class Driver {

    private static Driver instance;
    private TerminalDriver terminalDriver;
    private final MessageStorage messageStorage;
    private final GroupDriver groupDriver;
    private final TemplateDriver templateDriver;
    private final ModuleDriver moduleDriver;
    private WebServer webServer;
    private LanguageDriver languageDriver;
    private OfflinePlayerCacheDriver offlinePlayerCacheDriver;

    public Driver(){
        /*
         * @FUNCTION: Load all for the Driver.class
         * @Coder: RauchigesEtwas (Robin B.)
         * */
        instance = this;
        groupDriver = new GroupDriver();
        this.moduleDriver = new ModuleDriver();
        this.messageStorage = new MessageStorage();
        this.templateDriver = new TemplateDriver();


    }

    public void runWebServer(){
        this.webServer = new WebServer();
    }

    public void setTerminalDriver(TerminalDriver terminalDriver) {
        this.terminalDriver = terminalDriver;
    }

    public static Driver getInstance() {
        return instance;
    }

    public LanguageDriver getLanguageDriver() {
        return languageDriver;
    }

    public OfflinePlayerCacheDriver getOfflinePlayerCacheDriver() {
        return offlinePlayerCacheDriver;
    }

    public void handleOfflinePlayerCacheDriver() {
        this.offlinePlayerCacheDriver = new OfflinePlayerCacheDriver();
    }

    public void setLanguageDriver(LanguageDriver languageDriver) {
        this.languageDriver = languageDriver;
    }
}
