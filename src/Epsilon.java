
public class Epsilon {

	public static void main(String[] args) {
		double epsilon = 1.0;
		while(1.0 + 0.5 * epsilon != 1.0){
			epsilon *= 0.5;
		}
		System.out.println(epsilon);
	}

}
