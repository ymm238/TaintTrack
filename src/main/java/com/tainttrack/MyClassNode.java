package com.tainttrack;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyClassNode extends ClassNode implements Opcodes {
    private String className;
    private LinkedList<String> fieldsName;

    public MyClassNode(ClassVisitor cv) {
        super(ASM9);
        this.cv = cv;
        this.fieldsName = new LinkedList<>();
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name.replaceAll(".*/", "");
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        fieldsName.add(name);
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (name.equals("<init>") && mv != null) {
            mv = new InitAdviceAdapter(ASM9, mv, access, name, descriptor, className, fieldsName);
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        this.fields.add(new FieldNode(ASM9, ACC_PUBLIC, className + "_tag", "Lcom/tools/Taint;", null, null));
        Iterator<String> iterator = fieldsName.iterator();
        while (iterator.hasNext()) {
            String fieldName = iterator.next();
            this.fields.add(new FieldNode(ASM9, ACC_PUBLIC, fieldName + "_tag", "Lcom/tools/Taint;", null, null));
        }

        super.visitEnd();
    }
}
