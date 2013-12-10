package me.haosdent.cloud_base.example;

import afzkl.development.colorpickerview.preference.ColorPickerPreference;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.Toast;

public class ColorPickerDialog extends ColorPickerPreference {

  public ColorPickerDialog(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ColorPickerDialog(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public void onClick(DialogInterface dialog, int which) {
    switch (which) {
      case DialogInterface.BUTTON_POSITIVE:
        Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
        break;
      case DialogInterface.BUTTON_NEGATIVE:
        Toast.makeText(getContext(), "Cancle", Toast.LENGTH_SHORT).show();
        break;
      default:
        Toast.makeText(getContext(), "None", Toast.LENGTH_SHORT).show();
        break;
    }
  }
}
