<template>
    <el-container class="home-container">
      <!--头部区域-->
      <el-header>
        <div>
          <img src="../assets/head.png">
          <span>电商后台管理系统</span>
        </div>
        <el-button type="info" @click="logout">退出</el-button>
      </el-header>
      <!--页面主体区-->
      <el-container>
        <!--侧边栏-->
        <el-aside :width="isCollapse ? '64px' : '200px'">
          <!--侧边栏菜单区-->
          <!--收缩按钮-->
          <div class="toggle-button" @click="toggleCollapse">|||</div>
          <!--unique-opened表示一次只能下拉一个菜单;collapse表示是否折叠；collapse-transition关闭折叠动画;
          router表示是否使用 vue-router 的模式，启用该模式会在激活导航时以 index 作为 path 进行路由跳转;
          default-active表示哪个菜单被激活-->
          <el-menu
          background-color="#333744"
          text-color="#fff"
          active-text-color="#409BFF"
          :unique-opened="true"
          :collapse="isCollapse"
          :collapse-transition="false"
          :router="true"
          :default-active="activePath">
          <!--一级菜单-->
          <!--v-for表示一级菜单循环；每一个v-for尽量都提供一个key属性，id是唯一的，所以选id；
          index唯一是避免点其中一个下来菜单，其他的也跟着展开;后面+‘’是使其成为字符串-->
          <el-submenu :index="item.id + ''" v-for="item in menulist" :key="item.id">
            <!--一级菜单模板区-->
            <template slot="title">
              <!--图标-->
              <i :class="iconsObj[item.id]"></i>
              <!--文本-->
              <!--动态绑定名称-->
              <span>{{item.authName}}</span>
            </template>

            <!--二级菜单-->
            <el-menu-item :index="subItem.path + ''" v-for="subItem in item.children" :key="subItem.id" @click="saveNavState(subItem.path)">
              <template slot="title">
                <!--图标-->
                <i class="el-icon-menu"></i>
                <!--文本-->
                <span>{{subItem.authName}}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
        </el-aside>

        <!--右侧内容主体-->
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>

</template>

<script>
  import router from "../router";

  export default {
    name: "Home",
    data(){
      return{
        menulist: [
          {
            id:1,
            authName:'用户管理',
            path:'/User management',
            children:[
              {
                id:11,
                authName:'用户列表',
                path:'/users',
                children:[]
              },
            ]
          },
          {
            id:2,
            authName:'权限管理',
            path:'/Authority management',
            children:[
              {
                id:21,
                authName:'角色列表',
                path:'/roles',
                children:[]
              },
              {
                id:22,
                authName:'权限列表',
                path:'/rights',
                children:[]
              },
            ]
          },
          {
            id:3,
            authName:'商品管理',
            path:'/Commodity management',
            children:[
              {
                id:31,
                authName:'商品列表',
                path:'/Product list',
                children:[]
              },
              {
                id:32,
                authName:'分类参数',
                path:'/Classification parameters',
                children:[]
              },
              {
                id:33,
                authName:'商品分类',
                path:'/Commodity classification',
                children:[]
              },
            ]
          },
          {
            id:4,
            authName:'订单管理',
            path:'/Order management',
            children:[
              {
                id:41,
                authName:'未知1',
                path:'/unknown1',
                children:[]
              },
            ]
          },
          {
            id:5,
            authName:'数据统计',
            path:'/Data statistics',
            children:[
              {
                id:51,
                authName:'未知',
                path:'/unknown2',
                children:[]
              },
            ]
          },
        ],
        //一级菜单前的图标
        iconsObj:{
          //id作为唯一标识key值
          '1':'el-icon-s-custom',
          '2':'el-icon-s-management',
          '3':'el-icon-s-goods',
          '4':'el-icon-s-order',
          '5':'el-icon-s-platform'
        },
        //是否折叠
        isCollapse:false,
        activePath:''
      }
    },

    //从sessionStorage中取出activePath值，实现刷新时还保留activePath的状态
    created(){
      this.activePath = window.sessionStorage.getItem('activePath')
    },

    methods:{
      //退出
      logout(){
        // window.sessionStorage.clear(),
        this.$router.push("/login")
      },
      //点击按钮，切换菜单的折叠与展开
      toggleCollapse(){
        this.isCollapse = !this.isCollapse
      },
      //保存连接的激活状态
      saveNavState(activePath){
        this.activePath = activePath;
        //存到sessionStorage中
        window.sessionStorage.setItem('activePath',activePath)
      }
    }
  }
</script>

<style lang="less" scoped>
  .home-container{
    height: 100%;
    img{
      width: 50px;
      height: 50px;
    }
  }

/*  !*直接用标签名也可,但主体区也叫el-container*!
  .el-container{
    height: 100%;
  }*/

  .el-header{
    background-color: #373d41;
    /*将对象作为弹性伸缩盒*/
    display: flex;
    /*左右贴边对齐*/
    justify-content: space-between;
    /*删除左侧空白*/
    padding-left: 0;
    /*居中对齐弹性盒的各项 <div> 元素,主要是为了退出键*/
    align-items: center;
    /*设置内部字体*/
    color: #fff;
    font-size: 20px;
    div{
      display: flex;
      align-items: center;
      /*文字左侧间距图片15像素*/
      span{
        margin-left: 15px;
      }
    }
  }

  .el-aside{
    background-color: #333744;
    /*menu右边线对齐*/
    .el-menu{
      border-right: none;
    }
  }

  .el-main{
    background-color: #EAEDF1;
  }

  /*文本字体的设置*/
  .toggle-button{
    background-color: #4A5064;
    /*文字大小*/
    font-size: 10px;
    /*行高*/
    line-height: 24px;
    color: #fff;
    /*居中*/
    text-align: center;
    /*将指定的间隔添加到每个文字(包括单词内的每个字母)之后,且不能被应用于一行的开始和结束*/
    /*em单位是相对于font-size来讲的*/
    letter-spacing: 0.2em;
    /*鼠标放上变小手*/
    cursor: pointer;
  }
</style>
