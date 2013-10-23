package me.haosdent.cloud_hbase.sdk;

public class Req {
  public long rid;

  public Req(){
    this(System.currentTimeMillis());
  }

  public Req(long rid){
    this.rid = rid;
  }
}
