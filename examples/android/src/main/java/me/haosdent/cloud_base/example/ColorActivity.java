package me.haosdent.cloud_base.example;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;

import java.util.HashMap;
import java.util.Map;

public class ColorActivity extends PreferenceActivity {

  ColorFragment colorFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    colorFragment = new ColorFragment();
    getFragmentManager().beginTransaction()
        .replace(android.R.id.content, colorFragment).commit();
  }

  public ColorFragment getColorFragment() {
    return colorFragment;
  }

  public static class ColorFragment extends PreferenceFragment {
    PreferenceCategory colorStatPreferenceGroup;
    Map<Integer, ColorStatPreference> colorStatPreferenceMap =
        new HashMap<Integer, ColorStatPreference>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.main);
      colorStatPreferenceGroup =
          (PreferenceCategory) getPreferenceScreen().getPreference(1);
    }

    public void likeColor(int color) {
      ColorStatPreference preference = colorStatPreferenceMap.get(color);
      if (preference == null) {
        preference = new ColorStatPreference(getActivity(), color);
        colorStatPreferenceGroup.addPreference(preference);
        colorStatPreferenceMap.put(color, preference);
      }

      preference.like();
    }
  }
}
