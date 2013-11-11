package me.haosdent.cloud_hbase.sdk;

import static me.haosdent.cloud_hbase.sdk.Constants.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Model implements Listenable {

  private String gid;
  private String id;
  private String name;
  private long version;
  private JSONObject data;
  private Callback cb;
  private Syncer syncer;
  private App app;

  public Model(App app, String id, String name, Callback cb) {
    this.name = name;
    this.id = id;
    this.cb = cb;
    this.app = app;

    this.gid = app.getName() + this.name + this.id;
    this.syncer = new Syncer(this);
    this.on();
  }

  @Override
  public String getGid() {
    return gid;
  }

  public JSONObject getData() {
    return data;
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

  public void save() {
    Req req = putReq();
    Socket.send(req, true);
  }

  public void on() {
    this.up();
    syncer.start();
  }

  public void up() {
    Thread.State state = syncer.getState();
    if (state != Thread.State.NEW && state != Thread.State.TERMINATED)
      syncer.interrupt();
  }

  public void update(Resp resp) {
    if (resp.version == 0)
      return;
    this.version = resp.version;
    this.data = JSON.parseObject(resp.dataStr);
    this.cb.run();
  }

  public void error(Resp resp) {
  }

  public void success(Resp resp) {
  }

  public Req getReq() {
    Req req = new Req(ACT_GET, app.getName(), id, gid, version);
    return req;
  }

  public Req putReq() {
    Req req = new Req(ACT_PUT, app.getName(), id, gid, version,
            JSON.toJSONString(data));
    return req;
  }

  public static class Syncer extends Thread {
    private Model model;

    public Syncer(Model model) {
      this.model = model;
    }

    @Override
    public void run() {
      while (true) {
        Req req = model.getReq();
        Socket.send(req, false);
        try {
          sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
