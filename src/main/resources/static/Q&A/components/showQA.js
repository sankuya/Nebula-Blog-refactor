Vue.component('showqa', {
    props: {
        qid: {
            type: Number,
            default: 1
        }
    },
    data: function () {
        return {
            data: {
                title: 'Welcome but 你麋鹿了',
                author: 'Nebula',
                date: 'forever',
                uid: 0,
                authorPhotoPath: '../public/img/defaultProfilePhoto.jpg'
            },
            titleStyle: {
                textAlign: 'center',
                fontFamily: '微软雅黑',
                lineHeight: '40px'
            },
            dateStyle: {
                textAlign: 'center',
                fontFamily: '宋体',
                color: 'gray',
                lineHeight: '20px',
                fontSize: '9px'
            },
            contentStyle: {
                width: '760px',
                margin: 'auto'
            },
            authorInfoStyle: {
                marginTop: '30px',
                height: '100px',
                width: '800px',
            },
            authorPhotoStyle: {
                height: '75px',
                width: '75px',
                position: 'relative',
                float: 'right',
            },
            authorNameStyle: {
                fontSize: '15px',
                position: 'relative',
                float: 'right',
                lineHeight: '75px',
                marginRight: '20px',
            },
        }
    },
    methods: {
        mdToHTML: function (id) {
            editormd.markdownToHTML(id, {
                emoji: true,
                taskList: true,
                tex: true,
                flowChart: true,
                sequenceDiagram: true,
                codeFold: true
            })
        },
        getQData: function () {
            var qdata = this.data;
            var id = this.qid;
            var update = this.mdToHTML;
            var func = this.getAuthorData;
            let postData = {
                qid: id
            };
            $.ajax({
                url: "/FAQ/getQData",
                type: "POST",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify(postData),
                success: function (data, status) {
                    if (data == "没有这篇问题") alert("该问题不存在");
                    else if (typeof (data) == 'object') {
                        qdata['title'] = data['title'];
                        qdata['author'] = data['author'];
                        qdata['date'] = data['date'];
                        qdata['uid'] = data['uid'];
                        $('#contentText').text(data['content']);
                        $('#indexTitle').text("" + data['title'] + " - Nebula");
                    } else alert(data);
                    update("QContent");
                    $('.editormd-html-preview').css('background-color', 'rgba(255,255,255,0.3)')
                        .css('min-height', '200px')
                        .css('border-radius', '20px');
                    $('pre code').each(function (i, block) {
                        hljs.highlightBlock(block);
                    });
                    func();
                }
            });
        },
        getAData: function () {
            var adata = this.data;
            var id = this.qid;
            var update = this.mdToHTML;
            let postData = {
                qid: id
            }
            $.ajax({
                url: "/FAQ/getAData",
                type: "post",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify(postData),
                success: function (data, status) {
                    var floor = 0;
                    $.each(data, function (key, value) {
                        floor++;
                        var path = '../user/' + value['username'] + '/ProfilePhoto.jpg?' + Math.random();
                        if (typeof (value) == "object")
                            $('#answerlist').append(
                                '<hr style="margin:20px" class="layui-bg-blue"/>' +
                                '<div id="answer' + floor + '" style="width:760px;margin:auto">' +
                                '<textarea id="contentText" style="display:none;">' + value['content'] + '</textarea>' +
                                '</div>' +
                                '<div style="margin-top:30px;height:100px;width:800px;">' +
                                '<img style="height:75px;width:75px;position:relative;float:right;" alt="假装有头像" src="' + path + '" class="layui-nav-img">' +
                                '<span style="font-size:15px;position:relative;float:right;line-height:75px;margin-right:20px;">#' + floor + ": " + value['author'] + '</span>' +
                                '</div>'
                            );
                        update('answer' + floor);
                        $('.editormd-html-preview').css('background-color', 'rgba(255,255,255,0.3)')
                            .css('min-height', '200px')
                            .css('border-radius', '20px');
                        $('pre code').each(function (i, block) {
                            hljs.highlightBlock(block);
                        });
                    })
                }
            });
        },
        getAuthorData: function () {
            var authorData = this.data;
            let postData={
            	uid:authorData.uid
			};
            $.ajax({
				url:"/public/getUserDataByID",
				type:"POST",
				dataType:"json",
				contentType:"application/json",
				data:JSON.stringify(postData),
				success:function (data, status) {
					if (typeof (data) != 'object') {
						alert(data);
						return;
					}
					authorData['authorPhotoPath'] = '../user/' + data['username'] + '/ProfilePhoto.jpg?' + Math.random();
				}
			});
        },
        AClick: function () {
            if (window.location.href.match("#editor")) window.location.href = window.location.href;
            else window.location.href = window.location.href + "#editor";
        },
    },
    created() {
        this.getQData();
        this.getAData();
    },
    template:
        '<div>' +
        '<h1 :style="titleStyle">{{data.title}}</h1>' +
        '<h3 :style="dateStyle">{{data.date}}</h3>' +
        '<hr style="margin:20px" class="layui-bg-cyan"/>' +
        '<div id="QContent" :style="contentStyle">' +
        '<textarea id="contentText" style="display:none;">![](../public/img/logoRed.png)\n' +
        '# 我能吞下玻璃\n## 而不傷身體\n> 並非所有流浪者都迷失了自我\n' +
        '\n```c++\nprint("hello world");\nprint("hello Nebula");\n```\n' +
        '`仰望天堂吧`\n\n$$E=mc^2$$\n\n- [ ] 成为强大的人儿吧\n- [x] 你不是垃圾</textarea>' +
        '</div>' +
        '<div id="authorInfo" :style="authorInfoStyle">' +
        '<img :style="authorPhotoStyle" alt="假装有头像" :src="data.authorPhotoPath" class="layui-nav-img">' +
        '<span :style="authorNameStyle">{{data.author}}</span>' +
        '</div>' +
        '<zbutton :click="AClick">我来回答</zbutton>' +
        '<div id="answerlist"></div>' +
        '</div>'
})