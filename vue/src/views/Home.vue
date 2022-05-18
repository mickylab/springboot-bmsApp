<template>
  <div>
    <h1>Welcome to Homepage!</h1>
    <el-row>
      <el-col :span="12">
        <div id="line" style="width: 500px; height: 400px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="width: 500px; height: 400px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data() {
    return {

    }
  },
  mounted() { // 页面元素渲染之后再触发
    let lineChartDom = document.getElementById('line');
    let lineChart = echarts.init(lineChartDom);

    // line option
    let lineOption = {
      title: {
        text: 'Member Quantity',
        subtext: 'by Quarter',
        left: 'center'
      },
      xAxis: {
        type: 'category',
        data: ['Q1', 'Q2', 'Q3', "Q4"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };

    let pieChartDom = document.getElementById('pie');
    let pieChart = echarts.init(pieChartDom);

    // pie option
    let pieOption = {
      title: {
        text: 'Member Quantity',
        subtext: 'by Quarter',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '65%',
          label: {
            normal: {
              show: true,
              position: 'inner',
              textStyle: {
                fontWeight: 300,
                fontSize: 12,
                color: '#FFF'
              },
              formatter: '{d}%'
            }
          },
          data: [
            { value: 1048, name: 'Search Engine' },
            { value: 735, name: 'Direct' },
            { value: 580, name: 'Email' },
            { value: 484, name: 'Union Ads' },
            { value: 300, name: 'Video Ads' }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };

    this.request.get("/echarts/members").then(res => {
      lineOption.series[0].data = res.data;
      lineOption.series[1].data = res.data;
      lineChart.setOption(lineOption);
      pieOption.series[0].data = [
        {name: 'Q1', value: res.data[0]},
        {name: 'Q2', value: res.data[1]},
        {name: 'Q3', value: res.data[2]},
        {name: 'Q4', value: res.data[3]}
      ];
      pieChart.setOption(pieOption);
    })
  }
}
</script>

<style scoped>

</style>