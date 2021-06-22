Vue.component('infoedit', {
    props: {
        userdata: {
            type: Object,
            default: function () {
                return {
                    nickname: '',
                    motto: '',
                    userid: '',
                    gender: '',
                    hobby: '',
                    QQ: '',
                    location: '',
                    blogname: '',
                    username: '',
                    tel: '',
                    mail: ''
                }
            }
        }
    },
    data: function () {
        return {
            bgStyle: {
                height: '850px',
                backgroundColor: 'rgba(242,243,239,0.7)',
                padding: '40px 130px',
                borderRadius: '25px'
            },
            inputStyle: {
                height: '28px',
                border: '1px solid pink',
                width: '205px',
                backgroundColor: 'rgba(242,243,239,0.6)',
                padding: '0 5px'
            },
            tagStyle: {
                width: '58px',
                float: 'left',
                padding: '6px',
                borderRadius: '20px',
                backgroundColor: 'rgba(242,243,239,0.6)',
                margin: '0 50px 0 0',
                cursor: 'default'
            },
            lineStyle: {
                margin: '30px 0',
            }
        }
    },
    methods: {
        confirm: function () {
            var userdata = this.userdata;
            if (userdata.nickname.trim() == "") {
                alert('昵称不能为空哦~');
                return;
            }
            if (userdata.blogname.trim() == "") {
                alert('博客名不能为空哦~');
                return;
            }
            if (userdata.nickname.trim().length > 7) {
                alert('哎呀 名字太长可不好 最多七个字哦');
                return;
            }
            if (userdata.motto.trim().length > 16) {
                alert('看来你的心事重重呢 开心点 16字以内的心情叭~');
                return;
            }
            if (userdata.blogname.trim().length > 12) {
                alert('你的博客名太长啦 12个字以内好了');
                return;
            }
            let data = {
                nickname: userdata.nickname.trim(),
                blogname: userdata.blogname.trim(),
                motto: userdata.motto.trim(),
                gender: userdata.gender,
                location: userdata.location.trim(),
                hobby: userdata.hobby.trim(),
                QQ: userdata.QQ.trim()
            };
            $.ajax({
                type: "POST",
                url: "/api/home/infoChange",
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify(data),
                success: function (data, status) {
                    console.log(data, typeof data);
                    if (data == "ok") {
                        alert('修改成功');
                        window.alert_ok = function () {
                            window.alert_ok = function () {
                            };
                            window.location.href = "../home";
                        }
                    } else alert('由于未知原因修改失败');
                }
            });
        },
        cancel: function () {
            window.location.href = "../home"
        }
    },
    mounted() {
        window.userdata = this.userdata;
        $('input').each(function () {
            if ($(this).attr('type') == "text") {
                $(this).on('focus', function () {
                    $(this).css('border', '1px solid blue');
                });
                $(this).on('blur', function () {
                    $(this).css('border', '1px solid pink');
                });
            }
        });
        var showPhoto = setInterval(function () {
            if (userdata.username != '') {
                $('#profilephoto').attr("src", "/user/" + this.userdata.username + "/ProfilePhoto.jpg?" + Math.random());
                clearInterval(showPhoto);
            }
        }, 100);
        layui.use('upload', function () {
            var upload = layui.upload;
            var uploadInst = upload.render({
                elem: '#profilephoto',
                url: '/api/home/uploadPhoto',
                accept: 'images',
                acceptMime: 'image/jpeg',
                size: '2388.992',
                done: function (res) {
                    if (res['status'] == "ok") {
                        $('#profilephoto').attr("src", "" + res['path'] + "?" + Math.random());
                        alert('更换成功啦');
                    } else {
                        alert('更换失败了诶');
                    }
                }
            });
        })
    },
    template:
        '<div :style="bgStyle">' +
        '<center><img style="height:100px;width:100px;cursor:pointer" id="profilephoto" alt="假装有头像"  src="../public/img/defaultProfilePhoto.jpg" title="戳我更换头像" class="layui-nav-img"></center>' +
        '<div :style="lineStyle"><div onselectstart="return false" :style="tagStyle">&nbsp;&nbsp;邮&nbsp;&nbsp;&nbsp;&nbsp;箱</div><input :style="inputStyle" type="text" disabled="true" v-model=userdata.mail /></div>' +
        '<div :style="lineStyle"><div onselectstart="return false" :style="tagStyle">&nbsp;&nbsp;手&nbsp;&nbsp;&nbsp;&nbsp;机</div><input :style="inputStyle" type="text" disabled="true" v-model=userdata.tel /></div>' +
        '<div :style="lineStyle"><div onselectstart="return false" :style="tagStyle">用&nbsp;&nbsp;户&nbsp;&nbsp;名</div><input :style="inputStyle" type="text" disabled="true" v-model=userdata.username /></div>' +
        '<div :style="lineStyle"><div onselectstart="return false" :style="tagStyle">用&nbsp;户&nbsp;&nbsp;i&nbsp;d</div><input :style="inputStyle" type="text" disabled="true" v-model=userdata.userid /></div>' +
        '<div :style="lineStyle"><div onselectstart="return false" :style="tagStyle">&nbsp;&nbsp;昵&nbsp;&nbsp;&nbsp;&nbsp;称</div><input maxlength="8" :style="inputStyle" type="text" v-model=userdata.nickname /></div>' +
        '<div :style="lineStyle"><div onselectstart="return false" :style="tagStyle">博&nbsp;&nbsp;客&nbsp;&nbsp;名</div><input maxlength="15" :style="inputStyle" type="text" v-model=userdata.blogname /></div>' +
        '<div :style="lineStyle"><div onselectstart="return false" :style="tagStyle">个性签名</div><input maxlength="20" :style="inputStyle" type="text" v-model=userdata.motto /></div>' +
        '<div :style="lineStyle"><div onselectstart="return false" :style="tagStyle">&nbsp;&nbsp;性&nbsp;&nbsp;&nbsp;&nbsp;别</div><div style="line-height:30px"><input id="male" type="radio" value="男" v-model=userdata.gender />' +
        '<label for="male">男</label>&nbsp;&nbsp;&nbsp;&nbsp;<input id="female" type="radio" value="女" v-model=userdata.gender /><label for="female">女</label></div></div>' +
        '<div :style="lineStyle"><div onselectstart="return false" :style="tagStyle">&nbsp;&nbsp;地&nbsp;&nbsp;&nbsp;&nbsp;区</div><input maxlength="20" :style="inputStyle" type="text" v-model=userdata.location /></div>' +
        '<div :style="lineStyle"><div onselectstart="return false" :style="tagStyle">&nbsp;&nbsp;爱&nbsp;&nbsp;&nbsp;&nbsp;好</div><input maxlength="20" :style="inputStyle" type="text" v-model=userdata.hobby /></div>' +
        '<div :style="lineStyle"><div onselectstart="return false" :style="tagStyle">&nbsp;&nbsp;&nbsp;Q&nbsp;&nbsp;&nbsp;&nbsp;Q</div><input maxlength="13" :style="inputStyle" type="text" v-model=userdata.QQ /></div>' +
        '<zbutton margin="25px 0" float="left" w="120px" :click="confirm">确定修改</zbutton>' +
        '<zbutton margin="25px 0" float="right" w="120px" bgcolor="deepskyblue" shadowcolor="skyblue" :click="cancel">取消修改</zbutton>' +
        '</div>'
})