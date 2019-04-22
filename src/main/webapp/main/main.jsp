<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>持名法州主页</title>
    <link rel="stylesheet" type="text/css" href="../themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../themes/IconExtension.css">
    <link rel="stylesheet" type="text/css" href="../themes/icon.css">
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/datagrid-detailview.js"></script>
    <script type="text/javascript" src="../js/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        <!--菜单处理-->
        $(function () {
            $.ajax({
                url: '${pageContext.request.contextPath}/menu/selectAll',
                //javaType:"json",
                success: function (data) {
                    console.log(data)
                    /* each底层是foreach，第一个参数是要遍历的对象，
                    第二个是函数(第一个参数是下标，第二参数是遍历的对象名)*/
                    $.each(data, function (index1, first) {
                        //二层遍历，遍历list集合
                        var c = "<div align='center'>";
                        $.each(first.list, function (index2, second) {
                            var options = "data-options=" + "iconCls:" + second.icon_cls
                            // alert(options)

                            var child = JSON.stringify(second)
                            c += "<p><a class='easyui-linkbutton' onclick='addTabs(" + child + ")'>" + second.title + "</a></p>";

                        })
                        c += "</div>";
                        $('#aa').accordion('add', {
                            title: first.title,
                            iconCls: first.icon_cls,
                            content: c,
                            selected: false
                        });
                    })
                }
            });

        })

        function addTabs(second) {
            //判断是否存在
            var isExsits = $("#myTabsDiv").tabs("exists", second.title);
            //如果不存在，就添加
            if (!isExsits) {
                $('#myTabsDiv').tabs("add", {
                    title: second.title,
                    closable: true,
                    selected: true,
                    href: '${pageContext.request.contextPath}' + second.jsp_url,
                });
            } else {
                //存在就选中
                $('#myTabsDiv').tabs('select', second.title)
            }

        }

        //添加选项卡
        /* function toAddTabsForBook(node){
             var isExists = $('#myTabsDiv').tabs("exists",node.text);
             if(isExists){
                 $('#myTabsDiv').tabs("select",node.text);
             }else{
                 $("#myTabsDiv").tabs("add",{
                     title:node.text,
                     selected:true,
                     closable:true,
                     content:'<iframe src="index.jsp?id='+node.id+'" width="100%"></iframe>'
                     // content:'<iframe src="index.jsp?id='+node.id+'" width="100%" height="100%"></iframe>'
                 })
             }
         }*/
        /*function toAddBannerTabs(){
            var isExists = $("#myTabsDiv").tabs("exists","轮播图");
            if(isExists){
                $("#myTabsDiv").tabs("select","轮播图");
            }else{
                $("#myTabsDiv").tabs("add",{
                    title:"轮播图",
                    //在设置为true的时候，选项卡面板将显示一个关闭按钮，在点击的时候会关闭选项卡面板。
                    closable:true,
                    //在设置为true的时候，选项卡面板会被选中。
                    selected:true,
                    content:"轮播图页面"
                })
            }

        }*/
    </script>

</head>
<body class="easyui-layout">
<div data-options="region:'north',split:true" style="height:60px;background-color:  #5C160C">
    <div style="font-size: 24px;color: #FAF7F7;font-family: 楷体;font-weight: 900;width: 500px;float:left;padding-left: 20px;padding-top: 10px">
        持名法州后台管理系统
    </div>
    <div style="font-size: 16px;color: #FAF7F7;font-family: 楷体;width: 300px;float:right;padding-top:15px">欢迎您:xxxxx
        &nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改密码</a>&nbsp;&nbsp;<a href="#"
                                                                                                              class="easyui-linkbutton"
                                                                                                              data-options="iconCls:'icon-01'">退出系统</a>
    </div>
</div>
<div data-options="region:'south',split:true" style="height: 40px;background: #5C160C">
    <div style="text-align: center;font-size:15px; color: #FAF7F7;font-family: 楷体">&copy;百知教育 htf@zparkhr.com.cn</div>
</div>

<div data-options="region:'west',title:'导航菜单',split:true" style="width:220px;">
    <div id="aa" class="easyui-accordion" data-options="fit:true">
        <!--轮播图-->
        <%--<c:forEach var="menu" items="${requestScope.list}">
            <c:if test="${menu.parent_id==null}">
        <div title="${menu.title}" data-options="iconCls:'${menu.icon_cls}'" onclick="toAddTabs()" style="overflow:auto;padding:10px;">
            <c:forEach var="m" items="${menu.list}">
             <ul>${m.title}</ul>
            </c:forEach>
        </div>
          </c:if>
        </c:forEach>--%>
        <%--<!--吉祥妙音-->
        <div title="吉祥妙音" data-options="iconCls:'icon-neighbourhood'" style="overflow:auto;padding:10px;">
        </div>
        <!--甘露妙宝-->
        <div title="甘露妙宝" data-options="iconCls:'icon-neighbourhood'" style="overflow:auto;padding:10px;">
        </div>
        <!--功课记录-->
        <div title="功课记录" data-options="iconCls:'icon-neighbourhood'" style="overflow:auto;padding:10px;">
        </div>
        <!--用户模块-->
        <div title="用户模块" data-options="iconCls:'icon-neighbourhood'" style="overflow:auto;padding:10px;">
        </div>--%>
    </div>
</div>
<div data-options="region:'center'">
    <div id="myTabsDiv" class="easyui-tabs" data-options="fit:true,narrow:true,pill:true">
        <div title="主页" data-options="iconCls:'icon-neighbourhood'"
             style="background-image:url(${pageContext.request.contextPath}/main/image/shouye.jpg);background-repeat: no-repeat;background-size:100% 100%;"></div>
    </div>
</div>
</body>
</html>