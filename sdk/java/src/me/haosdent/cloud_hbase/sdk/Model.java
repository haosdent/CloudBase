package me.haosdent.cloud_hbase.sdk;

import java.util.concurrent.Callable;

public class Model implements Listenable {

  private String gid;
  private String name;
  private Callable cb;

  @Override
  public String getGid() {
    return gid;
  }

  @Override
  public void run(String cmd, Resp resp) {
    //TODO
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

  public void update(){
    //TODO
  }

  public void error(){
    //TODO
  }

  public void success(){
    //TODO
  }
}
