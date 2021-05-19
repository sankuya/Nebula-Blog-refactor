Vue.component('forget',{
	props:{
		display:{
			type:String,
			default:''
		},
		action:{
			type:String,
			default:''
		}
	},
	data:function(){
		return{
			forgetStyle:{
				width: '400px',
				height: '300px',
				backgroundColor: 'rgba(255,251,240,0.6)',
				position: 'absolute',
				left: '50%',
				top: '50%',
				margin: '-150px 0 0 -200px',
				borderRadius: '30px',
				boxShadow: 'red 0 0 20px 5px',
				display:this.display
			},
			formStyle:{
				position:'relative',
				left:'18%',
				top:'13%',
				width:'250px',
				fontSize:'15px',
				fontWeigth:'bold',
				fontFamily:'微软雅黑'
			},
			inputStyle:{
				width:'235px',
				height: '20px',
				padding:'3px 6px'
			},
			loginStyle:{
				fontSize:'13px',
				float:'left',
				fontWeight:'normal',
				color:'darkred',
				fontFamily:'微软雅黑',
				margin:'8px 0',
				cursor:'pointer'
			},
			registStyle:{
				fontSize:'13px',
				float:'right',
				fontWeight:'normal',
				color:'darkred',
				fontFamily:'微软雅黑',
				margin:'8px 0',
				cursor:'pointer'
			},
			mail:'',
			tel:''
		}
	},
	template:
	'<div id="forget" :style="forgetStyle">'+
		'<form :style="formStyle" id="forgetform" :action="action" method="post" @keydown="listenkey">'+
			'<div style="text-align:center;margin:-20px 0 15px 0;"><img src="../public/img/logoRed.png" width="150px"/></div>'+
			'<input :style="inputStyle" type="text" name="mail" placeholder="请输入您的邮箱" v-model="mail"><br/><br/>'+
			'<input :style="inputStyle" type="text" name="mail" placeholder="请输入您的手机号码" v-model="tel"><br/><br/>'+
			'<zbutton margin="10px auto" :click="forget" w="250px" h="40px" bgcolor="rgba(255,0,0,0.8)" shadowcolor="rgb(233,123,123)">发送</zbutton>'+
			'<a :style="loginStyle" @click="login">登录</a>'+
			'<a :style="registStyle" @click="regist">注册</a>'+
		'</form>'+
	'</div>',
	methods:{
		forget:function(){
			alert("该功能正在开发中");
			return;
		},
		listenkey:function(event){
			var e=event||window.event||arguments.callee.caller.arguments[0];
			if(e&&e.keyCode==13){
				this.forget();
			}
		},
		login:function(){
			$('#forget').fadeOut(200);
			$('#login').fadeIn(500);
		},
		regist:function(){
			$('#forget').fadeOut(200);
			$('#regist').fadeIn(500);
		}
	}
})