public abstract class IntMul {

    // the method to multiply two integers
    public abstract BigInt mul(BigInt i, BigInt j);
      int[] bi_i = i.getInternal();
      int[] bi_j = j.getInternal();
      int[] resultBi = new int[100];

      for (int i = 0; bi_i[i]!=-1; ++i){
        for (int j = 0; bi_j[j]!=-1; ++j){
            resultBi[i+j] += bi_i[i] * bi_j[j] % 2;
            resultBi[i+j] %= 2;
            resultBi[i+j+1] += resultBi[i+j]/2;
        }
      }

}
