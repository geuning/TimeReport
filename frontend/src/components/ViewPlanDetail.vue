<!--
  change에 대한 부분을 고려하지 않고, 단순히 뿌려주기만 하면 그만인 작업이기 때문에
  codeSubNm, codeMainNm만을 사용해서 화면에 띄워주기만 하면 됨

-->
<template>
  <!--검색 부분 -->
  <div style="height: 700px;" class="grow	rounded-lg ml-2 mt-5 text-gray-200 main-scroll-bar">
    <div class="rounded-lg h-16 bg-gray-700 ml-5 flex" style="max-width:1230px">
      <div class="h-8 place-self-center flex mt-2 ml-3">
        <p>계획 정보 : {{ week }}</p>
      </div>
      <div class="grow"></div>
      <div class="mt-5 mr-3"><p class="text-l">평일 <span class="text-rose-500">{{ totalWeekWorkTime }}</span>시간</p></div>
    </div>
    <div style="width: 1250px" class="flex mt-4" >
      <div class="grow"></div>
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
          <div class="border-b-2 mr-">
          </div>
        </div>
        <!-- 컨텐츠 부분 -->
        <div class="pl-5 ml-4 pt-5 text-gray-200 sub-scroll-bar bg-gray-700 rounded-b-lg" style="width: 1235px; height: 200px;">
          <!-- 표 만들기 -->

          <div v-for="(Task,index) in viewData[dateIndex]" class="bg-gray-600 mr-4 rounded-xl mb-4" style="height: 120px">

            <div class="flex">
              <div class="mr-3 pt-4 ml-3 border-r" style="width:200px; height: 120px;">
                <div class="flex mt-2 ml-8">
                  <div class="mr-1">
                    <p class="text-xl">{{[StartWorkTime[dateIndex].slice(0,2),':',StartWorkTime[dateIndex].slice(2,4)].join('')}}</p>
                  </div>
                  <span class="text-xl">~</span>
                  <div class="ml-1">
                    <p class="text-xl">{{ EndWorkTime[dateIndex] }}</p>
                  </div>
                </div>
                <div style="width:300px;" class="mt-2 ml-10">
                  <div v-if="isWfh(dateIndex)" style="width:105px;" class="bg-sky-300 w-16 h-6 rounded-md">재택근무</div>                
                </div>
              </div>
              <div class="ml-3 mt-5 pt-2" style="height:100px; width: 800px;">
                <div class="flex">
                  <div class="mr-3 bg-gray-500 shadow rounded">
                    <p style="width: 100px">{{Task.codeMainNm}}</p>
                  </div>
                  <div style="width: 700px; overflow: hidden;" class="bg-gray-500 shadow rounded">
                    <p style="width: 800px;" class="text-left ml-2">{{Task.codeSubNm}}</p>
                  </div>
                </div>
                <div style="width: 600px; overflow: hidden;" class="mt-3 bg-gray-500 shadow rounded">
                  <p class="text-left ml-2">{{Task.workDetail}}</p>
                </div>
              </div>

              <div class="grow"></div>
              <div class="mr-4 mt-5 bg-gray-500 pl-2 rounded shadow" style="height: 80px">
                <div class="flex mt-3">
                  <div>
                    <p>시간 :</p>
                  </div>
                  <div class="pb-1">
                    <p>{{Task.taskHour}}</p>
                  </div>
                </div>
                <div class="mt-2 mr-4">
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
  name: 'ViewPlanDetail',
  async mounted() {
    await this.getTask();
    await this.getMyProject();
    await this.GetWeekData();
    this.SetStartWeek();
    await this.GetData();
    this.MountDataSet();

    

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
      getData: [],

      //화면에 뿌려주는 변수
      viewData: [[]],
      isHoliday : [true, true, true, true, true, true, true, true, true, true, true, true, true, true],
      detailTask: [],


      //날짜에 대한 변수
      Viewdate:[],
      ViewDay:['월','화','수','목','금','토','일','월','화','수','목','금','토','일'],


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
      EndDayOfMonth: [31,28,31,30,31,30,31,31,30,31,30,31],


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
    //---------------------------------------------------axios get -------------------------------------
    async GetWeekData(){
        await axios.get('/api/biweekly',{withCredentials: true})
            .then((res)=>{
              if(res.data.code === 1000){
                this.searchWeekly = res.data.result.twoWeeksDtos;
                // this.startDate = res.data.result.startOfWeek;
                // console.log('데이터',this.startDate)
                // console.log('주간데이터', this.searchWeekly)
                this.startDate = this.$route.params.selectWeek;
              }else{
                alert(this.backMessage);
                localStorage.setItem('memberId', '0')
                localStorage.setItem('memberNm','No');
                localStorage.setItem('grade','GEUST');
                this.$router.push('/')
              }
            })
            .catch((res)=>{
              console.error(res);
            })
      },
    SetStartWeek(){ 
      for(var i = 0;i<this.searchWeekly.length;i++){
        if(this.searchWeekly[i].fromdt === this.startDate){
          this.week = this.searchWeekly[i].content;
          break;
        }
      }
    },
    async GetData(){
      await axios.get('/api/plans',{
        params:{
          day: this.startDate
        },withCredentials:true
      })
      .then((res)=>{
        if(res.data.code===1000){
          this.getData = res.data.result;
        }else if(res.data.code === 5006){
          alert(this.backMessage);
          localStorage.setItem('memberId', '0')
          localStorage.setItem('memberNm','No');
          localStorage.setItem('grade','GEUST');
          this.$router.push('/')
        }else{
          this.$router.push('/plan');
        }
      })
    },
     async getTask(){
      var testtask
      await axios.get('/api/tasks',{withCredentials:true})
          .then((res)=>{
            if(res.data.code === 1000){
              this.taskType = res.data.result;
            }else{
              alert(this.backMessage);
              localStorage.setItem('memberId', '0')
              localStorage.setItem('memberNm','No');
              localStorage.setItem('grade','GEUST');
              this.$router.push('/')
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
              localStorage.setItem('memberId', '0')
              localStorage.setItem('memberNm','No');
              localStorage.setItem('grade','GEUST');
              this.$router.push('/')
            }
          })
          .catch((res)=>{
            console.error(res);
          })
    },
    MountDataSet(){
      
      var start = Number(this.startDate)
      var uniqe = 0
      var date = [];
      var DayOfDate = [];
      for(var i = 0;i<14;i++){
        date.push(start+i)
      }
      console.log(date)

      for(var i = 0;i<14;i++){
        var arr = []
        for(var j = 0;j<this.getData.length;j++){
          if(date[i] === this.getData[j].planDay){
            arr.push(this.getData[i])
          }
        }
        this.viewData[i] = arr
      }
      console.log(this.viewData)
      

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
        this.Viewdate[i] = [strdate.slice(0,4),'.',strdate.slice(4,6),'.',strdate.slice(6,8)].join('');

        if(this.viewData[i][0].isHoliday === 'Y'){
          this.isHoliday[i] = false;
        }
      }
    },
    // ClassifyProject() {
    //   var MainArr = []
    //   for (var i = 0; i < this.project.length; i++) {
    //     MainArr.push({
    //       groupMainId: this.project[i].project_code,
    //       groupSubId: 'TR002',
    //       codeMainNm: this.project[i].project_nm
    //     });
    //   }
    //   for(var i = 0;i<14;i++){
    //     this.mainGroup.push(MainArr)
    //   }
    // },
    
    //2차원 배열로 셋팅해야함
    SetTaskHour_v2(){
      for(var j = 0; j<14;j++){
        var taskstart = [], taskend =[], tasktime =[];
        for(var i = 0;i<this.viewData[j].length;i++){
           taskstart.push(Number(this.viewData[j][i].startedHour))
           taskend.push(Number(this.viewData[j][i].endedHour))
           tasktime.push(this.viewData[j][i].taskHour)
        }
        this.taskStartHour[j] = taskstart
        this.taskEndHour[j] = taskend
        this.taskTime[j] = tasktime
      }
      for(var i = 0;i<14;i++){
        this.totalDayWorkTime[i] = this.viewData[i][0].dayHour;
        this.StartWorkTime[i] = this.viewData[i][0].startedHour;
      }

      for(var i = 0;i<this.totalDayWorkTime.length;i++){
        if(this.isHoliday[i]){
          this.totalWeekWorkTime += this.totalDayWorkTime[i];
        }
      }
      this.RenderTime_all()
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
      var config = 0;

      for(var i = 0;i<this.taskStartHour[dateIndex].length;i++){
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


      console.log(this.StartWorkTime[dateIndex][0])

      var index = this.taskStartHour[dateIndex].length-1
      var EndDay = String(this.taskEndHour[dateIndex][index])
      this.EndWorkTime[dateIndex] = [EndDay.slice(0,2),':',EndDay.slice(2,4)].join('');

    },
    //---------------------------------------경고창 모음집--------------------------------------
    alertTaskTime(e){
      var time = e.target.value;
      if(time <= 0){
        alert('음수는 입력할 수 없습니다.')
      }
    },
    SaveData(){
      alert: for(var i = 0;i<14;i++){
        for(var j = 0;j<this.viewData[i].length;j++){
          if(this.viewData[i][j].taskHour === 0){
            var warn_day = String(this.viewData[i][j].plan_day)
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
          this.viewData[i][j].startedHour = stringTaskStartHour;
          this.viewData[i][j].endedHour = stringTaskEndHour;
        }
      }

      if(this.year === 0 && this.year%100 != 0 || this.year%400 === 0){
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

      var index = 0;
      for(var i = 0;i<14;i++){
        for(var j = 0; j<this.viewData[i].length;j++){
          this.sendData[index] = {
            plan_day: String(date[i]),
            seq: this.viewData[i][j].seq,
            taskHour: this.view[i][j].taskHour,
            dayHour: this.viewData[i][j].dayHour,
            startedHour: this.viewData[i][j].startedHour,
            endedHour: this.viewData[i][j].endedHour,
            groupMainId: this.viewData[i][j].groupMainId,
            groupSubId: this.viewData[i][j].groupSubId,
            codeId : this.viewData[i][j].codeId,
            codeMainNm: this.viewData[i][j].codeMainNm,
            codeSubNm: this.viewData[i][j].codeSubNm,
            workDetail : this.viewData[i][j].workDetail,
            wfhYn: "0",
            enrollYn: '0',
            isHoliday :"N"
          }
          if(i === 5 || i === 6 || i === 12 || i === 13){
            this.sendData[index].isHoliday = "Y";
          }
          index++;
        }
      }
      console.log(this.sendData)
    },
    isWfh(index){
      if(this.viewData[index][0].wfhYn === '0'){
        return false;
      }else{
        true;
      }
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
  background-color: rgb(30 41 59);
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
  background-color: rgb(100 116 139);
}
.sub-scroll-bar::-webkit-scrollbar-thumb{
  border-radius: 5px;
  background-color: rgb(30 41 59);
}
.shadow{
  box-shadow: 1px 1px 0.3px 0.3px black;
}

</style>
