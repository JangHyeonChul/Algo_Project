function recommdationSubmit(boardNumber) {
    var boardBox = $('.board_recommdation');
    $.ajax({
        type : 'post',
        url : '/board/recommdation',
        dataType: 'text',
        data : ({
            "boardNumber" : boardNumber.toString(),
        }),

        success : function (data) {
            var dataJson = JSON.parse(data);
            console.log(dataJson);
            boardBox.empty();
            boardBox.append(
                    '<p>'+
                        '추천수 : ' + dataJson.recommdationCnt + '' +
                    '</p>'
                );

        }

    })
}