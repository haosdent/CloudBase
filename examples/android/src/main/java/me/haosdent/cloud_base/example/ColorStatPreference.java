package me.haosdent.cloud_base.example;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ColorStatPreference extends Preference {

  private ColorStatView colorView;
  private int color;
  private int count = 0;

  public ColorStatPreference(Context context, int color) {
    super(context);
    this.color = color;
    setWidgetLayoutResource(R.layout.preference_color_stat);
    setTitle("0");
  }

  @Override
  protected void onBindView(View view) {
    super.onBindView(view);
    colorView = (ColorStatView) view.findViewById(R.id.color_preview);
    colorView.setColor(color);
  }

  @Override
  protected void onClick() {
    super.onClick();
    like();
  }

  public void like() {
    count++;
    setTitle(count + "");
  }
}
