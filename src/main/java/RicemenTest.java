import java.lang.reflect.Method;

public class RicemenTest {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Method lengthMethod = String.class.getMethod("length");
		System.out.println(lengthMethod);
	}
}
