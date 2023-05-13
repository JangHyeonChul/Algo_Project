function problemSearchBtn() {
    var keyword = $('#keyword').val();
    var url =  "http://localhost:8080/search?keyword=" + encodeURIComponent(keyword) + '&type=problem';

    location.href = url;
}

function boardSearchBtn() {
    var keyword = $('#boardKeyword').val();
    var url =  "http://localhost:8080/search?keyword=" + encodeURIComponent(keyword) + '&type=board';

    location.href = url;
}



function notiSearchBtn() {
    var keyword = $('#notikeyword').val();
    var url =  "http://localhost:8080/search?keyword=" + encodeURIComponent(keyword) + '&type=noti';

    location.href = url;
}



$(".search-input").ready(function() {
    var keywordQuery = window.location.search;
    var urlParams = new URLSearchParams(keywordQuery);
    var keyword = urlParams.get('keyword');
    var type = urlParams.get('type');
    $('.search-input').val(keyword);

    $.ajax({
        type: 'get',
        url: '/searching',
        dataType: 'text',
        data: ({
            "keyword": $('.search-input').val(),
            "type": type,

        }),

        success: function (data) {
            var searchResultBox = $('.search-result-container');
            var dataJSON = JSON.parse(data);
            console.log(dataJSON);
            console.log(dataJSON.length);
            searchResultBox.empty();
            searchResultBox.append(
                '<div style="color: #999;">검색결과 ' + dataJSON.length + '개</div>'
            )


            console.log(type);

            if (type == 'problem') {

                $.each(dataJSON, function (index, searchResult) {
                    var url = "http://localhost:8080/problem/" + searchResult.p_no;
                    searchResultBox.append(
                        '<div class="search-result-content">' +
                        '<a href="' + url + '">' + searchResult.p_no + '번 : ' + searchResult.p_title + '</a>' +
                        '<span class="text-truncate">' + searchResult.p_content + '</span>' +

                        '</div>'
                    )
                })

            }

            if (type == 'board') {

                $.each(dataJSON, function (index, searchResult) {
                    var url = "http://localhost:8080/board/page/" + searchResult.p_no;
                    var categoryTag = transCategory(searchResult.b_category);
                    searchResultBox.append(
                        '<div class="search-result-content">' +
                        '<a href="' + url + '">' + categoryTag + ' ' + searchResult.b_title + '</a>' +
                        '<span class="text-truncate">' + searchResult.b_content + '</span>' +

                        ' <span>추천수 : ' + searchResult.b_recommdation + '</span>' +
                        '<span> 조회수 : ' + searchResult.b_viewcnt + '</span>' +
                        '<span> 댓글 : ' + searchResult.b_comment_cnt + '</span>' +
                        '</div>'
                    )
                })
            }

            if (type == 'noti') {

                $.each(dataJSON, function (index, searchResult) {
                    var url = "http://localhost:8080/notification/" + searchResult.noti_no;
                    searchResultBox.append(
                        '<div class="search-result-content">' +
                        '<a href="' + url + '">' + searchResult.noti_no + '번 : ' + searchResult.noti_title + '</a>' +
                        '<span class="text-truncate">' + searchResult.noti_content + '</span>' +

                        '</div>'
                    )
                })

            }
        }
    })


    $('.search-input[type="text"]').on('input', function () {
        urlParams.set('keyword', $('.search-input').val())
        window.history.replaceState({}, '', '?' + urlParams.toString());

        $.ajax({
            type: 'get',
            url: '/searching',
            dataType: 'text',
            data: ({
                "keyword": $('.search-input').val(),
                "type": type,

            }),

            success: function (data) {
                var searchResultBox = $('.search-result-container');
                var dataJSON = JSON.parse(data);
                searchResultBox.empty();
                searchResultBox.append(
                    '<div style="color: #999;">검색결과 ' + dataJSON.length + '개</div>'
                )
                if (type == 'problem') {
                    $.each(dataJSON, function (index, searchResult) {
                        var url = "http://localhost:8080/problem/" + searchResult.p_no;
                        searchResultBox.append(
                            '<div class="search-result-content">' +
                            '<a href="' + url + '">' + searchResult.p_no + '번 : ' + searchResult.p_title + '</a>' +
                            '<span class="text-truncate">' + searchResult.p_content + '</span>' +

                            '</div>'
                        )
                    })
                }

                if (type == 'board') {
                    $.each(dataJSON, function (index, searchResult) {
                        var url = "http://localhost:8080/board/page/" + searchResult.b_no;
                        var categoryTag = transCategory(searchResult.b_category);
                        searchResultBox.append(
                            '<div class="search-result-content">' +
                            '<a href="' + url + '">' + categoryTag + ' ' + searchResult.b_title + '</a>' +
                            '<span class="text-truncate">' + searchResult.b_content + '</span>' +

                            ' <span>추천수 : ' + searchResult.b_recommdation + '</span>' +
                            '<span> 조회수 : ' + searchResult.b_viewcnt + '</span>' +
                            '<span> 댓글 : ' + searchResult.b_comment_cnt + '</span>' +
                            '</div>'
                        )
                    })
                }
                if (type == 'noti') {
                    $.each(dataJSON, function (index, searchResult) {
                        var url = "http://localhost:8080/notification/" + searchResult.noti_no;
                        searchResultBox.append(
                            '<div class="search-result-content">' +
                            '<a href="' + url + '">' + searchResult.noti_title + '</a>' +
                            '<span class="text-truncate">' + searchResult.noti_content + '</span>' +

                            '</div>'
                        )
                    })
                }

            }

        })
    })
})

