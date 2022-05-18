<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 60px">
      <el-col :span="6">
        <el-card>
          <div style="color: #409EFF"><i class="el-icon-user-solid"/> Number of Users</div>
          <div  style="padding: 10px 0; text-align: center; font-weight: bold">
            10
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #F56C6C"><i class="el-icon-shopping-bag-2"/> Total Sales</div>
          <div  style="padding: 10px 0; text-align: center; font-weight: bold">
            $10,000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #67C23A"><i class="el-icon-money"/> Total Revenue</div>
          <div  style="padding: 10px 0; text-align: center; font-weight: bold">
            $3,000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #E6A23C"><i class="el-icon-s-shop"/> Number of Stores</div>
          <div  style="padding: 10px 0; text-align: center; font-weight: bold">
            3
          </div>
        </el-card>
      </el-col>
    </el-row>
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