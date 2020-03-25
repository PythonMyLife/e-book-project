<template>
  <div class="home">
    <div id="templatemo_container">
      <div id="templatemo_menu">
        <ul>
          <li><router-link :to="{name:'books',params:{username:this.username}}" >书籍浏览</router-link></li>
          <li><a href="#" class="current">书籍详情</a></li>
        </ul>
      </div> <!-- end of menu -->

      <div id="templatemo_header"></div> <!-- end of header -->

      <div id="templatemo_content">
        <div id="templatemo_content_right">
          <h1>{{detail}}</h1>


        </div> <!-- end of content right -->

        <div class="cleaner_with_height">&nbsp;</div>
    </div>
    </div>
  </div>
</template>

<script>
  import axios from "axios";
  export default {
    name: 'Home',
    data: function() {
      return {
        detail:'',
        username:'',
        data:'',
        isbn:''
      }
    },
    mounted () {
      this.username = this.$route.params.username;
      this.isbn = this.$route.params.data;
      axios.get('http://localhost:8088/ebook/subpage',{params:{isbn:this.isbn}}).then(response => {
        this.detail = response.data;
      });
    },
  }
</script>
