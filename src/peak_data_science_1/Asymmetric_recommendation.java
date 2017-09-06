package peak_data_science_1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Asymmetric_recommendation {
	double [][] sim;
	double [][] Intimacy;
	
	double [][] ratingMatrix_test;
	int userNumber;
	int itemNumber;
	int number_hit = 0;
	int number_all = 0;
	double [][] ratingMatrix;
	double [] average_rating;
	double[][] conf;
	double[][] conf_for_comparison;
	double [] intimacy_total_rating_denominator;
	double [] intimacy_total_rating_molecular;
	double temp_comparison = 0.0;
	public Asymmetric_recommendation(int paraUsers, int paraItems) throws Exception{
		userNumber = paraUsers;
		itemNumber = paraItems;
		ratingMatrix = new double[paraUsers][paraItems];
		String tempReaderfile = "E:/eclipse/eclipse/data/newRating_part1.txt"; //newRatings2
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
		}// Of if
		tempBufReader = new BufferedReader(new FileReader(tempFile));

		// 一次读入一行，直到读入null为文件结束
		while ((tempString = tempBufReader.readLine()) != null) {
			// 显示行号
			tempStrArray = tempString.split(",");
			tempUserIndex = Integer.parseInt(tempStrArray[0]);
			tempItemIndex = Integer.parseInt(tempStrArray[1]);
			tempRating = Integer.parseInt(tempStrArray[2]);
			ratingMatrix[tempUserIndex][tempItemIndex] = tempRating;
			//System.out.println("tempUserIndex" + tempUserIndex + "tempItemIndex" + tempItemIndex + "rating" + tempRating);
		}// Of while
//		printMatrix(ratingMatrix);
		tempBufReader.close();
		//System.out.println(userNumber + "dda"+ itemNumber);
	}//Of the first constructor
	
	public void read_data_set(int paraUsers, int paraItems) throws Exception{
		userNumber = paraUsers;
		itemNumber = paraItems;
		ratingMatrix_test = new double[paraUsers][paraItems];
		String tempReaderfile = "E:/eclipse/eclipse/data/newRating_part2.txt"; //newRatings2
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
		}// Of if
		tempBufReader = new BufferedReader(new FileReader(tempFile));

		// 一次读入一行，直到读入null为文件结束
		while ((tempString = tempBufReader.readLine()) != null) {
			// 显示行号
			tempStrArray = tempString.split(",");
			tempUserIndex = Integer.parseInt(tempStrArray[0]);
			tempItemIndex = Integer.parseInt(tempStrArray[1]);
			tempRating = Integer.parseInt(tempStrArray[2]);
			ratingMatrix_test[tempUserIndex][tempItemIndex] = tempRating;
			//System.out.println("tempUserIndex" + tempUserIndex + "tempItemIndex" + tempItemIndex + "rating" + tempRating);
		}// Of while
