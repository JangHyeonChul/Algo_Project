function MyinfoSubmit() {
    var MyInfoIDValue = $('#MyInfoID').val();
    var MyInfoMessageValue = $('#MyInfoMessage').val();

    $.ajax({
        type : 'post',
        url : '/mypage/modifyinfo',
        dataType: 'text',
        data : ({
            "username" : MyInfoIDValue,
            "message" : MyInfoMessageValue,
        }),

        success : function (data) {
            messagemove();
        }
    })
}

function MyinfoBtn() {
    $.ajax({
        type: 'get',
        url : '/mypage/modifyinfo',

        success : function (data) {
            var mypageBox = $('.mypage-info');
            var myInfoHTML =  '<h1 class="mypage-title">정보수정</h1>\n' +


                '<div class="img-upload">\n' +

                '<label>프로필이미지</label>\n' +
                '<img class="profile_img" src="/img/DEFALUTE_PROFILE_IMG.png" />\n' +
                '<p>업로드하기</p>\n' +

                '</div>\n' +

                '<div class="data-box">\n' +

                '<div class="mypage-input-group">\n' +
                '<label>아이디</label>\n' +
                '<input id="MyInfoID" type="text" value="" readOnly/>\n' +
                '</div>\n' +

                '<div class="mypage-input-group">\n' +
                '<label>상태메세지</label>\n' +
                '<input id="MyInfoMessage" type="text" value=""/>\n' +
                '</div>\n' +
                '</div>\n' +

            '<div class="problem-submit-btn">\n' +
                '<button type="button" onclick="MyinfoSubmit()">제출하기</button>\n' +
            '</div>\n'

            mypageBox.empty();
            mypageBox.html(myInfoHTML);
            $('#MyInfoID').attr("value", data.user_id);
            $('#MyInfoMessage').attr("value", data.user_message);
        }
    })
}

function PasswordSubmit() {
    var originalPwd = $('#PasswordOriginal').val();
    var newPassword = $('#PasswordNew').val();
    var newPasswordCheck = $('#PasswordNewCheck').val();

    var passwordModifyErrorMap = new Map();

    if (originalPwd.length < 3) {
        passwordModifyErrorMap.set("originalPwd", "비밀번호는 2글자 이상이여야 합니다");
        $('.password-original').text(passwordModifyErrorMap.get("originalPwd"));
    } else {
        passwordModifyErrorMap.delete("originalPwd");
        $('.password-original').text("");
    }


    if (newPassword.length < 3) {
        passwordModifyErrorMap.set("newPassword", "비밀번호는 2글자 이상이여야 합니다");
        $('.password-New').text(passwordModifyErrorMap.get("newPassword"));
    } else {
        passwordModifyErrorMap.delete("newPassword");
        $('.password-New').text("");
    }

    if (newPasswordCheck.length < 3) {
        passwordModifyErrorMap.set("newPasswordCheck", "비밀번호는 2글자 이상이여야 합니다");
        $('.password-NewCheck').text(passwordModifyErrorMap.get("newPasswordCheck"));
    } else {
        passwordModifyErrorMap.delete("newPasswordCheck");
        $('.password-NewCheck').text("");
    }

    if (passwordModifyErrorMap.size == 0) {
        $.ajax({
            type: 'post',
            url: '/mypage/password',
            dataType: 'text',
            data: ({
                "originalPwd": originalPwd,
                "newPassword": newPassword,
                "newPasswordCheck": newPasswordCheck
            }),

            success: function (data) {
                var dataJSON = JSON.parse(data);

                if (data.length != 2) {
                    $('.password-original').text(dataJSON.originalPwdError);
                    $('.password-New').text(dataJSON.newPasswordError);
                    $('.password-NewCheck').text(dataJSON.newPasswordCheckError);
                } else {
                    messagemove();
                }
            }
        })
    }
}

function PasswordBtn() {
    var mypageBox = $('.mypage-info');
    var passwordHTML = '<h1 class="mypage-title">비밀번호 변경</h1>\n'+

    '<div class="mypage-text">\n'+
        '<p>현재 비밀번호를 변경합니다</p>\n'+
        '<p>새로운 비밀번호는 2글자이상, 20글자이하로 생성가능합니다</p>\n'+
    '</div>'+

    '<div class="data-box">\n'+

        '<div class="mypage-input-group">\n'+
            '<label>현재 비밀번호</label>\n'+
            '<input id="PasswordOriginal" type="password" placeholder="현재 비밀번호"  />\n'+
            '<p class="password-original error"></p>\n'+
       ' </div>\n'+

        '<div class="mypage-input-group">\n'+
            '<label>새로운 비밀번호</label>\n'+
            '<input id="PasswordNew" type="password" placeholder="새로운 비밀번호"/>\n'+
            '<p class="password-New error"></p>\n'+
        '</div>'+

        '<div class="mypage-input-group">\n'+
            '<label>비밀번호 확인</label>\n'+
            '<input id="PasswordNewCheck" type="password" placeholder="새로운 비밀번호 확인" />\n'+
            '<p class="password-NewCheck error"></p>\n'+
        '\n'+


    '</div>\n'+

    '<div class="problem-submit-btn">\n'+
        '<button type="button" onclick="PasswordSubmit()">제출하기</button>\n'+
    '</div>'

    mypageBox.empty();
    mypageBox.html(passwordHTML);

}

