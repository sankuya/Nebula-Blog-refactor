Vue.component('showarticle', {
    props: {
        artid: {
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
                authorMotto: '只要你不调皮 我就和你玩',
                authorPhotoPath: '../public/img/defaultProfilePhoto.jpg'
            },
            titleStyle: {
                textAlign: 'center',
                fontFamily: '微软雅黑',
                lineHeight: '40px'
            },
            authorStyle: {
                textAlign: 'center',
                fontFamily: '黑体',
                color: 'lightgray',
                lineHeight: '20px',
                fontSize: '12px'
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
                height: '130px',
                width: '800px',
                backgroundColor: 'rgba(255,255,255,0.3)',
                borderRadius: '20px',
                margin: '30px auto 0 auto'
            },
            authorPhotoStyle: {
                height: '100px',
                width: '100px',
                position: 'relative',
                top: '50%',
                marginTop: '-50px',
                left: '10%'
            },
            authorNameStyle: {
                fontSize: '20px',
                position: 'relative',
                top: '35%',
                left: '7%',
                color: 'white',
                backgroundColor: 'skyBlue'
            },
            authorMottoStyle: {
                position: 'relative',
                top: '33%',
                left: '12%',
            }
        }
    },
    methods: {
        mdToHTML: function () {
            editormd.markdownToHTML("artContent", {
                emoji: true,
                taskList: true,
                tex: true,
                flowChart: true,
                sequenceDiagram: true,
                codeFold: true
            })
        },
        getArtData: function () {
            var artdata = this.data;
            var id = this.artid;
            var update = this.mdToHTML;
            var func = this.getAuthorData;
            let postData = {
                artid: id
            };
            $.ajax({
                type: "POST",
                url: "/api/article/getArticleData",
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify(postData),
                dataType: "json",
                success: function (data, status) {
                    if (data == "没有这篇文章") alert("该文章不存在");
                    else if (typeof (data) == 'object') {
                        artdata['title'] = data['title'];
                        artdata['author'] = data['author'];
                        artdata['date'] = data['date'];
                        artdata['uid'] = data['uid'];
                        $('#contentText').text(data['content']);
                        $('#indexTitle').text("" + data['title'] + " - " + data['author'] + " - Nebula");
                    } else alert(data);
                    update();
                    $('.editormd-html-preview').css('background-color', 'rgba(255,255,255,0.3)')
                        .css('min-height', '700px')
                        .css('border-radius', '20px');
                    $('pre code').each(function (i, block) {
                        hljs.highlightBlock(block);
                    });
                    func();
                },
            });
        },
        getAuthorData: function () {
            var authorData = this.data;
            let postData = {
                uid: authorData.uid
            };
            $.ajax({
                type: "POST",
                url: "/api/public/getUserDataByID",
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify(postData),
                dataType: "json",
                success: function (data, status) {
                    if (typeof (data) != 'object') {
                        alert(data);
                        return;
                    }
                    authorData['authorMotto'] = data['motto'];
                    authorData['authorPhotoPath'] = '../user/' + data['username'] + '/ProfilePhoto.jpg?' + Math.random();
                },
            });
        },
        focusClick: function () {
            alert("关注功能");
        },
        mailClick: function () {
            alert("私信功能");
        }
    },
    created() {
        this.getArtData();
    },
    template:
        '<div>' +
        '<h1 :style="titleStyle">{{data.title}}</h1>' +
        '<h2 :style="authorStyle">{{data.author}}</h2>' +
        '<h3 :style="dateStyle">{{data.date}}</h3>' +
        '<hr style="margin:20px" class="layui-bg-cyan"/>' +
        '<div id="artContent" :style="contentStyle">' +
        '<textarea id="contentText" style="display:none;">![](../public/img/logoRed.png)\n' +
        '# 我能吞下玻璃\n## 而不傷身體\n> 並非所有流浪者都迷失了自我\n' +
        '\n```c++\nprint("hello world");\nprint("hello Nebula");\n```\n' +
        '`仰望天堂吧`\n\n$$E=mc^2$$\n\n- [ ] 成为强大的人儿吧\n- [x] 你不是垃圾</textarea>' +
        '</div>' +
        '<div id="authorInfo" :style="authorInfoStyle">' +
        '<b :style="authorNameStyle">{{data.author}}</b>' +
        '<img :style="authorPhotoStyle" alt="假装有头像" :src="data.authorPhotoPath" class="layui-nav-img">' +
        '<i :style="authorMottoStyle">{{data.authorMotto}}</i>' +
        '<zbutton float="right" margin="50px 40px 50px 0" h="30px" w="50px" bgcolor="deepSkyBlue" shadowcolor="skyBlue" :click="mailClick" >私信</zbutton>' +
        '<zbutton float="right" margin="50px 20px 50px 0" h="30px" w="50px" bgcolor="red" shadowcolor="tomato" :click="focusClick" >关注</zbutton>' +
        '</div>' +
        '</div>'
})