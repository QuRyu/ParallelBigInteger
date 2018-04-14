/*
This is the sequential algorithm for computing two 32 bits integer
Implemented by Tracy and Zilin
*/

public class IntMulSequential extends IntMul {
    @Override
    public BigInt mul(BigInt i, BigInt j) {
    	//get array of binary representations
        int[] bi_i = i.getInternal();
      	int[] bi_j = j.getInternal();
      	int[] resultBi = new int[100];

      	//binary multiplication
      	for (int a = 0; a < bi_i.length; a++){
        	for (int b = 0; b < bi_j.length; b++){
            	resultBi[a+b] += bi_i[a] * bi_j[b] % 2;
            	resultBi[a+b] %= 2;
            	resultBi[a+b+1] += resultBi[a+b]/2;
        	}
      	}
      	for (int k=0;k<resultBi.length ;k++ ) {
      		System.out.println(resultBi[k]);
      	}

      	BigInt result = new BigInt(resultBi);
      	return result;
    }


    //test
    public static void main(String[] args) {
    	BigInt mulL = new BigInt(23231);
    	BigInt mulR = new BigInt(51431);
    	IntMulSequential test = new IntMulSequential();
    	BigInt result = test.mul(mulL, mulR); 
    	// System.out.println(result.toString());
    	// System.out.println(mulL.toString());
    	// System.out.println(mulR.toString());
    	System.out.println("\n");
    	for (int i=0;i<result.getInternal().length ;i++ ) {
    		System.out.println(result.getInternal()[i]);
    	}
    	
    }
}


