<template>
  <span id="main" class="picture"></span>
  <span id="number">
    <el-card :body-style="{ padding: '0'}" shadow="hover">
      <template #header>
        <span>总计</span>
      </template>
           <el-menu >
            <el-menu-item >
              <template #title>
                <el-icon style="color: #FFB500">
                  <User/>
                </el-icon>&nbsp;
                <span>用户总数:</span>
                <span style="margin-left: 60%">1人</span>
              </template>
            </el-menu-item>
            <el-menu-item >
              <template #title>
                <el-icon style="color: #FF3D00">
                  <Document/>
                </el-icon>&nbsp;
                <span>订单总数:</span>
                <span style="margin-left: 60%">1个</span>
              </template>
            </el-menu-item>
          </el-menu>
    </el-card>
      </span>
</template>

<script>
import {Document,User} from "@element-plus/icons";

export default {
  components: {
    User,
    Document
  },
  mounted() {
    document.title = "站点统计"
    this.$axios.get("https://mc.rainspace.cn:4443/admin/count").then(res => {
      const chart = this.$echarts.init(document.getElementById("main"))
      const option = {
        title: {
          text: '趋势'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['新增用户数', '新增订单数']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: res.data.dates
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '新增用户数',
            type: 'line',
            data: res.data.userIncrease
          },
          {
            name: '新增订单数',
            type: 'line',
            data: res.data.orderIncrease
          },
        ]
      };
      chart.setOption(option)
    })
  },
}
</script>

<style scoped>
.picture{
  height: 70%;
  width: 60%;
  margin-top: 1%;
  margin-left: 1%;
  margin-right: 0.5%;
  border: #E5E5E5 2px solid;
  border-radius: 15px;
}
#number{
  margin-top: 1%;
  width: 23%;
  z-index: -1;
}
</style>