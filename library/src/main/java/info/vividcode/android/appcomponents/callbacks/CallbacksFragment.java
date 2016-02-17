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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CallbacksFragment extends Fragment {

    public interface Callbacks {
        void onAttach(CallbacksFragment fragment, Context context);
        void onDetach(CallbacksFragment fragment);

        void onCreate(CallbacksFragment fragment, Bundle savedInstanceState);
        void onDestroy(CallbacksFragment fragment);

        @Nullable
        View onCreateView(CallbacksFragment fragment, LayoutInflater inflater,
                          @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
        void onDestroyView(CallbacksFragment fragment);

        void onActivityCreated(CallbacksFragment fragment, Bundle savedInstanceState);

        void onStart(CallbacksFragment fragment);
        void onStop(CallbacksFragment fragment);

        void onResume(CallbacksFragment fragment);
        void onPause(CallbacksFragment fragment);

        /**
         * {@link Fragment#onActivityResult(int, int, Intent)} 呼び出し時に実行される処理。
         * @return 真を返すとそれ以降の {@link Fragment#onActivityResult(int, int, Intent)}
         *         の処理が止まる。 親クラスのメソッド呼び出しも行われない。 偽であれば処理が続けられる。
         */
        boolean onActivityResult(CallbacksFragment fragment, int requestCode, int resultCode, Intent data);
    }

    public static class SimpleCallbacks implements Callbacks {
        public void onAttach(CallbacksFragment fragment, Context context) {}
        public void onDetach(CallbacksFragment fragment) {}
        public void onCreate(CallbacksFragment fragment, Bundle savedInstanceState) {}
        public void onDestroy(CallbacksFragment fragment) {}
        @Nullable
        public View onCreateView(CallbacksFragment fragment, LayoutInflater inflater,
                                 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return null;
        }
        public void onDestroyView(CallbacksFragment fragment) {}
        public void onActivityCreated(CallbacksFragment fragment, Bundle savedInstanceState) {}
        public void onStart(CallbacksFragment fragment) {}
        public void onStop(CallbacksFragment fragment) {}
        public void onResume(CallbacksFragment fragment) {}
        public void onPause(CallbacksFragment fragment) {}
        public boolean onActivityResult(CallbacksFragment fragment, int requestCode, int resultCode, Intent data) {
            return false;
        }
    }

    private final CallbacksRegistry<Callbacks> mCallbacksRegistry = new CallbacksRegistry<>();

    public void registerCallbacks(Callbacks c) {
        mCallbacksRegistry.register(c);
    }

    public void unregisterCallbacks(final Callbacks c) {
        mCallbacksRegistry.unregister(c);
    }

    @Override
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);
        for (Callbacks c : mCallbacksRegistry.getSnapshotInForwardOrder()) c.onAttach(this, context);
    }

    @Override
    @CallSuper
    public void onDetach() {
        for (Callbacks c : mCallbacksRegistry.getSnapshotInReverseOrder()) c.onDetach(this);
        super.onDetach();
    }

    @Override
    @CallSuper
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (Callbacks c : mCallbacksRegistry.getSnapshotInForwardOrder()) c.onCreate(this, savedInstanceState);
    }

    @Override
    @CallSuper
    public void onDestroy() {
        for (Callbacks c : mCallbacksRegistry.getSnapshotInReverseOrder()) c.onDestroy(this);
        super.onDestroy();
    }

    @Override
    @CallSuper
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        for (Callbacks c : mCallbacksRegistry.getSnapshotInForwardOrder()) {
            View v = c.onCreateView(this, inflater, container, savedInstanceState);
            if (v != null) {
                if (view == null) view = v;
                else throw new IllegalStateException("Although view was already created, another view also be created.");
            }
        }
        return view;
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        for (Callbacks c : mCallbacksRegistry.getSnapshotInReverseOrder()) c.onDestroyView(this);
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (Callbacks c : mCallbacksRegistry.getSnapshotInForwardOrder()) c.onActivityCreated(this, savedInstanceState);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        for (Callbacks c : mCallbacksRegistry.getSnapshotInForwardOrder()) c.onStart(this);
    }

    @Override
    @CallSuper
    public void onStop() {
        for (Callbacks c : mCallbacksRegistry.getSnapshotInReverseOrder()) c.onStop(this);
        super.onStop();
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        for (Callbacks c : mCallbacksRegistry.getSnapshotInForwardOrder()) c.onResume(this);
    }

    @Override
    @CallSuper
    public void onPause() {
        for (Callbacks c : mCallbacksRegistry.getSnapshotInReverseOrder()) c.onPause(this);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Callbacks c : mCallbacksRegistry.getSnapshotInReverseOrder()) {
            boolean toBeFinished = c.onActivityResult(this, requestCode, resultCode, data);
            if (toBeFinished) return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
