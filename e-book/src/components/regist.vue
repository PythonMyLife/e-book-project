<template>
  <div class="register">
    <div id="templatemo_container">
      <div id="templatemo_menu">
        <ul>
          <li><a href="/index" >首页</a></li>
          <li><a href="/regist" class="current">注册</a></li>
          <li><a href="/books">书籍浏览</a></li>
          <li><a href="/cart">购物车</a></li>
          <li><a href="/userorder">订单与统计</a></li>
        </ul>
      </div> <!-- end of menu -->

      <article >
        <section class="sign">
          <aside>
            <em>
              <img src="../assets/user.png">
            </em>
            <h3 class="welcome" style="color: crimson">{{error}}</h3>
            <form>
              <p class="name"><i></i><input type="text" id="userName" v-model="username" class="userName" placeholder="请输入用户名" value=""></p>
              <p class="password"><i></i><input type="password" id="password1" v-model="password1" class="pwd" placeholder="请输入密码"></p>
              <p class="password"><i></i><input type="password" id="password2" v-model="password2" class="pwd" placeholder="请再次输入密码"></p>
              <p><i></i><input type="text" id="email" v-model="email" class="email" placeholder="请输入邮箱"></p>
              <el-button class="login_btn" @click="submit" type="primary">注册</el-button>
              <div class="clear"></div>
            </form>
          </aside>
        </section>
      </article>
      <div class="cleaner_with_height">&nbsp;</div>
    </div> <!-- end of container -->
  </div>
</template>

<script>
  import axios from "axios";
export default {
  name: 'register',
  data: function() {
    return {
      username:'',
      password1:'',
      password2:'',
      email:'',
      error:''
    }
  },
  methods:{
    submit(){
      this.error ="";
      let regEmail= /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
      if(this.username==''){
        this.error = "请输入用户名！";
      }else if(this.password1 == ''){
        this.error = "请输入密码！";
      }else if(this.password2 == ''){
        this.error = "请输入确认密码！";
      }else if(this.password1 == ''){
        this.error = "请输入邮箱！";
      }else if(this.password1 != this.password2){
        this.error = "两次输入的密码应当相同！";
      }else if(!regEmail.test(this.email)){
        this.error = "邮箱格式不正确";
      }else{
        let form_data = {"username":this.username,"password":this.password1,"email":this.email};
        axios.post('http://localhost:8088/ebook/regist',form_data).then(response=>{
          this.msg = response.data;
          if(this.msg === 0 ){
            this.error = "用户名重复！";
          }else {
            alert("注册成功！");
            this.$router.push({name:"books",params:{username:this.username}});
          }
        })
      }
    },

  }
}
</script>
