function commentSubmit(boardNumber) {
    var commentContent = $('.comment-textarea').val();

    $.ajax({
        type : 'post',
        url : '/board/comment',
        dataType: 'text',
        data : ({
            "boardNumber" : boardNumber.toString(),
            "commentContent" : commentContent,
        }),

        success : function (data) {
            var boardBox = $('.board-content-box');
            var dataJson = JSON.parse(data);
            console.log(dataJson);
            boardBox.empty();
            $.each(dataJson, function (index, commentList) {
                var modifyOption = "";
                if (commentList.showModify) {
                    modifyOption = '<a class="margin-right-1 deletebtn"'+
                           'onclick="commentDeleteSubmit(' + commentList.c_no + ',' + +commentList.b_no +')">삭제</a>'+
                        ' <a class="margin-right-1 deletebtn"'+
                           ' onclick="commentModifySubmit(' + commentList.c_no + ')">수정</a>'
                }
                boardBox.append(
                    '  <div  class="board_container board_comment" style="border-radius: 1rem">\n' +
                    '    <div class="board_notifi_content">\n' +
                    commentList.c_comment +
                    '    </div>\n' +
                    '    <div class="board_notifi_userInfo">\n' +
                    '      <p class="margin-right-1" >작성자 : ' + commentList.user_id + '</p>\n' +
                    '      <p class="margin-right-1">작성일 : ' + commentList.c_transCreate + '</p>\n' +
                    modifyOption +
                    '    </div>\n' +
                    '  </div>'
                );
            })

        }

    })
}

function commentModalClose() {
    $('.hide-modal').hide();
}