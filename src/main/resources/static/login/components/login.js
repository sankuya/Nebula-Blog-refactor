Vue.component('login', {
    props: {
        action: {
            type: String,
            default: ''
        },
        display: {
            type: String,
            default: ''
        }
    },
    data: function () {
        return {
            panelStyle: {
                width: '400px',
                height: '300px',
                backgroundColor: 'rgba(255,251,240,0.6)',
                position: 'absolute',
                left: '50%',
                top: '50%',
                margin: '-150px 0 0 -200px',
                borderRadius: '30px',
                boxShadow: 'red 0 0 20px 5px',
                display: this.display
            },
            formStyle: {
                position: 'relative',
                left: '18%',
                top: '13%',
                width: '250px',
                fontSize: '15px',
                fontWeigth: 'bold',
                fontFamily: '微软雅黑'
            },
            inputStyle: {
                width: '235px',
                height: '20px',
                padding: '3px 6px',
                border: '1px solid transparent'
            },
            forgetStyle: {
                fontSize: '13px',
                float: 'left',
                fontWeight: 'normal',
                color: 'darkred',
                fontFamily: '微软雅黑',
                margin: '8px 0',
                cursor: 'pointer'
            },
            registStyle: {
                fontSize: '13px',
                float: 'right',
                fontWeight: 'normal',
                color: 'darkred',
                fontFamily: '微软雅黑',
                margin: '8px 0',
                cursor: 'pointer'
            },
            userIconStyle: {
                position: 'absolute',
                fontSize: '17px',
                color: 'darkred',
                left: '-26px',
                lineHeight: '30px'
            },
            passwordIconStyle: {
                position: 'absolute',
                fontSize: '17px',
                color: 'darkred',
                left: '-26px',
                lineHeight: '30px'
            },
            username: '',
            password: ''
        }
    },
    template:
        '<div :style="panelStyle" id="login">' +
        '<form id="loginform" :style="formStyle" method="post" :action="action" @keydown="listenkey">' +
        '<div style="text-align:center;margin:-20px 0 15px 0;"><img src="../public/img/logoRed.png" width="150px"/></div>' +
        '<i :style="userIconStyle" class="layui-icon layui-icon-username"></i>' +
        '<input name="username" maxLength="16" v-model="username" type="text" placeholder="用户名/邮箱/手机号" :style="inputStyle"><br/><br/>' +
        '<i :style="passwordIconStyle" class="layui-icon layui-icon-password"></i>' +
        '<input name="password" maxLength="16" v-model="password" type="password" placeholder="密码" :style="inputStyle"><br/><br/>' +
        '<zbutton :click="login" w="250px" margin="10px auto">登 录</zbutton>' +
        '<a :style="forgetStyle" @click="forget">忘记密码</a>' +
        '<a :style="registStyle" @click="regist">注册</a>' +
        '</form>' +
        '</div>',
    methods: {
        login: function () {
            if (this.username == '') {
                alert("账号不能为空");
                return;
            }
            if (this.password == '') {
                alert("密码不能为空");
                return;
            }
            let data = {
                username: this.username,
                password: this.password
            };
            $.ajax({
                type: "POST",
                url: "/login/login",
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify(data),
                dataType: "json",
                success: function (data, status) {
                    alert(data.status);
                    if (data.status == "登陆成功") window.alert_ok = function () {

                        window.alert_ok = function () {
                        };

                        window.location.href = ("../home");
                    }
                },
            });
        },
        listenkey: function (event) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if (e && e.keyCode == 13) {
                this.login();
            }
        },
        forget: function () {
            $('#login').fadeOut(200);
            $('#forget').fadeIn(500);
        },
        regist: function () {
            $('#login').fadeOut(200);
            $('#regist').fadeIn(500);
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
    }
})