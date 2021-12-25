<template>
<div id="main" style="height: 100%;width: 100%"></div>
</template>

<script>
export default {
  mounted() {
    document.title="站点统计"
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
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: '新增用户数',
          type: 'line',
          stack: 'Total',
          data: [120, 132, 101, 134, 90, 230, 210]
        },
        {
          name: '新增订单数',
          type: 'line',
          stack: 'Total',
          data: [220, 182, 191, 234, 290, 330, 310]
        },
      ]
    };
    chart.setOption(option)
  },
}
</script>

<style scoped>

</style>