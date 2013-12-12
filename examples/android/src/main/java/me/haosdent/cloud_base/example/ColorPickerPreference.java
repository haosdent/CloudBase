package me.haosdent.cloud_base.example;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

public class ColorPickerPreference extends
    afzkl.development.colorpickerview.preference.ColorPickerPreference {

  private ColorActivity.ColorFragment colorFragment;

  public ColorPickerPreference(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public ColorPickerPreference(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context);
  }

  private void init(Context context) {
    colorFragment = ((ColorActivity) context).getColorFragment();
  }

  @Override
  protected void onDialogClosed(boolean positiveResult) {
    super.onDialogClosed(positiveResult);
    if (positiveResult) {
      colorFragment.likeColor(getPersistedInt(0xFF000000));
    }
  }
}
