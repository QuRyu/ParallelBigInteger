public class Driver {
    private static long sequentialRuntime = 0;
    private final static int NUM_PROCESSORS =
                Runtime.getRuntime().availableProcessors();

    private final static int mulL = 23231;
    private final static int mulR = 51431;
    private final static int result = 1194793561;

    public static void main(String[] args) {
        System.out.println("Number of processors: " + NUM_PROCESSORS);

    }

    private static void test(String version, IntMul im) {
        im.mul(mulL, mulR);
        im.mul(mulL, mulR);

        Timer.start();
        int rel = im.mul(mulL, mulR);
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
