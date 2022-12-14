<template>
  <!-- 상단 메뉴 바-->
  <div class="bg-gray-800 relative ">

    <div class="flex" style="width:100vw;">
      <div class="bg-emerald-800 w-44 h-8">
        <router-link to="/">
          <img class="mt-2 ml-3 w-40" src="../public/hansol-timereport-logo.png">
        </router-link>
      </div>
      <div class="grow bg-emerald-800 flex ">
        <div class="grow"></div>
        <div v-if="is_logined" style="height: 23px; width: 100px;" class="flex-none rounded w-40 mt-1 bg-emerald-600 text-gray-200 mr-10 shadow">
          <p>{{ memberNm }} 님</p>
        </div>
        <div v-if="is_logined" style="height: 23px; width: 100px;" class="flex-none rounded w-40 mt-1 bg-emerald-600 text-gray-200 mr-10 shadow">
          <button @click="Logout()">Logout</button>
        </div>
      </div>
    </div>
    <!--내용 부분-->
    <div class="flex" style="height: 800px">
      <div class="flex-none w-44 bg-emerald-800 text-left text-gray-300 ">
        <div v-if="is_employee"  class="mt-4  mb-1 mt-1 side-menu-bar">
          <div class="text-lg bg-emerald-700 pl-2 shadow rounded-t side-menu-bar">
            <button @click="smallMenuToggle('manour')">맨아워관리</button>
          </div>
          <!--맨아워관리 폴더-->
          
            <ul v-if="LM_manoutToggle" class="text-left text-gray-300 bg-emerald-600 pb-1 rounded-b shadow">
              <div>
                <div class=""><button @click="smallMenuToggle('plan')" class="text-base ml-2">계획</button></div>
                  <ul v-if="LM_planToggle" class="bg-emerald-500 ml-3 text-sm rounded shadow mr-1">
                    <router-link to="/plan"><button class="ml-2 mt-0.5">계획관리</button></router-link>
                  </ul>
              </div>

              <div>
                <div><button @click="smallMenuToggle('performance')" class="text-base ml-2">실적</button></div>
                <ul v-if="LM_performanceToggle" class="bg-emerald-500 ml-3 text-sm rounded shadow mr-1">
                  <router-link to="/performance"><button class="ml-2 mt-0.5 ">실적관리</button></router-link>
                </ul>
              </div>

            </ul>
          </div>
        <!--맨아워관리 폴더-->

        <!--프로젝트 관리 폴더-->
          <div v-if="is_employee" class="mb-1 side-menu-bar">
            <div class="text-lg bg-emerald-700 shadow pl-2 rounded-t">
              <button @click="smallMenuToggle('project')">프로젝트 관리</button>
            </div>
            <div class="bg-emerald-600 rounded-b pb-1 pt-0.5">
              <ul v-if="LM_projectToggle" class="text-sm rounded bg-emerald-500 ml-3 mt-0.5 shadow mr-1">
                <router-link to="/project"><button class="ml-2 mt-0.5">내 프로젝트</button></router-link>
              </ul>
            </div>
          </div>
        <!--프로젝트 관리 폴더-->

        <!--승인 관리 폴더-->
          <div v-if="is_leader" class="mb-1 side-menu-bar">
            <div  class="text-lg bg-emerald-700 shadow rounded-t pl-2">
              <button @click="smallMenuToggle('approve')">승인관리</button>
            </div>
            <div class="bg-emerald-600 rounded-b shadow pb-1 pt-0.5">
              <ul v-if="LM_approveToggle" >
                <li class="text-sm rounded bg-emerald-500 ml-3 mt-1 shadow mr-1"><router-link to="/confirm"><button class="ml-2 mt-0.5">실적 팀장 승인</button></router-link></li>
                <li class="text-sm rounded bg-emerald-500 ml-3 mt-1 shadow mr-1"><router-link to="/non-enter"><button class="ml-2 mt-0.5">미입력자 관리</button></router-link></li>
                <li v-if="is_manager" class="text-sm rounded bg-emerald-500 ml-3 mt-1 shadow mr-1"><router-link to="/deadline" ><button class="ml-2 mt-0.5">마감 관리</button></router-link></li>
              </ul>
            </div>

          </div>
          
          <!--승인 관리 폴더-->
          <!--관리자 폴더-->
          <div v-if="is_manager" class="mb-1  side-menu-bar">
            <div class="text-lg bg-emerald-700 shadow rounded-t pl-2">
              <button @click="smallMenuToggle('manager')">관리자</button>
            </div>
            <div class="bg-emerald-600 rounded-b pb-1 shadow pt-0.5">
              <ul v-if="LM_managerToggle" class="text-sm rounded bg-emerald-500 ml-3 mt-1 shadow mr-1">
                <router-link to="/admin/project"><button class="ml-2 mt-0.5">프로젝트 관리</button></router-link>
              </ul>
            </div>
          </div>
          
          <!--관리자 폴더-->
      </div>
      <!--검색 부분 -->
      <div class="grow bg-gray-800	rounded-lg ml-2 mt-5" style="width:100vw;">
          <router-view/>
      </div>
    </div>
  </div>


