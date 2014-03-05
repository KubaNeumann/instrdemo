package com.example.jvmint.instr;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class JavassistSimpleTransformer implements ClassFileTransformer {

	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] bytes) throws IllegalClassFormatException {

		byte[] result = bytes;

		if (className.contains("Class1") || className.contains("Class2")) {
			try {
				String dotClassName = className.replace('/', '.');

				ClassPool cp = ClassPool.getDefault();
				CtClass ctClazz = cp.get(dotClassName);
				CtMethod method1 = ctClazz.getDeclaredMethod("doSomething");

				method1.insertBefore("{System.out.println(\"Calling doSomething() ... \");}");
				result = ctClazz.toBytecode();
			}

			catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
