package hashMap;

public class MyHashMap {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.7f;
    private int arrayCapacity;
    private int elementsNumber;
    private final float threshold = arrayCapacity * LOAD_FACTOR;
    private Node[] nodesArray;

    public MyHashMap() {
        nodesArray = new Node[DEFAULT_CAPACITY];
        arrayCapacity = nodesArray.length;
        elementsNumber = 0;
    }

    public long put(int key, long value) {
        resize();
        int index = getIndex(key);
        Node newNode = new Node(key, value);
        while (nodesArray[index] != null) {
            index++;
        }
        nodesArray[index] = newNode;
        elementsNumber++;
        return value;
    }

    public long get(int key) {
        int index = getIndex(key);
        Node answer = nodesArray[index];
        if (answer == null) {
            return 0;
        }
        while (!(key == answer.key)) {
            answer = nodesArray[++index];
        }
        return answer.value;
    }

    public int size() {
        return elementsNumber;
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
        if (elementsNumber == threshold) {
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
    }

    private int getIndex(int key) {
        return Math.abs(key) % arrayCapacity;
    }
}
