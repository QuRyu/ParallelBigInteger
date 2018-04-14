

public class IntMulSequential extends IntMul {
    @Override
    public BigInt mul(BigInt i, BigInt j) {
    	//get array of binary representations
        int[] bi_i = i.getInternal();
      	int[] bi_j = j.getInternal();
      	int[] resultBi = new int[100];

      	//binary multiplication
      	for (int a = 0; bi_i[a]!=-1; ++a){
        	for (int b = 0; bi_j[b]!=-1; ++b){
            	resultBi[a+b] += bi_i[a] * bi_j[b] % 2;
            	resultBi[a+b] %= 2;
            	resultBi[a+b+1] += resultBi[a+b]/2;
        	}
      	}
      	
      	BigInt result = new BigInt(resultBi);
      	return result;
    }


    public static void main(String[] args) {
    	BigInt mulL = new BigInt(23231);
    	BigInt mulR = new BigInt(51431);
    	IntMulSequential test = new IntMulSequential();
    	test.mul(mulL, mulR); 
    	
    }
}
