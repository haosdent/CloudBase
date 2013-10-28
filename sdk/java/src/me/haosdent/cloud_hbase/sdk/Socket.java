package me.haosdent.cloud_hbase.sdk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;

import de.tavendo.autobahn.AutobahnConnection;
import de.tavendo.autobahn.WebSocketHandler;

public class Socket {

  public final static String ERROR = "error";
  public final static String SUCCESS = "success";
  private AutobahnConnection entity;
  private Map<String, Listenable> listeners =
      new ConcurrentHashMap<String, Listenable>();

  public Socket(String url) {
    entity = new AutobahnConnection();
    try {
      entity.connect(url, new Handler(this));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void send(Req req, boolean isForce) {
    entity.sendTextMessage(JSON.toJSONString(req));
  }

  public void receive(Resp resp) {
    String cmd = resp.cmd;
    String gid = resp.gid;
    if (cmd.equals(ERROR))
      this.error(resp);
    else if (cmd.equals(SUCCESS))
      this.success(resp);
    Listenable listener = listeners.get(gid);
  }

  public void register(String gid, Listenable listener) {
    listeners.put(gid, listener);
  }

  public void error(Resp resp) {
    System.err.println("Rid: " + resp.rid + ", Obj: " + resp.gid + ", Act: "
        + resp.act + ", Result: error!");
  }

  public void success(Resp resp) {
    System.out.println("Rid: " + resp.rid + ", Obj: " + resp.gid + ", Act: "
        + resp.act + ", Result: success!");
  }

  public static class Handler extends WebSocketHandler {
    public Socket socket;

    public Handler(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void onTextMessage(String respStr) {
      super.onTextMessage(respStr);
      socket.receive(JSON.parseObject(respStr, Resp.class));
    }
  }
}
