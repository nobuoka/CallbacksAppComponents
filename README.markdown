CallbacksAppComponents
==============================

This library provides application components (currently, Activity and Fragment) which can retain
objects to receive callback method calls (e.g. `onCreate` method call, `onStart` method call, etc.).

## Usage

First, you define a class which implements `CallbacksActivity.Callbacks` interface.

```java
public class ActivityCallbacksExampleManager extends CallbacksActivity.SimpleCallbacks {
    @Override
    public void onCreate(CallbacksActivity activity, Bundle savedInstanceState) {
        // Do something.
        // This method is called when Activity's `onCreate` method is called.
    }
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
