//生成菜单
var menuItem = Vue.extend({
	name: 'menu-item',
	props:{item:{},index:0},
	template:[
	          '<li :class="{active: (item.type===0 && index === 0)}">',
				  '<a v-if="item.type === 0" href="#" class="dropdown-toggle">',
					  '<i v-if="item.icon != null" :class="\'menu-icon \'+item.icon"></i>',
					  '<span class="menu-text"> {{item.name}} </span>',
					  '<b class="arrow fa fa-angle-down"></b>',
				  '</a>',
				  '<b class="arrow"></b>',
				  '<ul v-if="item.type === 0" class="submenu">',
					  '<menu-item :item="item" :index="index" v-for="(item, index) in item.list"></menu-item>',
				  '</ul>',
				  '<a v-if="item.type === 1" :href="\'#\'+item.url" data-url="{{item.url}}">' +
					  '<i v-if="item.icon != null" :class="\'menu-icon \'+item.icon"></i>' +
					  '<i v-else class="menu-icon fa fa-caret-right"></i> {{item.name}}' +
				  '</a>',
	          '</li>'
	].join('')
});

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 120);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
	el:'#jeefastapp',
	data:{
		user:{},
		menuList:{},
		main:"main.html",
		password:'',
		newPassword:'',
        navTitle:"欢迎页"
	},
	methods: {
		getMenuList: function () {
			$.getJSON(baseURL + "sys/menu/nav", function(r){
				vm.menuList = r.menuList;
                window.permissions = r.permissions;
			});
		},
		getUser: function(){
			$.getJSON(baseURL + "sys/user/info", function(r){
				vm.user = r.user;
			});
		},
		profile: function(){
			/*$.getJSON(baseURL + "sys/user/info", function(r){
				vm.user = r.user;
			});*/
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				//offset: '50px',
				//skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['450px', '320px'],
				scrollbar: false,
				shade: 0,
                shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
					    url: baseURL + "sys/user/password",
					    data: data,
					    dataType: "json",
					    success: function(r){
							if(r.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(){
									location.reload();
								});
							}else{
								layer.alert(r.msg);
							}
						}
					});
	            }
			});
		},
        logout: function () {
			//删除本地token
            localStorage.removeItem("token");
            //跳转到登录页面
            location.href = baseURL + 'login.html';
        }
	},
	created: function(){
		this.getMenuList();
		this.getUser();
	},
    updated: function(){
        //路由
        var router = new Router();
        routerList(router, vm.menuList);
        router.start();
    }
});

function routerList(router, menuList){
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
			routerList(router, menu.list);
		}else if(menu.type == 1){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				
				//替换iframe的url
			    vm.main = url.replace('#', '');
			    
			    //导航菜单展开
			    $(".submenu li").removeClass("active");
                $(".nav-list li").removeClass("active");
			    $("a[href='"+url+"']").parents("li").addClass("active");

			    vm.navTitle = $("a[href='"+url+"']").text();
			});
		}
	}
}
