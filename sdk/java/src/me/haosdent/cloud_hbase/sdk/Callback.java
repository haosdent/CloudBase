package me.haosdent.cloud_hbase.sdk;

import org.apache.commons.lang.NotImplementedException;

public class Callback implements Runnable {
  private Model model;

  public Callback(Model model) {
    this.model = model;
  }

  @Override
  public void run() {
    throw new NotImplementedException("To implement run method first.");
  }
}