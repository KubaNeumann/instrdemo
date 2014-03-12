package com.example.jvmint.instr;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ASMSimpleTransformer implements ClassFileTransformer {

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] bytes) throws IllegalClassFormatException {

		byte[] result = bytes;

		if (className.contains("Class1") || className.contains("Class2")) {

			ClassWriter cw = new ClassWriter(0);
			ClassReader cr = new ClassReader(bytes);

			// 1. 
	        // cr.accept(cw, 0);
			// result = cw.toByteArray();
			
			// 2. 
			// cr = new ClassReader(bytes);
			// or
//			 try {
//				cr = new ClassReader("java.lang.Integer");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			//cr.accept(new ClassPrinter(), 0);
			//result = null;
			
			// 3.
			 cr.accept(new PrintStringClassAdapter(cw), 0);
			result = cw.toByteArray();
		}

		return result;
	}

	private class PrintStringClassAdapter extends ClassVisitor {

		public PrintStringClassAdapter(ClassVisitor cv) {
			super(Opcodes.ASM4, cv);
		}

		@Override
		public MethodVisitor visitMethod(int access, String name,
				String descriptor, String signature, String[] exceptions) {
			return new PrintStringMethodAdapter(super.visitMethod(access, name,
					descriptor, signature, exceptions), name, descriptor);
		}
	}

	private class PrintStringMethodAdapter extends MethodVisitor {
		private String methodName;
		private String methodDescriptor;

		public PrintStringMethodAdapter(MethodVisitor visitor, String name,
				String descriptor) {
			super(Opcodes.ASM4, visitor);
			methodName = name;
			methodDescriptor = descriptor;
		}

		@Override
		public void visitCode() {
			if (methodName.equals("doSomething")) {
				// trigger the super class
				super.visitCode();
				// load the system.out field into the stack
				super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System",
						"out", "Ljava/io/PrintStream;");
				// load the constant string we want to print into the stack
				// this string is created by the values we get from ASM
				super.visitLdcInsn("ASM: Method called " + methodName + "  "
						+ methodDescriptor);
				// trigger the method instruction for 'println'
				super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
						"java/io/PrintStream", "println",
						"(Ljava/lang/String;)V");
			}
		}
	}

	private class ClassPrinter extends ClassVisitor {

		public ClassPrinter() {
			super(Opcodes.ASM4);
		}

		public void visit(int version, int access, String name,
				String signature, String superName, String[] interfaces) {
			System.out.println(name + " extends " + superName + " {");
		}

		public void visitSource(String source, String debug) {
		}

		public void visitOuterClass(String owner, String name, String desc) {
		}

		public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
			return null;
		}

		public void visitAttribute(Attribute attr) {
		}

		public void visitInnerClass(String name, String outerName,
				String innerName, int access) {
		}

		public FieldVisitor visitField(int access, String name, String desc,
				String signature, Object value) {
			System.out.println(" " + name + " " + desc);
			return null;
		}

		public MethodVisitor visitMethod(int access, String name, String desc,
				String signature, String[] exceptions) {
			System.out.println(" " + name + desc);
			return null;
		}

		public void visitEnd() {
			System.out.println("}");
		}
	}
}
