package eu.schwicloud.manager.cloudservices.interfaces;

import eu.schwicloud.process.ServiceState;

public interface ITaskedService {

  void handelExecute(String line);
  void handelSync();
  void handelLaunch();
  void handelScreen();
  void handelQuit();
  void handelRestart();
  void handelStatusChange(ServiceState status);
  void handelCloudPlayerConnection(boolean connect);

}
