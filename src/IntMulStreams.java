import java.util.ArrayList;
import java.util.List;

public class IntMulStreams extends IntMul {

    @Override
    public BigInt mul(BigInt i, BigInt j) {
        List<Integer[]> collec = new ArrayList<Integer[]>(32);

        List<Tuple<Integer, Integer>> tuples = new ArrayList<>(i.getInternal().length); // the first element is index,
                                                                                        // the second element to multiply
        for (int k = 0; k < i.getInternal().length; k++) {
            tuples.add(new Tuple<>(k, i.getInternal()[k]));
        }

        tuples.stream().parallel()
                       .forEach(x -> mulList(x, j.getInternal(), collec));

        Integer [] id = new Integer[32];
        for (int k = 0; k < id.length; k++) {
            id[k] = 0;
        }

        Integer[] reduced = collec.stream().reduce(id, this::addLists);

        int[] result = new int[32];
        for (int k = 0; k < 32; k++) {
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

        for (int j = 0; j < 32-index; j++) {
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
                    carry = !carry;
                    break;
                case 2:
                    a[i] = carry ? 1 : 0;
                    carry = true;
                    break;
            }
        }
        return a;
    }

    //public static void main(String[] args) {
    //    BigInt l = new BigInt(4);
    //    BigInt r = new BigInt(15);
    //    IntMulStreams streams = new IntMulStreams();
    //    BigInt result = streams.mul(l, r);

    //    if (result.getInteger() != 60)
    //        System.out.println("the algorithm is wrong, expected 60;\n" +
    //                "but got " + result);

    //}

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
