package me.haosdent.cloud_hbase.sdk;

public class Req {
  public long rid;
  public String act;
  public String app;
  public String id;
  public String gid;
  public long version;
  public String data;

  public Req(String act, String app, String id, String gid, long version) {
    this(act, app, id, gid, version, null);
  }

  public Req(String act, String app, String id, String gid, long version,
      String data) {
    this.rid = System.currentTimeMillis();
    this.act = act;
    this.app = app;
    this.id = id;
    this.gid = gid;
    this.version = version;
    this.data = data;
  }
}
