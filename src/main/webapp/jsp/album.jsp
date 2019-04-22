<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    /* var value=''*/
    $(function () {

        var tb = [{
            text: '专辑详情',
            iconCls: 'icon-edit',
            handler: function () {
                var rowDate = $("#tt_album").datagrid("getSelected");
                if (rowDate == null) {
                    $.messager.alert('提示', '请选择一个章节或者专辑');
                    return;
                } else if (rowDate.albumId == null) {
                    $("#dd_album").dialog("open")
                    $('#dd_album').form('load', rowDate);
                    $("#pic").prop("src", "${pageContext.request.contextPath}" + rowDate.imgPath)
                } else {
                    $.messager.alert('提示', '请选择一个专辑');
                    return;
                }
                /*else if(rowDate.albumId==null){
                    //alert("这是专辑")
                    //
                   $.ajax({
                       url:'
                ${pageContext.request.contextPath}/album/selectOne?id='+rowDate.id,
                       success:function(data){
                           //console.log(data)
                          /!* var data = JSON.stringify(data)*!/
                           toOpenDetailDialog(data)
                       }
                   })
                }else{
                    alert("这是章节")
                    $.ajax({
                        url:'
                ${pageContext.request.contextPath}/album/selectOne?id='+rowDate.albumId,
                        success:function(data){


                            toOpenDetailDialog(data)
                        }
                    })
                }*/

                //  $('#dd_banner').dialog('open');
            }
        }, '-', {
            text: '添加专辑',
            iconCls: 'icon-add',
            handler: function () {
                $('#add_album').dialog('open');
            }
        }, '-', {
            text: '添加章节',
            iconCls: 'icon-remove',
            handler: function () {
                $('#add_chapter').dialog('open');
                $.ajax({
                    url: "${pageContext.request.contextPath}/album/selectAlbum",
                    dataType: "json",
                    success: function (data) {
                        var html = ''
                        $.each(data, function (index1, first) {
                            //html=  "  <option value =\"+first.id+\">first.title</option>\n"
                            html += "<option value='" + first.id + "'>" + first.title + "</option>";
                        })
                        $("#albumId").append(html);

                    }
                })

                // $('#add_chapter').dialog('open');

            }
        }, '-', {
            text: '章节下载,                                                                                                        ',
            iconCls: 'icon-save',
            handler: function () {
                var rowDate = $("#tt_album").datagrid("getSelected");
                if (rowDate == null) {
                    $.messager.alert('提示', '请选择要下载的章节');
                    return;
                } else if (rowDate.albumId == null) {
                    $.messager.alert('提示', '请选择章节');
                    return;
                } else {
                    location.href = "${pageContext.request.contextPath}/chapter/downLoad?title=" + rowDate.title + "&chapterUrl=" + rowDate.chapterUrl;
                }

            }

        }, '-', {
            text: '在线播放',
            iconCls: 'icon-add',
            handler: function () {
                var rowDate = $("#tt_album").datagrid("getSelected");
                if (rowDate == null) {
                    $.messager.alert('提示', '选择要播放的章节');
                    return;
                } else if (rowDate.albumId == null) {
                    $.messager.alert('提示', '章节才能播放哦');
                    return;
                } else {

                    $("#radio").prop("src", "${pageContext.request.contextPath}" + rowDate.chapterUrl)
                    $("#music").dialog("open")

                }
                /*else {
                    location.href = "${pageContext.request.contextPath}/chapter/play?title=" + rowDate.title + "&chapterUrl=" + rowDate.chapterUrl;
                }*/
            }
        }, '-', {
            text: '导出exl表',
            iconCls: 'icon-add',
            handler: function () {
                location.href = "${pageContext.request.contextPath}/album/selectExl";
            }
        }]

        $('#tt_album').treegrid({
            //后台Controller查询所有专辑以及对应的章节集合
            url: '${pageContext.request.contextPath}/album/select',
            idField: 'id',//用来标识标识树节点   主干树与分支树节点  ID不能有相同  并且使用相同的字段  否则ID冲突
            treeField: 'title',//用来定义树节点   树形表格上要展示的信息   注意使用相同的字段 用来展示对应节点名称
            toolbar: tb,
            fit: true,
            fitColumns: true,
            columns: [[
                {field: 'title', title: '名字', width: 180},
                {field: 'size', title: '章节大小', width: 60},
                {field: 'duration', title: '章节的时长', width: 80}
            ]]
        })
        //详情对话框
        $('#dd_album').dialog({
            title: '专辑详情',
            width: 400,
            height: 300,
            closed: true,
            cache: false,
            modal: true
        });

    })
    //打开详情对话框
    /*
     function  toOpenDetailDialog(data){
         $('#dd_album').dialog("open");
         $("#title1").val(data.album.title);
         $("#amount").val(data.album.amount);
         $("#score1").val(data.album.score);
         $("#author1").val(data.album.author);
         $("#boardCast1").val(data.album.boardCast);
         $("#publishDate").val(data.album.publishDate);
         $("#brief1").val(data.album.brief);
         //$("#pic").prop("src",data.album.imgPath);
     }*/
    //打开详情对话框

    //添加专辑
    function addAlbum() {
        $("#ff_album").form("submit", {
            url: "${pageContext.request.contextPath}/album/insert",
            success: function (data) {
                data = JSON.parse(data)
                if (data.isAdd) {
                    $("#tt_album").treegrid("load");
                    $('#add_album').dialog('close');
                    /*$("#title").val("");
                    $("#score").val("");
                    $("#author").val("");
                    $("#boardcast").val("");
                    $("#file").prompt("")*/
                    $("#ff_album").form("clear")
                } else {
                    $.messager.alert('提示', '添加失败，请仔细检查');
                }
            }
        })
    }

    //添加章节
    function addChapter() {
        $("#ff_chapter").form("submit", {
            url: "${pageContext.request.contextPath}/chapter/insert",
            success: function (data) {
                data = JSON.parse(data)
                if (data.isInsert) {
                    $("#tt_album").treegrid("load");
                    $('#add_chapter').dialog('close');
                    /* $("#name").val(""),
                     $("#file1").prompt("")*/
                    $("#ff_chapter").form("clear")
                } else {
                    $.messager.alert('提示', '添加失败，请仔细检查');
                }
            }
        })
    }


