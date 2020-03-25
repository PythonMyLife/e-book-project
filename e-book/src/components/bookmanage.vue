<template>
    <div class="books">
        <div id="templatemo_container">
            <div id="templatemo_menu">
                <ul>
                    <li><a href="/index">退出登录</a></li>
                    <li><router-link :to="{name:'manage',params:{username:this.username}}" >权限管理</router-link></li>
                    <li><router-link :to="{name:'bookmanage',params:{username:this.username}}" class="current" >书籍管理</router-link></li>
                    <li><router-link :to="{name:'order',params:{username:this.username}}" >订单管理</router-link></li>
                    <li><router-link :to="{name:'statistics',params:{username:this.username}}" >统计</router-link></li>
                </ul>
            </div> <!-- end of menu -->

            <div id="templatemo_header"></div> <!-- end of header -->
            <el-input v-model="search" size="mini" placeholder="输入关键字搜索"/>
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
                        <el-button size="mini" @click="handleAddBook()">添加书籍</el-button>
                    </template>
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleDetail(scope.$index, scope.row)">详情</el-button>
                        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="clear"></div>
            <el-dialog title="书籍编辑" :visible.sync="dialogFormVisible" width="400px">
                <el-form :model="bookForm">
                    <el-form-item label="书名">
                        <el-input v-model="bookForm.bookname" ></el-input>
                    </el-form-item>
                    <el-form-item label="封面">
                        <input type='file' accept="image/*" id="file1" ref="file1">
                    </el-form-item>
                    <el-form-item label="作者">
                        <el-input v-model="bookForm.author"></el-input>
                    </el-form-item>
                    <el-form-item label="单价">
                        <el-input v-model="bookForm.price"></el-input>
                    </el-form-item>
                    <el-form-item label="库存">
                        <el-input v-model="bookForm.num"></el-input>
                    </el-form-item>
                    <el-form-item label="详情">
                        <el-input v-model="bookForm.detail"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="handleEditBook()">提交</el-button>
                </div>
            </el-dialog>
            <el-dialog title="书籍详情" :visible.sync="dialogFormVisible1" width="400px">
                <el-form >
                    <el-form-item label="书籍详情">
                        <p>{{bookdetail}}</p>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="handleDetailFinish()">确定</el-button>
                </div>
            </el-dialog>
            <el-dialog title="添加书籍" :visible.sync="dialogFormVisible2" width="400px">
                <el-form :model="addbookForm">
                    <el-form-item label="ISBN">
                        <el-input v-model="addbookForm.isbn" ></el-input>
                    </el-form-item>
                    <el-form-item label="封面">
                        <input type='file' accept="image/*" id="file" ref="file">
                    </el-form-item>
                    <el-form-item label="书名">
                        <el-input v-model="addbookForm.bookname" ></el-input>
                    </el-form-item>
                    <el-form-item label="作者">
                        <el-input v-model="addbookForm.author"></el-input>
                    </el-form-item>
                    <el-form-item label="单价">
                        <el-input v-model="addbookForm.price"></el-input>
                    </el-form-item>
                    <el-form-item label="库存">
                        <el-input v-model="addbookForm.num"></el-input>
                    </el-form-item>
                    <el-form-item label="详情">
                        <el-input v-model="addbookForm.detail"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="handleAddBookSubmit()">提交</el-button>
                </div>
            </el-dialog>
            <div class="cleaner_with_height"></div>
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
                invalidTable:[],
                search: '',
                dialogFormVisible: false,
                dialogFormVisible1: false,
                dialogFormVisible2: false,
                username:'',
                file:'',
                bookdetail:'',
                bookForm: {
                    bookname: "",
                    author: "",
                    cover: null,
                    isbn: "",
                    num: 0,
                    price: 0,
                    detail: ""
                },
                addbookForm: {
                    bookname: "",
                    author: "",
                    cover: null,
                    isbn: "",
                    num: 0,
                    price: 0,
                    detail: ""
                }
            }
        },
        mounted () {
            this.username = this.$route.params.username;
            if(this.username == null){
                this.$alert("未登录请先登录");
                this.$router.push({name:"index",params:{}});
            }
            axios.get('http://localhost:8088/ebook/validBooks').then(response => {
                this.table = response.data;
                for(let i = 0; i < this.table.length; i++){
                    axios.get('http://localhost:8088/ebook/bookMongo', {params:{isbn:this.table[i].isbn}}
                    ).then(response => {
                        this.table[i].cover = "data:image/png;base64," + response.data.cover.toString();
                    });
                }
            });
        },
        methods: {
            handleDetail(index, row) {
                this.bookdetail = row.detail;
                this.dialogFormVisible1 = true;
            },
            handleDetailFinish() {
                this.dialogFormVisible1 = false;
            },
            handleDelete(index, row) {
                let form_data = {"username" : this.username, "isbn" : row.isbn};
                axios.post('http://localhost:8088/ebook/deleteBook', form_data).then(response =>{
                    if(response.data) {
                        this.$alert("删除成功");
                        axios.get('http://localhost:8088/ebook/validBooks').then(response => {
                            this.table = response.data;
                            for(let i = 0; i < this.table.length; i++){
                                axios.get('http://localhost:8088/ebook/bookMongo', {params:{isbn:this.table[i].isbn}}
                                ).then(response => {
                                    this.table[i].cover = "data:image/png;base64," + response.data.cover.toString();
                                });
                            }
                        });
                    }else {
                        this.$alert("删除失败");
                    }
                });
            },
            handleEdit(index, row){
                this.dialogFormVisible = true;
                this.bookForm.isbn = row.isbn;
                this.bookForm.author = row.author;
                this.bookForm.bookname = row.bookname;
                this.bookForm.cover = row.cover;
                this.bookForm.detail = row.detail;
                this.bookForm.num = row.num;
                this.bookForm.price = row.price;
            },
            handleEditBook(){
                this.dialogFormVisible = false;
                let imagFile = this.$refs.file1.files[0];
                let bodyFormData = new FormData();
                bodyFormData.set('isbn', this.bookForm.isbn);
                bodyFormData.set('bookname', this.bookForm.bookname);
                bodyFormData.set('author', this.bookForm.author);
                bodyFormData.set('detail', this.bookForm.detail);
                bodyFormData.set('num', this.bookForm.num);
                bodyFormData.set('price', this.bookForm.price);
                if(imagFile === undefined){
                    axios({method: 'post',
                        url: 'http://localhost:8088/ebook/editBookWithoutCover',
                        data: bodyFormData,
                        config: { headers: {'Content-Type': 'multipart/form-data' }}}
                    ).then(response =>{
                        if(response.data) {
                            axios
                                .get('http://localhost:8088/ebook/validBooks').then(response => {
                                this.table = response.data;
                                for(let i = 0; i < this.table.length; i++){
                                    axios.get('http://localhost:8088/ebook/bookMongo', {params:{isbn:this.table[i].isbn}}
                                    ).then(response => {
                                        this.table[i].cover = "data:image/png;base64," + response.data.cover.toString();
                                    });
                                }
                            });
                            this.$alert("编辑成功");
                        }else {
                            this.$alert("编辑失败");
                        }
                    });
                }else{
                    bodyFormData.append('cover',imagFile);
                    axios({method: 'post',
                        url: 'http://localhost:8088/ebook/editBook',
                        data: bodyFormData,
                        config: { headers: {'Content-Type': 'multipart/form-data' }}}
                    ).then(response =>{
                        if(response.data) {
                            axios
                                .get('http://localhost:8088/ebook/validBooks').then(response => {
                                this.table = response.data;
                                for(let i = 0; i < this.table.length; i++){
                                    axios.get('http://localhost:8088/ebook/bookMongo', {params:{isbn:this.table[i].isbn}}
                                    ).then(response => {
                                        this.table[i].cover = "data:image/png;base64," + response.data.cover.toString();
                                    });
                                }
                            });
                            this.$alert("编辑成功");
                        }else {
                            this.$alert("编辑失败");
                        }
                    });
                }
            },
            handleAddBook(){
                this.dialogFormVisible2 = true;
            },
            handleAddBookSubmit(){
                this.dialogFormVisible2 = false;
                if(this.addbookForm.isbn===''){
                    this.$alert("请添加书籍的ISBN号！")
                } else if(this.addbookForm.author===''){
                    this.$alert("请添加作者名字！")
                } else if(this.addbookForm.bookname===''){
                    this.$alert("请添加书籍名字！")
                } else if(this.addbookForm.detail===''){
                    this.$alert("请添加书籍的详细信息！")
                } else if(this.addbookForm.num===0){
                    this.$alert("请添加书籍数量！")
                } else if(this.addbookForm.price===0){
                    this.$alert("请添加书籍价格！")
                } else{
                    let imagFile = this.$refs.file.files[0];
                    let bodyFormData = new FormData();
                    bodyFormData.set('isbn', this.addbookForm.isbn);
                    bodyFormData.set('bookname', this.addbookForm.bookname);
                    bodyFormData.set('author', this.addbookForm.author);
                    bodyFormData.set('detail', this.addbookForm.detail);
                    bodyFormData.set('num', this.addbookForm.num);
                    bodyFormData.set('price', this.addbookForm.price);
                    bodyFormData.append('cover',imagFile);
                    axios({method: 'post',
                        url: 'http://localhost:8088/ebook/addBook',
                        data: bodyFormData,
                        config: { headers: {'Content-Type': 'multipart/form-data' }}}
                    ).then(response => {
                        if(response.data){
                            this.$alert("添加书籍成功");
                            axios
                                .get('http://localhost:8088/ebook/validBooks').then(response => {
                                this.table = response.data;
                                for(let i = 0; i < this.table.length; i++){
                                    axios.get('http://localhost:8088/ebook/bookMongo', {params:{isbn:this.table[i].isbn}}
                                    ).then(response => {
                                        this.table[i].cover = "data:image/png;base64," + response.data.cover.toString();
                                    });
                                }
                            });
                        }
                    });
                }
            }
        },
    }
</script>

<style scoped>

</style>