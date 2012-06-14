package com.commonsensenet.realfarm;

import android.app.ListActivity;
import android.os.Bundle;

public class Plot_test extends ListActivity {
int clickCounter=0;
public void onCreate(Bundle icicle) {
super.onCreate(icicle);


String[] values = new String[] { "AAA", "BBB", "CCC",
"DDD", "EEE", "FFF"};
int[] images = {
R.drawable.plot,
R.drawable.plot,
R.drawable.plot,
R.drawable.plot,
R.drawable.plot,
R.drawable.plot,
};
MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values, images);
setListAdapter(adapter);

}

}