function boardDeleteSubmit(boardNum) {
    $.ajax({
        type: 'DELETE',
        url: '/board/page/' + boardNum,

        success: function (data) {
            if (data) {
                location.href= "http://localhost:8080/board";
            }
        }
    })
}

function commentDeleteSubmit(c_no, b_no) {
    $.ajax({
        type: 'DELETE',
        url: '/board/comment',
        dataType: 'text',
        data : ({
            "commentNumber" : c_no,
            "boardNumber" : b_no,
        }),

        success: function (data) {
            if (data != null) {
                messagemove();
            }

        }
    })
}

function commentModifySubmit(c_no) {
    $('.hide-modal').show();

    $.ajax({
        type: 'GET',
        url: '/board/comment/update',
        dataType: 'text',
        data : ({
            "commentNumber" : c_no,
        }),

        success: function (data) {

            if (data.length == 0) {
                location.href="http://localhost:8080/board";
            }

            var dataJSON = JSON.parse(data);
            var content = dataJSON.c_comment
            $('.hide-modal').empty();
            $('.hide-modal').append(
                '<div class="modify-comment">'+
                    '<div class="modify-content">'+
                        '<textarea id="commentvalue">' + content + '</textarea>'+
                ' </div>'+

                '<div class="modify-modal-exit">'+
                   ' <a class="modify-modal-btn" onClick="commentModify(' + c_no + ')">수정하기</a>'+
                    '<a class="modify-modal-btn" onClick="commentModalClose()">닫기</a>'+
                '</div>'+
                '</div>'
            )
        }
    })
}

function commentModify(c_no) {
    var commentValue = $('#commentvalue').val();

    $.ajax({
        type: 'PATCH',
        url: '/board/comment/update',
        dataType: 'text',
        data: ({
            "commentNumber": c_no,
            "commentContent": commentValue,
        }),

        success: function (data) {

            if (data) {
                location.reload();
                $('.hide-modal').hide();
            }

        },
    })
}
