var pageAll = 1;
var pageMy = 1;
var getAllArt = function () {
    let data = {
        type: 'all',
        page: pageAll
    };
    $.ajax({
            type: "post",
            contentType: "application/json;charset=UTF-8",
            url: "/api/home/getArticleList",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data, status) {
                $('#showAllArt').empty();
                $('#showAllArt').append(
                    '<div id="allpage" style="top:930px;width:700px;height:60px;margin:auto;' +
                    'position:absolute;text-align:center;line-height:30px">' +
                    '<span style="cursor:pointer;color:purple" onclick={pageAll=1;getAllArt();}>&nbsp;首页&nbsp;</span>' +
                    '<span style="cursor:pointer;color:purple" onclick={pageAll=' + data["maxPageNum"] + ';getAllArt();}>&nbsp;尾页&nbsp;</span><br/' +
                    '</div>'
                );
                if (pageAll != 1) $('#allpage').append('<span style="cursor:pointer;color:purple" onclick={pageAll--;getAllArt();}>上一页</span>');
                for (var i = 1; i <= data['maxPageNum']; i++) {
                    if (i + 5 < pageAll || i - 5 > pageAll) continue;
                    if (i != pageAll) $('#allpage').append(
                        '<span style="cursor:pointer;color:purple" onclick={pageAll=' + i + ';getAllArt();}>&nbsp;' + i + '&nbsp;</span>'
                    )
                    else $('#allpage').append('<span style="cursor:default;color:red;font-size:16px">' + i + '</span>');
                }
                if (pageAll != data['maxPageNum']) $('#allpage').append('<span style="cursor:pointer;color:purple" onclick={pageAll++;getAllArt();}>下一页</span>');
                $.each(data, function (key, value) {
                    if (typeof (value) == "object")
                        $('#showAllArt').append(
                            '<a href="../article/detail.html?id=' + value["art_id"] + '">' +
                            '<div onmouseover={$(event.target).css("color","DeepSkyBlue");} ' +
                            'onmouseleave={$(event.target).css("color","black");} ' +
                            'style="line-height:20px;font-size:20px;margin:30px 20px;' +
                            'border-left:thick double lightskyblue;padding:0 0 0 20px;cursor:pointer">' +
                            value['title'] +
                            '<br/><span style="color:gray;font-size:8px;' +
                            'line-height:8px" onmouseleave={$(event.target).css("color","gray");} ' +
                            ' onmouseover={event.stopPropagation();$(event.target).css("color","purple");}>' +
                            value['author'] + "&nbsp;&nbsp;&nbsp;&nbsp;" + value['date'] + '</span>' +
                            '</div></a>'
                        );
                });
            }
        }
    );
}
let getMyArt = function () {
    let data = {
        type: 'my',
        page: pageMy
    };
    $.ajax({
        type: "post",
        contentType: "application/json;charset=UTF-8",
        url: "/api/home/getArticleList",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (data, status) {
            $('#showMyArt').empty();
            $('#showMyArt').append(
                '<div id="mypage" style="top:930px;width:700px;height:60px;margin:auto;' +
                'position:absolute;text-align:center;line-height:30px">' +
                '<span style="cursor:pointer;color:purple" onclick={pageMy=1;getMyArt();}>&nbsp;首页&nbsp;</span>' +
                '<span style="cursor:pointer;color:purple" onclick={pageMy=' + data["maxPageNum"] + ';getMyArt();}>&nbsp;尾页&nbsp;</span><br/' +
                '</div>'
            );
            if (pageMy != 1) $('#mypage').append('<span style="cursor:pointer;color:purple" onclick={pageMy--;getMyArt();}>上一页</span>');
            for (var i = 1; i <= data['maxPageNum']; i++) {
                if (i + 5 < pageMy || i - 5 > pageMy) continue;
                if (i != pageMy) $('#mypage').append(
                    '<span style="cursor:pointer;color:purple" onclick={pageMy=' + i + ';getMyArt();}>&nbsp;' + i + '&nbsp;</span>'
                )
                else $('#mypage').append('<span style="cursor:default;color:red;font-size:16px">' + i + '</span>');
            }
            if (pageMy != data['maxPageNum']) $('#mypage').append('<span style="cursor:pointer;color:purple" onclick={pageMy++;getMyArt();}>下一页</span>');
            $.each(data, function (key, value) {
                if (typeof (value) == "object")
                    $('#showMyArt').append(
                        '<a href="../article/detail.html?id=' + value["art_id"] + '">' +
                        '<div onmouseover={$(event.target).css("color","DeepSkyBlue");} ' +
                        'onmouseleave={$(event.target).css("color","black");} ' +
                        'style="line-height:20px;font-size:20px;margin:30px 20px;' +
                        'border-left:thick double lightskyblue;padding:0 0 0 20px;cursor:pointer">' +
                        value['title'] +
                        '<br/><span style="color:gray;font-size:8px;' +
                        'line-height:8px" onmouseleave={$(event.target).css("color","gray");} ' +
                        'onmouseover={event.stopPropagation();$(event.target).css("color","purple");}>' + value['date'] + '</span>' +
                        '</div></a>'
                    );
            })
        }
    });
};
Vue.component('articleboard', {
    props: {
        blogname: {
            type: String,
            default: 'Nebula'
        }
    },
    data: function () {
        return {
            cardStyle: {
                backgroundColor: 'rgba(255,255,255,0.2)',
                padding: '10px 20px',
                borderRadius: '15px 15px 0 0',
                margin: '1px',
                cursor: 'pointer'
            },
            listStyle: {
                backgroundColor: 'rgba(255,255,255,0.5)',
                padding: '20px',
                height: '780px',
                width: '700px',
                margin: '9px 1px 0 1px',
                display: 'none',
                borderRadius: '0 10px 10px 10px'
            },
            blognameStyle: {
                color: 'darkred',
                backgroundColor: 'rgba(255,255,255,0.5)',
                textAlign: 'center',
                margin: '0 auto 40px auto',
                padding: '10px',
                borderRadius: '20px',
                width: '600px',
                boxShadow: 'lightblue 0 0 20px 10px',
                cursor: 'default'
            },
            addStyle: {
                position: 'relative',
                top: '-805px',
                left: '700px',
                color: 'deepskyblue',
                fontSize: '20px',
                cursor: 'pointer'
            }
        }
    },
    methods: {
        allartclick: function () {
            $('#allcard').css("background-color", "rgba(255,255,255,0.5)");
            $('#mycard').css("background-color", "rgba(255,255,255,0.2)");
            $('#showAllArt').show();
            $('#showMyArt').hide();
        },
        myartclick: function () {
            $('#allcard').css("background-color", "rgba(255,255,255,0.2)");
            $('#mycard').css("background-color", "rgba(255,255,255,0.5)");
            $('#showAllArt').hide();
            $('#showMyArt').show();
        },
        addclick: function () {
            window.location.href = "../article/post.html";
        }
    },
    mounted() {
        $('#allcard').css("background-color", "rgba(255,255,255,0.5)");
        $('#showAllArt').show();
        getAllArt();
        getMyArt();
    },
    template:
        '<div style="float:right">' +
        '<h2 :style="blognameStyle">{{blogname}}</h2>' +
        '<span id="allcard" :style="cardStyle" @click="allartclick">所有文章</span>' +
        '<span id="mycard" :style="cardStyle" @click="myartclick">我的文章</span>' +
        '<div id="showAllArt" :style="listStyle"></div>' +
        '<div id="showMyArt" :style="listStyle"></div>' +
        '<i class="layui-icon layui-icon-add-circle" :style="addStyle" @click="addclick"></i>' +
        '</div>'
})

