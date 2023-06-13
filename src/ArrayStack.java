import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.lang.reflect.Method;

public class ArrayStack<E extends Cloneable> implements Stack<E> {
    int max_size;
    int head_index = -1; // last cell that contains data
    Cloneable[] stack;


    public ArrayStack(int max_size) {
        if(max_size >= 0) {
            this.max_size = max_size;
            this.stack = new Cloneable[max_size];
        }
        else throw new NegativeCapacityException();
    }

    @Override
    public void push(E element) {
        if(head_index + 1 < max_size) {
            stack[++head_index] = element;
            // TODO: make sure that it does +1 head index
        }
        else throw new StackOverflowException();
    }

    @Override
    public E pop() {
        if (!isEmpty()){
            E temp = (E)stack[head_index];
            //E temp = cloneElement((E)stack[head_index]);
            stack[head_index--] = null;
            return temp;
        }
        else throw new EmptyStackException();

    }

    @Override
    public E peek() {
        if(!isEmpty()) {
            return (E)stack[head_index];
            //return cloneElement((E)stack[head_index]);
        }
        else {
            throw new EmptyStackException();
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

    private E cloneElement(E element) {
        try {
            return (E) element.getClass().getMethod("clone").invoke(element);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            return null;
        }
    }


    @Override
    public ArrayStackIterator iterator() {
        return new ArrayStackIterator();
    }


    private class ArrayStackIterator implements Iterator<E> {
        private int index = head_index;

        @Override
        public boolean hasNext() {
            return index >= 0;
        }

        @Override
        public E next() {
            return (E)stack[index--];
        }
    }

}
