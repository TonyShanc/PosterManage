<template>
  <el-table
    :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
    style="width: 1150px;margin: 0 auto">
    <el-table-column
      label="海报"
      prop="poster"
      width="110">
    </el-table-column>
    <el-table-column
      label="海报名"
      prop="name"
      width="120">
    </el-table-column>
    <el-table-column
      label="导演"
      prop="director"
      width="110">
    </el-table-column>
    <el-table-column
    label="主演"
    prop="actors"
    width="170">
  </el-table-column>
    <el-table-column
    label="类型"
    prop="type"
    width="160">
  </el-table-column>
    <el-table-column
      label="所在地"
      prop="location"
      width="120">
    </el-table-column>
    <el-table-column
      label="上映年份"
      prop="year"
      width="120">
    </el-table-column>
    <el-table-column
      align="right">
      <template slot="header" slot-scope="scope" style="width: 100px">
        <div><el-button size="small" type="primary">新增</el-button></div>
          <el-input
            style="width:63%;"
            size="small"
            v-model="search"
            placeholder="查询关键字"/>
      </template>
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    name:"m_users",
    data() {
      return {
        tableData: [{
          poster:'一张图片',
          name:'我是坏蛋',
          director:'黄渤',
          location:'中国大陆',
          year:'2017',
          type:'冒险,爱情',
          actors:'黄渤,张一山,神算子',
        },{
          poster:'一张图',
          name:'我是坏蛋',
          director:'黄渤',
          location:'中国大陆',
          year:'2017',
          type:'冒险,爱情',
          actors:'黄渤,张一山',
        },
          {
            poster:'一张片',
            name:'我是坏蛋',
            director:'黄渤',
            location:'中国大陆',
            year:'2017',
            type:'冒险,爱情',
            actors:'黄渤,张一山',
          },
          {
            poster:'图片',
            name:'我是坏蛋',
            director:'黄渤',
            location:'中国大陆',
            year:'2017',
            type:'冒险,爱情',
            actors:'黄渤,张一山',
          },
          {
            poster:'一图片',
            name:'我是坏蛋',
            director:'黄渤',
            location:'中国大陆',
            year:'2017',
            type:'冒险,爱情',
            actors:'黄渤,张一山',
          },],
        search: ''
      }
    },
    methods: {
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      }
    },
  }
</script>
