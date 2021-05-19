Vue.component('regist',{
	props:{
		action:{
			type:String,
			default:''
		},
		display:{
			type:String,
			default:''
		}
	},
	data:function(){
		return{
			registStyle:{
				width:'400px',
				height:'580px',
				backgroundColor:'rgba(255,251,240,0.6)',
				position:'absolute',
				left:'50%',
				top:'50%',
				margin:'-290px 0 0 -200px',
				borderRadius:'30px',
				boxShadow:'red 0 0 30px 5px',
				display:this.display
			},
			formStyle:{
				position:'relative',
				left:'19%',
				top:'5%',
				width:'250px',
				fontSize:'15px',
				fontWeight:'500',
				fontFamily:'微软雅黑',
				lineHeight:'15px',
				color:'darkred'
			},
			inputStyle:{
				width:'235px',
				height: '20px',
				padding:'3px 6px'
			},
			loginStyle:{
				fontSize:'13px',
				float:'right',
				fontWeight:'normal',
				color:'darkred',
				fontFamily:'微软雅黑',
				margin:'15px 0',
				cursor:'pointer'
			},
			username:'',
			password:'',
			password2:'',
			tel:'',
			mail:''
		}
	},
	template:
	'<div id="regist" :style="registStyle">'+
		'<form id="registform" :action="action" method="post" :style="formStyle" @keydown="listenkey">'+
			'<div style="text-align:center;margin:-20px 0 10px 0;"><img src="../public/img/logoRed.png" width="150px"/></div>'+
			'<label>用户名</label><br/><br/>'+
			'<input v-model="username" type="text" name="username" placeholder="4-16位 以字母开头,仅包含数字字母" :style="inputStyle"><br/><br/>'+
			'<label>密码</label><br/><br/>'+
			'<input v-model="password" type="password" name="password" placeholder="6-16位 必须包含数字以及大小写字母" :style="inputStyle"><br/><br/>'+
			'<label>确定密码</label><br/><br/>'+
			'<input v-model="password2" type="password" name="password2" placeholder="再次输入您的密码" :style="inputStyle"><br/><br/>'+
			'<label>手机号码</label><br/><br/>'+
			'<input v-model="tel" type="text" name="tel" placeholder="11位手机号码" :style="inputStyle"><br/><br/>'+
			'<label>邮箱地址</label><br/><br/>'+
			'<input v-model="mail" type="text" name="mail" placeholder="请输入您的邮箱地址" :style="inputStyle"><br/><br/><br/>'+
			'<zbutton margin="10px auto" :click="regist" w="250px" h="40px" bgcolor="rgba(30,152,239,0.8)" shadowcolor="rgb(65,105,225)">注 册</zbutton>'+
			'<a :style="loginStyle" @click="login">登录</a>'+
		'</form>'+
	'</div>',
	methods:{
		regist:function(){
			if(this.username.trim()==""||this.username==null){
				alert("用户名不能空");
				return;
			}
			if(!(/^[A-Za-z]{1}[A-Za-z0-9]{3,15}$/.test(this.username))){
				alert("用户名必须以字母开头,只能包含数字字母的4-16位");
				return;
			}
			if(this.password.trim()==""||this.password==null){
				alert("密码不能为空");
				return;
			}
			if(!(/(?=^[a-zA-Z0-9]{6,16}$)(?=.*[A-Z])(?=.*[a-z])(?=.*\d).*$/.test(this.password))){
				alert("密码必须至少有一个数字,一个大写字母和一个小写字母,且不能包含其他符号,长度为6-16位");
				return;
			}
			if(this.password2.trim()==""||this.password2==null){
				alert("重复密码不能为空");
				return;
			}
			if(this.password!=this.password2){
				alert("两次输入的密码不一致");
				return;
			}
			if(this.tel.trim()==""||this.tel==null){
				alert("手机号码不能为空");
				return;
			}
			if(!(/^1[345789]\d{9}$/.test(this.tel))||this.tel.length!=11){
				alert("请填写正确的11位手机号码");
				return;
			}
			if(this.mail.trim()==""||this.mail==null){
				alert("邮箱地址不能为空");
				return;
			}
			if(!(/^[a-z0-9]{1}[a-z0-9_-]{1,}@[a-z0-9]{1,}\.[a-z]{2,}$/.test(this.mail))){
				alert("请填写正确的邮箱格式");
				return;
			}
			let postData={
				username:this.username,
				password:this.password,
				tel:this.tel,
				mail:this.mail
			};
			$.ajax({
				url:"/login/regist",
				type:"POST",
				contentType:"application/json",
				data:JSON.stringify(postData),
				success:function(data,status){
					alert(data);
					if(data=="注册成功"){
						window.alert_ok=function(){
							$('#regist').fadeOut(200);
							$('#login').fadeIn(500);
							window.alert_ok=function(){};
						}
					}
				}
			});
		},
		listenkey:function(){
			var e=event||window.event||arguments.callee.caller.arguments[0];
			if(e&&e.keyCode==13){
				this.regist();
			}
		},
		login:function(){
			$('#regist').fadeOut(200);
			$('#login').fadeIn(500);
		}
	}
})
