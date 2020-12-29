<template>
  <div class="login_container">
    <div class="login_box">
      <!--头像区域-->
      <div class="avatar_box">
        <img src="../assets/logo.png"/>
      </div>
      <!--登录表单区域
      model和v-model（加在input上）结合使用是共同绑定对象数据；
      rules和prop（加在el-form-item上）结合使用是检验验证规则；
      ref="loginFormRef"代表该组件的实例对象，对象.函数即可调用函数
      -->
      <el-form ref="loginFormRef" :rules="loginFormRules" :model="loginForm" label-width="0px" class="login_form" >
       <!--用户名-->
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" prefix-icon="el-icon-s-custom"></el-input>
        </el-form-item>
       <!--密码-->
        <el-form-item prop="password">
          <!--type="password"实现密码隐藏-->
          <el-input type="password" v-model="loginForm.password" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <!--按钮区域-->
        <el-form-item class="btns">
          <el-button type="primary" :plain="true" @click="open2">登录</el-button>
          <el-button type="info" @click="resetLoginForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import api from '../network/request'
  export default {
    data() {
      return {
        //这是登录表单的数据绑定对象
        loginForm: {
          username: 'admin',
          password: '123456'
        },
        //  这是表单的验证规则对象
        loginFormRules: {
          //  验证用户名是否合法
          username: [
            //required: true代表必填项，没填就提示message的内容，触发条件是trigger：blur是鼠标离开时
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
          ],
          //  验证密码是否合法
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur'}
          ]

        }
      }
    },
    methods: {
      //点击重置按钮，重置登录表单
      resetLoginForm() {
        this.$refs.loginFormRef.resetFields();
      },

/*    login(){
        this.$refs.loginFormRef.validate(async valid=>{
          console.log(valid);
          if(!valid) return;
     /!*   await和async是将promise变成具体的数据；
        data:res是将data从获取的数据中抽离出来重新命名为res（因为只需要data）
        请求地址是login，请求对象是this.loginForm，请求方式是post*!/
         /!* const {data : res} = await axios.post('login',this.loginForm);
          console.log(res);
          if(res.meta.status != 200) return this.$message.error("登录失败！")
          this.$message.error("登录成功！")*!/
          window.sessionStorage.setItem("token",res.data.token);
          })
        }*/

    /*  //完善版
    // 点击登录按钮，实现表单的预验证。其中valid返回的是验证结果booler值；如果为true，再进行是否存在该用户的验证
      login(){
      this.$refs.loginFormRef.validate(valid=>{
        console.log(valid);
        if(!valid) return;
        api.post('localhost/home').then(res=>{
          console.log(res);
          if(res.meta.status != 200) return this.$message.error("登录失败！")
          this.$message.error("登录成功！")*!/
          //1. 将登录成功之后的token保存到客户端的sessionStorage中
          //1.1 项目中出了登录之外的其他API接口，必须在登录之后才能访问
          //1.2 token只应在当前网站打开期间生效，所以将token保存在sessionStorage中
          window.sessionStorage.setItem("token",res.data.token);
          })
        }
      },*/
      open2() {
        this.$message({
          message: '恭喜你，登录成功',
          type: 'success'
        });
        //跳转到home页面，router同步配置
        this.$router.push('/home');
      },
    }
  }
</script>

<style lang="less" scoped>
.login_container{
  background-color: #2b4b6b;
  height: 100%;
}
.login_box{
  width: 450px;
  height: 300px;
  background-color: #fff;
  /*圆角边框*/
  border-radius: 3px;
  /*居中的固定写法*/
  position:absolute;
  left: 50%;
  top:50%;
  transform: translate(-50%,-50%);

  .avatar_box{
    height: 130px;
    width: 130px;
    background-color: #fff;
    /*边框线 1像素、实线、白色*/
    border:1px solid #eee;
    /*边框圆角*/
    border-radius: 50%;
    /*边框与图片间距*/
    padding: 10px;
    /*向外扩散10像素的阴影*/
    box-shadow: 0 0 10px #ddd;
    /*水平居中*/
    position:absolute;
    /*偏移上一层的50%*/
    left: 50%;
    /*移动自身的50%*/
    transform: translate(-50%,-50%);

    img{
      width: 100%;
      height: 100%;
      /*圆角*/
      border-radius: 50%;
      background-color: #eee;
    }
  }

  /*移动form表单，置于底部对齐*/
  .login_form{
    /*底部对齐*/
    position:absolute;
    bottom:0;
    /*横向100%*/
    width: 100%;
    /*左右空20像素*/
    padding: 0 20px;
    /*右侧超出，转换成border-box*/
    box-sizing: border-box;
  }
  /*右对齐*/
  .btns{
    display:flex;
    justify-content:flex-end;
  }
}
</style>
