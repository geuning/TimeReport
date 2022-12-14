<!--
추가 사항
  1. 데이터 바인딩을 통해서 반복문 돌리기, ajax 사용하기
  2. 최대 height를 지정하고 overflow: scroll 적용하기(때에 따라서 무한 스크롤로 돌려보기)

개선 사항
  1. 위에 확정 표기 지우고 다시 재정비하기
  2. 추가 버튼 boarder 삭제하기

  viewData가 2차원 배열의 형태로 돌아가게 된다면,
  그에 대한 영향으로 시간에 대한 변수들은 모드 2차원 배열의 형태로 변경되어야한다.
  1차원 배열 -> 2차원 배열
  숫자 -> 1차원 배열로 전향됨


   ------------
   남은 작업,
   add Box, select, close box 작업, sendata작업
   ajax작업

-->
<template>
  <!--검색 부분 -->
  <div style="height: 700px;" class="grow bg-gray-800	rounded-lg ml-2 mt-5 text-gray-200 main-scroll-bar">
    <div class="rounded-lg h-16 bg-gray-600 ml-5 flex" style="max-width:1230px">
      <div class="h-8 place-self-center flex mt-2 ml-3">
        <p>계획 정보 : {{ week }}</p>
      </div>
      <div class="grow"></div>
      <div class="mt-5 mr-3"><p>평일 {{ totalWeekWorkTime }}시간</p></div>
    </div>
    <div style="width: 1250px" class="flex mt-4" >
      <div class="grow"></div>
      <button @click="PostData('1')" style="width: 50px" class="ml-4 text-gray-200 bg-emerald-500 rounded mr-2">등록</button>
      <button @click="PostData('2')" style="width: 50px" class="ml-4 text-gray-200 bg-rose-500 rounded">저장</button>
    </div>
    <!--데이터 반복 시작 부분-->
    <div v-for="(date, dateIndex) in viewData" >
      <div v-if="isHoliday[dateIndex]" class="scroll-bar" style="height: 300px;">
        <div class="flex pt-4 mt-2 pb-2 ml-4 pl-5 bg-gray-700 rounded-t-lg" style="width: 1235px">
          <div class=" border-b-2">
            <p class="mb-1 ml-5 text-xl ">{{ Viewdate[dateIndex] }} <span class="bg-gray-500 pr-1 pl-1 rounded">{{ ViewDay[dateIndex] }}</span>
              <span class="text-teal-500" v-if="dateIndex < 7"> - 1주차</span>
              <span class="text-lime-500" v-if="dateIndex >= 7"> - 2주차</span>
            </p>
          </div>
          <div class="border-b-2 grow"></div>
          <div class="border-b-2 mr-4">
            <button @click="removeTaskBox(dateIndex)" style="width: 25px; height: 25px" class="text-lg pr-0.3 bg-rose-500 rounded mr-3 hover:bg-rose-600 active:bg-rose-700 focus:outline-none"><font-awesome-icon icon="fa-solid fa-xmark" /></button>
            <button @click="addTaskBox(dateIndex)" style="width: 25px; height: 25px" class="text-lg rounded bg-teal-500 pr-0.3 hover:bg-teal-600 active:bg-teal-700 focus:outline-none"><font-awesome-icon icon="fa-solid fa-plus"/></button>
          </div>
        </div>
        <!-- 컨텐츠 부분 -->
        <div class="pl-5 ml-4 pt-5 text-gray-200 sub-scroll-bar bg-gray-700 rounded-b-lg" style="width: 1235px; height: 200px;">
          <!-- 표 만들기 -->

          <div v-for="(Task,index) in viewData[dateIndex]" class="bg-gray-600 mr-4 rounded-xl mb-4" style="height: 120px">

            <div class="flex">
              <div class="mr-3 pt-4 ml-3 border-r" style="width:200px; height: 120px;">
                <div class="flex mt-2 ml-8">
                  <div class="text-black mr-1">
                    <select @change="onChangeStartHour_v2($event, dateIndex)" v-model="StartWorkTime[dateIndex]">
                      <option  v-for="start in startTime" :value="start.value">{{ start.text}}</option>
                    </select>
                  </div>
                  <span>~</span>
                  <div class="ml-1">
                    {{ EndWorkTime[dateIndex] }}
                  </div>
                </div>
                <div class="mt-2 mr-4 flex">
                  <p class="ml-10">재택근무</p>
                  <div class="ml-3"><input type="checkbox"></div>
                </div>
              </div>
              <div class="ml-3 mt-5 pt-2" style="height:100px; width: 480px;">
                <div class="flex">
                  <div class="mr-3 text-black">
                    <select v-model="Task.groupSubId" @change="onChangeSelectMain($event,dateIndex, index)" style="width: 300px">
                      <option v-for="main in mainGroup[dateIndex]" :value="main.groupSubId">{{ main.codeMainNm }}</option>
                    </select>
                  </div>
                  <div class="text-black">
                    <select v-model="Task.codeId" @change="onChangeSelectSub($event,dateIndex, index)" style="width: 450px">
                      <option v-for="subtask in subTaskCopy[dateIndex][index]" :value="subtask.codeId">{{ subtask.codeSubNm }}</option>
                    </select>
                  </div>
                </div>
                <div style="width: 400px" class="text-black mt-3">
                  <input v-model="detailTask" style="width: 400px" type="input">
                </div>
              </div>

              <div class="grow"></div>
              <div class="mr-4 mt-2">
                <div class="flex mt-5">
                  <div>
                    <p>시간 :</p>
                  </div>
                  <div class="pb-1">
                    <input @change="onChangeTaskHour_v2($event,index,dateIndex)" min="1" v-model="Task.taskHour" style="width: 40px; height: 20px" class="ml-2 pl-3 text-black rounded" type="number">
                  </div>
                </div>
                <div class="mt-4 mr-4">
                  <p>{{ renderTaskStartHour[dateIndex][index] }} ~ {{ renderTaskEndHour[dateIndex][index] }}</p>
                </div>
              </div>
            </div>
          </div>
          <!--반복 종료 구간-->

        </div>

      </div>
    </div>
  </div>

