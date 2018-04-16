import java.util.ArrayList;
import java.util.List;

public class IntMulStreams extends IntMul {

    @Override
    public BigInt mul(BigInt i, BigInt j) {
        List<Integer[]> collec = new ArrayList<Integer[]>(i.getInternal().length);

        List<Tuple<Integer, Integer>> tuples = new ArrayList<>(i.getInternal().length); // the first element is index,
                                                                                        // the second element to multiply
        for (int k = 0; k < i.getInternal().length; k++) {
            tuples.add(new Tuple<>(k, i.getInternal()[k]));
        }

        tuples.stream().parallel()
                       .forEach(x -> mulList(x, j.getInternal(), collec));

        Integer [] id = new Integer[i.getInternal().length];
        for (int k = 0; k < id.length; k++) {
            id[k] = 0;
        }

        Integer[] reduced = collec.stream().reduce(id, this::addLists);

        int[] result = new int[i.getInternal().length];
        for (int k = 0; k < i.getInternal().length; k++) {
            result[k] = reduced[k];
        }
        return new BigInt(result);
    }

    private void addList(int x, int[] arr, List<Integer[]> collec) {
        Integer[] i = new Integer[arr.length];
        for (int j = 0; j < arr.length; j++)
            i[j] = arr[j] * x;
        collec.add(i);
    }

    private void mulList(Tuple<Integer, Integer> tuple, int[] arr, List<Integer[]> collec) {
        Integer[] i = new Integer[arr.length];
        int index = tuple.getFirst();
        int elem = tuple.getSecond();

        for (int j = 0; j < index; j++) {
            i[j] = 0;
        }

        for (int j = 0; j < arr.length-index; j++) {
            i[j+index] = arr[j] * elem;
        }

        collec.add(i);
    }

    private Integer[] addLists(Integer[] a, Integer[] b) {
        boolean carry = false;

        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] + b[i];
            switch (a[i]) {
                case 0:
                    a[i] = carry ? 1 : 0;
                    carry = false;
                    break;
                case 1:
                    a[i] = carry ? 0 : 1;
                    break;
                case 2:
                    a[i] = carry ? 1 : 0;
                    carry = true;
                    break;
            }
        }
        return a;
    }

    public static void main(String[] args) {
        BigInt l = new BigInt(4);
        BigInt r = new BigInt(15);
        IntMulStreams streams = new IntMulStreams();
        BigInt result = streams.mul(l, r);

        if (result.getInteger() != 60)
            System.out.println("the algorithm is wrong, expected 60;\n" +
                    "but got " + result);

        BigInt l2 = streams.makeBigInt1();
        BigInt r2 = streams.makeBigInt2();
        result = streams.mul(l2, r2);

    }

    BigInt makeBigInt1() { // 85899439
        int[] arr = new int[64];
        for (int i = 0; i < 64; i++) {
            arr[i] = 0;
        }
        arr[26] = 1;
        arr[24] = 1;
        arr[20] = 1;
        arr[19] = 1;
        arr[18] = 1;
        arr[17] = 1;
        arr[15] = 1;
        arr[13] = 1;
        arr[12] = 1;
        arr[11] = 1;
        arr[0] = 1;
        arr[1] = 1;
        arr[2] = 1;
        arr[3] = 1;
        arr[5] = 1;
        arr[7] = 1;

        return new BigInt(arr);
    }

    BigInt makeBigInt2() { // 541768
        int[] arr = new int[64];
        for (int i = 0; i < 64; i++) {
            arr[i] = 0;
        }
        arr[19] = 1;
        arr[14] = 1;
        arr[10] = 1;
        arr[6] = 1;
        arr[3] = 1;
        return new BigInt(arr);
    }

    private class Tuple<T, U> {
        T first;
        U second;

        Tuple(T first, U second) {
            this.first = first;
            this.second = second;
        }

        T getFirst() {
            return first;
        }

        U getSecond() {
            return second;
        }
    }
}
