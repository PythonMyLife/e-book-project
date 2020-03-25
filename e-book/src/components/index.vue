<template>
    <div class="index">
        <div id="templatemo_container">
            <div id="templatemo_menu">
                <ul>
                    <li><a href="/index" class="current">首页</a></li>
                    <li><a href="/regist">注册</a></li>
                    <li><a href="/books">书籍浏览</a></li>
                    <li><a href="/cart">购物车</a></li>
                    <li><a href="/userorder">订单与统计</a></li>
                    <li><a href="/talk">聊天室</a></li>
                </ul>
            </div> <!-- end of menu -->

            <article>
                <section class="sign">
                    <aside>
                        <h2 class="welcome">欢迎来到网上书城！</h2>
                        <em>
                            <img src="../assets/user.png">
                        </em>
                        <h3 class="welcome" style="color: crimson">{{error}}</h3>
                        <form>
                            <p class="name"><i></i><input type="text" id="userName" class="userName" v-model="username" placeholder="请输入用户名"></p>
                            <p class="password"><i></i><input type="password" id="password" class="pwd" v-model="password" placeholder="请输入密码"></p>
                            <el-button class="login_btn" @click="submit" type="primary">登录</el-button>
                            <p class="regist"><span>没有账号?</span><a href="/regist">立即注册</a></p>
                            <div class="clear"></div>
                        </form>
                    </aside>
                </section>
            </article>
            <div class="cleaner_with_height">&nbsp;</div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: 'index',
        data: function() {
            return {
                error: '',
                username:'',
                password: '',
                msg:''

            }
        },
        methods:{
            submit(){
                let form_data = {"username":this.username,"password":this.password};
                axios.post('http://localhost:8088/ebook/index',form_data).then(response=>{
                    this.msg = response.data;
                    if(this.msg === -1 ){
                        this.error = "用户名或密码错误";
                    }else if(this.msg === 0){
                        this.$router.push({name:"books",params:{username:this.username}});
                    }else if(this.msg === 1){
                        this.$router.push({name:"manage",params:{username:this.username}});
                    }else if(this.msg === 2){
                        this.error = "您的用户已被禁用！";
                    }
                })
            }
        }
    }
</script>
