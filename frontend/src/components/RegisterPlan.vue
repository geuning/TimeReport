<!--
추가 사항
  1. 데이터 바인딩을 통해서 반복문 돌리기, ajax 사용하기
  2. 최대 height를 지정하고 overflow: scroll 적용하기(때에 따라서 무한 스크롤로 돌려보기)

개선 사항
  1. 위에 확정 표기 지우고 다시 재정비하기
  2. 추가 버튼 boarder 삭제하기
-->
<template>
  <!--검색 부분 -->
  <div class="grow bg-gray-800 rounded-lg ml-2 mt-5 text-white">
    <div class="rounded-lg bg-gray-600	ml-5 flex" style="height:50px; max-width:1230px">
      <div class="h-8 place-self-center flex mt-2 ml-3">
        <p>계획 정보 : {{ week }}</p><!--오늘 데이터를 전달 받아야함 props로 해결하면 될듯-->
      </div>
      <div class="grow"></div>
      <div class="mt-3 mr-3"><p>평일 {{ WeekWorkTime }}시간</p></div>
    </div>
    <div class="flex mt-4" style="width: 1270px">
      <div class="grow"></div>
      <button @click="removeTaskBox" style="width: 25px; height: 25px" class="text-lg pr-0.3 bg-rose-500 rounded mr-3 hover:bg-rose-600 active:bg-rose-700 focus:outline-none"><font-awesome-icon icon="fa-solid fa-xmark" /></button>
      <button @click="addTaskBox" style="width: 25px; height: 25px" class="text-lg rounded bg-teal-500 pr-0.3 mr-5 hover:bg-teal-600 active:bg-teal-700 focus:outline-none"><font-awesome-icon icon="fa-solid fa-plus"/></button>
    </div>
    <!-- 컨텐츠 부분 -->
    <div class="ml-5 mt-5 text-white" style="width: 1294px">
      <!-- 표 만들기 -->

      <!-- 실제 데이터 인풋 -->
      <div v-for="(Task,index) in viewData" class="bg-gray-600 mr-16 rounded-xl mb-4" style="height: 120px">
        <div class="flex">
          <div class="mr-3 pt-4 ml-3 border-r" style="width:200px; height: 120px;">
            <p class="mr-3">{{ Task.Week }}</p>
            <div class="flex mt-2 ml-8">
              <div class="text-black mr-1">
                <select @change="onChangeStartHour_v2($event)" v-model="StartWorkTime">
                  <option  v-for="start in startTime" :value="start.value">{{ start.text}}</option>
                </select>
              </div>
              <span>~</span>
              <div class="ml-1">
                {{ EndWorkTime }}
              </div>
            </div>
            <div class="mt-2 mr-4 flex">
              <p class="ml-10">재택근무</p>
              <div class="ml-3"><input type="checkbox"></div>
            </div>
          </div>
            <div class="ml-3 mt-5 pt-2" style="height:100px; width: 800px;">
              <div class="flex">
                <div class="mr-3 text-black">
                  <select v-model="Task.groupSubId" @change="onChangeSelectMain($event, index)" style="width: 300px">
                    <option v-for="main in mainGroup" :value="main.groupSubId">{{ main.codeMainNm }}</option>
                  </select>
                </div>
                <div class="text-black" style="width: 450px">
                  <select  v-model="Task.codeId" @change="onChangeSelectSub($event, index)" style="width: 450px">
                    <option v-for="subtask in subTaskCopy[index]" :value="subtask.codeId">{{ subtask.codeSubNm }}</option>
                  </select>
                </div>
              </div>
              <div style="width: 400px" class="text-black mt-3">
                <input style="width: 400px" type="input">
              </div>
            </div>

            <div class="grow"></div>
            <div class="mr-4 mt-2">
              <div class="flex mt-5">
                <div>
                  <p>시간 :</p>
                </div>
                <div class="pb-1">
                  <input @change="onChangeTaskHour_v2($event,index)" min="1" v-model="Task.taskHour" style="width: 40px; height: 20px" class="ml-2 pl-3 text-black rounded" type="number">
                </div>
              </div>
              <div class="mt-4 mr-4 ">
                <p>{{ renderTaskStartHour[index] }} ~ {{ renderTaskEndHour[index] }}</p>
              </div>
            </div>
        </div>
      </div>

      <div class="flex mt-4">
        <div class="grow"></div>
        <router-link :to="{name: '/registerplanweek' , params: {selectWeek: this.$route.params.selectWeek} }"><button style="width: 120px" class="text-white bg-sky-600 rounded ml-4">주간 별 계획</button></router-link>
        <router-link :to="{name: '/registerplandetail', params: {selectWeek: this.$route.params.selectWeek} }"><button style="width: 120px" class="text-white bg-sky-600 rounded ml-4">요일 별 계획</button></router-link>
        <button @click="PostData('0')" style="width: 50px" class="ml-4 text-white bg-gray-500 rounded">저장</button>
        <button @click="PostData('1')" style="width: 50px" class="ml-4 text-white bg-emerald-500 rounded">등록</button>
        <div class="grow"></div>
      </div>

    </div>


  </div>

