public class BigInt {
    private int [] internal;

    public BigInt(int i) {
        internal = new int[32];
        intDivision(i);
    }

    public int getBit(int i) {
        if (i > internal.length)
            throw new ArrayIndexOutOfBoundsException();
        else
            return internal[i];
    }

    public void setBit(int i, int bit) {
        if (i > internal.length)
            throw new ArrayIndexOutOfBoundsException();
        else
            internal[i] = bit;
    }

    public int[] getInternal() {
        return internal;
    }

    private void intDivision(int i) {
        boolean neg;

        neg = i < 0;
        for (int j=0; j<31; j++) {
            internal[j] = i % 2;
            i = i/2;
        }

        internal[31] = neg ? 1 : 0;
    }

}
