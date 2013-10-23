package me.haosdent.cloud_hbase.sdk;

public class Resp {
  public long rid;
  public String gid;
  public String act;
  public String cmd;

  public Resp(long rid, String gid, String act, String cmd){
    this.rid = rid;
    this.gid = gid;
    this.act = act;
    this.cmd = cmd;
  }
}
