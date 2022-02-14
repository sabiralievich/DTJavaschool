package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {


    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */

    public boolean find(List x, List y) {

         // TODO: Implement the logic here
        if ((x == null) || (y == null)) throw new IllegalArgumentException();
        else {
            if (x.isEmpty()) return true;
            if (y.isEmpty()) return false;

            int index = 0;
            boolean check = false;

            for (int i = 0; i < x.size(); i++) {

                if (!y.contains(x.get(i))) {
                    check = false;
                    break;
                } else {

                    int j = index;

                    while (j < y.size()) {

                        if (x.get(i) != y.get(j)) {
                            j++;
                            if (check) check = false;
                        } else {
                            check = true;
                            index = j;
                            break;
                        }
                    }

                    if ((j == y.size()) && (i != x.size())) {
                        check = false;
                        break;
                    }
                }
            }
            return check;

        }
    }
}
