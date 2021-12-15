import java.util.ArrayList;

public class Pell {
    public static  ArrayList<Integer> a =new ArrayList();
    public static  ArrayList<Long> b =new ArrayList();
    public static int size = 0;
    public Pell() {

    }

    public long get(int n) {

        if (n < 0) throw new UnsupportedOperationException("Pell.get is not supported for negative n");
        //return ???;
        if (n <= 2)
            return n;
        long val = 2 * get(n - 1) + get(n - 2);
        return val;
        //Memoizing
        //if(a.get(a.size()) < n){

        //    val = 2 * get(n - 1) + get(n - 2);
        //    return val;
        //}

        //a.add(n);
        //b.add(val);
        //if(n>a.lastIndexOf(size))
    }
}
