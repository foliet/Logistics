<template>
  <span id="main" class="picture"></span>
  <span id="number">
    <el-card :body-style="{ padding: '0'}" shadow="hover" style="border: #E5E5E5 2px solid;">
      <template #header>
        <span style="font-size: 18px;letter-spacing: 0.02em;font-weight: bold;">
          总计
        </span>
      </template>
           <el-menu >
            <el-menu-item style="margin-top: 1.5em">
              <template #title>
                <el-icon style="color: #FFB500;font-size: 20px">
                  <User/>
                </el-icon>&nbsp;
                <span>用户总数:</span>
                <span style="margin-left: 60%">{{userNumber}}</span>
              </template>
            </el-menu-item>
            <el-menu-item style="margin-top: 0.7em">
              <template #title>
                <el-icon style="color: #FF3D00;font-size: 20px">
                  <Document/>
                </el-icon>&nbsp;
                <span>订单总数:</span>
                <span style="margin-left: 60%">{{orderNumber}}</span>
              </template>
            </el-menu-item>
             <el-menu-item style="margin-top: 0.7em">
              <template #title>
                <el-icon style="color: #03A9F4;font-size: 20px">
                  <UserFilled/>
                </el-icon>&nbsp;
                <span >今日新增用户:</span>
                <span style="margin-left: 49%">{{ userIncrease[6] }}</span>
              </template>
            </el-menu-item>
             <el-menu-item style="margin-top: 0.7em;margin-bottom: 1.8em">
              <template #title>
                <el-icon style="color: #00BF96;font-size: 20px">
                  <DocumentAdd />
                </el-icon>&nbsp;
                <span >今日新增订单:</span>
                <span style="margin-left: 49%">{{ orderIncrease[6] }}</span>
              </template>
            </el-menu-item>
          </el-menu>
    </el-card>
      </span>
</template>

<script>
import {Document,User,UserFilled,DocumentAdd} from "@element-plus/icons";

export default {
  components: {
    User,
    Document,
    UserFilled,
    DocumentAdd
  },
  data(){
    return{
      userNumber:0,
      orderNumber:0,
      userIncrease:[],
      orderIncrease:[],
    }
  },
  mounted() {
    document.title = "站点统计"
    this.$axios.get("https://mc.rainspace.cn:4443/admin/count").then(res => {
      this.orderNumber=res.data.orderNumber
      this.orderIncrease=res.data.orderIncrease
      this.userNumber=res.data.userNumber
      this.userIncrease=res.data.userIncrease
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