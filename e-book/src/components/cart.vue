<template>
    <div class="home">
        <div id="templatemo_container">
            <div id="templatemo_menu">
                <ul>
                    <li><a href="/index">退出登录</a></li>
                    <li><router-link :to="{name:'books',params:{username:this.username}}" >书籍浏览</router-link></li>
                    <li><a href="#" class="current">购物车</a></li>
                    <li><router-link :to="{name:'userorder',params:{username:this.username}}" >订单与统计</router-link></li>
                    <li><router-link :to="{name:'talk',params:{username:this.username}}" >聊天室</router-link></li>
                    <li><router-link :to="{name:'friend',params:{username:this.username}}" >好友</router-link></li>
                </ul>
            </div> <!-- end of menu -->

            <div id="templatemo_content" >
                <el-card class="box-card" style="text-align: center;">
                    <div v-if="table.length == 0">
                        您的购物车为空
                    </div>
                    <el-row v-for="(item, index) in table" :key="index" style="padding: 10px 0; border-bottom: 1px solid #eff2f6">
                        <el-col :span="4" style="line-height: 103.8px">
                            <img :src="item.book.cover" style="width: 70px">
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
                    </el-row>
                </el-card>
                <el-row style="margin-top: 10px;">
                    <el-card class="box-card" style="text-align: center;">
                        <el-row type="flex" justify="center">
                            <el-col :span="4" style="text-align: center; line-height: 40px;">
                                总价: {{ getTotalAmount }}
                            </el-col>
                            <el-col :span="4" style="text-align: center;">
                                <el-button @click="handleSubmit()" type="primary">下单</el-button>
                            </el-col>
                        </el-row>
                    </el-card>
                </el-row>
            </div>
        </div> <!-- end of container -->
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: 'cart',
        data: function() {
            return {
                username:'',
                table: [],
                message:'',
                activeIndex: 'cart',
                cartItems: [{
                    id: 1,
                    title: '深入理解计算机系统',
                    number: 1,
                    price: 99,
                    cover:require("../../static/ics.jpg")
                },{
                    id: 2,
                    title: '数据库系统原理',
                    number: 2,
                    price: 139,
                    cover:require("../../static/database.jpg")
                }]
            }
        },
        mounted () {
            this.username = this.$route.params.username;
            if(this.username == null){
                this.$alert("未登录请先登录");
                this.$router.push({name:"index",params:{}});
            }
            axios.get('http://localhost:4333/ebook/get_user_carts',{params:{username:this.username}}).then(response => {
                this.table = response.data;
                for(let i = 0; i < this.table.length; i++){
                    axios.get('http://localhost:4333/ebook/bookMongo', {params:{isbn:this.table[i].book.isbn}}
                    ).then(response => {
                        this.table[i].book.cover = "data:image/png;base64," + response.data.cover.toString();
                    });
                }
            });
        },
        computed: {
            getTotalAmount() {
                var s = 0;
                for (var i = 0; i < this.table.length; i++) {
                    s += (this.table[i].num * this.table[i].book.price)
                }
                return s;
            }
        },
        methods: {
            handleDelete(isbn) {
                for (var i = 0; i < this.table.length; i++)
                {
                    if (this.table[i].book.isbn === isbn) {
                        let form_data={"username":this.username,"isbn":this.table[i].book.isbn};
                        axios.post('http://localhost:4333/ebook/deletecart',form_data);
                        this.table.splice(i, 1);
                    }
                }
            },
            handleChange(username,isbn,number){
                let form_data={"username":username,"isbn":isbn,"num":number};
                axios.get('http://localhost:4333/ebook/savecart',{params:form_data});
            },
            handleSubmit(){
                axios.get('http://localhost:4333/ebook/submitorder',{params:{username:this.username}}).then(response=>{
                    this.message = response.data;
                    this.$alert("提交订单成功,等待处理，详情请至订单页查询！");
                    this.table = [];
                    // axios.get('http://localhost:8088/ebook/get_user_carts',{params:{username:this.username}}).then(response => {
                    //     this.table = response.data;
                    // });
                });
            }
        }
    }
</script>