</template>
<script>
import { library } from '@fortawesome/fontawesome-svg-core'


/* add some free styles */
import {faXmark} from '@fortawesome/free-solid-svg-icons'
import {faPlus} from '@fortawesome/free-solid-svg-icons'
import axios from 'axios'

/* add each imported icon to the library */
library.add( faXmark, faPlus)


export default {
  name: 'App',
  mounted() {
    this.Grade();
  },
  emits: ['Grade', 'Logout'],
  data(){
    return{
      LM_manoutToggle : true,
      LM_planToggle : true,
      LM_performanceToggle : true,
      LM_projectToggle: true,
      LM_approveToggle:true,
      LM_managerToggle:true,
      preUrl: '',
      pageIndex: 0,
      memberNm : '',
      grade: '',
      is_logined: false,
      is_employee : false,
      is_leader : false,
      is_manager : false,
    }
  },
  watch:{
    $route(to, from){
      if(to.path != from.path){
        this.Grade()
      }

    }
  },
  methods:{
    Grade(){
      this.grade = localStorage.getItem('grade')
      this.memberNm = localStorage.getItem('memberNm')
      switch(this.grade){
        case 'ADMIN':
          this.is_manager = true;
          this.is_leader = true;
          this.is_employee = true;
          this.is_logined = true;
          break;
        case 'MANAGER':
          this.is_manager = false;
          this.is_leader = true;
          this.is_employee = true;
          this.is_logined = true;
          break;
        case 'USER':
          this.is_manager = false;
          this.is_leader = false;
          this.is_employee = true;
          this.is_logined = true;
          break;
        default:
          this.is_manager = false;
          this.is_leader = false;
          this.is_employee = false;
          this.is_logined = false;
      }

    },
    async Logout(){
      localStorage.setItem('memberId', '0');
      localStorage.setItem('memberNm','No');
      localStorage.setItem('grade','GEUST');
      localStorage.setItem('deptNm', '무소속');

      this.Grade()
      var memberId = localStorage.getItem('memberId')
      axios.post('/api/members/logout',memberId,{withCredentials: true})
      .then((res)=>{
        alert('로그아웃 되었습니다.')
      })
      .catch((res)=>{
        console.error(res)
      })
      this.$router.push('/')
      
    },
    smallMenuToggle(menuName){
      switch(menuName){
        case 'manour':
          if(this.LM_manoutToggle===true){
            this.LM_manoutToggle=false;
          }else{
            this.LM_manoutToggle=true;
          }
          console.log('manour 성공')
          break;
        case 'plan':
          if(this.LM_planToggle===true){
            this.LM_planToggle=false;
          }else{
            this.LM_planToggle=true;
          }
          console.log('plan 성공')
          break;

        case 'performance':
          if(this.LM_performanceToggle === true){
            this.LM_performanceToggle = false;
          }else{
            this.LM_performanceToggle = true;
          }
          console.log('performance 성공')
          break;
          
        case 'project':
          if(this.LM_projectToggle === true){
            this.LM_projectToggle = false;
          }else{
            this.LM_projectToggle = true;
          }
          console.log('project 성공')
          break;

        case 'approve':
          if(this.LM_approveToggle === true){
            this.LM_approveToggle = false;
          }else{
            this.LM_approveToggle = true;
          }
          console.log('approve 성공')
          break;

        case 'manager':
          if(this.LM_managerToggle === true){
            this.LM_managerToggle = false;
          }else{
            this.LM_managerToggle = true;
          }
          console.log('manager 성공')
          break;

        default:
          console.log('규격 외의 것이 들어옴');

      }
    },
  },
  components: {
  }
}
</script>

<style>
#app {
   width: 1200px;
  height: 800px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.side-menu-bar{
  width: 160px;
  margin: auto;
}
.shadow{
  box-shadow: 20px 20px 30px 10px black;
}


</style>
