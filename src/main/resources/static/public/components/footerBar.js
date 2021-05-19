Vue.component('footerbar',{
	data:function()
	{
		return{
			footerStyle:{
				position:'absolute',
				top:'',
				left:'0',
				height:'26px',
				width:'100%',
				margin:'5px 0',
				textAlign:'center',
				clear:'both',
				fontSize:'8px',
				backgroundColor:'rgb(255,251,240)',
				lineHeight:'13px',
				color:'black',
				opacity:'0.2',
				fontFamily:'微软雅黑'
			}
		}
	},
	template:
	'<div :style="footerStyle" @mouseenter="enter" @mouseleave="leave" id="footerbar">'+
		'<div id="time">'+new Date().toLocaleString()+'</div>'+
		'<div>Copyright &copy; 2018-2020 EddieMap1e All Rights Reserved.<a href="http://www.beian.miit.gov.cn/">粤ICP备20016557号</a></div>'+
	'</div>',
	methods:{
		enter:function(){
			$('#footerbar').css('opacity','0.5').css('font-size','10px');
		},
		leave:function(){
			$('#footerbar').css('opacity','0.2').css('font-size','8px');
		}
	},
	mounted() {
		$('#footerbar').css('top',''+$(document).height()-36+'px');
	}
})
setInterval(function(){
	var now=new Date().toLocaleString();
	$('#time').text(now);
	$('#footerbar').css('top',''+$(document).height()-36+'px')
},200);