<template>
  <div class="home">
    <!-- 제목 -->
    <div class="wrapper">
      <!-- <div class="titlebox"> -->
        <!-- <h1 class="title" data-heading="Cobit">Cobit</h1> -->
        <!-- <h2>놀면서 코딩 습관 + 논리력 기르기</h2>         -->
      <!-- </div> -->
      <div class="btnsbox">
        <button class="learn-more" @click="kakaoLogin" v-if="!isUser">로그인하기</button>
        <button class="learn-more" @click="kakaoLogout" v-else>로그아웃하기</button>
        <router-link to="/mypage">
          <button v-if="!isUser" class="learn-more" style="float: right">체험하기</button>
          <button v-else class="learn-more" style="float: right">입장하기</button>
        </router-link>
      </div>
    </div>
    
    
  </div>
</template>

<script>
import "../css/home.scss"
import axios from 'axios'
import store from '../vuex/store'
// import VueCookies from 'vue-cookies'


export default {
  name: 'Home',
  data(){
    return {
      userInfo: store.state.kakaoUserInfo,
      isUser: false,
    }
  },
  watch: {
    userInfo() {
      this.checkUser();
    }
  },
  mounted() {
    this.checkUser();
  },
  methods: {
    kakaoLogin() {
        window.Kakao.Auth.loginForm({
            success: this.GetMe,
        });
    },
    GetMe(authObj){
        // console.log(authObj.access_token);
        this.$cookies.set('access_token', authObj.access_token);
        this.$cookies.set('refresh_token', authObj.refresh_token);
        // VueCookies.set('auth-token', authObj.access_token);
        window.Kakao.API.request({
            url:'/v2/user/me',
            success : res => {
                const kakao_account = res.kakao_account;
                const userInfo = {
                    nickname : kakao_account.profile.nickname,
                    email : kakao_account.email,
                    // pw : authObj.access_token,
                }
                // console.log(userInfo);
                // // console.log(kakao_account);
                axios.post(`https://k3b102.p.ssafy.io:9999/cobit/user`,{
                    email : userInfo.email,
                    nickname : userInfo.nickname,
                })
                .then(res => {
                  // console.log(res);
                  store.commit('setKakaoUserInfo', res.data);
                  this.$router.push("/mypage");
                })
                  
            },
        })
    },
    checkUser() {
      if(this.$cookies.get('access_token')){
        this.isUser = true;
      } else {
        this.isUser = false;
      }
    },
    kakaoLogout() {
      this.$cookies.remove('access_token')
      store.commit('delKakaouserInfo');
      this.userInfo = ''

    }
  },
  
}
</script>

<style scoped>
.title {
  background: linear-gradient(0deg, #bee232 15%, rgb(139, 201, 105) 35%,rgb(94, 178, 145) 50%, rgb(39, 149, 194) 65%);
  -webkit-background-clip: text;
  -webkit-text-stroke: 25px transparent;
  color: white;
}
</style>