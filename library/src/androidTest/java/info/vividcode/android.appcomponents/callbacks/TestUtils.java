package info.vividcode.android.appcomponents.callbacks;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.test.mock.MockApplication;

public class TestUtils {

    public static <T extends Activity> T createActivityForUnitTest(Class<T> activityClass) {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        Application application = new MockApplication();
        Intent intent = new Intent();
        ComponentName cn = new ComponentName(BuildConfig.APPLICATION_ID, activityClass.getName());
        intent.setComponent(cn);
        ActivityInfo info = new ActivityInfo();
        CharSequence title = activityClass.getName();
        try {
            Activity activity = instrumentation.newActivity(
                    activityClass, instrumentation.getContext(), null, application,
                    intent, info, title, null, null, null);
            return activityClass.cast(activity);
        } catch (Exception /* InstantiationException | IllegalAccessException */ e) {
            throw new RuntimeException(e);
        }
    }

}
