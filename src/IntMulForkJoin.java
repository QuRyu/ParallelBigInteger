import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.lang.Math;

/**
 * Not complete yet
 */

public class IntMulForkJoin extends IntMul {

    @Override
    public BigInt mul( BigInt i, BigInt j ) {
        int result;
        result = 0;
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new PrimeAction(i, j, result));
        int r;
        r = (int) result;
        BigInt f;
        f = new BigInt(r);
        return f;
    }

    class PrimeAction extends RecursiveAction
    {
        private BigInt i, j;
        private int result;

        public PrimeAction(BigInt i, BigInt j, int result) {
            this.i = i;
            this.j = j;
            this.result = result;
        }

        @Override
        protected void compute() {
        	// Algorithm implementation
        	int[] i_i, j_j,il, ih, jl, jh;
          il = new int[] { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
          ih = new int[] { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
          jl = new int[] { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
          jh = new int[] { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };

        	i_i = i.getInternal();
        	j_j = j.getInternal();

          int len_i, len_j, len;
          len_i = (int) (Math.log(i.getInteger())/Math.log(2)+1);
          len_j = (int) (Math.log(j.getInteger())/Math.log(2)+1);
          len = Math.max(len_i, len_j);

          System.out.println(len);
          if (len%2!=0){
            len++;
          }
        	// Get high bits
        	for( int i = 0; i < len/2; i++ ) {
            il[i] = i_i[i];
            jl[i] = j_j[i];
        	}

        	// Get low bits
        	for( int i = len/2; i < len; i++ ) {
            ih[i-len/2] = i_i[i];
            jh[i-len/2] = j_j[i];
        	}

          BigInt ih_bi, il_bi, jh_bi, jl_bi;
          ih_bi = new BigInt(ih);
          il_bi = new BigInt(il);
          jh_bi = new BigInt(jh);
          jl_bi = new BigInt(jl);

          BigInt p1, p2, p3;
          p2 = bimul(ih_bi, jh_bi);
          p3 = bimul(il_bi, jl_bi);
          p1 = bimul(add(ih_bi, il_bi), add(jh_bi, jl_bi));

          int p1_d, p2_d, p3_d;
          p1_d = p1.getInteger();
          p2_d = p2.getInteger();
          p3_d = p3.getInteger();
          System.out.println(p1_d);
          System.out.println(p2_d);
          System.out.println(p3_d);
          System.out.println((p1.getInteger() - p2.getInteger() - p3.getInteger()));
          this.result =
            (int)(p2.getInteger()*(Math.pow(2,len)) +
            (p1.getInteger() - p2.getInteger() - p3.getInteger())*Math.pow(2,len/2)
            + p3.getInteger());
          System.out.println("result " + this.result);


        }


    public BigInt bimul(BigInt i, BigInt j) {
    	//get array of binary representations
        int[] bi_i = i.getInternal();
      	int[] bi_j = j.getInternal();
      	int[] resultBi = new int[200];
        // System.out.println("bi_i " + bi_i.length);
        // System.out.println("bi_j " + bi_j.length);
        // System.out.println("-------------------");

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

    public BigInt add(BigInt i, BigInt j) {
    	//get array of binary representations
        int[] bi_i = i.getInternal();
      	int[] bi_j = j.getInternal();
      	int[] resultBi = new int[80];

      	//binary multiplication
      	for (int a = 0; a < bi_i.length; a++){
            resultBi[a] += bi_i[a] + bi_j[a];
            resultBi[a+1] += resultBi[a]/2;
            resultBi[a] %= 2;
      	}

      	BigInt result = new BigInt(resultBi);
      	return result;
    }

    }

}