</template>
<script>

import registerTask from '../assets/task.json';
import registerProject from '../assets/project.json'
import axios from "axios";

export default {
  name: 'RegisterPlan',
  async mounted() {
    await this.GetWeekData();
    this.SetStartWeek();
    await this.getTask();
    await this.getMyProject();

    this.ClassifyTaskType();
    this.ClassifyProject();
    this.MountSelect();
    this.SetTaskHour_v2();
    console.log(this.$route.params.selectWeek);
  },
  data() {
    return {
      //받는 데이터
      taskType: [],
      project: [],
      
      searchWeekly:[],
      startDate : 0,
      week: '',


      //보내는 데이터
      sendData: [],

      //화면에 뿌려주는 변수
      WeekWorkTime: 0,
      viewData: [{
        Week: '',
        seq: 1,
        taskHour: 8,
        dayHour: 8,
        started_hour: "0900",
        endedHour: "1800",
        groupMainId: "TR001",
        groupSubId: "ZDUM1",
        codeId: "Z001",
        codeMainNm: "주업무",
        codeSubNm: "R&D 및 내부 PJT (NonPJT코드) - 시장조사, 분석, 계획, 설계/개발/테스트/이행",
        workDetail: "주업무",
      }
      ],

      //시간에 대한 변수
      StartWorkTime: "",
      EndWorkTime: [],

      //각 TaskTime
      planStartHour : "",
      taskStartHour : [],
      taskEndHour : [],
      taskTime :[],
      renderTaskStartHour:[],
      renderTaskEndHour:[],
      totalDayWorkTime: 8,

      startTime: [
        {text: "8:00", value: "0800"},
        {text: "8:30", value: "0830"},
        {text: "9:00", value: "0900"},
        {text: "9:30", value: "0930"},
        {text: "10:00", value: "1000"}
      ],
      endTime: [
        {text: "16:00", value: "1600"},
        {text: "16:30", value: "1630"},
        {text: "17:00", value: "1700"},
        {text: "17:30", value: "0930"},
        {text: "18:00", value: "1800"},
        {text: "18:30", value: "1830"},
        {text: "19:00", value: "1900"},
        {text: "19:30", value: "1930"},
        {text: "20:00", value: "2000"}
      ],

      startWorkTimeView: [],
      endWorkTimeView: [],


      // 대분류, 소 분류 -> 따로 데이터를 받아오는 값
      mainGroup: [],
      subTask: [],
      subTaskCopy: [],
      subProejct: [],
    }
  },
  props:{
    selectWeek:{
      type: String,
    } 
  },
  methods: {
    //----------------------------------------------AXIOS GET 통신 ----------------------------------------
    async getTask(){
      var testtask
      await axios.get('/api/tasks',{withCredentials:true})
      .then((res)=>{
        if(res.data.code === 1000){
          this.taskType = res.data.result;
        }else{
          alert(this.backMessage);
          this.logout();
        }
      })
      .catch((res)=>{
        console.error(res)
      })
    },
    async getMyProject(){
      await axios.get('/api/projects/me',{withCredentials:true})
      .then((res)=>{
        if(res.data.code === 1000){
          this.project = res.data.result;
        }else{
           alert(this.backMessage);
          this.logout();
        }
      })
      .catch((res)=>{
        console.error(res);
      })
    },
    async GetWeekData(){
        await axios.get('/api/biweekly',{withCredentials: true})
            .then((res)=>{
              if(res.data.code === 1000){
                this.searchWeekly = res.data.result.twoWeeksDtos;
                // this.startDate = res.data.result.startOfWeek;
                console.log('시작날짜',this.startDate);
                console.log('주간데이터', this.searchWeekly);
              }else{
                alert(this.backMessage);
                this.logout();
              }
            })
            .catch((res)=>{
              console.error(res);
            })
      },
    SetStartWeek(){ 
      this.startDate = this.$route.params.selectWeek;
      for(var i = 0;i<this.searchWeekly.length;i++){
        if(this.searchWeekly[i].fromdt === this.startDate){
          this.week = this.searchWeekly[i].content;
          break;
        }
      }
      this.viewData[0].Week = this.week;
    },
    MountSelect() { // subTask 완성
      //MainTask - 완성
      //이거 혹시 sendTaskData가 여러개라면, 여러개를 뿌려줘야하는데
      for(var j = 0;j<this.viewData.length;j++){
        this.subTaskCopy[j] = [...this.subTask];
      }
      


      for(var i = 0;i<this.viewData.length; i++){
        for(var j = 0; j<this.subTaskCopy[i].length; j++){

          if(this.subTaskCopy[i][j].groupSubId != this.viewData[i].groupSubId){
            this.subTaskCopy[i].splice(j,1)
            j--;
          }
        }
      }
      
    },

    ClassifyTaskType() {//완성
      for (var i = 0; i < this.taskType.length; i++) {
        if (this.taskType[i].groupId === 'TR001') {
          this.mainGroup.push({
            groupMainId: this.taskType[i].groupId,
            groupSubId: this.taskType[i].codeId,
            codeMainNm: this.taskType[i].codeNm
          });
        } else {
          this.subTask.push({
            groupSubId: this.taskType[i].groupId,
            codeId: this.taskType[i].codeId,
            codeSubNm: this.taskType[i].codeNm
          });
        }
      }
    },
    ClassifyProject() {
      for (var i = 0; i < this.project.length; i++) {
        this.mainGroup.push({
          groupMainId: this.project[i].projectCode,
          groupSubId: 'TR002',
          codeMainNm: this.project[i].projectNm
        });
      }
    },
    addTaskBox() {
      this.viewData.push(
          {
            Week: this.week,
            seq: 1,
            taskHour: 8,
            dayHour: 8,
            started_hour: "0900",
            endedHour: "1200",
            groupMainId: "TR001",
            groupSubId: "ZDUM1",
            codeId: "Z001",
            codeMainNm: "주업무",
            codeSubNm: "R&D 및 내부 PJT (NonPJT코드) - 시장조사, 분석, 계획, 설계/개발/테스트/이행",
            workDetail: "주업무",
            enrollYn : "0",
          }
      );
     
    },
    onChangeSelectMain(event, index) {
      var sub = event.target.value

      this.viewData[index].groupSubId = sub;

      for (var i = 0; i < this.mainGroup.length; i++) {
        if (sub === this.mainGroup[i].groupSubId) {
          this.viewData[index].groupMainId = this.mainGroup[i].groupMainId;
          this.viewData[index].codeMainNm = this.mainGroup[i].codeMainNm;
          break;
        }
      }
      this.subTaskCopy[index] = [...this.subTask];


      for (var i = 0; i < this.subTaskCopy[index].length; i++) {
        if (sub != this.subTaskCopy[index][i].groupSubId) {
          this.subTaskCopy[index].splice(i, 1);
          i--;
        }
      }
    },
    onChangeSelectSub(event, index) {

      var code = event.target.value;

      this.viewData[index].codeId = code;

      for (var i = 0; i < this.subTask.length; i++) {
        if (this.subTask[i].codeId === code) {
          this.viewData[index].codeSubNm = this.subTask[i].codeSubNm;
        }
      }
    },
    SetTaskHour_v2(){
      for(var i = 0;i<this.viewData.length;i++){
        this.taskStartHour.push(Number(this.viewData[i].started_hour))
        this.taskEndHour.push(Number(this.viewData[i].endedHour))
        this.taskTime.push(this.viewData[i].taskHour)
      }
      this.totalDayWorkTime = this.viewData[0].dayHour;
      this.WeekWorkTime = this.taskTime[0]*10
      this.RenderTime_v2()
      
    },
    onChangeStartHour_v2(e){
      var daystart = e.target.value;
      daystart = Number(daystart);
      this.taskStartHour[0] = daystart;
      this.calTime_v2();
    },
    onChangeTaskHour_v2(e,index){

      this.taskTime[index] = Number(e.target.value);
      var sumTime = 0
      for(var i = 0;i<this.taskTime.length;i++){
        sumTime += this.taskTime[i]
      }
      this.totalDayWorkTime = sumTime

      console.log(index)
      var weekSum = 0
      for(var i = 0;i<this.taskTime.length;i++){
        weekSum += this.taskTime[i]*10
      }
      this.WeekWorkTime = weekSum
      this.calTime_v2();

    },//시간 계산을 해주는 메서드 -> 시간 계산이 필요할 때 활용하면 된다.
    calTime_v2(){
      //12시 이전에 계획이 끝나는 경우
      var time = 0;
      //12시 이후에 계획이 끝나는 경우
      var lunch = 0;
      var lunchTime = 100;
      for(var i = 0;i<this.taskTime.length;i++){
        time = this.taskStartHour[i]+(this.taskTime[i]*100);
        if(lunch == 0 && time > 1200){
          time += lunchTime;
          this.taskEndHour[i] = time
          lunch++;
        }else if(time === 1200 && lunch === 0){
          this.taskEndHour[i] = time;
          time += lunchTime;
          lunch++;
        }else{//12시 이전이거나 혹은 12시 이후 일때
          this.taskEndHour[i] = time;
        }
        if(i < this.taskTime.length-1){
          this.taskStartHour[i+1] = time;
        }

      }

      this.RenderTime_v2();
    },
    RenderTime_v2(){
      for(var i = 0;i<this.taskStartHour.length;i++){
        var start = this.taskStartHour[i]
        var end = this.taskEndHour[i]

        if(start/100 < 10){
          start = '0'+String(start);
        }else {
          start = String(start);
        }
        if(end/100 < 10){
          end = '0'+String(end)
        }else{
          end = String(end);
        }
        this.renderTaskStartHour[i] = [start.slice(0,2),':',start.slice(2,4)].join('');
        this.renderTaskEndHour[i] = [end.slice(0,2),':',end.slice(2,4)].join('');
      }

      var start =  this.taskStartHour[0]
      this.StartWorkTime = '0'+String(start)

      var index = this.taskStartHour.length-1

      var EndTime = this.taskEndHour[index];

      if(EndTime > 2400){
        EndTime -= 2400;          
        this.taskEndHour[index] = EndTime;
        if(EndTime === 30){
          var EndDay = '00'+String(EndTime);
        }else{
          var EndDay = '0'+String(EndTime);
        }
      }else{
        var EndDay = String(EndTime);
      }

      this.EndWorkTime = [EndDay.slice(0,2),':',EndDay.slice(2,4)].join('')

    },
    removeTaskBox(){
      if(this.viewData.length > 1){
        this.viewData.pop();
        this.taskStartHour.pop();
        this.taskEndHour.pop();
        this.RenderTime_v2()
      }else{
        alert('삭제할 수 없습니다.');
      }
      if(this.taskTime.length>1){
        this.taskTime.pop()
      }
    },
    async PostData(status){
      for(var i = 0;i<this.viewData.length;i++){
        var stringTaskStartHour = this.taskStartHour[i]
        var stringTaskEndHour = this.taskEndHour[i]
        if(stringTaskStartHour/100 < 10){
          stringTaskStartHour = '0'+String(stringTaskStartHour);
        }else {
          stringTaskStartHour = String(stringTaskStartHour);
        }
        if(stringTaskEndHour/100 < 10){
          stringTaskEndHour = '0'+String(stringTaskEndHour)
        }else {
          stringTaskEndHour = String(stringTaskEndHour);
        }
        this.viewData[i].taskHour = this.taskTime[i];
        this.viewData[i].dayHour = this.totalDayWorkTime;
        this.viewData[i].started_hour = stringTaskStartHour;
        this.viewData[i].endedHour = stringTaskEndHour;
      }
      var index = 0;
      for(var i = 0;i<14;i++){
        for(var j = 0; j<this.viewData.length;j++){
          if(i === 5 || i === 6 || i === 12 || i === 13){
            console.log('주말데이터');
          }else{
            this.sendData[index] = {
              planId: 0,
              planDay: String(Number(this.startDate)+i),
              seq: this.viewData[j].seq,
              taskHour: this.viewData[j].taskHour,
              dayHour: this.viewData[j].dayHour,
              startedHour: this.viewData[j].started_hour,
              endedHour: this.viewData[j].endedHour,
              groupMainId: this.viewData[j].groupMainId,
              groupSubId: this.viewData[j].groupSubId,
              codeId : this.viewData[j].codeId,
              codeMainNm: this.viewData[j].codeMainNm,
              codeSubNm: this.viewData[j].codeSubNm,
              workDetail : this.viewData[j].workDetail,
              wfhYn: "0",
              enrollYn: '0',
              isHoliday :"N"
            }
            index++;  
          }
        }
      }
      console.log(this.sendData)
      if(status === '0'){
        await axios.post('/api/plans/drafts',this.sendData,{withCredentials: true})
            .then((res)=>{
              if(res.data.code === 1000){
                alert('계획이 등록 되었습니다.')
              }else if(res.data.code === 5006){
                alert(this.backMessage);
                this.logout();
              }else{
                alert(res.data.message)
              }
            })
            .catch((res)=>{
              console.error(res);
            })
      }else{
        await axios.patch('/api/plans', this.sendData,{withCredentials:true})
        .then((res)=>{
          if(res.data.code === 1000){
            alert('계획이 등록 되었습니다.')
            this.$router.go(0)
          }else if(res.data.code === 5006){
                alert(this.backMessage);
                this.logout();
              }else{
                alert(res.data.message)
              }
        })
      }
    },
    logout(){
      localStorage.setItem('memberId', '0')
      localStorage.setItem('memberNm','No');
      localStorage.setItem('grade','GEUST');
      localStorage.setItem('deptNm', '무소속');
      this.$router.push('/')
    },

  }
}
</script>

<style>
#app {
  width: 100vw;
  height: 100vh;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.interval{
  width: 100px;
}
.name-interval{
  width: 100px;
}
.table-size{
  height: 40px;
}
.textover{
  overflow: hidden;
  text-overflow: ellipsis;
}


</style>
