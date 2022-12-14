<!--
  변수 바꾸기 완료
  추가 사항
  1. Deadline 추가 하기
  2. 재택 근무 여부 추가하가
  overtimeDetail 추가하기
-->
<template>
  <div class="relative">
    <div class="modal bg-gray-700 rounded-lg absolute">
      <div class="modal-title bg-emerald-500 rounded-t-lg text-gray-200 flex">
        <div><p class="ml-3 mt-3">실적</p></div>
        <div class="grow"></div>
        <button @click="$emit('closeModal')" style="width: 30px; height: 30px;" class="hover:bg-rose-500 active:bg-rose-600 focus:outline-none rounded mr-5 mt-3"><font-awesome-icon icon="fa-solid fa-xmark" /></button>
      </div>

      <!--상단 근로 시간 표시 부분-->
      <div class="modal-total-time bg-gray-600 mt-3 pt-3 rounded-md" style="margin-top:10px">

      <div class="flex text-gray-200">
        <div class="ml-3">{{perfDay}}</div>
        <div class="ml-3 mr-3"><p>{{ selectDate }}</p></div>
        <div v-if="workFromHome()" class="bg-sky-300 w-16 h-6 rounded-md">재택근무</div>
        <div class="grow"></div>
        <!--시작 시간-->
        <div class="text-gray-200 ml-3 mr-3 text-lg">
          <p class="">{{ DayWorkTime }}</p>
        </div>
        <div class="mr-4"><p>총 <span class="text-rose-400">{{ oneDayInfo[0].dayHour }}</span>시간</p></div>
      </div>
      <div class="flex mt-2 ml-3 text-gray-200">
        <div class="text-sm mt-1"><p><span class="text-rose-400">12:00 ~ 13:00</span> 시간은 <span class="text-sky-400">점심시간(휴게시간)</span>으로 계산 됩니다. 
          <span class="text-rose-400">단, 휴일 및 휴일은 4시간당, 30분씩 휴게시간이 제외되어 다빈치에 반영됩니다.</span></p></div>
          <div class="grow"></div>
          <div class="mr-4">휴게시간 : <span class="text-sky-400">-1</span> 시간</div>
      </div>
     </div>


     <div class="flex">
      <div class="grow"></div>
        <div class="text-gray-200 flex mr-8 mt-4 text-xs">
                <div class="flex h-6">
                    <div class="mr-2 w-8 bg-emerald-500 rounded-xl"><p class="mt-1">확정</p></div>
                    <div><p class="mt-1">확정</p></div>
                </div>
                <div class="flex ml-3 h-6">
                    <div class="mr-2 w-8 bg-sky-500 rounded-xl"><p class="mt-1">PM</p></div>
                    <div><p class="mt-1">PM승인</p></div>
                </div>
                <div class="flex ml-3 h-6">
                    <div class="mr-2 w-8 bg-fuchsia-500 rounded-xl"><p class="mt-1">팀장</p></div>
                    <div><p class="mt-1">팀장승인</p></div>
                </div>
                <div class="flex ml-3 h-6">
                    <div class="mr-2 w-10 bg-indigo-500 rounded-xl"><p class="mt-1">사업부</p></div>
                    <div><p class="mt-1">사업부승인</p></div>
                </div>
                <div class="flex ml-3 h-6">
                    <div class="mr-2 w-8 bg-rose-500 rounded-xl"><p class="mt-1">마감</p></div>
                    <div><p class="mt-1">마감</p></div>
                </div>
            </div>
        </div>
          <!-- 프로젝트 작성 란-->
          <div class="text-gray-200">
            <div class="mt-4 perf-box rounded">
              <div class="flex mb-2">
                <div class="ml-2 border-l border-emerald-400 ml-3"><p class="ml-3">실적업무</p></div>
                <div class="grow"></div><!--여기다가 그거 확정 넣어야함-->
              </div>
              <!-- 데이터 반복 구간-->
              <div class="border-2 modal-content rounded" style="width: 99%; margin-bottom:10px" v-for="(data,index) in oneDayInfo">
                <div class="flex">
                  <div class=" mt-3 ml-3 pr-3 border-r-2"><p>{{ data.codeMainNm }}</p></div>
                  <div class="mt-3 ml-3"><p>{{ data.codeSubNm }}</p></div>
                  <div class="grow"></div>
                  <div v-if="confirm[index]" class="mt-2 h-7 w-11 mr-2 w-8 bg-emerald-500 rounded-xl"><p class="mt-0.5">확정</p></div>
                  <div v-if="approve[index]" class="mt-2 h-7 w-11 mr-2 w-8 bg-fuchsia-500 rounded-xl"><p class="mt-0.5">팀장</p></div>
                  <div v-if="ended[index]" class="mt-2 h-7 w-11 mr-2 w-8 bg-rose-500 rounded-xl"><p class="mt-0.5">마감</p></div>
                </div>
                <div class="flex">
                  <div><p class="mt-3 ml-3">{{ data.worDetail }}</p></div>
                  <div class="grow"></div>
                  <div style="width: 80px;" class="mt-3 mr-3 bg-gray-500 w-16 rounded">시간 : {{ data.taskHour }}</div>
                  <div class="mt-3 mr-3 bg-gray-500 w-28 rounded">{{ taskHour[index] }}</div>
                </div>
              </div>
              <!-- 데이터 반복 구간 끝-->
            </div>
            <div>

          </div>
        </div>
        <div class="flex text-gray-200 mt-10 pb-10">
          <div class="grow"></div>
          <button v-if="showEditBtn" @click="$emit('showEditModal')" class="bg-yellow-500 mr-4 w-10 hover:bg-yellow-600 active:bg-yellow-700 focus:outline-none rounded">수정</button>
          <button @click="ConfirmData()" class="bg-emerald-500 w-10 hover:bg-emerald-600 active:bg-emerald-700 focus:outline-none rounded">확정</button>
          <div class="grow"></div>
        </div>  

    </div>
  </div>


