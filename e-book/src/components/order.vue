<template>
    <div class="index">
        <div id="templatemo_container">
            <div id="templatemo_menu">
                <ul>
                    <li><a href="/index">退出登录</a></li>
                    <li><router-link :to="{name:'manage',params:{username:this.username}}" >权限管理</router-link></li>
                    <li><router-link :to="{name:'bookmanage',params:{username:this.username}}" >书籍管理</router-link></li>
                    <li><router-link :to="{name:'order',params:{username:this.username}}" class="current" >订单管理</router-link></li>
                    <li><router-link :to="{name:'statistics',params:{username:this.username}}" >统计</router-link></li>
                </ul>
            </div> <!-- end of menu -->

            <el-main style="width: 900px; margin: 0 auto;">
                <h2>订单：</h2>
                <el-input v-model="search" size="mini" placeholder="输入订单号搜索"/>
                <el-row v-for="(item, index) in items" :key="index" style="width: 100%">
                    <el-col v-if="include(item.order_id)">
                        <el-card :body-style="{ padding: '0px' }">
                            <el-row style="padding: 10px 0; border-bottom: 1px solid #eff2f6">
                                <el-col style="alignment: left">
                                    订单号：{{item.order_id}}
                                </el-col>
                                <el-col style="alignment: center">
                                    用户名：{{item.user.username}}
                                </el-col>
                                <el-col style="alignment: right">
                                    时间：{{item.time}}
                                </el-col>
                            </el-row>
                            <el-card class="box-card" style="text-align: center;">
                                <el-row v-for="(orderitem, index) in item.orderItemList" :key="index" style="padding: 10px 0; border-bottom: 1px solid #eff2f6">
                                    <el-col :span="4" style="line-height: 103.8px">
                                        <img :src="orderitem.book.cover" style="width: 70px">
                                    </el-col>
                                    <el-col :span="4" style="line-height: 103.8px">
                                        {{ orderitem.book.bookname }}
                                    </el-col>
                                    <el-col :span="3" style="height: 104px; display: flex; justify-content: center; flex-direction: column;">
                                        单价：{{ orderitem.book.price }}
                                    </el-col>
                                    <el-col :span="4" style="height: 104px; display: flex; justify-content: center; flex-direction: column;">
                                        数量：{{orderitem.number}}
                                    </el-col>
                                </el-row>
                            </el-card>
                            <el-row style="padding: 10px 0; border-bottom: 1px solid #eff2f6">
                                <el-col style="alignment: center">
                                    总价：{{getTotalPrice(item.order_id)}}
                                </el-col>
                            </el-row>
                        </el-card>
                    </el-col>
                </el-row>
            </el-main>
            <div class="cleaner_with_height">&nbsp;</div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: 'orders',
        data: function () {
            return {
                username: '',
                activeIndex: 'orders',
                items: [],
                table: [],
                search:'',
            };
        },
        mounted () {
            this.username = this.$route.params.username;
            if (this.username === '') {
                alert("请登录后查看订单！");
                this.$router.push({name: "index"});
            }
            if(this.username == null){
                this.$alert("未登录请先登录");
                this.$router.push({name:"index",params:{}});
            }
            axios
                .get('http://localhost:8088/ebook/getorders')
                .then(response => {
                    this.items = response.data;
                    this.table = response.data;
                    for(let i = 0; i < this.items.length; i++){
                        for(let j = 0; j < this.items[i].orderItemList.length; j++){
                            axios.get('http://localhost:8088/ebook/bookMongo', {params:{isbn:this.items[i].orderItemList[j].book.isbn}}
                            ).then(response => {
                                this.items[i].orderItemList[j].book.cover = "data:image/png;base64," + response.data.cover.toString();
                            });
                        }
                    }
                })
        },
        methods:{
            getTotalPrice(order_id) {
                let price = 0;
                for(let i = 0; i < this.items.length; i++){
                    if(this.items[i].order_id === order_id){
                        for(var j = 0; j < this.items[i].orderItemList.length; j++){
                            price += this.items[i].orderItemList[j].number * this.items[i].orderItemList[j].book.price;
                        }
                    }
                }
                return price;
            },
            include(order_id){
                let oi = order_id.toString();
                if(oi.toLowerCase().includes(this.search.toLowerCase())){
                    return true;
                }else {
                    return false;
                }
            }
        }
    };
</script>

<style scoped>

</style>