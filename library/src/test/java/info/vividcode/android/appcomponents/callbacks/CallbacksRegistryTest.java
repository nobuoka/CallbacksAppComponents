package info.vividcode.android.appcomponents.callbacks;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class CallbacksRegistryTest {

    private interface TestCallbacks {}

    private TestCallbacks callbacks1 = new TestCallbacks() {};
    private TestCallbacks callbacks2 = new TestCallbacks() {};
    private TestCallbacks callbacks3 = new TestCallbacks() {};

    @Test
    public void register_simpleUsage() throws Exception {
        CallbacksRegistry<TestCallbacks> registry = new CallbacksRegistry<>();

        registry.register(callbacks1);
        registry.register(callbacks2);

        assertEquals("All two `TestCallbacks` objects returned",
                Arrays.asList(callbacks1, callbacks2),
                registry.getSnapshotInForwardOrder());

        registry.register(callbacks3);

        assertEquals("Newly registered `TestCallbacks` object included.",
                Arrays.asList(callbacks1, callbacks2, callbacks3),
                registry.getSnapshotInForwardOrder());
    }

    @Test
    public void register_multipleTimes() throws Exception {
        CallbacksRegistry<TestCallbacks> registry = new CallbacksRegistry<>();

        registry.register(callbacks1);
        registry.register(callbacks2);
        registry.register(callbacks1); // Already registered

        assertEquals("Same object cannot appear repeatedly.",
                Arrays.asList(callbacks1, callbacks2),
                registry.getSnapshotInForwardOrder());
    }

    @Test
    public void unregister_simpleUsage() throws Exception {
        CallbacksRegistry<TestCallbacks> registry = new CallbacksRegistry<>();

        registry.register(callbacks1);
        registry.register(callbacks2);
        registry.register(callbacks3);

        assertEquals("All registered `TestCallbacks` objects returned.",
                Arrays.asList(callbacks1, callbacks2, callbacks3),
                registry.getSnapshotInForwardOrder());

        registry.unregister(callbacks1);

        assertEquals("Unregistered `TestCallbacks` object not included.",
                Arrays.asList(callbacks2, callbacks3),
                registry.getSnapshotInForwardOrder());

        registry.unregister(callbacks3);

        assertEquals("Unregistered `TestCallbacks` object not included.",
                Collections.singletonList(callbacks2),
                registry.getSnapshotInForwardOrder());
    }

    @Test
    public void unregister_notRegisteredItem() throws Exception {
        CallbacksRegistry<TestCallbacks> registry = new CallbacksRegistry<>();

        registry.register(callbacks1);
        registry.register(callbacks2);

        assertEquals("All two `TestCallbacks` objects returned",
                Arrays.asList(callbacks1, callbacks2),
                registry.getSnapshotInForwardOrder());

        registry.unregister(callbacks3); // Not registered item

        assertEquals("To unregister not registered item has no effect.",
                Arrays.asList(callbacks1, callbacks2),
                registry.getSnapshotInForwardOrder());
    }

    @Test
    public void getSnapshotInForwardOrder_isSnapshot() throws Exception {
        CallbacksRegistry<TestCallbacks> registry = new CallbacksRegistry<>();

        registry.register(callbacks1);
        registry.register(callbacks2);

        List<TestCallbacks> snapshot = registry.getSnapshotInForwardOrder();
        for (TestCallbacks c : snapshot) {
            registry.unregister(c);
        }

        assertEquals("It's snapshot, so `TestCallbacks` objects remain.",
                Arrays.asList(callbacks1, callbacks2),
                snapshot);

        assertEquals("Newly taken snapshot is empty",
                Collections.emptyList(),
                registry.getSnapshotInForwardOrder());
    }

    @Test
    public void getSnapshotInForwardOrder_isForwardOrder() throws Exception {
        CallbacksRegistry<TestCallbacks> registry = new CallbacksRegistry<>();

        List<TestCallbacks> callbacksList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            callbacksList.add(new TestCallbacks() {});
        }

        for (TestCallbacks c : callbacksList) {
            registry.register(c);
        }

        assertEquals("Entries in snapshot are arranged in the order of their registration.",
                callbacksList,
                registry.getSnapshotInForwardOrder());
    }

    @Test
    public void getSnapshotInReverseOrder_isSnapshot() throws Exception {
        CallbacksRegistry<TestCallbacks> registry = new CallbacksRegistry<>();

        registry.register(callbacks1);
        registry.register(callbacks2);

        List<TestCallbacks> snapshot = registry.getSnapshotInReverseOrder();
        for (TestCallbacks c : snapshot) {
            registry.unregister(c);
        }

        assertEquals("It's snapshot, so `TestCallbacks` objects remain.",
                Arrays.asList(callbacks2, callbacks1),
                snapshot);

        assertEquals("Newly taken snapshot is empty",
                Collections.emptyList(),
                registry.getSnapshotInReverseOrder());
    }

    @Test
    public void getSnapshotInReverseOrder_isReverseOrder() throws Exception {
        CallbacksRegistry<TestCallbacks> registry = new CallbacksRegistry<>();

        List<TestCallbacks> callbacksList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            callbacksList.add(new TestCallbacks() {});
        }

        for (TestCallbacks c : callbacksList) {
            registry.register(c);
        }

        List<TestCallbacks> callbacksListInReverseOrder = new ArrayList<>(callbacksList);
        Collections.reverse(callbacksListInReverseOrder);

        assertEquals("Entries in snapshot are arranged in the reverse order of their registration.",
                callbacksListInReverseOrder,
                registry.getSnapshotInReverseOrder());
    }

}
