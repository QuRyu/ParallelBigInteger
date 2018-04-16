// the main entry to the project

public class Driver {
    private static long sequentialRuntime = 0;
    private final static int NUM_PROCESSORS =
                Runtime.getRuntime().availableProcessors();

    // numbers for test
    private final static BigInt mulL = makeBigInt1();
    private final static BigInt mulR = makeBigInt2();

    public static void main(String[] args) {
        System.out.println("Number of processors: " + NUM_PROCESSORS);
        test("Sequential Version:", new IntMulSequential());
        test("Threads Version:", new IntMulThreads());
        test("Fork Join Version", new IntMulForkJoin());
        test("Streams Version", new IntMulStreams());
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

    static BigInt makeBigInt1() { // 85899439
        int[] arr = new int[64];
        for (int i = 0; i < 64; i++) {
            arr[i] = 0;
        }
        arr[26] = 1;
        arr[24] = 1;
        arr[20] = 1;
        arr[19] = 1;
        arr[18] = 1;
        arr[17] = 1;
        arr[15] = 1;
        arr[13] = 1;
        arr[12] = 1;
        arr[11] = 1;
        arr[0] = 1;
        arr[1] = 1;
        arr[2] = 1;
        arr[3] = 1;
        arr[5] = 1;
        arr[7] = 1;

        return new BigInt(arr);
    }

    static BigInt makeBigInt2() { // 541768
        int[] arr = new int[64];
        for (int i = 0; i < 64; i++) {
            arr[i] = 0;
        }
        arr[19] = 1;
        arr[14] = 1;
        arr[10] = 1;
        arr[6] = 1;
        arr[3] = 1;
        return new BigInt(arr);
    }
}
