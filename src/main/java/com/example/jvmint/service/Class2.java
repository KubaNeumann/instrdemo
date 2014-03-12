package com.example.jvmint.service;

public class Class2 implements SomeService{

	public Class2() {
	 	System.out.println("Class2 instance initialization");
	}

	public void doSomething() {
		System.out.println("Method from Class2");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
