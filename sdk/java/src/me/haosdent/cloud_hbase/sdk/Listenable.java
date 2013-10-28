package me.haosdent.cloud_hbase.sdk;

public interface Listenable {

  public String gid = null;

  public String getGid();
  public void run(String cmd, Resp resp);
}
