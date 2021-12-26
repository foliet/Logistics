<template>
<div id="main" style="height: 100%;width: 100%"></div>
</template>

<script>
export default {
  mounted() {
    document.title="站点统计"
    this.$axios.get("https://mc.rainspace.cn:4443/admin/count").then(res=>{
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
            stack: 'Total',
            data: res.data.userIncrease
          },
          {
            name: '新增订单数',
            type: 'line',
            stack: 'Total',
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

</style>