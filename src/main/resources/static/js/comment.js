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
            boardBox.empty();
            $.each(dataJson, function (index, commentList) {
                boardBox.append(
                    '  <div  class="board_container board_comment" style="border-radius: 1rem">\n' +
                    '    <div class="board_notifi_content">\n' +
                    commentList.c_comment +
                    '    </div>\n' +
                    '    <div class="board_notifi_userInfo">\n' +
                    '      <p class="margin-right-1" >작성자 : ' + commentList.user_id + '</p>\n' +
                    '      <p class="margin-right-1">작성일 : ' + commentList.c_transCreate + '</p>\n' +
                    '    </div>\n' +
                    '  </div>'
                );
            })

        }

    })
}