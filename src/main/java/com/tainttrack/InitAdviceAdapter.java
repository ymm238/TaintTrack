package com.tainttrack;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.Iterator;
import java.util.LinkedList;

public class InitAdviceAdapter extends AdviceAdapter {
    private LinkedList<String> classNameAndFieldsName;
    protected InitAdviceAdapter(int api, MethodVisitor methodVisitor, int access, String name, String descriptor, String className, LinkedList<String> fieldsName) {
        super(api, methodVisitor, access, name, descriptor);
        classNameAndFieldsName = new LinkedList<>();
        this.classNameAndFieldsName.add(className);
        this.classNameAndFieldsName.addAll(fieldsName);
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        Iterator<String> iterator = classNameAndFieldsName.iterator();
        while(iterator.hasNext()){
            String tagName = iterator.next() + "_tag";
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(NEW, "com/tools/Taint");
            mv.visitInsn(DUP);
            mv.visitFieldInsn(GETSTATIC, "com/tools/TaintLevel", "NEGLIGIBLE", "Lcom/tools/TaintLevel;");
            mv.visitLdcInsn("Base");
            mv.visitMethodInsn(INVOKESPECIAL, "com/tools/Taint", "<init>", "(Lcom/tools/TaintLevel;Ljava/lang/String;)V", false);
            mv.visitFieldInsn(PUTFIELD, "com/test/Base", tagName, "Lcom/tools/Taint;");
        }


    }
}
