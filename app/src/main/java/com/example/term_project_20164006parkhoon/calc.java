package com.example.term_project_20164006parkhoon;

import android.util.Log;

public class calc {

    private LinkedList_Queue queue;

    public calc(){
        queue = new LinkedList_Queue();
    }
    public int prec(String op)
    {
        switch (op){
            case "(": case ")": return 0;
            case "&": case "|": case "^": return 1;
            case "+": case "-": return 2;
            case "*": case "/": case"%": return 3;
            case "~": return 4;
        }
        return -1;
    }
    public String infix_to_postfix(LinkedList_Queue exp)
    {
        String temp="";
        int i=0;
        String ch, top_op;

        LinkedList_Stack s = new LinkedList_Stack();

        while(!exp.isEmpty())
        {
            ch = exp.peek();
            exp.dequeue();
            switch (ch){
                case "+": case"-": case"*": case"/": case"%": case"&": case"|": case"^": case"~":
                    while(!s.isEmpty() && (prec(ch) <= prec(s.peek()))) {

                        temp=temp+s.peek();
                        queue.enqueue(s.peek());
                        s.pop();
                    }
                    s.push(ch);
                    System.out.println(s.peek());
                    break;
                case "(":
                    s.push(ch);
                    break;
                case ")":
                    top_op = s.peek();
                    s.pop();
                    while(!top_op.equals("(")){
                        temp=temp+top_op;
                        queue.enqueue(top_op);
                        top_op = s.peek();
                        s.pop();
                    }
                    break;
                default:
                    temp=temp+ch;
                    queue.enqueue(ch);
                    break;
            }

        }
        while(!s.isEmpty())
        {
            temp=temp+s.peek();
            queue.enqueue(s.peek());
            s.pop();
        }
        queue.printListQueue();
        return temp;
    }

    public String eval()
    {
        double op1, op2, value, i=0;
        String ch;
        LinkedList_Stack s = new LinkedList_Stack();
        while(!queue.isEmpty())
        {
            ch = queue.peek();
            queue.dequeue();
            if(!ch.equals("+") && !ch.equals("-") && !ch.equals("*") && !ch.equals("/") && !ch.equals("%") && !ch.equals("&") && !ch.equals("|") && !ch.equals("^") && !ch.equals("~"))
            {
                value = Double.parseDouble(ch);//문자열 실수형 변환
                System.out.println(Double.toString(value));
                s.push(Double.toString(value));
            }
            else{
                op2 = Double.parseDouble(s.peek());
                s.pop();
                if(ch.equals("~"))
                {
                    s.push(Double.toString((double)(~(int)op2)));
                    continue;
                }
                op1 = Double.parseDouble(s.peek());
                s.pop();
                switch (ch){
                    case "+":
                        s.push(Double.toString(op1+op2));
                        break;
                    case "-":
                        s.push(Double.toString(op1-op2));
                        break;
                    case "*":
                        s.push(Double.toString(op1*op2));
                        break;
                    case "/":
                        s.push(Double.toString(op1/op2));
                        break;
                    case "%":
                        s.push(Double.toString(op1%op2));
                        break;
                    case "&":
                        s.push(Double.toString((double)((int)op1&(int)op2)));
                        break;
                    case "|":
                        s.push(Double.toString((double)((int)op1|(int)op2)));
                        break;
                    case "^":
                        s.push(Double.toString((double)((int)op1^(int)op2)));
                        break;


                }

            }
        }
        return s.peek();
    }



}
