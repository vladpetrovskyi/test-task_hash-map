package hashmaptest;

import hashmap.MyHashMap;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class HashMapTest {

    private static MyHashMap myHashMap;

    @BeforeEach
    void setUp() {
        myHashMap = new MyHashMap();
    }

    @Test
    void putAndGetOk() {
        myHashMap.put(-3, 1);
        myHashMap.put(0, 2);
        myHashMap.put(1, 3);

        Assertions.assertEquals(1, myHashMap.get(-3));
        Assertions.assertEquals(2, myHashMap.get(0));
        Assertions.assertEquals(3, myHashMap.get(1));
    }

    @Test
    void getByNonExistentKey() {
        Assertions.assertEquals(-1, myHashMap.get(4));
    }

    @Test
    void putAndGetWithSameKey() {
        myHashMap.put(1, 1);
        myHashMap.put(2, 2);
        myHashMap.put(3, 3);
        myHashMap.put(1, 1);
        myHashMap.put(2, 4);

        Assertions.assertEquals(1, myHashMap.get(1));
        Assertions.assertEquals(4, myHashMap.get(2));
        Assertions.assertEquals(3, myHashMap.get(3));

        Assertions.assertEquals(3, myHashMap.size());
    }

    @Test
    void putAndGetWithCollisionWithoutResize() {
        myHashMap.put(17, 1);
        myHashMap.put(15, 2);
        myHashMap.put(33, 3);

        Assertions.assertEquals(1, myHashMap.get(17));
        Assertions.assertEquals(3, myHashMap.get(33));
        Assertions.assertEquals(2, myHashMap.get(15));
    }

    @ParameterizedTest
    @MethodSource(value = "hashMapProvider")
    void getSize_Ok(MyHashMap myHashMap) {
        for (int i = 0; i < 5; i++) {
            myHashMap.put(i, i);
        }

        Assertions.assertEquals(5, myHashMap.size());
    }

    @ParameterizedTest
    @MethodSource(value = "hashMapProvider")
    public void getSizeOfEmptyHashMap(MyHashMap myHashMap) {
        Assertions.assertEquals(0, myHashMap.size());
    }

    @ParameterizedTest
    @MethodSource(value = "hashMapProvider")
    void resizeWhenQuantityExceedsAvailableSpace(MyHashMap myHashMap) throws IllegalAccessException {
        for (int i = 0; i < 13; i++) {
            myHashMap.put(i, i * 2);
        }

        for (int i = 0; i < 13; i++) {
            Assertions.assertEquals(i * 2, myHashMap.get(i));
        }

        Field[] declaredFields = myHashMap.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getType().isArray()) {
                field.setAccessible(true);
                Assertions.assertEquals(32, Array.getLength(field.get(myHashMap)));
            }
        }
    }

    @ParameterizedTest
    @MethodSource(value = "hashMapProvider")
    void checkCapacityIncreaseWithMultipleCollisions(MyHashMap myHashMap) {
        for (int i = 0; i < 1000; i++) {
            myHashMap.put(i, i * 2);
        }

        Assertions.assertEquals(1000, myHashMap.size());
        for (int i = 0; i < 1000; i++) {
            Assertions.assertEquals(i * 2, myHashMap.get(i));
        }
    }

    @ParameterizedTest
    @MethodSource(value = "hashMapProvider")
    void checkResize_MultipleCollisions_AlmostEmptyArray(MyHashMap myHashMap) {
        myHashMap.put(15, 1);
        myHashMap.put(31, 2);
        myHashMap.put(47, 3);
        myHashMap.put(63, 4);

        Assertions.assertEquals(1, myHashMap.get(15));
        Assertions.assertEquals(2, myHashMap.get(31));
        Assertions.assertEquals(3, myHashMap.get(47));
        Assertions.assertEquals(4, myHashMap.get(63));
    }

    static Stream<MyHashMap> hashMapProvider(){
        return Stream.of(new MyHashMap(), new MyHashMap(4), new MyHashMap(8, 0.5f));
    }
}
