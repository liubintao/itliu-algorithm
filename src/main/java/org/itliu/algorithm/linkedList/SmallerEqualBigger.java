package org.itliu.algorithm.linkedList;

/**
 * @desc: 将单向链表按某值划分成左边小、中间相等、右边大的形式
 * <p>
 * 1）把链表放入数组里，在数组上做partition（笔试用）
 * 2）分成小、中、大三部分，再把各个部分之间串起来（面试用）
 * @author: itliu
 * @date: 2020/4/28 15:32
 * @version: 1.0
 */
public class SmallerEqualBigger {

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 把链表放入数组里，在数组上做partition
     */
    public static Node listPartition1(Node head, int pivot) {
        if (head == null) {
            return head;
        }

        Node cur = head;
        int i = 0;

        while (cur != null) {
            i++;
            cur = cur.next;
        }

        Node[] nodeArr = new Node[i];
        i = 0;
        cur = head;
        for (; i < nodeArr.length; i++) {
            nodeArr[i] = cur;
            cur = cur.next;
        }

        arrPartition(nodeArr, pivot);
        //将数组中的元素链起来
        for (i = 1; i != nodeArr.length; i++) {
            nodeArr[i - 1].next = nodeArr[i];
        }
        nodeArr[i - 1].next = null;
        return nodeArr[0];
    }

    private static void arrPartition(Node[] nodeArr, int pivot) {
        int small = -1;
        int big = nodeArr.length;
        int index = 0;
        while (index != big) {
            //当前值<pivot
            if (nodeArr[index].value < pivot) {
                swap(nodeArr, index++, ++small);
            } else if (nodeArr[index].value == pivot) {
                //当前值=pivot
                index++;
            } else {
                //当前值>pivot
                swap(nodeArr, index, --big);
            }
        }
    }

    public static void swap(Node[] nodeArr, int a, int b) {
        Node tmp = nodeArr[a];
        nodeArr[a] = nodeArr[b];
        nodeArr[b] = tmp;
    }


    public static Node listPartition2(Node head, int pivot) {
        if (head == null) {
            return head;
        }

        Node sH = null;//small head
        Node sT = null;//small tail
        Node eH = null;//equal head
        Node eT = null;//equal tail
        Node mH = null;//big head
        Node mT = null;//big tail
        Node next = null;//save the next node
        // every node distributed to three lists
        while (head != null) {
            next = head.next;
            //此处指针一定要断掉
            head.next = null;
            if (head.value < pivot) {
                if (sH == null) {
                    sH = head;
                    sT = head;
                } else {
                    sT.next = head;
                    sT = head;
                }
            } else if (head.value == pivot) {
                if (eH == null) {
                    eH = head;
                    eT = head;
                } else {
                    eT.next = head;
                    eT = head;
                }
            } else {
                if (mH == null) {
                    mH = head;
                    mT = head;
                } else {
                    mT.next = head;
                    mT = head;
                }
            }

            head = next;
        }

        // small and equal reconnect
        //小于区域的尾巴连等于区域的头，等于区域的尾巴连大于区域的头
        if (sT != null) {//如果有小于区域
            sT.next = eH;
            eT = eT == null ? sT : eT;//下一步，谁去连大于区域的头，谁就变成eT
        }

        // 上面的if，不管跑了没有，et都得去连mH
        // all reconnect
        if (eT != null) {
            eT.next = mH;
        }

        return sH != null ? sH : (eH != null ? eH : mH);
    }


    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        printLinkedList(head1);
//         head1 = listPartition1(head1, 4);
        head1 = listPartition2(head1, 5);
        printLinkedList(head1);

    }
}
