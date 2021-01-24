import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        if (args.length == 1) {
            int k = Integer.parseInt(args[0]);

            int n = 0;
            // System.out.println(k);
            RandomizedQueue<String> rq = new RandomizedQueue<>();
            while (!StdIn.isEmpty()) {
                rq.enqueue(StdIn.readString());
                // String ss = StdIn.readString();

               /* if (n < k) {
                    rq.enqueue(ss);
                    n++;
                }
                else {
                    rq.dequeue();
                    ;
                }*/
            }

           /* while (rq.size() != 0) {
                System.out.println(rq.dequeue());
            }*/
            for (int i = 0; i < k; i++) {
                System.out.println(rq.dequeue());
            }
        }
    }
}