function EmailSubmit() {
    $.ajax({
        type: 'post',
        url: '/mypage/email',

        success: function (data) {
            var mypageBox = $('.mypage-info');
            var authCheck;
            var authText;
            var authMessage;

            if (data.role != 'ROLE_UNAUTH') {
                authText = "현재 인증 상태이며 이메일 인증을 완료한 상태입니다";
                authCheck = "인증";
            } else {
                authCheck = "미인증";
                authText = "현재 미인증 상태이며, 글쓰기, 알고리즘도전, 댓글작성 등 권한이 제한됩니다";
                authMessage = '<div class="problem-submit-btn">\n'+
                    '<button type="button" onClick="EmailSubmit()">이메일 인증</button>\n'+
                    ' </div>'+

                    '<p class="email-message" style="color: #ff5e5e;">남은 이메일인증 횟수 : ' + data.trial + '/5</p>\n';

            }

            var emailHTML = '<h1 class="mypage-title">이메일 인증</h1>\n'+

                '<div class="mypage-text">\n'+
                '<p>이메일 인증을 통해 글쓰기, 알고리즘도전, 댓글작성 등 권한을 얻습니다</p>\n'+
                '<p>하루에 총 5번 보낼수있으며, 그이상 넘어갈경우 제한됩니다</p>\n'+
                '<p>이메일은 자신이 가입했을때 작성한 이메일로 인증링크가 보내지며 해당인증링크 클릭시 정식가입됩니다</p>\n'+
                '<p>인증상태처리가 되있을경우 이메일 인증 버튼은 보이지않습니다</p>\n'+
                '</div>'+

                '<div class="data-box">\n'+

                '<p class="email-message">인증상태 : ' + authCheck + '</p>\n'+

                '<p class="email-message">이메일 : ' + data.email + '</p>\n'+

                '<p class="email-message" style="color: #ff5e5e;">' + authText + '</p>\n'+

                '</div>\n';

            messagemove();
            mypageBox.empty();
            mypageBox.html(emailHTML);
            mypageBox.append(authMessage);
        }
    })

}

function EmailBtn() {
    $.ajax({
        type: 'get',
        url: '/mypage/email',

        success: function (data) {
            var mypageBox = $('.mypage-info');
            var authCheck;
            var authText;
            var authMessage;

            if (data.role != 'ROLE_UNAUTH') {
                authText = "현재 인증 상태이며 이메일 인증을 완료한 상태입니다";
                authCheck = "인증";
            } else {
                authCheck = "미인증";
                authText = "현재 미인증 상태이며, 글쓰기, 알고리즘도전, 댓글작성 등 권한이 제한됩니다";
                authMessage = '<div class="problem-submit-btn">\n'+
                    '<button type="button" onClick="EmailSubmit()">이메일 인증</button>\n'+
               ' </div>'+

                '<p class="email-message" style="color: #ff5e5e;">남은 이메일인증 횟수 : ' + data.trial + '/5</p>\n';

            }

            var emailHTML = '<h1 class="mypage-title">이메일 인증</h1>\n'+

            '<div class="mypage-text">\n'+
                '<p>이메일 인증을 통해 글쓰기, 알고리즘도전, 댓글작성 등 권한을 얻습니다</p>\n'+
                '<p>하루에 총 5번 보낼수있으며, 그이상 넘어갈경우 제한됩니다</p>\n'+
                '<p>이메일은 자신이 가입했을때 작성한 이메일로 인증링크가 보내지며 해당인증링크 클릭시 정식가입됩니다</p>\n'+
                '<p>인증상태처리가 되있을경우 이메일 인증 버튼은 보이지않습니다</p>\n'+
            '</div>'+

            '<div class="data-box">\n'+

                '<p class="email-message">인증상태 : ' + authCheck + '</p>\n'+

                '<p class="email-message">이메일 : ' + data.email + '</p>\n'+

                '<p class="email-message" style="color: #ff5e5e;">' + authText + '</p>\n'+

            '</div>\n';


            mypageBox.empty();
            mypageBox.html(emailHTML);
            mypageBox.append(authMessage);
        }
    })
}

function BoardBtn() {
    $.ajax({
        type: 'get',
        url: '/mypage/board',

        success: function (data) {
            var mypageBox = $('.mypage-info');
            mypageBox.empty();
            mypageBox.append(
                '<h1 class="mypage-title">내가 쓴 게시물</h1>\n' +
                '<div class = "mypage-board" >\n' +
                '<div class = "mypage-board-box" >\n'
            )
            $.each(data, function (index, MyPageBoardList) {
                var boardHref = 'href="http://localhost:8080/board/page/' + String(MyPageBoardList.b_no) + '"';
                var boardCategory = transCategory(MyPageBoardList.b_category);

                mypageBox.append(
                    '<div class="mypage-writeboard">\n'+
                        '<div style="width: 70%"><a class="mypage-board-move"' + boardHref + '">' + boardCategory +
                        ' ' + MyPageBoardList.b_title + '</a></div>\n'+
                        '<div style="width: 10%">조회수 : ' + MyPageBoardList.b_viewcnt + '</div>\n'+
                        '<div style="width: 10%">추천수 : ' + MyPageBoardList.b_recommdation + '</div>\n'+
                        '<div style="width: 10%">댓글수 : ' + MyPageBoardList.b_comment_cnt + '</div>\n'+
                    '</div>'
                );
            })
            mypageBox.append(
                '</div>\n'+
                '</div>'
            )
        }
    })
}


