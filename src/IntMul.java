import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class IntMul {

    // the length of each array is 32
    public abstract int mul(BigInt i, BigInt j);

    protected int [] dec2bin(int i) {
       throw new NotImplementedException();
    }

    protected int [] bin2dec(int i) {
        throw new NotImplementedException();
    }

}
