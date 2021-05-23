Vue.component('jumptop',{
	data:function(){
		return{
			jumpTopStyle:{
				top:'90%',
				left:'93%',
				position:'fixed',
				cursor:'pointer',
				width:'55px',
				height:'55px',
				display:'none',
				zIndex:'9'
			}
		}
	},
	template:
	'<div @click="jump" id="jump_top" :style="jumpTopStyle">'+
		'<img src="../public/img/jump_top.png" style="width:55px"/>'+
	'</div>',
	methods:{
		jump:function(){
			$("html,body").animate({scrollTop:0},'slow');
		}
	},
	mounted() {
		$(window).scroll(function(){
			if($(window).scrollTop()>100)
				$('#jump_top').show();
			else $('#jump_top').hide();
		})
	}
})