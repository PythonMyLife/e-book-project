<template>
    <div class="manage">
        <div id="templatemo_container">
            <div id="templatemo_menu">
                <ul>
                    <li><a href="/index">退出登录</a></li>
                    <li><router-link :to="{name:'manage',params:{username:this.username}}" class="current" >权限管理</router-link></li>
                    <li><router-link :to="{name:'bookmanage',params:{username:this.username}}" >书籍管理</router-link></li>
                    <li><router-link :to="{name:'order',params:{username:this.username}}" >订单管理</router-link></li>
                    <li><router-link :to="{name:'statistics',params:{username:this.username}}" >统计</router-link></li>
                </ul>
            </div> <!-- end of menu -->

            <template style="text-align: center">
                <el-table
                        :data="tableData"
                        style="width: 100%">
                    <el-table-column
                            prop="username"
                            label="用户名"
                            width="180">
                    </el-table-column>
                    <el-table-column label="账号状态" width="300">
                        <template slot-scope="scope">
                            <el-button type="text" size="small" style="color: crimson" v-if="scope.row.status === 1">已禁用</el-button>
                            <el-button type="text" size="small" style="color: blue" v-else>未禁用</el-button>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" property="status" align="center">
                        <template slot-scope="scope">
                            <el-button size="mini" @click="handleChange(scope.row.username)">更改状态</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </template>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: 'manage',
        data: function () {
            return {
                username:"",
                tableData: []
            }
        },
        mounted () {
            axios.get('http://localhost:8088/ebook/getUsers').then(response => {
                this.tableData = response.data;
            });
            this.username = this.$route.params.username;
            if(this.username == null){
                this.$alert("未登录请先登录");
                this.$router.push({name:"index",params:{}});
            }
        },
        methods: {
            handleChange(username){
                axios.post('http://localhost:8088/ebook/changeStatus',{"username":username}).then(response =>{
                    if(response.data === 0){
                        this.$alert("更改失败");
                    }else{
                        this.$alert("更改成功");
                    }
                    axios.get('http://localhost:8088/ebook/getUsers').then(response => {
                        this.tableData = response.data;
                    });
                })
            }
        },
    }
</script>
