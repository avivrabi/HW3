import java.util.Iterator;

public class ArrayStack<E extends Cloneable> implements Stack<E> {
    // size will be received from builder
    // need to throw exception if max_size < 0
    int max_size;
    int head_index = -1; // last cell that contains data

    Cloneable[] stack;

    /**
     * Constructor
     * @param max_size
     */
    public ArrayStack(int max_size) {
        this.max_size = max_size;
    }
    @Override
    public void push(Cloneable element) {
        //TODO: change this func
        int x=1;
    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public E peek() {
        if(!isEmpty()) {
            return (E) stack[this.head_index];
            //TODO: why it's marked??
        }
        else {
            throw new EmptyStackException2();
        }
    }

    @Override
    public int size() {
        return head_index + 1;
    }

    @Override
    public boolean isEmpty() {
        return head_index < 0;
    }

    @Override
    public Stack clone() {
        // try catch
        // clue: use invoke
        return null;
    }
    public class StackIterator<T> implements Iterator{
        @Override
        public Iterator iterator() {
            return null;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            return null;
        }
    }
}
