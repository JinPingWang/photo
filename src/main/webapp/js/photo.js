/**
 * 这部分功能是当鼠标放到照片上时，用于突显
 */
$(function(){
	$('.col-md-3 .row').hover(function(){
		$('.mark',this).fadeIn();
	},function(){
		$('.mark',this).fadeOut();
	});
}); 

/**
 * 这部分功能用于音乐播放
 */
$(document).ready(function(){
	/**
	 * 第一次播放
	 */
    var audio = $("audio").get(0);
    audio.pause();
    var num = GetRandomNum(1,2); 
    audio.src = "/photo/music/"+num+".mp3";
    audio.play();
    
    /**
     * 播放结束后事件
     */
    audio.addEventListener("ended", function(){
    	audio.currentTime = 0;

        audio.pause();
        var num = GetRandomNum(1,2); 
        audio.src = "/photo/music/"+num+".mp3";
        audio.play();
    });
    
    /**
     * 随机播放的实现
     */
    function GetRandomNum(Min,Max)
    {   
        var Range = Max - Min;   
        var Rand = Math.random();   
        return(Min + Math.round(Rand * Range));   
    }   	
})

/**
 * 如果是通过ajax动态加载的元素添加进行html中
 * 则不能直接使用$(选择器).hover事件
 * $(选择器).hover只能用在已经有的元素，即不是动态加载的元素
 */
$(document).on(
	'mouseenter', 'li', function(){$(".mark", this).fadeIn();}
);

$(document).on(
	'mouseleave', 'li', function(){$(".mark", this).fadeOut();}
);

$(document).on(
		'click', '.mark', function(){$(".Original img", $(this).parent()).trigger("click");}
);

/**
 * 这部分用于处理：当鼠标放到返回顶部时，设置相应的透明度
 */
$(document).on(
		'mouseenter', '.topButton', function(){$('.topButton').css("opacity", "1.0")}
)

$(document).on(
		'mouseleave', '.topButton', function(){$('.topButton').css("opacity", "0.6")}
)

/**
 * 这部分代码用于添加返回顶部功能
 */
var scrolltotop={
		setting:{
		    startline:100, //起始行
		    scrollto:0, //滚动到指定位置
		    scrollduration:400, //滚动过渡时间
		    fadeduration:[500,100] //淡出淡现消失
		},
		controlHTML:'<img class="topButton" src="/photo/image/top.png" style="width:35px; height:35px; border:0;" />', //返回顶部按钮

		controlattrs: { offsetx: 5.5, offsety: 93 }, //返回按钮固定位置
		anchorkeyword:"#top",
		state:{
		    isvisible:false,
		    shouldvisible:false
		},scrollup:function(){
		    if(!this.cssfixedsupport){
		        this.$control.css({opacity:0});
		    }
		    var dest=isNaN(this.setting.scrollto)?this.setting.scrollto:parseInt(this.setting.scrollto);
		    if(typeof dest=="string"&&jQuery("#"+dest).length==1){
		        dest=jQuery("#"+dest).offset().top;
		    }else{
		        dest=0;
		    }
		    this.$body.animate({scrollTop:dest},this.setting.scrollduration);
		},keepfixed:function(){
		    var $window = jQuery(window);
		    var controlx=$window.scrollLeft()+$window.width()-this.$control.width()-this.controlattrs.offsetx;
		    var controly = $window.scrollTop() + $window.height() - this.$control.height() - this.controlattrs.offsety;
		    this.$control.css({left:controlx+"px",top:controly+"px"});
		},togglecontrol:function(){
		    var scrolltop=jQuery(window).scrollTop();
		    if(!this.cssfixedsupport){
		        this.keepfixed();
		    }
		    this.state.shouldvisible=(scrolltop>=this.setting.startline)?true:false;
		    if(this.state.shouldvisible&&!this.state.isvisible){
		        this.$control.stop().animate({opacity:1},this.setting.fadeduration[0]);
		        this.state.isvisible=true;
		    }else{
		        if(this.state.shouldvisible==false&&this.state.isvisible){
		            this.$control.stop().animate({opacity:0},this.setting.fadeduration[1]);
		            this.state.isvisible=false;
		        }
		    }
		},init:function(){
		    jQuery(document).ready(function ($) {
		        var mainobj=scrolltotop;
		        var iebrws=document.all;
		        mainobj.cssfixedsupport=!iebrws||iebrws&&document.compatMode=="CSS1Compat"&&window.XMLHttpRequest;
		        mainobj.$body=(window.opera)?(document.compatMode=="CSS1Compat"?$("html"):$("body")):$("html,body");
		        mainobj.$control=$('<div id="topcontrol">'+mainobj.controlHTML+"</div>").css({position:mainobj.cssfixedsupport?"fixed":"absolute",bottom:mainobj.controlattrs.offsety,right:mainobj.controlattrs.offsetx,opacity:0,cursor:"pointer"}).attr({title:"返回顶部"}).click(function(){mainobj.scrollup();return false;}).appendTo("body");if(document.all&&!window.XMLHttpRequest&&mainobj.$control.text()!=""){mainobj.$control.css({width:mainobj.$control.width()});}mainobj.togglecontrol();
		        $('a[href="'+mainobj.anchorkeyword+'"]').click(function(){mainobj.scrollup();return false;});
		        $(window).bind("scroll resize",function(e){mainobj.togglecontrol();});
		    });
		}};
		scrolltotop.init();