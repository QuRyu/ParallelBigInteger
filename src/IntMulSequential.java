/*
This is the sequential algorithm for computing two 32 bits integer
Implemented by Tracy and Zilin
*/
import java.util.*;


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
            	resultBi[a+b] += bi_i[a] * bi_j[b];
              resultBi[a+b+1] += resultBi[a+b]/2;
            	resultBi[a+b] %= 2;

        	}
      	}

      	BigInt result = new BigInt(resultBi);
      	return result;
    }


    //test
    public static void main(String[] args) {
    	BigInt mulL = new BigInt(235);
    	BigInt mulR = new BigInt(23);
      System.out.println("Number1: " + mulL.toString());
    	System.out.println("Number2: " + mulR.toString());
      System.out.println("Expected result: " + mulL.getInteger()*mulR.getInteger());
    	IntMulSequential test = new IntMulSequential();
    	BigInt result = test.mul(mulL, mulR);
    	System.out.println("Our result: " + result.toString());
    	System.out.println("\n");

    }
}
