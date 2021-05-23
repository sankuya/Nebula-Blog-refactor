var scrollBefore = $(window).scrollTop();
Vue.component('topbar',{
	props:{
		nickname:{
			type:String,
			default:'登录/注册'
		}
	},
	data:function(){
		return{
			topBarStyle:{
				width:'100%',
				position:'fixed',
				height:'35px',
				backgroundColor:'rgba(255,251,240,0.5)',
				margin:'4px 0 0 0',
				zIndex:'10'
			},
			logoStyle:{
				positon:'relative',
				width:'100px',
				height:'35px',
				cursor:'pointer',
				left:'50%'
			},
			itemStyle:{
				positon:'absolute',
				float:'right',
				margin:'-32px 10px 0 10px',
				lineHeight:'29px',
				fontSize:'14px',
				fontFamily:'微软雅黑',
				backgroundColor:'rgba(255,251,240,0.6)',
				padding:'0 18px',
				borderRadius:'15px',
				height:'29px',
				color:'blue',
				cursor:'pointer',
				textAlign:'center'
			},
			itemListStyle:{
				backgroundColor:'rgba(255,251,240,0.8)',
				margin:'-15px -18px 0 -18px',
				borderRadius:'0 0 15px 15px',
				display:'none',
				width:'auto',
				height:'auto',
				padding:'18px 5px 8px 5px'
			},
			childStyle:{
				height:'25px',
				lineHeight:'25px',
				margin:'3px 2px'
			},
		}
	},
	template:
	'<div id="topbar" :style="topBarStyle">'+
		'<center><img @click="homeclick" id="homelogo" :style="logoStyle" src="../public/img/logoBlue.png" @mouseover="logoover" @mouseleave="logoleave"/></center>'+
		'<div id="topBarNickname" :style="itemStyle" @click="userclick" @mouseover="itemover" @mouseleave="itemleave">'+
			"{{nickname}}"+
			'<div id="topBarNicknameList" :style="itemListStyle">'+
				'<div :style="childStyle" @mouseover="childover" @mouseleave="childleave" @click="personal_info">个人信息</div>'+
				'<div :style="childStyle" @mouseover="childover" @mouseleave="childleave" @click="message">消息</div>'+
				'<div :style="childStyle" @mouseover="childover" @mouseleave="childleave" @click="setting">设置</div>'+
				'<div :style="childStyle" @mouseover="childover" @mouseleave="childleave" @click="logout">登出</div>'+
			'</div>'+
		'</div>'+
	'</div>',
	methods:{
		logoover:function(){
			$('#homelogo').attr("src","../public/img/logoRed.png");
		},
		logoleave:function(){
			$('#homelogo').attr("src","../public/img/logoBlue.png");
		},
		itemover:function(){
			$('#topBarNickname').css('color','red').css('background-color','rgba(255,251,240,1)');
			if(this.nickname!="登录/注册"&&this.nickname.trim()!='')
				$('#topBarNicknameList').slideDown();
		},
		itemleave:function(){
			$('#topBarNickname').css('color','blue').css('background-color','rgba(255,251,240,0.6)');
			$('#topBarNicknameList').hide();
		},
		userclick:function(){
			if(this.nickname=="登录/注册"){
				this.logout();
			}
			else {
				window.location.href="../home";
			}
		},
		childover:function(event){
			$(event.target).css('background-color','rgba(144,144,144,0.3)');
			$(event.target).css('color','white');
		},
		childleave:function(event){
			$(event.target).css('background-color','transparent');
			$(event.target).css('color','red');
		},
		personal_info:function(){
			event.stopPropagation();
			window.location.href="../home/info.html"
		},
		message:function(){
			event.stopPropagation();
			alert("消息功能");
		},
		setting:function(){
			event.stopPropagation();
			alert("设置功能");
		},
		logout:function(){
			event.stopPropagation();
			$.post({
				url:"/api/public/logout",
				type:"POST",
				success:function(data){
					if(data!="ok")alert("登出失败");
					return;
				}
			});
			window.location.href="../login";
		},
		homeclick:function(){
			window.location.href="../home";
		}
	},
	mounted() {
		$(window).scroll(function(){
			if($(window).scrollTop()>scrollBefore)
				$('#topbar').hide();
			else $('#topbar').show();
			scrollBefore=$(window).scrollTop();
		})
	},
})
