package org.mybatis.generator.internal;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.config.MergeConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mybatis.generator.api.dom.OutputUtilities.newLine;

/**
 * client javafile merge.
 *
 * @author GengChao
 */
public class JavaFileMergerJaxp {
    public String getNewJavaFile(String newFileSource, String existingFileFullPath) throws FileNotFoundException {
        CompilationUnit newCompilationUnit = JavaParser.parse(newFileSource);
        CompilationUnit existingCompilationUnit = JavaParser.parse(new File(existingFileFullPath));
        return mergerFile(newCompilationUnit, existingCompilationUnit);
    }

    /**
     * 合并两个java文件
     *
     * @param newCompilationUnit
     * @param existingCompilationUnit
     * @return
     */
    public String mergerFile(CompilationUnit newCompilationUnit, CompilationUnit existingCompilationUnit) {

        System.out.println("合并java代码");
        StringBuilder sb = new StringBuilder(newCompilationUnit.getPackageDeclaration().get().toString());
        newCompilationUnit.removePackageDeclaration();

        //合并imports
        NodeList<ImportDeclaration> imports = newCompilationUnit.getImports();
        imports.addAll(existingCompilationUnit.getImports());
        Set importSet = new HashSet<ImportDeclaration>();
        importSet.addAll(imports);

        NodeList<ImportDeclaration> newImports = new NodeList<ImportDeclaration>();
        newImports.addAll(importSet);
        newCompilationUnit.setImports(newImports);
        for (ImportDeclaration i : newCompilationUnit.getImports()) {
            sb.append(i.toString());
        }
        newLine(sb);
        NodeList<TypeDeclaration<?>> types = newCompilationUnit.getTypes();
        NodeList<TypeDeclaration<?>> oldTypes = existingCompilationUnit.getTypes();

        for (int i = 0; i < types.size(); i++) {
            //截取Class
            String classNameInfo = types.get(i).toString().substring(0, types.get(i).toString().indexOf("{") + 1);
            sb.append(classNameInfo);
            newLine(sb);
            newLine(sb);
            //合并fields
            List<FieldDeclaration> fields = types.get(i).getFields();
            List<FieldDeclaration> oldFields = oldTypes.get(i).getFields();
            List<FieldDeclaration> newFields = new ArrayList<FieldDeclaration>();
            HashSet<FieldDeclaration> fieldDeclarations = new HashSet<FieldDeclaration>();
            fieldDeclarations.addAll(fields);
            fieldDeclarations.addAll(oldFields);
            newFields.addAll(fieldDeclarations);
            for (FieldDeclaration f : newFields) {
                if (f.getComment().isPresent()) {
                    OutputUtilities.javaIndent(sb, 1);
                    sb.append(f.getComment().get().getTokenRange().get().toString());
                    newLine(sb);
                }
                if (f.getTokenRange().isPresent()) {
                    OutputUtilities.javaIndent(sb, 1);
                    sb.append(f.getTokenRange().get().toString());
                }
                newLine(sb);
                newLine(sb);
            }

            //合并methods
            List<MethodDeclaration> methods = types.get(i).getMethods();
            List<MethodDeclaration> existingMethods = oldTypes.get(i).getMethods();

            for (MethodDeclaration f : methods) {
                if (f.getComment().isPresent()) {
                    OutputUtilities.javaIndent(sb, 1);
                    sb.append(f.getComment().get().getTokenRange().get().toString());
                    newLine(sb);
                }

                if (f.getNameAsString() != null && f.getNameAsString().length() > 0) {
                    NodeList<AnnotationExpr> annotationExprs = f.getAnnotations();
                    if (annotationExprs != null && annotationExprs.size() > 0) {
                        for (AnnotationExpr annot : annotationExprs) {
                            OutputUtilities.javaIndent(sb, 1);
                            sb.append(annot.toString());
                            newLine(sb);
                        }
                    }

                    OutputUtilities.javaIndent(sb, 1);
                    sb.append(f.getDeclarationAsString(true, true, true));

                    if(f.getBody().isPresent()){
                        // 有body说明是方法
                        String res = f.getBody().get().toString().replaceAll("\r\n", "\r\n    ");
                        sb.append(" ");
                        sb.append(res);
                    }else{
                        // 没有body说明是接口
                        sb.append(";");
                    }

                }
                newLine(sb);
                newLine(sb);
            }

            List<String> methodList = new ArrayList<String>();
            for (MethodDeclaration m : methods) {
                methodList.add(m.getName().toString());
            }
            methodList.add("toString");
            methodList.add("hashCode");
            methodList.add("equals");

            for (MethodDeclaration m : existingMethods) {
                if (methodList.contains(m.getName().toString())) {
                    continue;
                }

                boolean flag = true;
                for (String tag : MergeConstants.OLD_ELEMENT_TAGS) {
                    if (m.toString().contains(tag)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    if (m.getComment().isPresent()) {
                        OutputUtilities.javaIndent(sb, 1);
                        sb.append(m.getComment().get().getTokenRange().get().toString());
                        newLine(sb);
                    }
                    if (m.getNameAsString() != null && m.getNameAsString().length() > 0) {
                        NodeList<AnnotationExpr> annotationExprs = m.getAnnotations();
                        if (annotationExprs != null && annotationExprs.size() > 0) {
                            for (AnnotationExpr annot : annotationExprs) {
                                OutputUtilities.javaIndent(sb, 1);
                                sb.append(annot.toString());
                                newLine(sb);
                            }
                        }

                        OutputUtilities.javaIndent(sb, 1);
                        sb.append(m.getDeclarationAsString(true, true, true));

                        if(m.getBody().isPresent()){
                            // 有body说明是方法
                            String res = m.getBody().get().toString().replaceAll("\r\n", "\r\n    ");
                            sb.append(" ");
                            sb.append(res);
                        }else{
                            // 没有body说明是接口
                            sb.append(";");
                        }

                    }
                    newLine(sb);
                    newLine(sb);
                }
            }

            //判断是否有内部类
            types.get(i).getChildNodes();
            for (Node n : types.get(i).getChildNodes()) {
                if (n.toString().contains("static class")) {
                    String res = n.toString().replaceAll("\r\n", "\r\n    ");
                    sb.append("    " + res);
                }
            }

        }

        return sb.append(System.getProperty("line.separator") + "}").toString();
    }

}
