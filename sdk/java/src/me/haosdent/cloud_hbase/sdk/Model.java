package me.haosdent.cloud_hbase.sdk;

import java.util.concurrent.Callable;

import static me.haosdent.cloud_hbase.sdk.Constants.*;

public class Model implements Listenable {

  public static class Updater extends Thread {
    private Model model;
    private Socket socket = Socket.getInstance();

    public Updater(Model model){
      this.model = model;
    }

    @Override
    public void run(){
      while(true){
        Req req = model.getReq();
        socket.send(req, false);
        try {
          sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private String gid;
  private String id;
  private String name;
  private long version;
  private Callable cb;
  private Updater updater;

  public Model(App app, String id, String name, Callable cb) {
    this.name = name;
    this.id = id;
    this.cb = cb;

    this.gid = app.getName() + this.name + this.id;
    this.updater = new Updater(this);
    this.on();
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
    Req req = new Req();
  }

  public void on(){
    this.up();
    updater.start();
  }

  public void up(){
    updater.interrupt();
  }

  public void update(Resp resp){
    //TODO
    if (resp.version == 0)
      return;

    try {
      this.cb.call();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void error(Resp resp){
  }

  public void success(Resp resp){
  }

  public Req getReq(){
    //TODO
    return null;
  }
}
