CallbacksAppComponents
==============================

This library provides application components (currently, Activity and Fragment) which can retain
objects to receive callback method calls (e.g. `onCreate` method call, `onStart` method call, etc.).

## YOU CAN NOW HANDLE LIFECYCLES WITH LIFECYCLE-AWARE COMPONENTS

You can now handle Lifecycles with Lifecycle-Aware Components instead of this library.

See : [Handling Lifecycles with Lifecycle-Aware Components | Android Developers](https://developer.android.com/topic/libraries/architecture/lifecycle.html)

## Usage

First, you define a class which implements `CallbacksActivity.Callbacks` interface.

```java
public class ActivityCallbacksExampleManager extends CallbacksActivity.SimpleCallbacks {
    @Override
    public void onCreate(CallbacksActivity activity, Bundle savedInstanceState) {
        // Do something.
        // This method is called when Activity's `onCreate` method is called.
    }
    // You can define other callback methods. (e.g. `onStart` method, `onDestroy` method, etc.)
}
```

Then, you can register it with `CallbacksActivity` object.

```java
public class ExampleActivity extends CallbacksActivity {
    {
        registerCallbacks(new ActivityCallbacksExampleManager());
    }
}
```

## License

Apache License, Version 2.0
