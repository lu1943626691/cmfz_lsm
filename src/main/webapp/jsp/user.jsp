<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {
        var tb = [{
            text: '注册',
            iconCls: 'icon-add',
            handler: function () {

                $('#dd_user').dialog('open');
            }
        }, '-', {
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                var isSelect = $("#dg_banner").datagrid("getSelected")
                if (isSelect == null) {
                    $.messager.alert('提示', '请选择要修改的对象');
                    return;
                } else {
                    $("#updateDiv").dialog("open")
                    $('#updateForm').form('load', isSelect);

                }
            }
        }, '-', {
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                $('#dg_user').edatagrid('destroyRow');

            }
        }, '-', {
            text: '保存',
            iconCls: 'icon-save',
            handler: function () {
                $('#dg_user').edatagrid('saveRow');
            }
        }, '-', {
            text: '导出excl表 ',
            iconCls: 'icon-remove',
            handler: function () {
                /*$.ajax({
                    url:"
                ${pageContext.request.contextPath}/banner/selectExl",
                        success:function (data) {
                            if(data.isOk){
                                $.messager.alert('提示', '成功导出，请注意查看');
                            }else{
                                $.messager.alert('提示', '导出失败');
                            }
                    }
                    })*/

                location.href = "${pageContext.request.contextPath}/user/selectExl";


            }
        }]
        $('#updateDiv').dialog({
            title: '修改',
            width: 400,
            height: 200,
            closed: true,
            cache: false,
            modal: true
        });
        $("#dg_user").edatagrid({
            /*onDblClickRow:function(rowIndex,rowData){
                 //打开修改对话框，把要修改的内容写入到修改对话框中
                  toOpenUpdateDialog(rowData);
             },*/
            url: '${pageContext.request.contextPath}/user/selectAll',
            //保存
            saveUrl: '${pageContext.request.contextPath}/user/update',
            //删除
            updateUrl: '${pageContext.request.contextPath}/user/update',
            destroyUrl: '${pageContext.request.contextPath}/user/delete',
            fitColumns: true,
            fixed: true,
            idField: 'id',
            columns: [[
                {title: '编号', field: 'id', width: 60},
                {field: 'name', title: '用户名', width: 60},
                {
                    field: 'sex', title: '性别', width: 50,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return "男";
                        } else {
                            return "女";
                        }
                    }
                },
                {field: 'province', title: '省', width: 70},
                {field: 'city', title: '市', width: 60},
                {field: 'sign', title: '签名', width: 150},
                {
                    field: 'status', title: '状态', width: 80, editor: {
                        type: 'text',
                        options: {required: true}
                    }
                },
                {field: 'phone', title: '手机号', width: 150},
                {field: 'createDate', title: '注册时间', width: 150},

            ]],
            pagination: true,
            pageSize: 2,
            pageList: [2, 3, 4],
            toolbar: tb,
            //加减标识
            view: detailview,
            detailFormatter: function (rowIndex, rowData) {
                return '<table><tr>' +
                    '<td rowspan=5 style="border:10"><img src="${pageContext.request.contextPath}/' + rowData.headImg + '" style="height:50px;"></td>' +
                    '<td style="border:0">' +
                    '<p>名称: ' + rowData.name + '</p>' +
                    '<p>Status: ' + rowData.status + '</p>' +
                    '</td>' +
                    '</tr></table>';
            }

        });
        //$('#dd_banner').dialog('closed');
    })

    //添加
    function addUser() {
        $("#addForm").form("submit", {
            url: "${pageContext.request.contextPath}/user/insert",
            success: function (data) {
                data = JSON.parse(data)
                if (data.isAdd) {
                    $("#dg_user").edatagrid("load");
                    $('#dd_user').dialog('close');
                    $("#addForm").form("clear");
                } else {
                    $.messager.alert('提示', '添加失败，请仔细检查');
                }
            }
        })
    }

    function toOpenUpdateDialog(rowData) {
        $('#updateDiv').dialog("open")
        $("#name").val(rowData.title);
        $("#id").val(rowData.id);

        if (rowData.status == 'on') {
            $("#kai").prop("checked", true);
        } else {
            $("#guan").prop("checked", true);
        }
    }

    function updateBanner() {
        $('#updateForm').form("submit", {
            url: "${pageContext.request.contextPath}/banner/update",
            success: function (data) {
                data = JSON.parse(data);
                if (data.isUpdate) {
                    $("#dg_banner").edatagrid("load");
                    $('#updateDiv').dialog('close');
                } else {
                    alert("失败")
                }
            }
        });
    }
</script>

<table id="dg_user"></table>

<div id="dd_user" class="easyui-dialog" title="添加" collapsible=true, style="width:400px;height:280px;" data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addUser();
				}
			},{
				text:'关闭',
				handler:function(){
				   $('#dd_user').dialog('close');
				}
			}]">


    <form id="addForm" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">用户名:</label>
            <input id="name" type="text" name="name" data-options="required:true"/>
        </div>
        <div>
            <label for="password">密码:</label>
            <input id="password" type="password" name="password" data-options="required:true"/>
        </div>
        <div>
            性别<input type="radio" value="1" name="sex" id="man">男
            <input type="radio" value="0" name="sex" id="feman">女
        </div>
        <div>
            <label for="sign">签名:</label>
            <input id="sign" type="type" name="sign" data-options="required:true"/>
        </div>
        <div>
            <label for="province">省份:</label>
            <input id="province" type="text" name="province" data-options="required:true"/>
        </div>
        <div>
            <label for="city">城市:</label>
            <input id="city" type="text" name="city" data-options="required:true"/>
        </div>
        <div>
            <label for="phone">电话号码:</label>
            <input id="phone" type="text" name="phone" data-options="required:true"/>
        </div>
        <div>
            <label for="headImg">头像:</label>
            <input id="headImg" class="easyui-filebox" name="img" style="width:300px" data-options="buttonText:'选择头像'">
        </div>
    </form>
</div>

<div id="updateDiv" class="easyui-dialog" title="添加" collapsible=true, style="width:400px;height:200px;" data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    updateBanner();
				}
			},{
				text:'关闭',
				handler:function(){
				   $('#dd_banner').dialog('closed');
				}
			}]">
    <form id="updateForm" method="post" enctype="multipart/form-data">
        <input type="hidden" id="id" name="id"/>
        名称<input name="title" id="name2"></br>

        <div>
            <label for="path">路径:</label>
            <input id="path" type="file" name="file" style="width:300px">
        </div>

        状态<input type="radio" value="on" name="status" id="kai">开
        <input type="radio" value="off" name="status" id="guan">关
    </form>
</div>
