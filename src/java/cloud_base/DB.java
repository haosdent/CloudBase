package cloud_base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.PoolMap;

public class DB {

  private final static int HPOOL_SIZE = 50;
  private final static String FAMILY_NAME = "cf";
  private final static byte[] FAMILY_NAME_BYTES = Bytes.toBytes(FAMILY_NAME);
  private final static String KEY_VERSION = "version";
  private final static String KEY_DATA = "data";
  private final static byte[] KEY_DATA_BYTES = Bytes.toBytes(KEY_DATA);

  private static Configuration conf = HBaseConfiguration.create();
  private static HBaseAdmin admin;
  private static HTablePool putPool = new HTablePool(conf, HPOOL_SIZE,
      PoolMap.PoolType.ThreadLocal);
  private static HTablePool getPool = new HTablePool(conf, HPOOL_SIZE,
      PoolMap.PoolType.ThreadLocal);

  static {
    init();
  }

  public static void init() {
    conf.setInt("hbase.zookeeper.property.clientPort", 40060);
    conf.set("hbase.zookeeper.quorum", "10.232.98.94,10.232.98.72,10.232.98.40");
    conf.set("zookeeper.znode.parent", "/hbase-cdh4");
    try {
      admin = new HBaseAdmin(conf);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static boolean create(String table) {
    HTableDescriptor tableDesc = new HTableDescriptor(table);
    HColumnDescriptor colDesc = new HColumnDescriptor(FAMILY_NAME);
    tableDesc.addFamily(colDesc);
    try {
      if (!admin.tableExists(table))
        admin.createTable(tableDesc);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

  public static boolean put(String table, String row, long version, String data) {
    Put put = new Put(Bytes.toBytes(row));
    put.add(FAMILY_NAME_BYTES, KEY_DATA_BYTES, version, Bytes.toBytes(data));
    HTableInterface htable = putPool.getTable(table);
    try {
      htable.put(put);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        htable.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  public static Map<String, Object> get(String table, String row, long version) {
    Get get = new Get(Bytes.toBytes(row));
    HTableInterface htable = getPool.getTable(table);
    Map<String, Object> obj = new HashMap<String, Object>();
    try {
      get.setTimeRange(version + 1, Long.MAX_VALUE);
      get.setMaxVersions(1);
      Result r = htable.get(get);
      byte[] dataBytes = r.getValue(FAMILY_NAME_BYTES, KEY_DATA_BYTES);
      if (dataBytes != null) {
        String data = Bytes.toString(dataBytes);
        version = r.raw()[0].getTimestamp();
        obj.put(KEY_VERSION, version);
        obj.put(KEY_DATA, data);
      } else if (version == 0) {
        put(table, row, System.currentTimeMillis(), "{}");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        htable.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return obj;
  }
}
