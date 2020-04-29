<template>
    <div class="home">
        <div id="templatemo_container">
            <div id="templatemo_menu">
                <ul>
                    <li><a href="/index">退出登录</a></li>
                    <li><router-link :to="{name:'books',params:{username:this.username}}" >书籍浏览</router-link></li>
                    <li><router-link :to="{name:'cart',params:{username:this.username}}" >购物车</router-link></li>
                    <li><router-link :to="{name:'userorder',params:{username:this.username}}" >订单与统计</router-link></li>
                    <li><a href="#" class="current">聊天室</a></li>
                    <li><router-link :to="{name:'friend',params:{username:this.username}}" >好友</router-link></li>
                </ul>
            </div> <!-- end of menu -->
            <el-card class="box-card" style="text-align: left;">
                <el-container>
                    <el-container>
                        <el-aside style="height: 600px; width: 200px; border: 1px solid #eee">
                            <el-table :data="users">
                                <el-table-column prop="name" label="聊天成员" >
                                </el-table-column>
                            </el-table>
                        </el-aside>
                        <el-container>
                            <el-main style="padding-top: 0px; height: 600px; width: 720px; border: 1px solid #eee">
<!--                                                  <el-table :data="tableData">-->
<!--                                                    <el-table-column prop="date" label="日期" width="140">-->
<!--                                                    </el-table-column>-->
<!--                                                    <el-table-column prop="name" label="姓名" width="120">-->
<!--                                                    </el-table-column>-->
<!--                                                    <el-table-column prop="address" label="地址">-->
<!--                                                    </el-table-column>-->
<!--                                                  </el-table>-->
                                <div id="message" style='width: 600px;display:block;word-break: break-all;word-wrap: break-word;'>
                                    <el-row v-for="(item, index) in messages" :key="index" style="padding: 10px 0; border-bottom: 1px solid #eff2f6">
                                        <el-col style="line-height: 103.8px">
                                            {{item}}
                                        </el-col>
                                    </el-row>
                                </div>
                            </el-main>
                            <el-footer style="padding-top: 0px; height: 110px; width: 720px; border: 1px solid #eee" align="right">
                                <el-input type="textarea" :rows="3" resize="none" placeholder="请输入内容" v-model="textarea" maxlength="30" show-word-limit="true">
                                </el-input>
                                <el-button type="primary" size="small" @click="websocketSend()">发送</el-button>
                            </el-footer>
                        </el-container>
                    </el-container>
                </el-container>
            </el-card>
<!--            <el-row>-->
<!--                <el-col :span="16"><div class="grid-content bg-purple">-->
<!--                    <el-card class="box-card" style="text-align: center;">-->
<!--                        <div slot="header">-->
<!--                            <span>聊天内容</span>-->
<!--                        </div>-->
<!--                        <el-row v-for="(item, index) in messages" :key="index" style="padding: 10px 0; border-bottom: 1px solid #eff2f6">-->
<!--                            <el-col style="line-height: 103.8px">-->
<!--                                {{item}}-->
<!--                            </el-col>-->
<!--                        </el-row>-->
<!--                    </el-card>-->
<!--                </div></el-col>-->
<!--                <el-col :span="8"><div class="grid-content bg-purple-light">-->
<!--                    <el-card class="box-card" style="text-align: center;">-->
<!--                        <div slot="header">-->
<!--                            <span>当前用户</span>-->
<!--                        </div>-->
<!--                        <el-row v-for="(item, index) in users" :key="index" style="padding: 10px 0; border-bottom: 1px solid #eff2f6">-->
<!--                            <el-col style="line-height: 103.8px">-->
<!--                                {{item}}-->
<!--                            </el-col>-->
<!--                        </el-row>-->
<!--                    </el-card>-->
<!--                </div></el-col>-->
<!--            </el-row>-->
        </div> <!-- end of container -->
    </div>
</template>

<script>
    export default {
        name: 'talk',
        data: function() {
            const item = {
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            };
            return {
                tableData: Array(20).fill(item),
                textarea: "",
                messages: [],
                users: [],
                username:'',
                websocket: null
            }
            // return {
            //     username:'',
            //     websocket: null,
            //     messages: [],
            //     users: []
            // }
        },
        mounted () {
            this.username = this.$route.params.username;
            if(this.username == null){
                this.$alert("未登录请先登录");
                this.$router.push({name:"index",params:{}});
            }
            this.initWebsocket();
        },
        destroyed() {
            this.websocket.close();
        },
        methods: {
            initWebsocket() {
                const uri = "ws://localhost:4333/websocket";
                this.websocket = new WebSocket(uri);
                this.websocket.onopen = this.onOpen;
                this.websocket.onmessage = this.onMessage;
                this.websocket.onerror = this.onError;
                this.websocket.onClose = this.websocketClose;
            },
            onOpen() {
                let joinMsg = {};
                joinMsg.type = "join";
                joinMsg.name = this.username;
                let jsonstr = JSON.stringify(joinMsg);
                this.websocket.send(jsonstr);
                window.console.log("connect to ws.");
            },
            onError() {
                window.console.log("connect error");
            },
            onMessage(evt) {
                window.console.log(evt.data);
                var line = "";
                /* Parse the message into a JavaScript object */
                var msg = JSON.parse(evt.data);
                if (msg.type === "chat") {
                    line = msg.name + ": ";
                    if (msg.target.length > 0)
                        line += "@" + msg.target + " ";
                    line += msg.message + "";
                    this.messages.push(line);
                } else if (msg.type === "info") {
                    line = "[--" + msg.info + "--]";
                    this.messages.push(line);
                } else if (msg.type === "users") {
                    this.users = [];
                    for (var i=0; i < msg.userlist.length; i++)
                        this.users.push({"name": msg.userlist[i]});
                }
            },
            websocketSend() {
                const chatMsg = {};
                let jsonstr;
                let msgstr;
                chatMsg.type = "chat";
                chatMsg.name = this.username;
                msgstr = this.textarea;
                chatMsg.target = this.getTarget(msgstr.replace(/,/g, ""));
                chatMsg.message = this.cleanTarget(msgstr);
                chatMsg.message = chatMsg.message.replace(/(\r\n|\n|\r)/gm,"");
                /* Convert the object to JSON */
                jsonstr = JSON.stringify(chatMsg);
                /* Send the message as JSON text */
                this.websocket.send(jsonstr);
                this.textarea = "";
            },
            websocketClose(e) {
                window.console.log('connect close', e);
            },
            getTarget(str) {
                var arr = str.split(" ");
                var target = "";
                for (var i=0; i<arr.length; i++) {
                    if (arr[i].charAt(0) === '@') {
                        target = arr[i].substring(1,arr[i].length);
                        target = target.replace(/(\r\n|\n|\r)/gm,"");
                    }
                }
                return target;
            },
            cleanTarget(str) {
                var arr = str.split(" ");
                var cleanstr = "";
                for (var i=0; i<arr.length; i++) {
                    if (arr[i].charAt(0) !== '@')
                        cleanstr += arr[i] + " ";
                }
                return cleanstr.substring(0,cleanstr.length-1);
            }
        }
    }
</script>
