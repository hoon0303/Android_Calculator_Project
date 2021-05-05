package com.example.term_project_20164006parkhoon;


interface QueueIF {
    boolean isEmpty();
    void enqueue(String data);
    void dequeue();
    String peek();
    void clear();
}

// 큐를 구성하는 Node 클래스
class ListQueueNode {
    private String data;    // 데이터 저장 변수
    public ListQueueNode link;    // 다른 노드를 참조할 링크

    public ListQueueNode() {
        this.data = null;
        this.link = null;
    }

    public ListQueueNode(String data) {
        this.data = data;
        this.link = null;
    }

    public ListQueueNode(String data, ListQueueNode link) {
        this.data = data;
        this.link = link;
    }

    public String getData() {
        return this.data;
    }
}

public class LinkedList_Queue implements QueueIF {

    private ListQueueNode head;    // ListQueueNode 타입의 head 노드 인스턴스 변수
    private ListQueueNode front;    // 큐의 front 포인터
    private ListQueueNode rear;    // 큐의 rear 포인터

    // 큐 생성자
    public LinkedList_Queue() {
        head = null; // head 초기화
        front = null;    // front 포인터 초기화
        rear = null;    // rear 포인터 초기화
    }

    // 큐가 비어있는 상태인지 확인
    public boolean isEmpty(){
        return (front == null && rear == null);
    }

    // 큐에 Node 삽입
    public void enqueue(String data) {
        ListQueueNode newNode = new ListQueueNode(data);    // 새로운 노드 생성

        // 큐가 비었을 경우
        if(isEmpty()) {
            // front,rear 포인터가 null인 경우 새로운 노드를 참조하도록 함
            // 이 때 head도 함께 새로운 노드를 참조하도록 함 (head는 첫 노드를 참조하는 용도로만 사용을 제한)
            this.head = newNode;
            this.front = this.head;
            this.rear = this.head;
        } else {
            // rear 포인터의 노드(마지막 노드) link가 새로운 노드를 참조하도록 함.
            // 이후 rear 포인터는 새로 추가된 노드를 참조하도록 함.
            rear.link = newNode;
            rear = newNode;
        }
    }

    // 큐에서 Node 삭제
    public void dequeue() {
        ListQueueNode tempNode;

        // front 포인터가 null인 경우 모든 노드가 삭제되었으므로 return
        if(isEmpty()) {
            return;
        }
        // front 포인터의 link가 null인 경우 (큐에 노드가 1개 남았을 경우)
        if(front.link == null) {
            // head와 front,rear 포인터에 null을 할당하여 남은 노드와의 연결을 끊고 초기화
            head = null;
            front = null;
            rear = null;
        } else {
            tempNode = front.link;    // tempNode는 front 포인터가 가리키는 노드의 다음 노드를 할당.
            head = tempNode;    // head가 tempNode를 참조하도록 함
            front.link = null;    // 기존에 front 포인터가 가리키는 노드의 link를 초기화하여 끊음
            front = head;    // front 포인터가 head(다음 노드)를 참조하도록 함
        }
    }

    // 큐의 첫번째 데이터 추출
    public String peek() {
        if(isEmpty()) {
            return null;
        } else {
            return front.getData();
        }
    }

    // 큐 초기화
    public void clear() {
        if(isEmpty()) {
            return;
        } else {
            // 큐의  head와 front,rear포인터 초기화
            head = null;
            front = null;
            rear = null;
        }
    }
    public void printListQueue() {
        if(isEmpty()) {
            System.out.println("Queue is empty!");
            return;
        } else {
            ListQueueNode tempNode = this.front;    // tempNode에 head가 가리키는 첫번째 노드를 할당

            // tempNode가 null이 아닐 때까지 반복하여 출력
            while(tempNode != null) {
                System.out.print(tempNode.getData() + " ");
                tempNode = tempNode.link;    // temp 노드에 다음 노드(tempNode.link) 할당.
            }
            System.out.println();
        }
    }



}