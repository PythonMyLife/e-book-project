<template>
  <div class="login">
    <div id="templatemo_container">
      <div id="templatemo_menu">
        <ul>
          <li><a href="/home">首页</a></li>
          <li><a href="/cart">购物车</a></li>
          <li><a href="/login" class="current">登录</a></li>
          <li><a href="/regist">注册</a></li>
          <li><a href="/manage">权限管理</a></li>
          <li><a href="/order">订单管理</a></li>
        </ul>
      </div> <!-- end of menu -->

      <article>
        <section class="sign">
          <aside>
            <em>
              <img src="../assets/user.png">
            </em>
            <form action="#" method="post">
              <p class="name"><i></i><input type="text" id="userName" class="userName" placeholder="请输入用户名"></p>
              <p class="password"><i></i><input type="password" id="password" class="pwd" placeholder="请输入密码"></p>
              <button>登录</button>
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
export default {
  name: 'Home',
  data: function() {
    return {

    }
  }
}
</script>
