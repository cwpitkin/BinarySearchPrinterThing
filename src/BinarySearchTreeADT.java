
import java.util.*;

public class BinarySearchTreeADT {

    BinaryNode root;
    private int modCount;
    private int size;

    public BinarySearchTreeADT() {
        modCount = 0;
        size = 0;
    }

    public BinarySearchTreeADT(String data) {
        root = new BinaryNode(data, null, null);
        modCount = 0;
        size = 0;
    }

    public void add(String data) {
        if (size == 0) {
            root = new BinaryNode(data, null, null);
            size++;
            modCount++;
        } else {
            BinaryNode probe = root;
            boolean found = false;
            while (found == false) {
                while (probe.getData().compareTo(data) > 0 && found == false) {
                    if (probe.getLeft() == null) {
                        found = true;
                    }else {
                        probe = probe.getLeft();
                    }
                }
                while (probe.getData().compareTo(data) < 0 && found == false) {
                    if (probe.getRight() == null) {
                        found = true;
                    } else {
                        probe = probe.getRight();
                    }
                }
            }
            BinaryNode node = new BinaryNode(data, null, null);
            if (probe.getData().compareTo(data) > 0) {
                probe.setLeft(node);
            } else {
                probe.setRight(node);
            }
            setBalanceFactors(node);
            size++;
            modCount++;
        }
    }

    public Iterator<String> iterator() {
        return new BinaryIterator();
    }

    public String printTree() {
    String result = "";
    if (root != null){
      result = "\n\n" + printTree(root, 0);
    }
    return result;

    }

    public void remove(String item) {
        BinaryNode probe = root;
        BinaryNode parent;
        boolean found = false;
        while (found == false) {
            if (probe.getData().equals(item)) {
                found = true;
            } else if (probe.getData().compareTo(item) > 0) {
                probe = probe.getLeft();
            } else {
                probe = probe.getRight();
            }
        }
        if (probe.getRight() == null && probe.getLeft() == null) {
            parent = findParent(probe.getData());
            if (probe == root) {
                root = new BinaryNode(null,null,null);
            } else if (probe.equals(parent.getRight())) {
                parent.setRight(null);
            } else {
                parent.setLeft(null);
            }
        } else if (probe.getRight() == null) {
            BinaryNode probe2 = probe.getLeft();
            probe.setData(probe2.getData());
            probe.setRight(probe2.getRight());
            probe.setLeft(probe2.getLeft());
        } else if (probe.getLeft() == null) {
            BinaryNode probe2 = probe.getRight();
            probe.setData(probe2.getData());
            probe.setRight(probe2.getRight());
            probe.setLeft(probe2.getLeft());
        } else {
            BinaryNode probe2 = probe.getRight();
            while (probe2.getLeft() != null) {
                probe2 = probe2.getLeft();
            }
            parent = findParent(probe2.getData());
            probe.setData(probe2.getData());
            if (probe2.equals(parent.getRight())) {
                parent.setRight(probe2.getRight());
            } else {
                parent.setLeft(probe2.getRight());
            }
        }
        modCount++;
        size--;
    }

    private String printTree(BinaryNode root, int indent) {
    int separation = 5;
    String result = "";
    if (root.getRight() != null) result = result + printTree(root.getRight(), indent+separation);
    result = result + makeIndents(indent)+root.getData().toString()+
            "("+root.getBalance()+")"+ "\n";
    if (root.getLeft() != null) result = result + printTree(root.getLeft(), indent+separation);
    return result;

    }

    private String makeIndents(int indent) {
    String result = "";
    for (int i = 0; i < indent; i++) {
      result = result + "     ";
    }
    return result;

    }

    private BinaryNode findParent(String item) {
        BinaryNode parent = null;
        BinaryNode probe = root;
        boolean found = false;
        while (found == false) {
            if (probe.getData().equals(item)) {
                found = true;
            } else if (probe.getData().compareTo(item) > 0) {
                parent = probe;
                probe = probe.getLeft();
            } else {
                parent = probe;
                probe = parent.getRight();
            }
        }
        return parent;
    }

