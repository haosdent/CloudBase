package me.haosdent.cloud_hbase.sdk;

import static me.haosdent.cloud_hbase.sdk.Constants.ERROR;
import static me.haosdent.cloud_hbase.sdk.Constants.SUCCESS;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;

import de.tavendo.autobahn.AutobahnConnection;
import de.tavendo.autobahn.WebSocketHandler;

public class Socket {

  private final static String url;
  private static AutobahnConnection entity;
  private static Map<String, Listenable> listeners =
      new ConcurrentHashMap<String, Listenable>();

  static {
    entity = new AutobahnConnection();
    // FIXME
    url = "";
    try {
      entity.connect(url, new Handler());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void send(Req req, boolean isForce) {
    // FIXME
    if (!isForce)
      entity.sendTextMessage(JSON.toJSONString(req));
    else
      return;
  }

  public static void receive(Resp resp) {
    String cmd = resp.cmd;
    String gid = resp.gid;
    if (cmd.equals(ERROR))
      error(resp);
    else if (cmd.equals(SUCCESS))
      success(resp);
    Listenable listener = listeners.get(gid);
    listener.run(resp.cmd, resp);
  }

  public static void register(String gid, Listenable listener) {
    listeners.put(gid, listener);
  }

  public static void error(Resp resp) {
    System.err.println("Rid: " + resp.rid + ", Obj: " + resp.gid + ", Act: "
        + resp.act + ", Result: error!");
  }

  public static void success(Resp resp) {
    System.out.println("Rid: " + resp.rid + ", Obj: " + resp.gid + ", Act: "
        + resp.act + ", Result: success!");
  }

  public static class Handler extends WebSocketHandler {
    public Handler() {
    }

    @Override
    public void onTextMessage(String respStr) {
      super.onTextMessage(respStr);
      Socket.receive(JSON.parseObject(respStr, Resp.class));
    }
  }
}
