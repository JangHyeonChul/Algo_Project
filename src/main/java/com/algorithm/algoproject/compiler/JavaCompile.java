package com.algorithm.algoproject.compiler;


import com.algorithm.algoproject.config.CompileConstains;
import com.algorithm.algoproject.dto.AnswerDTO;
import com.algorithm.algoproject.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

@Component
public class JavaCompile {

    @Autowired
    ProblemService problemService;


    public List<String> compileJavaCode(String code, Integer pageNum) {
        List<AnswerDTO> problemAnswers = problemService.getProblemAnswers(pageNum);
        List<String> compileResultList = new ArrayList<>();


        for (AnswerDTO problemAnswer : problemAnswers) {

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            File sourceFile = new File("Main.java");
            PrintStream save = System.out;

            try (FileWriter writer = new FileWriter(sourceFile)) {
                writer.write(code);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int run = compiler.run(null, null, null, sourceFile.getPath());

            System.setIn(new ByteArrayInputStream(problemAnswer.getAnswer_input().getBytes()));


            try (URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File("").toURI().toURL()})) {
                if (run != 0) {
                    throw new Exception("Compile Error!");
                }

                Class<?> userClass = classLoader.loadClass("Main");


                Object instance = userClass.getDeclaredConstructor().newInstance();


                Method runMethod = userClass.getMethod("main");


                ByteArrayOutputStream out = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(out);
                System.setOut(printStream);


                runMethod.invoke(instance);


                System.setOut(save);
                printStream.close();


                String result = out.toString().trim();

                if (result.equals(problemAnswer.getAnswer_output())) {
                    compileResultList.add(CompileConstains.COMPILE_SUCCESS);
                }

                if (!result.equals(problemAnswer.getAnswer_output())) {
                    compileResultList.add(CompileConstains.COMPILE_FAIL);
                }


            } catch (Exception e) {
                compileResultList.add(CompileConstains.COMPILE_ERROR);
                break;
            } finally {
                sourceFile.delete();
                new File("Main.java").delete();

            }
        }

        return compileResultList;
    }
}
