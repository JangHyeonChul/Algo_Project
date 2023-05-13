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
import java.util.Scanner;

@Component
public class ClangCompile {

    @Autowired
    ProblemService problemService;


    public List<String> compileClangCode(String code, Integer pageNum) {
        List<AnswerDTO> problemAnswers = problemService.getProblemAnswers(pageNum);
        List<String> compileResultList = new ArrayList<>();


        for (AnswerDTO problemAnswer : problemAnswers) {


            File sourceFile = new File("world" + ".c");
            try (FileWriter writer = new FileWriter(sourceFile)) {
                writer.write(code);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] compileCommand = {"gcc", "-o", "world", sourceFile.getPath()};
            try {
                Process compileProcess = Runtime.getRuntime().exec(compileCommand);
                compileProcess.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }


            String[] executeCommand = {"./" + "world"};
            try {
                Process executeProcess = Runtime.getRuntime().exec(executeCommand);
                OutputStream outputStream = executeProcess.getOutputStream();
                Scanner scanner = new Scanner(problemAnswer.getAnswer_input());
                while (scanner.hasNext()) {
                    outputStream.write((scanner.nextLine() + "\n").getBytes());
                    outputStream.flush();
                }
                scanner.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(executeProcess.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(problemAnswer.getAnswer_output())) {
                        compileResultList.add(CompileConstains.COMPILE_SUCCESS);
                    }

                    if (!line.equals(problemAnswer.getAnswer_output())) {
                        compileResultList.add(CompileConstains.COMPILE_FAIL);
                    }
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sourceFile.delete();
            new File("world").delete();
        }

        return compileResultList;
    }
}
