import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.lang.reflect.Method;

/**
 * Represent a stack as an array
 * @param <E> Variant parameter, will be cloneable
 */
public class ArrayStack<E extends Cloneable> implements Stack<E> {
    int max_size;
    int head_index = -1; // last cell that contains data
    Cloneable[] stack;

    /**
     * Class constructor
     * @param max_size maximum size the that the stack is able to get
     */
    public ArrayStack(int max_size) {
        if(max_size >= 0) {
            this.max_size = max_size;
            this.stack = new Cloneable[max_size];
        }
        else throw new NegativeCapacityException();
    }

    /**
     * Adds an element to the head of stack
     * @param element Element to add
     */
    @Override
    public void push(E element) {
        if(head_index + 1 < max_size) {
            stack[++head_index] = element;
        }
        else throw new StackOverflowException();
    }

    /**
     * Take out the element in the head out of stack
     * @return Element that was outed of the stack
     */
    @Override
    public E pop() {
        if (!isEmpty()){
            //E temp = (E)stack[head_index];
            E temp = cloneElement((E)stack[head_index]);
            stack[head_index--] = null;
            return temp;
        }
        else throw new EmptyStackException();

    }

    /**
     * Returns the element in the head of stack
     * @return element in the head of stack
     */
    @Override
    public E peek() {
        if(!isEmpty()) {
            //return (E)stack[head_index];
            return cloneElement((E)stack[head_index]);
        }
        else {
            throw new EmptyStackException();
        }
    }

    /**
     * Return the current size of stack
     * @return How many object there are in the stack at the moment
     */
    @Override
    public int size() {
        return head_index + 1;
    }

    /**
     * Check if the stack is empty
     * @return boolean represent if the stack is empty
     */
    @Override
    public boolean isEmpty() {
        return head_index < 0;
    }

    /**
     * Make a deep clone of the stack
     * @return New cloned stack
     */
    @Override
    public ArrayStack<E> clone() {
        try {
            ArrayStack temp = (ArrayStack) super.clone();
            temp.stack = new Cloneable[this.max_size];
            for (int i = 0; i <= head_index; i++) {
                temp.stack[i] = cloneElement((E)this.stack[i]);
            }
            return temp;
        } catch (StackException | CloneNotSupportedException exception) {
            return null;
        }
    }

    /**
     * Make a clone of an element in the stack
     * @param element the element to make a clone out of
     * @return New cloned element
     */
    private E cloneElement(E element) {
        try {
            return (E) element.getClass().getMethod("clone").invoke(element);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IllegalArgumentException |
                 NullPointerException | ExceptionInInitializerError | SecurityException exception) {
            return null;
        }
    }

    /**
     * Creates an object to iterate ArrayStack
     * @return new iterator created
     */
    @Override
    public ArrayStackIterator iterator() {
        return new ArrayStackIterator();
    }

    /**
     * Initiate inner iterator for ArrayStack
     */
    private class ArrayStackIterator implements Iterator<E> {
        private int index = head_index;

        /**
         * Check if there is another element to get to
         * @return Boolean represent if there is another element
         */
        @Override
        public boolean hasNext() {
            return index >= 0;
        }

        /**
         *
         * @return the next element
         */
        @Override
        public E next() {
            return (E)stack[index--];
        }
    }

}
