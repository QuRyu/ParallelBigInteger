// the main entry to the project

public class Driver {
    private static long sequentialRuntime = 0;
    private final static int NUM_PROCESSORS =
                Runtime.getRuntime().availableProcessors();

    // numbers for test
    private final static BigInt mulL = new BigInt(23231);
    private final static BigInt mulR = new BigInt(51431);
    private final static BigInt result = new BigInt(1194793561);

    public static void main(String[] args) {
        System.out.println("Number of processors: " + NUM_PROCESSORS);
        int [] testArr = new int[5];
        testArr[0] = 0;
        testArr[1] = 1;
        testArr[2] = 1;
        testArr[3] = 1;
        testArr[4] = 0;
        BigInt test = new BigInt(testArr);
        System.out.println(test.toString());

        test("Parallel Version:", new IntMulThreads());

    }

    private static void test(String version, IntMul im) {
        // warm up
        im.mul(mulL, mulR);
        im.mul(mulL, mulR);

        Timer.start();
        BigInt rel = im.mul(mulL, mulR);
        Timer.stop();

        System.out.println("---------" + version + "----------");

        System.out.println("Multiplier and multiplicand: " + mulL + ", " + mulR);

        System.out.println("Time: " + Timer.getRuntime() + "ms");

        if (sequentialRuntime == 0)
            sequentialRuntime = Timer.getRuntime();
        else
            System.out.printf("Speed-up: %.2f\n",
                    sequentialRuntime/1.0/Timer.getRuntime());
        System.out.println();
    }

}
