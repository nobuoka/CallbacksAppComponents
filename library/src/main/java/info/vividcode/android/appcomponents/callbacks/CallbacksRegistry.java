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

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Class to manage objects which represents callback methods.
 * This class retain objects in the order of their registration and can return a snapshot of
 * a list includes all objects.
 *
 * This class is thread-safe.
 *
 * @param <T> Class witch represents callback methods.
 */
class CallbacksRegistry<T> {

    private final LinkedHashSet<T> mCallbacksList = new LinkedHashSet<>();

    @Nullable
    private ArrayList<T> mSnapshotInForwardOrder = null;

    @Nullable
    private ArrayList<T> mSnapshotInReverseOrder = null;

    public synchronized void register(T callback) {
        mCallbacksList.add(callback);
        invalidateSnapshots();
    }

    public synchronized void unregister(T callback) {
        mCallbacksList.remove(callback);
        invalidateSnapshots();
    }

    private void invalidateSnapshots() {
        mSnapshotInForwardOrder = null;
        mSnapshotInReverseOrder = null;
    }

    public synchronized List<T> getSnapshotInForwardOrder() {
        if (mSnapshotInForwardOrder == null) {
            mSnapshotInForwardOrder = new ArrayList<>(mCallbacksList);
        }
        return mSnapshotInForwardOrder;
    }

    public synchronized List<T> getSnapshotInReverseOrder() {
        if (mSnapshotInReverseOrder == null) {
            mSnapshotInReverseOrder = new ArrayList<>(mCallbacksList);
            Collections.reverse(mSnapshotInReverseOrder);
        }
        return mSnapshotInReverseOrder;
    }

}
