package info.vividcode.android.appcomponents.callbacks;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.common.base.Joiner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CallbacksActivityTest {

    private ConcurrentLinkedQueue<String> mTestQueue;

    @Rule
    public TestRule uiThreadTestRule = new UiThreadTestRule();

    @Before
    public void setupTestQueue() {
        mTestQueue = new ConcurrentLinkedQueue<>();
    }

    @Test
    @UiThreadTest
    public void test_callbacks() throws Throwable {
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        final CallbacksActivity activity = TestUtils.createActivityForUnitTest(CallbacksActivity.class);
        activity.setTheme(info.vividcode.android.appcomponents.callbacks.test.R.style.AppTheme);

        activity.registerCallbacks(new TestActivityCallbacks(mTestQueue, "1"));
        activity.registerCallbacks(new TestActivityCallbacks(mTestQueue, "2"));

        instrumentation.callActivityOnCreate(activity, null);
        assertEquals("`onCreate` methods are called in the order of callbacks objects' registration.",
                "onCreate1,onCreate2", Joiner.on(",").join(mTestQueue));

        instrumentation.callActivityOnStart(activity);
        assertEquals("`onStart` methods are called in the order of callbacks objects' registration.",
                "onCreate1,onCreate2,onStart1,onStart2", Joiner.on(",").join(mTestQueue));

        instrumentation.callActivityOnResume(activity);
        assertEquals("`onResume` methods are called in the order of callbacks objects' registration.",
                "onCreate1,onCreate2,onStart1,onStart2,onResume1,onResume2", Joiner.on(",").join(mTestQueue));

        mTestQueue.clear();

        instrumentation.callActivityOnPause(activity);
        assertEquals("`onPause` methods are called in the reverse order of callbacks objects' registration.",
                "onPause2,onPause1", Joiner.on(",").join(mTestQueue));

        instrumentation.callActivityOnStop(activity);
        assertEquals("`onStop` methods are called in the reverse order of callbacks objects' registration.",
                "onPause2,onPause1,onStop2,onStop1", Joiner.on(",").join(mTestQueue));

        instrumentation.callActivityOnDestroy(activity);
        assertEquals("`onDestroy` methods are called in the reverse order of callbacks objects' registration.",
                "onPause2,onPause1,onStop2,onStop1,onDestroy2,onDestroy1", Joiner.on(",").join(mTestQueue));
    }

    @Test
    @UiThreadTest
    public void test_unregister() throws Throwable {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        final CallbacksActivity activity = TestUtils.createActivityForUnitTest(CallbacksActivity.class);
        activity.setTheme(info.vividcode.android.appcomponents.callbacks.test.R.style.AppTheme);

        CallbacksActivity.Callbacks callbacks1 = new TestActivityCallbacks(mTestQueue, "1");
        activity.registerCallbacks(callbacks1);
        activity.registerCallbacks(new TestActivityCallbacks(mTestQueue, "2"));

        instrumentation.callActivityOnCreate(activity, null);
        assertEquals("`onCreate` methods of registered two objects are called.",
                "onCreate1,onCreate2", Joiner.on(",").join(mTestQueue));

        // Unregister one object.
        activity.unregisterCallbacks(callbacks1);

        instrumentation.callActivityOnStart(activity);
        assertEquals("",
                "onCreate1,onCreate2,onStart2", Joiner.on(",").join(mTestQueue));
    }

}
