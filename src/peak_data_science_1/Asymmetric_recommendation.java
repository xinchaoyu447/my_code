package peak_data_science_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Asymmetric_recommendation {
	double[][] sim;
	double[][] Intimacy;

	double[][] ratingMatrix_test;//���Լ�����
	int userNumber;//�û���
	int itemNumber;//��Ʒ��
	int number_hit = 0;//��¼���㷨�����еĸ���
	int number_all = 0;//��¼���㷨���Ƽ��ĸ���
	int comparison_number_hit = 0;//��¼Ƥ��ѷ���ƶ����еĸ���
	int comparison_number_all = 0;//��¼Ƥ��ѷ���ƶ����еĸ���
	double[][] ratingMatrix;//ѵ��������
	double[] average_rating;//ƽ������
	double[][] conf;//�㷨�����ζȾ���
	double[] intimacy_total_rating_denominator;//�����е����ܶȵļ��㹫ʽ�ķ�ĸ
	double[] intimacy_total_rating_molecular;//�����е����ܶȵļ��㹫ʽ�ķ���
	/*
	 * ��ȡѵ�����ļ�
	 */

	public Asymmetric_recommendation(int paraUsers, int paraItems) throws Exception {
		userNumber = paraUsers;
		itemNumber = paraItems;
		ratingMatrix = new double[paraUsers][paraItems];
		String tempReaderfile = "E:/eclipse/eclipse/data/newRating_part1.txt"; // newRatings2
		File tempFile = null;
		BufferedReader tempBufReader = null;

		String tempString = null;
		int tempRating = 0;
		int tempUserIndex = 0;
		int tempItemIndex = 0;
		String[] tempStrArray = null;

		// Compute values of arrays�������ֵ
		tempFile = new File(tempReaderfile);
		if (!tempFile.exists()) {
			return;
		} // Of if
		tempBufReader = new BufferedReader(new FileReader(tempFile));

		// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
		while ((tempString = tempBufReader.readLine()) != null) {
			// ��ʾ�к�
			tempStrArray = tempString.split(",");
			tempUserIndex = Integer.parseInt(tempStrArray[0]);
			tempItemIndex = Integer.parseInt(tempStrArray[1]);
			tempRating = Integer.parseInt(tempStrArray[2]);
			ratingMatrix[tempUserIndex][tempItemIndex] = tempRating;
			// System.out.println("tempUserIndex" + tempUserIndex +
			// "tempItemIndex" + tempItemIndex + "rating" + tempRating);
		} // Of while
			// printMatrix(ratingMatrix);
		tempBufReader.close();
		// System.out.println(userNumber + "dda"+ itemNumber);
	}// Of the first constructor


	/*
	 * ��ȡ���Լ��ļ�
	 */
	public void read_data_set(int paraUsers, int paraItems) throws Exception {
		userNumber = paraUsers;
		itemNumber = paraItems;
		ratingMatrix_test = new double[paraUsers][paraItems];
		String tempReaderfile = "E:/eclipse/eclipse/data/newRating_part2.txt"; // newRatings2
		File tempFile = null;
		BufferedReader tempBufReader = null;

		String tempString = null;
		int tempRating = 0;
		int tempUserIndex = 0;
		int tempItemIndex = 0;
		String[] tempStrArray = null;

		// Compute values of arrays�������ֵ
		tempFile = new File(tempReaderfile);
		if (!tempFile.exists()) {
			return;
		} // Of if
		tempBufReader = new BufferedReader(new FileReader(tempFile));

		// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
		while ((tempString = tempBufReader.readLine()) != null) {
			// ��ʾ�к�
			tempStrArray = tempString.split(",");
			tempUserIndex = Integer.parseInt(tempStrArray[0]);
			tempItemIndex = Integer.parseInt(tempStrArray[1]);
			tempRating = Integer.parseInt(tempStrArray[2]);
			ratingMatrix_test[tempUserIndex][tempItemIndex] = tempRating;
			// System.out.println("tempUserIndex" + tempUserIndex +
			// "tempItemIndex" + tempItemIndex + "rating" + tempRating);
		} // Of while
			// printMatrix(ratingMatrix);
		tempBufReader.close();
		// System.out.println(userNumber + "dda"+ itemNumber);
	}// Of the first constructor


	/*
	 * ����Ƥ��ѷ��ʽ�������ƶ�
	 */
	public void pearson() {
		int tempItemIndex = itemNumber;
		int tempUserIndex = userNumber;
		double pearson_molecular = 0;
		double pearson_denominator_1 = 0;
		double pearson_denominator_2 = 0;
		average_rating = new double[tempUserIndex];
		sim = new double[tempUserIndex][tempUserIndex];
		int count = 0;
		double total_rating = 0;
		for (int i = 0; i < tempUserIndex; i++) {
			for (int j = 0; j < tempItemIndex; j++) {
				if (ratingMatrix[i][j] != 0) {
					total_rating = ratingMatrix[i][j] + total_rating;
					count++;
				}

			} // for j
			average_rating[i] = (total_rating / count);
			// System.out.println("tempUserIndex == " + i + "average_rating " +
			// average_rating[i]);
		} // for i
		for (int i = 0; i < tempUserIndex; i++) {
			for (int j = 0; j < tempUserIndex; j++) {
				if (i != j) {
					for (int k = 0; k < tempItemIndex; k++) {
						if (ratingMatrix[i][k] != 0 && ratingMatrix[j][k] != 0) {
							pearson_molecular = pearson_molecular + ((ratingMatrix[i][k] - average_rating[i])
									* (ratingMatrix[j][k] - average_rating[j]));
							pearson_denominator_1 = pearson_denominator_1
									+ Math.pow((ratingMatrix[i][k] - average_rating[i]), 2);
							pearson_denominator_2 = pearson_denominator_2
									+ Math.pow((ratingMatrix[j][k] - average_rating[j]), 2);
						} // of if

					} // for k
				} // of if

				sim[i][j] = pearson_molecular / (Math.sqrt(pearson_denominator_1) * Math.sqrt(pearson_denominator_2));
				// System.out.println(pearson_molecular);
				// System.out.println("1 + " +
				// Math.sqrt(pearson_denominator_1));
				// System.out.println(Math.sqrt(pearson_denominator_2));
				// System.out.println(sim[i][j]);
				// System.out.println(pearson_denominator_2);
				// System.out.println(pearson_molecular/((Math.sqrt(pearson_denominator_1))*(Math.sqrt(pearson_denominator_2))));
			} // for j

		} // for i
	}


	/*
	 * ���������ж�������ܶ�
	 */
	public void Intimacy() {

		int tempItemIndex = itemNumber;
		int tempUserIndex = userNumber;
		Intimacy = new double[tempUserIndex][tempUserIndex];
		intimacy_total_rating_denominator = new double[tempUserIndex];
		intimacy_total_rating_molecular = new double[tempUserIndex];
		intimacy_total_rating_denominator = new double[tempUserIndex];
		for (int i = 0; i < tempUserIndex; i++) {
			double temp_total_denominator = 0;
			for (int k = 0; k < tempItemIndex; k++) {
				if (ratingMatrix[i][k] != 0) {
					temp_total_denominator = temp_total_denominator + 1;
				}

			}
			intimacy_total_rating_denominator[i] = temp_total_denominator;
		}
		for (int i = 0; i < tempUserIndex; i++) {
			for (int j = 0; j < tempUserIndex; j++) {
				if (i != j) {
					double temp_total_molecular = 0;
					for (int k = 0; k < tempItemIndex; k++) {
						if (ratingMatrix[i][k] != 0 && ratingMatrix[j][k] != 0
								&& Math.abs((ratingMatrix[i][k] - ratingMatrix[j][k])) <= 2) {
							temp_total_molecular = temp_total_molecular + 1;
						} // of if
					} // for k
					intimacy_total_rating_molecular[i] = temp_total_molecular;
				} // of if
				Intimacy[i][j] = (intimacy_total_rating_molecular[i]) / (intimacy_total_rating_denominator[i]);
			} // for j
		} // for i
	}


	/*
	 * ���������ж�������ζȲ���ȡ���ζ���ߵ�ǰ����������û�0���ζ���ߵ���1,2,3,4,5��Ȼ��ֱ����������û��ڲ��Լ���
	 * �Ե�Ӱ�����ִ��ڵ���������Ϊ�Ƽ����Ƽ���+ 1����¼һ�����Ƽ�������Ȼ���ڲ��Լ������û�0�Ը����Ӱ��������������+1.
	 */
	public void confidence() {
		int tempUserIndex = userNumber;
		int tempItemIndex = itemNumber;
		conf = new double[tempUserIndex][tempUserIndex];
		for (int i = 0; i < tempUserIndex; i++) {
			for (int j = 0; j < tempUserIndex; j++) {
				if (i != j) {
					conf[i][j] = (2 * Intimacy[i][j] * sim[i][j])
							/ (Intimacy[i][j] + sim[i][j]);
				} // of if
				else if (i == j) {
					conf[i][j] = 0;
				}
				// }
			} // for j
		} // for i
			// System.out.println(conf[0].length);

		for (int i = 0; i < conf[0].length; i++) {
			double[][] tempconf = new double[conf.length][3];
			for (int j = 0; j < tempconf.length; j++) {
				tempconf[j][0] = conf[j][i];
				tempconf[j][1] = i;
				tempconf[j][2] = j;
			} // of for j
			double[] temp = null;
			for (int j = 0; j < tempconf.length; j++) {

				for (int k = j + 1; k < tempconf.length; k++) {

					if (tempconf[j][0] < tempconf[k][0]) {
						temp = tempconf[j];
						tempconf[j] = tempconf[k];
						tempconf[k] = temp;
					} // of if
				} // of for k

			} // of for j
				// System.out.println(Arrays.deepToString(tempconf));
			for (int j = 0; j < 5; j++) {

				for (int l2 = 0; l2 < tempItemIndex; l2++) {

					if (ratingMatrix[(int) tempconf[j][2]][l2] >= 3) {
						number_all++;
					}
					if (ratingMatrix_test[(int) tempconf[j][1]][l2] != 0
							&& ratingMatrix[(int) tempconf[j][2]][l2] >= 3) {
						number_hit++;
					} // of if

				} // for l2

			} // for j
		} // of for i

		System.out.println("ѵ�����Ƽ��ĸ���" + number_all);
		System.out.println("���Լ������еĸ���" + number_hit);
		System.out.println("������ / �Ƽ���" + (double) number_hit / number_all);
	}

	/*
	 * ��Ƥ��ѷ��ʽ�õ������ƶȣ����ڶԱȡ�
	 */
	public void sim() {
		int tempUserIndex = userNumber;
		int tempItemIndex = itemNumber;
		for (int i = 0; i < sim[0].length; i++) {
			double[][] tempsim = new double[sim.length][3];
			for (int j = 0; j < tempsim.length; j++) {
				tempsim[j][0] = sim[j][i];
				tempsim[j][1] = i;
				tempsim[j][2] = j;
			} // of for j
			double[] temp = null;
			for (int j = 0; j < tempsim.length; j++) {

				for (int k = j + 1; k < tempsim.length; k++) {

					if (tempsim[j][0] < tempsim[k][0]) {
						temp = tempsim[j];
						tempsim[j] = tempsim[k];
						tempsim[k] = temp;
					} // of if
				} // of for k

			} // of for j
				// System.out.println(Arrays.deepToString(tempsim));
			for (int j = 0; j < 5; j++) {

				for (int l2 = 0; l2 < tempItemIndex; l2++) {

					if (ratingMatrix[(int) tempsim[j][2]][l2] >= 3) {
						comparison_number_all++;
					}
					if (ratingMatrix_test[(int) tempsim[j][1]][l2] != 0 && ratingMatrix[(int) tempsim[j][2]][l2] >= 3) {
						comparison_number_hit++;
					} // of if

				} // for l2

			} // for j

			// ����дtop5
		} // of for i

		System.out.println("ѵ�����Ƽ��ĸ���" + comparison_number_all);
		System.out.println("���Լ������еĸ���" + comparison_number_hit);
		System.out.println("������ / �Ƽ���" + (double) comparison_number_hit / comparison_number_all);

	}

	public static void main(String args[]) {
		try {
			Asymmetric_recommendation tempcomputePoint = new Asymmetric_recommendation(943, 1682);

			tempcomputePoint.pearson();

			tempcomputePoint.Intimacy();

			tempcomputePoint.read_data_set(943, 1682);

			tempcomputePoint.confidence();
			tempcomputePoint.sim();

		} catch (Exception ee) {
			ee.printStackTrace();
		} // of try
	}// Of main
}
