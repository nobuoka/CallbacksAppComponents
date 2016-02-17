package info.vividcode.android.appcomponents.callbacks;

import android.content.Intent;
import android.os.Bundle;

import java.util.Queue;

class TestActivityCallbacks implements CallbacksActivity.Callbacks {

    private final Queue<String> mQueue;
    private final String mSuffix;

    public TestActivityCallbacks(Queue<String> queue, String suffix) {
        mQueue = queue;
        mSuffix = suffix;
    }

    @Override
    public void onCreate(CallbacksActivity activity, Bundle savedInstanceState) {
        mQueue.add("onCreate" + mSuffix);
    }

    @Override
    public void onDestroy(CallbacksActivity activity) {
        mQueue.add("onDestroy" + mSuffix);
    }

    @Override
    public void onStart(CallbacksActivity activity) {
        mQueue.add("onStart" + mSuffix);
    }

    @Override
    public void onStop(CallbacksActivity activity) {
        mQueue.add("onStop" + mSuffix);
    }

    @Override
    public void onRestart(CallbacksActivity activity) {
        mQueue.add("onRestart" + mSuffix);
    }

    @Override
    public void onResume(CallbacksActivity activity) {
        mQueue.add("onResume" + mSuffix);
    }

    @Override
    public void onPause(CallbacksActivity activity) {
        mQueue.add("onPause" + mSuffix);
    }

    @Override
    public boolean onActivityResult(CallbacksActivity activity, int requestCode, int resultCode, Intent data) {
        mQueue.add("onActivityResult" + mSuffix);
        return false;
    }

}
