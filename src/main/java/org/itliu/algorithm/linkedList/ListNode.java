package org.itliu.algorithm.linkedList;

/**
 * @desc: 链表演示对象
 * @author: itliu
 * @date: 2019/12/24 11:20
 * @version: 1.0
 */
public class ListNode<T> {
    public T data;
    public ListNode next;
    public String ext;

    public ListNode(T data, ListNode next) {
        this.data = data;
        this.next = next;
    }

    public ListNode(T data) {
        this.data = data;
    }

    public ListNode(T data, String ext) {
        this.data = data;
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "data=" + data +
                ", ext='" + ext + '\'' +
                '}';
    }
}
