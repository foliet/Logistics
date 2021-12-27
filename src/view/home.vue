<template>
  <el-container>
    <el-main>
      <div v-for="notice in notices.slice((currentPage-1)*pageSize,currentPage*pageSize)" :key="notice.id">
        <el-card shadow="hover">
          <div id="msgTitle">{{ notice.title }}</div>
          <el-divider></el-divider>
          <div style="font-size: 13px;color: #666666;">
            {{ notice.createAt.slice(0, 10) + ' ' + notice.createAt.slice(11, 19) }}
          </div>
          <br/>
          <div style="font-size: 15px">{{ notice.content }}</div>
        </el-card>
        <br/>
      </div>
      <el-empty :style="{display: notices.length>0?'none':'flex'}" description="暂无消息"></el-empty>
    </el-main>
    <el-footer>
      <div style="padding-left: 35%">
        <el-pagination :current-page="currentPage" :page-size="pageSize" :total="notices.length" background
                       layout="prev, pager, next, jumper" style="width: 40%;float: left"
                       @current-change="currentChange">
        </el-pagination>
      </div>
    </el-footer>
  </el-container>
</template>

<script>
export default {
  title: "user",
  data() {
    return {
      currentPage: 1,
      pageSize: 4,
      notices: [],
    }
  },
  created() {
    this.$axios.get("/get-notices").then(res => {
      if (res.data.status < 10) this.notices = res.data.notices.sort(function (a, b) {
        return b.id - a.id
      })
    })
  },
  mounted() {
    document.title = "我的消息"
  },
  methods: {
    currentChange(index) {
      this.currentPage = index
    }
  }
}
</script>

<style scoped>
#msgTitle {
  font-size: 20px;
  letter-spacing: 0.02em;
  font-weight: bold;
}
</style>