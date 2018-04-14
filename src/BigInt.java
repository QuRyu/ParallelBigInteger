// for simplicity the BigInt only represents integers of size supported by Java
// note that it could be extended to handle integers of arbitrary size
//
// index 0 is the least significant bit, and 31 is the most significant bit
public class BigInt {
    private int [] internal;

    public BigInt(int i) {
        internal = new int[32];
        dec2bin(i);
    }

    public BigInt(int [] internal) {
        if (internal.length != 32)
            throw new IllegalArgumentException("the length of array must be 32");
        this.internal = internal;
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

    private void dec2bin(int i) {
        boolean neg;

        neg = i < 0;
        for (int j=0; j<31; j++) {
            internal[j] = i % 2;
            i = i/2;
        }

        internal[31] = neg ? 1 : 0;
    }

}
