package com.tainttrack;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Transformer {
    public static void main(String[] args) throws IOException {
        ClassReader classReader = new ClassReader("com.test.Base");
        ClassWriter classWriter = new ClassWriter(classReader,ClassWriter.COMPUTE_MAXS);
        ClassNode classNode = new MyClassNode(classWriter);

        classReader.accept(classNode,ClassReader.SKIP_DEBUG);
        classNode.accept(classWriter);

        File f = new File("target/classes/com/test/Base.class");
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        fileOutputStream.write(classWriter.toByteArray());
        fileOutputStream.close();
        System.out.println("success");
    }
}
