package hashmap;

public class MyHashMap {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.7f;
    private static final long DEFAULT_VALUE = -1;
    private final float loadFactor;
    private int arrayCapacity;
    private int elementsQuantity = 0;
    private Node[] nodesArray;

    public MyHashMap() {
        nodesArray = new Node[DEFAULT_CAPACITY];
        arrayCapacity = DEFAULT_CAPACITY;
        loadFactor = DEFAULT_LOAD_FACTOR;
    }

    public MyHashMap(int capacity) {
        nodesArray = new Node[capacity];
        arrayCapacity = capacity;
        loadFactor = DEFAULT_LOAD_FACTOR;
    }

    public MyHashMap(int capacity, float loadFactor) {
        nodesArray = new Node[capacity];
        arrayCapacity = capacity;
        this.loadFactor = loadFactor;
    }

    public void put(int key, long value) {
        if (elementsQuantity >= arrayCapacity * loadFactor) {
            resize();
        }

        int index = getIndex(key);
        while (nodesArray[index] != null && nodesArray[index].key != key) {
            index++;
            if (index >= arrayCapacity) {
                resize();
                index = getIndex(key);
            }
        }

        if (nodesArray[index] != null && nodesArray[index].key == key) {
            nodesArray[index].value = value;
        } else {
            nodesArray[index] = new Node(key, value);
            elementsQuantity++;
        }
    }

    public long get(int key) {
        int index = getIndex(key);
        while (index < arrayCapacity && nodesArray[index] != null && nodesArray[index].key != key) {
            index++;
        }

        if (index >= arrayCapacity || nodesArray[index] == null) {
            return DEFAULT_VALUE;
        }
        return nodesArray[index].value;
    }

    public int size() {
        return elementsQuantity;
    }

    private static class Node {
        int key;
        long value;

        Node(int key, long value) {
            this.key = key;
            this.value = value;
        }
    }

    private void resize() {
        arrayCapacity *= 2;
        Node[] newTable = new Node[arrayCapacity];
        for (Node node : nodesArray) {
            if (node == null) {
                continue;
            }
            int index = getIndex(node.key);
            while (newTable[index] != null) {
                index++;
            }
            newTable[index] = node;
        }
        nodesArray = newTable;
    }

    private int getIndex(int key) {
        return Math.abs(key) % arrayCapacity;
    }
}
