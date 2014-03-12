package com.example.jvmint.service;

public class Class1 implements SomeService{

	public Class1() {
	 	System.out.println("Class1 instatnce initialization");
	}

	public void doSomething() {
		System.out.println("Method from Class1");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