</script>

<table id="tt_album" style="width:600px;height:400px"></table>
<!--详情-->
<div id="dd_album" class="easyui-dialog" title="添加" collapsible=true, style="width:400px;height:200px;" data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'关闭',
				handler:function(){
				   $('#dd_album').dialog('close');
				}
			}]">
    <form id="aa" method="post">
        <input type="hidden" id="id" name="pid"/>
        专辑名称<input name="title" id="title1"></br>
        集数<input name="amount" id="amount"></br>
        评分<input name="score" id="score1"></br>
        作者<input name="author" id="author1"></br>
        播音<input name="boardCast" id="boardCast1"></br>
        出版时间<input name="publishDate" id="publishDate"></br>
        描述<input name="brief" id="brief1"></br>
        图片<img src="" id="pic" width="50px" height="50px">
    </form>
</div>
<!--详情-->


<%--添加专辑--%>
<div id="add_album" class="easyui-dialog" title="添加" collapsible=true, style="width:400px;height:200px;" data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				     addAlbum();
				}
			},{
				text:'关闭',
				handler:function(){
				   $('#add_album').dialog('close');
				}
			}]">


    <form id="ff_album" method="post" enctype="multipart/form-data">
        <div>
            <label for="title">名称:</label>
            <input id="title" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="file">图片:</label>
            <input id="file" class="easyui-filebox" name="file" style="width:300px">
        </div>
        <div>
            <label for="score">星级:</label>
            <input id="score" type="text" name="score" data-options="required:true"/>
        </div>
        <div>
            <label for="author">作者:</label>
            <input id="author" type="text" name="author" data-options="required:true"/>
        </div>
        <div>
            <label for="boardcast">CV:</label>
            <input id="boardcast" type="text" name="boardCast" data-options="required:true"/>
        </div>
        <div>
            <label for="brief">详情:</label>
            <input id="brief" type="text" name="brief" data-options="required:true"/>
        </div>
    </form>
</div>
<!--添加专辑-->

<!--添加章节-->
<div id="add_chapter" class="easyui-dialog" title="添加" collapsible=true, style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				     addChapter();
				}
			},{
				text:'关闭',
				handler:function(){
				   $('#add_chapter').dialog('close');
				}
			}]">


    <form id="ff_chapter" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">章节名称:</label>
            <input id="name" type="text" name="title" data-options="required:true"/>
        </div>


        <div>
            <label for="file1">音频:</label>
            <input id="file1" class="easyui-filebox" name="audio" style="width:300px">
        </div>
        <div>
            <label for="albumId">所属专辑:</label>
            <select id="albumId" name="albumId"></select>
        </div>
    </form>
</div>
<!--添加章节-->

<div id="music" class="easyui-dialog" data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,">

    <audio src="" controls="controls" type="audio/wav" autoplay="autoplay" width="30px" id="radio"></audio>

</div>


