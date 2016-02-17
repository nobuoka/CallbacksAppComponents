/*
 * Copyright 2016 NOBUOKA Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.vividcode.android.appcomponents.callbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;

public class CallbacksActivity extends AppCompatActivity {

    public interface Callbacks {
        void onCreate(CallbacksActivity activity, Bundle savedInstanceState);
        void onDestroy(CallbacksActivity activity);
        void onStart(CallbacksActivity activity);
        void onStop(CallbacksActivity activity);
        void onRestart(CallbacksActivity activity);
        void onResume(CallbacksActivity activity);
        void onPause(CallbacksActivity activity);

        /**
         * {@link android.app.Activity#onActivityResult(int, int, Intent)} 呼び出し時に実行される処理。
         * @return 真を返すとそれ以降の {@link android.app.Activity#onActivityResult(int, int, Intent)}
         *         の処理が止まる。 親クラスのメソッド呼び出しも行われない。 偽であれば処理が続けられる。
         */
        boolean onActivityResult(CallbacksActivity activity, int requestCode, int resultCode, Intent data);
    }

    public static class SimpleCallbacks implements Callbacks {
        public void onCreate(CallbacksActivity activity, Bundle savedInstanceState) {}
        public void onDestroy(CallbacksActivity activity) {}
        public void onStart(CallbacksActivity activity) {}
        public void onStop(CallbacksActivity activity) {}
        public void onRestart(CallbacksActivity activity) {}
        public void onResume(CallbacksActivity activity) {}
        public void onPause(CallbacksActivity activity) {}
        public boolean onActivityResult(CallbacksActivity activity, int requestCode, int resultCode, Intent data) {
            return false;
        }
    }

    private final CallbacksRegistry<Callbacks> mCallbacksRegistry = new CallbacksRegistry<>();

    public void registerCallbacks(Callbacks c) {
        mCallbacksRegistry.register(c);
    }

    public void unregisterCallbacks(Callbacks c) {
        mCallbacksRegistry.unregister(c);
    }

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (Callbacks c : mCallbacksRegistry.getSnapshotInForwardOrder()) c.onCreate(this, savedInstanceState);
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        for (Callbacks c : mCallbacksRegistry.getSnapshotInReverseOrder()) c.onDestroy(this);
        super.onDestroy();
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        for (Callbacks c : mCallbacksRegistry.getSnapshotInForwardOrder()) c.onStart(this);
    }

    @Override
    @CallSuper
    protected void onStop() {
        for (Callbacks c : mCallbacksRegistry.getSnapshotInReverseOrder()) c.onStop(this);
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onRestart() {
        super.onRestart();
        for (Callbacks c : mCallbacksRegistry.getSnapshotInForwardOrder()) c.onRestart(this);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        for (Callbacks c : mCallbacksRegistry.getSnapshotInForwardOrder()) c.onResume(this);
    }

    @Override
    @CallSuper
    protected void onPause() {
        for (Callbacks c : mCallbacksRegistry.getSnapshotInReverseOrder()) c.onPause(this);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Callbacks c : mCallbacksRegistry.getSnapshotInReverseOrder()) {
            final boolean toBeFinished = c.onActivityResult(this, requestCode, resultCode, data);
            if (toBeFinished) return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
