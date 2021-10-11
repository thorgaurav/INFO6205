import edu.neu.coe.info6205.union_find.UF_HWQUPC;

public class UnionFind {
    public static void main(String[] args){
        //int n = Integer.parseInt(args[0]);
        int n = 10000;
        int totalConnections = count(n);
        System.out.print(totalConnections);
    }

    private static int count(int n) {
        UF_HWQUPC uf = new UF_HWQUPC(n);
        int connections = 0;
        int pairs = 0;
        while(uf.components()!=1){
            int firstVal = (int)Math.round(Math.random()*10);
            int secondVal = (int)Math.round(Math.random()*(n-1));
            pairs+=1;
            if(uf.connected(firstVal,secondVal)==false){
                uf.connect(firstVal,secondVal);
                connections++;
            }
        }
        System.out.println(pairs);
        return connections;
    }
}
