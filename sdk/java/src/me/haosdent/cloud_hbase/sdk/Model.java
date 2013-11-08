package me.haosdent.cloud_hbase.sdk;

import java.util.concurrent.Callable;

import static me.haosdent.cloud_hbase.sdk.Constants.*;

public class Model implements Listenable {

  private String gid;
  private String id;
  private String name;
  private Callable cb;

  public Model(App app, String id, String name, Callable cb) {
    this.name = name;
    this.id = id;
    this.cb = cb;

    this.gid = app.getName() + this.name + this.id;
    //TODO
  }

  @Override
  public String getGid() {
    return gid;
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

  public void save(){
    //TODO
  }

  public void on(){
    //TODO
  }

  public void up(){
    //TODO
  }

  public void update(Resp resp){
    //TODO
  }

  public void error(Resp resp){
  }

  public void success(Resp resp){
  }
}
