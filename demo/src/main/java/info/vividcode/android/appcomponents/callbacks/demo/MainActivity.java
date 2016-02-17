package info.vividcode.android.appcomponents.callbacks.demo;

import info.vividcode.android.appcomponents.callbacks.CallbacksActivity;

public class MainActivity extends CallbacksActivity {

    {
        // This *callbacks* object manages this Activity's view.
        registerCallbacks(new ActivityCallbacksMainActivityViewManager());
    }

}
