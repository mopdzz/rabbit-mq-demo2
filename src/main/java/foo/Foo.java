package foo;

public class Foo {

	public void listen(String foo) throws InterruptedException {
		System.out.println(System.currentTimeMillis());
		System.out.println("listen:" + foo);
		Thread.sleep(2000);
	}
}