</template>
<script>

import registerTask from '../assets/task.json';
import registerProject from '../assets/project.json'
import axios from "axios";

export default {
  name: 'RegisterPlanDetail',
  async mounted() {
    await this.GetWeekData();
    this.SetStartWeek();
    await this.getTask();
    await this.getMyProject();
    this.MountDataSet()
    this.ClassifyTaskType();
    this.MountSelect();
    this.SetTaskHour_v2();
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
      viewData: [[]],
      isHoliday : [true, true, true, true, true, true, true, true, true, true, true, true, true, true],
      detailTask: [],
      SendDate: [],


      //날짜에 대한 변수
      Viewdate:[],
      ViewDay:['월','화','수','목','금','토','일','월','화','수','목','금','토','일'],
      EndDayOfMonth: [31,28,31,30,31,30,31,31,30,31,30,31],


      //시간에 대한 변수
      StartWorkTime: [],
      EndWorkTime: [],

      //각 TaskTime
      planStartHour : "",
      taskStartHour : [],
      taskEndHour : [],
      taskTime :[],
      renderTaskStartHour:[],
      renderTaskEndHour:[],
      totalDayWorkTime: [],
      totalWeekWorkTime: 0,

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
      subTaskCopy: [[[]]],
      subProejct: [],
    }
  },
  props:{
    selectWeek:{
      type: String,
    } 
  },
  methods: {
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
                // console.log('데이터',this.startDate)
                console.log('주간데이터', this.searchWeekly)
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
    },
    MountDataSet(){
      var arr = []
      for(var i = 0;i<14;i++){
         arr = [{
          seq:1,
          taskHour:8,
          planDay: this.startDate+i,
          dayHour: 8,
          started_hour:"0900",
          endedHour: "1800",
          groupMainId:"TR001",
          groupSubId : "ZDUM1",
          codeId : "Z001",
          codeMainNm : "주업무",
          codeSubNm:"R&D 및 내부 PJT (NonPJT코드) - 시장조사, 분석, 계획, 설계/개발/테스트/이행",
          workDetail : "주업무",
          wfhYn : "0",
          enrollYn : "0",
          isHoliday : "N"
        }];
        if(i === 5 || i === 6 || i === 12 || i === 13){
          arr[0].isHoliday = "Y"
        }
        this.viewData[i] = arr;
      }

      //시간에 대한 변수의 2차원 배열 셋팅

      //select 변수의 2차원 배열의 형태 셋팅

      var start = Number(this.startDate)
      var year = start/10000

      if(year === 0 && year%100 != 0 || year%400 === 0){
          this.EndDayOfMonth[1] = 29;
        }
        var startMonth = parseInt((start%10000)/100);
        console.log(startMonth)
        var EndDay = this.EndDayOfMonth[startMonth-1];

        EndDay = parseInt(start/100)*100+EndDay;
        console.log(EndDay)
        var newMonth = 0;
        var newMonthDay = 0
        var newDay = 0
        var date =[]
         for(var i = 0;i<14;i++){
          date[i] = start+i;
          if(date[i] > EndDay){
            newDay = + parseInt(EndDay%10000) 
            newMonthDay = parseInt(EndDay/10000)*10000 + (parseInt(newDay/100)+1)*100 + 1
            date[i] = newMonthDay+newMonth;
            newMonth++;
          }
        }
        console.log(date)
      
      for(var i = 0;i<14;i++){
        var strdate = ''
        strdate = String(date[i])
        this.SendDate[i] = strdate
        this.Viewdate[i] = [strdate.slice(0,4),'.',strdate.slice(4,6),'.',strdate.slice(6,8)].join('');

        if(this.viewData[i][0].isHoliday === 'Y'){
          this.isHoliday[i] = false;
        }
      }
    },
    MountSelect() { // subTask 완성
      //MainTask - 완성
      //1차원 - indexDate, 2차원 - 특정 날짜의 seq, 3차원 - 해당 sub의 갯수
      var arr = [...this.subTask];
      var arr1 = []

      for(var j = 0;j<this.viewData.length;j++){
        arr1[j] = arr
      }
      for(var i = 0;i<14;i++){
        this.subTaskCopy[i] = arr1;
      }


      for(var k = 0;k<14;k++){
        for(var i = 0;i<this.viewData[k].length; i++){
          for(var j = 0; j<this.subTaskCopy[k][i].length; j++){

            if(this.subTaskCopy[k][i][j].groupSubId != this.viewData[k][i].groupSubId){
              this.subTaskCopy[k][i].splice(j,1)
              j--;
            }
          }
        }
      }

    },

    ClassifyTaskType() {
      //
      var MainArr = []

      for (var i = 0; i < this.taskType.length; i++) {
        if (this.taskType[i].groupId === 'TR001') {
          MainArr.push({
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
      for (var i = 0; i < this.project.length; i++) {
        MainArr.push({
          groupMainId: this.project[i].projectCode,
          groupSubId: 'TR002',
          codeMainNm: this.project[i].projectNm
        });
      }
      for(var i = 0;i<14;i++){
        this.mainGroup.push(MainArr)
      }

      for(var i = 0;i<14;i++){
        this.mainGroup.push(MainArr);
      }
    },
    // ClassifyProject() {
    //   var MainArr = []
    //   for (var i = 0; i < this.project.length; i++) {
    //     MainArr.push({
    //       groupMainId: this.project[i].projectCode,
    //       groupSubId: 'TR002',
    //       codeMainNm: this.project[i].projectNm
    //     });
    //   }
    //   for(var i = 0;i<14;i++){
    //     this.mainGroup.push(MainArr)
    //   }
    // },
    //------------------------- 미 완성 ---------------------------
    addTaskBox(dateIndex) {
      var len = this.viewData[dateIndex].length;

      this.viewData[dateIndex].push(
          {
            "plan_id": 0,
            "seq": len+1,
            "taskHour": 0,
            "planDay": this.viewData[dateIndex][0].planDay,
            "dayHour": this.viewData[dateIndex][0].planDay,
            "started_hour": this.viewData[dateIndex][len-1].endedHour,
            "endedHour": this.viewData[dateIndex][len-1].endedHour,
            "groupMainId": "TR001",
            "groupSubId":"ZDUM1",
            "codeId": "Z001",
            "codeMainNm": "주업무",
            "codeSubNm": "R&D 및 내부 PJT (NonPJT코드) - 시장조사, 분석, 계획, 설계/개발/테스트/이행",
            "workDetail": "",
            "wfhYn": "0",
            "enrollYn": "1",
            "memberId": "1",
            "isHoliday": "N"
          }
      );
    },
     removeTaskBox(dateIndex){
      if(this.viewData[dateIndex].length > 1){
        this.viewData[dateIndex].pop();
        this.taskStartHour[dateIndex].pop();
        this.taskEndHour[dateIndex].pop();
        this.RenderTime_v2(dateIndex);
      }else{
        alert('삭제할 수 없습니다.');
      }
    },
    onChangeSelectMain(event, dateIndex, index) {
      var sub = event.target.value

      this.viewData[dateIndex][index].groupSubId = sub; // -> 이 부분의 오류

      for (var i = 0; i < this.mainGroup[dateIndex].length; i++) {
        if (sub === this.mainGroup[dateIndex][i].groupSubId) {
          this.viewData[dateIndex][index].groupMainId = this.mainGroup[dateIndex][i].groupMainId;
          this.viewData[dateIndex][index].codeMainNm = this.mainGroup[dateIndex][i].codeMainNm;
          break;
        }
      }
      this.subTaskCopy[dateIndex][index] = [...this.subTask];


      for (var i = 0; i < this.subTaskCopy[dateIndex][index].length; i++) {
        if (sub != this.subTaskCopy[dateIndex][index][i].groupSubId) {
          this.subTaskCopy[dateIndex][index].splice(i, 1);
          i--;
        }
      }
    },
    onChangeSelectSub(event, dateIndex, index) {

      var code = event.target.value;

      this.viewData[dateIndex][index].codeId = code;
      console.log(this.subTask)
      for (var i = 0; i < this.subTask.length; i++) {
        if (this.subTask[i].codeId === code) {
          this.viewData[dateIndex][index].codeSubNm = this.subTask[i].codeSubNm;
        }
      }
    },
    //2차원 배열로 셋팅해야함
    SetTaskHour_v2(){
      for(var j = 0; j<14;j++){
        var taskstart = [], taskend =[], tasktime =[];
        for(var i = 0;i<this.viewData[j].length;i++){
           taskstart.push(Number(this.viewData[j][i].started_hour))
           taskend.push(Number(this.viewData[j][i].endedHour))
           tasktime.push(this.viewData[j][i].taskHour)
        }
        this.taskStartHour[j] = taskstart
        this.taskEndHour[j] = taskend
        this.taskTime[j] = tasktime
      }
      
      for(var i = 0;i<14;i++){
        this.totalDayWorkTime[i] = this.viewData[i][0].dayHour;
        this.StartWorkTime[i] = this.viewData[i][0].started_hour;
      }

      for(var i = 0;i<this.totalDayWorkTime.length;i++){
        if(this.isHoliday[i]){
          this.totalWeekWorkTime += this.totalDayWorkTime[i];
        }
      }
      console.log(this.taskTime)
      console.log(this.totalDayWorkTime);
      console.log(this.totalWeekWorkTime)
      this.RenderTime_all()
    },


    onChangeStartHour_v2(e, dateIndex){
      var daystart = e.target.value;
      daystart = Number(daystart);
      this.taskStartHour[dateIndex][0] = daystart;

      this.calTime_v2(dateIndex);
    },
    onChangeTaskHour_v2(e, index,dateIndex){

      this.taskTime[dateIndex][index] = Number(e.target.value);
      console.log(this.taskTime)
      var sumTime = 0
      for(var i = 0;i<this.taskTime[dateIndex].length;i++){
        sumTime += this.taskTime[dateIndex][i]
      }
      this.totalDayWorkTime[dateIndex] = sumTime

      var sumWeek = 0;
      for(var i = 0;i<this.totalDayWorkTime.length;i++){
        if(i === 5 || i === 6 || i === 12|| i === 13){
         console.log('주말') 
        }else{
          sumWeek += this.totalDayWorkTime[i];
        }
      }
      this.totalWeekWorkTime = sumWeek;
      console.log(this.totalWeekWorkTime)
      this.calTime_v2(dateIndex);


    },//시간 계산을 해주는 메서드 -> 시간 계산이 필요할 때 활용하면 된다.
    calTime_v2(dateIndex){
      //12시 이전에 계획이 끝나는 경우
      var time = 0;
      //12시 이후에 계획이 끝나는 경우
      var lunch = 0;
      var lunchTime = 100;
      for(var i = 0;i<this.taskTime[dateIndex].length;i++){
        time = this.taskStartHour[dateIndex][i]+(this.taskTime[dateIndex][i]*100);
        if(lunch == 0 && time > 1200){
          time += lunchTime;
          this.taskEndHour[dateIndex][i] = time
          lunch++;
        }else if(time === 1200 && lunch === 0){
          this.taskEndHour[dateIndex][i] = time;
          time += lunchTime;
          lunch++;
        }else{//12시 이전이거나 혹은 12시 이후 일때
          this.taskEndHour[dateIndex][i] = time;
        }
        if(i < this.taskTime[dateIndex].length-1){
          this.taskStartHour[dateIndex][i+1] = time;
        }

      }
      this.RenderTime_v2(dateIndex);
    },
    RenderTime_all(){

      for(var i = 0; i<14; i++){
        var start_arr = [], end_arr = [];
        for(var j = 0;j<this.taskStartHour[i].length;j++){
          var start = this.taskStartHour[i][j];
          var end = this.taskEndHour[i][j];

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

          start_arr[j] = [start.slice(0,2),':',start.slice(2,4)].join('');
          end_arr[j] = [end.slice(0,2),':',end.slice(2,4)].join('');
        }
        this.renderTaskStartHour[i] = start_arr;
        this.renderTaskEndHour[i] = end_arr;
        this.EndWorkTime[i] = end_arr[end_arr.length-1]

      }
    },
    RenderTime_v2(dateIndex){
      for(var i = 0;i<this.taskStartHour[dateIndex].length;i++){
       
      var index = this.taskStartHour[dateIndex].length-1

      var EndTime = this.taskEndHour[dateIndex][index];

      if(EndTime > 2400){
        EndTime -= 2400;
        this.taskEndHour[dateIndex][index] = EndTime;
        if(EndTime === 30){
          var EndDay = '00'+String(EndTime);
        }else{
          var EndDay = '0'+String(EndTime);
        }
      }else{
        var EndDay = String(EndTime);
      }

      this.EndWorkTime[dateIndex] = [EndDay.slice(0,2),':',EndDay.slice(2,4)].join('');

       var start = this.taskStartHour[dateIndex][i];
        var end = this.taskEndHour[dateIndex][i];

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
        this.renderTaskStartHour[dateIndex][i] = [start.slice(0,2),':',start.slice(2,4)].join('');
        this.renderTaskEndHour[dateIndex][i] = [end.slice(0,2),':',end.slice(2,4)].join('');
      }

      var start =  this.taskStartHour[dateIndex][0]
      this.StartWorkTime[dateIndex] = '0'+String(start)



    },
    //---------------------------------------경고창 모음집--------------------------------------
    alertTaskTime(e){
      var time = e.target.value;
      if(time <= 0){
        alert('음수는 입력할 수 없습니다.')
      }
    },
    async PostData(status){
      alert: for(var i = 0;i<14;i++){
        for(var j = 0;j<this.viewData[i].length;j++){
          if(this.viewData[i][j].taskHour === 0){
            var warn_day = String(this.viewData[i][j].planDay)
            warn_day = [warn_day.slice(0,4),'.',warn_day.slice(4,6),'.',warn_day.slice(6,8)].join('')
            alert(warn_day+'일의 시간을 입력해주세요')
            break alert;
          }
          var stringTaskStartHour = this.taskStartHour[i][j];
          var stringTaskEndHour = this.taskEndHour[i][j];

          if(stringTaskStartHour/100 <10){
            stringTaskStartHour = '0'+String(stringTaskStartHour);
          }else{
            stringTaskStartHour = String(stringTaskStartHour);
          }
          if(stringTaskEndHour/100 < 10){
            stringTaskEndHour = '0'+String(stringTaskEndHour);
          }else{
            stringTaskEndHour = '0'+String(stringTaskEndHour);
          }
          this.viewData[i][j].dayHour = this.totalDayWorkTime[i];
          this.viewData[i][j].started_hour = stringTaskStartHour;
          this.viewData[i][j].endedHour = stringTaskEndHour;
        }
      }

      var index = 0;
      for(var i = 0;i<14;i++){
        for(var j = 0; j<this.viewData[i].length;j++){
          if(i === 5 || i === 6 || i === 12 || i === 13){
            console.log(i,'주말')
          }else{
            this.sendData[index] = {
            planId:0,
            planDay: this.SendDate[i],
            seq: this.viewData[i][j].seq,
            taskHour: this.taskTime[i][j],
            dayHour: this.totalDayWorkTime[i],
            startedHour: this.viewData[i][j].started_hour,
            endedHour: this.viewData[i][j].endedHour,
            groupMainId: this.viewData[i][j].groupMainId,
            groupSubId: this.viewData[i][j].groupSubId,
            codeId : this.viewData[i][j].codeId,
            codeMainNm: this.viewData[i][j].codeMainNm,
            codeSubNm: this.viewData[i][j].codeSubNm,
            workDetail : this.viewData[i][j].workDetail,
            wfhYn: "0",
            enrollYn: status,
            isHoliday :"N"
          }
          
          index++;
          }
        }
      }
      console.log(this.sendData)
      if(status === '1'){
        await axios.post('/api/plans/drafts',this.sendData,{withCredentials: true})
            .then((res)=>{
              if(res.data.code === 1000){
                alert('계획이 수정되었습니다.')
                this.$router.go(0)
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
.main-scroll-bar{
  overflow-y: scroll;
}
.main-scroll-bar::-webkit-scrollbar{
  width: 10px;
  border-radius: 5px;
}
.main-scroll-bar::-webkit-scrollbar-thumb{
  border-radius: 5px;
  background-color: rgb(55 65 81);
}
.scroll-bar{
  overflow: hidden;
}
.sub-scroll-bar{
  overflow-y: scroll;
}
.sub-scroll-bar::-webkit-scrollbar{
  width: 10px;
  border-radius: 5px;
  background-color: rgb(31 41 55);
}
.sub-scroll-bar::-webkit-scrollbar-thumb{
  border-radius: 5px;
  background-color: rgb(55 65 81);
}

</style>
