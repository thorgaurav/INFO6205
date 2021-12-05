package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.sort.elementary.InsertionSort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static edu.neu.coe.info6205.util.Utilities.formatWhole;

/**
 * This class implements a simple Benchmark utility for measuring the running time of algorithms.
 * It is part of the repository for the INFO6205 class, taught by Prof. Robin Hillyard
 * <p>
 * It requires Java 8 as it uses function types, in particular, UnaryOperator&lt;T&gt; (a function of T => T),
 * Consumer&lt;T&gt; (essentially a function of T => Void) and Supplier&lt;T&gt; (essentially a function of Void => T).
 * <p>
 * In general, the benchmark class handles three phases of a "run:"
 * <ol>
 *     <li>The pre-function which prepares the input to the study function (field fPre) (may be null);</li>
 *     <li>The study function itself (field fRun) -- assumed to be a mutating function since it does not return a result;</li>
 *     <li>The post-function which cleans up and/or checks the results of the study function (field fPost) (may be null).</li>
 * </ol>
 * <p>
 * Note that the clock does not run during invocations of the pre-function and the post-function (if any).
 *
 * @param <T> The generic type T is that of the input to the function f which you will pass in to the constructor.
 */
public class Benchmark_Timer<T> implements Benchmark<T> {

    /**
     * Calculate the appropriate number of warmup runs.
     *
     * @param m the number of runs.
     * @return at least 2 and at most m/10.
     */
    static int getWarmupRuns(int m) {
        return Integer.max(2, Integer.min(10, m / 10));
    }

    /**
     * Run function f m times and return the average time in milliseconds.
     *
     * @param supplier a Supplier of a T
     * @param m        the number of times the function f will be called.
     * @return the average number of milliseconds taken for each run of function f.
     */
    @Override
    public double runFromSupplier(Supplier<T> supplier, int m) {
        logger.info("Begin run: " + description + " with " + formatWhole(m) + " runs");
        // Warmup phase
        final Function<T, T> function = t -> {
            fRun.accept(t);
            return t;
        };
        new Timer().repeat(getWarmupRuns(m), supplier, function, fPre, null);

        // Timed phase
        return new Timer().repeat(m, supplier, function, fPre, fPost);
    }

    /**
     * Constructor for a Benchmark_Timer with option of specifying all three functions.
     *
     * @param description the description of the benchmark.
     * @param fPre        a function of T => T.
     *                    Function fPre is run before each invocation of fRun (but with the clock stopped).
     *                    The result of fPre (if any) is passed to fRun.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     *                    When you create a lambda defining fRun, you must return "null."
     * @param fPost       a Consumer function (i.e. a function of T => Void).
     */
    public Benchmark_Timer(String description, UnaryOperator<T> fPre, Consumer<T> fRun, Consumer<T> fPost) {
        this.description = description;
        this.fPre = fPre;
        this.fRun = fRun;
        this.fPost = fPost;
    }

    /**
     * Constructor for a Benchmark_Timer with option of specifying all three functions.
     *
     * @param description the description of the benchmark.
     * @param fPre        a function of T => T.
     *                    Function fPre is run before each invocation of fRun (but with the clock stopped).
     *                    The result of fPre (if any) is passed to fRun.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     */
    public Benchmark_Timer(String description, UnaryOperator<T> fPre, Consumer<T> fRun) {
        this(description, fPre, fRun, null);
    }

    /**
     * Constructor for a Benchmark_Timer with only fRun and fPost Consumer parameters.
     *
     * @param description the description of the benchmark.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     *                    When you create a lambda defining fRun, you must return "null."
     * @param fPost       a Consumer function (i.e. a function of T => Void).
     */
    public Benchmark_Timer(String description, Consumer<T> fRun, Consumer<T> fPost) {
        this(description, null, fRun, fPost);
    }

