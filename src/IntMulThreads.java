//Parallel Version of Integer Multiplication
//Implemented by Zilin


public class IntMulThreads extends IntMul {

    public static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    @Override
    public BigInt mul(BigInt i, BigInt j) {
        // System.out.println(NUM_THREADS);
        // throw new NotImplementedException();
    	int arr_length = i.getInternal().length;
    	String[] results = new String [100];
    	//create threads
    	BigIntThread[] threads = new BigIntThread[NUM_THREADS];
    	for (int x = 0; x < threads.length; x++) {
            threads[x] = new BigIntThread(results, 
                    x * arr_length / NUM_THREADS,  
                    ((x+1) * arr_length / NUM_THREADS )-1, 
                    i, 
                    j); 
            threads[x].start();
        }

        //wait for all threads to finish
        for (BigIntThread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // print out string
        String s = "";
        for (int y=0;y<results.length ;y++ ) {
        	if (results[y] != null){
        		// System.out.println(results[y]);
                s = addBinary(s,results[y]);
        	}
        }
        
        // System.out.println(s);
        //convert to arr
        int [] list = new int [s.length()];
        for (int n=0;n<s.length() ;n++ ) {
            list[n]=s.charAt(n);
        }

        BigInt result = new BigInt(list);
        // System.out.println(result.toString());
        return result;
    }


    //binary addition for two large strings
    public String addBinary(String a, String b) {
        // Start typing your Java solution below
        // DO NOT write main() function
        int la = a.length();
        int lb = b.length();
        
        int max = Math.max(la, lb);
        
        StringBuilder sum = new StringBuilder("");
        int carry = 0;
        
        for(int i = 0; i < max; i++){
            int m = getBit(a, la - i - 1);
            int n = getBit(b, lb - i - 1);
            int add = m + n + carry;
            sum.append(add % 2);
            carry = add / 2;
        }
        
        if(carry == 1)
            sum.append("1");
        
        return sum.reverse().toString();
        
    }
    
    public int getBit(String s, int index){
        if(index < 0 || index >= s.length())
            return 0;
        
        if(s.charAt(index) == '0')
            return 0;
        else
            return 1;
        
    }



    //build thread class
    class BigIntThread extends Thread{
    	private int start, end;
    	private BigInt i, j;
    	private String[] results;

    	public BigIntThread(String[] results, int start, int end, BigInt i1, BigInt i2) {
            this.results = results;
            this.start = start;
            this.end = end;
            i = i1;
            j = i2;
        }

        @Override
        public void run() {
            // System.out.println(this.start + ", " + this.end);
        	//same process in sequential 
            int[] bi_i = i.getInternal();
      		int[] bi_j = j.getInternal();
      		int[] resultBi = new int[100];

      		//compute
      		for (int a = this.start; a < this.end; a++){
	        	for (int b = 0; b < bi_j.length; b++){
	            	resultBi[a+b] += bi_i[a] * bi_j[b];
	              	resultBi[a+b+1] += resultBi[a+b]/2;
	            	resultBi[a+b] %= 2;
	        	}
      		}

      		String s = "";
      		for (int x=0; x<resultBi.length ;x++ ) {
      			s += Integer.toString(resultBi[x]);
      		}	
      		this.results[start] = s;
            // System.out.println(s);
        }

    }
 

    //test
    public static void main(String[] args) {
    	BigInt mulL = new BigInt(2435);
    	BigInt mulR = new BigInt(8240);
    	IntMulThreads test = new IntMulThreads();
    	BigInt result = test.mul(mulL, mulR);
    }
}
