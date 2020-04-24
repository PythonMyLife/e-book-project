<template>
    <div class="books">
        <div id="templatemo_container">
            <div id="templatemo_menu">
                <ul>
                    <li><a href="/index">退出登录</a></li>
                    <li><a href="#" class="current">书籍浏览</a></li>
                    <li><router-link :to="{name:'cart',params:{username:this.username}}" >购物车</router-link></li>
                    <li><router-link :to="{name:'userorder',params:{username:this.username}}" >订单与统计</router-link></li>
                    <li><router-link :to="{name:'talk',params:{username:this.username}}" >聊天室</router-link></li>
                </ul>
            </div> <!-- end of menu -->

            <div id="templatemo_header"></div> <!-- end of header -->
            <p>累计访问：{{this.num}}</p>
                <el-table
                        :data="table.filter(data => !search || data.bookname.toLowerCase().includes(search.toLowerCase()))"
                        style="width: 100%">
                    <el-table-column label="书名" prop="bookname"></el-table-column>
                    <el-table-column label="封面" prop="cover" align="center">
                        <template slot-scope="scope">
                            <img :src="scope.row.cover" style="width: 70px">
                        </template>
                    </el-table-column>
                    <el-table-column label="作者" prop="author"></el-table-column>
                    <el-table-column label="价格" prop="price"></el-table-column>
                    <el-table-column label="ISBN编号" prop="isbn"></el-table-column>
                    <el-table-column label="库存量" prop="num"></el-table-column>
                    <el-table-column align="right">
                        <template slot="header" slot-scope="scope">
                            <el-input v-model="search" size="mini" placeholder="输入关键字搜索"/>
                        </template>
                        <template slot-scope="scope">
                            <el-button size="mini" @click="handledetail(scope.$index, scope.row)">详情</el-button>
                            <el-button size="mini" type="danger" @click="handleadd(scope.$index, scope.row)">加购</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            <el-dialog title="书籍详情" :visible.sync="dialogFormVisible" width="400px">
                <el-form >
                    <el-form-item label="书籍详情">
                        <p>{{bookdetail}}</p>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="handleDetailFinish()">确定</el-button>
                </div>
            </el-dialog>
            <div class="cleaner_with_height">&nbsp;</div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: 'books',
        data: function() {
            return {
                message:'',
                table:[],
                search: '',
                username:'',
                bookdetail:'',
                dialogFormVisible: false,
                num: 0
            }
        },
        mounted () {
            this.username = this.$route.params.username;
            if(this.username == null){
                this.$alert("未登录请先登录");
                this.$router.push({name:"index",params:{}});
            }
            axios
                .get('http://localhost:8088/ebook/validBooks').then(response => {
                    this.table = response.data;
                    for(let i = 0; i < this.table.length; i++){
                        axios.get('http://localhost:8088/ebook/bookMongo', {params:{isbn:this.table[i].isbn}}
                        ).then(response => {
                            this.table[i].cover = "data:image/png;base64," + response.data.cover.toString();
                        });
                    }
                    axios.get('http://localhost:8088/visit/count').then(response => {
                        this.num = response.data;
                    })
                });
        },
        methods: {
            handledetail(index, row) {
                this.bookdetail = row.detail;
                this.dialogFormVisible = true;
            },
            handleDetailFinish(){
                this.dialogFormVisible = false;
            },
            handleadd(index, row) {
                let form_data = {"username": this.username,"isbn":row.isbn};
                axios.post('http://localhost:8088/ebook/addcart',form_data).then(response =>{
                    this.message = response.status;
                });
                this.$alert("加购成功!");
            }
        },
    }
</script>

<style scoped>

</style>
