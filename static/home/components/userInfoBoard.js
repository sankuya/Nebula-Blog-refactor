Vue.component('userinfoboard',{
	props:{
		userdata:{
			type:Object,
			default:function(){return{
				nickname:'',
				motto:'',
				userid:'',
				gender:'',
				hobby:'',
				QQ:'',
				location:'',
				blogname:'',
				username:''
			}}
		}
	},
	data:function(){
		return{
			bgStyle:{
				width:'270px',
				padding:'30px 10px 10px 10px',
				backgroundColor:'rgba(242,243,239,0.6)',
				float:'left',
				borderRadius:'10px',
			},
			indexStyle:{
				width:'100%',
				color:'navy',
				fontSize:'15px',
				lineHeight:'35px',
				cursor:'pointer',
				padding:'0 10px'
			},
			ProfilePhoto:''
		}
	},
	methods:{
		infoEdit:function(){
			if(this.userdata['userid']=='')alert("没登录还想改!");
			else {
				window.location.href="./info.html";
			}
		},
		indexOver:function(event){
			$(event.target).css('background-color','rgba(144,144,144,0.3)');
			$(event.target).css('color','white');
		},
		indexLeave:function(event){
			$(event.target).css('background-color','transparent');
			$(event.target).css('color','navy');
		},
		homeGo:function(){
			window.location.href="../home";
		},
		friendGo:function(){
			alert('好友功能');
		},
		albumGo:function(){
			alert('相册功能');
		},
		boardGo:function(){
			window.location.href="../Q&A";
		},
		appGo:function(){
			alert('应用功能');
		},
		articleGo:function(){
			window.location.href="../article";
		},
		None:function(){
			event.stopPropagation();
		}
	},
	mounted() {
		window.userdata=this.userdata;
		var changeIcon=setInterval(function(){
			if($('#genderText').text()=="女")$('#genderIcon').attr("class","layui-icon layui-icon-female").css('color',"pink");
			if($('#genderText').text()!="")clearInterval(changeIcon);
		},1);
		var showPhoto=setInterval(function(){
			if(userdata.username!='')
			{
				$('#profilephoto').attr("src","/user/"+this.userdata.username+"/ProfilePhoto.jpg?"+Math.random());
				clearInterval(showPhoto);
			}
		},100);
	},
	template:
	'<div :style="bgStyle">'+
		'<img style="height:100px;width:100px" alt="假装有头像" id="profilephoto" src="../public/img/defaultProfilePhoto.jpg" class="layui-nav-img">'+
		'<div style="width:150px;height:100px;float:right">'+
			'<b style="font-family:微软雅黑;font-size:18px;line-height:50px">{{userdata.nickname}}</b>'+
			'<p style="font-size:13px;height:45px;font-family:宋体">{{userdata.motto}}</p>'+
			'<i @click="infoEdit" class="layui-icon layui-icon-edit" style="color:Crimson;margin:0 0 0 120px;cursor:pointer"></i></span>'+
		'</div>'+
		'<hr style="margin:20px 0" class="layui-bg-cyan"/>'+
		'<div style="padding:0 20px;line-height:30px">'+
			'<i class="layui-icon layui-icon-user" style="color:purple"></i><span style="color:Maroon">&nbsp;&nbsp;用户id:&nbsp;&nbsp;&nbsp;{{userdata.userid}}</span><br/>'+
			'<i id="genderIcon" class="layui-icon layui-icon-male" style="color:red"></i><span style="color:Maroon">&nbsp;&nbsp;性别&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;<span id="genderText">{{userdata.gender}}</span></span><br/>'+
			'<i class="layui-icon layui-icon-tree" style="color:pink"></i><span style="color:Maroon">&nbsp;&nbsp;爱好&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;{{userdata.hobby}}</span><br/>'+
			'<i class="layui-icon layui-icon-login-qq" style="color:lightblue"></i><span style="color:Maroon">&nbsp;&nbsp;QQ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;{{userdata.QQ}}</span><br/>'+
			'<i class="layui-icon layui-icon-location" style="color:Peru"></i><span style="color:Maroon">&nbsp;&nbsp;所在地:&nbsp;&nbsp;&nbsp;{{userdata.location}}</span><br/>'+
		'</div>'+
		'<div style="margin:30px 0 10px">'+
			'<h4 style="font-size:11px;">导航</h4>'+
			'<div style="padding:10px">'+
				'<div @click="homeGo" @mouseover="indexOver" @mouseleave="indexLeave" :style="indexStyle"><i @mouseover="None" class="layui-icon layui-icon-home" style="color:navy"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首页</div>'+
				'<div @click="articleGo" @mouseover="indexOver" @mouseleave="indexLeave" :style="indexStyle"><i @mouseover="None" class="layui-icon layui-icon-align-left" style="color:navy"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文章</div>'+
				'<div @click="friendGo" @mouseover="indexOver" @mouseleave="indexLeave" :style="indexStyle"><i @mouseover="None" class="layui-icon layui-icon-friends" style="color:navy"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;好友</div>'+
				'<div @click="albumGo" @mouseover="indexOver" @mouseleave="indexLeave" :style="indexStyle"><i @mouseover="None" class="layui-icon layui-icon-camera" style="color:navy"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;相册</div>'+
				'<div @click="boardGo" @mouseover="indexOver" @mouseleave="indexLeave" :style="indexStyle"><i @mouseover="None" class="layui-icon layui-icon-form" style="color:navy"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;问答</div>'+
				'<div @click="appGo" @mouseover="indexOver" @mouseleave="indexLeave" :style="indexStyle"><i @mouseover="None" class="layui-icon layui-icon-app" style="color:navy"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;应用</div>'+
			'</div>'+
			'<h4 style="font-size:11px;">友链</h4>'+
			'<div style="padding:10px">'+
				'<div @mouseover="indexOver" @mouseleave="indexLeave" :style="indexStyle"><i @mouseover="None" class="layui-icon layui-icon-link" style="color:navy"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;空空如也</div>'+
			'</div>'+
		'</div>'+
	'</div>'
})
