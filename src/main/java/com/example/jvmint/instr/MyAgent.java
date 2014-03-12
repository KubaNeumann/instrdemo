package com.example.jvmint.instr;

import java.lang.instrument.Instrumentation;

public class MyAgent {

	public static void premain(String agentArgs, Instrumentation inst) {
		// Transformer registration 
		//inst.addTransformer(new MyClassFileTransformer());
		//inst.addTransformer(new JavassistSimpleTransformer());
		inst.addTransformer(new ASMSimpleTransformer());
	}

}
