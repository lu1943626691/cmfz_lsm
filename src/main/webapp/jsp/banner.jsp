<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {
        var tb = [{
            text: '添加',
            iconCls: 'icon-add',
            handler: function () {

                $('#dd_banner').dialog('open');
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
                $('#dg_banner').edatagrid('destroyRow');

            }
        }, '-', {
            text: '保存',
            iconCls: 'icon-save',
            handler: function () {
                $('#dg_banner').edatagrid('saveRow');
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
        $("#dg_banner").edatagrid({
            /*onDblClickRow:function(rowIndex,rowData){
                 //打开修改对话框，把要修改的内容写入到修改对话框中
                  toOpenUpdateDialog(rowData);
             },*/
            url: '${pageContext.request.contextPath}/banner/selectAll',
            //保存
            saveUrl: '${pageContext.request.contextPath}/banner/update',
            //删除
            updateUrl: '${pageContext.request.contextPath}/banner/update',
            destroyUrl: '${pageContext.request.contextPath}/banner/delete',
            fitColumns: true,
            idField: 'id',
            columns: [[
                {title: '编号', field: 'id', width: 60},
                {field: 'title', title: '图片名称', width: 60},
                {field: 'img_path', title: '路径', width: 80},
                {field: 'create_date', title: '创建时间', width: 150},
                {
                    field: 'status', title: '状态', width: 80, editor: {
                        type: 'text',
                        options: {required: true}
                    }
                }

            ]],
            pagination: true,
            pageSize: 3,
            pageList: [3, 6, 9],
            toolbar: tb,
            //加减标识
            view: detailview,
            detailFormatter: function (rowIndex, rowData) {
                return '<table><tr>' +
                    '<td rowspan=5 style="border:10"><img src="${pageContext.request.contextPath}/' + rowData.img_path + '" style="height:50px;"></td>' +
                    '<td style="border:0">' +
                    '<p>名称: ' + rowData.title + '</p>' +
                    '<p>Status: ' + rowData.status + '</p>' +
                    '</td>' +
                    '</tr></table>';
            }

        });
        //$('#dd_banner').dialog('closed');
    })

    //添加
    function addBanner() {
        $("#addForm").form("submit", {
            url: "${pageContext.request.contextPath}/banner/insert",
            success: function (data) {
                data = JSON.parse(data)
                if (data.isAdd) {
                    $("#dg_banner").edatagrid("load");
                    $('#dd_banner').dialog('close');
                    $("#title").val("");
                    $("#img_path").val("")
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

<table id="dg_banner"></table>

<div id="dd_banner" class="easyui-dialog" title="添加" collapsible=true, style="width:400px;height:200px;" data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addBanner();
				}
			},{
				text:'关闭',
				handler:function(){
				   $('#dd_banner').dialog('closed');
				}
			}]">


    <form id="addForm" method="post" enctype="multipart/form-data">
        <div>
            <label for="title">名称:</label>
            <input id="title" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="img_path">路径:</label>
            <input id="img_path" class="easyui-filebox" name="file1" style="width:300px">
        </div>
        状态<input type="radio" value="on" name="status">开
        <input type="radio" value="off" name="status">关
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
        名称<input name="title" id="name"></br>

        <div>
            <label for="path">路径:</label>
            <input id="path" type="file" name="file" style="width:300px">
        </div>

        状态<input type="radio" value="on" name="status" id="kai">开
        <input type="radio" value="off" name="status" id="guan">关
    </form>

</div>