//		printMatrix(ratingMatrix);
		tempBufReader.close();
		//System.out.println(userNumber + "dda"+ itemNumber);
	}//Of the first constructor
	public void pearson(){
		int tempItemIndex = itemNumber;
		int tempUserIndex = userNumber;
		double pearson_molecular = 0;
		double pearson_denominator_1 = 0;
		double pearson_denominator_2 = 0;
		average_rating = new double [tempUserIndex];
		sim = new double [tempUserIndex][tempUserIndex];
		int count = 0;
		double total_rating = 0;
		for(int i = 0; i < tempUserIndex; i ++ ){
			for (int j = 0; j < tempItemIndex; j++) {
				if (ratingMatrix[i][j] != 0) {
					total_rating =  ratingMatrix[i][j] + total_rating;
					count++;
				}
				
			}// for j
			average_rating[i] = (total_rating / count);
			//System.out.println("tempUserIndex == " + i + "average_rating " + average_rating[i]);
		}// for i
		for (int i = 0; i < tempUserIndex; i++) {
			for (int j = 0; j < tempUserIndex; j++) {
				if (i != j) {
					for (int k = 0; k < tempItemIndex; k++) {
						if (ratingMatrix[i][k] != 0 && ratingMatrix[j][k] != 0 ) {
							 pearson_molecular = pearson_molecular + ((ratingMatrix[i][k] - average_rating[i]) *(ratingMatrix[j][k] - average_rating[j]));
							pearson_denominator_1  =  pearson_denominator_1 + Math.pow((ratingMatrix[i][k] - average_rating[i]), 2);
							pearson_denominator_2  =  pearson_denominator_2 + Math.pow((ratingMatrix[j][k] - average_rating[j]), 2);	
						}// of if 

					}//for k
				}// of if
				
				sim[i][j] =pearson_molecular/(Math.sqrt(pearson_denominator_1)*Math.sqrt(pearson_denominator_2)) ;
				//System.out.println(pearson_molecular);
				//System.out.println("1 + " + Math.sqrt(pearson_denominator_1));
				//System.out.println(Math.sqrt(pearson_denominator_2));
				//System.out.println(sim[i][j]);
				if(sim[i][j] > temp_comparison)
				{
					temp_comparison = sim[i][j];
				}
				//System.out.println(pearson_denominator_2);
				//System.out.println(pearson_molecular/((Math.sqrt(pearson_denominator_1))*(Math.sqrt(pearson_denominator_2))));
			}//for j
			
		}// for i  
	}
	
	public void Intimacy(){
		
		
		int tempItemIndex = itemNumber;
		int tempUserIndex = userNumber;
		Intimacy = new double [tempUserIndex][tempUserIndex];
		intimacy_total_rating_denominator = new double[tempUserIndex];
		intimacy_total_rating_molecular = new double[tempUserIndex];
		intimacy_total_rating_denominator = new double[tempUserIndex];
		for (int i = 0; i < tempUserIndex; i++) {
			double temp_total_denominator = 0;
			for (int k = 0; k < tempItemIndex; k++) {
				if (ratingMatrix[i][k] != 0 ) {
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
						if (ratingMatrix[i][k] != 0 && ratingMatrix[j][k] != 0 && Math.abs((ratingMatrix[i][k] - ratingMatrix[j][k])) <= 2) {
							temp_total_molecular = temp_total_molecular + 1;
						}// of if 
					}//for k
					intimacy_total_rating_molecular[i] = temp_total_molecular;
				}// of if
				Intimacy[i][j] = (intimacy_total_rating_molecular[i])/(intimacy_total_rating_denominator[i]);
			}//for j			
		}// for i 
	}
	public void confidence(){
		int tempUserIndex = userNumber;
		int tempItemIndex = itemNumber;
		conf = new double [tempUserIndex][tempUserIndex];
		conf_for_comparison = new double[tempUserIndex][tempUserIndex];
		for (int i = 0; i < tempUserIndex; i++) {
			for (int j = 0; j < tempUserIndex; j++) {
				if (i != j) {
					conf_for_comparison[i][j] = conf[i][j] = (2 * Intimacy[i][j] * sim[i][j] )/(Intimacy[i][j] + sim[i][j]);
					}// of if 
				//}
			}//for j
		}// for i 
		for (int i = 0; i < conf[0].length; i++) {
			double [][] tempconf = new double[conf.length][3];
			for (int j = 0; j < tempconf.length; j++) {
				tempconf[j][0] = conf[j][i];
				tempconf[j][1] = i;
				tempconf[j][2] = j;	
			}// of for j
			double[] temp = null;
			for (int j = 0; j < tempconf.length; j++) {
				for (int k = j + 1; k < tempconf.length; k++) {
					if (tempconf[j][0] < tempconf[k][0]) {
						temp = tempconf[j];
						tempconf[j] = tempconf[k];
						tempconf[k] = temp;
					}// of if
				}// of for k
			}// of for j
			for (int j = 0; j < 5; j++) {
				
		}// for j
			
			//这里写top5
		}// of for i
		for (int i = 0; i < conf.length; i++) {
			for (int j = 0; j < 5; j++) {
				
					for (int k3 = 0; k3 < tempItemIndex; k3++) {
						if (conf[i][j] == conf_for_comparison[j][k3]) {
							for (int l2 = 0; l2 < tempItemIndex; l2++) {
								if (ratingMatrix[j][l2] >= 3) {
									number_all ++;
								}
								if (ratingMatrix_test[j][l2] != 0) {
									number_hit ++;
								}// of if
								
							}// for l2
							
						}// of if 
						
					}// for k3
			}// for j
		}// for i
		System.out.println(number_hit / number_all);
	}
	
	public static void main(String args[]) {
		try {
			Asymmetric_recommendation tempcomputePoint = new Asymmetric_recommendation(943, 1682);
			tempcomputePoint.pearson();
			tempcomputePoint.Intimacy();
			tempcomputePoint.read_data_set(943, 1682);
			tempcomputePoint.confidence();



		} catch (Exception ee) {
			ee.printStackTrace();
		}// of try
	}// Of main
	}

