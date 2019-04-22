<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/echarts.min.js"></script>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="statistics_main" style="width: 600px;height: 400px;;margin-top: 30px;margin-left: 30px"></div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('statistics_main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '持名法州App活跃用户'
        },
        tooltip: {},
        legend: {
            data: ['用户数量']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '数量',
            type: 'bar',
            data: []
        }]
    };

    myChart.setOption(option);

    /*
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("intervals",new String[]{"7天","15天"});
    map.put("counts",new int[]{5,10});'
    return map;

    [{"intervals":["7天","15天"]},{}]


    */
    // 异步加载统计信息
    $.post("${pageContext.request.contextPath }/user/activeUser", function (data) {
        console.log(data);
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption({
            xAxis: {
                data: data.intervals
            },
            series: [{
                // 根据名字对应到相应的系列
                name: '活跃用户',
                data: data.counts
            }]
        });
    }, "json");


</script>
