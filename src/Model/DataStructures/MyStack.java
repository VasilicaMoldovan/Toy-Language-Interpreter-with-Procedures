package Model.DataStructures;

import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack(){
        stack = new Stack<T>();
    }

    @Override
    public T pop(){
        return stack.pop();
    }

    @Override
    public void push(T el){
        stack.push(el);
    }

    @Override
    public boolean isEmpty(){
        return stack.empty();
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ExeStack:\n");
        int size = this.stack.size() - 1;
        for (int index = 0; index <= size; index++)
            result.append(this.stack.get(index).toString()).append("\n");

        return result.toString();
    }

    @Override
    public T top(){
        return stack.peek();
    }

    @Override
    public List<T> getAll(){
        return stack;
    }

    @Override
    public MyIStack<T> deepCopy(){
        MyStack<T> newStack = new MyStack<>();
        for (T elem : stack){
            newStack.push(elem);
        }
        return newStack;
    }
}
