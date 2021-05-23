Vue.component('alert',{
	props:{
		ok:{
			type:String,
			default:"确定"
		}
	},
	data:function(){
		return{
			winStyle:{
				position:'absolute',
				left:'50%',
				top:""+(window.innerHeight/2+document.documentElement.scrollTop)+"px",
				height:'200px',
				width:'400px',
				margin:'-120px 0 0 -220px',
				backgroundColor:'rgba(235,245,253,0.8)',
				borderRadius:'10px',
				boxShadow:'pink 0 0 10px 8px, pink 0 0 10px 8px inset',
				zIndex:'2334',
				padding:'20px'
			},
			textStyle:{
				display:'table-cell',
				verticalAlign:'middle',
				textAlign:'center',
				fontSize:'18px',
				margin:'10px auto',
				padding:'15px',
				color:'red',
				fontFamily:'微软雅黑',
				fontWeight:'bold',
				width:'370px',
				height:'140px',
				lineHegiht:'25px',
				letterSpacing:'1px'
			},
			shadowStyle:{
				position:'absolute',
				left:'0',
				top:'0',
				width:'100%',
				height:Math.max(document.body.scrollHeight,document.documentElement.scrollHeight,document.documentElement.offsetHeight)+'px',
				backgroundColor:'rgba(0,0,0,0.6)',
				zIndex:'2333'
			}
		}
	},
	template:
	'<div id="alert" oncontextmenu="return false" onselectstart="return false">'+
	'<div :style="shadowStyle"></div>'+
	'<div :style="winStyle">'+
		'<div :style="textStyle"><slot></slot></div>'+
		'<zbutton  bgcolor="rgb(102,157,246)" h="35px" w="100px"'+
		'shadowcolor="rgb(102,100,255)" :click="click">{{ok}}</zbutton>'+
	'</div></div>',
	methods:{
		click:function(){
			if(typeof(this.$parent.alertshow)=="undefined"){
				console.log("父组件没有alertshow变量");
				return;
			}
			if(typeof(window.alert_ok)=="undefined"){
				console.log("没有设置alert_ok函数");
				return;
			}
			this.$parent.alertshow=false;
			window.alert_ok();
		}
	}
})