    private BinaryNode findSuccessor(String item) {
        BinaryNode successor = null;
        BinaryNode probe = root;
        boolean found = false;
        while (found == false) {
            if (probe.getData().equals(item)) {
                found = true;
            } else if (probe.getData().compareTo(item) > 0) {
                successor = probe;
                probe = probe.getLeft();
            } else {
                probe = probe.getRight();
            }
        }
        if (probe.getRight() != null) {
            successor = probe.getRight();
            while (successor.getLeft() != null) {
                successor = successor.getLeft();
            }
        }
        return successor;
    }

    private void setBalanceFactors(BinaryNode node) {
        BinaryNode parent = findParent(node.getData());
        if (parent.getData().compareTo(node.getData()) > 0) {
            parent.setBalance(-1);
        } else {
            parent.setBalance(1);
        }
        if (parent.getBalance() >= 2) {
            rotateLeft(parent, node);
        } else if (parent.getBalance() <= -2) {
            rotateRight(parent, node);
        } else if (!parent.equals(root)) {
            if (parent.getBalance() != 0) {
                setBalanceFactors(parent);
            }
        }
    }

    private void rotateLeft(BinaryNode parent, BinaryNode child) {
        if (child.getBalance() == -1) {            
            rotateRight(child, child.getLeft());
            child = findParent(child.getData());
            child.setBalance(1);
        }
        BinaryNode temp = child.getLeft();
        child.setLeft(parent);
        parent.setRight(temp);
        if (parent.equals(root)) {
            root = child;
        } else {
            BinaryNode grandParent = findParent(parent.getData());
            if (grandParent.getData().compareTo(parent.getData()) > 0) {
                grandParent.setLeft(child);
            } else {
                grandParent.setRight(child);
            }
        }
        parent.setBalance(-2);
        child.setBalance(-1);
    }

    private void rotateRight(BinaryNode parent, BinaryNode child) {
        if (child.getBalance() == 1) {
            rotateLeft(child, child.getRight());
            child = findParent(child.getData());
            child.setBalance(-1);
        }
        BinaryNode temp = child.getRight();
        child.setRight(parent);
        parent.setLeft(temp);
        if (parent.equals(root)) {
            root = child;
        } else {
            BinaryNode grandParent = findParent(parent.getData());
            if (grandParent.getData().compareTo(parent.getData()) > 0) {
                grandParent.setLeft(child);
            } else {
                grandParent.setRight(child);
            }
        }
        parent.setBalance(2);
        child.setBalance(1);
    }

    private class BinaryIterator implements Iterator<String> {

        private int pos;
        private int curMod;
        BinaryNode smallestSoFar;

        public BinaryIterator() {
            pos = 0;
            curMod = modCount;
            smallestSoFar = null;
        }

        public boolean hasNext() {
            return (pos < size);
        }

        public String next() {
            if (curMod != modCount) {
                throw new BinarySearchTreeIteratorException("Tree has been modified");
            } else if(!hasNext()) {
                throw new NoSuchElementException();
            }
            String result;
            if (pos == 0) {
                smallestSoFar = root;
                while (smallestSoFar.getLeft() != null) {
                    smallestSoFar = smallestSoFar.getLeft();
                }
            } else {
                smallestSoFar = findSuccessor(smallestSoFar.getData());
            }
            result = smallestSoFar.getData();
            pos++;
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class BinaryNode {

    	String data;
        BinaryNode left;
        BinaryNode right;
        private int balance;

        public BinaryNode(String d, BinaryNode l, BinaryNode r) {
            data = d;
            left = l;
            right = r;
            balance = 0;
        }

        public void setData(String d) {
            data = d;
        }

        public void setRight(BinaryNode r) {
            right = r;
        }

        public void setLeft(BinaryNode l) {
            left = l;
        }

        public String getData() {
            return data;
        }

        public BinaryNode getRight() {
            return right;
        }

        public BinaryNode getLeft() {
            return left;
        }

        public void setBalance(int x) {
            balance = balance + x;
        }

        public int getBalance() {
            return balance;
        }
    }
}
