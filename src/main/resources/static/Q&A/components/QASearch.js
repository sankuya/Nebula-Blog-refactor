var page = 1;
var keyword = '';
var getlist = function () {
    let postData = {
        page: page,
        keyword: keyword
    };
    $.ajax({
        url: "/FAQ/getQASearch",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(postData),
        success: function (data, status) {
            $('#list').empty();
            $('#list').append(
                '<div id="page" style="top:860px;width:800px;margin:auto;' +
                'position:absolute;text-align:center;line-height:30px">' +
                '<span style="cursor:pointer;color:purple" onclick={page=1;getlist();}>&nbsp;首页&nbsp;</span>' +
                '<span style="cursor:pointer;color:purple" onclick={page=' + data["maxPageNum"] + ';getlist();}>&nbsp;尾页&nbsp;</span><br/>' +
                '</div>'
            );
            if (page != 1) $('#page').append('<span style="cursor:pointer;color:purple" onclick={page--;getlist();}>上一页</span>');
            for (var i = 1; i <= data['maxPageNum']; i++) {
                if (i + 5 < page || i - 5 > page) continue;
                if (i != page) $('#page').append(
                    '<span style="cursor:pointer;color:purple" onclick={page=' + i + ';getlist();}>&nbsp;' + i + '&nbsp;</span>'
                )
                else $('#page').append('<span style="cursor:default;color:red;font-size:16px">' + i + '</span>');
            }
            if (page != data['maxPageNum']) $('#page').append('<span style="cursor:pointer;color:purple" onclick={page++;getlist();}>下一页</span>');
            $.each(data, function (key, value) {
                if (typeof (value) == "object")
                    $('#list').append(
                        '<div style="height:50px;border-left:thick double ' + (value['status'] == 1 ? 'limegreen' : 'orange') + ';margin:30px 20px;">' +
                        '<div style="height:40px;width:75px;float:left;background:' + (value['status'] == 1 ? 'limegreen' : 'orange') + ';margin:0 20px;padding:5px 0">' +
                        '<div style="width:75px;text-align:center;color:white;font-size:25px;">' + value['answer'] + '</div>' +
                        '<div style="width:75px;text-align:center;color:white;font-size:10px;">回答</div>' +
                        '</div>' +
                        '<a href="/Q&A/detail.html?id=' + value["q_id"] + '">' +
                        '<div onmouseover={$(event.target).css("color","' + (value['status'] == 1 ? 'green' : 'orange') + '");} ' +
                        'onmouseleave={$(event.target).css("color","black");} ' +
                        'style="line-height:50px;font-size:20px;cursor:pointer;">' +
                        value['title'] +
                        '<span style="float:right;color:black;font-size:8px;' +
                        'line-height:50px" onmouseleave={$(event.target).css("color","black");} ' +
                        ' onmouseover={event.stopPropagation();$(event.target).css("color","purple");}>' +
                        value['author'] + "&nbsp;&nbsp;&nbsp;&nbsp;" + value['date'] + '</span>' +
                        '</div></a>' +
                        '</div>'
                    );
            })
        }
    });
};
Vue.component('qasearch', {
    props: {
        search: {
            type: String,
            default: ''
        }
    },
    data: function () {
        return {
            listStyle: {
                backgroundColor: 'rgba(255,255,255,0.5)',
                padding: '40px 60px',
                height: '780px',
                width: '800px',
                margin: '9px 1px 0px 1px',
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
                top: '-840px',
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
            window.location.href = "./search.html?keyword=" + this.search;
        },
        listenkey: function (event) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if (e && e.keyCode == 13) {
                this.searchClick();
            }
        },
        qClick: function () {
            window.scrollTo(0,50000);
            // window.location.href = "./#QTitle";
        }
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
        getlist();
    },
    template:
        '<div>' +
        '<div id="list" :style="listStyle"></div>' +
        '<div :style="searchStyle">' +
        '<input name="search" v-model="search" type="text" placeholder="搜索问题关键字" :style="inputStyle" @keydown="listenkey">' +
        '<i class="layui-icon layui-icon-search" :style="buttonStyle" @click="searchClick"></i>' +
        '<zbutton bgcolor="red" w="90px" h="30px" fontsize="14px" shadowcolor="red" top="-25px" left="6px" position="relative" :click="qClick">我要提问 <i class="layui-icon layui-icon-edit"></i></zbutton>' +
        '</div>' +
        '</div>'
})