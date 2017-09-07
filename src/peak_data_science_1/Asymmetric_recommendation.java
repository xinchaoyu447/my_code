package peak_data_science_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Asymmetric_recommendation {
	double[][] sim;
	double[][] Intimacy;

	double[][] ratingMatrix_test;//测试集矩阵
	int userNumber;//用户数
	int itemNumber;//商品数
	int number_hit = 0;//记录该算法中命中的个数
	int number_all = 0;//记录该算法中推荐的个数
	int comparison_number_hit = 0;//记录皮尔逊相似度命中的个数
	int comparison_number_all = 0;//记录皮尔逊相似度命中的个数
	double[][] ratingMatrix;//训练集矩阵
	double[] average_rating;//平均评分
	double[][] conf;//算法中信任度矩阵
	double[] intimacy_total_rating_denominator;//论文中的亲密度的计算公式的分母
	double[] intimacy_total_rating_molecular;//论文中的亲密度的计算公式的分子
	/*
	 * 读取训练集文件
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

		// Compute values of arrays数组计算值
		tempFile = new File(tempReaderfile);
		if (!tempFile.exists()) {
			return;
		} // Of if
		tempBufReader = new BufferedReader(new FileReader(tempFile));

		// 一次读入一行，直到读入null为文件结束
		while ((tempString = tempBufReader.readLine()) != null) {
			// 显示行号
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
	 * 读取测试集文件
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

		// Compute values of arrays数组计算值
		tempFile = new File(tempReaderfile);
		if (!tempFile.exists()) {
			return;
		} // Of if
		tempBufReader = new BufferedReader(new FileReader(tempFile));

		// 一次读入一行，直到读入null为文件结束
		while ((tempString = tempBufReader.readLine()) != null) {
			// 显示行号
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
	 * 利用皮尔逊公式计算相似度
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
	 * 计算论文中定义的亲密度
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
	 * 计算论文中定义的信任度并且取信任度最高的前五个，例如用户0信任度最高的有1,2,3,4,5；然后分别算出这五个用户在测试集中
	 * 对电影的评分大于等于三的作为推荐，推荐数+ 1，记录一共的推荐数量，然后在测试集中若用户0对该项电影有评分则命中数+1.
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

		System.out.println("训练集推荐的个数" + number_all);
		System.out.println("测试集中命中的个数" + number_hit);
		System.out.println("命中数 / 推荐数" + (double) number_hit / number_all);
	}

	/*
	 * 用皮尔逊公式得到的相似度，用于对比。
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

			// 这里写top5
		} // of for i

		System.out.println("训练集推荐的个数" + comparison_number_all);
		System.out.println("测试集中命中的个数" + comparison_number_hit);
		System.out.println("命中数 / 推荐数" + (double) comparison_number_hit / comparison_number_all);

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
