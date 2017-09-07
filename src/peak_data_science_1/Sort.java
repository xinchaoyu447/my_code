package peak_data_science_1;

import java.util.Arrays;

public class Sort {
	public static void main(String[] args) {
		int[][] vector = { { 1, 2, 5, 4 }, { 7, 8, 4, 3 }, { 8, 9, 4, 6 }, { 4, 5, 6, 2 } };

		for (int i = 0; i < vector[0].length; i++) {
			int[][] tempVector = new int[vector.length][3];
			for (int j = 0; j < tempVector.length; j++) {
				tempVector[j][0] = vector[j][i];
				tempVector[j][2] = j;
				tempVector[j][1] = i;
			} // Of for j

			int[] temp = null;
			for (int j = 0; j < tempVector.length; j++) {
				for (int k = j + 1; k < tempVector.length; k++) {
					if (tempVector[j][0] < tempVector[k][0]) {
						temp = tempVector[j];
						tempVector[j] = tempVector[k];
						tempVector[k] = temp;
					} // Of if
				} // Of for k
			} // Of for j
			for (int j = 0; j < 2; j++) {
				System.out.println(tempVector[j][2]);

			}
			System.out.println(Arrays.deepToString(tempVector));
		} // Of for i

	}// Of main
}// Of sort