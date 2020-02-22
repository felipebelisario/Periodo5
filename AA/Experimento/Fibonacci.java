import java.util.concurrent.TimeUnit;

public class Fibonacci {
	static int f(int n) {
		if (n == 0) return 0;
		if (n == 1) return 1;
		return f(n-1) + f(n-2);
	}

	public static void main(String[] args) {
		for (int i = 0; i <= 45; i++) {
			long startTime = System.currentTimeMillis();
			int x = Fibonacci.f(i);
			long endTime = System.currentTimeMillis();

			long timeElapsed = endTime - startTime;

			System.out.println((double)timeElapsed/1000);
		}
	}
}
