<template>
    <div class="home">
        <div id="templatemo_container">
            <div id="templatemo_menu">
                <ul>
                    <li><a href="/home" class="current">首页</a></li>
                    <li><a href="/cart">购物车</a></li>
                    <li><a href="/login">登录</a></li>
                    <li><a href="/regist">注册</a></li>
                    <li><a href="/manage">权限管理</a></li>
                    <li><a href="/order">订单管理</a></li>
                    <form class="b_search">
                        <span>Search</span>
                        <input class="Search" type="text">
                        <button>搜索</button>
                    </form>
                </ul>
            </div> <!-- end of menu -->

            <div id="templatemo_header"></div> <!-- end of header -->

            <div id="templatemo_content">
                <div id="templatemo_content_right">
                    <div class="templatemo_product_box">
                        <h1 onclick="change(this)" style="color: black;">数据库系统概念  <span>(机械工业出版社)</span></h1>
                        <img src="../assets/database.jpg" alt="image" />
                        <div class="product_info">
                            <p>作者: （美）Abraham Silberschatz / （美）Henry F.Korth / （美）S.Sudarshan </p>
                            <h3>99.00元</h3>
                            <div class="buy_now_button"><a href="/cart">Buy Now</a></div>
                            <div class="detail_button"><a href="/subpage">Detail</a></div>
                        </div>
                        <div class="cleaner">&nbsp;</div>
                    </div>

                    <div class="templatemo_product_box">
                        <h1 onclick="change(this)" style="color: black;">深入理解计算机系统  <span>(机械工业出版社)</span></h1>
                        <img src="../assets/ics.jpg" alt="image" />
                        <div class="product_info">
                            <p>作者: Randal E.Bryant / David O'Hallaron</p>
                            <h3>139.00元</h3>
                            <div class="buy_now_button"><a href="/cart">Buy Now</a></div>
                            <div class="detail_button"><a href="/subpage">Detail</a></div>
                        </div>
                        <div class="cleaner">&nbsp;</div>
                    </div>

                    <div class="templatemo_product_box">
                        <h1 onclick="change(this)" style="color: black;">深入理解计算机系统  <span>(机械工业出版社)</span></h1>
                        <img src="../assets/ics.jpg" alt="image" />
                        <div class="product_info">
                            <p>作者: Randal E.Bryant / David O'Hallaron</p>
                            <h3>139.00元</h3>
                            <div class="buy_now_button"><a href="/cart">Buy Now</a></div>
                            <div class="detail_button"><a href="/subpage">Detail</a></div>
                        </div>
                        <div class="cleaner">&nbsp;</div>
                    </div>
                </div>
                <div class="cleaner_with_height">&nbsp;</div>
            </div> <!-- end of content -->
        </div> <!-- end of container -->
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
