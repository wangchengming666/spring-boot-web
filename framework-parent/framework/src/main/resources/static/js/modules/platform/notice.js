$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'platform/notice/list',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', index: "id", width: 15, key: true },
			{ label: '标题', name: 'title', width: 75 },
            { label: '类型', name: 'type', width: 25, formatter: function(value, options, row){
				return value === 0 ? 
						'<span class="label label-danger">平台通知</span>' : 
						'<span class="label label-success">业务通知</span>';
			}},
			{ label: '内容', name: 'content', width: 130 },
			{ label: '发布时间', name: 'publishTime', width: 53 },
			{ label: '创建人', name: 'createrName', width: 30 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
        	root: "page.records",
            page: "page.current",
            total: "page.pages",
            records: "page.total"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#jeefastapp',
	data:{
		q:{
			title: null
		},
		showList: true,
		title: null,
		notice:{
			type: 1
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.notice = {type:1};
		},
		update: function () {
			var noticeId = getSelectedRow();
			if(noticeId == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改";
			
			vm.getNotice(noticeId);
		},
		del: function () {
			var noticeIds = getSelectedRows();
			if(noticeIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "platform/notice/delete",
                    contentType: "application/json",
				    data: JSON.stringify(noticeIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(){
                                vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function () {
			var url = vm.notice.id == null ? "platform/notice/save" : "platform/notice/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.notice),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		getNotice: function(noticeId){
			$.get(baseURL + "platform/notice/info/"+noticeId, function(r){
				vm.notice = r.notice;
			});
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'title': vm.q.title},
                page:page
            }).trigger("reloadGrid");
		}
	}
});