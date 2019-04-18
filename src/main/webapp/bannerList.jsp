<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>持名法州后台管理中心</title>

    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <link rel="icon" href="img/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="css/common.css" type="text/css"></link>
    <link rel="stylesheet" href="css/login.css" type="text/css"></link>

    <script type="text/javascript" src="script/jquery.js"></script>
    <script type="text/javascript" src="script/common.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#dg_banner").edatagrid({
                url: "banner/selectAll",
                idField: 'id',
                treeField: 'name',
                columns: [[
                    {title: '编号', field: 'id', width: 180},
                    {field: 'title', title: '图片名称', width: 60},
                    {field: 'img_path', title: '路径', width: 80},
                    {field: 'create_date', title: '创建时间', width: 80},
                    {field: 'status', title: '状态', width: 80}
                ]]
            })
        })
    </script>
</head>
<body>
<table id="dg_banner"></table>
</body>
</html>