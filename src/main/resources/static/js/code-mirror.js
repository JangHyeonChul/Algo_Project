var editor = ace.edit("editor");
var codeInput = document.getElementById("codeInput");

// 에디터 설정
editor.setTheme("ace/theme/eclipse"); // 테마 설정
editor.session.setMode("ace/mode/java"); // 언어 모드 설정
editor.setValue("구현언어를 선택하고 소스템플릿을 선택하면 기본 문법이 들어갑니다");

// 이벤트 리스너 추가
editor.getSession().on('change', function() {
    // 에디터의 변경 내용을 처리하는 코드
});



function submitCode() {
    var code = editor.getValue();
    codeInput.value = code;

}

/* 소스 템플릿 */
$('.select-Java').click(function () {
    editor.setValue("import java.util.Scanner;\n" +
        "public class Main {\n" +
        "public static void main() {\n" +
        "}\n" +
        "}");
})

$('.select-C').click(function () {
    editor.setValue("#include <stdio.h>\n" +
        "int main() {\n" +
        "return 0;\n" +
        "}")
})


$('.select-Python').click(function () {
    editor.setValue("파이썬");
})
