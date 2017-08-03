package sk.kadlecek.mle.ml.factory;

import sk.kadlecek.mle.ml.bean.ClassifierWithProperties;

import java.util.*;

import static sk.kadlecek.mle.ml.Common.generateIntRange;

public abstract class AbstractAlgorithmFactory {


    public abstract ClassifierWithProperties[] generateAllClassifiers();

    /**
     * Generates the cartesian product of all positions accross arrays, e.g.
     * for input array lengths (2,3)
     * returns: [[0,0],[0,1],[0,2],[1,0],[1,1],[1,2]]
     * Which than can be used to retrieve the cartesian product of array elements.
     * If any of the arrays has length of 0, it yields just one position, '-1'.
     * @return
     */
    protected int[][] generateArrayIndexCartesianProduct(int... arrayLengths) {
        int[][] ranges = generateArrayLengthRanges(arrayLengths);

        // calculate cartesian product of array ranges
        if (ranges.length > 0) {
            if (ranges.length > 1) {
                // at least 2 arrays, do the cartesian product
                return cartesianProduct(ranges);
            }else {
                // just one array, return its the range
                return ranges;
            }
        }else {
            return new int[0][];
        }
    }

    private int[][] generateArrayLengthRanges(int... arrayLengths) {
        int[] emptyRange = {-1};

        int[][] ranges = new int[arrayLengths.length][];
        for (int i = 0; i < arrayLengths.length; i++) {
            if (arrayLengths[i] > 0) {
                ranges[i] = generateIntRange(0, arrayLengths[i] - 1, 1);
            }else {
                ranges[i] = emptyRange;
            }
        }
        return ranges;
    }

    private int[][] cartesianProduct(int[]... arrays) {
        Queue<int[]> queue = buildQueue(arrays);
        return processCartesianProductOfQueue(queue);
    }

    private int[][] cartesianProduct(int[][] firstArray, int[] secondArray) {
        List<int[]> resultLst = new ArrayList<>();

        for (int i = 0; i < firstArray.length; i++) {
            for (int j = 0; j < secondArray.length; j++) {

                int[] firstArrayElem = firstArray[i];
                int secondArrayElem = secondArray[j];

                resultLst.add(addElementToArray(firstArrayElem, secondArrayElem));
            }
        }
        return resultLst.toArray(new int[resultLst.size()][]);
    }

    private int[] addElementToArray(int[] array, int elem) {
        int[] newArray = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[array.length] = elem;
        return newArray;
    }


    private Queue<int[]> buildQueue(int[][] arrayOfRanges) {
        Queue<int[]> queue = new LinkedList<>();
        Collections.addAll(queue, arrayOfRanges);
        return queue;
    }

    private int[][] processCartesianProductOfQueue(Queue<int[]> queue) {
        int[][] firstArray = null;
        int[] secondArray;

        if (queue.size() > 1) {
            while (!queue.isEmpty()) {
                if (firstArray == null) {
                    int[] firstArrayTmp = queue.remove();
                    firstArray = new int[firstArrayTmp.length][1];
                    for (int i = 0; i < firstArrayTmp.length; i++) {
                        firstArray[i][0] = firstArrayTmp[i];
                    }
                }
                secondArray = queue.remove();
                firstArray = cartesianProduct(firstArray, secondArray);
            }
            return firstArray;
        }else {
            int[][] oneElemArr = new int[1][0];
            oneElemArr[0] = queue.remove();
            return oneElemArr;
        }
    }

}
