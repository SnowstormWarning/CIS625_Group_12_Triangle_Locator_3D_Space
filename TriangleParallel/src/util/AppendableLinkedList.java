package util;

import java.util.function.Consumer;
import java.util.function.Function;

public class AppendableLinkedList<T> {

    private class Node<T> {
        T data;
        Node<T> next;
    }

    private int size;

    private Node<T> head;
    private Node<T> tail;
    public AppendableLinkedList(){
        head = null;
        tail = null;

        size = 0;
    }

    public void add(T data){
        if(head == null){
            head = new Node<>();
            head.data = data;
            head.next = null;

            tail = head;
        }else{
            tail.next = new Node<>();
            tail = tail.next;

            tail.data = data;
            tail.next = null;
        }

        size++;
    }

    public void append(AppendableLinkedList<T> list){
        if(head == null){
            head = list.head;
            tail = head;
        }else{
            tail.next = list.head;
            tail = list.tail;
        }
        size += list.size;
    }

    public void iterate(Consumer<T> consumer){
        Node<T> temp = head;
        while(temp != null){
            consumer.accept(temp.data);
            temp = temp.next;
        }
    }

    public int getSize(){
        return size;
    }
}
