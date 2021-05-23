var page = 1;
var keyword = '';
var getResult = function () {
    let postData = {
        page: page,
        keyword: keyword
    }
    $.ajax({
        url: "/api/article/getSearchResult",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(postData),
        type:"POST",
        success: function (data, status) {
            $('#result').empty();
            $('#result').append(
                '<div id="page" style="top:860px;width:800px;margin:auto;' +
                'position:absolute;text-align:center;line-height:30px">' +
                '<span style="cursor:pointer;color:purple" onclick={page=1;getResult();}>&nbsp;首页&nbsp;</span>' +
                '<span style="cursor:pointer;color:purple" onclick={page=' + data["maxPageNum"] + ';getResult();}>&nbsp;尾页&nbsp;</span><br/>' +
                '</div>'
            );
            if (page != 1) $('#page').append('<span style="cursor:pointer;color:purple" onclick={page--;getResult();}>上一页</span>');
            for (var i = 1; i <= data['maxPageNum']; i++) {
                if (i + 5 < page || i - 5 > page) continue;
                if (i != page) $('#page').append(
                    '<span style="cursor:pointer;color:purple" onclick={page=' + i + ';getResult();}>&nbsp;' + i + '&nbsp;</span>'
                )
                else $('#page').append('<span style="cursor:default;color:red;font-size:16px">' + i + '</span>');
            }
            if (page != data['maxPageNum']) $('#page').append('<span style="cursor:pointer;color:purple" onclick={page++;getResult();}>下一页</span>');
            $.each(data, function (key, value) {
                if (typeof (value) == "object")
                    $('#result').append(
                        '<a href="../article/detail.html?id=' + value["art_id"] + '">' +
                        '<div onmouseover={$(event.target).css("color","DeepSkyBlue");} ' +
                        'onmouseleave={$(event.target).css("color","black");} ' +
                        'style="line-height:25px;font-size:20px;margin:30px 20px;' +
                        'border-left:thick double lightskyblue;padding:0 0 0 20px;cursor:pointer">' +
                        value['title'] +
                        '<span style="float:right;;color:black;font-size:8px;' +
                        'line-height:25px" onmouseleave={$(event.target).css("color","black");} ' +
                        ' onmouseover={event.stopPropagation();$(event.target).css("color","purple");}>' +
                        value['author'] + "&nbsp;&nbsp;&nbsp;&nbsp;" + value['date'] + '</span>' +
                        '</div></a>'
                    );
            })
        }
    })
};
Vue.component('searcharticle', {
    props: {
        search: {
            type: String,
            default: ''
        }
    },
    data: function () {
        return {
            resultStyle: {
                backgroundColor: 'rgba(255,255,255,0.5)',
                padding: '40px 60px',
                height: '780px',
                width: '800px',
                margin: '9px 1px 50px 1px',
                borderRadius: '15px'
            },
            inputStyle: {
                width: '240px',
                height: '18px',
                padding: '3px 6px',
                border: '1px solid transparent',
                borderRadius: '6px',
            },
            searchStyle: {
                position: 'relative',
                top: '-890px',
                left: '320px',
                width: '850px'
            },
            buttonStyle: {
                verticalAlign: 'middle',
                marginLeft: '10px',
                fontSize: '20px',
                color: 'skyblue',
                cursor: 'pointer',
            }
        }
    },
    methods: {
        searchClick: function () {
            window.location.href = "../search.html?keyword=" + this.search;
        },
        listenkey: function (event) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if (e && e.keyCode == 13) {
                this.searchClick();
            }
        },
    },
    mounted() {
        $('input').each(function () {
            $(this).on('focus', function () {
                $(this).css('border', '1px solid blue');
            });
            $(this).on('blur', function () {
                $(this).css('border', '1px solid transparent');
            })
        });
        keyword = this.search;
        getResult();
    },
    template:
        '<div>' +
        '<div id="result" :style="resultStyle"></div>' +
        '<div :style="searchStyle">' +
        '<input name="search" v-model="search" type="text" placeholder="标题/用户/关键字" :style="inputStyle" @keydown="listenkey">' +
        '<i class="layui-icon layui-icon-search" :style="buttonStyle" @click="searchClick"></i>' +
        '</div>' +
        '</div>'
})