package hashMapTest;

import hashMap.MyHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HashMapTest {

    private static MyHashMap myHashMap;

    @BeforeEach
    void setUp() {
        myHashMap = new MyHashMap();
    }

    @Test
    void putAndGet_Positives_Ok() {
        myHashMap.put(1, 2);
        myHashMap.put(2019824, 234);

        Assertions.assertEquals(234, myHashMap.get(2019824));
        Assertions.assertEquals(2, myHashMap.get(1));
    }

    @Test
    void putAndGet_Negatives_Ok() {
        myHashMap.put(-2142, -12312);

        Assertions.assertEquals(-12312, myHashMap.get(-2142));
    }

    @Test
    void putAndGet_PositiveKey_NegativeValue_Ok() {
        myHashMap.put(124241, -241214);

        Assertions.assertEquals(-241214, myHashMap.get(124241));
    }

    @Test
    void putAndGet_NegativeKey_PositiveValue_Ok() {
        myHashMap.put(-1241, 21341);

        Assertions.assertEquals(21341, myHashMap.get(-1241));
    }
}
