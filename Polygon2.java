import java.awt.Color;

public class Polygon2{

	public static void execute(double x1, double y1, double x2, double y2, double x3, double y3){

		String filename1 = "picture.jpg";
		String filename2 = filename1;
		MyImage input = JpegFileReader.read(filename1);
		MyImage output = new MyImage(input.width, input.height);
		int redSum = 0;
		int greenSum = 0;
		int blueSum = 0;
		int counter = 0;

		for(int i = 0; i < input.height; i++) {
			for(int j = 0; j < input.width; j++) {
				if(inside(j, i, x1, x2, x3, y1, y2, y3)){//選ばれた3点からなる三角形の内側なら
					//System.out.println("inside");
					counter ++;
					Color color = input.getColor(j, i);
					redSum += color.getRed();
					greenSum += color.getGreen();
					blueSum += color.getBlue();
				}
				else{
					Color color = input.getColor(j, i);
					output.setColor(j, i, color);
				}
			}
		}

		Color averageColor = new Color(redSum/counter, greenSum/counter, blueSum/counter);
		int maxX = returnMax(x1, x2, x3);
		int maxY = returnMax(y1, y2, y3);
		//System.out.println("maxX: "+maxX+", maxY: "+maxY);
		int minX = returnMin(x1, x2, x3);
		int minY = returnMin(y1, y2, y3);
		//System.out.println("minX: "+minX+", minY: "+minY);

		for(int i = minY; i < maxY; i++) {
			for(int j = minX; j < maxX; j++) {//塗り直す範囲を最小にする
				if(inside(j, i, x1, x2, x3, y1, y2, y3)){//選ばれた3点からなる三角形の内側なら
					//System.out.println("set averageColor");
					output.setColor(j, i, averageColor);
				}
				else{
					Color color = input.getColor(j, i);
					output.setColor(j, i, color);
				}
			}
		}


		System.out.println("("+x1+","+y1+"), ("+x2+","+y2+"), ("+x3+","+y3+")");

		JpegFileWriter.write(filename2, output);
		System.out.println("done");
	}

	//与える点が、3点で囲まれる三角形の内側かどうかを判断。
	public static boolean inside(int x, int y, double x1, double x2, double x3, double y1, double y2, double y3){
		//(x1, y1),(x2, y2),(x3, y3)の三角形
		//(x, y)が判定したい三角形
		double vecAPx = x - x1;
		double vecAPy = y - y1;
		double vecABx = x2 - x1;
		double vecABy = y2 - y1;
		double vecACx = x3 - x1;
		double vecAcy = y3 - y1;

		double bunbo = vecABx * vecAcy - vecABy * vecACx;
		if (bunbo == 0){
			System.out.println("Error: 0除算");
			System.out.println("("+x1+","+y1+"), ("+x2+","+y2+"), ("+x3+","+y3+"), ("+x+","+y+")");
			System.exit(0);
		}

		double s = (vecAPx * vecAcy - vecAPy * vecACx) / bunbo;
		double t = (vecAPy * vecABx - vecAPx * vecABy) / bunbo;


		if((0<s) && (s<1) && (0<t) && (t<1) && (0<(1.-s-t)) && ((1.-s-t) < 1)){
		    return true; //Inside Triangle
		}
		else{
		    return false;
		}
	}

	static int returnMax(double a, double b, double c){ //３つの値の中で最大の値を返す
		double tmp = (a  > b)  ? a : b;
		double ans = (tmp > c) ? tmp : c;
		return (int)ans;
	}

	static int returnMin(double a, double b, double c){ //３つの値の中で最小の値を返す
		double tmp = (a < b)  ? a : b;
		double ans = (tmp < c) ? tmp : c;
		return (int)ans;
	}


}