</template>
<script>
import { library } from '@fortawesome/fontawesome-svg-core'
import axios from "axios";
/* add some free styles */
import {faXmark} from '@fortawesome/free-solid-svg-icons'
import {faPlus} from '@fortawesome/free-solid-svg-icons'

/* add each imported icon to the library */
library.add(faXmark, faPlus)

export default {
  name: 'PerfDetail',
  props:{
    oneDayInfo: Object,
    DayWorkTime: String,
  },
  mounted() {
    this.partHour();
    this.DistinguishDetailStatus()
    this.calDay();
    this.IsDeadline();
  },
  // status ->  1 -  저장, 2 - 확정, 3 - 팀장, 4 - 마감
  // enroll ->  1 - 저장, 2 - 확정
  data(){
    return{
      //axios 통신 변수
      responseCode: 0,
      backMessage: '',


      TaskNum : 0,
      selectDate: '',
      DayHour : '',
      taskHour: [],
      confirm: [],
      approve: [],
      ended: [],
      perfDay: '',
      showEditBtn: true,

    }
  },
  methods:{
    //------------------------------------ AXIOS -------------------------------------------
    async ConfirmData(){

      const perfConfirmDay = this.oneDayInfo[0].perfDay
      console.log(perfConfirmDay)

      //axios 통신
      await axios.patch('/api/performances/confirm',{},{
        params:{
          day: perfConfirmDay
        }
      }, {withCredentials: true})
          .then((res)=>{
            this.responseCode = res.data.code;
            this.backMessage = res.data.message;
            if(this.responseCode === 1000){
              alert('확정 처리 되었습니다.');
              this.$router.go(0)
            }else if(res.data.code === 5006){
              alert(this.backMessage);
              this.logout();
            }else{
              alert(this.backMessage)
              this.$router.push('/performance');
            } //로그인이 아닌 경우는 이곳에서 else if로 튕겨주기
          })
          .catch((res)=>{
            console.log(res)
          })
    },
     //------------------------------------ AXIOS -------------------------------------------
    calDay(){
      var perfDay = String(this.oneDayInfo[0].perfDay)
      this.perfDay = [perfDay.slice(0,4),'년 ',perfDay.slice(4,6),'월 ',perfDay.slice(6,8),'일'].join('')
    },
    partHour(){
      this.TaskNum = this.oneDayInfo.length;
      console.log(this.TaskNum)
      for(var i = 0;i<this.TaskNum;i++){
        var startTask = this.oneDayInfo[i].startedHour;
        var endTask = this.oneDayInfo[i].endedHour;

        this.taskHour[i] = [startTask.slice(0,2),':',startTask.slice(2,4),' ~ ',endTask.slice(0,2),':',endTask.slice(2,4)].join('');
      }
    },
    DistinguishDetailStatus(){
      this.TaskNum = this.oneDayInfo.length;
      for(var i = 0;i<this.TaskNum;i++) {
        if (this.oneDayInfo[i].signStatus === '2') {
          this.confirm[i] = true;
          this.approve[i] = false;
        }else if(this.oneDayInfo[i].signStatus === '3') {
          this.confirm[i] = false;
          this.approve[i] = true;
        }else{
          this.confirm[i] = false;
          this.approve[i] = false;
        }
        if(this.oneDayInfo[i].isDeadline === '1'){
          this.confirm[i] = false;
          this.approve[i] = false;
          this.ended[i] = true;
        }else{
          this.ended[i] = false;
        }

      }
    },
    IsDeadline(){
      console.log(this.oneDayInfo[0].signStatus)
      if(this.oneDayInfo[0].isDeadline === '1' || this.oneDayInfo[0].signStatus === '3'){
        this.showEditBtn = false;
      }
    },
    workFromHome(){
      if(this.oneDayInfo[0].wfhYn === '0'){
        return false;
      }else{
        return true;
      }
    },
    logout(){
      localStorage.setItem('memberId', '0')
      localStorage.setItem('memberNm','No');
      localStorage.setItem('grade','GEUST');
      localStorage.setItem('deptNm', '무소속');
      this.$router.push('/')
    },



  },
  components: {
  }
}
</script>

<style>

.modal{
  width: 1200px;
  height: 500px;
  left: 300px;
  top: 100px;
  z-index: 1;
  overflow: scroll;
}
.modal-title{
  width: 100%;
  height: 50px;
}
.modal-total-time{
  width: 1150px;
  height: 80px;
  margin: auto;
}
.modal-content{
  margin: auto;
  width: 1150px;
  height: 100px;
}
.perf-box{
  width: 1150px;
  margin: 10px 20px 0 23px;
  padding: 10px;
  background-color: rgb(75 85 99);
}

</style>
