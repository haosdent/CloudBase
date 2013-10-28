package me.haosdent.cloud_hbase.sdk;

import java.util.concurrent.Callable;

public class App implements Listenable {

  public final static String SUCCESS = "success";
  public final static String UPDATE = "update";
  public final static String ERROR = "error";

  private String name;
  private String gid;

  public App(String name){
    this.name = name;
    this.gid = name;
    Socket.getInstance().register(this.name, this);
  }

  @Override
  public String getGid(){
    return gid;
  }

  @Override
  public void run(String cmd, Resp resp){
    if (cmd.equals(SUCCESS))
      success(resp);
    else if (cmd.equals(ERROR))
      error(resp);
    else if (cmd.equals(UPDATE))
      update(resp);
  }

  public void create(String id, String name, Callable cb){
    //TODO
  }

  public void update(Resp resp){
    System.out.println("Create " + this.name + " successfullly!");
  }

  public void error(Resp resp){
    //TODO
  }

  public void success(Resp resp){
    //TODO
  }
}
