package edu.neu.coe.info6205.sort.elementary;
import edu.neu.coe.info6205.util.Timer;
import edu.neu.coe.info6205.sort.elementary.InsertionSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Insertion_Timer {

    public static void main(String[] args){
        final List<Integer> list = new ArrayList<>();
        list.add(55);
        list.add(467);
        list.add(3);
        list.add(22);
        list.add(1098);
        list.add(108);
        list.add(98);
        list.add(78);
        Integer[] xs = list.toArray(new Integer[0]);
        Timer timer = new Timer();
        //   InsertionSort.sort(xs);
        Random rd = new Random(); // creating Random object
        Integer[] arr = new Integer[5];
        for (int i = 0; i < xs.length; i++) {
            // InsertionSort.sort(xs);
            System.out.println(xs[i]);
        }
        final double mean = timer.repeat(50, () -> xs, t -> {
            InsertionSort.sort(t);
            return null;
        });
        System.out.println(mean);


        //Timer timer = new Timer();
    }
}