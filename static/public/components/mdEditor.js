Vue.component('mdeditor',{
	props:{
		edit:{
			type:String,
			default:""
		},
		placeholder:{
			type:String,
			default:"支持全部Markdown语法哦~ \nEnjoy Writing~"
		},
		width:{
			type:String,
			default:"1100px"
		},
		height:{
			type:String,
			default:"700px"
		},
		theme:{
			type:String,
			default:"mdn-like"
		}
	},
	methods:{
		init:function(){
			var edit=this.edit;
			window.myeditor=editormd("editor", {
				placeholder: this.placeholder,
				width: this.width,
				height: this.height,
				syncScrolling: "single",  
				path: "../editormd/lib/",
				theme: "default",
				previewTheme: "default",
				editorTheme: this.theme,
				saveHTMLToTextarea: true,
				emoji: false,
				taskList: true, 
				tocm: true,
				tex: true,
				flowChart: true,
				sequenceDiagram: true,
				toolbarIcons : function() {
					return ["undo","redo",
					"|","bold","del","italic",
					"|","h1","h2","h3","h4","h5","h6",
					"|","code-block","datetime","link",
					"|","list-ol","list-ul","hr",
					"|","image",
					"||","Henshin","watch","preview"]
				},
				onload:function(){
					this.unwatch();
					$('.CodeMirror').css('margin-top','');
					if(edit.length)this.setMarkdown(edit);
				},
				imageUpload:true,
				imageFormats:["jpg", "jpeg", "gif", "png", "bmp", "webp"],
				imageUploadURL:"/api/public/uploadImage",
				toolbarIconsClass:{
					Henshin:"fa-star"
				},
				toolbarHandlers:{
					Henshin:function(cm,icon,cursor,selection)
					{
						themes=["default",
						"ambiance",
						"3024-day",
						"3024-night",
						"base16-dark",
						"base16-light",
						"blackboard",
						"cobalt",
						"eclipse",
						"elegant",
						"erlang-dark",
						"lesser-dark",
						"mbo",
						"mdn-like",
						"midnight",
						"monokai",
						"neat",
						"neo",
						"night",
						"paraiso-dark",
						"paraiso-light",
						"pastel-on-dark",
						"rubyblue",
						"solarized",
						"the-matrix",
						"tomorrow-night-eighties",
						"twilight",
						"vibrant-ink",
						"xq-dark",
						"xq-light"];
						var wow=Math.floor(Math.random()*30);
						console.log("你发现了!你更换了第"+wow+"个主题:"+themes[wow]);
						// editormd("editor",{editorTheme:themes[wow]});
					}
				}
			});
			$('#editor').css('z-index','2');
		}
	},
	mounted() {
		this.init();
	},
	template:
	'<div>'+
	    '<div id="editor">'+
	        '<textarea></textarea>'+
	    '</div>'+
	'</div>'
})