    /**
     * Constructor for a Benchmark_Timer where only the (timed) run function is specified.
     *
     * @param description the description of the benchmark.
     * @param f           a Consumer function (i.e. a function of T => Void).
     *                    Function f is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     */
    public Benchmark_Timer(String description, Consumer<T> f) {
        this(description, null, f, null);
    }
    public static void main(String[] args) {

        //Instantiate the insertion sort class
        InsertionSort insSort = new InsertionSort();

        //Instantiate Benchmark_Timer class to perform Benchmark Test
        Benchmark_Timer<Integer[]> benchTimer = new Benchmark_Timer<>("Benchmark Test", null, (x) -> insSort.sort(x, 0, x.length), null);

        //Create a randomly ordered array and run benchmark test
        for(int i = 200; i < 10000; i = i*2) {

            int j = i;

            //Provide a randomly ordered array of size j to supplier
            Supplier<Integer[]> supplier = () -> {
                Random random = new Random();
                Integer[] arr = new Integer[j];

                //Generate the random array
                for(int k = 0; k < j; k++) {
                    arr[k] = random.nextInt(j);
                }
                return arr;
            };

            //Time taken by insertion sort to run 10 times
            double time = benchTimer.runFromSupplier(supplier, 10);

            System.out.println("Value of N: " + i + " Order Type- Random" + " Time Taken: " + time);
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------------");

        //Create an ordered array and run benchmark test
        for(int i = 200; i < 10000; i = i*2) {

            int j = i;

            //Provide an ordered array of size j to supplier
            Supplier<Integer[]> supplier = () -> {
                Random random = new Random();
                Integer[] arr = new Integer[j];

                //Generate the array
                for(int k = 0; k < j; k++) {
                    arr[k] = random.nextInt(j*100);
                }

                //Sort the array
                Arrays.sort(arr);
                return arr;
            };

            //Time taken by insertion sort to run 10 times
            double time = benchTimer.runFromSupplier(supplier, 10);

            System.out.println("Value of N: " + i + " Order Type- Ordered" + " Time Taken: " + time);

        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------------");

        //Create a partially ordered array and run benchmark test
        for(int i = 200; i < 10000; i = i*2) {
            int j = i;

            //Provide the array of size j to supplier
            Supplier<Integer[]> supplier = () -> {
                Random random = new Random();
                Integer[] arr = new Integer[j];

                //Generate the array
                for(int k = 0; k < j; k++) {
                    arr[k] = random.nextInt(j*100);
                }

                //Sort the array
                Arrays.sort(arr);

                //Rearrange half the array elements
                int rearrange = (int) (0.5*j);

                //Generate index to rearrange the array
                for(int i1 = 0; i1 < rearrange; i1++) {
                    int index = random.nextInt(j);
                    arr[index] = random.nextInt(j*100);
                }

                return arr;
            };

            //Time taken by insertion sort to run 10 times
            double time = benchTimer.runFromSupplier(supplier, 10);

            System.out.println("Value of N: " + i + " Order Type- Partially Ordered" + " Time Taken: " + time);

        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------------");

        //Create a reverse ordered array and run benchmark test
        for(int i = 200; i < 10000; i = i*2) {
            int j = i;

            //Provide the array of size j to supplier
            Supplier<Integer[]> supplier = () -> {
                Random random = new Random();
                Integer[] arr = new Integer[j];

                //Generate the array
                for(int k = 0; k < j; k++) {
                    arr[k] = random.nextInt(j);
                }

                //Sort the array in reverse manner
                Arrays.sort(arr, Collections.reverseOrder());
                return arr;
            };

            //Time taken by insertion sort to run 10 times
            double time = benchTimer.runFromSupplier(supplier, 10);

            System.out.println("Value of N: " + i + " Order Type- Reverse" + " Time Taken: " + time);
        }



    }
    private final String description;
    private final UnaryOperator<T> fPre;
    private final Consumer<T> fRun;
    private final Consumer<T> fPost;

    final static LazyLogger logger = new LazyLogger(Benchmark_Timer.class);
}