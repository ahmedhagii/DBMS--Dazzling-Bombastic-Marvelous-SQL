package eg.edu.guc.dbms.classes;

import java.util.Scanner;

public class Test implements Runnable{

	static Scanner sc;
	public static void main(String[] args) throws InterruptedException {
		sc = new Scanner(System.in);
		Student s = new Student(sc);
	    Thread x = new Thread(s);
	    Thread y = new Thread(new Test());
	    y.start();
	    x.start();
	   
	    System.out.println(x.isAlive());
	    Thread.sleep(100);
	    s.age = 20;
	}

	@Override
	public void run() {
//		System.out.println("Enter test");
//		int no = sc.nextInt();
//		System.out.println(no)
		int i = 100;
		while(i-- > 0)System.out.println("HAHAHA");
	}

}

class Student implements Runnable{  
	int age;
	Scanner sc;
	public Student(Scanner sc) {
		age = 10;
		this.sc = sc;
	}
	
	public int getAge() {
		return age;
	}

	@Override
	public void run() {
//		System.out.println("Enter Student:");
//		int y = sc.nextInt();
//		System.out.println(y);
		int i = 100;
		while(i>0){System.out.println("I'm in Student: " + getAge());i--;}
	}
	
}