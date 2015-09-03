package me.tomassetti.turin.compiler.bytecode;

import me.tomassetti.turin.jvm.JvmConstructorDefinition;
import org.objectweb.asm.MethodVisitor;

import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class NewInvocation extends BytecodeSequence {

    private String type;
    private List<BytecodeSequence> argumentsPush;
    private String signature;

    public NewInvocation(JvmConstructorDefinition constructorDefinition, List<BytecodeSequence> argumentsPush) {
        this.type = constructorDefinition.getJvmType();
        this.argumentsPush = argumentsPush;
        this.signature = constructorDefinition.getSignature();
    }

    @Override
    public void operate(MethodVisitor mv) {
        mv.visitTypeInsn(NEW, type);
        // The first type is consumed by new, the second by the constructor
        mv.visitInsn(DUP);
        argumentsPush.forEach((ap)->ap.operate(mv));
        // false because the method is not declared on an interface
        mv.visitMethodInsn(INVOKESPECIAL, type, "<init>", signature, false);
    }

}
