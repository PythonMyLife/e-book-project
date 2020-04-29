<template>
    <div class="index">
        <div id="templatemo_container">
            <div id="templatemo_menu">
                <ul>
                    <li><a href="/index">退出登录</a></li>
                    <li><router-link :to="{name:'books',params:{username:this.username}}" >书籍浏览</router-link></li>
                    <li><router-link :to="{name:'cart',params:{username:this.username}}" >购物车</router-link></li>
                    <li><router-link :to="{name:'userorder',params:{username:this.username}}" >订单与统计</router-link></li>
                    <li><router-link :to="{name:'talk',params:{username:this.username}}" >聊天室</router-link></li>
                    <li><a href="#" class="current">好友</a></li>
                </ul>
            </div> <!-- end of menu -->

            <el-main style="width: 900px; margin: 0 auto;">
                <h2>好友列表:</h2>
                <div id="templatemo_content1" >
                    <el-card class="box-card" style="text-align: center;">
                        <div v-if="table.length == 0">
                            您现在孤身一人！
                        </div>
                        <el-row v-for="(item, index) in table" :key="index" style="padding: 10px 0; border-bottom: 1px solid #eff2f6">
                            <el-col :span="8" style="line-height: 103.8px">
                                {{ item }}
                            </el-col>
                            <el-col :span="16" style="line-height: 104px">
                                <el-button  circle @click="handleDelete(item, index)">删除好友</el-button>
                            </el-col>
                        </el-row>
                    </el-card>
                </div>

                <h2>添加好友:</h2>
                <el-row>
                    <el-col :span="20"><el-input  v-model="search" placeholder="输入关键字搜索"/></el-col>
                    <el-col :span="4"><el-button @click="handleFind()">查找用户</el-button></el-col>
                </el-row>
                <div id="templatemo_content2" >
                    <el-card class="box-card" style="text-align: center;">
                        <div v-if="spanArr.length == 0">
                            未搜索到用户！
                        </div>
                        <el-row v-for="(item, index) in spanArr" :key="index" style="padding: 10px 0; border-bottom: 1px solid #eff2f6">
                            <el-col :span="8" style="line-height: 103.8px">
                                {{ item }}
                            </el-col>
                            <el-col :span="16" style="line-height: 104px">
                                <el-button  circle @click="handleAdd(item)">添加好友</el-button>
                            </el-col>
                        </el-row>
                    </el-card>
                </div>
            </el-main>
            <div class="cleaner_with_height">&nbsp;</div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: 'friend',
        data: function () {
            return {
                username: '',
                table: [],
                spanArr: [],
                search: ''
            };
        },
        mounted () {
            this.username = this.$route.params.username;
            if (this.username === '') {
                alert("请登录后查看好友！");
                this.$router.push({name: "index"});
            }
            if(this.username == null){
                this.$alert("未登录请先登录");
                this.$router.push({name:"index",params:{}});
            }
            axios
                .get('http://localhost:4333/ebook/getFriendList', {params:{name:this.username}})
                .then(response => {
                    this.table = response.data;
                })
        },
        methods:{
            handleDelete(name, index) {
                this.table.splice(index, 1);
                axios.get('http://localhost:4333/ebook/deleteFriend', {params:{name:this.username, friend:name}}).then(
                    this.$alert("删除好友成功！")
                )
            },
            handleAdd(name) {
                axios.get('http://localhost:4333/ebook/addFriend', {params:{name:this.username, friend:name}})
                .then(response => {
                    axios.get('http://localhost:4333/ebook/getFriendList', {params:{name:this.username}})
                        .then(response => {
                            this.table = response.data;
                            this.$alert("添加好友成功！");
                        })
                    }
                )

            },
            handleFind() {
                axios
                    .get('http://localhost:4333/ebook/findUser', {params:{name:this.search}})
                    .then(response => {
                        this.spanArr = response.data;
                        for(var i = 0; i < this.spanArr.length; i++) {
                            if(this.spanArr[i] === this.username) {
                                this.spanArr.splice(i, 1);
                                i--;
                            }
                        }
                    });
            }
        }
    };
</script>

<style scoped>

</style>
