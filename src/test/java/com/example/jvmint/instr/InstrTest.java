package com.example.jvmint.instr;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.jvmint.service.App;

public class InstrTest {
	
	private App app = new App();

	@Test
	public void agentTest(){
		
		app.runService();
		assertTrue(true);
	}

}
