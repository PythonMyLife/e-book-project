<template>
    <div class="index">
        <div id="templatemo_container">
            <div id="templatemo_menu">
                <ul>
                    <li><a href="/index">退出登录</a></li>
                    <li><router-link :to="{name:'books',params:{username:this.username}}" >书籍浏览</router-link></li>
                    <li><router-link :to="{name:'cart',params:{username:this.username}}" >购物车</router-link></li>
                    <li><a href="#" class="current">订单与统计</a></li>
                    <li><router-link :to="{name:'talk',params:{username:this.username}}" >聊天室</router-link></li>
                    <li><router-link :to="{name:'friend',params:{username:this.username}}" >好友</router-link></li>
                </ul>
            </div> <!-- end of menu -->

            <el-main style="width: 900px; margin: 0 auto;">
                <h2>订单：</h2>
                <!--<el-table :data="items.filter(data => (!startdate || data.time >= startdate) &&(!enddate || data.time <= enddate))" :span-method="objectSpanMethod" border style="width: 100%; margin-top: 20px">
                    <el-table-column align="center" prop="order_id" label="订单号" width="100"></el-table-column>
                    <el-table-column align="center" prop="time" label="日期" width="160"></el-table-column>
                    <el-table-column align="center" prop="cover" label="封面" width="110">
                        <template slot-scope="scope">
                            <img :src="scope.row.cover" style="width: 80px; height: 120px">
                        </template>
                    </el-table-column>
                    <el-table-column align="center" prop="bookname" label="书名"></el-table-column>
                    <el-table-column align="center" prop="totalAmount" label="单价"></el-table-column>
                    <el-table-column align="center" prop="number" label="数量"></el-table-column>
                    <el-table-column align="center" prop="total" width="200">
                        <template slot="header" slot-scope="scope">
                            <el-input v-model="startdate" type="date" style="width: 170px;"/>
                            <el-input v-model="enddate"   type="date" style="width: 170px;"/>
                        </template>
                    </el-table-column>
                </el-table>-->

                <el-row v-for="(item, index) in items" :key="index" style="width: 100%">
                    <el-col>
                        <el-card :body-style="{ padding: '0px' }">
                            <el-row style="padding: 10px 0; border-bottom: 1px solid #eff2f6">
                                <el-col style="alignment: center">
                                    订单号：{{item.order_id}}
                                </el-col>
                                <el-col style="alignment: center">
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

                <h2>统计:</h2>
                <!--<template>
                    <el-table
                            :data="items.filter(data => (!starttime || data.time >= starttime) &&(!endtime || data.time <= endtime))"
                            style="width: 100%">
                        <el-table-column
                                label="时间"
                                prop="time">
                        </el-table-column>
                        <el-table-column
                                label="书名"
                                prop="orderItemList.book.bookname">
                        </el-table-column>
                        <el-table-column
                                label="数量"
                                prop="number">
                        </el-table-column>
                        <el-table-column style="width: 100px;"
                                         label="单价"
                                         prop="totalAmount">
                        </el-table-column>
                        <el-table-column
                                align="left">
                            <template slot="header" slot-scope="scope">
                                <el-input v-model="starttime" type="date" size="mini" style="width: 150px;"/>
                                <el-input v-model="endtime"   type="date" size="mini" style="width: 150px;"/>
                            </template>
                        </el-table-column>
                    </el-table>
                </template>-->

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

                    <el-row v-if="spanArr.length===0">
                        <el-col span="10" style="alignment: center">
                            <p style="color: crimson">当前时间区间内没有购买图书！</p>
                        </el-col>
                    </el-row>

                    <el-row v-for="data in spanArr" :key="data" style="padding: 10px 0; border-bottom: 1px solid #eff2f6">
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
                    </el-row>
                    <!--<el-row v-for="(item, index) in items.filter(data => (!starttime || data.time >= starttime) &&(!endtime || data.time <= endtime))" :key="index" style="padding: 10px 0; border-bottom: 1px solid #eff2f6">
                        <el-col :span="4" style="line-height: 103.8px">
                            <img :src="item.time" style="width: 70px">
                        </el-col>
                        <el-col :span="4" style="line-height: 103.8px">
                            {{ item.book.bookname }}
                        </el-col>
                        <el-col :span="3" style="height: 104px; display: flex; justify-content: center; flex-direction: column;">
                            单价：{{ item.book.price }}
                        </el-col>
                        <el-col :span="4" style="height: 104px; display: flex; justify-content: center; flex-direction: column;">
                            数量：
                            <el-input-number v-model="item.num" :precision="0" @change="handleChange(username,item.book.isbn,item.num)" size="small" :min="1" :max="99"></el-input-number>
                        </el-col>
                        <el-col :span="3" style="height: 104px; display: flex; justify-content: center; flex-direction: column;">
                            总价：{{ item.book.price * item.num }}
                        </el-col>
                        <el-col :span="5" style="line-height: 104px">
                            <el-button  circle @click="handleDelete(item.book.isbn)">删除</el-button>
                        </el-col>
                    </el-row>-->
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
                startdate: '',
                enddate: '',
                starttime:'',
                endtime:'',
                bookTemplate:{
                    isbn:'',
                    bookname: '',
                    price: 0,
                    num: 0,
                },
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
                .get('http://localhost:4333/ebook/getuserorder', {params:{username:this.username}})
                .then(response => {
                    this.items = response.data;
                    this.table = response.data;
                    for(let i = 0; i < this.items.length; i++){
                        for(let j = 0; j < this.items[i].orderItemList.length; j++){
                            axios.get('http://localhost:4333/ebook/bookMongo', {params:{isbn:this.items[i].orderItemList[j].book.isbn}}
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
            }
        }
    };
</script>

<style scoped>

</style>
