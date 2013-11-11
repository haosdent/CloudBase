package me.haosdent.cloud_hbase.sdk;

import static me.haosdent.cloud_hbase.sdk.Constants.*;

public class App implements Listenable {

  private String name;
  private String gid;

  public App(String name) {
    this.name = name;
    this.gid = name;
    Socket.register(this.name, this);
  }

  @Override
  public String getGid() {
    return gid;
  }

  public String getName() {
    return name;
  }

  @Override
  public void run(String cmd, Resp resp) {
    if (cmd.equals(SUCCESS))
      success(resp);
    else if (cmd.equals(ERROR))
      error(resp);
    else if (cmd.equals(UPDATE))
      update(resp);
  }

  public Model create(String id, String name, Callback cb) {
    Model model = new Model(this, id, name, cb);
    return model;
  }

  public void update(Resp resp) {
    System.out.println("Create " + this.name + " successfullly!");
  }

  public void error(Resp resp) {
  }

  public void success(Resp resp) {
  }
}
