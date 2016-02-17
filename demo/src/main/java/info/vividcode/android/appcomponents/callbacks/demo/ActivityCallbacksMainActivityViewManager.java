package info.vividcode.android.appcomponents.callbacks.demo;

import android.os.Bundle;

import info.vividcode.android.appcomponents.callbacks.CallbacksActivity;

public class ActivityCallbacksMainActivityViewManager extends CallbacksActivity.SimpleCallbacks {

    @Override
    public void onCreate(CallbacksActivity activity, Bundle savedInstanceState) {
        activity.setContentView(R.layout.activity_main);
    }

}
