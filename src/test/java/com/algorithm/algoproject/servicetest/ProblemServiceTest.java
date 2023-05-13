package com.algorithm.algoproject.servicetest;


import com.algorithm.algoproject.PageHandler;
import com.algorithm.algoproject.dto.AnswerDTO;
import com.algorithm.algoproject.dto.ProblemDTO;
import com.algorithm.algoproject.dto.RankDTO;
import com.algorithm.algoproject.mapper.ProblemMapper;
import com.algorithm.algoproject.service.ProblemService;
import com.algorithm.algoproject.service.RankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


@SpringBootTest

public class ProblemServiceTest {

    @Autowired
    ProblemService problemService;

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    RankService rankService;

//    @BeforeEach
//    @DisplayName("임시데이터삽입")
//    void insertTempData() {
//        ProblemDTO problemDTO = ProblemDTO.builder()
//                .p_title("더미데이터제목")
//                .p_level("easy")
//                .p_type("문자열")
//                .p_lang("KR")
//                .p_content("데이터내용")
//                .build();
//
//        problemService.writeProblem(problemDTO);
//    }


    @Test
    @Transactional
    void getProblemTest() {
        List<ProblemDTO> allProblems = problemService.getAllProblems(15);

        for (ProblemDTO problemDTO : allProblems) {
            System.out.println("problemDTO = " + problemDTO);
        }


    }

    @Test
    @DisplayName("JAVA동적컴파일")
    void javaCompile() throws Exception {
        String code = "import java.util.Scanner;" +
                "public class Main {" +
                "public static void main() {" +
                "Scanner in = new Scanner(System.in);" +
                "int A = in.nextInt();" +
                "int B = in.nextInt();" +
                "System.out.println(A+B);" +
                "in.close();" +
                "}" +
                "}";

        List<AnswerDTO> problemAnswers = problemService.getProblemAnswers(1);
        System.out.println("problemAnswers = " + problemAnswers);

        List<String> compileResultList = new ArrayList<>();


        for(AnswerDTO problemAnswer : problemAnswers) {

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

                System.out.println("result = " + result);
                System.out.println("problemAnswer = " + problemAnswer.getAnswer_output());
                if(result.equals(problemAnswer.getAnswer_output())) {
                    compileResultList.add("success");
                }

                if(!result.equals(problemAnswer.getAnswer_output())) {
                    compileResultList.add("fail");
                }



            } catch (Exception e) {
                compileResultList.add("compile error");
            } finally {
                sourceFile.delete();
                new File("Main.java").delete();

            }
        }

        System.out.println("compileResultList = " + compileResultList);
    }



    @Test
    void C언어컴파일() {
        String code = "#include <stdio.h>\n" +
                "int main(){\n" +
                "    int a, b;\n" +
                "    scanf(\"%d %d\", &a, &b);\n" +
                "    printf(\"%d\", a-b);\n" +
                "    return 0;\n" +
                "}";

// 사용자가 제출한 C 언어 코드를 파일로 저장
        File sourceFile = new File("world" + ".c");
        try (FileWriter writer = new FileWriter(sourceFile)) {
            writer.write(code);
        } catch (IOException e) {
            e.printStackTrace();
        }

// C 언어 코드 컴파일
        String[] compileCommand = {"gcc", "-o", "world", sourceFile.getPath()};
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCommand);
            compileProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

// 컴파일된 실행 파일 실행
        String[] executeCommand = {"./" + "world"};
        try {
            Process executeProcess = Runtime.getRuntime().exec(executeCommand);
            OutputStream outputStream = executeProcess.getOutputStream();
            Scanner scanner = new Scanner("3 4");
            while (scanner.hasNext()) {
                outputStream.write((scanner.nextLine() + "\n").getBytes());
                outputStream.flush();
            }
            scanner.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(executeProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("안녕");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

// 사용이 끝난 파일 삭제
        sourceFile.delete();
        new File("world").delete();
    }

    @Test
    void 파이썬컴파일() {
        String code = "a = input()\n" +
                "b = input()\n" +
                "print(int(a)+int(b))\n";

// 사용자가 제출한 파이썬 코드를 파일로 저장
        File sourceFile = new File("hello.py");
        try (FileWriter writer = new FileWriter(sourceFile)) {
            writer.write(code);
        } catch (IOException e) {
            e.printStackTrace();
        }

// 파이썬 코드 실행
        String[] command = {"python", sourceFile.getPath()};
        try {
            Process process = Runtime.getRuntime().exec(command);
            OutputStream outputStream = process.getOutputStream();
            outputStream.write("3\n4\n".getBytes());
            outputStream.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getRank() {
        RankDTO wkdgus1136 = rankService.getRankInfoByUserName("wkdgus1136");

        System.out.println("wkdgus1136 = " + wkdgus1136);


    }

    @Test
    void writeProblemAnswer() {
        List<String> inputs = new ArrayList<>();
        List<String> outputs= new ArrayList<>();

        inputs.add("1 2");
        inputs.add("3 4");
        inputs.add("5 6");

        outputs.add("3");
        outputs.add("7");
        outputs.add("11");


        Iterator<String> input = inputs.iterator();
        Iterator<String> output = outputs.iterator();

        while(input.hasNext() && output.hasNext()) {
            String getInput = input.next();
            String getOutput = output.next();

            problemMapper.insertProblemAnswer(getInput, getOutput, 2);
        }

    }

    @Test
    void problemSearch() {
        Integer problemTotalCountByType = problemService.getCountAllProblemsBySearch("안녕");

        System.out.println("problemTotalCountByType = " + problemTotalCountByType);


        List<ProblemDTO> allProblems =  problemService.getAllProblemsBySearch("안녕", 0);
    }
}