function AlertBtn() {
    $.ajax({
        type: 'get',
        url: '/mypage/alram',
        success: function (data) {
            var mypageBox = $('.mypage-info');
            mypageBox.empty();
            mypageBox.append(
                '<h1 class="mypage-title">알림</h1>'+

            '<div class="alter-nav">'+
                '<a class="AlramBtn" style="color:#0076C0; " onclick="AlertNotReadBtn()">안읽은 알림</a>'+
                '<a class="AlramBtn" onclick="AlertReadBtn()">읽은 알림</a>'+
            '</div>'
            )

            writeAlramMessage('0', data, mypageBox);
        }
    })
}

function AlertNotReadBtn() {
    $.ajax({
        type: 'get',
        url: '/mypage/alram',
        success: function (data) {
            var mypageBox = $('.mypage-info');
            mypageBox.empty();

            mypageBox.append(
                '<h1 class="mypage-title">알림</h1>'+

                '<div class="alter-nav">'+
                '<a class="AlramBtn" style="color:#0076C0; " onclick="AlertNotReadBtn()">안읽은 알림</a>'+
                '<a class="AlramBtn" onclick="AlertReadBtn()" >읽은 알림</a>'+
                '</div>'
            )

            writeAlramMessage('0', data, mypageBox);
        }
    })
}

function AlertReadBtn() {
    $.ajax({
        type: 'get',
        url: '/mypage/alram',
        success: function (data) {
            var mypageBox = $('.mypage-info');
            mypageBox.empty();

            mypageBox.append(
                '<h1 class="mypage-title">알림</h1>'+

                '<div class="alter-nav">'+
                '<a class="AlramBtn" onclick="AlertNotReadBtn()">안읽은 알림</a>'+
                '<a class="AlramBtn" style="color:#0076C0; " onclick="AlertReadBtn()">읽은 알림</a>'+
                '</div>'
            )

            writeAlramMessage('1', data, mypageBox);

        }
    })
}

function writeAlramMessage(check, data, mypageBox) {
    $.each(data, function (index, alramBoard) {
        if (alramBoard.a_check == check) {
            var category = transCategory(alramBoard.b_category);

            mypageBox.append(
                '<div class="alter-message">'+
                '<a href="http://localhost:8080/board/page/' + String(alramBoard.b_no) + '">' + category + ' ' + alramBoard.b_title + ' 게시글에 새로운 댓글이 달렸습니다</a>'+
                '</div>'
            )
        }
    })

}


function transCategory(category) {
    switch (category) {
        case 'FR':
            return '[자유]';
        case 'QU':
            return '[질문]';
        case 'TE':
            return '[강의]';
        case 'ROLE_UNAUTH':
            return '미인증유저';
        case 'ROLE_USER':
            return '일반유저';
        case 'ROLE_ADMIN':
            return '관리자';
    }
}


function UserInfoBtn() {
    $.ajax({
        url : '/mypage/userinfo',
        type : 'get',

        success : function (data) {
            var mypageBox = $('.mypage-info');
            var userRole = transCategory(data.user_role);
            mypageBox.empty();
            mypageBox.append(
               ' <h1 class="mypage-title">정보</h1>'+
                '<div class="user-data-gruop">'+
                    '<h1>이메일</h1>'+
                    '<p>' + data.email + '</p>'+
                '</div>'+

           ' <div class="user-data-gruop">'+
                '<h1>가입일자</h1>'+
                '<p>' + data.create_at + '</p>'+
            '</div>'+

            '<div class="user-data-gruop">'+
                '<h1>권한상태</h1>'+
                '<p>' + userRole + '</p>'+
            '</div>'+

            '<div class="user-data-gruop">'+
                '<h1>포인트</h1>'+
                '<p>' + data.point + '점</p>'+
            '</div>'+

            '<div class="user-data-gruop">'+
                '<h1>레벨</h1>'+
                '<p>' + data.grade + '</p>'+
            '</div>'+

            '<div class="user-data-gruop">'+
                '<h1>제출한 문제</h1>'+
                '<p>' + data.submitProblem + '</p>'+
            '</div>'+

            '<div class="user-data-gruop">'+
                '<h1>성공한 문제</h1>'+
                '<p>' + data.successProblem + '</p>'+
            '</div>'+

            '<div class="user-data-gruop">'+
                '<h1>실패한 문제</h1>'+
                '<p>' + data.failProblem + '</p>'+
            '</div>'
            );
        }
    })

}

