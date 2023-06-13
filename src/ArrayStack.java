import java.util.Iterator;
//import java.lang.reflect.Method;

public class ArrayStack<E extends Cloneable> implements Stack<Cloneable> {
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
    public void push(Cloneable element) {
        if(head_index + 1 < max_size) {
            stack[++head_index] = element;
            // TODO: make sure that it does +1 head index
        }
        else throw new StackOverflowException();
    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public Cloneable peek() {
        if(!isEmpty()) {
            return cloneElement((E)stack[head_index]);
        }
        else {
            //TODO: change exception name
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
    public Stack<Cloneable> clone() {
        try {
            ArrayStack temp = (ArrayStack) super.clone();
            for (int i = 0; i < max_size; i++) {
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
        }
        catch (NoSuchMethodException | IllegalAccessException exception) {return null;}
    }


    @Override
    public Iterator<Cloneable> iterator() {
        return new StackIterator();
    }


    private class StackIterator implements Iterator {
        private int index = head_index;

        @Override
        public boolean hasNext() {
            return index - 1 >= 0;
        }

        @Override
        public Cloneable next() {
            return stack[index--];
        }
    }

}
