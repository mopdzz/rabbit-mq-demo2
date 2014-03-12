package foo;

public class Too {

	public void receiver(String foo) throws InterruptedException {
		System.out.println(System.currentTimeMillis());
		System.out.println("receiver:" + foo);
		Thread.sleep(2000);
	}
}
