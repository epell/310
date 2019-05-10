 // CSC 310
 // Assignment 3
 // Ethan Pellittiere
 // 3/10/19
 // Stack based arithmetic
 import java.util.Scanner;

 class intStack{
    Node head;
    private int min;
    private int size = 0;

    public void Stack(){}

    public void push(int val){
        if(size == 0){
            head = new Node(val, null);
            min = val;
        }else{
            head = new Node(val,head);
            if(val < min){
                min = val;
            }
        }
        size++;
    }

    public int pop() {
        if (size == 0) {
            throw new RuntimeException();
        }
        int x = head.datum;
        if(size==1) {
            head = null;
        }else{
            head = head.next;
        }
        size--;
        if (x == min && size>0) {
            min = head.datum;
            boolean b = true;
            Node temp = head;
            while (b) {
                if (temp.datum < min) {
                    min = temp.datum;
                }
                if (temp.next == null) {
                    b = false;
                } else {
                    temp = temp.next;
                }
            }
        }
        return x;
    }

    public int top(){
        if(size == 0){
            throw new RuntimeException();
        }
        return head.datum;
    }

    public int getMin(){return min;}

    public boolean isEmpty(){ return size==0;}

     class Node{
         Node next;
         int datum;
         Node(int datum, Node next){
             this.datum = datum;
             this.next = next;
         }
     }
 }

 class charStack{
     Node head;
     private int size = 0;

     public void Stack(){}

     public void push(char val){
         if(size == 0){
             head = new Node(val, null);
         }else{
             head = new Node(val,head);
         }
         size++;
     }

     public char pop() {
         if (size == 0) {
             throw new RuntimeException();
         }
         char x = head.datum;
         if(size==1) {
             head = null;
         }else{
             head = head.next;
         }
         size--;
         return x;
     }

     public char top(){
         if(size == 0){
             throw new RuntimeException();
         }
         return head.datum;
     }

     public boolean isEmpty(){ return size==0;}

     class Node{
         Node next;
         char datum;
         Node(char datum, Node next){
             this.datum = datum;
             this.next = next;
         }
     }
 }


public class main {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);
        intStack stk1 = new intStack();
        loop_1:
        while(true){
            System.out.println("1:Push, 2:Pop, 3:Top, 4:Min, 0:Exit");
            switch(scan.nextInt()){
                case 0: break loop_1;
                case 1:
                    System.out.println("Enter value to add to stack");
                    stk1.push(scan.nextInt());
                    continue;
                case 2:
                    System.out.println("Pop: " + stk1.pop());
                    continue;
                case 3:
                    System.out.println("Top: " + stk1.top());
                    continue;
                case 4:
                    System.out.println("Min: " + stk1.getMin());
                default: continue;
            }

        }

        intStack ands = new intStack();
        charStack ors = new charStack();
        String exp;
        char op;
        int  op_pos, t, x;
        while(true){
            System.out.println("Enter expression to evaluate: (Q to quit)");
            exp = scan.next();
            if(exp.equals("Q")){
                break;
            }

            while(!exp.isEmpty()){
                op_pos=exp.indexOf('+');
                op_pos = (op_pos==-1 ? exp.length() : op_pos);
                t = exp.indexOf('-');
                t = (t==-1 ? exp.length() : t);
                op_pos = (t < op_pos ? t : op_pos);
                t = exp.indexOf('/');
                t = (t==-1 ? exp.length() : t);
                op_pos = (t < op_pos ? t : op_pos);
                t=exp.indexOf('*');
                t = (t==-1 ? exp.length() : t);
                op_pos = (t < op_pos ? t : op_pos);

                x = Integer.parseInt(exp.substring(0, op_pos));
                exp=exp.substring(op_pos);

                if(exp.isEmpty()){
                    op = 'E';
                }else{
                    op = exp.charAt(0);
                    exp = exp.substring(1);
                }

                if(ands.isEmpty()){
                    ands.push(x);
                    ors.push(op);
                    continue;
                }
                if(ors.top() == '*'){
                    ands.push((ands.pop()*x));
                    ors.pop();
                    ors.push(op);
                }else if(ors.top() == '/') {
                    ands.push(ands.pop() / x);
                    ors.pop();
                    ors.push(op);
                }else if (ors.top() == '+'){
                    if (op == '+' || op == '-' || op == 'E'){
                        ands.push(ands.pop()+x);
                        ors.pop();
                        ors.push(op);
                    }else{
                        ands.push(x);
                        ors.push(op);
                    }
                }else if (ors.top() == '-'){
                    if (op == '+' || op == '-' || op == 'E'){
                        ands.push(ands.pop()-x);
                        ors.pop();
                        ors.push(op);
                    }else{
                        ands.push(x);
                        ors.push(op);
                    }

                }

            }
            ors.pop();
            while(!ors.isEmpty()){
                x = ands.pop();
                op = ors.pop();
                ands.push(op=='+' ? x+ands.pop() : ands.pop()-x);
            }
            System.out.println("Result: " + ands.pop());

        }

        intStack postStack = new intStack();
        String operators ="+-/*";
        String c;
        int a,b;
        while(true){
            System.out.println("Enter postfix expression to evaluate: (Q to quit)");
            exp = scan.next();
            if(exp.equals("Q")){
                break;
            }

            while(!exp.isEmpty()){
                c = exp.substring(0,1);
                exp=exp.substring(1);

                if(operators.contains(c)){
                    b=postStack.pop();
                    a=postStack.pop();
                    switch(c) {
                        case "+":
                            postStack.push(a + b);
                            continue;
                        case "-":
                            postStack.push(a - b);
                            continue;
                        case "*":
                            postStack.push(a * b);
                            continue;
                        case "/":
                            postStack.push(a / b);
                            continue;
                    }
                }else{
                    postStack.push(Integer.parseInt(c));
                }
            }
            System.out.println("Result: " + postStack.pop());

        }

    }

}


