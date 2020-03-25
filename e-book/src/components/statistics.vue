<template>
    <div class="index">
        <div id="templatemo_container">
            <div id="templatemo_menu">
                <ul>
                    <li><a href="/index">退出登录</a></li>
                    <li><router-link :to="{name:'manage',params:{username:this.username}}" >权限管理</router-link></li>
                    <li><router-link :to="{name:'bookmanage',params:{username:this.username}}" >书籍管理</router-link></li>
                    <li><router-link :to="{name:'order',params:{username:this.username}}" >订单管理</router-link></li>
                    <li><router-link :to="{name:'statistics',params:{username:this.username}}" class="current" >统计</router-link></li>
                </ul>
            </div> <!-- end of menu -->

            <el-main style="width: 900px; margin: 0 auto;">
                <h2>书籍统计:</h2>
                <el-card class="box-card" style="text-align: center;">
                    <el-row>
                        <template>
                            <p>
                                起始时间：
                                <el-input v-model="starttime" type="date" size="mini" style="width: 150px;"/>
                                终止时间：
                                <el-input v-model="endtime"   type="date" size="mini" style="width: 150px;"/>
                                <el-button @click="statistics()">确定</el-button>
                            </p>
                        </template>
                    </el-row>

                    <el-table :data="spanArr" style="alignment: center">
                        <el-table-column prop="isbn" style="alignment: center" label="ISBN" width="150"></el-table-column>
                        <el-table-column prop="bookname" style="alignment: center" label="书名" width="150"></el-table-column>
                        <el-table-column prop="price" style="alignment: center" label="单价" width="150"></el-table-column>
                        <el-table-column prop="num" style="alignment: center" label="销量" width="150"></el-table-column>
                    </el-table>

                    <!--<el-row v-for="data in spanArr" :key="data" style="padding: 10px 0; border-bottom: 1px solid #eff2f6">
                        <el-col :span="4" style="line-height: 103.8px">
                            {{data.isbn}}
                        </el-col>
                        <el-col :span="4" style="line-height: 103.8px">
                            {{ data.bookname }}
                        </el-col>
                        <el-col :span="3" style="height: 104px; display: flex; justify-content: center; flex-direction: column;">
                            单价：{{ data.price }}
                        </el-col>
                        <el-col :span="3" style="height: 104px; display: flex; justify-content: center; flex-direction: column;">
                            数量：{{ data.num }}
                        </el-col>
                    </el-row>-->
                </el-card>
            </el-main>

            <el-main style="width: 900px; margin: 0 auto;">
                <h2>所有用户消费统计:</h2>
                <el-card class="box-card" style="text-align: center;">
                    <el-row>
                        <template>
                            <p>
                                起始时间：
                                <el-input v-model="startdate" type="date" size="mini" style="width: 150px;"/>
                                终止时间：
                                <el-input v-model="enddate"   type="date" size="mini" style="width: 150px;"/>
                                <el-button @click="statisticsUser()">确定</el-button>
                            </p>
                        </template>
                    </el-row>
                    <el-table :data="spanUser" style="alignment: center">
                        <el-table-column prop="username" style="alignment: center" label="用户名" width="360"></el-table-column>
                        <el-table-column prop="total" style="alignment: center" label="累计消费" width="360"></el-table-column>
                    </el-table>
                </el-card>
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
                spanArr: [],
                spanUser: [],
                allUser: [],
                startdate: '',
                enddate: '',
                starttime:'',
                endtime:'',
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
                .get('http://localhost:8088/ebook/getorders', {params:{username:this.username}})
                .then(response => {
                    this.items = response.data;
                    this.table = response.data;
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
            statistics() {
                this.spanArr = [];
                let data_all = this.table.slice();
                while(data_all.length > 0){
                    let data = data_all.pop();
                    let oiList = data.orderItemList.slice();
                    if((!this.starttime || data.time >= this.starttime) &&(!this.endtime || data.time <= this.endtime)){
                        while(oiList.length > 0){
                            let orderItem = oiList.pop();
                            let flag = false;
                            for(let i = 0; i < this.spanArr.length; i++){
                                if(orderItem.book.isbn === this.spanArr[i].isbn){
                                    flag = true;
                                    this.spanArr[i].num = this.spanArr[i].num + orderItem.number;
                                }
                            }
                            if(!flag){
                                let book = {
                                    isbn:'',
                                    bookname: '',
                                    price: 0,
                                    num: 0,
                                };
                                book.bookname = orderItem.book.bookname;
                                book.isbn = orderItem.book.isbn;
                                book.num = orderItem.number;
                                book.price = orderItem.book.price;
                                this.spanArr.push(book);
                            }
                        }
                    }
                }
            },
            statisticsUser() {
                this.spanUser = [];
                let data_all = this.table.slice();
                while(data_all.length > 0){
                    let data = data_all.pop();
                    if((!this.startdate || data.time >= this.startdate) &&(!this.enddate || data.time <= this.enddate)){
                        let flag = false;
                        for(let i = 0; i < this.spanUser.length; i++){
                            if(data.user.username === this.spanUser[i].username){
                                flag = true;
                                this.spanUser[i].total = this.spanUser[i].total + this.getTotalPrice(data.order_id);
                            }
                        }
                        if(!flag){
                            let new_user = {
                                username: "",
                                total: 0
                            };
                            new_user.username = data.user.username;
                            new_user.total = this.getTotalPrice(data.order_id);
                            this.spanUser.push(new_user);
                        }
                    }
                }
                axios.get('http://localhost:8088/ebook/getUsers').then(response => {
                    this.allUser = response.data;
                    while(this.allUser.length > 0){
                        let username = (this.allUser.pop()).username;
                        let flag = false;
                        for(let i = 0; i < this.spanUser.length; i++){
                            if(username === this.spanUser[i].username){
                                flag = true;
                            }
                        }
                        if(!flag){
                            let new_user = {
                                username: "",
                                total: 0
                            };
                            new_user.username = username;
                            this.spanUser.push(new_user);
                        }
                    }
                });
            }
        }
    };
</script>

<style scoped>

</style>